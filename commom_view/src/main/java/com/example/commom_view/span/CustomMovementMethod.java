package com.example.commom_view.span;

import android.text.Layout;
import android.text.Spannable;
import android.text.method.BaseMovementMethod;
import android.text.style.ClickableSpan;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * 这个是为了解决 LinkMovementMethod 在列表会有textview滑动导致有时候出现边缘被切割的情况
 */
public class CustomMovementMethod extends BaseMovementMethod {
    private static CustomMovementMethod customMovementMethod;

    public static CustomMovementMethod getInstance() {
        if (customMovementMethod == null) {
            customMovementMethod = new CustomMovementMethod();
        }
        return customMovementMethod;
    }

    @Override
    public boolean onTouchEvent(TextView widget, Spannable buffer, MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            ClickableSpan[] link = buffer.getSpans(off, off, ClickableSpan.class);

            if (link.length != 0) {
                if (action == MotionEvent.ACTION_UP) {
                    //除了点击事件，我们不要其他东西
                    link[0].onClick(widget);
                }
                return true;
            }
        }
        return super.onTouchEvent(widget, buffer, event);
    }

    private CustomMovementMethod() {

    }
}
