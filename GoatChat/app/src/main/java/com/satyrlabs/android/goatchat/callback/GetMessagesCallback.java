package com.satyrlabs.android.goatchat.callback;

import com.satyrlabs.android.goatchat.models.Message;

import java.util.Map;

/**
 * Created by mz on 12/27/16.
 */

public interface GetMessagesCallback {
    public void execute(Map<String, Message> messages);
}
