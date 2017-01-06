package com.satyrlabs.android.goatchat.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.satyrlabs.android.goatchat.Constants;
import com.satyrlabs.android.goatchat.Database;
import com.satyrlabs.android.goatchat.R;
import com.satyrlabs.android.goatchat.util.IabHelper;
import com.satyrlabs.android.goatchat.util.IabResult;
import com.satyrlabs.android.goatchat.util.Inventory;

import java.util.ArrayList;

public class GoatSelectionActivity extends AppCompatActivity {
    IabHelper mHelper;
    String receiver;
    String sender;
    ArrayList<String> senders;
    ArrayList<String> receivers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goat_selection);
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
      //  setSupportActionBar(toolbar);


        setupBilling();


        Bundle bundle = getIntent().getExtras();
      //  receiver = bundle.getString("receiver");
       // sender = bundle.getString("sender");
        try {
            senders = bundle.getStringArrayList("senders");
            receivers = bundle.getStringArrayList("receivers");
        }
        catch (Exception e)
        {

        }

        ImageView happy = (ImageView) findViewById(R.id.HappyG);
        ImageView sex = (ImageView) findViewById(R.id.SexyG);
        sex.setImageBitmap(blur(sex));
        ImageView nostalgic = (ImageView) findViewById(R.id.NostalgicG);
        nostalgic.setImageBitmap(blur(nostalgic));
        ImageView obsequious = (ImageView) findViewById(R.id.ObsequiousG);
        obsequious.setImageBitmap(blur(obsequious));
        ImageView frat = (ImageView) findViewById(R.id.FratGoat);
        frat.setImageBitmap(blur(frat));
    }

    public Bitmap blur(ImageView im)
    {
        BitmapDrawable drawable = (BitmapDrawable) im.getDrawable();
        Bitmap src = drawable.getBitmap();
        src= Bitmap.createScaledBitmap(src, 10,10, true);
        return src;
    }

    public void sendHappyGoats(View view){
        Log.d("ca","calling");
       // Database.instance.createMessage("TEMP",sender, receiver, 1);
     //   finish();
        Log.d(Constants.LOG_TAG, ""+senders.size());
        for(int i=0; i<senders.size(); i++){
              Database.instance.createMessage("TEmp",senders.get(i),receivers.get(i),1);
        }
        finish();

    }
    public void sendSadGoats(View view){
      //  Database.instance.createMessage("TEMP",sender,receiver,0);
     //   finish();
        for(int i=0; i<senders.size(); i++){
            Database.instance.createMessage("TEmp",senders.get(i),receivers.get(i),0);
        }
        finish();
    }

    public void sendSexyGoats(View view){
        Log.d(Constants.LOG_TAG, "Clicked on sexygoat");
        try {
//            https://developer.android.com/google/play/billing/billing_testing.html#billing-testing-static
//            https://developer.android.com/training/in-app-billing/purchase-iab-products.html
            mHelper.launchPurchaseFlow(this, "android.test.canceled", 1, null, null);
        } catch (Exception e) {
            Log.d(Constants.LOG_TAG, "Exception on Purchase Flow: " + e.getMessage());
        }

    }
    public void sendObsequiousGoats(){


    }
    public void sendNostalgicGoats(){

    }
    public void sendFratGoats(){

    }

    private void setupBilling() {
        String base64EncodedPublicKey = Constants.LICENSE_KEY;

        // compute your public key and store it in base64EncodedPublicKey
        mHelper = new IabHelper(this, base64EncodedPublicKey);


        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    // Oh no, there was a problem.
                    Log.d(Constants.LOG_TAG, "Problem setting up In-app Billing: " + result);
                }
//                If it works!!!
                else {
                    Log.d(Constants.LOG_TAG, "In-app Billing set up:" + result);
                    getPurchasableProducts();

                }


            }
        });
    }

    private void getPurchasableProducts() {
        final String sexyGoatString = "android.test.purchased";

        IabHelper.QueryInventoryFinishedListener
                mQueryFinishedListener = new IabHelper.QueryInventoryFinishedListener() {
            public void onQueryInventoryFinished(IabResult result, Inventory inventory)
            {
                if (result.isFailure()) {
                    // handle error
                    return;
                }
                Log.d(Constants.LOG_TAG, inventory.toString());
                String sexyGoatPrice = inventory.getSkuDetails(sexyGoatString).getPrice();
                Log.d(Constants.LOG_TAG, "Sexy Goat PRice: " + sexyGoatPrice);
                // update the UI
            }
        };


        ArrayList<String> additionalSkuList = new ArrayList<>();
        additionalSkuList.add(sexyGoatString);
        try {
            mHelper.queryInventoryAsync(true, additionalSkuList, null, mQueryFinishedListener);
        } catch (Exception e) {
            Log.d(Constants.LOG_TAG, "Exception trying to query inventory: " + e.getMessage());
        }

    }

}
