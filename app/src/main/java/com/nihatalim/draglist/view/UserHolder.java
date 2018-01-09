package com.nihatalim.draglist.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.nihatalim.draglist.R;

/**
 * Created by thecower on 1/8/18.
 */

public class UserHolder extends RecyclerView.ViewHolder{
    public TextView textName = null;

    public UserHolder(View itemView) {
        super(itemView);
        this.textName = itemView.findViewById(R.id.textName);
    }
}
