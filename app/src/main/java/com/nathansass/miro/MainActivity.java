package com.nathansass.miro;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
/*
    - Cache Bitmaps https://developer.android.com/training/displaying-bitmaps/cache-bitmap.html
    - Load Large Bitmaps by sampling https://developer.android.com/training/displaying-bitmaps/load-bitmap.html
    - Get Bitmaps off the UI Thread https://developer.android.com/training/displaying-bitmaps/process-bitmap.html
    - LruCache: https://developer.android.com/reference/android/util/LruCache.html
 */

public class MainActivity extends AppCompatActivity {
    ImageView mImageView;
    Button btnLoadResource;
    Button btnClearFileCache;
    Button btnClearMemoryCache;
    private static final int IO_BUFFER_SIZE = 8 * 1024;
    private static final int DISK_CACHE_INDEX = 0;
    String mImageUrl = "https://lh6.googleusercontent.com/-55osAWw3x0Q/URquUtcFr5I/AAAAAAAAAbs/rWlj1RUKrYI/s1024/A%252520Photographer.jpg";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        mImageView = (ImageView) findViewById(R.id.ivImage);
        btnLoadResource = (Button) findViewById(R.id.btnLoadResource);
        btnClearFileCache = (Button) findViewById(R.id.btnClearFileCache);
        btnClearMemoryCache = (Button) findViewById(R.id.btnClearMemoryCache);

        Miro.loadImage(this, mImageUrl, mImageView);

        setClickListeners();

    }

    public void setClickListeners() {
        btnClearFileCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Miro.clearFileCache(context);
            }
        });

        btnLoadResource.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Miro.loadImage(context, mImageUrl, mImageView);
            }
        });

        btnClearMemoryCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Miro.clearMemoryCache(context);
            }
        });
    }
}
