package ru.gb.lesson6.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import ru.gb.lesson6.R;
import ru.gb.lesson6.data.Constants;
import ru.gb.lesson6.data.InMemoryRepoImpl;
import ru.gb.lesson6.data.InitRepo;
import ru.gb.lesson6.data.Note;
import ru.gb.lesson6.data.Repo;
import ru.gb.lesson6.recycler.NotesAdapter;

public class NotesListActivity extends AppCompatActivity implements NotesAdapter.OnNoteClickListener {

    // private Repo repository = new InMemoryRepoImpl();
    private Repo repository = InMemoryRepoImpl.getInstance();
    private RecyclerView list;
    private NotesAdapter adapter;
    private InitRepo flagRepo = new InitRepo();
    private final boolean flagTest = true;
    public static final String RESULT = "RESULT";
    public static final String TAG = "happy";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        Log.d(TAG,"Flag Repo Before " + flagRepo.isFlagInitRepo());


//        flagRepo.setFlagInitRepo(false);
//        Log.d(TAG,"Flag Repo After " + flagRepo.isFlagInitRepo());
        if (savedInstanceState != null && savedInstanceState.containsKey(RESULT)) {
            flagRepo = (InitRepo) savedInstanceState.getSerializable(RESULT);
            Log.d(TAG,"Flag REPO " + flagRepo.isFlagInitRepo());
//            flagRepo.setFlagInitRepo(true);
/*            if (!flagRepo.isFlagInitRepo()) {
//                fillRepo();
                flagRepo.setFlagInitRepo(true);
            }*/
        }
        if (!flagRepo.isFlagInitRepo()) {
            fillRepo();
            flagRepo.setFlagInitRepo(true);
        }
        Log.d(TAG,"Flag Repo After " + flagRepo.isFlagInitRepo());

        adapter = new NotesAdapter();
        adapter.setNotes(repository.getAll());

        adapter.setOnNoteClickListener(this);


        list = findViewById(R.id.list);
        // list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)); // horizontal
        list.setLayoutManager(new LinearLayoutManager(this)); // Vertical
        list.setAdapter(adapter);


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(RESULT,flagRepo);
    }

    private void fillRepo() {
        repository.create(new Note("Title 1", "Description 1"));
        repository.create(new Note("Title 2", "Description 2"));
        repository.create(new Note("Title 3", "Description 3"));
        repository.create(new Note("Title 4", "Description 4"));
        repository.create(new Note("Title 5", "Description 5"));
        repository.create(new Note("Title 6", "Description 6"));
        repository.create(new Note("Title 7", "Description 7"));
        repository.create(new Note("Title 8", "Description 8"));
        repository.create(new Note("Title 9", "Description 9"));
        repository.create(new Note("Title 10", "Description 10"));
        repository.create(new Note("Title 11", "Description 11"));
        repository.create(new Note("Title 12", "Description 12"));
        repository.create(new Note("Title 13", "Description 13"));
        repository.create(new Note("Title 14", "Description 14"));
        repository.create(new Note("Title 15", "Description 15"));
        repository.create(new Note("Title 16", "Description 16"));
    }

    @Override
    public void onNoteClick(Note note) {
        Intent intent = new Intent(this, EditNoteActivity.class);
        intent.putExtra(Constants.NOTE, note);
        flagRepo.setFlagInitRepo(true);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_create:
                // создать Intent
                // TODO запустить EditNoteActivity
                Intent intent = new Intent(this, EditNoteActivity.class);
//                intent.putExtra(Constants.NOTE, InMemoryRepoImpl.getInstance().read());
//                intent.putExtra(Constants.NOTE, notes.size());

//                intent.putExtra(Constants.NOTE_ID, repository.getAll().size()+1);
                flagRepo.setFlagInitRepo(true);
                Note note = new Note(-1, "New title", "New description");
                intent.putExtra(Constants.NOTE, note);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            flagRepo.setFlagInitRepo(true);
            adapter.setNotes(repository.getAll());
            list.setAdapter(adapter);
        }
    }
/*

    @Override
    protected void onResume() {
        super.onResume();
        flagRepo = false;
        adapter.setNotes(repository.getAll());
//        adapter.notifyItemChanged();
//        adapter.notifyItemRangeInserted();
        list.setAdapter(adapter);
    }
*/

/*    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        adapter.setNotes(repository.getAll());
        list.setAdapter(adapter);
    }*/
}