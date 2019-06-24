package org.techtown.dle;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    GridView gridView;
    ArrayAdapter adapter;
    ArrayList<Datas> wordBook = new ArrayList<>();
    ArrayList<String> arrayList = new ArrayList<>();
    Button addBtn;
    EditText editText;
    String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridView);
        addBtn = findViewById(R.id.addBtn);
        editText = findViewById(R.id.editText);

        loadData();

        adapter = new ArrayAdapter<String>(this, R.layout.item_grid, R.id.wordBook, arrayList);
        Log.e("fgj", arrayList.toString());
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), WordlList.class);
                intent.putExtra("name", wordBook.get(i).name);
                intent.putExtra("int", i);
                startActivity(intent);
            }
        });
        addBtn.setOnClickListener(this);
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == keyEvent.KEYCODE_ENTER)) {
                    text = editText.getText().toString();
                    if (text.equals(""))
                        Toast.makeText(MainActivity.this, "아무것도 입력 되지 않음", Toast.LENGTH_SHORT).show();
                    else onClick(addBtn);
                    return true;
                } else return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == addBtn) {
            text = editText.getText().toString();
            if (wordBook.indexOf(text) != -1)
                Toast.makeText(this, "중복된 그룹이 있음", Toast.LENGTH_SHORT).show();
            else {
                if (text.equals(""))
                    Toast.makeText(MainActivity.this, "아무것도 입력 되지 않음", Toast.LENGTH_SHORT).show();
                else {
                    wordBook.add(new Datas(text, null, null, null));
                    arrayList.add(text);
                    editText.setText(null);
                    text = "";
                    editText.clearFocus();
                    saveData();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        }
    }

    public void loadData() {
        Gson gson = new Gson();
        SharedPreferences mprefs = getSharedPreferences("MyWord", MODE_PRIVATE);
        String json = mprefs.getString("MyWord", "");
        ArrayList<Datas> mitems;
        mitems = gson.fromJson(json, new TypeToken<ArrayList<Datas>>() {
        }.getType());
        Log.e("loadData", mitems.toString());
        if (mitems != null) {
            // 나머지 실행
            wordBook.addAll(mitems);
            for (int i = 0; i < wordBook.size(); i++) {
                arrayList.add(wordBook.get(i).name);
            }
        } else {
            //앱 처음 킬때 실행
            wordBook.add(new Datas("고등 영어 단어", "https://quizlet.com/379058595/real-life-flash-cards/",
                    "TermText notranslate lang-en", "TermText notranslate lang-ko"));
            wordBook.add(new Datas("실행활 영어 단어", null, null, null));
            wordBook.add(new Datas("수능 영어 단어", null, null, null));
            wordBook.add(new Datas("토익 영어 단어", null, null, null));
            for (int i = 0; i < wordBook.size(); i++) {
                arrayList.add(wordBook.get(i).name);
            }
        }
    }

    public void saveData() {
        SharedPreferences mprefs = getSharedPreferences("MyWord", MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mprefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(wordBook);
        mEditor.putString("MyWord", json);
        mEditor.apply();
    }
}
