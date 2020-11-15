package br.com.daniel.hackathon;

import android.app.Application;
import android.content.ContextWrapper;
import com.pixplicity.easyprefs.library.Prefs;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        this.initializePreferences();
   }

    private void initializePreferences(){
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
    }

}
