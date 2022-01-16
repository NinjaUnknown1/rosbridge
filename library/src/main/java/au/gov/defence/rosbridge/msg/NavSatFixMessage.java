package au.gov.defence.rosbridge.msg;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class NavSatFixMessage extends Message {

    private static final String TAG = "ROSBridge_NavSatFix";
    private Header mHeader;
    private float mLatitude, mLongitude, mAltitude;

    public NavSatFixMessage() {
        this(new Header(), 0, 0, 0);
    }

    public NavSatFixMessage(Header inHeader, float inLat, float inLon, float inAlt) {
        if (inHeader == null)
            mHeader = new Header();
        else
            mHeader = inHeader;

        mLatitude = inLat;
        mLongitude = inLon;
        mAltitude = inAlt;
    }

    @Override
    public JSONObject getJSON() {
        try {
            JSONObject header = mHeader.getJSON();
            JSONObject navMessage = new JSONObject();
            navMessage.put("header", header);
            navMessage.put("latitude", mLatitude);
            navMessage.put("longitude", mLongitude);
            navMessage.put("altitude", mAltitude);

            return navMessage;
        }
        catch (JSONException exception) {
            exception.printStackTrace();
            Log.e(TAG, "NAVSATFIX : ERROR IN JSON MESSAGE");
        }
        return null;
    }

    @Override
    public Message updateMessage(JSONObject inJSONObject) {
        try {
            JSONObject msg = inJSONObject.getJSONObject("msg");

            mLatitude = Float.parseFloat(msg.getString("latitude"));
            mLongitude = Float.parseFloat(msg.getString("longitude"));
            mAltitude = Float.parseFloat(msg.getString("altitude"));
        }
        catch(JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "NAVSATFIX : ERROR IN JSON MESSAGE");
        }
        return this;
    }

}
