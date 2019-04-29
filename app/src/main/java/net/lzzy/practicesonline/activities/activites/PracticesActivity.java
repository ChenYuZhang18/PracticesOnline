package net.lzzy.practicesonline.activities.activites;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import net.lzzy.practicesonline.R;
import net.lzzy.practicesonline.activities.fragments.PracticesFragment;
import net.lzzy.practicesonline.activities.models.PracticeFactory;
import net.lzzy.practicesonline.activities.network.DetectWebService;
import net.lzzy.practicesonline.activities.utlis.AppUtils;
import net.lzzy.practicesonline.activities.utlis.ViewUtils;


/**
 * Created by lzzy_gxy on 2019/4/16.
 * Description:
 */
public class PracticesActivity extends BaseActivity implements PracticesFragment.OnPracticesSelectedListener {
    public static final String EXTRA_PRACTICE_ID = "extraPracticeId";
    public static final String EXTRA_API_ID = "extraApiId";
    public static final String EXTRA_LOCAL_COUNT = "localhost";

    private ServiceConnection connection;

    private boolean refresh=false;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SearchView search = findViewById(R.id.main_sv_search);
        search.setQueryHint("请输入关键词搜索");
        search.setOnQueryTextListener(new ViewUtils.AbstractQueryListener() {
            @Override
            public void handleQuery(String kw) {
                ((PracticesFragment) getFragment()).search(kw);
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
        });

        if (getIntent()!=null){
            refresh=getIntent().getBooleanExtra(DetectWebService.EXTRA_REFRESH,false);
        }

        /**④Activity中创建ServiceConnection**/
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                DetectWebService.DetectWebBinder binder = (DetectWebService.DetectWebBinder) service;
                binder.detect();
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };

        //读取本地数据，传到DetectWebService,进行对比
        int localCount = PracticeFactory.getInstance().get().size();
        Intent intent = new Intent(this, DetectWebService.class);
        intent.putExtra(EXTRA_LOCAL_COUNT, localCount);

        /**⑤Activity中启动Service(bindService/startService)**/
        bindService(intent, connection, BIND_AUTO_CREATE);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (refresh){
            ((PracticesFragment)getFragment()).staetRefresh();
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("退出应用吗？")
                .setPositiveButton("退出",((dialog, which) -> AppUtils.exit()))
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activicty_practices;
    }

    @Override
    protected int getContainerId() {
        return R.id.activity_practices_container;
    }

    @Override
    protected Fragment createFragment() {
        return new PracticesFragment();
    }


    @Override
    public void onPracticesSelected(String practiceId, int apiId) {
        Intent intent = new Intent(this, QuestionActivity.class);
        intent.putExtra(EXTRA_PRACTICE_ID, practiceId);
        intent.putExtra(EXTRA_API_ID, apiId);
        startActivity(intent);
    }


}
