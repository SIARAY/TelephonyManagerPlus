package ir.siaray.telephonymanagerplussample;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;

public class Utils {

    static int getAppTargetSdkVersion(Context context) {
        int version = Build.VERSION_CODES.Q;
        if (context != null) {
            PackageManager pm = context.getPackageManager();
            try {
                if (pm != null) {
                    ApplicationInfo applicationInfo = pm.getApplicationInfo(context.getPackageName(), 0);
                    if (applicationInfo != null) {
                        version = applicationInfo.targetSdkVersion;
                    }
                }
            } catch (Exception e) {
            }
        }
        return version;
    }

    public static boolean isLowerThanAndroidQ(Context context) {
        if (getAppTargetSdkVersion(context) < Build.VERSION_CODES.Q
                || Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            return true;
        }
        return false;
    }
}
