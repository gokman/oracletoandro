package com.example.oracletoandro;
 
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
 
public class NetworkUtil {
     
    public static double TYPE_WIFI = 1;
    public static double TYPE_MOBILE = 2;
    public static double TYPE_MOBILE_GPRS = 2.1;
    public static double TYPE_MOBILE_EDGE = 2.2;
    //bize alttaki üç baðlantý da yeter o yüzden ayný puan yeterli
    public static double TYPE_MOBILE_3G = 2.3;
    public static double TYPE_MOBILE_HSUPA = 2.3;
    public static double TYPE_MOBILE_UMTS = 2.3;
    public static double TYPE_NOT_CONNECTED = 0;
     
     
    public static double getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
 
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;
             
            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
            	if(activeNetwork.getSubtype()==TelephonyManager.NETWORK_TYPE_CDMA){
            		return TYPE_MOBILE_3G;
            	}else if(activeNetwork.getSubtype()==TelephonyManager.NETWORK_TYPE_EDGE){
            		return TYPE_MOBILE_EDGE;
            	}else if(activeNetwork.getSubtype()==TelephonyManager.NETWORK_TYPE_GPRS){
            		return TYPE_MOBILE_GPRS;
            	}else if(activeNetwork.getSubtype()==TelephonyManager.NETWORK_TYPE_HSUPA){
            		return TYPE_MOBILE_HSUPA;
            	}else if(activeNetwork.getSubtype()==TelephonyManager.NETWORK_TYPE_UMTS){
            		return TYPE_MOBILE_UMTS;
            	}
                
        }
        return TYPE_NOT_CONNECTED;
    }
     
    public static String getConnectivityStatusString(Context context) {
        double conn = NetworkUtil.getConnectivityStatus(context);
        String status = null;
        if (conn == NetworkUtil.TYPE_WIFI) {
            status = "Wifi enabled";
        } else if (conn == NetworkUtil.TYPE_MOBILE_3G) {
            status = "3G data enabled";
        } else if (conn == NetworkUtil.TYPE_MOBILE_EDGE) {
            status = "Edge data enabled";
        } else if (conn == NetworkUtil.TYPE_MOBILE_GPRS) {
            status = "gprs data enabled";
        } else if (conn == NetworkUtil.TYPE_MOBILE_HSUPA){
            status =  "HSUPA (3G) Speed: 1-23 Mbps"; 
        } else if (conn == NetworkUtil.TYPE_MOBILE_UMTS ) {
		    status = "UMTS (3G) Speed: 0.4-7 Mbps";
		} else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
            status = "Not connected to Internet";
        }
        return status;
    }
}
