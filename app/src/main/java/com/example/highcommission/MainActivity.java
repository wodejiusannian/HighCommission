package com.example.highcommission;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.highcommission.bean.JavaBean;
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
    private List<JavaBean> mData = new ArrayList<>();
    private UtilsInternet internet = UtilsInternet.getInstance();
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            JavaBean bean = mData.get(count);
            String s = bean.getJihua_link();
            String id = bean.getID();
            count++;
            record++;
            if (record % 200 == 0) {
                loadMore(page);
                return;
            }
            if (!TextUtils.isEmpty(s)) {
                String path = String.format(NetConfig.UP_GENERALIZE, id);
                Log.i("TAG", "----------PAHT" + path);
                mWB.loadUrl(path);
            }
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
                JavaBean bean = new JavaBean();
                bean.setID(jsonObject.getString("ID"));
                bean.setJihua_link(jsonObject.getString("Jihua_link"));
                mData.add(bean);
            }
            mHandler.sendEmptyMessageDelayed(1, 1000);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
