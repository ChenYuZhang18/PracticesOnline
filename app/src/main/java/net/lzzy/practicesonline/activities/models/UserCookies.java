package net.lzzy.practicesonline.activities.models;

import android.content.Context;
import android.content.SharedPreferences;
import net.lzzy.practicesonline.activities.utlis.AppUtils;
import net.lzzy.practicesonline.activities.utlis.DateTimeUtils;
import java.util.Date;

/**
 * Created by lzzy_gxy on 2019/4/24.
 * Description:
 */
public class UserCookies {
    private SharedPreferences spTime;
    private static final UserCookies INSTANCE=new UserCookies();

    private SharedPreferences spCommit;
    private SharedPreferences spChecked;
    private SharedPreferences spPostion;
    private SharedPreferences spReadCount;

    private static final String KEY_TIME="time";
    private static final String ID_SPLITTER=",";
    private static final int FLAG_COMMIT_Y=1;
    private static final int FLAG_COMMIT_N=0;

    private UserCookies(){
        spTime= AppUtils.getContext()
                .getSharedPreferences("refresh_time", Context.MODE_PRIVATE);

    }
    public static UserCookies getInstance(){
        return INSTANCE;
    }

    public void updateLastRefreshTime(){
        String time= DateTimeUtils.DATE_TIME_FORMAT.format(new Date());
        spTime.edit().putString(KEY_TIME,time).apply();
    }

    public String getLastRefreshTime(){
        return spTime.getString(KEY_TIME,"");
    }

    public boolean isPracticeCommitted(){
        return true;
    }

    public void commitPractice(){

    }
}
