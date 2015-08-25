package com.hs.searchid.searchid.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hs.searchid.searchid.R;


public class MainActivity extends ActionBarActivity  implements View.OnClickListener {

    private EditText input_id;
    private Button  btn_search,btn_history_records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去除标题栏
        ActionBar actionbar = getSupportActionBar();
        actionbar.hide();
        Log.d("debug","create main activity---->");

        setContentView(R.layout.activity_main);

        //获取控件
        input_id = (EditText) findViewById(R.id.input_id);
        btn_search = (Button) findViewById(R.id.btn_search);
        btn_history_records = (Button) findViewById(R.id.btn_history_records);

        setListeners();
    }

    public void setListeners(){
        btn_history_records.setOnClickListener(this);
        btn_search.setOnClickListener(this);
    }



    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.btn_history_records:
                Log.d("debug", "click the goto history button---->");

                Intent intent1 = new Intent(MainActivity.this,HistoryRecordsActivity.class);
                startActivity(intent1);
                break;

            case R.id.btn_search:
                Log.d("debug", "click the goto search button---->");

                String uid = input_id.getText().toString();
                Log.d("debug","uid is : " + uid);
                //int len = uid.length();
                //Log.d("debug","len of uid is : " + Integer.toString(len));

                if (uid.trim().equals("")){
                    Toast.makeText(MainActivity.this,"please input uid",Toast.LENGTH_SHORT).show();
                }else{
                    //跳转至查询结果页面
                    Intent intent = new Intent(MainActivity.this,ResultActivity.class);
                    //传递数据给下一个页面
                    intent.putExtra("uid",uid);
                    startActivity(intent);
                }


                break;

            default:
                break;

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
