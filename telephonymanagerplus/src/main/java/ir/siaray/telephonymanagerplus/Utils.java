package ir.siaray.telephonymanagerplus;

import android.content.Context;
import android.telephony.TelephonyManager;

import java.lang.reflect.Method;

import static ir.siaray.telephonymanagerplus.Constants.DEFAULT_GSM_CELL_ID_VALUE;
import static ir.siaray.telephonymanagerplus.Constants.DEFAULT_TELEPHONY_MANAGER_INT_VALUE;
import static ir.siaray.telephonymanagerplus.Constants.DEFAULT_TELEPHONY_MANAGER_STRING_VALUE;

class Utils {

    static String getTelephonyManagerValues(Context context
            , TelephonyManager telephony
            , String methodName
            , int simSlotId) {
        if (context == null)
            return DEFAULT_TELEPHONY_MANAGER_STRING_VALUE;
        Class<?> telephonyClass;
        String reflectionMethod = null;
        String output = null;
        if (telephony != null) {
            try {
                telephonyClass = Class.forName(telephony.getClass().getName());
                for (Method method : telephonyClass.getMethods()) {
                    String name = method.getName();
                    if (name.equalsIgnoreCase(methodName)/*name.contains(methodName)*/) {
                        Class<?>[] params = method.getParameterTypes();
                        if (params.length == 1 && params[0].getName().equals("int")) {
                            reflectionMethod = name;

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
        String result = DEFAULT_TELEPHONY_MANAGER_STRING_VALUE;
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
                }
            } else {
                if (isPrivate) {
                    getSimID = telephonyClass.getDeclaredMethod(predictedMethodName);
                } else {
                    getSimID = telephonyClass.getMethod(predictedMethodName);
                }
            }
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
            return DEFAULT_TELEPHONY_MANAGER_STRING_VALUE;
        }
        if (TextUtils.isEmpty(result)) {
            return DEFAULT_TELEPHONY_MANAGER_STRING_VALUE;
        }

        return result;

    }

    interface CellLocationType {
        int LAC = 0;
        int CID = 1;
        int PSC = 2;
    }

    static int getCellLocationValue(Context context, String cellLocation, int cellLocationType) {
        String[] splitedCellLocation;
        if (isTelephonyManagerValueValid(cellLocation)) {
            try {
                cellLocation = cellLocation.replaceAll("[\\[\\]]", "");
                splitedCellLocation = cellLocation.split(",");
                if (!TextUtils.isEmpty(splitedCellLocation)) {
                    String value = splitedCellLocation[cellLocationType];
                    if (isNumeric(value)) {
                        return Integer.parseInt(value);
                    }
                }

            } catch (Exception e) {
                Log.print(context.getString(R.string.error_cell_location_value));
            }
        }
        return DEFAULT_GSM_CELL_ID_VALUE;
    }

    static boolean isNumeric(String stringNumber) {
        return !TextUtils.isEmpty(stringNumber) && stringNumber.matches("[-+]?\\d*\\.?\\d+");
    }


    private static boolean isTelephonyManagerValueValid(String value) {
        if (TextUtils.isEmpty(value)) {
            return false;
        }
        return true;
    }

    static int getMncFromNetworkOperator(String simOperatorCode) {
        if (isNumeric(simOperatorCode)) {
            if (simOperatorCode.length() >= 5) {
                return Integer.parseInt(simOperatorCode.substring(3));
            }
        }
        return DEFAULT_TELEPHONY_MANAGER_INT_VALUE;
    }

    static int getMccFromNetworkOperator(String simOperatorCode) {
        if (isNumeric(simOperatorCode)) {
            if (simOperatorCode.length() > 3) {
                return Integer.parseInt(simOperatorCode.substring(0, 3));
            }
        }
        return DEFAULT_TELEPHONY_MANAGER_INT_VALUE;
    }
}
