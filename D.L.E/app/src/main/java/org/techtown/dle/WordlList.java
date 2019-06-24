package org.techtown.dle;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class WordlList extends AppCompatActivity {
    android.support.v7.widget.RecyclerView recyclerView;
    MyAdapter adapter;
    String lists,means,setList[], setMean[];
    Document doc;
    Elements word,mean;
    ArrayList<Datas> list= new ArrayList<>();
    Datas item;
    String name;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wordl_list);
        recyclerView =findViewById(R.id.recyclerView);
        adapter=new MyAdapter(list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayout.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        loadData();

        Intent intent=getIntent();
        name = intent.getStringExtra("name");
        i = intent.getIntExtra("int",-1);
        if(i!=-1){
            item = list.get(i);
            if(item.url!=null){
                parsing(item.url);
            }
        }//else 버튼 있는 화면으로 전환??


    }
    public void parsing(final String url){
        new Thread() {
            public void run() {
                Word save=null;
                try {
                    doc = Jsoup.connect(url).get();
                    word = doc.getElementsByClass(item.urlWord);
                    Log.e("word", String.valueOf(word));
                    mean = doc.getElementsByClass(item.urlMean);
                    lists = word.text();
                    means = mean.text();
                    Log.e("test", "test");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.e("lists",lists);
                Log.e("means",means);
                setList = lists.split("");
                setMean = means.split(",");
//                for(int i = 0; i< setList.length; i++) {
//                    Log.e("text",setList[i]);
//                    save.setWord(setList[i]);
//                    save.setMean(setMean[i]);
//                    item.word.add(save.getWord());
//                    item.mean.add(save.getMean());
//                }
//                adapter.notifyDataSetChanged();
            }
        }.start();
    }
    public void loadData() {
        Gson gson = new Gson();
        SharedPreferences mprefs = getSharedPreferences("MyWord", MODE_PRIVATE);
        String json = mprefs.getString("MyWord", "");
        ArrayList<Datas> mitems;
        mitems = gson.fromJson(json, new TypeToken<ArrayList<Datas>>() {
        }.getType());
        list.addAll(mitems);
    }
}
