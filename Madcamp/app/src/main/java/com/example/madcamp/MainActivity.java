package com.example.madcamp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ArrayList<Phonenumber> dataList;
    private ListView mListview;
    private Button mBtnAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListview = (ListView) findViewById(R.id.listview);
        mBtnAddress = (Button) findViewById(R.id.btnAddress);
        mBtnAddress.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddress:

                dataList = new ArrayList<Phonenumber>();
                Cursor c = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                        null, null, null,
                        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " asc");


                while (c.moveToNext()) {
                    // 연락처 id 값
                    String id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                    // 연락처 대표 이름
                    String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                    String number;
                    // ID로 전화 정보 조회
                    Cursor phoneCursor = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                            null, null);

                    // 데이터가 있는 경우
                    if (phoneCursor.moveToFirst()) {
                         number= phoneCursor.getString(phoneCursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER));
                    }
                    else{
                        number="0";
                    }
                    Phonenumber p = new Phonenumber(name,number);
                    phoneCursor.close();
                    dataList.add(p);
                }// end while
                Collections.sort(dataList, new Comparator<Phonenumber>() {
                    @Override
                    public int compare(Phonenumber s1, Phonenumber s2) {
                        return s1.getName().toUpperCase().compareTo(s2.getName().toUpperCase());
                    }
                });

                c.close();

                ArrayList Templist = new ArrayList<Map<String, String>>();


                for(Phonenumber p:dataList){
                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("name", p.getName());
                    map.put("phone", p.getNumber());
                    Templist.add(map);
                }
                System.out.println(Templist);

                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
                        Templist,
                        android.R.layout.simple_list_item_2,
                        new String[]{"name", "phone"},
                        new int[]{android.R.id.text1, android.R.id.text2});
                mListview.setAdapter(adapter);
            }

        }





        /*MyView m=new MyView(this);
        setContentView(m);*/



        /*setContentView(R.layout.activity_main);

        Button b= findViewById(R.id.button1);
        b.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                TextView t=(TextView)findViewById(R.id.text1);
                t.setText("change");
                setContentView(R.layout.activity_second_view);

                SharedPreferences s= PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editor=s.edit();

                editor.putInt("point", 20);
                editor.commit();

                int k=s.getInt("point",10);
                TextView t=findViewById(R.id.text1);
                t.setText("point의 값: "+k);
            }
        });*/
    }

    class Phonenumber {
        String name;
        String number;

        public Phonenumber(String name, String number) {
            this.name = name;
            this.number = number;
        }

        public String getName() {
            return this.name;
        }

        public String getNumber() {
            return this.number;
        }
    }




