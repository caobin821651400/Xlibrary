package cn.sccl.xlibrary.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.cb.xlibrary.R;

import java.util.List;

import cn.sccl.xlibrary.adapter.progress.XAutoAnimImageView;


public abstract class XRecyclerViewAdapter<T> extends BaseRecyclerViewAdapter<T, XViewHolder> {
    //用来存放head和footer  view的集合
    private SparseArray<View> mHeaderViews = new SparseArray<>();
    private SparseArray<View> mFooterViews = new SparseArray<>();

    private static int TYPE_HEADER = 0x100;//头部标识
    private static int TYPE_FOOTER = 0x200;//
    private static final int TYPE_LOAD_FAILED = 0x1;//加载失败
    private static final int TYPE_NO_MORE = 0x2;//没有更多
    private static final int TYPE_LOAD_MORE = 0x3;//可以加载
    private static final int TYPE_NO_VIEW = 0x4;//没有view
    private static final int TYPE_EMPTY = 0x5;//空数据
    private static final int TYPE_NETWORK_ERROR = 0x6;//网络出错

    private RecyclerView mRecyclerView;
    private Context mContext;
    private View mLoadMoreView;
    private View mLoadMoreFailedView;
    private View mNoMoreView;
    private int mLoadItemType = TYPE_NO_VIEW;//默认footer

    private String mEmptyViewTxt = "暂无数据";
    private int mEmptyViewImg = R.drawable.xloading_empty_view_cblibrary;

    private OnLoadMoreListener onLoadMoreListener;
    private LayoutInflater inflater;
    private int layoutId;
    private boolean isLoadError = false;//标记是否加载出错
    private boolean isCanLoadMore = false;//是否有加载更多
    private boolean isFirst = false;//是否是第一次getCount
    private boolean isEnableEmpty = true;//是否允许空数据显示
    private boolean isNetworkError = false;//网络出错
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private OnNetworkErrorListener networkErrorListener;
    private OnLoadMoreErrorListener loadMoreErrorListener;

    public XRecyclerViewAdapter(@NonNull RecyclerView mRecyclerView) {
        this(mRecyclerView, -1);
    }

    public XRecyclerViewAdapter(@NonNull RecyclerView mRecyclerView, @LayoutRes int layoutId) {
        this.mRecyclerView = mRecyclerView;
        this.layoutId = layoutId;
        this.isFirst = true;
        this.mContext = mRecyclerView.getContext();
        this.inflater = LayoutInflater.from(mContext);
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public int getItemCount() {
        if (isNetworkError) return 1;
        if ((dataLists == null || dataLists.size() == 0) && !isFirst && isEnableEmpty) {
            //空数据返回1+getHeaderCount()
            return getHeaderCount() + 1;
        }
        return getDataCount() + getHeaderCount() + getFooterCount() + (isCanLoadMore ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        if (isNetworkError) return TYPE_NETWORK_ERROR;
        if (isHeaderPosition(position)) {
            return mHeaderViews.keyAt(position);
        }
        if ((dataLists == null || dataLists.size() == 0) && !isFirst && isEnableEmpty) {
            return TYPE_EMPTY;
        }
        if (isLoadPosition(position)) {
            return mLoadItemType;
        }
        if (isFooterPosition(position)) {
            position = position - getHeaderCount() - getDataCount();
            return mFooterViews.keyAt(position);
        }
        position = position - getHeaderCount();
        return getItemLayoutResId(getItem(position), position);
    }

    @NonNull
    @Override
    public XViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isHeaderViewType(viewType)) {
            return new XViewHolder(mHeaderViews.get(viewType));
        }
        if (isFooterViewType(viewType)) {
            return new XViewHolder(mFooterViews.get(viewType));
        }
        if (viewType == TYPE_NO_MORE) {
            mNoMoreView = inflater.inflate(R.layout.xadapter_no_more_cblibrary, mRecyclerView, false);
            return new XViewHolder(mNoMoreView);
        }
        if (viewType == TYPE_LOAD_MORE) {
            mLoadMoreView = inflater.inflate(R.layout.xadapter_load_more_cblibrary, mRecyclerView, false);
            return new XViewHolder(mLoadMoreView);
        }
        if (viewType == TYPE_LOAD_FAILED) {
            mLoadMoreFailedView = inflater.inflate(R.layout.xadapter_load_failed_cblibrary, mRecyclerView, false);
            return new XViewHolder(mLoadMoreFailedView);
        }
        if (viewType == TYPE_EMPTY) {//空数据
            View view = inflater.inflate(R.layout.xadapter_load_empty_cblibrary, mRecyclerView, false);
            return new XViewHolder(view);
        }
        if (viewType == TYPE_NETWORK_ERROR) {//网络出错
            View view = inflater.inflate(R.layout.xadapter_network_error_cblibrary, mRecyclerView, false);
            return new XViewHolder(view);
        }
        return new XViewHolder(inflater.inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(final XViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_EMPTY) {
            ((ImageView) holder.getView(R.id.iv_xadapter_empty_view)).setImageResource(mEmptyViewImg);
            ((TextView) holder.getView(R.id.iv_xadapter_empty_txt)).setText(mEmptyViewTxt);
            return;
        }
        if (holder.getItemViewType() == TYPE_NETWORK_ERROR) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (networkErrorListener != null) networkErrorListener.onRetry();
                }
            });
            return;
        }
        if (holder.getItemViewType() == TYPE_LOAD_FAILED) {
            mLoadMoreFailedView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (loadMoreErrorListener != null) {
                        loadMoreErrorListener.onRetry();
                        isLoadMore(true);
                    }
                }
            });
            return;
        }
        if (holder.getItemViewType() == TYPE_LOAD_MORE) {
            final XAutoAnimImageView imageView = holder.getView(R.id.progress_view_cblibrary);
            final TextView textView = holder.getView(R.id.load_txt_cblibrary);
            textView.setText("点击加载更多");
            imageView.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onLoadMoreListener != null && isCanLoadMore) {
                        textView.setText("努力加载中...");
                        imageView.setVisibility(View.VISIBLE);
                        onLoadMoreListener.onLoadMore();
                    }
                }
            });
