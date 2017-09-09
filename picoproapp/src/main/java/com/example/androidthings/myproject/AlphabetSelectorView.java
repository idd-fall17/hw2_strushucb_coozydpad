package com.example.androidthings.myproject;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Esteban on 9/1/2017.
 */

public class AlphabetSelectorView extends RelativeLayout {
    private HighlightView highlight;
    private TextView result;
    private TextView row1;
    private TextView row2;
    private TextView row3;
    public static final int ABC = 1;
    public static final int DEF = 2;
    public static final int GHI = 3;

    //https://www.techyourchance.com/mvp-mvc-android-2/
    public AlphabetSelectorView(Context context){
        super(context);


        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);
        row1 = new TextView(context);
        row1.setText("A B C D E F G H I");
        row1.setTextAppearance(R.style.SelectText);
        row1.setId(View.generateViewId());
        params.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        this.addView(row1, params);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        row2 = new TextView(context);
        row2.setText("J K L M N O P Q R");
        row2.setTextAppearance(R.style.SelectText);
        row2.setId(View.generateViewId());
        params.addRule(RelativeLayout.BELOW, row1.getId());
        params.addRule(RelativeLayout.ALIGN_START,row1.getId());
        this.addView(row2, params);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        row3 = new TextView(context);
        row3.setText("S T U V W X Y Z _");
        row3.setTextAppearance(R.style.SelectText);
        row3.setId(View.generateViewId());
        params.addRule(RelativeLayout.BELOW, row2.getId());
        params.addRule(RelativeLayout.ALIGN_START,row1.getId());
        this.addView(row3, params);

        params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        result = new TextView(context);
        result.setText(" ");
        result.setTextAppearance(R.style.ResultText);
        result.setId(View.generateViewId());
        params.addRule(RelativeLayout.CENTER_VERTICAL);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        this.addView(result, params);

        highlight = new HighlightView(this.getContext());
        this.addView(highlight);
    }

    public void setMessage(String text){
        result.setText(text);
    }


    public void setHighlight(int row, int column){
        highlight.setPosition(row, column);
        highlight.invalidate();
    }


    private class HighlightView extends View {
        Paint paint = new Paint();
        private int x;
        private int y;
        public HighlightView(Context context) {
            super(context);
            x = 0;
            y = 0;
        }

        public void setPosition(int row, int column){
            this.x = column;
            this.y = row;
        }

        @Override
        public void onDraw(Canvas canvas) {
            paint.setStrokeWidth(0);
            paint.setARGB(30,255,0,0);
            canvas.drawRect(240+(x*36), 5+(y * 40), 270+(x*36), 40+(y * 40), paint);
        }

    }
}
