package org.smartregister.simprint.sample;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import org.smartregister.simprint.SimPrintsConstant;
import org.smartregister.simprint.SimPrintsLibrary;
import org.smartregister.simprint.SimPrintsRegisterActivity;
import org.smartregister.simprint.SimPrintsRegistration;
import org.smartregister.simprint.SimPrintsVerifyActivity;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_REGISTER = 1234;
    private static final int REQUEST_CODE_VERIFY = 1334;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SimPrintsLibrary.init(MainActivity.this,"tZqJnw0ajK04LMYdZzyw","test_user");

        findViewById(R.id.capture_finger_print_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimPrintsRegisterActivity.startSimprintsRegisterActivity(MainActivity.this,"mpower",REQUEST_CODE_REGISTER);

            }
        });
        findViewById(R.id.verify_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimPrintsVerifyActivity.startSimprintsVerifyActivity(MainActivity.this,"mpower","12312312312",REQUEST_CODE_VERIFY);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data !=null){

            SimPrintsRegistration simprintsRegistration =(SimPrintsRegistration) data.getSerializableExtra(SimPrintsConstant.INTENT_DATA);

            switch (requestCode){
                case  REQUEST_CODE_REGISTER:
                    ((TextView)findViewById(R.id.text_status)).setText("GUID:"+simprintsRegistration.getGuid()+":status:"+simprintsRegistration.getCheckStatus());
                    break;
                case REQUEST_CODE_VERIFY:
                    ((TextView)findViewById(R.id.text_status)).setText("verification status:"+simprintsRegistration.getCheckStatus());
                    break;
            }
        }
    }
}
