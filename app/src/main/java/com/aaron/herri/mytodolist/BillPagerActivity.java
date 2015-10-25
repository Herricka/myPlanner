package com.aaron.herri.mytodolist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by herri on 10/21/2015.
 */
public class BillPagerActivity extends AppCompatActivity {

    private static final String EXTRA_BILL_ID =
            "com.aaron.herri.mytodolist.bill_id";

    private ViewPager mViewPager;
    private List<Bill> mBills;

    public static Intent newIntent(Context packageContext, UUID billId) {
        Intent intent = new Intent(packageContext, BillPagerActivity.class);
        intent.putExtra(EXTRA_BILL_ID, billId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_pager);

        UUID billId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_BILL_ID);

        mViewPager = (ViewPager) findViewById(R.id.activity_bill_view_pager);

        mBills = BillLab.get(this).getBills();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Bill bill = mBills.get(position);
                return TaskFragment.newInstance(bill.getId());
            }

            @Override
            public int getCount() {
                return mBills.size();
            }
        });

        for (int i = 0; i < mBills.size(); i++) {
            if (mBills.get(i).getId().equals(billId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
