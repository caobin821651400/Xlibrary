package cn.sccl.xlibrary.adapter;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected List<T> dataLists;

    public BaseRecyclerViewAdapter() {
    }

//    public BaseRecyclerViewAdapter() {
//    }

    /**
     * 获取指定位置的数据
     *
     * @param position
     * @return
     */
    public T getItem(int position) {
        if (dataLists == null) return null;
        return dataLists.get(position);
    }

    /**
     * 获取数据集合
     *
     * @return
     */
    public List<T> getDataLists() {
        return dataLists;
    }

    /**
     * 设置全新的数据集合，如果传入null，则清空数据列表（会清空以前的集合数据）
     *
     * @param datas
     */
    public void setDataLists(List<T> datas) {
        this.dataLists = datas;
//        if (dataLists != null)
            notifyDataSetChanged();
    }

    /**
     * 添加数据条目
     *
     * @param data
     */
    public void add(T data) {
        if (dataLists != null) {
            dataLists.add(data);
            notifyItemInserted(dataLists.size() - 1);
        }
    }


    /**
     * 在指定位置添加数据条目
     *
     * @param position
     * @param data
     */
    public void add(int position, T data) {
        if (dataLists != null) {
            dataLists.add(position, data);
            notifyItemInserted(position);
        }
    }

    /**
     * 添加数据条目集合
     *
     * @param datas
     */
    public void addAll(List<T> datas) {
        if (dataLists != null) {
            int position = dataLists.size();
            dataLists.addAll(datas);
//            notifyDataSetChanged();
            notifyItemRangeChanged(position, datas.size() - position);
        }
    }

    /**
     * 在指定位置添加数据条目集合
     *
     * @param position
     * @param datas
     */
    public void addAll(int position, List<T> datas) {
        if (dataLists != null) {
            dataLists.addAll(position, datas);
            notifyDataSetChanged();
        }
    }

    /**
     * 删除指定索引数据条目
     *
     * @param position
     */
    public void remove(int position) {
        if (position < 0 || position > dataLists.size() - 1) {
            return;
        }
        if (dataLists != null) {
            dataLists.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, dataLists.size() - position);
        }
    }

    /**
     * 删除指定数据条目
     *
     * @param data
     */
    public void remove(T data) {
        if (dataLists != null) {
            if (!dataLists.contains(data)) return;
            remove(dataLists.indexOf(data));
        }
    }


    /**
     * 替换指定索引的数据条目
     *
     * @param position
     * @param newData
     */
    public void replace(int position, T newData) {
        if (dataLists != null) {
            dataLists.set(position, newData);
            notifyItemChanged(position);
        }
    }

    /**
     * 替换指定数据条目
     *
     * @param oldData
     * @param newData
     */
    public void replace(T oldData, T newData) {
        if (dataLists != null) {
            replace(dataLists.indexOf(oldData), newData);
        }
    }

    /**
     * 交换两个数据条目的位置
     *
     * @param fromPosition
     * @param toPosition
     */
    public void move(int fromPosition, int toPosition) {
        if (dataLists != null) {
            Collections.swap(dataLists, fromPosition, toPosition);
            notifyItemMoved(fromPosition, toPosition);
        }
    }

    /**
     * 清空
     */
    public void clear() {
        if (dataLists != null) {
            dataLists.clear();
            notifyDataSetChanged();
        }
    }
}
