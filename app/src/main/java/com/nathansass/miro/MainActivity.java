package com.nathansass.miro;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
/*
    - Cache Bitmaps https://developer.android.com/training/displaying-bitmaps/cache-bitmap.html
    - Load Large Bitmaps by sampling https://developer.android.com/training/displaying-bitmaps/load-bitmap.html
    - Get Bitmaps off the UI Thread https://developer.android.com/training/displaying-bitmaps/process-bitmap.html
    - LruCache: https://developer.android.com/reference/android/util/LruCache.html
 */

public class MainActivity extends AppCompatActivity {
    ImageView mImageView;
    private static final int IO_BUFFER_SIZE = 8 * 1024;
    private static final int DISK_CACHE_INDEX = 0;
    String mImageUrl = "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView) findViewById(R.id.ivImage);

        ImageLoader imageLoaderSD = new ImageLoader(this, mImageUrl, mImageView);
        imageLoaderSD.execute();

        ImageLoader imageLoaderMem = new ImageLoader(this, mImageUrl, mImageView);
        imageLoaderMem.execute();
    }


//    public void loadBitmap(int resId, ImageView imageView) {
//        final String imageKey = String.valueOf(resId);
//
//        final Bitmap bitmap = getBitmapFromMemCache(imageKey);
//        if (bitmap != null) {
//            mImageView.setImageBitmap(bitmap);
//        } else {
////            mImageView.setImageResource(R.drawable.image_placeholder);
//            BitmapWorkerTask task = new BitmapWorkerTask(mImageView);
//            task.execute(resId);
//        }
//    }
//
//    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
//        private final WeakReference<ImageView> imageViewReference;
//        private int data = 0;
//
//        public BitmapWorkerTask(ImageView imageView) {
//            // Use a WeakReference to ensure the ImageView can be garbage collected
//            imageViewReference = new WeakReference<ImageView>(imageView);
//        }
//
//        // Decode image in background.
//        @Override
//        protected Bitmap doInBackground(Integer... params) {
//            final Bitmap bitmap = decodeSampledBitmapFromResource(
//                    getResources(), params[0], 100, 100);
//
//            addBitmapToMemoryCache(String.valueOf(params[0]), bitmap);
//
//            return bitmap;
//        }
//
//        // Once complete, see if ImageView is still around and set bitmap.
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            if (imageViewReference != null && bitmap != null) {
//                final ImageView imageView = imageViewReference.get();
//                if (imageView != null) {
//                    imageView.setImageBitmap(bitmap);
//                }
//            }
//        }
//    }
//
//    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
//                                                         int reqWidth, int reqHeight) {
//
//        // First decode with inJustDecodeBounds=true to check dimensions
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeResource(res, resId, options);
//
//        // Calculate inSampleSize
//        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
//
//        // Decode bitmap with inSampleSize set
//        options.inJustDecodeBounds = false;
//        return BitmapFactory.decodeResource(res, resId, options);
//    }
//
//    public static int calculateInSampleSize(
//            BitmapFactory.Options options, int reqWidth, int reqHeight) {
//        // Raw height and width of image
//        final int height = options.outHeight;
//        final int width = options.outWidth;
//        int inSampleSize = 1;
//
//        if (height > reqHeight || width > reqWidth) {
//
//            final int halfHeight = height / 2;
//            final int halfWidth = width / 2;
//
//            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
//            // height and width larger than the requested height and width.
//            while ((halfHeight / inSampleSize) >= reqHeight
//                    && (halfWidth / inSampleSize) >= reqWidth) {
//                inSampleSize *= 2;
//            }
//        }
//
//        return inSampleSize;
//    }

}
