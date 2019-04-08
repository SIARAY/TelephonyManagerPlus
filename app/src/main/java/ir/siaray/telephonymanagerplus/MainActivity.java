package ir.siaray.telephonymanagerplus;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
    }

    private void initUi() {
        tvOutput = findViewById(R.id.tv_output);
        printDeviceInfo();
    }

    private void printDeviceInfo() {
        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceInfo = null;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {

            deviceInfo = ""
                    + "\n" + "getSimCarrierId: " + mTelephonyManager.getSimCarrierId()
                    + "\n" + "getImei: " + mTelephonyManager.getImei()
                    + "\n" + "getSimSerialNumber: " + mTelephonyManager.getSimSerialNumber()
                    + "\n" + "getDeviceSoftwareVersion: " + mTelephonyManager.getDeviceSoftwareVersion()
                    + "\n" + "getCallState: " + mTelephonyManager.getCallState()
                    + "\n" + "getDataActivity: " + mTelephonyManager.getDataActivity()
                    + "\n" + "getDataNetworkType: " + mTelephonyManager.getDataNetworkType()
                    + "\n" + "getDataState: " + mTelephonyManager.getDataState()
                    + "\n" + "getGroupIdLevel1: " + mTelephonyManager.getGroupIdLevel1()
                    + "\n" + "getLine1Number: " + mTelephonyManager.getLine1Number()
                    + "\n" + "getMeid: " + mTelephonyManager.getMeid()
                    + "\n" + "getMmsUAProfUrl: " + mTelephonyManager.getMmsUAProfUrl()
                    + "\n" + "getMmsUserAgent: " + mTelephonyManager.getMmsUserAgent()
                    + "\n" + "getNai: " + mTelephonyManager.getNai()
                    + "\n" + "getNetworkCountryIso: " + mTelephonyManager.getNetworkCountryIso()
                    + "\n" + "getNetworkOperator: " + mTelephonyManager.getNetworkOperator()
                    + "\n" + "getPhoneCount: " + mTelephonyManager.getPhoneCount()
                    + "\n" + "getPhoneType: " + mTelephonyManager.getPhoneType()
                    + "\n" + "getSubscriberId: " + mTelephonyManager.getSubscriberId()
                    + "\n" + "getSimOperatorName: " + mTelephonyManager.getSimOperatorName()
                    + "\n" + "getDeviceId: " + mTelephonyManager.getDeviceId();
        }
        tvOutput.setText(deviceInfo);

    }
}
