package net.lzzy.practicesonline.activities.network;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;


import androidx.annotation.Nullable;


import net.lzzy.practicesonline.activities.activites.PracticesActivity;

import net.lzzy.practicesonline.activities.models.Practice;
import net.lzzy.practicesonline.activities.utlis.AppUtils;


import java.util.List;

/**
 *
 * @author lzzy_gxy
 * @date 2019/4/28
 * Description:
 *            ①创建服务类继承Service，并在Manifest中声明Service组件
 */

@SuppressLint("Registered")
public class DetectWebService extends Service {


    public static final String EXTRA_REFRESH = "extraRefresh";
    private int localCount;
    private NotificationManager manager;
    public static final int NOTIFICATION_DETECT_ID = 3;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        localCount=intent.getIntExtra(PracticesActivity.EXTRA_LOCAL_COUNT,0);
        return new DetectWebBinder();
    }

    /**②在服务类中创建Binder类（与Activity通信），在onBind中返回Binder对象**/
    public class DetectWebBinder extends Binder{

        public static final int FLAG_DATA_CHANGED = 1;
        public static final int FLAG_SERVER_EXCEPTION = 0;
        public static final int FLAG_DATA_SANE = 2;


        /**③在Binder类中添加执行后台任务的方法**/
        /**检测远程服务器是否有更新，有则提示可更新数据**/
        public void detect(){
            AppUtils.getExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    int flag=compareData();
                    if (flag== FLAG_SERVER_EXCEPTION){
                        notifyUser("服务器无法连接",android.R.drawable.ic_menu_compass,false);
                    }else if(flag== FLAG_DATA_CHANGED){
                        notifyUser("远程服务器有更新",android.R.drawable.ic_popup_sync,true);
                    }else {
                       if (manager!=null){
                           manager.cancel(NOTIFICATION_DETECT_ID);
                       }

                    }
                }
            });
        }

        /**
         * 构建状态通知栏
         * @param info 通知内容
         * @param icon 图标
         * @param refresh  通知类型
         */
        private void notifyUser(String info, int icon, boolean refresh) {
            /** 点击通知栏的时候传refresh到PracticesActivity并且延迟跳转到PracticesActivity**/
            Intent intent=new Intent(DetectWebService.this,PracticesActivity.class);
            intent.putExtra(EXTRA_REFRESH,refresh);
            PendingIntent pendingIntent=PendingIntent.getActivity(DetectWebService.this,
                    0,intent,PendingIntent.FLAG_ONE_SHOT);
            /****/
            manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification;
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                notification = new Notification.Builder(DetectWebService.this,"0")
                        .setContentTitle("检测远程服务器")
                        .setContentText(info)
                        .setSmallIcon(icon)
                        .setContentIntent(pendingIntent)
                        .setWhen(System.currentTimeMillis())
                        .build();
            }else {
                notification =new Notification.Builder(DetectWebService.this)
                        .setContentTitle("检测远程服务器")
                        .setContentText(info)
                        .setSmallIcon(icon)
                        .setContentIntent(pendingIntent)
                        .setWhen(System.currentTimeMillis())
                        .build();
            }
            if (manager!=null){
                manager.notify(NOTIFICATION_DETECT_ID,notification);
            }


        }

        private int compareData() {
            try {
                List<Practice> remote=
                        PracticeService.getPractice(PracticeService.getPracticesFromServer());
                if (remote.size() != localCount) {
                    return FLAG_DATA_CHANGED;
                }else {
                    return FLAG_DATA_SANE;
                }
            } catch (Exception e) {
                return FLAG_SERVER_EXCEPTION;
            }
        }

    }


}
