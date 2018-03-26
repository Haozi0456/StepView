package com.zwh.stepView;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zwh.stepView.bean.StepBean;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 *
 * @author Zhaohao
 * @Date 2017/04/18 17:00
 * @Description: 垂直指示器
 */
public class VerticalStepView extends LinearLayout {
    private LinearLayout mTextContainer;
    private VerticalStepViewIndicator mStepsViewIndicator;
    private List<StepBean> mTexts;
    private int mComplectingPosition;
    private int mUnComplectedTextColor = ContextCompat.getColor(getContext(), R.color.uncompleted_text_color);//定义默认未完成文字的颜色;
    private int mComplectedTextColor = ContextCompat.getColor(getContext(), R.color.completed_text_color);//定义默认完成文字的颜色;
    private int mCurrentDoTextColor = ContextCompat.getColor(getContext(), R.color.current_text_color);//定义当前完成文字的颜色;

    private int mTextSize = 14;//default textSize
    private TextView mTextView;

    private int totalHeight = 0;

    public VerticalStepView(Context context) {
        this(context, null);
    }

    public VerticalStepView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalStepView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private OnDrawIndicatorListener mOnDrawListener;

    private List<Integer> stepTextPositionList;

    private void init() {
        stepTextPositionList = new ArrayList<>();

        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.widget_vertical_stepsview, this);
        mStepsViewIndicator = (VerticalStepViewIndicator) rootView.findViewById(R.id.steps_indicator);
        setmOnDrawListener(mStepsViewIndicator);
        mTextContainer = (LinearLayout) rootView.findViewById(R.id.rl_text_container);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        // 循环所有子View
        for (int i=0; i<mTextContainer.getChildCount(); i++) {
            View child = mTextContainer.getChildAt(i);
            // 取出当前子View长宽
            int top = child.getTop();
            stepTextPositionList.add(top);
        }

        for (int i=0; i<getChildCount(); i++) {
            View child = getChildAt(i);
            // 取出当前子View长宽
            int height = child.getMeasuredHeight();
            if(totalHeight <=height){
                totalHeight = height;
            }
        }

