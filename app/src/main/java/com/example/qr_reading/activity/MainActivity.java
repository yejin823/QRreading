package com.example.qr_reading.activity;

import androidx.annotation.IdRes;
import com.example.qr_reading.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.fragment.app.FragmentManager;
import com.roughike.bottombar.OnTabSelectListener;

import com.example.qr_reading.fragment.FragMessage;
import com.example.qr_reading.fragment.FragScan;

import com.google.zxing.integration.android.IntentIntegrator;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {

    //FragmentManager가 있어야 FragmentTransaction을 불러올 수 있다.
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;

    //각 Fragment 불러오기
    FragScan fragScan = new FragScan();
    FragMessage fragMessage = new FragMessage();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FragmentManager는 Activity에서 제공.
        fragmentManager = getSupportFragmentManager();
    }

    public void changeFragment(int id) {
        //Trasaction 구하기
        //Transaction Manager는 매번 해야 된다.
        fragmentTransaction = fragmentManager.beginTransaction();

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                if (tabId == R.id.tab_scan) {
                    fragmentTransaction.replace(R.id.contentContainer, fragScan).commit();
                } else if (tabId == R.id.tab_message) {
                    fragmentTransaction.replace(R.id.contentContainer, fragMessage).commit();
                }
            }
        });
    }



    public void onClick(View view) {
        changeFragment(view.getId());
    }
}
