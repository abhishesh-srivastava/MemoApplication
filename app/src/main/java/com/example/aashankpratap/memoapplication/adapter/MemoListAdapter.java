package com.example.aashankpratap.memoapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.aashankpratap.memoapplication.R;
import com.example.aashankpratap.memoapplication.model.Memo;

import java.util.List;

/**
 * Adapter class for binding listview and memo list data
 */
public class MemoListAdapter extends BaseAdapter{

    private List<Memo> mList;
    private LayoutInflater mInflater;

    public MemoListAdapter(Context context, List<Memo>list) {
        mList = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item,parent,false);
            ViewHolder vh = new ViewHolder();
            vh.mTxtMemoContent = (TextView) convertView.findViewById(R.id.memo_content);
            vh.mTxtMemoTag = (TextView) convertView.findViewById(R.id.memo_tag);
            convertView.setTag(vh);
        }
        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.mTxtMemoContent.setText(mList.get(position).getContent());
        viewHolder.mTxtMemoTag.setText(mList.get(position).getTag());
        return convertView;
    }

    public void setList(List<Memo> list) {
        mList = list;
    }

    // Using view holder pattern ensure smooth scrolling in list as it avoids frequent calls to findViewById
    private static class ViewHolder{
        TextView mTxtMemoTag;
        TextView mTxtMemoContent;
    }
}
