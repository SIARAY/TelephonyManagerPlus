package ir.siaray.telephonymanagerplussample;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
    private int slot1 = 1;
    private int slot2 = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
        //test
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
        //printDeviceInfoByLibrary();
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
            return;
        }
        deviceInfo = ""
                + "\n" + "getSimSerialNumber1: " + telephonyManagerPlus.getSimSerialNumber(slot1)
                + "\n" + "getSimSerialNumber2: " + telephonyManagerPlus.getSimSerialNumber(slot2)
                + "\n" + "getNetworkOperator1: " + telephonyManagerPlus.getNetworkOperator(slot1)
                + "\n" + "getNetworkOperator2: " + telephonyManagerPlus.getNetworkOperator(slot2)
                + "\n" + "getSimOperatorName1: " + telephonyManagerPlus.getSimOperatorName(slot1)
                + "\n" + "getSimOperatorName2: " + telephonyManagerPlus.getSimOperatorName(slot2)
                + "\n" + "getImei1: " + telephonyManagerPlus.getImei(slot1)
                + "\n" + "getImei2: " + telephonyManagerPlus.getImei(slot2)
                + "\n" + "getCellLocation1: " + telephonyManagerPlus.getCellLocation(slot1)
                + "\n" + "getCellLocation2: " + telephonyManagerPlus.getCellLocation(slot2);

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
            List<SubscriptionInfo> subsInfoList = subscriptionManager.getActiveSubscriptionInfoList();

            Log.i("Current list = " + subsInfoList);
            deviceInfo=deviceInfo+"\n-----------";
            int slotNumber=1;
            for (SubscriptionInfo subscriptionInfo : subsInfoList) {

                int simIndex = subscriptionInfo.getSimSlotIndex();
                String number = subscriptionInfo.getNumber();
                String name = (String)subscriptionInfo.getDisplayName();

                Log.i("slot: " + simIndex + " ::: number: " + number);
                deviceInfo = deviceInfo + "\nslot "+slotNumber+" index: " + simIndex
                        + " - number: " + number
                        + " - name: " + name;
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
                + "\n" + "getDeviceId: " + mTelephonyManager.getDeviceId()
                + "\n" + "getCellLocation: " + mTelephonyManager.getCellLocation()
                + "\n" + "getLine1Number: " + mTelephonyManager.getLine1Number()
                + "\n" + "getNetworkType: " + mTelephonyManager.getNetworkType()
                + "\n" + "getPhoneType: " + mTelephonyManager.getPhoneType();
        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            deviceInfo = deviceInfo
                    + "\n" + "getCellLocation: " + mTelephonyManager.getCellLocation();
        }*/

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            deviceInfo = deviceInfo
                    + "\n" + "getSimCarrierId: " + mTelephonyManager.getSimCarrierId();
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            deviceInfo = deviceInfo
                    + "\n" + "getMeid: " + mTelephonyManager.getMeid()
                    + "\n" + "getForbiddenPlmns: " + Arrays.toString(mTelephonyManager.getForbiddenPlmns())
                    + "\n" + "getImei: " + mTelephonyManager.getImei();
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            deviceInfo = deviceInfo
                    + "\n" + "getDataNetworkType: " + mTelephonyManager.getDataNetworkType();
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            deviceInfo = deviceInfo
                    + "\n" + "getPhoneCount: " + mTelephonyManager.getPhoneCount();
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            deviceInfo = deviceInfo
                    + "\n" + "getGroupIdLevel1: " + mTelephonyManager.getGroupIdLevel1();
        }

        tvOutput.setText(deviceInfo);

    }
}
