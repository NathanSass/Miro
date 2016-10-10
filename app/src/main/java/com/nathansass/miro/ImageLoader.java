package com.nathansass.miro;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by nathansass on 10/10/16.
 */



public class ImageLoader extends AsyncTask<Void, Void, Bitmap> {
    String urlString;
    ImageView imageView;
    FileCache fileCache;
    Context context;
    MemoryCache memoryCache;

    public ImageLoader(Context context, String url, ImageView imageView) {
        this.context = context;
        this.urlString = url;
        this.imageView = imageView;
        fileCache = new FileCache(context);
        memoryCache = new MemoryCache();
    }

    @Override
    protected Bitmap doInBackground(Void... params) {

        // From memory:
        Bitmap bitmap = memoryCache.getBitmapFromMemCache(urlString);
        if (bitmap !=null) {
            Log.v("DEBUG", "From Memory");
            return bitmap;
        }

        // From SD cache:
        File f = fileCache.getFile(urlString);
        Bitmap b = decodeFile(f);
        if(b!=null) {
            Log.v("DEBUG", "From SD cache");
            return b;
        }

        // From Web

        try {
            URL imageUrl = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is=conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            Utils.CopyStream(is, os);
            os.close();
            bitmap = decodeFile(f);
            Log.v("DEBUG", "From Web");
            return bitmap;
        } catch (Throwable ex){
            ex.printStackTrace();
        }
        return bitmap;
    }


    @Override
    protected void onPostExecute(Bitmap bitmap) {
        memoryCache.addBitmapToMemoryCache(urlString, bitmap);
        imageView.setImageBitmap(bitmap);
    }

    public Bitmap decodeFile(File f) {

        try {
            //decode image size
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,options);

            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 70;
            int width_tmp = options.outWidth, height_tmp=options.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }

            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {}
        return null;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

}
