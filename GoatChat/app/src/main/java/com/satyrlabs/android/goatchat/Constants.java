package com.satyrlabs.android.goatchat;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;

/**
 * Created by mz on 12/26/16.
 */

public class Constants {
    public static String LOG_TAG = "GOAT";
    public static String CANT_CREATE_ACCOUNT = "Sorry try again";
    public static String CANT_SIGN_IN = "Couldn't sign in";
    public static String EMAIL_INVALID = "Email is invalid";

    public static String USER_DOESNT_EXIST = "That user doesn't exist";
    public static String USERNAME_ALREADY_EXISTS = "That username already exists";
    public static String PASSWORD_TOO_SHORT = "Password must be over 6 characters";

    public static final String MERCHANT_NAME = "Awesome Bike Store";
    public static final String CURRENCY_CODE_USD = "USD";
    public static final String LICENSE_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAlwq3OwsdSb3zQ2XUyOFdA+u66eFjHEurWuPYNx2Y/ULy6cZIq/dlAmIPuxRUYjfGNIO+Bi2Ioi3sJtjMg6YSmbDgbipRTbN+3IhsEfvbthb3izGRMCq8U+PjmRfRZRul8MksTNpE9Mj92CkjtDk3RmSEtT7JtZLuDaZHUPFdHkQGZosQG9NyoT42pma9BlGudNBjRhN5oHXtTTwOyoDrvaG2PKUMJ84XXamxIaPyO+k6BjhzQ114xe6cKfxy08gwb+13HJWlsa5IcpiP1tiFrbpLJ1ouIJB4Uq73e7BYiac5Jf6ofqz/9+UfnsGfVR78FxL3/s4vePa13p4PlZeqVwIDAQAB";
    public static final Typeface tf(Activity activity) {
        Typeface tf = Typeface.DEFAULT;
        try {
            tf = Typeface.createFromAsset(activity.getResources().getAssets(), "Axis Extrabold.otf");
        } catch (Exception e) {
            Log.d(Constants.LOG_TAG, "Couldn't create font");
        }

        return tf;
    }

    public static String encodeTobase64(Bitmap image)
    {
        Bitmap immagex=image;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        immagex.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);
        return imageEncoded;
    }

    public static Bitmap decodeBase64(String input)
    {
        if (input == null)
            return null;
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }



}
