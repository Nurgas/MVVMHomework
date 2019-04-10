package kg.geektech.to_do_mvvm;

import android.app.Application;
import android.content.Context;

public class AppDelegate extends Application {

    private static Context INSTANCE;

    @Override
    public void onCreate() {
        super.onCreate();

        INSTANCE = this;
    }

    public static Context getAppContext(){
        return INSTANCE;
    }
}
