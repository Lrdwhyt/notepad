package com.lrdwhyt.notepad.notes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.lrdwhyt.notepad.DatabaseManager;
import com.lrdwhyt.notepad.Model;
import com.lrdwhyt.notepad.NoteAdapter;
import com.lrdwhyt.notepad.activities.EditorActivity;
import com.lrdwhyt.notepad.activities.SettingsActivity;
import com.lrdwhyt.notepad.R;

import java.util.ArrayList;
import java.util.List;

public class NotesActivity extends AppCompatActivity implements NotesContract.View {

    private Intent drawerClosedIntent;
    private boolean isSelectionMode = false;
    private List<Long> selectedNotes = new ArrayList<Long>();
    private List<View> selectedNoteViews = new ArrayList<View>();
    private Toolbar toolbar;
    private NotesContract.Presenter presenter;
    private RecyclerView noteContainer;
    private ActionBarDrawerToggle abdt;
    private DrawerLayout dl;
    private DrawerLayout.DrawerListener dldl;
    private View.OnClickListener fabClickListener;
    private View.OnLongClickListener noteLongClickListener;
    private NavigationView navigationView;
    NavigationView.OnNavigationItemSelectedListener onisl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_note_overview);

        initialiseToolbar();
        initialiseDrawer();
        initialiseFloatingActionButton();

        noteContainer = (RecyclerView) findViewById(R.id.recyclerView);

        attachPresenter();
    }

    @Override
    protected void onDestroy() {
        // Remove listeners
        dl.removeDrawerListener(abdt);
        dl.removeDrawerListener(dldl);
        navigationView.setNavigationItemSelectedListener(null);
        fabClickListener = null;
        noteLongClickListener = null;
        super.onDestroy();
    }

    @Override
    public void setNumberColumns(int numberColumns) {
        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(numberColumns, StaggeredGridLayoutManager.VERTICAL);
        noteContainer.setLayoutManager(sglm);
    }

    private void attachPresenter() {
        if (presenter == null) {
            DatabaseManager dbh = new DatabaseManager(getApplicationContext());
            SharedPreferences sp = getSharedPreferences(getPackageName() + "_preferences", Context.MODE_PRIVATE);
            Model model = new Model(dbh, sp);
            presenter = new NotesPresenter(model, this);
        }
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return presenter;
    }

    public void initialiseToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_main);
        setSupportActionBar(toolbar);
    }

    public void initialiseFloatingActionButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fabClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NotesActivity.this, EditorActivity.class));
            }
        };
        fab.setOnClickListener(fabClickListener);
    }

    public void initialiseDrawer() {
        dl = (DrawerLayout) findViewById(R.id.drawer_layout);
        abdt = new ActionBarDrawerToggle(
                this, dl, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dl.addDrawerListener(abdt);
        abdt.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        onisl = new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.nav_overview) {
                    presenter.browseAll();
                } else if (itemId == R.id.nav_settings) {
                    drawerClosedIntent = new Intent(NotesActivity.this, SettingsActivity.class);
                } else {

                }
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        };
        navigationView.setNavigationItemSelectedListener(onisl);

        navigationView.inflateMenu(R.menu.nav_drawer);

        dldl = new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {}

            @Override
            public void onDrawerOpened(View drawerView) {}

            @Override
            public void onDrawerClosed(View drawerView) {
                if (drawerClosedIntent != null) {
                    startActivity(drawerClosedIntent);
                    drawerClosedIntent = null;
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {}
        };
        dl.addDrawerListener(dldl);

        navigationView.setCheckedItem(R.id.nav_overview);
    }

    @Override
    public void populateDrawer(List<String> drawerItems) {
        SubMenu sm = navigationView.getMenu().findItem(R.id.drawer_tags).getSubMenu();
        for (final String tagName : drawerItems) {
            MenuItem mi = sm.add(0, 0, 0, tagName);
            mi.setCheckable(true);
            mi.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    presenter.browseTag(tagName);
                    navigationView.setCheckedItem(item.getItemId());
                    return false;
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (isSelectionMode) {
            exitSelectionMode();
            return;
        }
        if (dl.isDrawerOpen(GravityCompat.START)) {
            dl.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (!isSelectionMode) {
            getMenuInflater().inflate(R.menu.main, menu);
        } else {
            getMenuInflater().inflate(R.menu.note_selection, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int itemId = item.getItemId();

        if (itemId == R.id.action_delete) {
            presenter.onDeleteSelectedNotesClick(new ArrayList(selectedNotes));
            exitSelectionMode();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onNoteClick(View v) {
        if (isSelectionMode) {
            toggleNoteSelection(v);
            return;
        }
        Intent i = new Intent(this, EditorActivity.class);
        i.putExtra("noteId", v.getTag().toString());
        // Sending note information to note editor activity
        startActivity(i);
    }

    public void onNoteLongClick(View v) {
        toggleNoteSelection(v);
    }

    public void updateSelectionModeToolbarTitle() {
        toolbar.setTitle(String.valueOf(selectedNotes.size()));
    }

    public void setSelectionModeToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().invalidateOptionsMenu();
        getSupportActionBar().setTitle(String.valueOf(selectedNotes.size()));
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        dl.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        abdt = new ActionBarDrawerToggle(
                this, dl, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        abdt.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        abdt.setDrawerIndicatorEnabled(false);
        abdt.syncState();
    }

    public void restoreDefaultToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().invalidateOptionsMenu();
        getSupportActionBar().setTitle(R.string.title_activity_main);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        dl.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        abdt = new ActionBarDrawerToggle(
                this, dl, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        abdt.setDrawerIndicatorEnabled(true);
        abdt.syncState();
    }

    public void exitSelectionMode() {
        isSelectionMode = false;
        for (View v : selectedNoteViews) {
            unmarkNoteAsSelected(v);
        }
        selectedNotes.clear();
        selectedNoteViews.clear();
        restoreDefaultToolbar();
    }

    public void toggleNoteSelection(View v) {
        long id = Long.parseLong(v.getTag().toString());
        if (selectedNotes.contains(id)) {
            removeSelectedNote(v);
        } else {
            addSelectedNote(v);
        }

        if (selectedNotes.size() > 0) {
            isSelectionMode = true;
            setSelectionModeToolbar();
            updateSelectionModeToolbarTitle();
        } else {
            exitSelectionMode();
        }
    }

    public void markNoteAsSelected(View v) {
        v.findViewById(R.id.text_body).setBackgroundColor(getResources().getColor(R.color.selectedCard));
        v.setBackgroundColor(getResources().getColor(R.color.selectedCard));
    }

    public void unmarkNoteAsSelected(View v) {
        v.findViewById(R.id.text_body).setBackgroundColor(getResources().getColor(R.color.normalCard));
        v.setBackgroundColor(getResources().getColor(R.color.normalCard));
    }

    public void addSelectedNote(View v) {
        long id = Long.parseLong(v.getTag().toString());
        selectedNotes.add(id);
        selectedNoteViews.add(v);
        markNoteAsSelected(v);
    }

    public void removeSelectedNote(View v) {
        long id = Long.parseLong(v.getTag().toString());
        selectedNotes.remove(id);
        selectedNoteViews.remove(v);
        unmarkNoteAsSelected(v);
    }

    @Override
    public void updateNotes(List newList) {
        noteLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                onNoteLongClick(v);
                return true;
            }
        };
        int maxWidth = Math.max(getResources().getDisplayMetrics().widthPixels,
                getResources().getDisplayMetrics().heightPixels); // Why? To prevent it from becoming too small?
        NoteAdapter newAdapter = new NoteAdapter(newList, maxWidth, noteLongClickListener);
        noteContainer.setAdapter(newAdapter);
    }

}