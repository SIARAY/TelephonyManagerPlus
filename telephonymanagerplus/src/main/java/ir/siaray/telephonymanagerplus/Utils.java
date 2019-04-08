package ir.siaray.telephonymanagerplus;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.lang.reflect.Method;

import static ir.siaray.telephonymanagerplus.Constants.DEFAULT_TELEPHONY_MANAGER_VALUE;

public class Utils {

    static String getTelephonyManagerValues(Context context, String methodName, int simSlotId) {
        if (context == null)
            return DEFAULT_TELEPHONY_MANAGER_VALUE;
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        Class<?> telephonyClass;
        String reflectionMethod = null;
        String output = null;
        if (telephony != null) {
            try {
                telephonyClass = Class.forName(telephony.getClass().getName());
                for (Method method : telephonyClass.getMethods()) {
                    String name = method.getName();
                    if (name.contains(methodName)) {
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

    private static String getOutputByReflection(TelephonyManager telephony, String predictedMethodName, int slotID, boolean isPrivate) {

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
            return DEFAULT_TELEPHONY_MANAGER_VALUE;
        }
        if (TextUtils.isEmpty(result)) {
            return DEFAULT_TELEPHONY_MANAGER_VALUE;
        }

        return result;

    }

}
