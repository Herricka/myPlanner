package com.aaron.herri.mytodolist;

import android.support.v4.app.Fragment;

/**
 * Created by herri on 10/6/2015.
 */
public class TaskListActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new TaskListFragment();
    }
}
