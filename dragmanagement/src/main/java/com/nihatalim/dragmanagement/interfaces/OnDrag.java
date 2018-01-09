package com.nihatalim.dragmanagement.interfaces;

import android.view.DragEvent;
import android.view.View;

/**
 * Created by thecower on 1/9/18.
 */

public interface OnDrag<T> {
    public void Drop(View view, DragEvent dragEvent, T data);
    public void DragEntered(View view, DragEvent dragEvent, T data);
    public void DragExited(View view, DragEvent dragEvent, T data);
}
