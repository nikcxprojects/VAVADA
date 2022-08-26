package com.tictacx.toe;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import com.yandex.metrica.YandexMetrica;
import com.yandex.metrica.YandexMetricaConfig;
import com.yandex.metrica.push.YandexMetricaPush;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // Creating an extended library configuration.
        YandexMetricaConfig config = YandexMetricaConfig.newConfigBuilder("1fca783c-d056-4e03-8004-d01a8489b642").build();
        // Initializing the AppMetrica SDK.
        YandexMetrica.activate(getApplicationContext(), config);
        Log.d("YandexMetrica", "YandexMetrica");
        // Automatic tracking of user activity.
        YandexMetrica.enableActivityAutoTracking(this);


        YandexMetricaPush.init(getApplicationContext());

    }
}