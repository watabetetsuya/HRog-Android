package jp.co.goalist.hrog.hrog.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import jp.co.goalist.hrog.hrog.MainActivity;

/**
 * Created by RicoShiota on 2015/11/29.
 */
public class HRogWebView extends WebView{
    GestureDetector gd;

    public HRogWebView(Context context) {
        super(context);
        gd = new GestureDetector(context,onGestureListner);
    }

    public HRogWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gd = new GestureDetector(context,onGestureListner);
    }

    public HRogWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        gd = new GestureDetector(context,onGestureListner);
    }

    private int prevY = -2;
    private int prevX = 0;
    private int counter = 0;
    private TextView reloadText;
    private WebViewClient client;
    private MainActivity mainActivity;

    public TextView getReloadText() {
        return reloadText;
    }

    public void setReloadText(TextView reloadText) {
        this.reloadText = reloadText;
    }

    @Override
    public void setWebViewClient(WebViewClient client) {
        super.setWebViewClient(client);
        this.client = client;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean flag = gd.onTouchEvent(event);
        if (flag) {
            return flag;
        }else{
            return super.onTouchEvent(event);
        }
    }
    private GestureDetector.SimpleOnGestureListener onGestureListner = new GestureDetector.SimpleOnGestureListener(){
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e1.getRawX() < e2.getRawX() && e2.getRawX() - e1.getRawX() > 250){//左から右へスライド（戻る）
                System.out.println("左から右");
                mainActivity.moveToPrevTab();

            }else if(e1.getRawX() > e2.getRawX() && e1.getRawX() - e2.getRawX() > 250){//右から左へスライド（進む）
                System.out.println("右から左");
                mainActivity.moveToNextTab();
            }


            if (getScrollY() >= 10) {
                return super.onFling(e1, e2, velocityX, velocityY);
            }

            if(e1.getRawY() < e2.getRawY() && e2.getRawY() - e1.getRawY() > 250){//左から右へスライド（戻る）
               reload();
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    };

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
}
