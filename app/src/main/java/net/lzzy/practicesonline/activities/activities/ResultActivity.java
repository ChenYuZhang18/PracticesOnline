package net.lzzy.practicesonline.activities.activities;

import android.annotation.SuppressLint;
import androidx.fragment.app.Fragment;
import net.lzzy.practicesonline.R;

/**
 * Created by lzzy_gxy on 2019/5/13.
 * Description:
 */
@SuppressLint("Registered")
public class ResultActivity extends BaseActivity{

    @Override
    protected int getContainerId() {
        return R.layout.activity_result;
    }

    @Override
    protected int getLayoutRes() {
        return R.id.activity_result_container;
    }

    @Override
    protected Fragment createFragment() {
        return null;
    }
}