//            if (onLoadMoreListener != null && isCanLoadMore) {
//                if (!isLoadError) {
//                    holder.getConvertView().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            onLoadMoreListener.onLoadMore();
//                        }
//                    }, 2000);
//                }
//            }
            return;
        }
        if (isFooterPosition(position) || isHeaderPosition(position)) return;

        final int finalPosition = position - getHeaderCount();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(holder.itemView, finalPosition);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    return onItemLongClickListener.onItemLongClick(holder.itemView, finalPosition);
                }
                return false;
            }
        });

        bindData(holder, getItem(finalPosition), finalPosition);
    }

    protected abstract void bindData(XViewHolder holder, T data, int position);

    /**
     * 返回正常item布局
     *
     * @return
     */
    public int getItemLayoutResId(T data, int position) {
        return layoutId;
    }

    public int getDataCount() {
        return dataLists == null ? 0 : dataLists.size();
    }

    public int getHeaderCount() {
        return mHeaderViews.size();
    }

    public int getFooterCount() {
        return mFooterViews.size();
    }

    /**
     * 是否可以加载更多
     */
    public void isLoadMore(boolean isHaveStatesView) {
        mLoadItemType = TYPE_LOAD_MORE;
        isLoadError = false;
        this.isCanLoadMore = isHaveStatesView;
        notifyItemChanged(getItemCount());
    }

    /**
     * 加载更多失败
     */
    public void showLoadError() {
        mLoadItemType = TYPE_LOAD_FAILED;
        isLoadError = true;
        isCanLoadMore = true;
        notifyItemChanged(getItemCount());
    }

    /**
     * 设置网络出错
     *
     * @param b
     */
    public void showNetworkError(boolean b) {
        this.isNetworkError = b;
        notifyDataSetChanged();
    }

    /**
     * 加载更多完成
     */
    public void showLoadComplete() {
        mLoadItemType = TYPE_NO_MORE;
        isLoadError = false;
        isCanLoadMore = true;
        notifyItemChanged(getItemCount());
    }

    /**
     * 刷新数据
     *
     * @param datas
     */
    @Override
    public void setDataLists(List<T> datas) {
        isFirst = false;
        isNetworkError = false;
        if (datas == null || datas.isEmpty() || datas.size() == 0 && isEnableEmpty) {
            mLoadItemType = TYPE_EMPTY;
        } else {
            mLoadItemType = TYPE_LOAD_MORE;
        }
        super.setDataLists(datas);
    }

    /**
     * 判断是否是FooterView
     *
     * @param viewType
     * @return
     */
    private boolean isFooterViewType(int viewType) {
        int position = mFooterViews.indexOfKey(viewType);
        return position >= 0;
    }

    /**
     * 判断是否是HeaderView
     *
     * @param viewType
     * @return
     */
    public boolean isHeaderViewType(int viewType) {
        int position = mHeaderViews.indexOfKey(viewType);
        return position >= 0;
    }

    /**
     * 当前位置是否是Footer
     *
     * @param position
     * @return
     */
    public boolean isFooterPosition(int position) {
        return position >= (getHeaderCount() + getDataCount());
    }

    /**
     * 当前位置是否是加载更多
     *
     * @param position
     * @return
     */
    public boolean isLoadPosition(int position) {
        return position == getItemCount() - 1 && isCanLoadMore;
    }

    /**
     * 当前位置是否是headView
     *
     * @param position
     * @return
     */
    public boolean isHeaderPosition(int position) {
        return position < getHeaderCount();
    }

    /**
     * 添加HeaderView
     *
     * @param view
     */
    public void addHeaderView(View view) {
        int position = mHeaderViews.indexOfValue(view);
        if (position < 0) {
            mHeaderViews.put(TYPE_HEADER++, view);
        }
        notifyDataSetChanged();
    }

    /**
     * 添加FooterView
     *
     * @param view
     */
    public void addFooterView(View view) {
        int position = mFooterViews.indexOfValue(view);
        if (position < 0) {
            mFooterViews.put(TYPE_FOOTER++, view);
        }
        notifyDataSetChanged();
    }

    /**
     * 去掉HeaderView
     *
     * @param view
     */
    public void removeHeaderView(View view) {
        int index = mHeaderViews.indexOfValue(view);
        if (index < 0) return;
        mHeaderViews.removeAt(index);
        notifyDataSetChanged();
    }

    /**
     * 展示空数据
     *
     * @param b
     */
    public void setEnableEmpty(boolean b) {
        this.isEnableEmpty = b;
    }

    /**
     * 设置空View图片和文字
     *
     * @param img
     * @param txt
     */
    public void setEmptyStringAndImg(@DrawableRes int img, String txt) {
        this.mEmptyViewImg = img;
        this.mEmptyViewTxt = txt;
    }

    /**
     * 去掉FooterView
     *
     * @param view
     */
    public void removeFooterView(View view) {
        int index = mFooterViews.indexOfValue(view);
        if (index < 0) return;
        mFooterViews.removeAt(index);
        notifyDataSetChanged();
    }

    /**
     * 目的让headView和footerView独占一行
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {   // 布局是GridLayoutManager所管理
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // 如果是Header、Footer的对象则占据spanCount的位置，否则就只占用1个位置
                    return (isHeaderPosition(position) || isFooterPosition(position)) ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }
    }

    /**
     * 目的让headView和footerView独占一行
     */
    @Override
    public void onViewAttachedToWindow(XViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isFooterPosition(position) || isHeaderPosition(position)) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    /**
     * 上拉加载监听
     *
     * @param onLoadMoreListener
     * @return
     */
    public XRecyclerViewAdapter<T> setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
        return this;
    }

    /**
     * item点击事件
     *
     * @param onItemClickListener
     * @return
     */
    public XRecyclerViewAdapter<T> setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    /**
     * item长按事件
     *
     * @param onItemLongClickListener
     * @return
     */
    public XRecyclerViewAdapter<T> setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
        return this;
    }

    /**
     * 网络出错点击重试
     *
     * @param networkErrorListener
     * @return
     */
    public XRecyclerViewAdapter<T> setNetworkErrorListener(OnNetworkErrorListener networkErrorListener) {
        this.networkErrorListener = networkErrorListener;
        return this;
    }

    /**
     * 加载更多时网络出错点击重试
     *
     * @param listener
     * @return
     */
    public XRecyclerViewAdapter<T> setOnLoadMoreErrorListener(OnLoadMoreErrorListener listener) {
        this.loadMoreErrorListener = listener;
        return this;
    }


    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public interface OnLoadMoreErrorListener {
        void onRetry();//点击重试回调
    }

    public interface OnNetworkErrorListener {
        void onRetry();//点击重试回调
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View v, int position);
    }
}
