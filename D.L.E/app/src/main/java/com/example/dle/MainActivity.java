package com.example.dle;

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
                Intent intent = new Intent(getApplicationContext(), WordList.class);
                intent.putExtra("name", wordBook);
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
                    wordBook.add(new Datas(text, null, null));
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
        //Log.e("loadData", mitems.toString());
        if (mitems != null) {
            // 나머지 실행
            wordBook.addAll(mitems);
            for (int i = 0; i < wordBook.size(); i++) {
                arrayList.add(wordBook.get(i).name);
            }
        } else {
            //앱 처음 킬때 실행
            wordBook.add(new Datas("고등 영어 단어", new String[]{"tradition", "patriotism", "religious", "conscience", "intellect", "culture", "civilization", "prejudice", "instinct", "privilege",
                    "impulse", "virtue", "vice", "revolution", "stimulus", "ambition", "vanity", "envy", "jealousy", "fatigue",
                    "passion", "famine", "calamity", "individual", "emotion", "evolution", "hypocrisy", "character", "method", "psychology",
                    "chivalry", "literature", "responsibility", "system", "challenge", "monotony", "disgrace", "luxury", "anachronism", "conquest",
                    "siege", "insight", "panic", "universe", "criticism", "influence", "resistance", "convenience", "hardship", "existence"},
                    new String[]{"전통,전설","애국심","종교","양심","지성","교양,문화","문명","편견,선입견","본능","특권,특권을 주다",
                            "충동", "미덕,장점","악덕,결점","혁명,(전체의)운행","자극","대망,야심","허영심","시기,부러움","질투","피로",
                            "정열","기근","재난,불행","개인,개인의","정서,감정","진화,발전","위선","특징,인격,문자","방법","심리(학)",
                            "기사도","문학","책임","조직,체계","도전,도전하다","단조로움","불명에,치욕","사치(품)","시대착오","정복",
                            "포위공격","통찰력","공황,당황","우주,전세계","비평,비판","영향(력),여향을 미치다","저항","편리","고난","존재,생존"}));

            wordBook.add(new Datas("실행활 영어 단어", new String[]{"lodgings","introvert","extrovert","extrovert","abbreviation","acronym","flaunt","pottage","porridge","discipline",
                    "inferior","stationary","district","coalesce","fallacious","malfeasance","abstract","marinate","bucket down","chuck",
                    "be stubborn","arrogant","farment","ladle","wander","seasoned","seasonal","seasoning","sesame","apparently",
                    "obviously","priority","elegant","neurotic","fussy","talk rough","publicity","be competent in","be overwhelmed by","complete",
                    "bear hunger","tolerant","tolerate","be required to","require","acquire","commitment","enterprise","vinegar","sucker"}, new String[]{"하숙","내성적인 사람","외향적인 사람","걱정하는","약어","두문자어","자랑,과시하다","수프","죽","훈련하다, 규율",
                    "열등한","움직이지 않는","구역,직역"," 합쳐지다","잘못된,틀린","불법 행위","추상적인,관념적인","양념장에 재워두다","비가 억수같이 오다","던지다",
                    "고집이 세다","거만한,오만한","발효되다,발효시키다.","국자","헤매다,배회하다","노련한","계절의","양념,조미료","참꺠","듣자하니",
                    "확실히","우선순위","우아한","신경과민의","까다로운","말투가 거칠다","홍보","~에 능력이 있다","~에 압도되다.","완성하다",
                    "배고품을 참다","관대한","참다","~하도록 요구되다","요구하다","습득하다","약속,헌신","기업,사업","식초","잘 속는 사람"}));

            wordBook.add(new Datas("수능 영어 단어", new String[]{"thorn", "banner", "pavement", "outlook", "conviction", "ignorance", "bliss", "gratitude", "boredom", "inflation",
                    "foam", "shed" , "onfine", "diabetes", "flutter","notify", "converse","confront","convert","revise","dominate","sustain",
                    "fluid","liberal","utmost","baggy","allergic","oral","coherent","skeptical","spectacle","auditorium","patriot","peasant",
                    "meadow","hay","kettle","baggage","basement","enrollment","compassion","moderation","reputation","scarcity","switch","bilingual",
                    "gorgeous","solemn","homesick","ironic"}, new String[]{"가시", "현수막","포장도로","전망", "신념", "무지","기쁨", "감사","지루함","인플레이션","거품","흘리다","한정시키다","당뇨병",
                    "퍼덕거리다","통고하다","대화하다","직면하다","전환시키다","개정하다","지배하다","유지하다, 상처를 입다","액체","자유로운","최대의",
                    "헐렁한","알레르기의","구두의","조리있는","비판적인","관객","청중","애국자","농부","초지","건초더미","주전자","수화물","기초","등록",
                    "동정","절제","평판","부족","전환(하다)","2개 국어의","아름다운","엄숙한","향수병의","반어적인"}));

            wordBook.add(new Datas("토익 영어 단어", new String[]{"reliant","illustrate","dispose of","audit","curtail","reimburse","amend","be exempt from","fiscal year","fiscal operations","incidental",
                    "lead to inflation","turnover","expansion project","building expansion","refinery expansion","competitiveness","dedicated",
                    "understanding","adjourn","preside","preside over","irrespective of","inhibition","temporarily","temporary","dealership","bulk",
                    "in bulk","run short","be short of","a wide selection of","be provided with","assure","subject","affordability","clientele",
                    "speak for","encompass","quote","consignment","perishable","]courier","carton","enclose","encase","pick up","carry","formerly",
                    "correspondence","recipient"}, new String[]{"의존하는, 의지하는","분명히 보여 주다, 설명하다","~을 처분하다, 지우다","회계 감사, 심사","~을 줄이다, 삭감하다","변제하다, 상환하다",
                    "수정하다","~을 면제받다,","회계 연도","회계 업무","부수적인","인플레이션을 초래하다","총 매상고, 거래액, 이직률","확장 계획","건물 확장",
                    "제련소 확장","경쟁력","(목표 등에)전념한는, 헌신적인","이해심 있는","(회의 등을) 휴회하다","(회의의) 의장을 보다","~의 사회를 보다, 통솔하다",
                    "~와 상관없이","(감정의) 거리낌, 억압","일시적으로","일시적인","판매 대리점","대량의","대량으로","~이 부족하다, 떨어지다","~이 부족하다",
                    "다양하게 선정된","~이 갖추어져 있다","~에게 보장하다, ~을 장담하다","~의 영향을 받기 쉬운","감당할 수 있는 비용","(집합적) 고객들, 모든 의뢰인",
                    "(회사, 단체) 대신하여 말하다","포함하다, 둘러싸다","견적(액)","위탁 판매","부패하기 쉬운","급송 택배","(큰) 판지 상자","~을 둘러싸다, 에워싸다",
                    "(상자, 포장) ~을 넣다","~을 찾다, 도중에 태우다","지니다,(물품을) 팔다","이전에","편지, 통신문","수신자"}));
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
