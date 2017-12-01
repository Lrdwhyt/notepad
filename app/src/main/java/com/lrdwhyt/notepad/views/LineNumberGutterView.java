package com.lrdwhyt.notepad.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;

public class LineNumberGutterView extends View {

    Paint paint;
    EditText et;
    int rightEdge;
    int topEdgeOffset;
    NumberedEditText parent;

    public LineNumberGutterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.RIGHT);
        paint.setColor(Color.parseColor("#b0bec5"));
        paint.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, getResources().getDisplayMetrics()));
        Typeface robotoCondensedLight = Typeface.createFromAsset(getContext().getAssets(), "fonts/RobotoCondensed-Light.ttf");
        paint.setTypeface(robotoCondensedLight);
        rightEdge = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 27, getResources().getDisplayMetrics());
        topEdgeOffset = -((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, getResources().getDisplayMetrics()) - (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 14, getResources().getDisplayMetrics())) / 2;
    }

    @Override
    public void onFinishInflate() {
        this.parent = (NumberedEditText) getParent();
        super.onFinishInflate();
    }

    public void setBody(EditText nt) {
        this.et = nt;
        paint.setTextSize((float) (et.getTextSize() * 0.9));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (et != null) {
            int topEdge = et.getBaseline() + topEdgeOffset;
            int lineNumber = 1;
            Layout layout = et.getLayout();
            Editable textContent = et.getText();
            for (int i = 0; i < et.getLineCount(); ++i) {
                if (i == 0) {
                    canvas.drawText("1", rightEdge, topEdge, paint);
                    ++lineNumber;
                } else if (textContent.charAt(layout.getLineStart(i) - 1) == '\n') {
                    canvas.drawText(lineNumber + "", rightEdge, topEdge, paint);
                    ++lineNumber;
                }
                topEdge += et.getLineHeight();
            }
        }
        super.onDraw(canvas);
    }
}
