package ir.siaray.telephonymanagerplus;

import android.content.Context;
import android.telephony.TelephonyManager;

public class TelephonyManagerPlus {
    private TelephonyManager mTelephonyManager;

    public TelephonyManagerPlus(Context context) {
        mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

/*        mTelephonyManager.getSimCarrierId();
        mTelephonyManager.getImei();
        mTelephonyManager.getSimSerialNumber();
        mTelephonyManager.getDeviceSoftwareVersion();
        mTelephonyManager.getCallState();
        mTelephonyManager.getDataActivity();
        mTelephonyManager.getDataNetworkType();
        mTelephonyManager.getDataState();
        mTelephonyManager.getGroupIdLevel1();
        mTelephonyManager.getLine1Number();
        mTelephonyManager.getMeid();
        mTelephonyManager.getMmsUAProfUrl();
        mTelephonyManager.getMmsUserAgent();
        mTelephonyManager.getNai();
        mTelephonyManager.getNetworkCountryIso();
        mTelephonyManager.getNetworkOperator();
        mTelephonyManager.getPhoneCount();
        mTelephonyManager.getPhoneType();
        mTelephonyManager.getSubscriberId();
        mTelephonyManager.getSimOperatorName();
        mTelephonyManager.getDeviceId();*/


    }

    public static TelephonyManagerPlus getInstance(Context context){
        return (new TelephonyManagerPlus(context));
    }
}
