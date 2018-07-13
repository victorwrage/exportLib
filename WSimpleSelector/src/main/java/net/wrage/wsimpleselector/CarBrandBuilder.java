package net.wrage.wsimpleselector;

import android.content.Context;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import java.util.List;


public class CarBrandBuilder {
    private static final int INVALID = -1;
    private final int[] outMostMargin = new int[4];
    private final FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.BOTTOM
    );
    List<CardBrandBean> items;
    private int gravity = Gravity.BOTTOM;
    private OnCancelListener onCancelListener;
    private OnItemClickListener onItemClickListener;
 //   private OnBackpressListener onBackpressListener;
 //   private Holder holder;

/*    public OnBackpressListener getOnBackpressListener() {
        return onBackpressListener;
    }

    public void setOnBackpressListener(OnBackpressListener onBackpressListener) {
        this.onBackpressListener = onBackpressListener;
    }*/
/*
    public Holder getHolder() {
        return holder;
    }

    public CarBrandBuilder setHolder(Holder holder) {
        this.holder = holder;
        return this;
    }*/

    private boolean isCancelable = true;
    private boolean expanded;
    private Context context;
    private int inAnimation = INVALID;
    private int outAnimation = INVALID;
    private int overlayBackgroundResource =  0xdddddd;
    private CarBrandBuilder() {
    }
    /**
     * Initialize the builder with a valid context in order to inflate the dialog
     */
    CarBrandBuilder(Context context) {
        if (context == null) {
            throw new NullPointerException("Context may not be null");
        }
        this.context = context;

    }

    public boolean isCancelable() {
        return isCancelable;
    }

    public void setCancelable(boolean cancelable) {
        isCancelable = cancelable;
    }

    /**
     * Set the adapter that will be used when ListHolder or GridHolder are passed
     */
    public CarBrandBuilder setData(List<CardBrandBean> items) {
        if (items == null) {
            throw new NullPointerException("data may not be null");
        }
        this.items = items;
        return this;
    }
    public CarBrandBuilder setExpanded(boolean expanded) {
        this.expanded = expanded;
        return this;
    }
    public OnCancelListener getOnCancelListener() {
        return onCancelListener;
    }
    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }
    public CarBrandBuilder setOnCancelListener(OnCancelListener listener) {
        this.onCancelListener = listener;
        return this;
    }

    public CarBrandBuilder setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
        return this;
    }

    public List<CardBrandBean> getItems() {
        return items;
    }

    public Animation getInAnimation() {
        int res = (inAnimation == INVALID) ? Utils.getAnimationResource(this.gravity, true) : inAnimation;
        return AnimationUtils.loadAnimation(context, res);
    }

    public Animation getOutAnimation() {
        int res = (outAnimation == INVALID) ? Utils.getAnimationResource(this.gravity, false) : outAnimation;
        return AnimationUtils.loadAnimation(context, res);
    }

    public CarBrandBuilder setOverlayBackgroundResource(int resourceId) {
        this.overlayBackgroundResource = resourceId;
        return this;
    }
    public int getOverlayBackgroundResource() {
        return overlayBackgroundResource;
    }
    /**
     * Add margins to your outmost view which contains everything. As default they are 0
     * are applied
     */
    public CarBrandBuilder setOutMostMargin(int left, int top, int right, int bottom) {
        this.outMostMargin[0] = left;
        this.outMostMargin[1] = top;
        this.outMostMargin[2] = right;
        this.outMostMargin[3] = bottom;
        return this;
    }

    public FrameLayout.LayoutParams getOutmostLayoutParams() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        );
        params.setMargins(outMostMargin[0], outMostMargin[1], outMostMargin[2], outMostMargin[3]);
        return params;
    }

    /**
     * Create the dialog using this builder
     */
    public CarBrand create() {
        return new CarBrand(this);
    }

    public Context getContext() {
        return context;
    }

    public boolean isExpanded() {
        return expanded;
    }
}
