package com.braulio.cassule.designfocus;

import android.os.Bundle;
import android.view.View;

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
