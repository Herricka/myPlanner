package com.aaron.herri.mytodolist;

import android.widget.Spinner;

import java.util.Date;
import java.util.UUID;

/**
 * Created by herri on 10/21/2015.
 */
public class Bill {

    private UUID mId;

    private String mTitle;

    private String mDescription;

    private Spinner mSpinner;

    private Date mDate;

    private boolean mPaid;

    public Bill() {
        // Generate unique identifier
        this(UUID.randomUUID());
    }

    public Bill(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

 /*   public String getPriority() {
        return mSpinner;
    }

    public void SetPriority(Spinner spinner) {
        mSpinner = spinner;
    } */

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isPaid() {
        return mPaid;
    }

    public void setPaid(boolean paid) {
        mPaid = paid;
    }


}
