package com.example.android.goatchat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mz on 12/27/16.
 */

public interface GetMessagesCallback {
    public void execute(Map<String, String> messages);
}
