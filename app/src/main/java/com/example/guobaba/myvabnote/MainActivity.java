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
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
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

public class MainActivity extends Activity  {

    private EditText word;
    private EditText wordMean;
    private Button searchBut;
    private Button insertBut;
    private EditText input;
    private ListView listView;
    private MyDataBaseHelper dbHelper;
    private SQLiteDatabase sqlDB;
    SimpleAdapter simpleAdapter;
    ArrayList<Map<String, String>> result = new ArrayList<Map<String, String>>();
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
//        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//
//                if(result.remove(position)!=null){//这行代码必须有
//                    System.out.println("success");
//
//                    TextView mTextView1 = (TextView) view
//                            .findViewById(R.id.textView1);
//                    String string1 = mTextView1.getText().toString().trim();
//                    sqlDB = dbHelper.getReadableDatabase();
//                    sqlDB.execSQL("delete from dict where word like ? OR detail like ?", new String[] {
//                            string1  , string1 });
//                    Toast.makeText(getBaseContext(),string1, Toast.LENGTH_SHORT).show();
//                }else {
//                    System.out.println("failed");
//                }
//
//                simpleAdapter.notifyDataSetChanged();
//
//                return false;
//            }
//        });
        this.registerForContextMenu(listView);
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
        result.clear();
        String key = input.getText().toString();
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                "SELECT * FROM dict WHERE word LIKE ? OR detail LIKE ?",
                new String[] { "%" + key + "%", "%" + key + "%" });

        while (cursor.moveToNext()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("word", cursor.getString(1));
            map.put("detail", cursor.getString(2));
                    result.add(map);

        }
         simpleAdapter = new SimpleAdapter(
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


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("选择操作");
        menu.add(0,3,Menu.NONE,"添加到生词本");
        menu.add(0,4,Menu.NONE,"删除");
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case 1:
// 发送...
                break;
            case 2:
//标记...
                break;
            case 3:
//重命名...



                break;
            case 4:
//删除列表项...
                int pos=(int)listView.getAdapter().getItemId(menuInfo.position);
                if(result.remove(pos)!=null){//这行代码必须有
                    System.out.println("success");

                    TextView mTextView1 = (TextView) menuInfo.targetView
                            .findViewById(R.id.textView1);
                    String string1 = mTextView1.getText().toString().trim();
                    sqlDB = dbHelper.getReadableDatabase();
                    sqlDB.execSQL("delete from dict where word like ? OR detail like ?", new String[] {
                            string1  , string1 });
                    Toast.makeText(getBaseContext(),string1+"删除成功", Toast.LENGTH_SHORT).show();
                }else {
                    System.out.println("failed");
                }

                simpleAdapter.notifyDataSetChanged();
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }


}
