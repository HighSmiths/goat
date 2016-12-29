package com.example.android.goatchat.callback;

import java.util.Map;

/**
 * Created by mz on 12/27/16.
 */

public interface GetMessagesCallback {
    public void execute(Map<String, String> messages);
}
