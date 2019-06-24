package org.techtown.dle;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Datas implements Serializable {
    String name;
    String[] words;
    String[] means;
    ArrayList<String> dataWord,dataMean;
    public Datas(String name, String words[],String means[]){
        this.name=name;
        this.words = words;
        this.means= means;
        dataWord=new ArrayList<>(Arrays.asList(words));
        dataMean=new ArrayList<>(Arrays.asList(means));
    }

}
