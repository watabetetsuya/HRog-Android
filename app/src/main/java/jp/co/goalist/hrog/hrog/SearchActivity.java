package jp.co.goalist.hrog.hrog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final WebView wv = (WebView) this.findViewById(R.id.search_result);

        final TextView text = (TextView)findViewById(R.id.text_form);
        final InputMethodManager inputMethodManager =  (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        text.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //イベントを取得するタイミングには、ボタンが押されてなおかつエンターキーだったときを指定
                System.out.println("test");
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    wv.loadUrl("https://app.hrog.net/?s=" + text.getText());
                    //キーボードを閉じる
                    inputMethodManager.hideSoftInputFromWindow(text.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    return true;
                }

                return false;
            }
        });
    }

    public void onClickSearchTextDeleteBtn(View v){
        TextView text = (TextView)findViewById(R.id.text_form);
        text.setText("");
    }

    public void onClickBackBtnFrmSearch(View v){
        Intent i = new Intent(SearchActivity.this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
    }

}
