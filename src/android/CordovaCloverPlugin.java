package com.tekorius.cordovaCloverPlugin;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

public class CordovaCloverPlugin extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        Log.d("CordovaCloverPlugin", "Action called!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

        if (action.equals("test")) {
            callbackContext.success("yay");
            return true;
        }

        return false;
    }
}
