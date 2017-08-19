package com.braulio.cassule.designfocus.activity;

import android.os.Bundle;
import android.view.View;

import com.braulio.cassule.designfocus.R;
import com.braulio.cassule.designfocus.activity.BaseActivity;


public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

    }
    public void close(View v){
        finish();
    }

}
