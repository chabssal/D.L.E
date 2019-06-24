package org.techtown.dle;

import android.net.Uri;

import java.util.ArrayList;

public class Datas{
    String name;
    String url=null,urlWord=null,urlMean=null;
    ArrayList<String> word,mean;
    public Datas(String name, String url,String urlWord,String urlMean){
        this.name=name;
        this.url=url;
        this.urlWord=urlWord;
        this.urlMean=urlMean;
    }
}
