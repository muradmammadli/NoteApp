package com.example.noteapp;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    private static volatile NoteDatabase noteDatabase;

    static NoteDatabase getnoteDatabase(Context context) {
        if (noteDatabase == null) {
            synchronized (NoteDatabase.class) {
                if (noteDatabase == null) {
                    noteDatabase = Room.databaseBuilder(context, NoteDatabase.class, "_my_note_database").build();
                }
            }
        }
        return noteDatabase;
    }

    public abstract NoteDao noteDao();
}
