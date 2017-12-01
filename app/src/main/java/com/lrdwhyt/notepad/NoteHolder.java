package com.lrdwhyt.notepad;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class NoteHolder extends RecyclerView.ViewHolder {

    public CardView cardView;
    public TextView textBody;

    public NoteHolder(View view) {
        super(view);
        cardView = (CardView) view.findViewById(R.id.card_view);
        textBody = (TextView) view.findViewById(R.id.text_body);
    }

    public NoteHolder(View view, int minWidth) {
        this(view);
        textBody.setMinimumWidth(minWidth);
    }

}
