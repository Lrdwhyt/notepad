package com.lrdwhyt.notepad.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.lrdwhyt.notepad.DatabaseManager;
import com.lrdwhyt.notepad.EditorContract;
import com.lrdwhyt.notepad.EditorPresenter;
import com.lrdwhyt.notepad.Model;
import com.lrdwhyt.notepad.R;

import java.util.List;

public class EditorActivity extends AppCompatActivity implements EditorContract.View {

    private EditText et;
    private List<String> tags;
    EditorContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.str_activity_editor);

        et = (EditText) findViewById(R.id.edit_text);

        attachPresenter();

        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().get("noteId") != null) {
                long noteId = Long.parseLong(getIntent().getExtras().getString("noteId"));
                presenter.setNoteId(noteId);
                presenter.initialiseText();
            }
        }

    }

    @Override
    public void initialiseText(String text) {
        et.append(text);
    }

    @Override
    public void setTextSize(Float textSize) {
        et.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
    }

    private void attachPresenter() {
        if (presenter == null) {
            DatabaseManager dbh = new DatabaseManager(getApplicationContext());
            SharedPreferences sp = getSharedPreferences(getPackageName() + "_preferences", Context.MODE_PRIVATE);
            Model model = new Model(dbh, sp);
            presenter = new EditorPresenter(model, this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.post, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        if (itemId == R.id.action_save) {
            saveToDatabase();
            return true;
        } else if (itemId == R.id.action_tag) {
            // Open dialogue to edit tags
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onPause() {

        saveToDatabase();
        super.onPause();

    }

    private void saveToDatabase() {
        presenter.updateNote(et.getText().toString());
    }

}
