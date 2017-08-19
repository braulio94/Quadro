package com.braulio.cassule.designfocus.activity;

import android.os.Bundle;
import android.view.View;

import com.braulio.cassule.designfocus.R;
import com.braulio.cassule.designfocus.activity.BaseActivity;

public class InfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

    }
    public void close(View v){
        finish();
    }
}
