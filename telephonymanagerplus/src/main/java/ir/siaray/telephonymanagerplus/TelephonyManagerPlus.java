package ir.siaray.telephonymanagerplus;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

import static ir.siaray.telephonymanagerplus.Constants.DEFAULT_TELEPHONY_MANAGER_VALUE;
import static ir.siaray.telephonymanagerplus.Constants.TELEPHONY_MANAGER_CELL_LOCATION;
import static ir.siaray.telephonymanagerplus.Constants.TELEPHONY_MANAGER_IMEI;
import static ir.siaray.telephonymanagerplus.Constants.TELEPHONY_MANAGER_NETWORK_OPERATOR;
import static ir.siaray.telephonymanagerplus.Constants.TELEPHONY_MANAGER_SIM_OPERATOR_NAME;
import static ir.siaray.telephonymanagerplus.Constants.TELEPHONY_MANAGER_SIM_SERIAL_NUMBER;
import static ir.siaray.telephonymanagerplus.Constants.TELEPHONY_MANAGER_SUBSCRIBERID;
import static ir.siaray.telephonymanagerplus.Utils.getTelephonyManagerValues;

public class TelephonyManagerPlus {
    private TelephonyManager mTelephonyManager;
    private Context mContext;
    //private String simSerialNumber1;
    //private String simSerialNumber2;
    private int simSlot2 = 1;

    public TelephonyManagerPlus(Context context) {
        mContext = context;
        mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        findSimSlot2();
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

    private void findSimSlot2() {
        String simSerialNumber1 = getSimSerialNumber1();
        getTelephonyInfo(simSerialNumber1, TELEPHONY_MANAGER_SIM_SERIAL_NUMBER);
    }

    private boolean isPermissionGranted() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            Log.print("READ_PHONE_STATE permission is not granted");
            return false;
        }
        return true;
    }

    public static TelephonyManagerPlus getInstance(Context context) {
        return (new TelephonyManagerPlus(context));
    }

    private String getTelephonyInfo(String sim1Value, String methodName) {
        try {
            for (int i = 0; i < 10; i++) {
                String simSerialNumber2 = getTelephonyManagerValues(mContext
                        , mTelephonyManager
                        , methodName
                        , i);
                if (!TextUtils.isEmpty(simSerialNumber2)
                        && !simSerialNumber2.equals("0")
                        && !simSerialNumber2.equals("-1")) {
                    if (simSerialNumber2.length() > 1 &&
                            !simSerialNumber2.equals(sim1Value)) {
                        Log.i("sim2 slot: " + i + " serial: " + simSerialNumber2);
                        if (methodName.equals(TELEPHONY_MANAGER_SIM_SERIAL_NUMBER))
                            simSlot2 = i;
                        return simSerialNumber2;
                    }
                }
            }
        }catch (Exception e){
            Log.print("Error on getting telephonymanager info");
        }
        return DEFAULT_TELEPHONY_MANAGER_VALUE;
    }

    private String getCorrectSim2TelephonyInfo(String sim1Value, String methodName) {
        String telephonyManagerValue = getTelephonyManagerValues(mContext
                , mTelephonyManager
                , methodName
                , simSlot2);
        if (!TextUtils.isEmpty(telephonyManagerValue)
                && telephonyManagerValue.equals(sim1Value))
            return getTelephonyInfo(sim1Value, TELEPHONY_MANAGER_IMEI);
        else return telephonyManagerValue;
    }

    public String getSimSerialNumber1() {
        return (isPermissionGranted() && mTelephonyManager != null) ?
                mTelephonyManager.getSimSerialNumber() : DEFAULT_TELEPHONY_MANAGER_VALUE;
        /*return getTelephonyManagerValues(mContext
                , mTelephonyManager
                , TELEPHONY_MANAGER_SIM_SERIAL_NUMBER
                , slot);*/
    }

    public String getSimSerialNumber2() {
        return getTelephonyManagerValues(mContext
                , mTelephonyManager
                , TELEPHONY_MANAGER_SIM_SERIAL_NUMBER
                , simSlot2);
    }

    /*public String getSimSerialNumber1(int slot) {
        return getTelephonyManagerValues(mContext
                , mTelephonyManager
                , TELEPHONY_MANAGER_SIM_SERIAL_NUMBER
                , slot);
    }*/

    public String getNetworkOperator1() {
        return mTelephonyManager.getNetworkOperator();

    }

    public String getNetworkOperator2() {
        /*return getTelephonyManagerValues(mContext
                , mTelephonyManager
                , TELEPHONY_MANAGER_NETWORK_OPERATOR
                , simSlot2);*/
        return getCorrectSim2TelephonyInfo(getNetworkOperator1(), TELEPHONY_MANAGER_NETWORK_OPERATOR);

    }

    public String getSimOperatorName1() {
        return mTelephonyManager.getSimOperatorName();

    }

    public String getSimOperatorName2() {
        /*return getTelephonyManagerValues(mContext
                , mTelephonyManager
                , TELEPHONY_MANAGER_SIM_OPERATOR_NAME
                , simSlot2);*/
        return getCorrectSim2TelephonyInfo(getSimOperatorName1(), TELEPHONY_MANAGER_SIM_OPERATOR_NAME);
    }

    public String getImei1() {
        if ((isPermissionGranted() && mTelephonyManager != null)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return mTelephonyManager.getImei();
            }
            return mTelephonyManager.getDeviceId();
        } else {
            return DEFAULT_TELEPHONY_MANAGER_VALUE;
        }
    }


    public String getImei2() {
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return mTelephonyManager.getImei(1);
        }*/

        return getCorrectSim2TelephonyInfo(getImei1(), TELEPHONY_MANAGER_IMEI);
    }

    public String getCellLocation1() {
        return "" + mTelephonyManager.getCallState();

    }

    public String getCellLocation2() {
        return getCorrectSim2TelephonyInfo(getCellLocation1(), TELEPHONY_MANAGER_CELL_LOCATION);
    }

    public String getSubscriberId1() {
        return (isPermissionGranted() && mTelephonyManager != null) ?
                mTelephonyManager.getSubscriberId() : DEFAULT_TELEPHONY_MANAGER_VALUE;

    }

    public String getSubscriberId2() {
        return getCorrectSim2TelephonyInfo(getSubscriberId1(), TELEPHONY_MANAGER_SUBSCRIBERID);
    }
 /////////////////////////////////////////////////////
    public String getImei(int slot) {
        return getTelephonyManagerValues(mContext
                , mTelephonyManager
                , TELEPHONY_MANAGER_IMEI
                , slot);
    }

    public String getSubscriberId(int slot) {
        return getTelephonyManagerValues(mContext
                , mTelephonyManager
                , TELEPHONY_MANAGER_SUBSCRIBERID
                , slot);
    }


    public String getSimSerialNumber(int slot) {
        return getTelephonyManagerValues(mContext
                , mTelephonyManager
                , TELEPHONY_MANAGER_SIM_SERIAL_NUMBER
                , slot);
    }

}