        if (mOnDrawListener != null) {
            mOnDrawListener.ondrawIndicator(stepTextPositionList,totalHeight);
            mStepsViewIndicator.invalidate();
        }

    }

    /**
     * 设置显示的文字
     *
     * @param texts
     * @return
     */
    public VerticalStepView setStepViewTexts(List<StepBean> texts) {
        mTexts = texts;
        if (texts != null) {
            mStepsViewIndicator.setStepNum(mTexts.size());
        } else {
            mStepsViewIndicator.setStepNum(0);
        }
        drawStepText();
        return this;
    }


    /**
     * 设置未完成文字的颜色
     *
     * @param unComplectedTextColor
     * @return
     */
    public VerticalStepView setStepViewUnComplectedTextColor(int unComplectedTextColor) {
        mUnComplectedTextColor = unComplectedTextColor;
        return this;
    }

    /**
     * 设置完成文字的颜色
     *
     * @param complectedTextColor
     * @return
     */
    public VerticalStepView setStepViewComplectedTextColor(int complectedTextColor) {
        this.mComplectedTextColor = complectedTextColor;
        return this;
    }

    /**
     * 设置StepsViewIndicator未完成线的颜色
     *
     * @param unCompletedLineColor
     * @return
     */
    public VerticalStepView setStepsViewIndicatorUnCompletedLineColor(int unCompletedLineColor) {
        mStepsViewIndicator.setUnCompletedLineColor(unCompletedLineColor);
        return this;
    }

    /**
     * 设置StepsViewIndicator完成线的颜色
     *
     * @param completedLineColor
     * @return
     */
    public VerticalStepView setStepsViewIndicatorCompletedLineColor(int completedLineColor) {
        mStepsViewIndicator.setCompletedLineColor(completedLineColor);
        return this;
    }

    /**
     * 设置StepsViewIndicator默认图片
     *
     * @param defaultIcon
     */
    public VerticalStepView setStepsViewIndicatorDefaultIcon(Drawable defaultIcon) {
        mStepsViewIndicator.setDefaultIcon(defaultIcon);
        return this;
    }

    /**
     * 设置StepsViewIndicator已完成图片
     *
     * @param completeIcon
     */
    public VerticalStepView setStepsViewIndicatorCompleteIcon(Drawable completeIcon) {
        mStepsViewIndicator.setCompleteIcon(completeIcon);
        return this;
    }

    /**
     * 设置StepsViewIndicator正在进行中的图片
     *
     * @param attentionIcon
     */
    public VerticalStepView setStepsViewIndicatorAttentionIcon(Drawable attentionIcon) {
        mStepsViewIndicator.setAttentionIcon(attentionIcon);
        return this;
    }

    /**
     * is reverse draw 是否倒序画
     *
     * @param isReverSe default is true
     * @return
     */
    public VerticalStepView reverseDraw(boolean isReverSe) {
        this.mStepsViewIndicator.reverseDraw(isReverSe);
        return this;
    }


    /**
     * set textSize
     *
     * @param textSize
     * @return
     */
    public VerticalStepView setTextSize(int textSize) {
        if (textSize > 0) {
            mTextSize = textSize;
        }
        return this;
    }


    public void drawStepText() {
        if (mTextContainer != null) {
            mTextContainer.removeAllViews();//clear ViewGroup
            if (mTexts != null) {
                if(this.mStepsViewIndicator.isReverseDraw()){
                    for (int i = mTexts.size() -1; i >= 0; i--) {
                        StepBean stepBean = mTexts.get(i);
                        mTextView = new TextView(getContext());
                        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
                        mTextView.setText(stepBean.getName());
                        int top =dp2px(getContext(),14);
                        mTextView.setPadding(0, top, 0, top);
                        mTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        if(stepBean.getState() == StepBean.STEP_COMPLETED){
                            mTextView.setTextColor(mComplectedTextColor);
                        }else if(stepBean.getState() == StepBean.STEP_CURRENT){
                            mComplectingPosition = i;
                            mStepsViewIndicator.setComplectingPosition(mComplectingPosition);
                            mTextView.setTypeface(null, Typeface.BOLD);
                            mTextView.setTextColor(mCurrentDoTextColor);
                        }else if(stepBean.getState() == StepBean.STEP_UNDO){
                            mTextView.setTextColor(mUnComplectedTextColor);
                        }

                        mTextContainer.addView(mTextView);
                    }
                }else{
                    for (int i = 0; i < mTexts.size(); i++) {
                        StepBean stepBean = mTexts.get(i);
                        mTextView = new TextView(getContext());
                        mTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, mTextSize);
                        mTextView.setText(stepBean.getName());
                        int top =dp2px(getContext(),14);
                        mTextView.setPadding(0, top, 0, top);
                        mTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        if(stepBean.getState() == StepBean.STEP_COMPLETED){
                            mTextView.setTextColor(mComplectedTextColor);
                        }else if(stepBean.getState() == StepBean.STEP_CURRENT){
                            mComplectingPosition = i;
                            mStepsViewIndicator.setComplectingPosition(mComplectingPosition);
                            mTextView.setTypeface(null, Typeface.BOLD);
                            mTextView.setTextColor(mCurrentDoTextColor);
                        }else if(stepBean.getState() == StepBean.STEP_UNDO){
                            mTextView.setTextColor(mUnComplectedTextColor);
                        }
                        mTextContainer.addView(mTextView);
                    }
                }
            }
        }
    }

    /**
     * dp转px
     *
     * @param context 上下文
     * @param dpValue dp值
     * @return px值
     */
    public int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public OnDrawIndicatorListener getmOnDrawListener() {
        return mOnDrawListener;
    }

    public void setmOnDrawListener(OnDrawIndicatorListener mOnDrawListener) {
        this.mOnDrawListener = mOnDrawListener;
    }


    /**
     * 设置对view监听
     */
    public interface OnDrawIndicatorListener {
        void ondrawIndicator(List<Integer> positionList, int totalHeight);
    }
}
