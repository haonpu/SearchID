package com.hs.searchid.searchid.activities;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.hs.searchid.searchid.R;
import com.hs.searchid.searchid.adapters.PersonAdapter;
import com.hs.searchid.searchid.models.Person;
import com.hs.searchid.searchid.utils.MyDatabaseHelper;

import java.util.ArrayList;
import java.util.List;

public class HistoryRecordsActivity extends ActionBarActivity {

    private ListView lv_history;
    private List<Person> personList = new ArrayList<Person>();
    private MyDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去除标题栏
        ActionBar actionbar = getSupportActionBar();
        actionbar.hide();


        setContentView(R.layout.activity_history_records);

        dbHelper = new MyDatabaseHelper(this,"IdRecords.db",null,1); //版本号为1
        lv_history = (ListView) findViewById(R.id.lv_history);

        getPersonData();
        PersonAdapter adapter = new PersonAdapter(HistoryRecordsActivity.this,R.layout.person_item,personList);
        lv_history.setAdapter(adapter);


    }


    public void getPersonData(){


        Log.d("debug", "查询历史信息---->");

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //查询表中的所有的数据  Id_recorders
        String sql_str = "select * from Id_records";
        Cursor cursor = db.rawQuery(sql_str, null);

        int count = cursor.getCount();
        Log.d("debug", "count is " + Integer.toString(count));
        if(cursor.moveToFirst()){
            do {
                //遍历cursor
                String uid = cursor.getString(cursor.getColumnIndex("id_num"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String address = cursor.getString(cursor.getColumnIndex("address"));
                String sex = cursor.getString(cursor.getColumnIndex("sex"));
                String birthday = cursor.getString(cursor.getColumnIndex("birthday"));

                Log.d("debug"," address: " +address );
                Log.d("debug"," sex: " + sex);
                Log.d("debug"," birthday: " +birthday );
                Person temp = new Person(uid,address,sex,birthday);
                personList.add(temp);

                //Log.d("debug"," :" + );

            }while (cursor.moveToNext() );
        }else{
            //Toast.makeText(HistoryRecordsActivity.this,"暂无数据",Toast.LENGTH_SHORT);
            Log.d("debug", "count is -------->" + Integer.toString(count));
        }

        cursor.close();





        /*

        Person p1 = new Person("130625198912180418","北京市海淀区","M","1990-01-14");  //String uid, String address, String sex, String birthday
        Person p2 = new Person("130625198912180418","北京市顺义区","M","1990-01-14");
        Person p3 = new Person("130625198912180418","保定市北市区","M","1990-01-14");
        Person p4 = new Person("130625198912180418","天津市西青区","M","1995-01-30");

        personList.add(p1);
        personList.add(p1);
        personList.add(p1);
        personList.add(p1);


        personList.add(p2);
        personList.add(p2);
        personList.add(p2);
        personList.add(p2);
        personList.add(p2);
        personList.add(p2);



        personList.add(p3);
        personList.add(p3);
        personList.add(p3);
        personList.add(p3);


        personList.add(p1);
        personList.add(p2);
        personList.add(p3);
        personList.add(p4);





        personList.add(p1);
        personList.add(p2);
        personList.add(p3);
        personList.add(p4);

*/


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history_records, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
