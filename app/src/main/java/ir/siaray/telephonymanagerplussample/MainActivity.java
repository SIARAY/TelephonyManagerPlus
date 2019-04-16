package ir.siaray.telephonymanagerplussample;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfo;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import ir.siaray.telephonymanagerplus.Log;
import ir.siaray.telephonymanagerplus.TelephonyManagerPlus;

public class MainActivity extends AppCompatActivity {

    private TextView tvOutput;
    private int slot1 = 0;
    private int slot2 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
    }

    private void initUi() {
        tvOutput = findViewById(R.id.tv_output);
        Button btnRefreshAndroid = findViewById(R.id.btn_refresh_android);
        Button btnRefreshLibrary = findViewById(R.id.btn_refresh_lib_info);
        btnRefreshLibrary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printDeviceInfoByLibrary();
            }
        });
        btnRefreshAndroid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printDeviceInfo();

            }
        });
        printDeviceInfo();
    }

    private void printDeviceInfoByLibrary() {
        TelephonyManagerPlus telephonyManagerPlus = TelephonyManagerPlus.getInstance(this);
        String deviceInfo = "";
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            tvOutput.setText("Need to permission");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);

            return;
        }
        deviceInfo = ""
                + "\n" + "getSimSerialNumber1: " + telephonyManagerPlus.getSimSerialNumber1()
                + "\n" + "getSimSerialNumber2: " + telephonyManagerPlus.getSimSerialNumber2()
                + "\n" + "getNetworkOperator1: " + telephonyManagerPlus.getNetworkOperator1()
                + "\n" + "getNetworkOperator2: " + telephonyManagerPlus.getNetworkOperator2()
                + "\n" + "getNetworkOperatorName1: " + telephonyManagerPlus.getNetworkOperatorName1()
                + "\n" + "getNetworkOperatorName2: " + telephonyManagerPlus.getNetworkOperatorName2()
                + "\n" + "getSimOperatorName1: " + telephonyManagerPlus.getSimOperatorName1()
                + "\n" + "getSimOperatorName2: " + telephonyManagerPlus.getSimOperatorName2()
                + "\n" + "getSimOperator1: " + telephonyManagerPlus.getSimOperator1()
                + "\n" + "getSimOperator2: " + telephonyManagerPlus.getSimOperator2()
                + "\n" + "getImei1: " + telephonyManagerPlus.getImei1()
                + "\n" + "getImei2: " + telephonyManagerPlus.getImei2()
                + "\n" + "getSubscriberId1: " + telephonyManagerPlus.getSubscriberId1()
                + "\n" + "getSubscriberId2: " + telephonyManagerPlus.getSubscriberId2();
        deviceInfo = deviceInfo + "\n----------------"
                + "\n" + "getMnc1: " + telephonyManagerPlus.getMnc1()
                + "\n" + "getMnc2: " + telephonyManagerPlus.getMnc2()
                + "\n" + "getMcc1: " + telephonyManagerPlus.getMcc1()
                + "\n" + "getMcc2: " + telephonyManagerPlus.getMcc1()
                + "\n" + "getLoc1: " + telephonyManagerPlus.getGSMLocationAreaCodeSlot1()
                + "\n" + "getLoc2: " + telephonyManagerPlus.getGSMLocationAreaCodeSlot2()
                + "\n" + "getCellId1: " + telephonyManagerPlus.getCellId1()
                + "\n" + "getCellId2: " + telephonyManagerPlus.getCellId2();

        //+ "\n" + "getCellLocation1: " + telephonyManagerPlus.getCellLocation1()
        //+ "\n" + "getCellLocation2: " + telephonyManagerPlus.getCellLocation2();
        /*for (int i = 0; i < 5; i++) {
            Log.i("print123 serial -------------");
            Log.i("print123 serial -------------");
            Log.i("print123 serial -------------");
            Log.i("print123 serial " + i + " ::: " + telephonyManagerPlus.getSimSerialNumber(i));
            Log.i("print123 simOperator "+i+" ::: "+ telephonyManagerPlus.getSimOperator(i));
            Log.i("print123 operatorName "+i+" ::: "+ telephonyManagerPlus.getSimOperatorName(i));
            Log.i("print123 operatorCode "+i+" ::: "+ telephonyManagerPlus.getNetworkOperator(i));
            Log.i("print123 imei " + i + " ::: " + telephonyManagerPlus.getImei(i));
            Log.i("print123 getSubscriberId " + i + " ::: " + telephonyManagerPlus.getSubscriberId(i));
            Log.i("print123 getCellLocation " + i + " ::: " + telephonyManagerPlus.getCellLocation(i));
            //Log.i("print123 getCellLocation "+i+" ::: "+ telephonyManagerPlus.getCellLocation(i));
        }*/
        deviceInfo = getSimSlotIndex(deviceInfo);
        tvOutput.setText(deviceInfo);

    }

    private String getSimSlotIndex(String deviceInfo) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
            SubscriptionManager subscriptionManager = SubscriptionManager.from(getApplicationContext());
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return "";
            }
            try {
                Log.i("getActiveSubscriptionInfoCount : " + subscriptionManager.getActiveSubscriptionInfoCount());
                List<SubscriptionInfo> subsInfoList = subscriptionManager.getActiveSubscriptionInfoList();
                if (subsInfoList != null) {
                    //Log.i("Current list = " + subsInfoList);
                    deviceInfo = deviceInfo + "\n-----------";
                    int slotNumber = 1;
                    for (SubscriptionInfo subscriptionInfo : subsInfoList) {

                        int simIndex = subscriptionInfo.getSimSlotIndex();
                        String number = subscriptionInfo.getNumber();
                        String name = (String) subscriptionInfo.getDisplayName();
                        String carrier = (String) subscriptionInfo.getCarrierName();
                        String countryIso = (String) subscriptionInfo.getCountryIso();
                        String iccId = (String) subscriptionInfo.getIccId();
                        int mnc = subscriptionInfo.getMnc();
                        int mcc = subscriptionInfo.getMcc();

                        //Log.i("slot: " + simIndex + " ::: number: " + number);
                        deviceInfo = deviceInfo + "\nslot " + slotNumber + " index: " + simIndex
                                + " - number: " + number
                                + " - carrier: " + carrier
                                + " - countryIso: " + countryIso;
                        deviceInfo = deviceInfo + "\nslot " + slotNumber
                                + " - iccId: " + iccId
                                + " - mnc: " + mnc
                                + " - mcc: " + mcc
                                + " - name: " + name;
                        slotNumber++;
                    }
                } else {
                    deviceInfo = deviceInfo + "\nSubscriptionInfo is null ";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return deviceInfo;
    }

    private void printDeviceInfo() {
        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceInfo = "";
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            tvOutput.setText("Need to permission");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
            return;
        }
        deviceInfo = ""
                + "\n" + "getCallState: " + mTelephonyManager.getCallState()
                + "\n" + "getDataActivity: " + mTelephonyManager.getDataActivity()
                + "\n" + "getDataState: " + mTelephonyManager.getDataState()
                + "\n" + "getLine1Number: " + mTelephonyManager.getLine1Number()
                + "\n" + "getSimSerialNumber: " + mTelephonyManager.getSimSerialNumber()
                + "\n" + "getDeviceSoftwareVersion: " + mTelephonyManager.getDeviceSoftwareVersion()
                + "\n" + "getNetworkCountryIso: " + mTelephonyManager.getNetworkCountryIso()
                + "\n" + "getSimOperator: " + mTelephonyManager.getSimOperator()
                + "\n" + "getNetworkOperator: " + mTelephonyManager.getNetworkOperator()
                + "\n" + "getSubscriberId: " + mTelephonyManager.getSubscriberId()
                + "\n" + "getSimOperatorName: " + mTelephonyManager.getSimOperatorName()
                + "\n" + "getNetworkOperatorName: " + mTelephonyManager.getNetworkOperatorName()
                + "\n" + "getDeviceId1: " + mTelephonyManager.getDeviceId()
                + "\n" + "getLine1Number: " + mTelephonyManager.getLine1Number()
                + "\n" + "getNetworkType: " + mTelephonyManager.getNetworkType()
                + "\n" + "getPhoneType: " + mTelephonyManager.getPhoneType();
        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            deviceInfo = deviceInfo
                    + "\n" + "getCellLocation: " + mTelephonyManager.getCellLocation();
        }*/
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            deviceInfo = deviceInfo
                    + "\n" + "getCellLocation: " + mTelephonyManager.getCellLocation();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            deviceInfo = deviceInfo
                    + "\n" + "getSimCarrierId: " + mTelephonyManager.getSimCarrierId();
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            deviceInfo = deviceInfo
                    + "\n" + "getMeid: " + mTelephonyManager.getMeid()
                    + "\n" + "getForbiddenPlmns: " + Arrays.toString(mTelephonyManager.getForbiddenPlmns())
                    + "\n" + "getImei1: " + mTelephonyManager.getImei()
                    + "\n" + "getImei2: " + mTelephonyManager.getImei(1);
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            deviceInfo = deviceInfo
                    + "\n" + "getDataNetworkType: " + mTelephonyManager.getDataNetworkType();
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            deviceInfo = deviceInfo
                    + "\n" + "getDeviceId2: " + mTelephonyManager.getDeviceId(1)
                    + "\n" + "getPhoneCount: " + mTelephonyManager.getPhoneCount();
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            deviceInfo = deviceInfo
                    + "\n" + "getGroupIdLevel1: " + mTelephonyManager.getGroupIdLevel1();
        }

        tvOutput.setText(deviceInfo);
    }
}
