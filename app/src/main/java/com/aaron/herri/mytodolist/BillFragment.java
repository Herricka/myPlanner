package com.aaron.herri.mytodolist;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Date;
import java.util.UUID;

/**
 * Created by herri on 10/22/2015.
 */
public class BillFragment extends Fragment {

    private static final String ARG_BILL_ID = "bill_id";
    private static final String DIALOG_DATE = "DialogDate";

    private static final int REQUEST_DATE = 0;

    private Bill mBill;
    private EditText mTitleField;
    private EditText mDescriptionField;
    private Button mDateButton;
    private CheckBox mPaidCheckBox;

    public static BillFragment newInstance(UUID billID) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_BILL_ID, billID);

        BillFragment fragment = new BillFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID billId = (UUID) getArguments().getSerializable(ARG_BILL_ID);
        mBill = BillLab.get(getActivity()).getBill(billId);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.bill_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_bill_action:
                UUID billId = mBill.getId();
                BillLab.get(getActivity()).deleteBill(billId);

                Toast.makeText(getActivity(), R.string.toast_delete_bill, Toast.LENGTH_SHORT).show();
                getActivity().finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        BillLab.get(getActivity())
                .updateBill(mBill);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bill, container, false);

        mTitleField = (EditText)v.findViewById(R.id.bill_title);
        mTitleField.setText(mBill.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBill.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This one too
            }
        });

        mDescriptionField = (EditText)v.findViewById(R.id.bill_description);
        mDescriptionField.setText(mBill.getDescription());
        mDescriptionField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mBill.setDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        /*Spinner spinner = (Spinner)v.findViewById(R.id.priority_spinner);
        String[] items = new String[] {"Low", "Medium", "High"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);*/

        mDateButton = (Button)v.findViewById(R.id.bill_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mBill.getDate());
                dialog.setTargetFragment(BillFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mPaidCheckBox = (CheckBox)v.findViewById(R.id.bill_paid);
        mPaidCheckBox.setChecked(mBill.isPaid());
        mPaidCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Set the task's solved property
                mBill.setPaid(isChecked);
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data
                    .getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mBill.setDate(date);
            updateDate();
        }

    }

    private void updateDate() {
        mDateButton.setText(mBill.getDate().toString());
    }
}
