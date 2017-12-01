package com.lrdwhyt.notepad.views;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.CharacterStyle;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.lrdwhyt.notepad.R;

public class NumberedEditText extends LinearLayout {

    LineNumberGutterView lng;
    DynamicEditText cet;
    boolean isPasteDetected = false;

    public NumberedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onFinishInflate() {
        cet = (DynamicEditText) findViewById(R.id.edit_text);
        lng = (LineNumberGutterView) findViewById(R.id.lineNumberView);
        lng.setBody(cet);

        cet.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (isPasteDetected && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    Object spansToRemove[] = s.getSpans(0, s.length(), Object.class);
                    for (Object span : spansToRemove) {
                        if (span instanceof CharacterStyle) {
                            s.removeSpan(span);
                        }
                    }
                    isPasteDetected = false;
                }
                Linkify.addLinks(s, Linkify.WEB_URLS);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Updates line numbers even while keyboard is open
                lng.setMinimumHeight(cet.getLineHeight() * cet.getLineCount() + cet.getBaseline());
            }
        });

        super.onFinishInflate();
    }

    public void notifySizeChange() {
        lng.setMinimumHeight(cet.getLineHeight() * cet.getLineCount() + cet.getBaseline());
    }

    public void setPasteDetected(boolean newState) {
        isPasteDetected = newState;
    }

}
