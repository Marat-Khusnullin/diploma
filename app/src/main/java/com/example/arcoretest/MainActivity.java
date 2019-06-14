package com.example.arcoretest;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;



public class MainActivity extends AppCompatActivity {
    private static final String TAG = "666";
    private static final double MIN_OPENGL_VERSION = 3.0;
    private double mLant = 55.790612;
    private double mLong = 49.123078;
    private double pointLant = 55.741623;
    private double pointLong = 49.221095;
    private double northDif;
    private double coordNorth;
    private double longDif;
    private double coordLong;
    private float zCoord;
    private Anchor anchor;
    private BottomBar bottomBar;

    Context context = this;

    private static final double LATITUDE_COEF = 111134.861111;
    private static final double LONGITUDE_COEF = 111321.377778;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!checkIsSupportedDeviceOrFinish(this))
            return;

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_geo) {
                    GeoFragment fragment = new GeoFragment();
                    getSupportFragmentManager().beginTransaction().add(R.id.main_frame, fragment).commit();
                }

                if (tabId == R.id.tab_main) {
                    ArObjectsFragment arObjectsFragment = new ArObjectsFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_frame, arObjectsFragment).commit();


                }
            }
        });

    }

    public static boolean checkIsSupportedDeviceOrFinish(final Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            Log.e(TAG, "Sceneform requires Android N or later");
            Toast.makeText(activity, "Sceneform requires Android N or later", Toast.LENGTH_LONG).show();
            activity.finish();
            return false;
        }
        String openGlVersionString =
                ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo()
                        .getGlEsVersion();
        if (Double.parseDouble(openGlVersionString) < MIN_OPENGL_VERSION) {
            Log.e(TAG, "Sceneform requires OpenGL ES 3.0 later");
            Toast.makeText(activity, "Sceneform requires OpenGL ES 3.0 or later", Toast.LENGTH_LONG)
                    .show();
            activity.finish();
            return false;
        }
        return true;
    }
}
