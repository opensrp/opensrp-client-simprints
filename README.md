
# Table of Contents

* [Introduction](#introduction)
* [Features](#features)
* [Developer Documentation](#developer-documentation)

# Introduction
OpenSRP client simprints is the wrapping module to access simprints SDK to integrate finger print at openSRP projects. It'll help developer to access the simprints logic through this module.

# Features
OpenSRP client simprints supports 3 basic features, during capture of a beneficiary's biometrics: Enrolment, Identification, and Verification.


   1. Enrolment/Registration - this is a process that involves registering a new beneficiary, using their biometric data.

   2. Identification - this is a process that involves identifying a beneficiary from a list of matching candidates.

   3. Verification -  this involves verifying that a beneficiary is who they say they are.

# Developer Documentation
This section will provide a brief description how to integrate this sdk in your application

##Installation
First need to install simpritsID application in your device

NOTE:  ensure you are installing the latest version of OpenSRP client simprints, you can check our releases page for the latest version. 
```
implementation('org.smartregister:opensrp-client-simprints:1.1.0-SNAPSHOT@aar') {
        transitive = true
        exclude group: 'com.android.support', module: 'appcompat-v7'
        exclude group: 'com.android.support', module: 'design'
    }
    
 ```
## Enrollment/Registration

Registration can be added through json form or button click action

## Through json form

```
{
        "key": "finger_print",
        "openmrs_entity_parent": "",
        "openmrs_entity": "",
        "openmrs_entity_id": "",
        "type": "finger_print",
        "project_id": "tZqJnw0ajK04LMYdZzyw",
        "user_id": "test_user",
        "module_id": "mpower",
        "finger_print_option": "register",
        "uploadButtonText": "Take finger print",
        "image_file": "",
        
      }
  ```
  This process requires some basic parameters to be sent in the corresponding intent message:

   1. Project ID - the specific project of the integrator.

   2. Module ID - the specific module of beneficiaries, within the specified project.

   3. User ID - the logged in user id (specific to your app).
   
   ## Through code
   
   Initialize the simprints library with below code
   
```
   SimPrintsLibrary.init(MainActivity.this, "project_id","user_id");
   
```
After press any button add below code for registration
```
 findViewById(R.id.capture_finger_print_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimPrintsRegisterActivity.startSimprintsRegisterActivity(MainActivity.this,"module_id",REQUEST_CODE_REGISTER);

            }
        });
```
   Note: After calling this method it'll check the simprintsID app onready install or not with below check. So no need to check this from application side
   ```
   ......
   if(!SimPrintsUtils.isPackageInstalled(SIMPRINTS_PACKAGE_NAME,getPackageManager())){
            SimPrintsUtils.downloadSimprintIdApk(this);
            return;
        }
   ```
   From onActivityResult we'll catch the result callback from simprints ID as below. You'll need to check the **simprintsRegistration.getCheckStatus()** before access other option. If this return true you'll get the GUID with others parameter.
   
   ```
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data !=null){

            switch (requestCode){
                case  REQUEST_CODE_REGISTER:
                    SimPrintsRegistration simprintsRegistration =(SimPrintsRegistration) data.getSerializableExtra(SimPrintsConstantHelper.INTENT_DATA);
                    ((TextView)findViewById(R.id.text_status)).setText("GUID:"+simprintsRegistration.getGuid()+":status:"+simprintsRegistration.getCheckStatus());
                    break;
             .......
             .......

            }
        }
    }
    
   ```
   
