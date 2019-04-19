package ir.siaray.telephonymanagerplussample;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;

import ir.siaray.telephonymanagerplus.Log;
import ir.siaray.telephonymanagerplus.TelephonyManagerPlus;

import static android.view.View.inflate;

public class MainActivity extends AppCompatActivity {

    private ViewGroup itemsContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
    }

    private void initUi() {
        itemsContainer = findViewById(R.id.items_container);
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
        //printDeviceInfo();
    }

    private void clearContainer() {
        if (itemsContainer != null
                && itemsContainer.getChildCount() > 0) {
            itemsContainer.removeAllViews();
        }
    }

    private void addItem(String label, String value) {
        View view = getLayoutInflater().inflate(R.layout.info_item, null);
        itemsContainer.addView(view);
        Log.i(itemsContainer.getChildCount()+" : "+label+" added: "+value);
        TextView tvLabel = view.findViewById(R.id.tv_label);
        TextView tvValue = view.findViewById(R.id.tv_output);
        tvLabel.setText(label);
        tvValue.setText(value);
    }

    private void printDeviceInfoByLibrary() {
        clearContainer();
        TelephonyManagerPlus telephonyManagerPlus = TelephonyManagerPlus.getInstance(this);
        String deviceInfo = "";
        if (!isPermissionGranted()) return;

        //View item = inflate(this, R.layout.info_item, null);
        addItem( "SimSerialNumber1:", telephonyManagerPlus.getSimSerialNumber1());
        addItem( "SimSerialNumber2:", telephonyManagerPlus.getSimSerialNumber2());
        addItem( "SimOperatorName1:", telephonyManagerPlus.getSimOperatorName1());
        addItem( "SimOperatorName2:", telephonyManagerPlus.getSimOperatorName2());
        addItem( "SimOperator1:", telephonyManagerPlus.getSimOperator1());
        addItem( "SimOperator2:", telephonyManagerPlus.getSimOperator2());
        addItem( "Imei1:", telephonyManagerPlus.getImei1());
        addItem( "Imei2:", telephonyManagerPlus.getImei2());
        addItem( "SubscriberId1:", telephonyManagerPlus.getSubscriberId1());
        addItem( "SubscriberId2:", telephonyManagerPlus.getSubscriberId2());
        addItem( "Mnc1:", ""+telephonyManagerPlus.getMnc1());
        addItem("getMnc2:", ""+telephonyManagerPlus.getMnc2());
        addItem("Mcc1:", ""+telephonyManagerPlus.getMcc1());
        addItem("Mcc1:", ""+telephonyManagerPlus.getMcc2());
        addItem("Lac1:", ""+telephonyManagerPlus.getLac1());
        addItem("Lac1:", ""+telephonyManagerPlus.getLac2());
        addItem("CellId1:", ""+telephonyManagerPlus.getCellId1());
        addItem("CellId2:", ""+telephonyManagerPlus.getCellId2());

        /*deviceInfo = ""
                + "\n" + "getSimSerialNumber1: " + telephonyManagerPlus.getSimSerialNumber1()
                + "\n" + "getSimSerialNumber2: " + telephonyManagerPlus.getSimSerialNumber2()
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
                + "\n" + "getMcc2: " + telephonyManagerPlus.getMcc2()
                + "\n" + "getLac1: " + telephonyManagerPlus.getLac1()
                + "\n" + "getLac2: " + telephonyManagerPlus.getLac2()
                + "\n" + "getCellId1: " + telephonyManagerPlus.getCellId1()
                + "\n" + "getCellId2: " + telephonyManagerPlus.getCellId2();

        tvOutput.setText(deviceInfo);*/
    }

    private boolean isPermissionGranted() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            //tvOutput.setText("Need to permission");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);

            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void printDeviceInfo() {
        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceInfo = "";
        if (!isPermissionGranted()) return;
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

        //tvOutput.setText(deviceInfo);
    }
}
