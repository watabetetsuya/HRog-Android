package jp.co.goalist.hrog.hrog.component;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import jp.co.goalist.hrog.hrog.DetailActivity;
import jp.co.goalist.hrog.hrog.MainActivity;

/**
 * Created by RicoShiota on 2015/12/23.
 */
public class HRogWebViewClient extends WebViewClient {
    ProgressDialog pdLog;
    TextView reloadText;
    boolean loading = false;
    MainActivity activity;

    public HRogWebViewClient(ProgressDialog pd,TextView reloadText, MainActivity activity) {
        super();
        pdLog = pd;
        this.activity = activity;
        //this.reloadText = reloadText;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        Intent i = new Intent(activity, DetailActivity.class);
        i.putExtra("URL", url);
        activity.startActivity(i);
        activity.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        pdLog.show();
        loading = true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        pdLog.dismiss();
        //this.reloadText.setVisibility(View.GONE);
        loading = false;
    }

    public boolean isLoading() {
        return loading;
    }
}
