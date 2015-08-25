package com.hs.searchid.searchid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hs.searchid.searchid.R;
import com.hs.searchid.searchid.models.Person;

import java.util.List;

/**
 * Created by User on 2015/8/25.
 */
public class PersonAdapter extends ArrayAdapter<Person> {

    private int resourceId;

    public PersonAdapter(Context context, int textViewResourceId, List<Person> objects) {  //constructor
        super(context, textViewResourceId, objects);
        this.resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Person person = getItem(position);

        View view;
        ViewHolder viewHolder ;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
            viewHolder = new ViewHolder();
            viewHolder.person_uid = (TextView) view.findViewById(R.id.lv_person_uid);
            viewHolder.person_address = (TextView) view.findViewById(R.id.lv_person_address);
            view.setTag(viewHolder);
        }else {
            view = convertView;  //将之前加载好的布局进行缓存
            viewHolder = (ViewHolder) view.getTag();  //重新获取viewHolder
        }


        viewHolder.person_uid.setText(person.getUid());  //设置显示的图片
        viewHolder.person_address.setText(person.getAddress());    //设置显示的文字

        return view;
    }


    //内部类viewHolder对控件的实例进行缓存。不用每次都调用findViewById获取控件实例了
    class ViewHolder {

        TextView person_uid;
        TextView person_address;
    }
}
