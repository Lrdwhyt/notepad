package com.lrdwhyt.notepad;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by lrdwh on 2017-09-04.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteHolder> {


    private List<NoteEntry> values;
    int width;
    View.OnLongClickListener olcl;

    public NoteAdapter(List<NoteEntry> values) {
        this.values = values;
    }

    public NoteAdapter(List<NoteEntry> values, int width) {
        this(values);
        this.width = width;
    }

    public NoteAdapter(List<NoteEntry> values, int width, View.OnLongClickListener olcl) {
        this(values, width);
        this.olcl = olcl;
    }

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        View v = li.inflate(R.layout.layout_note, parent, false);
        return new NoteHolder(v, width);
    }

    @Override
    public void onBindViewHolder(NoteHolder holder, int position) {
        String text = values.get(position).text;
        holder.textBody.setText(text);
        holder.cardView.setTag(String.valueOf(values.get(position)._id));
        holder.cardView.setOnLongClickListener(olcl);
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

}
