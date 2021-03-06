package pri.weiqiang.myjapanese;

import android.app.Application;
import android.support.v7.app.AppCompatDelegate;

import com.blankj.utilcode.utils.NetworkUtils;
import com.umeng.analytics.MobclickAgent;

import pri.weiqiang.myjapanese.config.Constants;
import pri.weiqiang.myjapanese.manager.SharedPreferenceManager;

public class MyApplication extends Application {

    private static MyApplication instance = null;

    public static int CURRENT_ITEM = 0;
    public static int TYPE_MING = Constants.TYPE_HIRAGANA;

    public static int FROM_LAN = 0;
    public static int TO_LAN = 0;

    public static boolean ISWIFI = false;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        setDayNightMode(SharedPreferenceManager.getInstance().getString(Constants.MODE_THEME, Constants.MODE_DAY));

        ISWIFI = NetworkUtils.isWifiConnected(this);

        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

    }

    public synchronized static MyApplication getInstance() {
        return instance;
    }

    public void setDayNightMode(String mode) {

        switch (mode) {
            case Constants.MODE_AUTO:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO);
                break;
            case Constants.MODE_NIGHT:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                break;
            case Constants.MODE_DAY:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;
            default:
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                break;

        }

    }


}
