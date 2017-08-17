package com.braulio.cassule.designfocus;

/**
 * Created by Braulio on 1/21/2017.
 */
import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;


public class Quadro  extends Application {


        @Override
        public void onCreate() {
            super.onCreate();
            FacebookSdk.sdkInitialize(getApplicationContext());
            AppEventsLogger.activateApp(this);
        }
    }
