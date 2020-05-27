//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.stevecrossin.mindlab;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff.Mode;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import androidx.annotation.AnyThread;
import androidx.annotation.ColorInt;
import androidx.annotation.WorkerThread;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HeatMapView extends View implements OnTouchListener {
    private List<DataPoint> data;
    private List<DataPoint> dataBuffer;
    private boolean dataModified = false;
    private double min = -1.0D / 0.0;
    private double max = 1.0D / 0.0;
    private double mBlur = 0.85D;
    private double mRadius = 200.0D;
    private int opacity = 0;
    private int minOpacity = 0;
    private int maxOpacity = 255;
    private double[] mRenderBoundaries = new double[4];
    @ColorInt
    private int[] colors = new int[]{-65536, -16711936};
    private float[] positions = new float[]{0.0F, 1.0F};
    private Paint mBlack;
    private boolean mTransparentBackground = true;
    private Paint mBackground;
    private Paint mFill;
    private int[] palette = null;
    private boolean needsRefresh = true;
    private boolean sizeChange = false;
    private float mTop = 0.0F;
    private float mLeft = 0.0F;
    private float mRight = 0.0F;
    private float mBottom = 0.0F;
    private Integer mMaxWidth = 0;
    private Integer mMaxHeight = 0;
    private HeatMapView.OnMapClickListener mListener;
    private Bitmap mShadow = null;
    private final Object tryRefreshLock = new Object();
    private boolean mUseDrawingCache = false;
    private float touchX;
    private float touchY;

    public void setRightPadding(int padding) {
        this.mRight = (float) padding;
    }

    public void setLeftPadding(int padding) {
        this.mLeft = (float) padding;
    }

    public void setTopPadding(int padding) {
        this.mTop = (float) padding;
    }

    public void setBottomPadding(int padding) {
        this.mBottom = (float) padding;
    }


    @AnyThread
    public void setBlur(double blur) {
        if (blur <= 1.0D && blur >= 0.0D) {
            this.mBlur = blur;
        } else {
            throw new IllegalArgumentException("Blur must be between 0 and 1.");
        }
    }

    @AnyThread
    public double getBlur() {
        return this.mBlur;
    }

    @AnyThread
    public void setMaximum(double max) {
        this.max = max;
    }

    @AnyThread
    public void setMinimum(double min) {
        this.min = min;
    }

    @AnyThread
    public void setOpacity(int opacity) {
        this.opacity = opacity;
    }

    @AnyThread
    public void setMinimumOpactity(int min) {
        this.minOpacity = min;
    }

    @AnyThread
    public void setMaximumOpactity(int max) {
        this.maxOpacity = max;
    }

    @AnyThread
    public void setRadius(double radius) {
        this.mRadius = radius;
    }

    public void setUseDrawingCache(boolean use) {
        this.mUseDrawingCache = use;
        this.invalidate();
    }

    public void setMaxDrawingWidth(int width) {
        this.mMaxWidth = width;
    }

    public void setMaxDrawingHeight(int height) {
        this.mMaxHeight = height;
    }

    @AnyThread
    public void setColorStops(Map<Float, Integer> stops) {
        if (stops.size() < 2) {
            throw new IllegalArgumentException("There must be at least 2 color stops");
        } else {
            this.colors = new int[stops.size()];
            this.positions = new float[stops.size()];
            int i = 0;

            for (Iterator var3 = stops.keySet().iterator(); var3.hasNext(); ++i) {
                Float key = (Float) var3.next();
                this.colors[i] = (Integer) stops.get(key);
                this.positions[i] = key;
            }

            if (!this.mTransparentBackground) {
                this.mBackground.setColor(this.colors[0]);
            }

        }
    }

    @AnyThread
    public void addData(HeatMapView.DataPoint point) {
        this.dataBuffer.add(point);
        this.dataModified = true;
    }

    @AnyThread
    public void addAllData(List<DataPoint> points) {
        Log.e("yxss", "添加数组长度：" + points.size());
        this.dataBuffer.addAll(points);
        this.dataModified = true;
    }

    @AnyThread
    public void clearData() {
        this.dataBuffer.clear();
        this.dataModified = true;
    }

    public void setOnMapClickListener(HeatMapView.OnMapClickListener listener) {
        this.mListener = listener;
    }

    /**
     * @deprecated
     */
    @Deprecated
    public void setOnTouchListener(OnTouchListener listener) {
        this.mListener = null;
        super.setOnTouchListener(listener);
    }

    public HeatMapView(Context context) {
        super(context);
        this.initialize();
    }

    public HeatMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.initialize();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.HeatMapView, 0, 0);

        try {
            this.opacity = a.getInt(R.styleable.HeatMapView_opacity, -1);
            if (this.opacity < 0) {
                this.opacity = 0;
            }

            this.minOpacity = a.getInt(R.styleable.HeatMapView_minOpacity, -1);
            if (this.minOpacity < 0) {
                this.minOpacity = 0;
            }

            this.maxOpacity = a.getInt(R.styleable.HeatMapView_maxOpacity, -1);
            if (this.maxOpacity < 0) {
                this.maxOpacity = 255;
            }

            this.mBlur = (double) a.getFloat(R.styleable.HeatMapView_blur, -1.0F);
            if (this.mBlur < 0.0D) {
                this.mBlur = 0.85D;
            }

            this.mRadius = (double) a.getDimension(R.styleable.HeatMapView_radius, -1.0F);
            if (this.mRadius < 0.0D) {
                this.mRadius = 200.0D;
            }

            float padding = a.getDimension(R.styleable.HeatMapView_dataPadding, -1.0F);
            if (padding < 0.0F) {
                padding = 0.0F;
            }

            this.mTop = a.getDimension(R.styleable.HeatMapView_dataPaddingTop, -1.0F);
            if (this.mTop < 0.0F) {
                this.mTop = padding;
            }

            this.mBottom = a.getDimension(R.styleable.HeatMapView_dataPaddingBottom, -1.0F);
            if (this.mBottom < 0.0F) {
                this.mBottom = padding;
            }

            this.mRight = a.getDimension(R.styleable.HeatMapView_dataPaddingRight, -1.0F);
            if (this.mRight < 0.0F) {
                this.mRight = padding;
            }

            this.mLeft = a.getDimension(R.styleable.HeatMapView_dataPaddingLeft, -1.0F);
            if (this.mLeft < 0.0F) {
                this.mLeft = padding;
            }

            this.mMaxWidth = (int) a.getDimension(R.styleable.HeatMapView_maxDrawingWidth, -1.0F);
            if (this.mMaxWidth < 0) {
                this.mMaxWidth = null;
            }

            this.mMaxHeight = (int) a.getDimension(R.styleable.HeatMapView_maxDrawingHeight, -1.0F);
            if (this.mMaxHeight < 0) {
                this.mMaxHeight = null;
            }

            this.mTransparentBackground = a.getBoolean(R.styleable.HeatMapView_transparentBackground, true);
        } finally {
            a.recycle();
        }

    }

    public void forceRefresh() {
        this.needsRefresh = true;
        this.invalidate();
    }

    private void initialize() {
        this.mBlack = new Paint();
        this.mBlack.setColor(-16777216);
        this.mFill = new Paint();
        this.mFill.setStyle(Style.FILL);
        this.mBackground = new Paint();
        if (!this.mTransparentBackground) {
            this.mBackground.setColor(-65794);
        }

        this.data = new ArrayList();
        this.dataBuffer = new ArrayList();
        super.setOnTouchListener(this);
        if (this.mUseDrawingCache) {
            this.setDrawingCacheEnabled(true);
            this.setDrawingCacheBackgroundColor(0);
        }

    }

    @AnyThread
    @SuppressLint({"WrongThread"})
    private int getDrawingWidth() {
        return this.mMaxWidth == null ? this.getWidth() : Math.min(this.calcMaxWidth(), this.getWidth());
    }

    @AnyThread
    @SuppressLint({"WrongThread"})
    private int getDrawingHeight() {
        return this.mMaxHeight == null ? this.getHeight() : Math.min(this.calcMaxHeight(), this.getHeight());
    }

    @AnyThread
    private float getScale() {
        if (this.mMaxWidth != null && this.mMaxHeight != null) {
            float sourceRatio = (float) (this.getWidth() / this.getHeight());
            float targetRatio = (float) (this.mMaxWidth / this.mMaxHeight);
            float scale;
            if (sourceRatio < targetRatio) {
                scale = (float) this.getWidth() / (float) this.mMaxWidth;
            } else {
                scale = (float) this.getHeight() / (float) this.mMaxHeight;
            }

            return scale;
        } else {
            return 1.0F;
        }
    }

    @AnyThread
    @SuppressLint({"WrongThread"})
    private int calcMaxHeight() {
        return (int) ((float) this.getHeight() / this.getScale());
    }

    @AnyThread
    @SuppressLint({"WrongThread"})
    private int calcMaxWidth() {
        return (int) ((float) this.getWidth() / this.getScale());
    }

    @AnyThread
    @SuppressLint({"WrongThread"})
    private void redrawShadow(int width, int height) {
        this.mRenderBoundaries[0] = 10000.0D;
        this.mRenderBoundaries[1] = 10000.0D;
        this.mRenderBoundaries[2] = 0.0D;
        this.mRenderBoundaries[3] = 0.0D;
        if (this.mUseDrawingCache) {
            this.mShadow = this.getDrawingCache();
        } else {
            this.mShadow = Bitmap.createBitmap(this.getDrawingWidth(), this.getDrawingHeight(), Config.ARGB_8888);
        }

        Canvas shadowCanvas = new Canvas(this.mShadow);
        this.drawTransparent(shadowCanvas, width, height);
    }

    @WorkerThread
    @SuppressLint({"WrongThread"})
    public void forceRefreshOnWorkerThread() {
        synchronized (this.tryRefreshLock) {
            this.tryRefresh(true, this.getDrawingWidth(), this.getDrawingHeight());
        }
    }

    @AnyThread
    private void tryRefresh(boolean forceRefresh, int width, int height) {
        if (!forceRefresh && !this.needsRefresh) {
            if (this.sizeChange) {
                this.redrawShadow(width, height);
            }
        } else {
            Bitmap bit = Bitmap.createBitmap(256, 1, Config.ARGB_8888);
            Canvas canvas = new Canvas(bit);
            LinearGradient grad = new LinearGradient(0.0F, 0.0F, 256.0F, 1.0F, this.colors, this.positions, TileMode.CLAMP);
            Paint paint = new Paint();
            paint.setStyle(Style.FILL);
            paint.setShader(grad);
            canvas.drawLine(0.0F, 0.0F, 256.0F, 1.0F, paint);
            this.palette = new int[256];
            bit.getPixels(this.palette, 0, 256, 0, 0, 256, 1);
            if (this.dataModified) {
                this.data.clear();
                this.data.addAll(this.dataBuffer);
                this.dataBuffer.clear();
                this.dataModified = false;
            }

            this.redrawShadow(width, height);
        }

        this.needsRefresh = false;
        this.sizeChange = false;
    }

    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (this.mMaxWidth == null || this.mMaxHeight == null) {
            this.sizeChange = true;
        }

    }

    protected void onDraw(Canvas canvas) {
        synchronized (this.tryRefreshLock) {
            this.tryRefresh(false, this.getDrawingWidth(), this.getDrawingHeight());
        }

        this.drawColour(canvas);
    }

    @AnyThread
    private void drawDataPoint(Canvas canvas, float x, float y, double radius, double blurFactor, double alpha) {
        if (blurFactor == 1.0D) {
            canvas.drawCircle(x, y, (float) radius, this.mBlack);
        } else {
            RadialGradient gradient = new RadialGradient(x, y, (float) (radius * blurFactor), new int[]{Color.argb((int) (alpha * 255.0D), 0, 0, 0), Color.argb(0, 0, 0, 0)}, (float[]) null, TileMode.CLAMP);
            this.mFill.setShader(gradient);
            canvas.drawCircle(x, y, (float) (2.0D * radius), this.mFill);
        }

    }

    @AnyThread
    private void drawTransparent(Canvas canvas, int width, int height) {
        double blur = 1.0D - this.mBlur;
        canvas.drawColor(0, Mode.CLEAR);
        float scale = this.getScale();
        float top = this.mTop / scale;
        float bottom = this.mBottom / scale;
        float left = this.mLeft / scale;
        float right = this.mRight / scale;
        float w = (float) width - left - right;
        float h = (float) height - top - bottom;
        Iterator var13 = this.data.iterator();

        while (var13.hasNext()) {
            HeatMapView.DataPoint point = (HeatMapView.DataPoint) var13.next();
            float x = point.x * w + left;
            float y = point.y * h + top;
            double value = Math.max(this.min, Math.min(point.value, this.max));
            double rectX = (double) x - this.mRadius;
            double rectY = (double) y - this.mRadius;
            double alpha = (value - this.min) / (this.max - this.min);
            this.drawDataPoint(canvas, x, y, this.mRadius, blur, alpha);
            if (rectX < this.mRenderBoundaries[0]) {
                this.mRenderBoundaries[0] = rectX;
            }

            if (rectY < this.mRenderBoundaries[1]) {
                this.mRenderBoundaries[1] = rectY;
            }

            if (rectX + 2.0D * this.mRadius > this.mRenderBoundaries[2]) {
                this.mRenderBoundaries[2] = rectX + 2.0D * this.mRadius;
            }

            if (rectY + 2.0D * this.mRadius > this.mRenderBoundaries[3]) {
                this.mRenderBoundaries[3] = rectY + 2.0D * this.mRadius;
            }
        }

    }

    private void drawColour(Canvas canvas) {
        if (this.data.size() != 0) {
            int x = (int) (Math.round(this.mRenderBoundaries[0]));
            int y = (int) (Math.round(this.mRenderBoundaries[1]));
            int width = (int) (Math.round(this.mRenderBoundaries[2]));
            int height = (int) (Math.round(this.mRenderBoundaries[3]));
            int maxWidth = this.getDrawingWidth();
            int maxHeight = this.getDrawingHeight();
            if (x < 0) {
                x = 0;
            }

            if (y < 0) {
                y = 0;
            }
            Log.e("yxs", "X：" + x + "===Width：" + width + "===MaxWidths：" + maxWidth);
            if (x + width > maxWidth) {
                width = maxWidth - x;
            }

            //      if (width < 0)
            //        width = maxWidth;

            if (y + height > maxHeight) {
                height = maxHeight - y;
            }
            Log.e("yxs", "宽度：" + width);
            int[] pixels = new int[width];

            for (int j = 0; j < height; ++j) {
                this.mShadow.getPixels(pixels, 0, width, x, y + j, width, 1);

                for (int i = 0; i < width; ++i) {
                    int pixel = pixels[i];
                    int alpha = 255 & pixel >> 24;
                    int clampAlpha;
                    if (this.opacity > 0) {
                        clampAlpha = this.opacity;
                    } else if (alpha < this.maxOpacity) {
                        if (alpha < this.minOpacity) {
                            clampAlpha = this.minOpacity;
                        } else {
                            clampAlpha = alpha;
                        }
                    } else {
                        clampAlpha = this.maxOpacity;
                    }

                    pixels[i] = (255 & clampAlpha) << 24 | 16777215 & this.palette[alpha];
                }

                this.mShadow.setPixels(pixels, 0, width, x, y + j, width, 1);
            }

            if (!this.mTransparentBackground) {
                canvas.drawRect(0.0F, 0.0F, (float) this.getWidth(), (float) this.getHeight(), this.mBackground);
            }

            canvas.drawBitmap(this.mShadow, new Rect(0, 0, this.getDrawingWidth(), this.getDrawingHeight()), new Rect(0, 0, this.getWidth(), this.getHeight()), (Paint) null);
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (this.mListener != null) {
            if (motionEvent.getAction() == 1) {
                float x = motionEvent.getX();
                float y = motionEvent.getY();
                double d = Math.sqrt(Math.pow((double) (this.touchX - x), 2.0D) + Math.pow((double) (this.touchY - y), 2.0D));
                if (d < 10.0D) {
                    x /= (float) this.getWidth();
                    y /= (float) this.getHeight();
                    double minDist = 1.7976931348623157E308D;
                    HeatMapView.DataPoint minPoint = null;
                    Iterator var10 = this.data.iterator();

                    while (var10.hasNext()) {
                        HeatMapView.DataPoint point = (HeatMapView.DataPoint) var10.next();
                        double dist = point.distanceTo(x, y);
                        if (dist < minDist) {
                            minDist = dist;
                            minPoint = point;
                        }
                    }

                    this.mListener.onMapClicked((int) x, (int) y, minPoint);
                    return true;
                }
            } else if (motionEvent.getAction() == 0) {
                this.touchX = motionEvent.getX();
                this.touchY = motionEvent.getY();
                return true;
            }
        }

        return false;
    }

    public interface OnMapClickListener {
        void onMapClicked(int var1, int var2, HeatMapView.DataPoint var3);
    }

    public static class DataPoint {
        public float x;
        public float y;
        public double value;
        public Object userData = null;

        public DataPoint(float x, float y, double value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }

        double distanceTo(float x, float y) {
            return Math.sqrt(Math.pow((double) (x - this.x), 2.0D) + Math.pow((double) (y - this.y), 2.0D));
        }
    }
}
