package com.zwh.stepView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * @author Zhaohao
 * @Date 2017/04/18 16:59
 * @Description: 垂直流程指示器
 */
public class VerticalStepViewIndicator extends View implements VerticalStepView.OnDrawIndicatorListener {
    private final String TAG_NAME = this.getClass().getSimpleName();

    //定义默认的高度   definition default height
    private int defaultStepIndicatorNum = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());

    private float mCompletedLineHeight;//完成线的高度     definition completed line height
    private float mCircleRadius;//圆的半径  definition circle radius

    private Drawable mCompleteIcon;//完成的默认图片    definition default completed icon
    private Drawable mAttentionIcon;//正在进行的默认图片     definition default underway icon
    private Drawable mDefaultIcon;//默认的背景图  definition default unCompleted icon
    private float mCenterX;//该View的X轴的中间位置
    private float mLeftY;
    private float mRightY;

    private int mStepNum = 0;//当前有几部流程    there are currently few step

    private List<Float> mCircleCenterPointPositionList;//定义所有圆的圆心点位置的集合 definition all of circles center point list
    private Paint mUnCompletedPaint;//未完成Paint  definition mUnCompletedPaint
    private Paint mCompletedPaint;//完成paint      definition mCompletedPaint
    private int mUnCompletedLineColor = ContextCompat.getColor(getContext(), R.color.uncompleted_color);//定义默认未完成线的颜色  definition mUnCompletedLineColor
    private int mCompletedLineColor = Color.GREEN;//定义默认完成线的颜色      definition mCompletedLineColor
    private PathEffect mEffects;

    private int mComplectingPosition;//正在进行position   underway position
    private Path mPath;

    private Rect mRect;
    private int mHeight;//这个控件的动态高度    this view dynamic height

    public boolean isReverseDraw() {
        return mIsReverseDraw;
    }


    private boolean mIsReverseDraw;//is reverse draw this view;


    private List<Integer> positionCircleList;
    private int totalHeight = 0;

    public VerticalStepViewIndicator(Context context) {
        this(context, null);
    }

    public VerticalStepViewIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalStepViewIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    /**
     * init
     */
    private void init() {
        mPath = new Path();
        mEffects = new DashPathEffect(new float[]{8, 8, 8, 8}, 1);
        mCircleCenterPointPositionList = new ArrayList<>();//初始化

        mUnCompletedPaint = new Paint();
        mCompletedPaint = new Paint();
        mUnCompletedPaint.setAntiAlias(true);
        mUnCompletedPaint.setColor(mUnCompletedLineColor);
        mUnCompletedPaint.setStyle(Paint.Style.STROKE);
        mUnCompletedPaint.setStrokeWidth(2);

        mCompletedPaint.setAntiAlias(true);
        mCompletedPaint.setColor(mCompletedLineColor);
        mCompletedPaint.setStyle(Paint.Style.STROKE);
        mCompletedPaint.setStrokeWidth(5);

        mUnCompletedPaint.setPathEffect(mEffects);
        mCompletedPaint.setStyle(Paint.Style.FILL);

        //已经完成线的宽高 set mCompletedLineHeight
        mCompletedLineHeight = 0.05f * defaultStepIndicatorNum;
        //圆的半径  set mCircleRadius
        mCircleRadius = 0.28f * defaultStepIndicatorNum;

        mCompleteIcon = ContextCompat.getDrawable(getContext(), R.drawable.complted);//已经完成的icon
        mAttentionIcon = ContextCompat.getDrawable(getContext(), R.drawable.attention);//正在进行的icon
        mDefaultIcon = ContextCompat.getDrawable(getContext(), R.drawable.default_icon);//未完成的icon

        mIsReverseDraw = true;//default draw
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.i(TAG_NAME, "onMeasure");
        int width = defaultStepIndicatorNum;
        mHeight = 0;
        if (positionCircleList != null && positionCircleList.size() > 0) {
            mHeight = totalHeight;
        }
        setMeasuredDimension(width, mHeight);

    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i(TAG_NAME, "onSizeChanged");
        mCenterX = getWidth() / 2;
        mLeftY = mCenterX - (mCompletedLineHeight / 2);
        mRightY = mCenterX + (mCompletedLineHeight / 2);
        if (positionCircleList != null) {

            if (mIsReverseDraw) {
                for (int i = mStepNum - 1; i >= 0; i--) {
                    float pos = positionCircleList.get(i) + mCircleRadius + 20;
                    mCircleCenterPointPositionList.add(pos);
                }
            } else {
                for (int i = 0; i < mStepNum; i++) {
                    float pos = positionCircleList.get(i) + mCircleRadius + 20;
                    mCircleCenterPointPositionList.add(pos);
                }
            }


        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG_NAME, "onDraw");
//        if (mOnDrawListener != null) {
//            mOnDrawListener.ondrawIndicator();
//        }
        mUnCompletedPaint.setColor(mUnCompletedLineColor);
        mCompletedPaint.setColor(mCompletedLineColor);

        if (positionCircleList == null || positionCircleList.size() == 0) {
            Log.d(TAG, "onDraw: 没有数据!");
            return;
        }

        //-----------------------画线-------draw line-----------------------------------------------
        for (int i = 0; i < mCircleCenterPointPositionList.size() - 1; i++) {
            //前一个ComplectedXPosition
            final float preComplectedXPosition = mCircleCenterPointPositionList.get(i);
            //后一个ComplectedXPosition
            final float afterComplectedXPosition = mCircleCenterPointPositionList.get(i + 1);

            if (i < mComplectingPosition)//判断在完成之前的所有点
            {
                //判断在完成之前的所有点，画完成的线，这里是矩形,很细的矩形，类似线，为了做区分，好看些
                if (mIsReverseDraw) {
                    canvas.drawLine(mLeftY, preComplectedXPosition, mLeftY, afterComplectedXPosition, mCompletedPaint);
                } else {
                    canvas.drawLine(mLeftY, preComplectedXPosition, mLeftY, afterComplectedXPosition, mCompletedPaint);
//                    canvas.drawRect(mLeftY, preComplectedXPosition + mCircleRadius - 10, mRightY, afterComplectedXPosition - mCircleRadius + 10, mCompletedPaint);
                }
            } else {
                if (mIsReverseDraw) {
                    mPath.moveTo(mCenterX, afterComplectedXPosition + mCircleRadius);
                    mPath.lineTo(mCenterX, preComplectedXPosition + mCircleRadius);
                    canvas.drawPath(mPath, mUnCompletedPaint);
                } else {
                    mPath.moveTo(mCenterX, preComplectedXPosition + mCircleRadius);
                    mPath.lineTo(mCenterX, afterComplectedXPosition - mCircleRadius);
                    canvas.drawPath(mPath, mUnCompletedPaint);
                }

            }
        }
        //-----------------------画线-------draw line-----------------------------------------------

        //-----------------------画图标-----draw icon-----------------------------------------------
        for (int i = 0; i < mCircleCenterPointPositionList.size(); i++) {
            final float currentComplectedXPosition = mCircleCenterPointPositionList.get(i);
            mRect = new Rect((int) (mCenterX - mCircleRadius), (int) (currentComplectedXPosition - mCircleRadius), (int) (mCenterX + mCircleRadius), (int) (currentComplectedXPosition + mCircleRadius));
            if (i < mComplectingPosition) {
                mCompleteIcon.setBounds(mRect);
                mCompleteIcon.draw(canvas);
            } else if (i == mComplectingPosition && mCircleCenterPointPositionList.size() != 1) {
                mCompletedPaint.setColor(Color.WHITE);
                canvas.drawCircle(mCenterX, currentComplectedXPosition, mCircleRadius * 1.1f, mCompletedPaint);
                mAttentionIcon.setBounds(mRect);
                mAttentionIcon.draw(canvas);
            } else {
                mDefaultIcon.setBounds(mRect);
                mDefaultIcon.draw(canvas);
            }
        }
        //-----------------------画图标-----draw icon-----------------------------------------------
    }


    /**
     * 设置流程步数
     *
     * @param stepNum 流程步数
     */
    public void setStepNum(int stepNum) {
        this.mStepNum = stepNum;
        requestLayout();
    }


    /**
     * 设置正在进行position
     *
     * @param complectingPosition
     */
    public void setComplectingPosition(int complectingPosition) {
        this.mComplectingPosition = complectingPosition;
        requestLayout();
    }

    /**
     * 设置未完成线的颜色
     *
     * @param unCompletedLineColor
     */
    public void setUnCompletedLineColor(int unCompletedLineColor) {
        this.mUnCompletedLineColor = unCompletedLineColor;
    }

    /**
     * 设置已完成线的颜色
     *
     * @param completedLineColor
     */
    public void setCompletedLineColor(int completedLineColor) {
        this.mCompletedLineColor = completedLineColor;
    }

    /**
     * is reverse draw 是否倒序画
     */
    public void reverseDraw(boolean isReverseDraw) {
        this.mIsReverseDraw = isReverseDraw;
        invalidate();
    }

    /**
     * 设置默认图片
     *
     * @param defaultIcon
     */
    public void setDefaultIcon(Drawable defaultIcon) {
        this.mDefaultIcon = defaultIcon;
    }

    /**
     * 设置已完成图片
     *
     * @param completeIcon
     */
    public void setCompleteIcon(Drawable completeIcon) {
        this.mCompleteIcon = completeIcon;
    }

    /**
     * 设置正在进行中的图片
     *
     * @param attentionIcon
     */
    public void setAttentionIcon(Drawable attentionIcon) {
        this.mAttentionIcon = attentionIcon;
    }


    @Override
    public void ondrawIndicator(List<Integer> positionList, int totalHeight) {
        positionCircleList = positionList;
        this.totalHeight = totalHeight;
    }
}
