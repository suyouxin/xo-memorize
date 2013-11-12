package com.morphoss.xo.memorize;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.morphoss.xo.memorize.obj.MemoryObj;

public class ObjView extends View {
	float mDpi;

	final static public int CORNER_SIZE_DP = 10;
	final static public int BORDER_SIZE_DP = 5;

	private MemoryObj mObj;

	public ObjView(Context context) {
		this(context, null);

	}

	public ObjView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ObjView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mDpi = (float) context.getResources().getDisplayMetrics().densityDpi / 160f;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

	}

	public void setObj(MemoryObj obj) {
		mObj = obj;
	}

	public MemoryObj getObj() {
		return mObj;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		if (mObj == null)
			return;
		super.onDraw(canvas);
		switch (mObj.getMode()) {
		case MemoryObj.MEMORY_OBJ_MODE_SHOWN:
			drawBackground(canvas, Color.rgb(7, 86, 91), false, true,
					Color.rgb(118, 81, 28));
			mObj.draw(canvas);
			break;
		case MemoryObj.MEMORY_OBJ_MODE_MATCHED:
			drawBackground(canvas, Color.rgb(4, 79, 75), false, true,
					Color.rgb(180, 255, 0));
			mObj.draw(canvas);
			break;
		case MemoryObj.MEMORY_OBJ_MODE_HIDEN:
		default:
			drawBackground(canvas, Color.rgb(218, 238, 155), true, false, 0);
			break;
		}
	}

	private void drawBackground(Canvas canvas, int color, boolean withGroup,
			boolean withBorder, int borderColor) {
		int cornerSizePx = (int) (CORNER_SIZE_DP * mDpi);
		int borderSizePx = (int) (BORDER_SIZE_DP * mDpi);

		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setStyle(Paint.Style.FILL);

		RectF rf = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
		RectF rfSmall = new RectF(borderSizePx, borderSizePx, canvas.getWidth()
				- borderSizePx, canvas.getHeight() - borderSizePx);

		if (withBorder) {
			p.setColor(borderColor);
			p.setStyle(Paint.Style.FILL);
			canvas.drawRoundRect(rf, cornerSizePx, cornerSizePx, p);
			p.setColor(color);
			canvas.drawRoundRect(rfSmall, cornerSizePx, cornerSizePx, p);
		} else {
			p.setColor(color);
			canvas.drawRoundRect(rf, cornerSizePx, cornerSizePx, p);
		}
		if (withGroup) {
			Bitmap bmp;
			Log.d("theme", "value of theme : "+GameCanvas.theme);
			if (GameCanvas.theme == 1) {
				if (mObj.getGroupId() == 1) {
					bmp = BitmapFactory.decodeResource(getResources(),
							R.drawable.bg_1);
				} else {
					bmp = BitmapFactory.decodeResource(getResources(),
							R.drawable.bg_2);
				}
			}
			if (GameCanvas.theme == 2) {
				if (mObj.getGroupId() == 1) {
					bmp = BitmapFactory.decodeResource(getResources(),
							R.drawable.bg_3);
				} else {
					bmp = BitmapFactory.decodeResource(getResources(),
							R.drawable.bg_4);
				}
			}
			if (GameCanvas.theme == 3) {
				if (mObj.getGroupId() == 1) {
					bmp = BitmapFactory.decodeResource(getResources(),
							R.drawable.bg_5);
				} else {
					bmp = BitmapFactory.decodeResource(getResources(),
							R.drawable.bg_6);
				}
			}
			if (GameCanvas.theme == 4) {
				if (mObj.getGroupId() == 1) {
					bmp = BitmapFactory.decodeResource(getResources(),
							R.drawable.bg_7);
				} else {
					bmp = BitmapFactory.decodeResource(getResources(),
							R.drawable.bg_8);
				}
			}else {
				if (mObj.getGroupId() == 1) {
					bmp = BitmapFactory.decodeResource(getResources(),
							R.drawable.bg_1);
				} else {
					bmp = BitmapFactory.decodeResource(getResources(),
							R.drawable.bg_2);
				}
			}
			int reduce = ObjView.BORDER_SIZE_DP;

			Rect rc = new Rect(reduce, reduce, canvas.getWidth() - reduce,
					canvas.getHeight() - reduce);
			Paint paint = new Paint();
			canvas.drawBitmap(bmp, null, rc, paint);
		}
	}
}