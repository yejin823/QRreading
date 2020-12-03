package com.example.qr_reading.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.qr_reading.activity.MainActivity;
import com.example.qr_reading.fragment.FragMessage;
import com.example.qr_reading.fragment.FragScan;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.fragment.app.Fragment;

import com.example.qr_reading.R;

import static androidx.core.content.ContextCompat.getSystemService;

public class FragScan extends Fragment implements View.OnClickListener {
    WebView wv;
    EditText et;
    Button bt;
    IntentIntegrator integrator ;
    public FragScan() {

    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.frag_scan, container, false);

        LinearLayout layout1 = root.findViewById(R.id.btn);
        layout1.setOnClickListener(this);
        return root;
    }

    /*
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_scan, container, false);
    } */

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);

        et = view.findViewById(R.id.editT);
        wv = view.findViewById(R.id.wv);
        bt = view.findViewById(R.id.btn);
        WebSettings webSettings = wv.getSettings();

        //자바 스크립트 사용을 할 수 있도록 합니다.
        webSettings.setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient(){
            //페이지 로딩이 끝나면 호출됩니다.
            @Override public void onPageFinished(WebView view,String url){
                Toast.makeText(getActivity(),"로딩 완료", Toast.LENGTH_SHORT).show();
            }
        });
        et.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    //bt의 onClick을 실행
                    bt.callOnClick();

                    //키보드 숨기기
                    InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                    return true;
                }
                return false;
            }
        });
        integrator = new IntentIntegrator(getActivity());

        //바코드 안의 텍스트
        integrator.setPrompt("바코드를 사각형 안에 비춰주세요");

        //바코드 인식시 소리 여부
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);

        //바코드 스캐너 시작
        integrator.initiateScan();
    }

    public void onClick(View view){
        String address = et.getText().toString();

        if(!address.startsWith("http://")){
            address = "http://" + address;
        }
        wv.loadUrl(address);
    }
    /*
        public void onBackPressed() {
            if(wv.isActivated()){
                if(wv.canGoBack()){
                    wv.goBack();
                }else{
                    //스캐너 재시작
                    integrator.initiateScan();
                }
            } else{
                super.onBackPressed();
            }
        }*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() == null){

            }else{
                //qr코드를 읽어서 EditText에 입력해줍니다.
                et.setText(result.getContents());

                //Button의 onclick호출
                bt.callOnClick(); Toast.makeText(getActivity(), "Scanned: " + result.getContents(), Toast.LENGTH_SHORT).show();
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
