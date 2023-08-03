package sevensky.cordova.plugins;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IntentPlugin extends CordovaPlugin {

    private CallbackContext callbackContext;
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        this.callbackContext = callbackContext;
        if (action.equals("startActivity")) {
            String appName = args.getString(0);
            String activityName = args.getString(1);
            JSONObject jsonObject=new JSONObject(args.getString(2));
            Bundle bundle=new Bundle();
            for(int i = 0; i<jsonObject.names().length(); i++){
                bundle.putString(jsonObject.names().getString(i) ,
                        jsonObject.get(jsonObject.names().getString(i)).toString());
            }
            this.startActivity(appName,activityName, callbackContext,bundle);
            return true;
        }
        return false;
    }


    private void startActivity(String appName, String activityName, CallbackContext callbackContext,Bundle bundle) {
        if (appName != null && appName.length() > 0) {
            PluginResult r = new PluginResult(PluginResult.Status.NO_RESULT);
            r.setKeepCallback(true);
            callbackContext.sendPluginResult(r);

            Intent intent = new Intent();
            intent.putExtras(bundle);
            intent.setComponent(new ComponentName(appName, appName+"."+activityName));
            this.cordova.startActivityForResult(this, intent, 1);
        } else {
            callbackContext.error("Expected one non-empty string argument.");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        this.callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.OK, resultCode));
    }
}