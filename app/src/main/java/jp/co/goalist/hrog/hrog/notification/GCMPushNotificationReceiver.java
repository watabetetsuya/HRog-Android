package jp.co.goalist.hrog.hrog.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import jp.co.goalist.hrog.hrog.MainActivity;
import jp.co.goalist.hrog.hrog.R;

/**
 * Created by RicoShiota on 2015/11/06.
 */
public class GCMPushNotificationReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // 送られてきたデータを受け取る
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
        String messageType = gcm.getMessageType(intent);

        // 送られてきたデータのメッセージ
        Bundle extras = intent.getExtras();
        String mess = extras.toString();
        String msg = extras.getString("message");

        System.out.println("push通知が送信されてきました！！！");
        if (!extras.isEmpty()) {
            System.out.println("01");
            // エラー
            if (messageType
                    .equals(GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR)) {
                Log.d("PushTestApp", "MESSAGE_TYPE_SEND_ERROR：" + mess);
            }
            // サーバーでメッセージ削除
            else if (messageType
                    .equals(GoogleCloudMessaging.MESSAGE_TYPE_DELETED)) {
                Log.d("PushTestApp", "MESSAGE_TYPE_DELETED：" + mess);
            }
            // 正常に受信
            else if (messageType
                    .equals(GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE)) {
                System.out.println("02");
                Resources res = context.getResources();

                Notification n = new Notification(); // Notificationの生成
                //n.icon = R.drawable.ic_launcher; // アイコンの設定

                // 通知されたときに通知バーに表示される文章
                n.tickerText = mess + "(short)"; // メッセージの設定
                n.flags = Notification.FLAG_AUTO_CANCEL; // 通知を選択した時に自動的に通知が消えるための設定

                // 通常の着信音を選択する
                Uri uri = RingtoneManager
                        .getDefaultUri(RingtoneManager.TYPE_ALARM); // アラーム音
                n.sound = uri; // サウンド

                Intent i = new Intent(context, MainActivity.class);
                i.putExtra("MESS", mess);

                // Intent の作成
                PendingIntent contentIntent = PendingIntent.getActivity(
                        context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.app_icon);

                // NotificationBuilderを作成
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
                builder.setContentIntent(contentIntent);
                // ステータスバーに表示されるテキスト
                builder.setTicker("HRogの記事が更新されました");
                // アイコン
                builder.setSmallIcon(R.drawable.small_icon);
                // Notificationを開いたときに表示されるタイトル
                builder.setContentTitle("HRog");
                // Notificationを開いたときに表示されるサブタイトル
                builder.setContentText(msg);
                // Notificationを開いたときに表示されるアイコン
                builder.setLargeIcon(largeIcon);
                // 通知するタイミング
                builder.setWhen(System.currentTimeMillis());
                // 通知時の音・バイブ・ライト
                builder.setDefaults(Notification.DEFAULT_SOUND
                        //| Notification.DEFAULT_VIBRATE
                        | Notification.DEFAULT_LIGHTS);
                // タップするとキャンセル(消える)
                builder.setAutoCancel(true);

                // NotificationManagerを取得
                NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                // Notificationを作成して通知
                manager.notify(100, builder.build());
            }
        } else {
            System.out.println("03");
        }
    }
}
