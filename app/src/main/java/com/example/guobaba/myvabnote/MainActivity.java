package com.example.guobaba.myvabnote;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {

    private EditText word;
    private EditText wordMean;
    private Button searchBut;
    private Button insertBut;
    private EditText input;
    private ListView listView;
    private MyDataBaseHelper dbHelper;
    private SQLiteDatabase sqlDB;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        word = (EditText) findViewById(R.id.editText1);
        wordMean = (EditText) findViewById(R.id.editText2);
        input = (EditText) findViewById(R.id.editText3);
        insertBut = (Button) findViewById(R.id.button1);
        searchBut = (Button) findViewById(R.id.button2);
        listView = (ListView) findViewById(R.id.listView1);
        dbHelper = new MyDataBaseHelper(MainActivity.this, "myDict.db3", 1);
        searchMethod();
        insertBut.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                insertMethod();
            }
        });
        searchBut.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                searchMethod();
            }
        });
    }

    // 插入单词
    protected void insertMethod() {
        String strWord = word.getText().toString();
        String strMean = wordMean.getText().toString();
        // 插入生词记录
        sqlDB = dbHelper.getReadableDatabase();
        sqlDB.execSQL("INSERT INTO dict VALUES(NULL,?,?)", new String[] {
                strWord, strMean });

        Toast.makeText(getApplicationContext(), "插入成功", Toast.LENGTH_LONG)
                .show();
    }

    // 查找单词
    protected void searchMethod() {
        String key = input.getText().toString();
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                "SELECT * FROM dict WHERE word LIKE ? OR detail LIKE ?",
                new String[] { "%" + key + "%", "%" + key + "%" });
        ArrayList<Map<String, String>> result = new ArrayList<Map<String, String>>();
        while (cursor.moveToNext()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("word", cursor.getString(1));
            map.put("detail", cursor.getString(2));
            result.add(map);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(
                getApplicationContext(), result, R.layout.line, new String[] {
                "word", "detail" }, new int[] { R.id.textView1,
                R.id.textView2 });
        listView.setAdapter(simpleAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}