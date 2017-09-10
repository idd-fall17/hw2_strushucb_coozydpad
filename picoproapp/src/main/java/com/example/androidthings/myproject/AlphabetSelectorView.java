package com.example.androidthings.myproject;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Esteban on 9/1/2017.
 * AlphabetSelectorView is a RelativeLayout that displays three rows of alphabet letters
 * and displays a cursor. A model can update the view by changing the position of the cursor
 * and changing the text of the resulting message.
 */

public class AlphabetSelectorView extends RelativeLayout {
    private HighlightView highlight;
    private TextView result;
    private TextView row1;
    private TextView row2;
    private TextView row3;


    public AlphabetSelectorView(Context context){
        super(context);

        //This code was based on the conversation in this thread:
        //https://stackoverflow.com/questions/5191099/how-to-set-relativelayout-layout-params-in-code-not-in-xml
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

    //setMessage - updates the TextView showing the currently displayed message result.
    public void setMessage(String text){
        result.setText(text);
    }

    //setHighlight - adjusts the cursor position and redraws on the screen.
    public void setHighlight(int row, int column){
        highlight.setPosition(row, column);
        highlight.invalidate();
    }

/*HighlightView is simply a View that draws a red rectangle over the
specified area on the display acting as a cursor for the user.
 */
    private class HighlightView extends View {
        private final int CHAR_WIDTH = 36;
        private final int CHAR_HEIGHT = 40;
        private final int START_X1 = 240;
        private final int START_X2 = 270;
        private final int START_Y1 = 5;
        private final int START_Y2 = 40;

        private final int OPACITY = 30;

        private Paint paint;
        private int x;
        private int y;

        public HighlightView(Context context) {
            super(context);
            x = 0;
            y = 0;
            paint = new Paint();

            //set the Paint object to red with specified opacity
            paint.setStrokeWidth(0);
            paint.setARGB(OPACITY,255,0,0);
        }

        //setPosition updates the position of the cursor
        public void setPosition(int row, int column){
            //update the position of the highlight
            this.x = column;
            this.y = row;
        }

        @Override
        public void onDraw(Canvas canvas) {
            //draws a rectangle adjusted to the specified position
            canvas.drawRect(
                    START_X1+(x * CHAR_WIDTH),
                    START_Y1+(y * CHAR_HEIGHT),
                    START_X2+(x * CHAR_WIDTH),
                    START_Y2+(y * CHAR_HEIGHT),
                    paint);
        }

    }
}
