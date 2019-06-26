package com.example.dle;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Datas implements Serializable {
    String name;
    ArrayList<String> word=new ArrayList<>();
    ArrayList<String> mean = new ArrayList<>();
    public Datas(String name, String[] word, String[] mean){
        this.name=name;
        Collections.addAll(this.word,word);
        Collections.addAll(this.mean,mean);
    }
}
