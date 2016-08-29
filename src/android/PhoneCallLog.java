package org.apache.cordova.phonecalllog;

import android.database.Cursor;
import android.provider.CallLog;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class PhoneCallLog extends CordovaPlugin {
    /**
     * Constructor.
     */
    public PhoneCallLog() {
    }

    /**
     * Sets the context of the Command. This can then be used to do things like
     * get file paths associated with the Activity.
     *
     * @param cordova The context of the main Activity.
     * @param webView The CordovaWebView Cordova is running in.
     */
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action            The action to execute.
     * @param args              JSONArry of arguments for the plugin.
     * @param callbackContext   The callback id used when calling back into JavaScript.
     * @return                  True if the action was valid, false if not.
     */
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if ("PhoneCallLog".equals(action)) {
            JSONObject r = new JSONObject();
            r.put("PhoneCallLog", this.getCallDetails());
            callbackContext.success(r);
        }
        else {
            return false;
        }
        return true;
    }

    public JSONArray getCallDetails() {
        StringBuffer sb = new StringBuffer();

        Cursor managedCursor = this.cordova.getActivity().managedQuery(CallLog.Calls.CONTENT_URI, null, null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        sb.append("Call Log :");

        JSONArray jsonarray = new JSONArray();

        while (managedCursor.moveToNext()) {
            JSONObject r = new JSONObject();

            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = managedCursor.getString(duration);

            try{
                r.put("phNumber", phNumber);
                r.put("callType", callType);
                r.put("callDate",  callDate);
                r.put("callDayTime", callDayTime);
                r.put("callDuration", callDuration);

                String dir = null;

                int dircode = Integer.parseInt(callType);
                switch (dircode) {
                    case CallLog.Calls.OUTGOING_TYPE:dir = "OUTGOING";
                        break;
                    case CallLog.Calls.INCOMING_TYPE:dir = "INCOMING";
                        break;
                    case CallLog.Calls.MISSED_TYPE: dir = "MISSED";
                        break;
                }
                r.put("Direction", dir);

            }catch(JSONException e){
                e.printStackTrace();
            }
            jsonarray.put(r);

            //sb.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- " + dir + " \nCall Date:--- " + callDayTime + " \nCall duration in sec :--- " + callDuration);
            //sb.append("\n----------------------------------");
        } //managedCursor.close();
        //textView.setText(sb);
        return jsonarray;
    }
}

