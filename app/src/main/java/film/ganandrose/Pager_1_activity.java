package film.ganandrose;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.util.List;

import javabean.BaiSiBuDeQiJieBean;

/**
 * Created by p on 2016/11/12.
 */
public class Pager_1_activity extends AppCompatActivity {

    public WebView webView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pager_1_activity);
        webView = (WebView) findViewById(R.id.webView);

        Intent intent = this.getIntent();
        Bundle bd = intent.getExtras();
        BaiSiBuDeQiJieBean  bsdqj = (BaiSiBuDeQiJieBean) bd.getSerializable("films");
        String url = bsdqj.getWeixin_url();
        System.out.println("===========你好，我是WEb_view_网址======="+url);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });


    }
}