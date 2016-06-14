package com.jyuesong.android.scalabeimageview;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ScalableImageView mScalableImageView;
    private static final int MY_PERMISSIONS_REQUEST_WRITE = 104;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_WRITE);
        }

        mScalableImageView = (ScalableImageView) findViewById(R.id.scale);
    }

    public void reset(View v) {
        mScalableImageView.reset();
    }

    public void save(View v) {


        String rsult = saveMyBitmap(saveViewBitmap(mScalableImageView), System.currentTimeMillis() + ".png");

        Intent intent = new Intent(this, ResultActivit.class);
        intent.putExtra("rsult", rsult);
        startActivity(intent);
    }

    private Bitmap saveViewBitmap(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap bitmap = view.getDrawingCache(true);

        Bitmap bmp = duplicateBitmap(bitmap);
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        // clear the cache
        view.setDrawingCacheEnabled(false);
        return bmp;
    }

    public static Bitmap duplicateBitmap(Bitmap bmpSrc) {
        if (null == bmpSrc) {
            return null;
        }

        int bmpSrcWidth = bmpSrc.getWidth();
        int bmpSrcHeight = bmpSrc.getHeight();

        Bitmap bmpDest = Bitmap.createBitmap(bmpSrcWidth, bmpSrcHeight, Bitmap.Config.ARGB_8888);
        if (null != bmpDest) {
            Canvas canvas = new Canvas(bmpDest);
            final Rect rect = new Rect(0, 0, bmpSrcWidth, bmpSrcHeight);

            canvas.drawBitmap(bmpSrc, rect, rect, null);
        }

        return bmpDest;
    }

    public String saveMyBitmap(Bitmap mBitmap, String bitName) {
        File f = new File("/sdcard/" + bitName);
        FileOutputStream fOut = null;
        try {
            f.createNewFile();
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
        try {
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return f.toString();
    }
}
