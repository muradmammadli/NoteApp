package com.example.noteapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.example.noteapp.AddActivity.DATE;
import static com.example.noteapp.AddActivity.NEW_NOTE;
import static com.example.noteapp.UpdateActivity.UPDATED_ID;
import static com.example.noteapp.UpdateActivity.UPDATED_NOTE;

public class MainActivity extends AppCompatActivity implements MainInterface {
    private RecyclerView rv;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private MainViewModel viewModel;
    private NoteAdapter adapter;
    public static final int ADD_PERSON = 1;
    public static final int UPDATE_NOTE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();

        toolbar.setTitle("Qeydlər");
        setSupportActionBar(toolbar);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));


        adapter = new NoteAdapter(this, this);
        rv.setAdapter(adapter);


        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {

                if (notes != null && notes.size() > 0) {
                    adapter.setNoteList(notes);
                }

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivityForResult(intent, ADD_PERSON);
            }
        });
    }

    public void findViewById() {
        rv = findViewById(R.id.rv);
        toolbar = findViewById(R.id.toolbar);
        fab = findViewById(R.id.floatingActionButton);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case ADD_PERSON:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

                    String id = UUID.randomUUID().toString();
                    String note;
                    String date;
                    if (data != null) {
                        SharedPreferences result = getSharedPreferences("SavedDate", Context.MODE_PRIVATE);
                        date = result.getString(DATE,"DATE NOT FOUND");

                        note = data.getStringExtra(NEW_NOTE);
                        if (note != null) {
                            Note note1 = new Note(id, note,date);
                            viewModel.InsertPerson(note1);
                        } else {
                            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
                        }

                    }
                }

            case UPDATE_NOTE:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this, "Uğurla yenijləndi", Toast.LENGTH_SHORT).show();

                    String id;
                    String note;
                    String date;
                    if (data != null) {
                        SharedPreferences result = getSharedPreferences("SavedDate", Context.MODE_PRIVATE);
                        date = result.getString(DATE,"DATE NOT FOUND");
                        id = data.getStringExtra(UPDATED_ID);
                        note = data.getStringExtra(UPDATED_NOTE);
                        if (id != null || note != null) {
                            Note note1 = new Note(id, note,date);
                            viewModel.UpdatePerson(note1);
                        }
                    }
                }
        }
    }

    @Override
    public void onDeleteClicked(Note note) {
        viewModel.deletePerson(note);
    }
}

