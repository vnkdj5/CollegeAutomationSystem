package com.gpp.cas.com.gpp.cas.timetable;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gpp.cas.R;

import java.util.List;

/**
 * Created by Vaibhav on 30/03/2016.
 */
public class TimetableAdapter extends BaseAdapter {
Activity con;
    LayoutInflater inflater;
    List<Timetable> list;
    TimetableAdapter(Activity con,List<Timetable> list)
    {
this.con=con;
        this.list=list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(inflater==null)
        {

            inflater=(LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(convertView ==null)
        {
            convertView=inflater.inflate(R.layout.timetable_row,null);
        }
        TextView time=(TextView) convertView.findViewById(R.id.tvTime);
        TextView perioad=(TextView) convertView.findViewById(R.id.tvPeriod);
        Timetable timetable= list.get(position);
        time.setText(timetable.getTime());
        perioad.setText(timetable.getPeriod());
        return convertView;
    }
}
