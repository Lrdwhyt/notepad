package com.lrdwhyt.notepad.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.lrdwhyt.notepad.DatabaseManager;
import com.lrdwhyt.notepad.EditorContract;
import com.lrdwhyt.notepad.EditorPresenter;
import com.lrdwhyt.notepad.Model;
import com.lrdwhyt.notepad.R;

import java.util.Arrays;
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
            openTagSelectionDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private void openTagSelectionDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        String[] tagNames = presenter.getTagNames();
        boolean[] tagStates = presenter.getTagStates();
        final boolean[] tagSelections = Arrays.copyOf(tagStates, tagStates.length);
        final boolean[] checkedTags = tagSelections;
        adb.setTitle("Tag");
        adb.setMultiChoiceItems(tagNames, tagSelections, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                checkedTags[which] = isChecked;
            }
        });
        adb.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                presenter.updateTags(checkedTags);
            }
        });
        adb.setNeutralButton("Add tag", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog ad = adb.create();
        ad.show();
        ad.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewTagDialog();
            }
        });
    }

    private void openNewTagDialog() {
        AlertDialog.Builder bdb = new AlertDialog.Builder(this);
        bdb.setTitle("Add tag");
        final EditText et = new EditText(this);
        bdb.setView(et);
        bdb.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String newTag = et.getText().toString();
                presenter.createTag(newTag);
                // Restart tag dialog
            }
        });
        bdb.create().show();
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
