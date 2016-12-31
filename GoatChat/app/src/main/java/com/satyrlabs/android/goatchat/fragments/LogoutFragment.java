package com.satyrlabs.android.goatchat.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.satyrlabs.android.goatchat.Constants;
import com.satyrlabs.android.goatchat.Database;
import com.satyrlabs.android.goatchat.R;
import com.satyrlabs.android.goatchat.activity.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by mz on 12/29/16.
 */

public class LogoutFragment extends Fragment {
    Activity activity;
    View view;


//    TODO: Something about having activity implement an interface???
//    http://stackoverflow.com/questions/14354279/call-parents-activity-from-a-fragment
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

//      Retain a reference to the parent Activity.
        this.activity = activity;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(Constants.LOG_TAG, "creating logout view fragment");
        view = inflater.inflate(R.layout.logout_view, container, false);

        bitmapShit();

        return view;
    }

    private void bitmapShit() {
        try {

            final Uri imageUri = FirebaseAuth.getInstance().getCurrentUser().getProviderData().get(0).getPhotoUrl();
            Log.d(Constants.LOG_TAG,"uri"+ imageUri);
//                            run on a background thread
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        final Bitmap aoeu = Glide.with(activity).
                                load(imageUri.toString()).
                                asBitmap().
                                into(100, 100). // Width and height
                                get();
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ((ImageView) view.findViewById(R.id.imageView)).setImageBitmap(aoeu);
                            }
                        });
                        Log.d(Constants.LOG_TAG, "bitmap" + aoeu.toString());
                    } catch (Exception e) {
                        Log.d(Constants.LOG_TAG, "bitmap exception: " + e.getMessage());
                    }
                }
            });

        }
        catch(Exception e)
        {
            Log.d(Constants.LOG_TAG, "not even close"+e.toString());
        }
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(activity, MainActivity.class);
        if(isFacebookUser()){
            //TODo logout of fb
        }
        startActivity(intent);
        Log.d(Constants.LOG_TAG, "Logging out");
    }

    public boolean isFacebookUser()
    {
        return false;  //TODO check if is fb uses
    }

}
