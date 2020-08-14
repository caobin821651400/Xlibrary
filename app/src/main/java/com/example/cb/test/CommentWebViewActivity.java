package com.example.cb.test;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.cb.test.base.BaseActivity;

import cn.sccl.xlibrary.utils.XLogUtils;


/**
 * 公用WebView
 */
public class CommentWebViewActivity extends BaseActivity {

    private WebView mWebView;
    private WebSettings webSettings;
    private String url;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_device_web_view;
    }

    @Override
    public void initUI() {
//        url = getIntent().getStringExtra("url");
        url = "";
        mWebView = findViewById(R.id.mWebView);
        getInternet();
    }

    @Override
    protected void initEvent() {

    }

    /**
     * 通过接口，获取网页
     */
    private void getInternet() {
        webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);  //加上这一行网页为响应式的
        webSettings.setDefaultTextEncodingName("utf-8");//设置字符编码
        webSettings.setDomStorageEnabled(true);//主要是这句,网址加载空白的情况
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        XLogUtils.e(url);
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);

            }
        });
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                view.loadUrl(url);
                return true;   //返回true， 立即跳转，返回false,打开网页有延时
            }

            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return shouldOverrideUrlLoading(view, request.getUrl().toString());
            }
        });
        mWebView.loadUrl(url);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        //在 Activity 销毁（ WebView ）的时候，先让 WebView 加载null内容，然后移除 WebView，再销毁 WebView，最后置空
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
        super.onDestroy();
    }
}
