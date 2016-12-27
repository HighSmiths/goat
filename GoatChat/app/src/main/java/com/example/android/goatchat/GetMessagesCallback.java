package com.example.android.goatchat;

import java.util.HashMap;

/**
 * Created by mz on 12/27/16.
 */

public interface GetMessagesCallback {
    public void execute(HashMap<String,String> messages);
}
