package com.nihatalim.dragmanagement.business;

import android.content.ClipData;
import android.view.DragEvent;
import android.view.View;

import com.nihatalim.dragmanagement.helpers.ViewData;
import com.nihatalim.dragmanagement.interfaces.OnDrag;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thecower on 1/9/18.
 */

public class DragManager {
    // Base View Lists
    private List<ViewData> BaseViewDatas = null;
    // Drop to TargetView
    private View TargetView = null;
    // OnDrag interface
    private OnDrag OnDrag = null;

    private DragManager() {
        this.BaseViewDatas = new ArrayList<>();
        this.OnDrag = new OnDrag() {
            @Override
            public void Drop(View view, DragEvent dragEvent, Object data) {

            }

            @Override
            public void DragEntered(View view, DragEvent dragEvent, Object data) {

            }

            @Override
            public void DragExited(View view, DragEvent dragEvent, Object data) {

            }
        };
    }

    private DragManager(View target) {
        this();
        this.TargetView = target;
    }

    public static DragManager init() {
        return new DragManager();
    }

    public static DragManager init(View target) {
        return new DragManager(target);
    }

    public DragManager OnDrag(OnDrag dragEvents) {
        this.OnDrag = dragEvents;
        return this;
    }

    public boolean drag(View fromView, Object transferData) {
        ViewData viewData = new ViewData(fromView, transferData);
        this.BaseViewDatas.add(viewData);

        ClipData data = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(fromView);
        fromView.startDrag(data, shadowBuilder, viewData, 0);
        return true;
    }

    public DragManager build() {
        this.TargetView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View view, DragEvent dragEvent) {
                ViewData localStateData = null;
                try {
                    localStateData = (ViewData) dragEvent.getLocalState();
                } catch (ClassCastException ex) {
                    return false;
                }
                if (localStateData != null && BaseViewDatas.contains(localStateData)) {
                    switch (dragEvent.getAction()) {
                        case DragEvent.ACTION_DROP:
                            DragManager.this.OnDrag.Drop(view, dragEvent, dragEvent.getLocalState());
                            break;

                        case DragEvent.ACTION_DRAG_ENTERED:
                            DragManager.this.OnDrag.DragEntered(view, dragEvent, dragEvent.getLocalState());
                            break;

                        case DragEvent.ACTION_DRAG_EXITED:
                            DragManager.this.OnDrag.DragExited(view, dragEvent, dragEvent.getLocalState());
                            break;
                    }
                }
                return true;
            }
        });
        return this;
    }
}
