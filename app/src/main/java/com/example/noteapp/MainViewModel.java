package com.example.noteapp;

import android.app.Application;
import android.app.Person;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private NoteDao noteDao;
    private NoteDatabase noteDatabase;
    private LiveData<List<Note>> liveData;


    public MainViewModel(@NonNull Application application) {
        super(application);
        noteDatabase = NoteDatabase.getnoteDatabase(application);
        noteDao = noteDatabase.noteDao();
        liveData = noteDao.getNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return liveData;
    }


    public void InsertPerson(Note note) {
        new InsertTask(noteDao).execute(note);
    }

    private class InsertTask extends AsyncTask<Note, Void, Void> {
        NoteDao noteDao;

        public InsertTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.addNote(notes[0]);
            return null;
        }
    }

    public void UpdatePerson(Note note) {
        new UpdateTask(noteDao).execute(note);
    }

    private class UpdateTask extends AsyncTask<Note, Void, Void> {
        NoteDao noteDao;

        public UpdateTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    public void deletePerson(Note note){
        new deleteTask(noteDao).execute(note);
    }

    private class deleteTask extends AsyncTask<Note,Void,Void>{
        NoteDao noteDao;
        public deleteTask(NoteDao noteDao) {
            this.noteDao = noteDao;

        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }
}
