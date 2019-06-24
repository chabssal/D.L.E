package org.techtown.dle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class WordlList extends AppCompatActivity {
    android.support.v7.widget.RecyclerView recyclerView;
    MyAdapter adapter;
    ArrayList<Datas> word;
    ArrayList<String> mainWord = new ArrayList<>();
    String i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordl_list);


        Intent intent=getIntent();
        word = (ArrayList<Datas>) intent.getSerializableExtra("arraylist");
        i= intent.getStringExtra("name");
        if(word.get(0).name.equals(i)){
            mainWord=word.get(0).dataWord;
        }
        else if(word.get(1).name.equals(i)){
            mainWord=word.get(1).dataWord;
        }
        else if(word.get(2).name.equals(i)){
            mainWord=word.get(2).dataWord;
        }
        else if(word.get(3).name.equals(i)){
            mainWord=word.get(3).dataWord;
        }
        else{
            //단어 추가
        }

        recyclerView =findViewById(R.id.recyclerView);
        adapter=new MyAdapter(mainWord);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayout.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        loadData();


    }
    public void loadData() {
        Gson gson = new Gson();
        SharedPreferences mprefs = getSharedPreferences("MyWord", MODE_PRIVATE);
        String json = mprefs.getString("MyWord", "");
        ArrayList<Datas> mitems;
        mitems = gson.fromJson(json, new TypeToken<ArrayList<Datas>>() {
        }.getType());
        if(mitems !=null)
            word.addAll(mitems);
    }
}
