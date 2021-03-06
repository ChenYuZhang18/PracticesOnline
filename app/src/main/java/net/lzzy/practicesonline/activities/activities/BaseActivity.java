package net.lzzy.practicesonline.activities.activities;

import android.os.Bundle;
import android.view.Window;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import net.lzzy.practicesonline.activities.utlis.AppUtils;

/**
 * Created by lzzy_gxy on 2019/4/11.
 * Description:
 */
public abstract class BaseActivity extends AppCompatActivity {


    private Fragment fragment;
    private FragmentManager manager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutRes());
        AppUtils.addActivity(this);
        manager = getSupportFragmentManager();
        fragment = manager.findFragmentById(getContainerId());
        if (fragment == null) {
            fragment = createFragment();
            manager.beginTransaction().add(getContainerId(), fragment).commit();
        }
    }
    protected  FragmentManager getManager(){
        return manager;
    }

    protected abstract int getContainerId();

    protected abstract int getLayoutRes();

    protected abstract Fragment createFragment();

    protected Fragment getFragment() {
        return fragment;
    }


        @Override
        protected void onDestroy () {
            super.onDestroy();
            AppUtils.removeActivity(this);
        }

        @Override
        protected void onResume () {
            super.onResume();
            AppUtils.setRunning(getLocalClassName());
        }

        @Override
        protected void onStop () {
            super.onStop();
            AppUtils.setStopped(getLocalClassName());
        }
    }