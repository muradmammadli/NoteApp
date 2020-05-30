package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class UpdateActivity extends AppCompatActivity {

    public static final String UPDATED_NOTE = "UPDATED_NOTE";
    public static final String UPDATED_ID = "UPDATED_ID";
    private EditText updateTxt;
    private Button updateBtn;
    private UpdateViewModel viewModel;
    private TextView currentPerson;
    private String person_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        updateTxt = findViewById(R.id.updateEdit);
        updateBtn = findViewById(R.id.updateBtn);
        currentPerson = findViewById(R.id.currentPerson);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(updateTxt.getText())) {
                    setResult(RESULT_CANCELED);
                    finish();
                } else {
                    Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                    intent.putExtra(UPDATED_NOTE, updateTxt.getText().toString());
                    intent.putExtra(UPDATED_ID, person_id);
                    setResult(RESULT_OK,intent);
                    finish();

                }
            }
        });


        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            person_id = bundle.getString("note_id");
        }
        viewModel = ViewModelProviders.of(this).get(UpdateViewModel.class);
        viewModel.getLiveData(person_id).observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                if (notes.size() > 0 && notes!=null) {
                    String note = notes.get(0).getNote();
                    currentPerson.setText(note);
                }
            }
        });
    }
}
