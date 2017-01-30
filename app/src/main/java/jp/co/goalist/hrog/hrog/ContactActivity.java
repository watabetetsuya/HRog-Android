package jp.co.goalist.hrog.hrog;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        WebView wv = (WebView)this.findViewById(R.id.webView_layout);
        wv.loadUrl("https://app.hrog.net/app-contact-html");
    }

    public void onClickBackBtn(View v){
        Intent i = new Intent(ContactActivity.this, SettingActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
    }

}
