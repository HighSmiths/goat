package com.example.sisyphus.bittest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{
            Uri imageUri = "https://scontent.xx.fbcdn.net/v/t1.0-1/p100x100/923432_10154072935492792_3019523864008481966_n.jpg?oh=05205aaf7eafc45066b55d30e9419197&oe=58DC1D24";
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
        }
        catch(Exception e){
        }
    }
}
