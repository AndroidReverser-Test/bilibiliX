package com.bili.biliquguanggao;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class QuGuang implements IXposedHookLoadPackage {

    public static String TAG = "xingtong";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (loadPackageParam.packageName.equals("tv.danmaku.bili")){

            //开屏广告
            XposedHelpers.findAndHookMethod("tv.danmaku.bili.ui.splash.ad.model.Splash", loadPackageParam.classLoader, "isValid", new XC_MethodReplacement() {
                @Override
                protected Object replaceHookedMethod(MethodHookParam methodHookParam) throws Throwable {
                    Log.d(TAG,"splash ad pass");
                    return false;
                }
            });

            //更新校验
            XposedHelpers.findAndHookMethod("org.json.JSONObject", loadPackageParam.classLoader, "optInt",
                    String.class, int.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            if (param.args[0].equals("code") && StackUtils.isCallingFromupdate()){
                                Log.d(TAG,"pass version check");
                                param.setResult(304);
                            }
                            super.afterHookedMethod(param);
                        }
                    });

            XposedHelpers.findAndHookConstructor("com.bilibili.adcommon.biz.AdAbsView", loadPackageParam.classLoader, View.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    View adsView = (View) param.args[0];
                    adsView.setVisibility(View.GONE);
                    Log.d(TAG,"pass ads view");
                }
            });



            XposedHelpers.findAndHookMethod("com.bilibili.pegasus.card.banner.b", loadPackageParam.classLoader, "onCreateViewHolder", ViewGroup.class, int.class, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    super.beforeHookedMethod(param);
                    ViewGroup viewGroup = (ViewGroup) param.args[0];
                    viewGroup.setVisibility(View.GONE);
                    Log.d(TAG,"pass banner ad");
                }
            });

        }
    }
}
