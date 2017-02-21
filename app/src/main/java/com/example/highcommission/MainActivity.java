package com.example.highcommission;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.highcommission.config.NetConfig;
import com.example.highcommission.utils.UtilsInternet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, UtilsInternet.XCallBack {
    private Button mStart;
    private WebView mWB;
    private int page = 1;
    private int count = 0;
    private int record = 200;
    private List<String> mData = new ArrayList<>();
    private UtilsInternet internet = UtilsInternet.getInstance();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String s = mData.get(count);
            count++;
            record++;
            if (record % 200 == 0) {
                loadMore(page);
                return;
            }
            String path = String.format(NetConfig.UP_GENERALIZE, s);
            Log.i("TAG", "-------------count" + count);
            Log.i("TAG", "-------------record" + count);
            mWB.loadUrl(path);
            mHandler.sendEmptyMessageDelayed(1, 100);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initWebView();
        initData();
        setData();
        setListener();
    }


    private void initView() {
        mStart = (Button) findViewById(R.id.btn_start);
        mWB = (WebView) this.findViewById(R.id.wb);
    }

    private void initData() {
    }

    private void setData() {
    }

    private void setListener() {
        mStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                loadMore(1);
                break;
            default:
                break;
        }
    }


    private void initWebView() {
        mWB.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        WebSettings webSettings = mWB.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setLoadWithOverviewMode(true);
        mWB.loadUrl(NetConfig.DA_TAO_KE);
    }

    private void loadMore(int p) {
        String get_data = String.format(NetConfig.GET_DATA, p);
        internet.get(get_data, null, this);
    }

    @Override
    public void onResponse(String result) {
        try {
            page++;
            JSONObject object = new JSONObject(result);
            JSONArray array = object.getJSONArray("result");
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                mData.add(jsonObject.getString("ID"));
            }
            mHandler.sendEmptyMessageDelayed(1, 1000);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
