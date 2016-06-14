package com.jyuesong.android.scalabeimageview;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class ResultActivit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        String result = getIntent().getStringExtra("rsult");
        ImageView imageView = (ImageView) findViewById(R.id.imageview);
        imageView.setImageURI(Uri.parse(result));


    }
}
