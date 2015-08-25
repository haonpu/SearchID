package com.hs.searchid.searchid.activities;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hs.searchid.searchid.R;
import com.hs.searchid.searchid.models.Person;
import com.hs.searchid.searchid.utils.HttpCallbackListener;
import com.hs.searchid.searchid.utils.HttpUtil;
import com.hs.searchid.searchid.utils.MyDatabaseHelper;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends ActionBarActivity {


    public static final int SHOW_RESPONSE = 0;
    private String uid = null;
    private MyDatabaseHelper dbHelper;
    private TextView tv_show_result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去除标题栏
        ActionBar actionbar = getSupportActionBar();
        actionbar.hide();

        setContentView(R.layout.activity_result);

        tv_show_result = (TextView) findViewById(R.id.tv_show_result);
        dbHelper = new MyDatabaseHelper(this,"IdRecords.db",null,1); //版本号为1

        //获取传入的身份证号码
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        Log.d("debug","received uid is : " + uid);

        doSearch(uid);
    }



    //定义一个handler,改变UI的显示效果HttpUtil

    private Handler handler = new Handler() {


        public void handleMessage(Message msg) {
            // process incoming messages here

            switch (msg.what){
                case SHOW_RESPONSE:
                    String response = (String) msg.obj;
                    tv_show_result.setText(response);    //设置textview的值
                    //tv_show_id_info.setText(response);
                    Toast.makeText(ResultActivity.this,"查询完成",Toast.LENGTH_SHORT).show();

            }
        }

    };



    public void doSearch(String uid){
        Log.d("debug", "调用查询函数  ， 点击button获取百度 的 ---json数据");

        //sendHttpRequestWithHttpClient();

        //查询身份证信息  ，api 来自百度 API STORE
        String httpUrl = "http://apis.baidu.com/apistore/idservice/id";
        String httpArg = "id=" + uid;  //"id=130625198912180418"
        httpUrl = httpUrl + "?" + httpArg;


        HttpUtil.sendHttpRequest(httpUrl,new HttpCallbackListener() {

            @Override
            public void onFinish(String response) {
                //在这里执行返回具体内容的逻辑
                parseJSONWithGSON(response);
                Log.d("debug","msg from baidu is : "   + response);
                //Log.d("debug","" + );

                //Log.d("debug","" + );

                //Log.d("debug","" + );
            }

            @Override
            public void onError(Exception e) {
                //在这里执行异常情况的处理
                e.printStackTrace();
            }
        });
    }



    //使用GSON处理json数据
    private void parseJSONWithGSON(String jsondata){
        Log.d("debug","处理json数据,  GSON 并打印到控制台  ----------->");
        try{

            //json data example msg from baidu is :
            // {"errNum":0,"retMsg":"success","retData":{"address":"\u6cb3\u5317\u7701\u4fdd\u5b9a\u5e02\u5f90\u6c34\u53bf","sex":"M","birthday":"1989-12-18"}}
            //将JSON文本解析为对象
            JSONTokener jsonParser = new JSONTokener(jsondata);
            JSONObject personID = (JSONObject) jsonParser.nextValue();
            //从json对象中回去信息


            String retMsg = personID.getString("retMsg");
            Log.d("debug","ret msg is : " + retMsg);
            if (retMsg.equals("success")){  //查询成功的条件下
                jsondata = personID.getJSONObject("retData").toString();

                Gson gson = new Gson();
                Person person = gson.fromJson(jsondata,Person.class);
                //List<App> appList = gson.fromJson(jsondata,new TypeToken<List<App>>(){}.getType());
                List<Person> appList = new ArrayList<Person>();
                appList.add(person);
                //foreach 的写法
                for (Person item : appList){
                    //遍历并打印相关的信息
                    Log.d("debug", "id is " + item.getAddress());
                    Log.d("debug", "name is " + item.getName());
                    Log.d("debug", "version is " + item.getBirthday());


                }


                Log.d("debug","001-----");
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Log.d("debug","002-------");
                ContentValues values = new ContentValues();
                //开始组装数据
                Log.d("debug","将数据插入sqlite数据库");
                String id_num = uid.trim();
                Log.d("debug","id nums " + id_num);

                values.put("id_num",id_num);
                values.put("sex",person.getSex());
                values.put("name",person.getName());
                values.put("address", person.getAddress() );
                values.put("birthday", person.getBirthday());
                Log.d("debug","数据插入 完成");
                db.insert("Id_records",null,values);

                values.clear();

                String str_search_result = id_num + "\n "
                        + person.getBirthday() + "\n"
                        + person.getSex() + "\n"
                        + person.getAddress() + "\n"
                        ;

                Message message = new Message();
                message.what = SHOW_RESPONSE;
                message.obj = str_search_result;
                handler.sendMessage(message);   //发送消息 更新UI的显示效果





            }else {
                Toast.makeText(ResultActivity.this,"查询失败",Toast.LENGTH_SHORT);
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
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
