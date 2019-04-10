package org.godotengine.godot;

import android.app.Activity;
import android.content.Intent;
import com.godot.game.R;
import javax.microedition.khronos.opengles.GL10;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdListener;
import com.google.ads.mediation.admob.AdMobAdapter;
import android.util.Log;

public class MythicAdmob extends Godot.SingletonBase {

    protected Activity appActivity;
    protected Context appContext;
    private int instanceId = 0;

    private InterstitialAd mInterstitialAd;
    Bundle extras;
    private String admobUnitId = "";
    private String admobAppId = "";

    public void initMythicAdmob(String admob_app_id, String ad_unit, int instance_id)
    {
        instanceId = instance_id;
        admobAppId = admob_app_id;
        admobUnitId = ad_unit;
        extras = new Bundle();
        extras.putString("max_ad_content_rating", "G");
    }

    public void loadInterstitialAd()
    {
        appActivity.runOnUiThread(new Runnable() {
                public void run() {
                    MobileAds.initialize(appActivity, admobAppId);
                    mInterstitialAd = new InterstitialAd(appActivity);
                    mInterstitialAd.setAdUnitId(admobUnitId);
                    mInterstitialAd.setAdListener(new AdListener() {
                    @Override
                    public void onAdClosed() {
                        // Load the next interstitial.
                        mInterstitialAd.loadAd(new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, extras).build());
                    }
                    @Override
                    public void onAdLoaded() {
                        Log.d("MythicAdmob", "The interstitial is ready.");
                    }

                    @Override
                    public void onAdFailedToLoad(int errorCode) {
                        Log.d("MythicAdmob", "The interstitial failed to loaded.");
                        mInterstitialAd.loadAd(new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, extras).build());
                    }

                    @Override
                    public void onAdOpened() {
                        // Code to be executed when the ad is displayed.
                    }

                    @Override
                    public void onAdLeftApplication() {
                        // Code to be executed when the user has left the app.
                    }
                });
                mInterstitialAd.loadAd(new AdRequest.Builder().addNetworkExtrasBundle(AdMobAdapter.class, extras).build());
                }
        });
    }

    public void showInterstitialAd()
    {
        appActivity.runOnUiThread(new Runnable() {
                public void run() {
                    if (mInterstitialAd.isLoaded()) {
                        mInterstitialAd.show();
                    } else {
                        Log.d("MythicAdmob", "The interstitial wasn't loaded yet.");
                    }
                }
        });
    }

    static public Godot.SingletonBase initialize(Activity p_activity) {
        return new MythicAdmob(p_activity);
    }

    public MythicAdmob(Activity p_activity) {
        //register class name and functions to bind
        registerClass("MythicAdmob", new String[]
            {
                "initMythicAdmob",
                "loadInterstitialAd",
                "showInterstitialAd"
            });
        this.appActivity = p_activity;
        this.appContext = appActivity.getApplicationContext();
        // you might want to try initializing your singleton here, but android
        // threads are weird and this runs in another thread, so to interact with Godot you usually have to do
    }

    // forwarded callbacks you can reimplement, as SDKs often need them

    protected void onMainActivityResult(int requestCode, int resultCode, Intent data) {}
    protected void onMainRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {}

    protected void onMainPause() {}
    protected void onMainResume() {}
    protected void onMainDestroy() {}

    protected void onGLDrawFrame(GL10 gl) {}
    protected void onGLSurfaceChanged(GL10 gl, int width, int height) {} // singletons will always miss first onGLSurfaceChanged call

}