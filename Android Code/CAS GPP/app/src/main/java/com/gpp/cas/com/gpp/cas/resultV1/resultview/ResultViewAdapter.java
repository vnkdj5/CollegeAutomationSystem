package com.gpp.cas.com.gpp.cas.resultV1.resultview;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.gpp.cas.R;

import java.util.List;

/**
 * Created by Vaibhav on 18/04/2016.
 */
public class ResultViewAdapter extends BaseAdapter {
    Activity con;
    LayoutInflater inflater;
    List<ResultItem> list;

    public ResultViewAdapter(Activity con, List<ResultItem> list) {
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
        try {
            if (inflater == null) {
                inflater = (LayoutInflater) con.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.result_view_row, null);
            }
            TextView tvtype = (TextView) convertView.findViewById(R.id.typeRV);
            TextView tvmarks = (TextView) convertView.findViewById(R.id.marksRV);
            TextView tvMaxmarks = (TextView) convertView.findViewById(R.id.maxMarksRV);

            ResultItem rs = list.get(position);
            tvtype.setText(rs.getType());
            tvmarks.setText(rs.getMarks());
            tvMaxmarks.setText(rs.getMaxMarks());
      }
        catch (Exception e)
        {
            Toast.makeText(con,"Error in Adapter",Toast.LENGTH_SHORT).show();
        }
        return convertView;
    }
}
