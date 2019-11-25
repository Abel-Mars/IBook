package com.example.ibook;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class bookstore_frg extends Fragment {
    private WebView webview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view =inflater.inflate(R.layout.bookstore_frg,container,false);
        return view;
    }
    @Override
    public void onViewCreated(View view,Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        webview=(WebView)view.findViewById(R.id.web_view);
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setBuiltInZoomControls(true);
        settings.setSupportZoom(true);
        webview.loadUrl("https://m.bookben.net/");
        /*webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(!(url.startsWith("http")||url.startsWith("https"))){
                    return true;
                }
                return false;
            }
        });*/
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
            //网络请求部分
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.i("-->","地址："+url);
                url = url.toLowerCase();

                if(url.contains("https://m.bookben.net/")){
                    return super.shouldInterceptRequest(view, url);
                }else{
//去掉广告
                    return new WebResourceResponse(null,null,null); } }});
        webview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
                    if (webview!=null&&webview.canGoBack()){
                        webview.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
    }


    /*@Override
    public void onStart() {
        super.onStart();

    }*/
   /* @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WebView webView =(WebView)getActivity().findViewById(R.id.web_view);
        //webView.loadUrl("https://www.baidu.com/");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
    }*/

}
