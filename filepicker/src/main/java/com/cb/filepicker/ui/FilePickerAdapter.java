package com.cb.filepicker.ui;

import android.content.Context;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


import com.cb.filepicker.R;
import com.cb.filepicker.models.Document;

import java.util.ArrayList;
import java.util.List;


/**
 * 文件列表适配
 * Created by caobin on 2017/3/22.
 */
public class FilePickerAdapter extends BaseAdapter {
    //扫描的集合
    private List<Document> mList;
    //已选择的集合
    private LayoutInflater mInflater;
    private Context activity;

    public FilePickerAdapter(Context activity,
                             List<Document> list, ArrayList<Document> coursewareList) {
        mInflater = LayoutInflater.from(activity);
        this.activity = activity;
        this.mList = list;
        getCheckData().addAll(coursewareList);
    }

    /**
     * @param list
     */
    public void setList(List<Document> list) {
        this.mList = list;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
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
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_list_chose_courseware, viewGroup, false);
            holder.name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.size = (TextView) convertView.findViewById(R.id.tv_size);
            holder.icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        setData(position, holder);
        return convertView;
    }

    /**
     * 填充数据
     *
     * @param position
     * @param holder
     */
    private void setData(int position, ViewHolder holder) {
        Document item = (Document) getItem(position);
        holder.name.setText(item.getTitle());
        holder.size.setText(Formatter.formatFileSize(activity, Long.parseLong(item.getSize())));
        holder.icon.setImageResource(item.getFileType().getDrawable());
        //---
        if (getCheckData().contains(item)) {
            holder.checkBox.setChecked(true);
        } else {
            holder.checkBox.setChecked(false);
        }
        holder.checkBox.setTag(position);
        holder.checkBox.setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Document item = (Document) getItem(Integer.parseInt(v.getTag().toString()));
            if (getCheckData().contains(item)) {
                getCheckData().remove(item);
            } else {
                getCheckData().add(item);
            }
        }
    };

    private ArrayList<Document> checkList;

    /**
     *
     * @return
     */
    public ArrayList<Document> getCheckData() {
        if (checkList == null) {
            checkList = new ArrayList<Document>();
        }
        return checkList;
    }

    class ViewHolder {
        TextView name;
        TextView size;
        ImageView icon;
        CheckBox checkBox;
    }
}
