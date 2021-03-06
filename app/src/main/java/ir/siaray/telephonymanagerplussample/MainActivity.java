package ir.siaray.telephonymanagerplussample;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Arrays;
import java.util.Random;

import ir.siaray.telephonymanagerplus.TelephonyManagerPlus;

import static android.Manifest.permission.READ_PHONE_STATE;
import static ir.siaray.telephonymanagerplussample.Utils.isLowerThanAndroidQ;

public class MainActivity extends AppCompatActivity {

    private ViewGroup itemsContainer;
    private Button btnTelephonyManager;
    private Button btnTelephonyManagerPlus;
    private boolean libraryButtonClicked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
    }

    private void initUi() {
        itemsContainer = findViewById(R.id.items_container);
        btnTelephonyManager = findViewById(R.id.btn_tm_details);
        btnTelephonyManagerPlus = findViewById(R.id.btn_tm_plus_details);
        btnTelephonyManagerPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printTelephonyManagerPlusDetails();
            }
        });
        btnTelephonyManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printTelephonyManagerDetails();
            }
        });
        printTelephonyManagerPlusDetails();
    }

    @SuppressLint("MissingPermission")
    private void printTelephonyManagerPlusDetails() {
        btnTelephonyManagerPlus.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.selectedButton));
        btnTelephonyManager.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        clearContainer();
        TelephonyManagerPlus telephonyManagerPlus = TelephonyManagerPlus.getInstance(this);
        addItem("SimOperatorName1", telephonyManagerPlus.getSimOperatorName1());
        addItem("SimOperatorName2", telephonyManagerPlus.getSimOperatorName2());
        addItem("SimOperator1", telephonyManagerPlus.getSimOperatorCode1());
        addItem("SimOperator2", telephonyManagerPlus.getSimOperatorCode2());
        addItem("Mnc1", telephonyManagerPlus.getMnc1());
        addItem("Mnc2", telephonyManagerPlus.getMnc2());
        addItem("Mcc1", telephonyManagerPlus.getMcc1());
        addItem("Mcc2", telephonyManagerPlus.getMcc2());
        if (isReadPhoneStatePermissionGranted()) {
            addItem("Permission: READ_PHONE_STATE", "label");
            addItem("DualSim", telephonyManagerPlus.isDualSim());
            addItem("SimSerialNumber1", telephonyManagerPlus.getSimSerialNumber1());
            addItem("SimSerialNumber2", telephonyManagerPlus.getSimSerialNumber2());
            addItem("Imei1", telephonyManagerPlus.getImei1());
            addItem("Imei2", telephonyManagerPlus.getImei2());
            addItem("SubscriberId1", telephonyManagerPlus.getSubscriberId1());
            addItem("SubscriberId2", telephonyManagerPlus.getSubscriberId2());
        }
        if (isLocationPermissionGranted()) {
            addItem("Permission: ACCESS_COARSE_LOCATION", "label");
            addItem("Lac1", telephonyManagerPlus.getLac1());
            addItem("Lac2", telephonyManagerPlus.getLac2());
            addItem("Cid1", telephonyManagerPlus.getCid1());
            addItem("Cid2", telephonyManagerPlus.getCid2());
        }
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try {
            if (grantResults.length > 0) {
                switch (requestCode) {
                    case 1: {
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            if (libraryButtonClicked)
                                printTelephonyManagerPlusDetails();
                            else printTelephonyManagerDetails();

                        } else {
                            Toast.makeText(this, "Need to READ_PHONE_STATE permission", Toast.LENGTH_SHORT).show();
                        }
                        return;
                    }
                    case 2: {
                        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                            if (libraryButtonClicked)
                                printTelephonyManagerPlusDetails();
                            else printTelephonyManagerDetails();

                        } else {
                            Toast.makeText(this, "Need to Location permission", Toast.LENGTH_SHORT).show();
                        }
                        return;
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("MissingPermission")
    private void printTelephonyManagerDetails() {
        btnTelephonyManager.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.selectedButton));
        btnTelephonyManagerPlus.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
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
            if (isLowerThanAndroidQ(this))
                addItem("SimSerialNumber: ", mTelephonyManager.getSimSerialNumber());
            else
                addItem("SimSerialNumber: ", "Not available in android Q");
            addItem("DeviceSoftwareVersion: ", mTelephonyManager.getDeviceSoftwareVersion());
            if (isLowerThanAndroidQ(this))
                addItem("SubscriberId: ", mTelephonyManager.getSubscriberId());
            else
                addItem("SubscriberId: ", "Not available in android Q");
            if (isLowerThanAndroidQ(this))
                addItem("DeviceId1: ", mTelephonyManager.getDeviceId());
            else
                addItem("DeviceId1: ", "Not available in android Q");
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                addItem("ForbiddenPlans: ", Arrays.toString(mTelephonyManager.getForbiddenPlmns()));
                if (isLowerThanAndroidQ(this)) {
                    addItem("Meid: ", mTelephonyManager.getMeid());
                    addItem("Imei1: ", mTelephonyManager.getImei());
                    addItem("Imei2: ", mTelephonyManager.getImei(1));
                }else{
                    addItem("Meid: ", "Not available in android Q");
                    addItem("Imei1: ", "Not available in android Q");
                    addItem("Imei2: ", "Not available in android Q");
                }
            }

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                addItem("DataNetworkType: ", mTelephonyManager.getDataNetworkType());
            }
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (isLowerThanAndroidQ(this)) {
                    addItem("DeviceId2: ", mTelephonyManager.getDeviceId(1));
                }else{
                    addItem("DeviceId2: ", "Not available in android Q");
                }
            }
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                addItem("GroupIdLevel1: ", mTelephonyManager.getGroupIdLevel1());
            }
        }
        if (isLocationPermissionGranted()) {
            addItem("Permission: ACCESS_COARSE_LOCATION", "label");
            if (isLowerThanAndroidQ(this)) {
                addItem("CellLocation: ", mTelephonyManager.getCellLocation());
            }else{
                addItem("CellLocation: ", "Not available in android Q");
            }
        }

    }

    private void clearContainer() {
        if (itemsContainer != null
                && itemsContainer.getChildCount() > 0) {
            itemsContainer.removeAllViews();
        }
    }

    private <T> void addItem(String label, T value) {
        final int random1 = new Random().nextInt(230);
        final int random2 = new Random().nextInt(230);
        final int random3 = new Random().nextInt(230);

        View view = getLayoutInflater().inflate(R.layout.info_item, null);
        itemsContainer.addView(view);
        ViewGroup itemContainer = view.findViewById(R.id.item_container);
        TextView tvLabel = view.findViewById(R.id.tv_label);
        TextView tvValue = view.findViewById(R.id.tv_output);
        itemContainer.setBackground(getBackgroundDrawable(Color.rgb(random1, random2, random3)));
        //tvLabel.setBackground(getBackgroundDrawable(Color.rgb(random1, random2, random3)));
        //tvValue.setBackground(getBackgroundDrawable(Color.rgb(random1, random2, random3)));

        tvLabel.setText(label);
        if (value instanceof String) {
            if (((String) value).equalsIgnoreCase("label")) {
                tvValue.setVisibility(View.GONE);
                tvLabel.setLayoutParams(new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                tvLabel.setGravity(Gravity.CENTER);
                itemContainer.setBackground(null);
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
