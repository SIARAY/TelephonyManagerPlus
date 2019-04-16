package ir.siaray.telephonymanagerplus;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.CellLocation;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;

import java.util.List;

import static ir.siaray.telephonymanagerplus.Constants.DEFAULT_TELEPHONY_MANAGER_INT_VALUE;
import static ir.siaray.telephonymanagerplus.Constants.DEFAULT_TELEPHONY_MANAGER_STRING_VALUE;
import static ir.siaray.telephonymanagerplus.Constants.TELEPHONY_MANAGER_CELL_LOCATION;
import static ir.siaray.telephonymanagerplus.Constants.TELEPHONY_MANAGER_CELL_LOCATION_BY_SUB_ID;
import static ir.siaray.telephonymanagerplus.Constants.TELEPHONY_MANAGER_IMEI;
import static ir.siaray.telephonymanagerplus.Constants.TELEPHONY_MANAGER_NETWORK_OPERATOR;
import static ir.siaray.telephonymanagerplus.Constants.TELEPHONY_MANAGER_NETWORK_OPERATOR_NAME;
import static ir.siaray.telephonymanagerplus.Constants.TELEPHONY_MANAGER_SIM_OPERATOR;
import static ir.siaray.telephonymanagerplus.Constants.TELEPHONY_MANAGER_SIM_OPERATOR_NAME;
import static ir.siaray.telephonymanagerplus.Constants.TELEPHONY_MANAGER_SIM_SERIAL_NUMBER;
import static ir.siaray.telephonymanagerplus.Constants.TELEPHONY_MANAGER_SUBSCRIBERID;
import static ir.siaray.telephonymanagerplus.Utils.getCellLocationValue;
import static ir.siaray.telephonymanagerplus.Utils.getTelephonyManagerValues;

public class TelephonyManagerPlus {
    private TelephonyManager mTelephonyManager;
    private Context mContext;
    //private String simSerialNumber1;
    //private String simSerialNumber2;
    private int simSlot2 = 1;
    List<SubscriptionInfo> mSubscriptionInfoList;

