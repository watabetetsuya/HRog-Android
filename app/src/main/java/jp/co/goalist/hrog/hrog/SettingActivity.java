package jp.co.goalist.hrog.hrog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Switch;

import jp.co.goalist.hrog.hrog.user.model.AuthTokenModel;
import jp.co.goalist.hrog.hrog.user.model.UserModel;
import jp.co.goalist.hrog.hrog.user.service.UserInfoService;
import jp.co.goalist.hrog.hrog.user.service.UserTokenService;

public class SettingActivity extends AppCompatActivity {
    ProgressDialog pd = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        pd = new ProgressDialog(this);
        // インジケータのメッセージ
        pd.setMessage("Loading...");
        pd.show();
        final Context ctx = this;
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserInfoService service = new UserInfoService();
                UserTokenService uts = new UserTokenService();
                AuthTokenModel model =uts.getSavedToken(ctx);
                final UserModel um = service.getUserData(model);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ((SettingActivity) ctx).switchSettings(um);
                    }
                });
            }
        }).start();

    }

    public void switchSettings(UserModel um) {
        final Switch morning = (Switch)findViewById(R.id.morning);
        final Switch noon = (Switch)findViewById(R.id.noon);
        final Switch evening = (Switch)findViewById(R.id.evening);
        final Switch nonperiodic = (Switch)findViewById(R.id.nonperiodic);
        morning.setChecked(um.isNotifyAtMorning());
        noon.setChecked(um.isNotifyAtNoon());
        evening.setChecked(um.isNotifyAtEvening());
        nonperiodic.setChecked(um.isNotifyForNonPeriodic());
        pd.dismiss();

    }

    public void onReturnBtnClick(View v){
        Intent i = new Intent(SettingActivity.this, MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.abc_slide_in_top, R.anim.abc_slide_out_bottom);
    }

    public void onClickSwitch(View v){
        final Switch morning = (Switch)findViewById(R.id.morning);
        final Switch noon = (Switch)findViewById(R.id.noon);
        final Switch evening = (Switch)findViewById(R.id.evening);
        final Switch nonperiodic = (Switch)findViewById(R.id.nonperiodic);
        final UserModel um = new UserModel(morning.isChecked(), noon.isChecked(), evening.isChecked(),nonperiodic.isChecked());
        System.out.println("morning : " + morning.isChecked());
        System.out.println("noon : " + noon.isChecked());
        System.out.println("evening : " + evening.isChecked());
        System.out.println("nonperiodic : " + nonperiodic.isChecked());

        final Context ctx = this;
        new Thread(new Runnable() {
            @Override
            public void run() {
                UserInfoService userInfoService = new UserInfoService();
                AuthTokenModel model = new UserTokenService().getSavedToken(ctx);
                userInfoService.updateUserData(model, um);
            }
        }).start();
    }

    public void onClickContactBtn(View v){
        Intent i = new Intent(SettingActivity.this, ContactActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.abc_slide_in_bottom, R.anim.abc_slide_out_top);
    }

}
