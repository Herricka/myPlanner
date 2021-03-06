package com.aaron.herri.mytodolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

/**
 * Created by herri on 10/21/2015.
 */
public class BillListFragment extends Fragment {
    private RecyclerView mBillRecycleView;
    private BillAdapter mBillAdapter;
    private boolean mSubtitleVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill_list, container, false);

        mBillRecycleView = (RecyclerView) view
                .findViewById(R.id.bill_recycler_view);
        mBillRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_bill_list, menu);

      /*  MenuItem subtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        } */
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_new_bill:
                Bill bill = new Bill();
                BillLab.get(getActivity()).addBill(bill);
                Intent intent = BillPagerActivity
                        .newIntent(getActivity(), bill.getId());
                startActivity(intent);
                return true;
         /*   case R.id.menu_item_show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true; */


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle() {
        BillLab billLab = BillLab.get(getActivity());
        int billCount = billLab.getBills().size();
        String subtitle = getString(R.string.subtitle_format, billCount);

        if (!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void updateUI() {
        BillLab billLab = BillLab.get(getActivity());
        List<Bill> bills = billLab.getBills();

        if (mBillAdapter == null) {
            mBillAdapter = new BillAdapter(bills);
            mBillRecycleView.setAdapter(mBillAdapter);
        } else {
            mBillAdapter.setBills(bills);
            mBillAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    private class BillHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private CheckBox mPaidCheckBox;
        private Bill mBill;

        public BillHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView)
                    itemView.findViewById(R.id.list_item_bill_title_text_view);
            mDateTextView = (TextView)
                    itemView.findViewById(R.id.list_item_bill_date_text_view);
            mPaidCheckBox = (CheckBox)
                    itemView.findViewById(R.id.list_item_bill_paid_check_box);

        }

        public void bindBill(Bill bill) {
            mBill = bill;
            mTitleTextView.setText(mBill.getTitle());
            mDateTextView.setText(mBill.getDate().toString());
            mPaidCheckBox.setChecked(mBill.isPaid());
        }

        @Override
        public void onClick(View v) {
            Intent intent = BillPagerActivity.newIntent(getActivity(), mBill.getId());
            startActivity(intent);
        }
    }

    private class BillAdapter extends RecyclerView.Adapter<BillHolder> {

        private List<Bill> mBills;

        public BillAdapter(List<Bill> bills) {
            mBills = bills;
        }

        @Override
        public BillHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.list_item_bill, parent, false);
            return new BillHolder(view);
        }

        @Override
        public void onBindViewHolder(BillHolder holder, int position) {
            Bill bill = mBills.get(position);
            holder.bindBill(bill);
        }

        @Override
        public int getItemCount() {
            return mBills.size();
        }

        public void setBills(List<Bill> bills) {
            mBills = bills;
        }
    }


}
