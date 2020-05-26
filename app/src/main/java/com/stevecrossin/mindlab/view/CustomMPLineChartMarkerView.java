package com.stevecrossin.mindlab.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.TypedValue;
import android.widget.TextView;

import com.stevecrossin.mindlab.R;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

public class CustomMPLineChartMarkerView extends MarkerView {
    private final int DEFAULT_INDICATOR_COLOR = 0xffFD9138;//Indicator default color
    private final int ARROW_HEIGHT = dp2px(5); // Arrow height
    private final int ARROW_WIDTH = dp2px(10); // Arrow width
    private final float ARROW_OFFSET = dp2px(2);// Arrow offset
    private final float BG_CORNER = dp2px(2);// Background rounded corners
    private final TextView tvContent;//Text
    private Bitmap bitmapForDot;//Selected dot picture
    private int bitmapWidth;//Dot width
    private int bitmapHeight;//Dot height

    public CustomMPLineChartMarkerView(Context context) {
        super(context, R.layout.layout_for_custom_marker_view);
        tvContent = findViewById(R.id.tvContent);
        //Picture replacement
        bitmapForDot = BitmapFactory.decodeResource(getResources(), R.drawable.img1);
        bitmapWidth = 30;
        bitmapHeight = 30;
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        if (e instanceof CandleEntry) {
            CandleEntry ce = (CandleEntry) e;
            tvContent.setText((int) ce.getHigh() + "");
        } else {
            tvContent.setText((int) e.getY() + "");
        }

        super.refreshContent(e, highlight);
    }

    @Override
    public void draw(Canvas canvas, float posX, float posY) {
        Chart chart = getChartView();
        if (chart == null) {
            super.draw(canvas, posX, posY);
            return;
        }

        //Indicator background brush
        Paint bgPaint = new Paint();
        bgPaint.setStyle(Paint.Style.FILL);
        bgPaint.setAntiAlias(true);
        bgPaint.setColor(DEFAULT_INDICATOR_COLOR);
        //arrow paint brush
        Paint arrowPaint = new Paint();
        arrowPaint.setStyle(Paint.Style.FILL);
        arrowPaint.setAntiAlias(true);
        arrowPaint.setColor(DEFAULT_INDICATOR_COLOR);

        float width = getWidth();
        float height = getHeight();

        int saveId = canvas.save();
        //Move the canvas to the point and draw the point
        canvas.translate(posX, posY);
        //Draw indicator
        Path path = new Path();
        RectF bRectF;
        if (posY < height + ARROW_HEIGHT + ARROW_OFFSET + bitmapHeight / 2f) {//Handling beyond the upper boundary
            //Move the canvas and draw triangles and background
            canvas.translate(0, height + ARROW_HEIGHT + ARROW_OFFSET + bitmapHeight / 2f);
            //Draw triangle
            path.moveTo(0, -(height + ARROW_HEIGHT));
            path.lineTo(ARROW_WIDTH / 2f, -(height - BG_CORNER));
            path.lineTo(-ARROW_WIDTH / 2f, -(height - BG_CORNER));
            path.lineTo(0, -(height + ARROW_HEIGHT));

            bRectF = new RectF(-width / 2, -height, width / 2, 0);

            canvas.drawPath(path, arrowPaint);
            //Draw rectangle
            canvas.drawRoundRect(bRectF, BG_CORNER, BG_CORNER, bgPaint);
            canvas.translate(-width / 2f, -height);
        } else {//Did not exceed the upper boundary
            //Move the canvas and draw triangles and background
            canvas.translate(0, -height - ARROW_HEIGHT - ARROW_OFFSET - bitmapHeight / 2f);
            path.moveTo(0, height + ARROW_HEIGHT);
            path.lineTo(ARROW_WIDTH / 2f, height - BG_CORNER);
            path.lineTo(-ARROW_WIDTH / 2f, height - BG_CORNER);
            path.lineTo(0, height + ARROW_HEIGHT);

            bRectF = new RectF(-width / 2, 0, width / 2, height);

            canvas.drawPath(path, arrowPaint);
            canvas.drawRoundRect(bRectF, BG_CORNER, BG_CORNER, bgPaint);
            canvas.translate(-width / 2f, 0);
        }
        draw(canvas);
        canvas.restoreToCount(saveId);
    }

    /**
     * Unit dp and px conversion
     *
     * @param dpValues
     * @return
     */
    private int dp2px(int dpValues) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dpValues,
                getResources().getDisplayMetrics());
    }
}