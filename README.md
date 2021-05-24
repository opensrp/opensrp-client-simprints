
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
   From onActivityResult we'll catch the result callback from simprints ID as below. We'll need to check the **simprintsRegistration.getCheckStatus()** before access other option. If this return true we'll get the GUID with others parameter.
   
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
  ## Verification
  This verification process involves matching previously enrolled biometric data with the present beneficiary's biometric.Calling app creates a Verify Intent using projectID, moduleID, userID and beneficiary's uniqueID(GUID)
  ```
  findViewById(R.id.verify_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimPrintsVerifyActivity.startSimprintsVerifyActivity(MainActivity.this,"module_id","gu_id",REQUEST_CODE_VERIFY);

            }
        });
  ```
  At onActivityResult we can get the result from simprints IDs.
  
  ``` @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data !=null){

            switch (requestCode){
                
                case REQUEST_CODE_VERIFY:
                    SimPrintsVerification verifyResults = (SimPrintsVerification) data.getSerializableExtra(SimPrintsConstantHelper.INTENT_DATA);
                    ((TextView)findViewById(R.id.text_status)).setText("verification status:"+verifyResults.getCheckStatus());
                    break;
              

            }
        }
    }
  ```
We'll need to check the **simprintsRegistration.getCheckStatus()** before access other option.
Handling Verification Response

During the verification process, the present beneficiary's biometric is captured and the verifyGuid, that is passed in the Intent, is used to retrieve previously enroled beneficiary's biometric, and the two biometric templates are tested for a match.

If this verification process completes successfully, a verification result containing the following properties is returned:


   1. Tier - the ranking tier for the biometric record **verifyResults.getTier**

   2. Confidence - the percentage to which the record matches the captured biometric **verifyResults.getConfidence**

   3. Guid - the unique id for the biometric record **verifyResults.getGuid**


Note:  The confidence and tier values can then be used to determine the ranking and accuracy for the matched biometric record, to get more info on this

  ## Identification/Search by finger print
  
  You will need to identify this beneficiary by capturing the biometrics and running it through the system, for potential matches. The beneficiary would then be selected from this list of potential matches. This is the Identification process.
  
   ```
    findViewById(R.id.identify_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimPrintsIdentifyActivity.startSimprintsIdentifyActivity(MainActivity.this, "module_id", REQUEST_CODE_IDENTIFY);
            }
        });
   ```
   We'll get the list identification result from the callback
   
    ```
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data !=null){

            switch (requestCode){
                
                case REQUEST_CODE_IDENTIFY:
                    ArrayList<SimPrintsIdentification> identifications = (ArrayList<SimPrintsIdentification>) data.getSerializableExtra(SimPrintsConstantHelper.INTENT_DATA);
                    StringBuilder identificationsResults = new StringBuilder();
                    for (SimPrintsIdentification identification : identifications){
                        identificationsResults.append(identification.getGuid());
                        identificationsResults.append("\n");
                    }
                    ((TextView)findViewById(R.id.text_status)).setText("Identification Results:"+identificationsResults);
                    break;

            }
        }
    }
    ```
  This generated ranked list contains **SimPrintsIdentification identification** records with each having the following properties:

  1.  Tier - the ranking tier for the biometric record **identification.getTier**

  2.  Confidence Score - the percentage to which the record matches the captured biometric **identification.getConfidence**

  3.  Guid - the unique id for the biometric record **identification.getGuid**
  4.  Session id - data.getStringExtra(Constants.SIMPRINTS_SESSION_ID);
  
  ## Identification Callout - (Confirm Identity)
  
  After getting identity result and if it's match with any beneficiary then application need to send the identification callout for simprints as like below
   
 ```
   SimPrintsHelper simPrintsHelper = new SimPrintsHelper(project_id, user_id);
        Intent intent;
        if (TextUtils.isEmpty(simPrintsGuid)) {
            intent = simPrintsHelper.confirmIdentity(context, sessiodId, "none_selected");

        } else {
            intent = simPrintsHelper.confirmIdentity(context, sessiodId, simPrintsGuid);
        }
        startActivityForResult(intent, REQUEST_CODE);
 ```
