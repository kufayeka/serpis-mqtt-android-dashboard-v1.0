package com.serpis.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.serpis.R;
import com.serpis.fragment.ConnectToMqttFragment;
import com.serpis.fragment.Greenhouse1Fragment;
import com.serpis.fragment.Greenhouse2Fragment;
import com.serpis.fragment.HomeFragment;
import com.serpis.fragment.InfoFragment;
import com.serpis.fragment.KontrolFragment;
import com.serpis.fragment.MediaTanahFragment;

public class MainActivity extends AppCompatActivity {

    FragmentContainerView frameLayout;
    RelativeLayout connectedNotificationBar;
    RelativeLayout disconnectedNotificatioBar;

    final Fragment fragmentHome = new HomeFragment();
    final Fragment fragmentKontrol = new KontrolFragment();
    final Fragment fragmentInfo = new InfoFragment();
    final Fragment fragmentGreenhouse1 = new Greenhouse1Fragment();
    final Fragment fragmentGreenhouse2 = new Greenhouse2Fragment();
    final Fragment fragmentMediaTanah = new MediaTanahFragment();
    final Fragment fragmentConnectToMqtt = new ConnectToMqttFragment();

    final FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragmentHome;

    BottomNavigationView bottomNavigationView;

    private static final int delay = 200;

    Handler h = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_main);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_bar_bottom);
        frameLayout = (FragmentContainerView) findViewById(R.id.main_frame);
        connectedNotificationBar = (RelativeLayout) findViewById(R.id.mainActivityConnectedNotificationBar);
        disconnectedNotificatioBar = (RelativeLayout) findViewById(R.id.mainActivityDisconnectedNotificationBar);

    }

    @Override
    protected void onStart() {
        super.onStart();

        fm.beginTransaction()
                .add(R.id.main_frame, fragmentInfo, "3")
                .setMaxLifecycle(fragmentInfo, Lifecycle.State.INITIALIZED)
                .hide(fragmentInfo)
                .commit();
        fm.beginTransaction()
                .add(R.id.main_frame, fragmentKontrol, "2")
                .setMaxLifecycle(fragmentKontrol, Lifecycle.State.INITIALIZED)
                .hide(fragmentKontrol)
                .commit();
        fm.beginTransaction()
                .add(R.id.main_frame, fragmentGreenhouse1, "5")
                .setMaxLifecycle(fragmentGreenhouse1, Lifecycle.State.INITIALIZED)
                .hide(fragmentGreenhouse1)
                .commit();
        fm.beginTransaction()
                .add(R.id.main_frame, fragmentGreenhouse2, "6")
                .setMaxLifecycle(fragmentGreenhouse2, Lifecycle.State.INITIALIZED)
                .hide(fragmentGreenhouse2)
                .commit();
        fm.beginTransaction()
                .add(R.id.main_frame, fragmentMediaTanah, "7")
                .setMaxLifecycle(fragmentMediaTanah, Lifecycle.State.INITIALIZED)
                .hide(fragmentMediaTanah)
                .commit();
        fm.beginTransaction()
                .add(R.id.main_frame, fragmentHome, "1")
                //.hide(active)
                .commit();

        bottomNavigationView.setOnNavigationItemSelectedListener
                (new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.homeID:
                                h.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        fm.beginTransaction()
                                                .hide(active)
                                                .show(fragmentHome)
                                                .setMaxLifecycle(fragmentHome, Lifecycle.State.RESUMED)
                                                .commit();
                                        active = fragmentHome;
                                    }
                                }, delay);
                                return true;

                            case R.id.kontrolID:
                                h.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        fm.beginTransaction()
                                                .hide(active)
                                                .show(fragmentKontrol)
                                                .setMaxLifecycle(fragmentKontrol, Lifecycle.State.RESUMED)
                                                .commit();
                                        active = fragmentKontrol;
                                    }
                                }, delay);
                                return true;

                            case R.id.infoID:
                                h.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        fm.beginTransaction()
                                                .hide(active)
                                                .show(fragmentInfo)
                                                .setMaxLifecycle(fragmentInfo, Lifecycle.State.RESUMED)
                                                .commit();
                                        active = fragmentInfo;
                                    }
                                }, delay);
                                return true;
                        }
                        return false;
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void showConnectedNotificationBar(){
        connectedNotificationBar.setVisibility(View.VISIBLE);
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                connectedNotificationBar.setVisibility(View.GONE);
            }
        }, 1000);
    }

    public void showDisconnectedNotificationBar(){
        disconnectedNotificatioBar.setVisibility(View.VISIBLE);
    }

    public void changeToGreenhouse1Fragment(){
        fm.beginTransaction()
                .setMaxLifecycle(active, Lifecycle.State.STARTED)
                .hide(active)
                .show(fragmentGreenhouse1)
                .setMaxLifecycle(fragmentGreenhouse1, Lifecycle.State.RESUMED)
                .commit();
        active = fragmentGreenhouse1;
    }

    public void changeToGreenhouse2Fragment(){
        fm.beginTransaction()
                .setMaxLifecycle(active, Lifecycle.State.STARTED)
                .hide(active)
                .show(fragmentGreenhouse2)
                .setMaxLifecycle(fragmentGreenhouse2, Lifecycle.State.RESUMED)
                .commit();
        active = fragmentGreenhouse2;
    }

    public void changeToMediaTanahFragment(){
        fm.beginTransaction()
                .setMaxLifecycle(active, Lifecycle.State.STARTED)
                .hide(active)
                .show(fragmentMediaTanah)
                .setMaxLifecycle(fragmentMediaTanah, Lifecycle.State.RESUMED)
                .commit();
        active = fragmentMediaTanah;
    }

    public void backToHomeFragment(){
        fm.beginTransaction()
                .hide(active)
                .show(fragmentHome)
                .commit();
        active = fragmentHome;
    }

    public void reloadHomeActivity(){
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    long backPressed;

    @Override
    public void onBackPressed() {
        if (backPressed + 1000 > System.currentTimeMillis()){
            super.onBackPressed();
        } else if(fragmentHome.isHidden() && backPressed + 1000 < System.currentTimeMillis()) { } else {
            Toast.makeText(getBaseContext(), "Klik sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();
        }
        backPressed = System.currentTimeMillis();
    }

}