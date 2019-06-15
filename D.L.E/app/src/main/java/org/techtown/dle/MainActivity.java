package org.techtown.dle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    GridView gridView;
    ListAdapter adapter;
    ArrayList<String> wordList;
    Button addBtn;
    EditText editText;
    String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);

        wordList = new ArrayList<String>();
        wordList.add("고등 영어 단어");
        wordList.add("실행활 영어 단어");
        wordList.add("수능 영어 단어");
        wordList.add("토익 영어 단어");

        adapter = new ArrayAdapter<String>(this,R.layout.item,R.id.word,wordList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        addBtn = findViewById(R.id.addBtn);
        editText = findViewById(R.id.editText);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text=editText.getText().toString();
                if(text.equals(""))
                    Toast.makeText(MainActivity.this, "아무것도 입력 되지 않음", Toast.LENGTH_SHORT).show();
                else {
                    wordList.add(text);
                    editText.setText(null);
                    text="";
                }


            }
        });

    }
}
