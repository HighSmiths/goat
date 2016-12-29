package com.example.android.goatchat.callback;

import com.example.android.goatchat.models.Message;

import java.util.Map;

/**
 * Created by mz on 12/27/16.
 */

public interface GetMessagesCallback {
    public void execute(Map<String, Message> messages);
}
