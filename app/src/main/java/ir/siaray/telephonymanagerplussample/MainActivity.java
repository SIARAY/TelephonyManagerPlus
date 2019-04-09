package ir.siaray.telephonymanagerplussample;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.CellLocation;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.widget.TextView;

import ir.siaray.telephonymanagerplus.TelephonyManagerPlus;

public class MainActivity extends AppCompatActivity {

    private TextView tvOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
        //test
    }

    private void initUi() {
        tvOutput = findViewById(R.id.tv_output);
        //printDeviceInfo();
        printDeviceInfoByLibrary();
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
                + "\n" + "getSimSerialNumber1: " + telephonyManagerPlus.getSimSerialNumber(1)
                + "\n" + "getSimSerialNumber2: " + telephonyManagerPlus.getSimSerialNumber(2)
                + "\n" + "getNetworkOperator1: " + telephonyManagerPlus.getNetworkOperator(1)
                + "\n" + "getNetworkOperator2: " + telephonyManagerPlus.getNetworkOperator(2)
                + "\n" + "getSimOperatorName1: " + telephonyManagerPlus.getSimOperatorName(1)
                + "\n" + "getSimOperatorName2: " + telephonyManagerPlus.getSimOperatorName(2)
                + "\n" + "getImei1: " + telephonyManagerPlus.getImei(1)
                + "\n" + "getImei1: " + telephonyManagerPlus.getImei(2)
                + "\n" + "getCellLocation1: " + telephonyManagerPlus.getCellLocation(1)
                + "\n" + "getCellLocation2: " + telephonyManagerPlus.getCellLocation(2);


        tvOutput.setText(deviceInfo);

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
                + "\n" + "getNetworkOperator: " + mTelephonyManager.getNetworkOperator()
                + "\n" + "getSubscriberId: " + mTelephonyManager.getSubscriberId()
                + "\n" + "getSimOperatorName: " + mTelephonyManager.getSimOperatorName()
                + "\n" + "getDeviceId: " + mTelephonyManager.getDeviceId()
                + "\n" + "getCellLocation: " + mTelephonyManager.getCellLocation()
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

        tvOutput.setText(deviceInfo);

    }
}
