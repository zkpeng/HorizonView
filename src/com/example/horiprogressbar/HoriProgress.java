package com.example.horiprogressbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.FontMetrics;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

public class HoriProgress extends View implements Runnable {

	private Paint mReachLinePaint;
	private Paint mUnReachLinePaint;
	private TextPaint mTextPaint;
	private boolean isRunning = false;

	// 属性暂时写死了
	private int mFinishColor = Color.GREEN;
	private int mUnFinishColor = Color.RED;
	private int mTextColor = Color.BLACK;
	private int mLineHeight = 4;
	private int mTextSize = 20;

	private float mMax = 100.0f;
	private int mCurrent = 0;

	public HoriProgress(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO 自动生成的构造函数存根
		init();
	}

	private void init() {
		mReachLinePaint = new Paint();
		mReachLinePaint.setAntiAlias(true);
		mReachLinePaint.setStyle(Paint.Style.STROKE);
		mReachLinePaint.setStrokeWidth(mLineHeight);
		mReachLinePaint.setColor(mFinishColor);
		mReachLinePaint.setStrokeCap(Cap.ROUND);

		mUnReachLinePaint = new Paint();
		mUnReachLinePaint.setAntiAlias(true);
		mUnReachLinePaint.setStyle(Paint.Style.STROKE);
		mUnReachLinePaint.setStrokeWidth(mLineHeight);
		mUnReachLinePaint.setColor(mUnFinishColor);
		mUnReachLinePaint.setStrokeCap(Cap.ROUND);

		mTextPaint = new TextPaint();
		mTextPaint.setAntiAlias(true);
		mTextPaint.setColor(mTextColor);
		mTextPaint.setTextSize(mTextSize);
		// mTextPaint.measureText(mCurrent+"%");
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO 自动生成的方法存根
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSpec = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSpec = MeasureSpec.getSize(heightMeasureSpec);

		if (heightMode != MeasureSpec.EXACTLY) {
			float textheight = Math.abs(mTextPaint.ascent())
					+ Math.abs(mTextPaint.descent());
			heightSpec = (int) Math.max(textheight, mLineHeight);
			heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSpec,
					MeasureSpec.EXACTLY);
		}
		if (widthMode != MeasureSpec.EXACTLY) {
			widthSpec = 300;
			widthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSpec,
					MeasureSpec.EXACTLY);
		}
		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO 自动生成的方法存根
		// super.onDraw(canvas);
		int left = getPaddingLeft();
		int top = getPaddingTop();
		canvas.save();
		canvas.translate(left, top);
		FontMetrics metrics = mTextPaint.getFontMetrics();

		float percent = mCurrent / mMax;
		float textWidth = mTextPaint.measureText(mCurrent + "%");
		float tLength = getMeasuredWidth() - getPaddingLeft()
				- getPaddingRight() - textWidth;
		float fLength = tLength * percent;
		float uLength = tLength * (1 - percent);
		canvas.drawLine(0, getMeasuredHeight() / 2, fLength,
				getMeasuredHeight() / 2, mReachLinePaint);
		canvas.drawText(mCurrent + "%", fLength, Math.abs(metrics.ascent),
				mTextPaint);
		canvas.drawLine(fLength + textWidth, getMeasuredHeight() / 2, fLength
				+ textWidth + uLength, getMeasuredHeight() / 2,
				mUnReachLinePaint);

		canvas.restore();
	}

	@Override
	public void run() {
		// TODO 自动生成的方法存根
		isRunning = true;
		while (isRunning) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			postInvalidate();
			mCurrent += 10;
			if (mCurrent > 100) {
				mCurrent = 100;
				isRunning = false;
			}
		}
		mCurrent = 0;
	}
}
