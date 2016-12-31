package com.satyrlabs.android.goatchat.callback;

import com.satyrlabs.android.goatchat.models.User;

import java.util.Map;

/**
 * Created by mz on 12/27/16.
 */

public interface GetUsersCallback {
    public void execute(Map<String, User> users, boolean success);
}
