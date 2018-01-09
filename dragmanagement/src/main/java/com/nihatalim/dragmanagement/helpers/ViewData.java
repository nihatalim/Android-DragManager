package com.nihatalim.dragmanagement.helpers;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

/**
 * Created by thecower on 1/9/18.
 */

public class ViewData {
    private View baseView = null;
    private Object data = null;

    public ViewData() {
    }

    public ViewData(View baseView, Object data) {
        this.baseView = baseView;
        this.data = data;
    }

    public View getBaseView() {
        return baseView;
    }

    public void setBaseView(View baseView) {
        this.baseView = baseView;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
