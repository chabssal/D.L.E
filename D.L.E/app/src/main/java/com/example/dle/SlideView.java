package com.example.dle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


public class SlideView extends View {

    private Paint mPaint;//그말대로 페인트
    private Bitmap slideButtonBitmap;//슬라이드 버튼 비트맵
    private String mText;
    boolean isTouchMode = false;
    private OnSwitchStateUpdateListener onSwitchStateUpdateListener;
    private int mBaseLineY;
    private float currentX;
    private Context c;

    //초기화
    public SlideView(Context context) {
        this(context, null);
    }

    public SlideView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public SlideView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initPaint();//그리기 호출
        TypedArray tyeparray = context.obtainStyledAttributes(attrs, R.styleable.SlideButton);
        setSlideButtonResource(tyeparray.getResourceId(R.styleable.SlideButton_slide_button, -1));
        Log.d("버튼: ",""+R.styleable.SlideButton_slide_button);
        Log.d("값2: ",""+slideButtonBitmap);
        setText(tyeparray.getString(R.styleable.SlideButton_android_text));
        setTextSize(tyeparray.getDimension(R.styleable.SlideButton_android_textSize,30));
        setTextColor(tyeparray.getColor(R.styleable.SlideButton_android_textColor, Color.BLACK));
        tyeparray.recycle();

    }
    /*
     *
     */
    private void initTextBaseLine() {
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float top = fontMetrics.top;
        float bottom = fontMetrics.bottom;
        mBaseLineY = (int) (getMeasuredHeight() / 2 - top / 2 - bottom / 2);
    }
    /*
     *
     */
    private void initPaint() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setAntiAlias(true);
    }
    /*
     *
     */
    public void setSlideButtonResource(int slideButton) {
        slideButtonBitmap = BitmapFactory.decodeResource(getContext().getResources(), slideButton);
        Log.d("값: ",""+slideButtonBitmap);
        Log.d("리소스: ",""+getResources());
    }
    /*
     *
     */
    public void setText(String text) {
        mText = text;
    }
    /*
     *
     */
    public void setTextSize(float textSize) {
        mPaint.setTextSize(textSize);
        mPaint.setStrokeWidth(textSize / 15.f);
    }
    /*
     *
     */
    public void setTextColor(int color) {
        mPaint.setColor(color);
    }
    /*
     *
     */
    private float getTextWidth() {
        return mPaint.measureText(mText);
    }
    /*
     *
     */

    private float getTextHeight() {
        return mPaint.getFontMetrics().bottom - mPaint.getFontMetrics().top;
    }
    /*
     *
     */
    public interface OnSwitchStateUpdateListener {

        void onStateUpdate();
    }
    /*
    *
    */
    public void setOnSwitchStateUpdateListener(
            OnSwitchStateUpdateListener onSwitchStateUpdateListener) {
        this.onSwitchStateUpdateListener = onSwitchStateUpdateListener;
    }
    @SuppressLint("DrawAllocation")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST) {
            int newWidth = (int) (slideButtonBitmap.getWidth() * 2 + getTextWidth());
            if (width >= newWidth)
                width = newWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            if (height < slideButtonBitmap.getHeight()) {

                int widthSlide = slideButtonBitmap.getWidth();
                int heightSlide = slideButtonBitmap.getHeight();
                float scaleHeight = height * 1.0f / slideButtonBitmap.getHeight();
                Matrix matrix = new Matrix();
                matrix.postScale(scaleHeight, scaleHeight);
                slideButtonBitmap = Bitmap.createBitmap(slideButtonBitmap, 0, 0, widthSlide,
                        heightSlide, matrix, true);
                invalidate();
            }
        }

        if (slideButtonBitmap.getWidth() > (width - getTextWidth()) / 2) {

            int widthSlide = slideButtonBitmap.getWidth();
            int heightSlide = slideButtonBitmap.getHeight();
            float scaleWidth = (width - getTextWidth()) / 2 / slideButtonBitmap.getWidth();
            Matrix matrix = new Matrix();
            matrix.postScale(scaleWidth, scaleWidth);
            slideButtonBitmap = Bitmap.createBitmap(slideButtonBitmap, 0, 0, widthSlide,
                    heightSlide, matrix, true);
            invalidate();
        }

        setMeasuredDimension(width, slideButtonBitmap.getHeight());
        initTextBaseLine();
    }
    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawText(mText, slideButtonBitmap.getWidth(), mBaseLineY, mPaint);

        if (isTouchMode) {

            float newLeft = currentX - slideButtonBitmap.getWidth() / 2.0f;

            int maxLeft = getMeasuredWidth() - slideButtonBitmap.getWidth();


            if (newLeft < 0) {
                newLeft = 0;
            } else if (newLeft > maxLeft) {
                newLeft = maxLeft;
            }

            canvas.drawBitmap(slideButtonBitmap, newLeft, 0, mPaint);
        } else {

            canvas.drawBitmap(slideButtonBitmap, 0, 0, mPaint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isTouchMode = true;
                currentX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                currentX = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                isTouchMode = false;
                currentX = event.getX();

                float center = getMeasuredWidth() / 2.0f;

                boolean isStateChanged = currentX > center;


                if (isStateChanged && onSwitchStateUpdateListener != null) {
                    onSwitchStateUpdateListener.onStateUpdate();
                }
                break;

            default:
                break;
        }


        invalidate();

        return true;
    }










}
