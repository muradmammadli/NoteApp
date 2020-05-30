package com.example.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    public static final String NEW_NOTE = "NEW_NOTE";
    public static final String DATE = "DATE";
    private EditText editText;
    private Button addBtn;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        addBtn = findViewById(R.id.button);
        editText = findViewById(R.id.editText);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(editText.getText().toString())){
                    setResult(RESULT_CANCELED);
                    finish();
                }else {
                    Intent intent = new Intent(AddActivity.this,MainActivity.class);
                    String note = editText.getText().toString();

                    Calendar calendar =Calendar.getInstance();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE  dd-MM-yyyy hh:mm");
                    String currentDate = simpleDateFormat.format(calendar.getTime());

                    sharedPreferences = getSharedPreferences("SavedDate", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(DATE,currentDate);
                    editor.apply();

                    intent.putExtra(NEW_NOTE,note);
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }
}
