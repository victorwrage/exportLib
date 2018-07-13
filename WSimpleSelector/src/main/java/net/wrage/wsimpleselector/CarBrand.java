package net.wrage.wsimpleselector;

import android.app.Activity;
import android.content.Context;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import com.github.promeg.pinyinhelper.Pinyin;
import com.github.promeg.tinypinyin.lexicons.android.cncity.CnCityDict;


import java.util.List;

import me.yokeyword.indexablerv.IndexableAdapter;
import me.yokeyword.indexablerv.IndexableLayout;

/**
 * @author xiaoyl
 * @date 2018-05-16
 * 汽车品牌选择器
 */
public class CarBrand {
    /**
     * basically activity root view
     */
    private final ViewGroup decorView;
    private final Context context;
    /**
     * Listener to notify the user that dialog has been canceled
     */
    private final OnCancelListener onCancelListener;
    private final OnItemClickListener onItemClickListener;

    private final Animation outAnim;
    private final Animation inAnim;
 //   Holder holder;
    /**
     * Determines whether dialog should be dismissed by back button or touch in the black overlay
     */
    private final boolean isCancelable;
    View content_view;
    /**
     * Determines whether dialog is showing dismissing animation and avoid to repeat it
     */
    private boolean isDismissing;
    private View contentContainer;
    /**
     * Called when the user touch on black overlay in order to dismiss the dialog
     */
    private final View.OnTouchListener onCancelableTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (onCancelListener != null) {
                    onCancelListener.onCancel(CarBrand.this);
                }
                dismiss();
            }
            return false;
        }
    };


    public CarBrand(CarBrandBuilder builder) {
        this.context = builder.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(builder.getContext());
        Activity activity = (Activity) builder.getContext();
        onCancelListener = builder.getOnCancelListener();
        onItemClickListener = builder.getOnItemClickListener();
     //   onBackPressListener = builder.getOnBackpressListener();
      //  holder = builder.getHolder();
        isCancelable = builder.isCancelable();

        decorView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
        content_view = layoutInflater.inflate(R.layout.brand_car_selecter, decorView, false);
        contentContainer = content_view.findViewById(R.id.dialogplus_content_container);
        contentContainer.setBackgroundResource(builder.getOverlayBackgroundResource());
        outAnim = builder.getOutAnimation();
        inAnim = builder.getInAnimation();

        initContentView(
                layoutInflater,
                builder.getItems()
        );
        initCancelable();
    }

    /**
     * It is called in order to create content
     */
    private void initContentView(LayoutInflater inflater, List<CardBrandBean> brandBeans) {
        View contentView = createView(inflater, brandBeans);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        );
        contentView.setLayoutParams(params);
    }

    /**
     * It is called to set whether the dialog is cancellable by pressing back button or
     * touching the black overlay
     */
    private void initCancelable() {
        if (!isCancelable) {
            return;
        }
        contentContainer.setOnTouchListener(onCancelableTouchListener);
    }

    /**
     * it is called when the content view is created
     *
     * @param inflater used to inflate the content of the dialog
     * @return any view which is passed
     */
    private View createView(LayoutInflater inflater, List<CardBrandBean> brandBeans) {

        if (brandBeans != null) {
            IndexableLayout indexableLayout = content_view.findViewById(R.id.indexableLayout);

            final LinearLayoutManager linearLayoutManager = new WrapContentLinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            indexableLayout.setLayoutManager(linearLayoutManager);
            //indexableLayout.setLayoutManager(new GridLayoutManager(this, 1));
            // 多音字处理
            Pinyin.init(Pinyin.newConfig().with(CnCityDict.getInstance(context)));
            // 快速排序。  排序规则设置为：只按首字母  （默认全拼音排序）  效率很高，是默认的10倍左右。  按需开启～
            indexableLayout.setCompareMode(IndexableLayout.MODE_FAST);

            CarBrandAdapter adapter = new CarBrandAdapter(context);
            indexableLayout.setAdapter(adapter);
            adapter.setDatas(brandBeans);
            indexableLayout.setOverlayStyle_Center();

            adapter.setOnItemContentClickListener(new IndexableAdapter.OnItemContentClickListener<CardBrandBean>() {
                @Override
                public void onItemClick(View v, int originalPosition, int currentPosition, CardBrandBean entity) {
                    OnItemClick(entity);
                }
            });
        }
        return content_view;
    }

    private void OnItemClick(CardBrandBean entity) {
        onItemClickListener.ItemClick(entity);
        dismiss();
    }

    /**
     * It is called when to dismiss the dialog, either by calling dismiss() method or with cancellable
     */
    public void dismiss() {
        if (isDismissing) {
            return;
        }

        outAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                decorView.post(new Runnable() {
                    @Override
                    public void run() {
                        decorView.removeView(content_view);
                        isDismissing = false;

                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        contentContainer.startAnimation(outAnim);
        isDismissing = true;
    }

    public static CarBrandBuilder newDialog(Context context) {
        return new CarBrandBuilder(context);
    }

    /**
     * It adds the dialog view into rootView which is decorView of activity
     */
    public void show() {
        if (isShowing()) {
            return;
        }
        onAttached(content_view);
    }

    /**
     * It basically check if the rootView contains the dialog plus view.
     *
     * @return true if it contains
     */
    public boolean isShowing() {
        View view = decorView.findViewById(R.id.dialogplus_content_container);
        return view != null;
    }

    /**
     * It is called when the show() method is called
     *
     * @param view is the dialog plus view
     */
    private void onAttached(View view) {
        decorView.addView(view);
        contentContainer.startAnimation(inAnim);

        contentContainer.requestFocus();
        contentContainer.setOnKeyListener(new View.OnKeyListener() {
            @Override public boolean onKey(View v, int keyCode, KeyEvent event) {

                switch (event.getAction()) {
                    case KeyEvent.ACTION_UP:
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            onBackPressed(CarBrand.this);
                            return true;
                        }
                        break;
                }
                return false;
            }
        });

    }


    /**
     * Dismiss the dialog when the user press the back button
     *
     * @param dialogPlus is the current dialog
     */
    public void onBackPressed(CarBrand dialogPlus) {

        if (onCancelListener != null) {
            onCancelListener.onCancel(CarBrand.this);
        }
        dismiss();
    }


    public class WrapContentLinearLayoutManager extends LinearLayoutManager {

        public WrapContentLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }


        @Override
        public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
            try {
                super.onLayoutChildren(recycler, state);
            } catch (IndexOutOfBoundsException e) {
                Log.e("probe", "meet a IOOBE in RecyclerView");
            }
        }
    }
}
