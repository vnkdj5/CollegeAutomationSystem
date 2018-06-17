package com.gpp.cas.com.gpp.cas.attendance.show;

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
public class ShowAdapter extends BaseAdapter {
    Activity con;
    LayoutInflater inflater;
    List<Attendance> list;

    public ShowAdapter(Activity con, List<Attendance> list) {
        this.con = con;
        this.list = list;
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
        if (convertView==null)
        {
            convertView=inflater.inflate(R.layout.attendance_show_row,null);
        }
        TextView tvSubject=(TextView) convertView.findViewById(R.id.tvSubject);
        TextView tvLP=(TextView) convertView.findViewById(R.id.tvLP);
        TextView tvLA=(TextView) convertView.findViewById(R.id.tvLA);
        TextView tvPP=(TextView) convertView.findViewById(R.id.tvPP);
        TextView tvPA=(TextView) convertView.findViewById(R.id.tvPA);
        TextView tvPerc=(TextView) convertView.findViewById(R.id.tvPerc);

        //Get Attendance Object from the list
        Attendance atdc=list.get(position);

        //setting texts to textViews
        tvSubject.setText(atdc.getSubject());
        tvLP.setText(atdc.getLP());
        tvLA.setText(atdc.getLA());
        tvPP.setText(atdc.getPP());
        tvPA.setText(atdc.getPA());
        tvPerc.setText(atdc.getPERC());

        return convertView;
    }
}
