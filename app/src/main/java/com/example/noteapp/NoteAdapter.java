package com.example.noteapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static com.example.noteapp.AddActivity.DATE;
import static com.example.noteapp.MainActivity.UPDATE_NOTE;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.viewHolder> {
    Context context;
    List<Note> noteList = new ArrayList<>();
    private MainInterface view;
    View v;


    public NoteAdapter(Context context,MainInterface view) {
        this.context = context;
        this.view = view;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        v = LayoutInflater.from(context).inflate(R.layout.note_card_item, parent, false);
        return new viewHolder(v);
    }

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull final viewHolder holder, int position) {


        final Note note = noteList.get(position);
        holder.title.setText(note.getNote());
        holder.date.setText(note.getDate());
        holder.popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popupMenu = new PopupMenu(context, holder.popup);
                popupMenu.inflate(R.menu.popup_item);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete:
                                AlertDialog.Builder ad =new AlertDialog.Builder(context);
                                ad.setTitle("Sil");
                                ad.setMessage("Bu qeydi silmək istədiyinizdən əminsinizmi?");
                                ad.setIcon(R.drawable.ic_add_black_24dp);
                                ad.setPositiveButton("Bəli", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        view.onDeleteClicked(note) ;
                                        Snackbar.make(v,"Silindi",Snackbar.LENGTH_LONG).show();

                                    }
                                });
                                ad.setNegativeButton("Ləğv et", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Snackbar.make(v,"Ləğv edildi",Snackbar.LENGTH_LONG).show();
                                    }
                                });

                                return true;
                            case R.id.edit:
                                Intent intent = new Intent(context, UpdateActivity.class);
                                intent.putExtra("note_id", note.getId());
                                ((Activity) context).startActivityForResult(intent, UPDATE_NOTE);
                            default:
                                break;
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder {
        private TextView title, date;
        private ImageView popup;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.noteTitle);
            date = itemView.findViewById(R.id.date);
            popup = itemView.findViewById(R.id.popup);
        }

    }


}
