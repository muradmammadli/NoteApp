package com.example.noteapp;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "_note_table")
public class Note {

    @NonNull
    @PrimaryKey
    String id;

    @NonNull
    @ColumnInfo(name = "_note")
    String note;

    @NonNull
    @ColumnInfo(name = "_date")
    String date;

    public Note(@NonNull String id, @NonNull String note,@NonNull String date) {
        this.id = id;
        this.note = note;
        this.date = date;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getNote() {
        return note;
    }

    public void setNote(@NonNull String note) {
        this.note = note;
    }

}
