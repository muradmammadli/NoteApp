package com.example.noteapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UpdateViewModel extends AndroidViewModel {

    private NoteDao noteDao;
    private NoteDatabase noteDatabase;
    private LiveData<List<Note>> liveData;


    public UpdateViewModel(@NonNull Application application) {
        super(application);

        noteDatabase = NoteDatabase.getnoteDatabase(application);
        noteDao = noteDatabase.noteDao();
    }

    public LiveData<List<Note>> getLiveData(String personId) {
        return noteDao.getAllNotes(personId);
    }
}
