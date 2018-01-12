package com.nihatalim.dragmanagement.business;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.ClipData;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.DragEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.nihatalim.dragmanagement.R;
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
    // Technique for animate
    private Techniques technique = null;

    private DragManager() {
        this.BaseViewDatas = new ArrayList<>();
        this.OnDrag = new OnDrag() {
            @Override
            public void DragStarted(View view, DragEvent dragEvent, Object data) {

            }

            @Override
            public void DragEntered(View view, DragEvent dragEvent, Object data) {

            }

            @Override
            public void DragLocation(View view, DragEvent dragEvent, Object data) {

            }

            @Override
            public void DragExited(View view, DragEvent dragEvent, Object data) {

            }

            @Override
            public void Drop(View view, DragEvent dragEvent, Object data) {

            }

            @Override
            public void DragEnded(View view, DragEvent dragEvent, Object data) {

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

    public DragManager build(Techniques... techniques) {
        if(techniques.length>0){
            this.technique = techniques[0];
        }
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

                        case DragEvent.ACTION_DRAG_ENDED:
                            // Set visibility
                            DragManager.this.OnDrag.DragEnded(view, dragEvent, dragEvent.getLocalState());
                            localStateData.getBaseView().setVisibility(View.VISIBLE);
                            break;

                        case DragEvent.ACTION_DRAG_STARTED:
                            // Set visibility
                            DragManager.this.OnDrag.DragStarted(view, dragEvent, dragEvent.getLocalState());
                            localStateData.getBaseView().setVisibility(View.INVISIBLE);
                            // Animation
                            if(technique!=null) YoYo.with(technique).duration(1000).playOn(view);
                            break;

                        case DragEvent.ACTION_DRAG_LOCATION:
                            DragManager.this.OnDrag.DragLocation(view, dragEvent, dragEvent.getLocalState());
                            break;
                    }
                }
                return true;
            }
        });
        return this;
    }
}
