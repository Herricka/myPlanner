package com.aaron.herri.mytodolist;

import android.support.v4.app.Fragment;

/**
 * Created by herri on 10/21/2015.
 */
public class BillListActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new BillListFragment();
    }
}

