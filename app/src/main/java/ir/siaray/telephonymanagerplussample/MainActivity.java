package ir.siaray.telephonymanagerplussample;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

import ir.siaray.telephonymanagerplus.Log;
import ir.siaray.telephonymanagerplus.TelephonyManagerPlus;

import static android.Manifest.permission.READ_PHONE_STATE;
import static android.view.View.inflate;

public class MainActivity extends AppCompatActivity {

    private ViewGroup itemsContainer;
    private Button btnRefreshAndroid;
    private Button btnRefreshLibrary;
    private boolean libraryButtonClicked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
    }

    private void initUi() {
        itemsContainer = findViewById(R.id.items_container);
        btnRefreshAndroid = findViewById(R.id.btn_refresh_android);
        btnRefreshLibrary = findViewById(R.id.btn_refresh_lib_info);
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
        printDeviceInfoByLibrary();
    }

    private void printDeviceInfoByLibrary() {
        btnRefreshLibrary.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.selectedButton));
        btnRefreshAndroid.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        clearContainer();
        TelephonyManagerPlus telephonyManagerPlus = TelephonyManagerPlus.getInstance(this);
        //if (!isReadPhoneStatePermissionGranted()) return;
        addItem("SimOperatorName1:", telephonyManagerPlus.getSimOperatorName1());
        addItem("SimOperatorName2:", telephonyManagerPlus.getSimOperatorName2());
        addItem("SimOperator1:", telephonyManagerPlus.getSimOperator1());
        addItem("SimOperator2:", telephonyManagerPlus.getSimOperator2());
        addItem("Mnc1:", telephonyManagerPlus.getMnc1());
        addItem("Mnc2:", telephonyManagerPlus.getMnc2());
        addItem("Mcc1:", telephonyManagerPlus.getMcc1());
        addItem("Mcc2:", telephonyManagerPlus.getMcc2());
        addItem("Permission: READ_PHONE_STATE", "label");
        addItem("SimSerialNumber1:", telephonyManagerPlus.getSimSerialNumber1());
        addItem("SimSerialNumber2:", telephonyManagerPlus.getSimSerialNumber2());
        addItem("Imei1:", telephonyManagerPlus.getImei1());
        addItem("Imei2:", telephonyManagerPlus.getImei2());
        addItem("SubscriberId1:", telephonyManagerPlus.getSubscriberId1());
        addItem("SubscriberId2:", telephonyManagerPlus.getSubscriberId2());
        addItem("Permission: ACCESS_COARSE_LOCATION", "label");
        addItem("Lac1:", telephonyManagerPlus.getLac1());
        addItem("Lac2:", telephonyManagerPlus.getLac2());
        addItem("Cid1:", telephonyManagerPlus.getCid1());
        addItem("Cid2:", telephonyManagerPlus.getCid2());
    }

    private boolean isReadPhoneStatePermissionGranted() {
        if (ActivityCompat.checkSelfPermission(this
                , READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{READ_PHONE_STATE}, 1);
            return false;
        }
        return true;
    }

    private boolean isLocationPermissionGranted() {
        if (ActivityCompat.checkSelfPermission(this
                , Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this
                , Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
        return false;
    }

   /* @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (libraryButtonClicked)
                        printDeviceInfoByLibrary();
                    else printDeviceInfo();

                } else {
                    Toast.makeText(this, "Need to READ_PHONE_STATE permission", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            case 2: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (libraryButtonClicked)
                        printDeviceInfoByLibrary();
                    else printDeviceInfo();

                } else {
                    Toast.makeText(this, "Need to Location permission", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }*/

    private void printDeviceInfo() {
        btnRefreshAndroid.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.selectedButton));
        btnRefreshLibrary.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        clearContainer();
        TelephonyManager mTelephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (mTelephonyManager == null) return;
        addItem("CallState: ", mTelephonyManager.getCallState());
        addItem("DataActivity: ", mTelephonyManager.getDataActivity());
        addItem("DataState: ", mTelephonyManager.getDataState());
        addItem("NetworkCountryIso: ", mTelephonyManager.getNetworkCountryIso());
        addItem("SimOperator: ", mTelephonyManager.getSimOperator());
        addItem("NetworkOperator: ", mTelephonyManager.getNetworkOperator());
        addItem("SimOperatorName: ", mTelephonyManager.getSimOperatorName());
        addItem("NetworkOperatorName: ", mTelephonyManager.getNetworkOperatorName());
        addItem("NetworkType: ", mTelephonyManager.getNetworkType());


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            addItem("SimCarrierId: ", mTelephonyManager.getSimCarrierId());
        }
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            addItem("PhoneCount: ", mTelephonyManager.getPhoneCount());
        }
        addItem("PhoneType: ", mTelephonyManager.getPhoneType());

        if (isReadPhoneStatePermissionGranted()) {
            addItem("Permission: READ_PHONE_STATE", "label");
            addItem("Line1Number: ", mTelephonyManager.getLine1Number());
            addItem("SimSerialNumber: ", mTelephonyManager.getSimSerialNumber());
            addItem("DeviceSoftwareVersion: ", mTelephonyManager.getDeviceSoftwareVersion());
            addItem("SubscriberId: ", mTelephonyManager.getSubscriberId());
            addItem("DeviceId1: ", mTelephonyManager.getDeviceId());

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                addItem("Meid: ", mTelephonyManager.getMeid());
                addItem("ForbiddenPlans: ", Arrays.toString(mTelephonyManager.getForbiddenPlmns()));
                addItem("Imei1: ", mTelephonyManager.getImei());
                addItem("Imei2: ", mTelephonyManager.getImei(1));
            }

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                addItem("DataNetworkType: ", mTelephonyManager.getDataNetworkType());
            }
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                addItem("DeviceId2: ", mTelephonyManager.getDeviceId(1));
            }
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                addItem("GroupIdLevel1: ", mTelephonyManager.getGroupIdLevel1());
            }
        }
        if (isLocationPermissionGranted()) {
            addItem("Permission: ACCESS_COARSE_LOCATION", "label");
            addItem("CellLocation: ", mTelephonyManager.getCellLocation());
        }

    }


    private void clearContainer() {
        if (itemsContainer != null
                && itemsContainer.getChildCount() > 0) {
            itemsContainer.removeAllViews();
        }
    }

    private <T> void addItem(String label, T value) {

        final int random1 = new Random().nextInt(256);
        final int random2 = new Random().nextInt(256);
        final int random3 = new Random().nextInt(256);
        View view = getLayoutInflater().inflate(R.layout.info_item, null);
        itemsContainer.addView(view);
        TextView tvLabel = view.findViewById(R.id.tv_label);
        TextView tvValue = view.findViewById(R.id.tv_output);
        tvLabel.setBackground(getBackgroundDrawable(Color.rgb(random1, random2, random3)));
        tvValue.setBackground(getBackgroundDrawable(Color.rgb(random1, random2, random3)));

        tvLabel.setText(label);
        if(value instanceof String){
            if(((String) value).equalsIgnoreCase("label")) {
                tvValue.setVisibility(View.GONE);
                tvLabel.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                tvLabel.setGravity(Gravity.CENTER);
                tvLabel.setBackground(null);
                return;
            }
        }
        tvValue.setText("" + value);
    }

    private GradientDrawable getBackgroundDrawable(int color) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadii(new float[]{20, 20, 20, 20, 20, 20, 20, 20});
        shape.setColor(color);
        //shape.setStroke(3, borderColor);
        return shape;
    }

}
