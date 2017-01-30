package jp.co.goalist.hrog.hrog.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.webkit.WebView;
import android.widget.ImageButton;

import jp.co.goalist.hrog.hrog.DetailActivity;

/**
 * Created by RicoShiota on 2016/01/07.
 */
public class DetailPageWebView extends WebView {

    private ImageButton facebookBtn;
    private ImageButton twitterBtn;

    class ButtonAnimation extends Animation {
        int targetValue;
        View view;
        boolean flg;

        public ButtonAnimation(View view, int targetValue, boolean flg) {
            this.view = view;
            this.targetValue = targetValue;
            this.flg = flg;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            if (flg) {
                ((MarginLayoutParams)view.getLayoutParams()).bottomMargin = (int)(targetValue * interpolatedTime);
            } else {
                ((MarginLayoutParams)view.getLayoutParams()).bottomMargin =
                        (int)(targetValue - targetValue * interpolatedTime);
            }
            view.requestLayout();
        }

        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
        }

        @Override
        public boolean willChangeBounds() {
            return true;
        }
    }

    public ImageButton getFacebookBtn() {
        return facebookBtn;
    }

    public void setFacebookBtn(ImageButton facebookBtn) {
        this.facebookBtn = facebookBtn;
    }

    public ImageButton getTwitterBtn() {
        return twitterBtn;
    }

    public void setTwitterBtn(ImageButton twitterBtn) {
        this.twitterBtn = twitterBtn;
    }

    GestureDetector gd;
    DetailActivity activity = null;

    public void setActivity(DetailActivity activity) {
        this.activity = activity;
    }

    public DetailPageWebView(Context context) {
        super(context);
        gd = new GestureDetector(context,onGestureListner);
    }

    public DetailPageWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gd = new GestureDetector(context,onGestureListner);
    }

    public DetailPageWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        gd = new GestureDetector(context,onGestureListner);
    }

    private boolean buttonInView = true;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println(getScrollY());
        if(getScrollY() >= 700){
            if (buttonInView) {
                Animation a = new ButtonAnimation(twitterBtn, -80, true);
                a.setDuration(500);
                twitterBtn.startAnimation(a);
                Animation fa = new ButtonAnimation(facebookBtn, -80, true);
                fa.setDuration(500);
                facebookBtn.startAnimation(fa);
            }
            buttonInView = false;
        }
        if(getScrollY() < 700){
            if (!buttonInView) {
                Animation a = new ButtonAnimation(twitterBtn, -80, false);
                a.setDuration(500);
                twitterBtn.startAnimation(a);
                Animation fa = new ButtonAnimation(facebookBtn, -80, false);
                fa.setDuration(500);
                facebookBtn.startAnimation(fa);
            }
            buttonInView = true;
        }

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
                if(canGoBack()){
                 goBack();
                }else{
                    if(activity != null){
                        activity.closeDetailPage();
                    }
                }

            }else if(e1.getRawX() > e2.getRawX() && e1.getRawX() - e2.getRawX() > 250){//右から左へスライド（進む）
                goForward();
            }

            return super.onFling(e1, e2, velocityX, velocityY);
        }
    };


}
