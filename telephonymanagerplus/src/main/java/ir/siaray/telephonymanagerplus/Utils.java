package ir.siaray.telephonymanagerplus;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static ir.siaray.telephonymanagerplus.Constants.DEFAULT_GSM_CELL_ID_VALUE;
import static ir.siaray.telephonymanagerplus.Constants.DEFAULT_TELEPHONY_MANAGER_VALUE;

public class Utils {

    static String getTelephonyManagerValues(Context context
            , TelephonyManager telephony
            , String methodName
            , int simSlotId) {
        if (context == null)
            return DEFAULT_TELEPHONY_MANAGER_VALUE;
        Class<?> telephonyClass;
        String reflectionMethod = null;
        String output = null;
        if (telephony != null) {
            try {
                telephonyClass = Class.forName(telephony.getClass().getName());
                for (Method method : telephonyClass.getMethods()) {
                    String name = method.getName();
                    if (name.equalsIgnoreCase(methodName)/*name.contains(methodName)*/) {
                        //Log.i("methodName: " + methodName );
                        //Log.i("methodName: " + name + " value: " + getOutputByReflection(telephony, name, simSlotId, false));
                        Class<?>[] params = method.getParameterTypes();
                        //Log.i("methodName param: " + name + " value: " + params.getClass().getName());
                        if (params.length == 1 && params[0].getName().equals("int")) {
                            reflectionMethod = name;
                            //Log.i("methodName okkkkkk");
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (reflectionMethod != null) {
            try {
                output = getOutputByReflection(telephony, reflectionMethod, simSlotId, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return output;
    }

    private static String getOutputByReflection(TelephonyManager telephony
            , String predictedMethodName
            , int slotID
            , boolean isPrivate) {

        String result = DEFAULT_TELEPHONY_MANAGER_VALUE;
        try {
            Class<?> telephonyClass = Class.forName(telephony.getClass().getName());
            Class<?>[] parameter = new Class[1];
            parameter[0] = int.class;
            Method getSimID;
            if (slotID != -1) {
                if (isPrivate) {
                    getSimID = telephonyClass.getDeclaredMethod(predictedMethodName, parameter);
                } else {
                    getSimID = telephonyClass.getMethod(predictedMethodName, parameter);
                    printSlot("simid1", slotID, telephony, getSimID);
                }
            } else {
                if (isPrivate) {
                    getSimID = telephonyClass.getDeclaredMethod(predictedMethodName);
                } else {
                    getSimID = telephonyClass.getMethod(predictedMethodName);
                }
            }
            //Log.i("sim slot " +slotID+" : id:"+ getSimID);
            Object ob_phone;
            Object[] obParameter = new Object[1];
            obParameter[0] = slotID;
            if (getSimID != null) {
                if (slotID != -1) {
                    ob_phone = getSimID.invoke(telephony, obParameter);
                } else {
                    ob_phone = getSimID.invoke(telephony);
                }

                if (ob_phone != null) {
                    result = ob_phone.toString();
                }
            }
        } catch (Exception e) {
            return DEFAULT_TELEPHONY_MANAGER_VALUE;
        }
        if (TextUtils.isEmpty(result)) {
            return DEFAULT_TELEPHONY_MANAGER_VALUE;
        }

        return result;

    }

    private static void printSlot(String tag, int slotID, TelephonyManager telephony, Method getSimID) {
        Object ob_phone;
        Object[] obParameter = new Object[1];
        obParameter[0] = slotID;
        try {
            if (getSimID != null) {
                if (slotID != -1) {
                    ob_phone = getSimID.invoke(telephony, obParameter);

                } else {
                    ob_phone = getSimID.invoke(telephony);
                }

                if (ob_phone != null) {
                    Log.i("slottttt " + slotID + " : " + tag + " : " + ob_phone.toString());
                    //Log.printItems(getSimID);
                } else {
                    Log.i("slottttt " + slotID + " : " + tag + " : null");
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    interface CellLocationType {
        int LOC = 0;
        int CID = 1;
        int PSC = 2;
    }

    static int getCellLocationValue(Context context, String cellLocation, int cellLocationType) {
        Log.i("cellLoc: " + cellLocation);
        String[] splitedCellLocation;
        //if (isTelephonyManagerValueValid(cellLocation)) {
        try {
            cellLocation = cellLocation.replaceAll("[\\[\\]]", "");
            splitedCellLocation = cellLocation.split(",");
            if (!TextUtils.isEmpty(splitedCellLocation)) {
                String value = splitedCellLocation[cellLocationType];
                if (isNumeric(value)) {
                    return Integer.parseInt(value);
                }
                Log.i("cellLoc value: " + value);
            }

        } catch (Exception e) {
            Log.i(context.getString(R.string.error_cell_location_value));
        }
        //}
        return DEFAULT_GSM_CELL_ID_VALUE;
    }

    static boolean isNumeric(String s) {
        return s.matches("[-+]?\\d*\\.?\\d+");
    }

}
