package com.example.noteapp;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void addNote(Note note);



    @Query("SELECT * FROM _note_table")
    LiveData<List<Note>> getNotes();

    @Query("SELECT * FROM _note_table WHERE id=:noteId")
    LiveData<List<Note>> getAllNotes(String noteId);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);
}
