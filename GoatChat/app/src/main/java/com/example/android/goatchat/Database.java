package com.example.android.goatchat;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.*;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.util.HashMap;


/**
 * Created by mz on 12/26/16.
 */

public class Database {
    public static Database instance = new Database();

    private FirebaseDatabase database;

    private Database() {
        database = FirebaseDatabase.getInstance();
    }

    public void setData(String data) {
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue(data);
    }

    public void createNewUserRecordInFirebase(String userId, String email) {
        User user = new User(email);
        user.friends.put("id", userId);
        database.getReference("users/" + userId).setValue(user);

    }

    public void execute() {
        Log.d("MSG", "Executing DB methods");

        FirebaseUser curUser = FirebaseAuth.getInstance().getCurrentUser();

        String uid = curUser.getUid();
        String email = curUser.getEmail();
        Log.d("CUR_USR_uid: ", uid);
        Log.d("CUR_USR_email: ", email);

        createNewUserRecordInFirebase(uid, email);

    }
}
