package com.gpp.cas.com.gpp.cas.result;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gpp.cas.R;

import java.util.List;

/**
 * Created by Vaibhav on 24/03/2016.
 */
public class ResultAdapter extends BaseAdapter  {
    private LayoutInflater inflater;
    private Activity activity;
    private List<Item> items;
    int pos;

    public  ResultAdapter(Activity activity,List<Item> items)
    {
        this.activity=activity;
        this.items=items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder
    {
        TextView  enroll;
        EditText mark;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        final ViewHolder viewHolder;
        pos=position;
            if (inflater == null) {

                inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }

            if (v == null) {
                v = inflater.inflate(R.layout.result_row, null);
                viewHolder=new ViewHolder();
                viewHolder.enroll = (TextView) v.findViewById(R.id.textViewEnrollNo);
                viewHolder.mark = (EditText) v.findViewById(R.id.editTextMark);

                viewHolder.mark.setTag(position);

                v.setTag(viewHolder);

               // convertView.setTag(R.id.textViewEnrollNo, viewHolder.enroll);
               // convertView.setTag(R.id.editTextMark, viewHolder.mark);
            }
            else
            {
                viewHolder = (ViewHolder) v.getTag();
            }

/*
             TextView enroll = (TextView) convertView.findViewById(R.id.textViewEnrollNo);
            EditText mark = (EditText) convertView.findViewById(R.id.editTextMark);
            //getting data for row
*/
            Item item = items.get(position);

            //title
        viewHolder.enroll.setText(item.getEnrollNo()+" Mark =="+item.getMark());
        int tag_pos=(Integer   )viewHolder.mark.getTag();
        viewHolder.mark.setId(tag_pos);
           viewHolder.mark.setOnFocusChangeListener(new View.OnFocusChangeListener() {
               @Override
               public void onFocusChange(View v, boolean hasFocus) {
                   if (!hasFocus) {
                       try
                       {

                            int postn = viewHolder.mark.getId();
                            EditText Mark = (EditText) viewHolder.mark;
                           Mark.setText(Mark.getText().toString());
                           items.get(postn).setMark(Integer.valueOf(Mark.getText().toString()));


                       } catch (Exception e) {
                           Toast.makeText(activity,"Error In Parsing marks",1000).show();
                       }
                   }

               }
           });




        return v;
    }



/*    @Override
    public void afterTextChanged(Editable s) {
    list.get(listPos).setMark(Integer.valueOf(s.toString()));
    }
    */
}
