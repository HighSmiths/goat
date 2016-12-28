package com.example.android.goatchat;

import java.util.HashMap;

/**
 * Created by mz on 12/27/16.
 */

public interface GetAllUsersCallback {
    public void execute(HashMap<String, User> users);
}
