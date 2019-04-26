## TelephonyManagerPlus
TelephonyManager for dualsim devices.

##### Dependency

    dependencies {
        implementation 'ir.siaray:telephonymanagerplus:1.0.2'
    }

## Usage

    TelephonyManagerPlus telephonyManagerPlus = TelephonyManagerPlus.getInstance(context);
    
    String imei1 = telephonyManagerPlus.getImei1();
    String imei2 = telephonyManagerPlus.getImei2();
    String simOperatorCode1 = telephonyManagerPlus.getSimOperatorCode1();
    String simOperatorCode2 = telephonyManagerPlus.getSimOperatorCode2();
    String simOperatorName1 = telephonyManagerPlus.getSimOperatorName1();
    String simOperatorName2 = telephonyManagerPlus.getSimOperatorName2();
    String simSerialNumber1 = telephonyManagerPlus.getSimSerialNumber1();
    String simSerialNumber2 = telephonyManagerPlus.getSimSerialNumber2();
    String subscriberId1 = telephonyManagerPlus.getSubscriberId1();
    String subscriberId2 = telephonyManagerPlus.getSubscriberId2();
    int mcc1 = telephonyManagerPlus.getMcc1();
    int mcc2 = telephonyManagerPlus.getMcc2();
    int mnc1 = telephonyManagerPlus.getMnc1();
    int mnc2 = telephonyManagerPlus.getMnc2();
    int cid1 = telephonyManagerPlus.getCid1();
    int cid2 = telephonyManagerPlus.getCid2();
    int lac1 = telephonyManagerPlus.getLac1();
    int lac2 = telephonyManagerPlus.getLac2();
