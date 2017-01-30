package jp.co.goalist.hrog.hrog;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import jp.co.goalist.hrog.hrog.component.DetailPageWebView;

public class DetailActivity extends AppCompatActivity {
    private String url;
    private DetailPageWebViewClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        final DetailPageWebView wv2 = (DetailPageWebView)this.findViewById(R.id.webView2);

        wv2.setFacebookBtn((ImageButton)findViewById(R.id.facebook_btn));
        wv2.setTwitterBtn((ImageButton)findViewById(R.id.twitter_btn));

        Intent i = getIntent();
        this.url = i.getStringExtra("URL");
        ProgressDialog pd = new ProgressDialog(this);
        // インジケータのメッセージ
        pd.setMessage("Loading...");
        wv2.setActivity(this);

        this.client = new DetailPageWebViewClient(pd);
        wv2.setWebViewClient(client);
        wv2.loadUrl(this.url);
        //final String title = wv2.getTitle();


        ImageButton twitterBtn = (ImageButton)this.findViewById(R.id.twitter_btn);
        twitterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                if(url.contains("app.hrog.net")){
                    url = url.replaceAll("app.hrog.net/","hrog.net/");
                }
                String message = client.firstTitle + " " + url;
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT,  message);
                PackageManager packManager = getPackageManager();
                List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                boolean resolved = false;
                for(ResolveInfo resolveInfo: resolvedInfoList){
                    if(resolveInfo.activityInfo.name.equals("com.twitter.android.composer.ComposerActivity")){
                        intent.setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name );
                        resolved = true;
                        break;
                    }
                }
                if(resolved) {
                    startActivity(intent);
                }else{
                    Intent i = new Intent();
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_TEXT, message);
                    i.setAction(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://twitter.com/intent/tweet?text=" + urlEncode(message)));
                    startActivity(i);
                }
            }
        });

        ImageButton facebookBtn = (ImageButton)this.findViewById(R.id.facebook_btn);
        facebookBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                if(url.contains("app.hrog.net")){
                    url = url.replaceAll("app.hrog.net/","hrog.net/");
                }
                intent.putExtra(Intent.EXTRA_TEXT, url);
                PackageManager packManager = getPackageManager();
                List<ResolveInfo> resolvedInfoList = packManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                boolean resolved = false;
                for(ResolveInfo resolveInfo: resolvedInfoList){
                    if(resolveInfo.activityInfo.name.startsWith("com.facebook.composer.shareintent")){
                        intent.setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
                        resolved = true;
                        break;
                    }
                }
                if(resolved){
                    startActivity(intent);
                }else{
                    Intent i = new Intent();
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_TEXT, url);
                    i.setAction(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("https://www.facebook.com/sharer/sharer.php?u=" + urlEncode(url)));
                    startActivity(i);
                }
            }
        });

        ImageButton topBtn = (ImageButton)this.findViewById(R.id.top);
        topBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailActivity.this, MainActivity.class);
                startActivity(i);
                overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
            }
        });
    }

    private String urlEncode(String s) {
        try {
            return URLEncoder.encode(s, "UTF-8");
        }catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    public void closeDetailPage() {
        Intent i = new Intent(DetailActivity.this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }

    class DetailPageWebViewClient extends WebViewClient{
        ProgressDialog pdLog;
        String firstTitle = null;
        public DetailPageWebViewClient(ProgressDialog pd) {
            super();
            pdLog = pd;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            pdLog.show();
        }
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(firstTitle == null) {
                firstTitle = view.getTitle();
            }
            pdLog.dismiss();
        }
    }

    public void onClickShareBtn (View v) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        String message = client.firstTitle + " " + this.url;
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,  message);
        startActivity(intent);
    }
}
