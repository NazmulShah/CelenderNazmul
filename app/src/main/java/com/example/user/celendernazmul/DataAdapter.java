package com.example.user.celendernazmul;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DataAdapter extends BaseAdapter{
	
	private ArrayList<KumheiModel> mArrayList;
    LayoutInflater inflater;
    private Context mContext ;

    public DataAdapter(Context context) {
        mContext = context ;
    }

    @Override
    public int getCount() {
        return mArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            System.out.println("convert view null");
            holder = new ViewHolder();
            inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.list_detail, null);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    private class ViewHolder {

        ImageView imvTick ;
        TextView tvDate, tvName;

    }

}
