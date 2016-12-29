package com.example.android.goatchat;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.List;


/**
 * Created by mz on 12/29/16.
 */

public class PhoneContacts {
    static final String CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
    static final String PHONE_NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

    public static void askContactsPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            activity.requestPermissions(new String[]{android.Manifest.permission.READ_CONTACTS}, 1);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            getContacts(activity);
        }
    }

    public static void getContacts(Activity activity) {
        // Sets the columns to retrieve for the user profile
        String [] mProjection = new String[]
                {
                        CONTACT_ID,
                        PHONE_NUMBER
                };

// Retrieves the profile from the Contacts Provider
        Cursor contactsCursor =
                activity.getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        mProjection ,
                        null,
                        null,
                        null);

        Log.d(Constants.LOG_TAG, contactsCursor.toString());

//        CODE: http://stackoverflow.com/questions/15243278/android-get-all-contacts-telephone-number-in-arraylist
        if (contactsCursor != null) {
            if (contactsCursor.getCount() > 0) {
//                HashMap<Integer, ArrayList<String>> phones = new HashMap<>();
                while (contactsCursor.moveToNext()) {
                    String phoneNumber = contactsCursor.getString(contactsCursor.getColumnIndex(PHONE_NUMBER));
                    Log.d(Constants.LOG_TAG, phoneNumber);
                }
            }
            contactsCursor.close();
        }
    }
}
