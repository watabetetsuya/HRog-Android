package jp.co.goalist.hrog.hrog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import java.util.ServiceConfigurationError;

import jp.co.goalist.hrog.hrog.component.HRogWebView;
import jp.co.goalist.hrog.hrog.component.HRogWebViewClient;
import jp.co.goalist.hrog.hrog.user.model.AuthTokenModel;
import jp.co.goalist.hrog.hrog.user.service.UserRegisterService;
import jp.co.goalist.hrog.hrog.user.service.UserService;

public class MainActivity extends AppCompatActivity {
    private HRogWebView webView = null;
    private Map<MenuTabButtonEnum, ImageButton> imageButtonMap = new EnumMap<MenuTabButtonEnum, ImageButton>(MenuTabButtonEnum.class);
    private Map<MenuTabButtonEnum, BtnResource> btnResourceMap = new EnumMap<MenuTabButtonEnum, BtnResource>(MenuTabButtonEnum.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView reloadText = null;

        ProgressDialog pd = new ProgressDialog(this);
        // インジケータのメッセージ
        pd.setMessage("Loading...");

        webView = (HRogWebView)this.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setReloadText(reloadText);
        webView.setWebViewClient(new HRogWebViewClient(pd, reloadText, this));
        webView.loadUrl("http://app.hrog.net");
        webView.setMainActivity(this);

        imageButtonMap.put(MenuTabButtonEnum.TOP, (ImageButton) this.findViewById(R.id.top_btn));
        imageButtonMap.put(MenuTabButtonEnum.SPECIAL, (ImageButton) this.findViewById(R.id.special_btn));
        imageButtonMap.put(MenuTabButtonEnum.REPORT, (ImageButton) this.findViewById(R.id.report_btn));
        imageButtonMap.put(MenuTabButtonEnum.IR, (ImageButton) this.findViewById(R.id.ir_btn));
        imageButtonMap.put(MenuTabButtonEnum.RELEASE, (ImageButton) this.findViewById(R.id.release_btn));

        btnResourceMap.put(MenuTabButtonEnum.TOP, new BtnResource(R.drawable.app_top_normal,R.drawable.app_top,"http://app.hrog.net"));
        btnResourceMap.put(MenuTabButtonEnum.SPECIAL, new BtnResource(R.drawable.app_tokusyu_normal, R.drawable.app_tokusyu,"http://app.hrog.net/category/matome"));
        btnResourceMap.put(MenuTabButtonEnum.REPORT, new BtnResource(R.drawable.app_report_normal,R.drawable.app_report,"http://app.hrog.net/category/report"));
        btnResourceMap.put(MenuTabButtonEnum.IR, new BtnResource(R.drawable.app_ir_normal,R.drawable.app_ir,"http://app.hrog.net/category/ir"));
        btnResourceMap.put(MenuTabButtonEnum.RELEASE, new BtnResource(R.drawable.app_release_normal,R.drawable.app_release,"http://app.hrog.net/category/press"));


        registerId();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    public void showMenu(View v){
        Intent i = new Intent(MainActivity.this, SettingActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
    }

    public void registerId() {
        final Context ctx = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(ctx);
                try {
                    SharedPreferences p = ctx.getSharedPreferences("pref", MODE_PRIVATE);
                    String id = p.getString("uid",null);
                    String pw = p.getString("password",null);
                    if(id==null) {
                        String registerId = gcm.register(UserService.PROJECT_NUMBER);
                        AuthTokenModel model = new UserRegisterService().register(registerId);
                        if (model != null) {
                            SharedPreferences.Editor e = p.edit();
                            e.putString("uid", model.getUserId());
                            e.putString("password", model.getPassword());
                            e.commit();
                        }
                        System.out.println("Register ID:" + registerId);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private MenuTabButtonEnum currentTabEnum = MenuTabButtonEnum.TOP;

    class BtnResource{
        int selected;
        int normal;
        String url;

        public BtnResource(int normal, int selected, String url) {
            this.normal = normal;
            this.selected = selected;
            this.url = url;
        }
    }

    private void selectBtn(MenuTabButtonEnum nextTabEnum) {
        ImageButton current = imageButtonMap.get(currentTabEnum);
        ImageButton next = imageButtonMap.get(nextTabEnum);

        next.setImageResource(btnResourceMap.get(nextTabEnum).selected);
        current.setImageResource(btnResourceMap.get(currentTabEnum).normal);
        currentTabEnum=nextTabEnum;

        webView.loadUrl(btnResourceMap.get(currentTabEnum).url);
    }

    public void moveToNextTab(){
        int index = MenuTabButtonEnum.indexOfEnum(currentTabEnum);
        MenuTabButtonEnum nextTab = MenuTabButtonEnum.enumOfIndex(index + 1);
        if(nextTab!=null){
            selectBtn(nextTab);
        }
    }

    public void moveToPrevTab() {
        int index = MenuTabButtonEnum.indexOfEnum(currentTabEnum);
        MenuTabButtonEnum prevTab = MenuTabButtonEnum.enumOfIndex(index - 1);
        if(prevTab!=null){
            selectBtn(prevTab);
        }
    }

    public void onClickTopBtn(View v){
        selectBtn(MenuTabButtonEnum.TOP);

    }



    public void onClickSpecialBtn(View v){
        selectBtn(MenuTabButtonEnum.SPECIAL);
    }

    public void onClickReportBtn(View v){
        selectBtn(MenuTabButtonEnum.REPORT);
    }

    public void onClickIrBtn(View v){
        selectBtn(MenuTabButtonEnum.IR);
    }

    public void onClickReleaseBtn(View v){
        selectBtn(MenuTabButtonEnum.RELEASE);
    }

    public void onClickSearchBtn(View v){
        Intent i = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
    }
}
