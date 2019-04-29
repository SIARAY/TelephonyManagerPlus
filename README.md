## TelephonyManagerPlus
TelephonyManager for dual sim devices.

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


## License

    Copyright 2017 Siamak Rayeji

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.