    public TelephonyManagerPlus(Context context) {
        mContext = context;
        mTelephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        mSubscriptionInfoList = getSubscriptionManager();
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

    private boolean isPhoneStatePermissionGranted() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            Log.print("READ_PHONE_STATE permission is not granted");
            return false;
        }
        return true;
    }

    private boolean isLocationPermissionGranted() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.print("ACCESS_COARSE_LOCATION permission is not granted");
            return false;
        }
        return true;
    }

    public static TelephonyManagerPlus getInstance(Context context) {
        return (new TelephonyManagerPlus(context));
    }

    private String getTelephonyInfo(String sim1Value, String methodName) {
        try {
            for (int i = 0; i < 7; i++) {
                String simValue2 = getTelephonyManagerValues(mContext
                        , mTelephonyManager
                        , methodName
                        , i);

                if (!TextUtils.isEmpty(simValue2)
                        && !simValue2.equals("0")
                        && !simValue2.equals("-1")) {
                    if (simValue2.length() > 0 &&
                            !simValue2.equals(sim1Value)) {
                        if (methodName.equals(TELEPHONY_MANAGER_SIM_SERIAL_NUMBER))
                            simSlot2 = i;
                        return simValue2;
                    }
                }
            }
        } catch (Exception e) {
            Log.print("Error on getting telephonymanager info");
        }
        return DEFAULT_TELEPHONY_MANAGER_STRING_VALUE;
    }

    private String getCorrectSim2TelephonyInfo(String sim1Value, String methodName) {
        String telephonyManagerValue = getTelephonyManagerValues(mContext
                , mTelephonyManager
                , methodName
                , simSlot2);
        if (methodName.equals(TELEPHONY_MANAGER_CELL_LOCATION)) {
            Log.i("getCellLocation2 2: " + telephonyManagerValue);
        }
        if (!TextUtils.isEmpty(telephonyManagerValue)
                && telephonyManagerValue.equals(sim1Value))
            return getTelephonyInfo(sim1Value, TELEPHONY_MANAGER_IMEI);
        else return telephonyManagerValue;
    }

    public String getSimSerialNumber1() {
        if (isPhoneStatePermissionGranted() && mTelephonyManager != null) {
            return mTelephonyManager.getSimSerialNumber();
        }
        return DEFAULT_TELEPHONY_MANAGER_STRING_VALUE;
    }

    public String getSimSerialNumber2() {
        if (mSubscriptionInfoList != null
                && mSubscriptionInfoList.size() > 1) {
            return mSubscriptionInfoList.get(1).getIccId();
        }
        return getCorrectSim2TelephonyInfo(getSimSerialNumber1(), TELEPHONY_MANAGER_SIM_SERIAL_NUMBER);
    }

    public String getNetworkOperator1() {
        return mTelephonyManager.getNetworkOperator();

    }

    public String getNetworkOperator2() {
        return getCorrectSim2TelephonyInfo(getNetworkOperator1(), TELEPHONY_MANAGER_NETWORK_OPERATOR);
    }

    public String getNetworkOperatorName1() {
        return mTelephonyManager.getNetworkOperatorName();

    }

    public String getNetworkOperatorName2() {
        if (mSubscriptionInfoList != null
                && mSubscriptionInfoList.size() > 1) {
            return String.valueOf(mSubscriptionInfoList.get(1).getDisplayName());
        }
        return getCorrectSim2TelephonyInfo(getNetworkOperator1(), TELEPHONY_MANAGER_NETWORK_OPERATOR_NAME);
    }

    public String getSimOperator1() {
        return mTelephonyManager.getSimOperator();

    }

    public String getSimOperator2() {
        return getCorrectSim2TelephonyInfo(getNetworkOperator1(), TELEPHONY_MANAGER_SIM_OPERATOR);
    }

    public String getSimOperatorName1() {
        return mTelephonyManager.getSimOperatorName();

    }

    public String getSimOperatorName2() {
        if (mSubscriptionInfoList != null
                && mSubscriptionInfoList.size() > 1) {
            return String.valueOf(mSubscriptionInfoList.get(1).getCarrierName());
        }
        return getCorrectSim2TelephonyInfo(getSimOperatorName1(), TELEPHONY_MANAGER_SIM_OPERATOR_NAME);
    }

    public String getImei1() {
        if ((isPhoneStatePermissionGranted() && mTelephonyManager != null)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return mTelephonyManager.getImei();
            }
            return mTelephonyManager.getDeviceId();
        } else {
            return DEFAULT_TELEPHONY_MANAGER_STRING_VALUE;
        }
    }


    public String getImei2() {
        if (isPhoneStatePermissionGranted()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return mTelephonyManager.getImei(1);
            } else if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return mTelephonyManager.getDeviceId(1);
            }
            return getCorrectSim2TelephonyInfo(getImei1(), TELEPHONY_MANAGER_IMEI);
        }
        return DEFAULT_TELEPHONY_MANAGER_STRING_VALUE;
    }

    private CellLocation getCellLocation1() {
        if (isLocationPermissionGranted()
                && mTelephonyManager != null)
            return mTelephonyManager.getCellLocation();
        return null;
    }


    private String getCellLocation1ToString() {
        if (isLocationPermissionGranted()
                && mTelephonyManager != null)
            return "" + getCellLocation1();
        return DEFAULT_TELEPHONY_MANAGER_STRING_VALUE;
    }

    private String getCellLocation2() {
        Log.i("getCellLocation2");
        if (isLocationPermissionGranted()) {
            String cellLocation1 = getTelephonyManagerValues(mContext
                    , mTelephonyManager
                    , TELEPHONY_MANAGER_CELL_LOCATION
                    , 0);
            if (TextUtils.isEmpty(cellLocation1))
                return getCorrectSim2TelephonyInfo(getCellLocation1ToString(), TELEPHONY_MANAGER_CELL_LOCATION_BY_SUB_ID);
            else
                return getCorrectSim2TelephonyInfo(getCellLocation1ToString(), TELEPHONY_MANAGER_CELL_LOCATION);
        }
        return DEFAULT_TELEPHONY_MANAGER_STRING_VALUE;
    }

    public int getCellId1() {
        CellLocation cellLocation1 = getCellLocation1();
        if(cellLocation1!=null){
            return ((GsmCellLocation)cellLocation1).getCid();
        }
        return DEFAULT_TELEPHONY_MANAGER_INT_VALUE;

    }


    public int getCellId2() {
        return getCellLocationValue(mContext, getCellLocation2(), Utils.CellLocationType.CID);
    }

    public int getGSMLocationAreaCodeSlot1() {
        CellLocation cellLocation1 = getCellLocation1();
        if(cellLocation1!=null){
            return ((GsmCellLocation)cellLocation1).getLac();
        }
        return DEFAULT_TELEPHONY_MANAGER_INT_VALUE;
    }

    public int getGSMLocationAreaCodeSlot2() {
        return getCellLocationValue(mContext,getCellLocation2(), Utils.CellLocationType.LAC);
    }

    public int getMnc1() {
        if (mSubscriptionInfoList != null
                && mSubscriptionInfoList.size() > 0) {
            return mSubscriptionInfoList.get(0).getMnc();
        }
        return Utils.getMncFromNetworkOperator(getNetworkOperator1());
    }

    public int getMnc2() {
        if (mSubscriptionInfoList != null
                && mSubscriptionInfoList.size() > 1) {
            return mSubscriptionInfoList.get(1).getMnc();
        }
        return Utils.getMncFromNetworkOperator(getCorrectSim2TelephonyInfo(getSimSerialNumber1(), TELEPHONY_MANAGER_SIM_OPERATOR));
    }


    public int getMcc1() {
        if (mSubscriptionInfoList != null
                && mSubscriptionInfoList.size() > 0) {
            return mSubscriptionInfoList.get(0).getMcc();
        }
        return Utils.getMccFromNetworkOperator(getSimOperator1());
    }

    public int getMcc2() {
        if (mSubscriptionInfoList != null
                && mSubscriptionInfoList.size() > 1) {
            return mSubscriptionInfoList.get(1).getMcc();
        }
        return Utils.getMccFromNetworkOperator(getCorrectSim2TelephonyInfo(getSimSerialNumber1(), TELEPHONY_MANAGER_NETWORK_OPERATOR));
    }

    public String getSubscriberId1() {
        return (isPhoneStatePermissionGranted()
                && mTelephonyManager != null) ?
                mTelephonyManager.getSubscriberId() : DEFAULT_TELEPHONY_MANAGER_STRING_VALUE;

    }

    public String getSubscriberId2() {
        return getCorrectSim2TelephonyInfo(getSubscriberId1(), TELEPHONY_MANAGER_SUBSCRIBERID);
    }

    /////////////////////////////////////////////////////
    /*public String getNetworkOperator(int slot) {
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

    public String getSimOperator(int slot) {
        return getTelephonyManagerValues(mContext
                , mTelephonyManager
                , TELEPHONY_MANAGER_SIM_OPERATOR
                , slot);
    }

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


    public String getCellLocation(int slot) {
        return getTelephonyManagerValues(mContext
                , mTelephonyManager
                , TELEPHONY_MANAGER_CELL_LOCATION
                , slot);
    }


    public String getSimSerialNumber(int slot) {
        return getTelephonyManagerValues(mContext
                , mTelephonyManager
                , TELEPHONY_MANAGER_SIM_SERIAL_NUMBER
                , slot);
    }*/


    private List<SubscriptionInfo> getSubscriptionManager() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            SubscriptionManager subscriptionManager = SubscriptionManager.from(mContext);
            try {
                if (!isPhoneStatePermissionGranted()) {
                    return null;
                }
                return subscriptionManager != null ? subscriptionManager.getActiveSubscriptionInfoList() : null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Log.i("SubscriptionManager is null");
        return null;
    }

}
