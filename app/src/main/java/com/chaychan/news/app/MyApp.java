package com.chaychan.news.app;

import android.text.TextUtils;

import com.chaychan.news.BuildConfig;
import com.chaychan.news.app.base.BaseApp;
import com.chaychan.news.utils.SharedPrefsUtil;
import com.github.anzewei.parallaxbacklayout.ParallaxHelper;
import com.socks.library.KLog;

import org.litepal.LitePalApplication;

/**
 * @author ChayChan
 * @description: Application类
 * @date 2017/6/10  15:44
 */

public class MyApp extends BaseApp {

    private static MyApp instances;

    public static MyApp getInstances() {
        return instances;
    }

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean NotLogged() {
        return TextUtils.isEmpty(token);
    }

    public void removeToken() {
        this.token = "";
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instances = this;
        token = SharedPrefsUtil.getValue(BaseApp.getContext(), "token", "");
        //**************************************相关第三方SDK的初始化等操作*************************************************
        KLog.init(BuildConfig.DEBUG);//初始化KLog
        LitePalApplication.initialize(getApplicationContext());//初始化litePal

        registerActivityLifecycleCallbacks(ParallaxHelper.getInstance());
    }
}
