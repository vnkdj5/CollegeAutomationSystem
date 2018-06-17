package com.gpp.cas.com.gpp.cas.resultV1;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.gpp.cas.R;

import java.util.List;

/**
 * Created by Vaibhav on 03/04/2016.
 */
public class ResultAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Result> resultList;

    public ResultAdapter(Activity activity, List<Result> resultList) {
        this.activity = activity;
        this.resultList = resultList;
    }


    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Object getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
        {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.result_row, null);
            holder.enroll=(TextView) convertView.findViewById(R.id.textViewEnrollNo);
            holder.mark=(EditText) convertView.findViewById(R.id.editTextMark);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }


        final Result result=resultList.get(position);

        holder.enroll.setText(result.getEnrollNo());
        if (holder.mark.getTag() instanceof TextWatcher) {
            holder.mark.removeTextChangedListener((TextWatcher) (holder.mark.getTag()));
        }
        if (TextUtils.isEmpty(String.valueOf(result.getMarks()))) {
            holder.mark.setText("");
        } else {
            holder.mark.setText(result.getMarks());
        }
        holder.mark.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {


                    if (!holder.mark.isFocused()) {
                        holder.mark.requestFocus();
                        holder.mark.onWindowFocusChanged(true);
                    }
                }
                return false;
            }
        });

        final TextWatcher watcher = new SimpleTextwatcher() {

            @Override

            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    result.setMarks(null);
                } else {
                    result.setMarks(String.valueOf(s));
                }
            }


        };
        holder.mark.addTextChangedListener(watcher);
        holder.mark.setTag(watcher);
          /*  public void afterTextChanged(Editable s) {
                try {
                    resultList.get(position).setMarks(Integer.parseInt(s.toString()));
                } catch (Exception e) {
                    Toast.makeText(activity,"EditText Error",Toast.LENGTH_LONG).show();
                }
            }
        */
         return convertView;
    }
    static class ViewHolder {
        EditText mark;
        TextView enroll;
}

}
