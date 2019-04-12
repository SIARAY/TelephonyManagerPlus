package ir.siaray.telephonymanagerplus;

import android.content.Context;
import android.telephony.TelephonyManager;

import static ir.siaray.telephonymanagerplus.Constants.TELEPHONY_MANAGER_CELL_LOCATION;
import static ir.siaray.telephonymanagerplus.Constants.TELEPHONY_MANAGER_IMEI;
import static ir.siaray.telephonymanagerplus.Constants.TELEPHONY_MANAGER_NETWORK_OPERATOR;
import static ir.siaray.telephonymanagerplus.Constants.TELEPHONY_MANAGER_SIM_OPERATOR_NAME;
import static ir.siaray.telephonymanagerplus.Constants.TELEPHONY_MANAGER_SIM_SERIAL_NUMBER;
import static ir.siaray.telephonymanagerplus.Utils.getTelephonyManagerValues;

public class TelephonyManagerPlus {
    private TelephonyManager mTelephonyManager;
    private Context mContext;

    public TelephonyManagerPlus(Context context) {
        mContext = context;
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

    public static TelephonyManagerPlus getInstance(Context context) {
        return (new TelephonyManagerPlus(context));
    }

    public String getSimSerialNumber(int slot) {
        return getTelephonyManagerValues(mContext
                , mTelephonyManager
                , TELEPHONY_MANAGER_SIM_SERIAL_NUMBER
                , slot);
    }
    public String getNetworkOperator(int slot) {
        return getTelephonyManagerValues(mContext
                , mTelephonyManager
                , TELEPHONY_MANAGER_NETWORK_OPERATOR
                , slot);
    }
    public String getSimOperatorName(int slot) {
        return getTelephonyManagerValues(mContext
                , mTelephonyManager
                , TELEPHONY_MANAGER_SIM_OPERATOR_NAME
                , slot);
    }
    public String getImei(int slot) {
        return getTelephonyManagerValues(mContext
                , mTelephonyManager
                , TELEPHONY_MANAGER_IMEI
                , slot);
    }
    public String getCellLocation(int slot) {
        return getTelephonyManagerValues(mContext
                , mTelephonyManager
                , TELEPHONY_MANAGER_CELL_LOCATION
                , slot);
    }

}
