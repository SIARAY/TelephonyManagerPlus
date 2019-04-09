package ir.siaray.telephonymanagerplus;

public class TextUtils {
    static boolean isEmpty(String text) {
        if (text == null
                || text.length() == 0
                || text.equals("")) {
            return true;
        }
        return false;
    }

    static boolean isEmpty(String[] text) {
        if (text == null
                || text.length == 0) {
            return true;
        }
        return false;
    }

}
