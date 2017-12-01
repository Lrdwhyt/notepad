package com.lrdwhyt.notepad.views;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.text.Layout;
import android.text.style.ClickableSpan;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class DynamicEditText extends AppCompatEditText {

    private NumberedEditText parent;

    public DynamicEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAutoLinkMask(Linkify.WEB_URLS);
        Linkify.addLinks(getText(), Linkify.WEB_URLS);
    }

    @Override
    public void onFinishInflate() {
        this.parent = (NumberedEditText) getParent();
        super.onFinishInflate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (parent != null) {
            parent.notifySizeChange();
        }
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= getTotalPaddingLeft();
            y -= getTotalPaddingTop();

            x += getScrollX();
            y += getScrollY();

            Layout layout = getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);
            final View currentView = this;

            final ClickableSpan[] link = getText().getSpans(off, off, ClickableSpan.class);

            if (link.length != 0) {
                AlertDialog.Builder linkConfirmDialogBuilder = new AlertDialog.Builder(getContext());
                linkConfirmDialogBuilder.setTitle(getText().subSequence(getText().getSpanStart(link[0]),getText().getSpanEnd(link[0])));
                linkConfirmDialogBuilder.setPositiveButton("Open link", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        link[0].onClick(currentView);
                    }
                });
                linkConfirmDialogBuilder.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog linkConfirmDialog = linkConfirmDialogBuilder.create();
                linkConfirmDialog.show();
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        if (id == android.R.id.paste) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                id = android.R.id.pasteAsPlainText;
            } else {
                parent.setPasteDetected(true);
            }
        }
        return super.onTextContextMenuItem(id);
    }

}
