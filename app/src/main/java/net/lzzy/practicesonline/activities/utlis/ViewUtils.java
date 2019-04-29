package net.lzzy.practicesonline.activities.utlis;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;
import net.lzzy.practicesonline.R;
import net.lzzy.practicesonline.activities.activites.SplashActivity;

/**
 * Created by lzzy_gxy on 2019/4/15.
 * Description:
 */
public class ViewUtils {
    private static AlertDialog dialog;
    public static void showProgress(Context context,String message){
        if (dialog==null){
            View view= LayoutInflater.from(context).inflate(R.layout.dialog_progress,null);
            TextView tv = view.findViewById(R.id.dialog_progress_tv);
            tv.setText(message);
            dialog=new AlertDialog.Builder(context).create();
            dialog.setView(view);
        }
        dialog.show();
    }
    public static void dismissProgress() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

        public static void gotoSetting (Context context){
        View view= LayoutInflater.from(context).inflate(R.layout.dialog_setting,null);
        Pair<String,String>url=AppUtils.loadServerSetting(context);
        EditText edtIp=view.findViewById(R.id.dialog_setting_edt_ip);
        edtIp.setText(url.first);
        EditText edPort=view.findViewById(R.id.dialog_setting_edt_port);
        edPort.setText(url.second);
        new AlertDialog.Builder(context)
                .setView(view)
                .setNeutralButton("取消",(dialog, which) -> {gotMain(context);})
                .setPositiveButton("确定",(dialog, which) -> {
                    String ip=edtIp.getText().toString();
                    String port=edPort.getText().toString() ;
                    if (TextUtils.isEmpty(ip) || TextUtils.isEmpty(port)) {
                        Toast.makeText(context,"信息不完成整,",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    AppUtils.saveServerSetting(ip,port,context);
                    gotMain(context);
                })
                .show();
    }

    private static void gotMain(Context context){
        if (context instanceof SplashActivity){
            ((SplashActivity)context).gotoMain();
        }
    }

    public static abstract class AbstractQueryListener implements SearchView.OnQueryTextListener{
        @Override
        public boolean onQueryTextChange(String newText) {
             handleQuery(newText);
             return true;
        }

        /**
         * handle query logic
         * @param kw keyword
         * @return end query
         */
        public abstract void handleQuery(String kw);
    }

    public static abstract class AbstractTouchHandler implements View.OnTouchListener {

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return handleTouch(event);
            }

        protected abstract boolean handleTouch(MotionEvent event);

    }
}


