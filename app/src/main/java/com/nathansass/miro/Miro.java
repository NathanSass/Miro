package com.nathansass.miro;

import android.content.Context;
import android.widget.ImageView;

public class Miro {
    private static Miro instance = null;

    private MemoryCache memoryCache;

    private FileCache fileCache;
    private Context context;
    protected Miro(Context context) {
        this.memoryCache = new MemoryCache();
        this.fileCache = new FileCache(context);
        this.context = context;
    }
    public static Miro get(Context context) {
        if(instance == null) {
            instance = new Miro(context);
        }
        return instance;
    }

    public static void loadImage(Context context, String url, ImageView imageView) {
        Miro miro = Miro.get(context);
        new ImageLoader(miro.getFileCache(), miro.getMemoryCache(), url, imageView).execute();
    }

    public static void clearMemoryCache(Context context) {
        Miro miro = Miro.get(context);
        miro.getMemoryCache().clear();
    }

    public static void clearFileCache(Context context) {
        Miro miro = Miro.get(context);
        miro.getFileCache().clear();
    }

    private MemoryCache getMemoryCache() {
        return memoryCache;
    }

    private FileCache getFileCache() {
        return fileCache;
    }

}
