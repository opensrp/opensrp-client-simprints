package org.smartregister.simprint;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.simprints.libsimprints.Constants;
import com.simprints.libsimprints.Registration;

import static com.simprints.libsimprints.Constants.SIMPRINTS_PACKAGE_NAME;

public class SimprintsRegisterActivity extends AppCompatActivity {

    private static final String PUT_EXTRA_REQUEST_CODE =  "result_code";
    private static final String TAG = "SimprintsRegister";


    private int REQUEST_CODE;
    private String moduleId;



    public static void startSimprintsRegisterActivity(Activity context, String moduleId, int requestCode){
        Intent intent = new Intent(context,SimprintsRegisterActivity.class);
        intent.putExtra(Constants.SIMPRINTS_MODULE_ID,moduleId);
        intent.putExtra(PUT_EXTRA_REQUEST_CODE,requestCode);
        context.startActivityForResult(intent,requestCode);

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!Utils.isPackageInstalled(SIMPRINTS_PACKAGE_NAME,getPackageManager())){
            Utils.downloadSimprintIdApk(this);
            return;
        }
        moduleId = getIntent().getStringExtra(Constants.SIMPRINTS_MODULE_ID);
        REQUEST_CODE = getIntent().getIntExtra(PUT_EXTRA_REQUEST_CODE,111);
        try{
            SimprintsHelper simprintsHelper = new SimprintsHelper(SimprintsLibrary.getInstance().getProjectId(),
                    SimprintsLibrary.getInstance().getUserId());
            Intent intent = simprintsHelper.enroll(moduleId);
            startActivityForResult(intent,REQUEST_CODE);
        }catch (IllegalStateException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            finish();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if( data!=null && resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            Registration registration = data.getParcelableExtra(Constants.SIMPRINTS_REGISTRATION);

            String uniqueId = registration.getGuid();
            Boolean check = data.getBooleanExtra(Constants.SIMPRINTS_BIOMETRICS_COMPLETE_CHECK,true);
            Intent returnIntent = new Intent();
            SimprintsRegistration simprintsRegistration = new SimprintsRegistration(uniqueId);
            simprintsRegistration.setCheckStatus(check);
            returnIntent.putExtra(SimprintsConstant.INTENT_DATA,simprintsRegistration);
            setResult(RESULT_OK,returnIntent);
            finish();

        }
    }
}
