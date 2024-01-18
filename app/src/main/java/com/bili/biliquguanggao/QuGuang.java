package com.bili.biliquguanggao;

import android.util.Log;
import android.view.View;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class QuGuang implements IXposedHookLoadPackage {

    public static String TAG = "xingtong";

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        if (loadPackageParam.packageName.equals("tv.danmaku.bili")){
            Class SplashClazz = XposedHelpers.findClassIfExists("tv.danmaku.bili.ui.splash.ad.model.Splash",loadPackageParam.classLoader);
            Class SourceContentClazz = XposedHelpers.findClassIfExists("com.bilibili.adcommon.basic.model.SourceContent", loadPackageParam.classLoader);
            Class FeedAdInfoClazz = XposedHelpers.findClassIfExists("com.bilibili.adcommon.basic.model.FeedAdInfo", loadPackageParam.classLoader);


            XposedHelpers.findAndHookMethod("tv.danmaku.bili.ui.splash.ad.page.x", loadPackageParam.classLoader, "a", SplashClazz, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    param.args[0] = null;
                    super.beforeHookedMethod(param);
                    Log.d(TAG,"hook1成功");

                }
            });
            XposedHelpers.findAndHookMethod("org.json.JSONObject", loadPackageParam.classLoader, "optInt",
                    String.class, int.class, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            if (param.args[0].equals("code") && StackUtils.isCallingFromupdate()){
                                Log.d(TAG,"hook2成功");
                                param.setResult(304);
                            }
                            super.afterHookedMethod(param);
                        }
                    });
            XposedHelpers.findAndHookMethod("com.bilibili.ad.adview.videodetail.b", loadPackageParam.classLoader, "a", SourceContentClazz, new XC_MethodHook() {
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) throws Throwable {
                    Log.d(TAG, "layout:" + (int)methodHookParam.getResult());
                    if ((int)methodHookParam.getResult() != 105){
                        methodHookParam.setResult(105);
                    }
                    super.afterHookedMethod(methodHookParam);
                }
            });

            XposedHelpers.findAndHookMethod("com.bilibili.adcommon.basic.model.FeedItem", loadPackageParam.classLoader, "setFeedAdInfo", FeedAdInfoClazz, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    Log.d(TAG,"hook3成功");
                    param.args[0] = null;
                    super.beforeHookedMethod(param);
                }
            });

            XposedHelpers.findAndHookConstructor("com.bilibili.pegasus.card.banner.BannerV8Card$BannerV8Holder", loadPackageParam.classLoader, android.view.View.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    View view2 = (View)param.args[0];
                    int banid = 2131296911;
                    View v8Banner = view2.findViewById(banid);
                    v8Banner.setVisibility(View.GONE);
                    Log.d(TAG,"去除banner成功");
                    super.afterHookedMethod(param);
                }
            });

        }
    }
}
