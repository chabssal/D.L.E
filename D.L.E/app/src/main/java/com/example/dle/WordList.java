package com.example.dle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class WordList extends AppCompatActivity {
    RecyclerView recyclerView;
    MyAdapter adapter;
    ArrayList<Datas> list = new ArrayList<>();
    ArrayList<String> words = new ArrayList<>();
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_list);
        Intent intent=getIntent();
        list = (ArrayList<Datas>) intent.getSerializableExtra("name");
        i = intent.getIntExtra("int",-1);
        words=list.get(i).word;

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new MyAdapter(words);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        loadData();


    }

    public void loadData() {
        Gson gson = new Gson();
        SharedPreferences mprefs = getSharedPreferences("MyWord", MODE_PRIVATE);
        String json = mprefs.getString("MyWord", "");
        ArrayList<String> mitems;
        mitems = gson.fromJson(json, new TypeToken<ArrayList<String>>() {
        }.getType());
        if (mitems != null) {
            words.addAll(mitems);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_btn1:
                //
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
}
