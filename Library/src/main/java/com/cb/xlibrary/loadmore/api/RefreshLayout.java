package com.cb.xlibrary.loadmore.api;

import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

import com.cb.xlibrary.loadmore.listener.OnLoadmoreListener;
import com.cb.xlibrary.loadmore.listener.OnRefreshLoadmoreListener;


/**
 * 刷新布局
 * Created by SCWANG on 2017/5/26.
 */

@SuppressWarnings({"UnusedReturnValue", "SameParameterValue", "unused"})
public interface RefreshLayout {

    RefreshLayout setFooterHeight(float dp);

    RefreshLayout setFooterHeightPx(int px);

    RefreshLayout setHeaderHeight(float dp);

    RefreshLayout setHeaderHeightPx(int px);

    /**
     * 显示拖动高度/真实拖动高度（默认0.5，阻尼效果）
     */
    RefreshLayout setDragRate(float rate);

    /**
     * 设置下拉最大高度和Header高度的比率（将会影响可以下拉的最大高度）
     */
    RefreshLayout setHeaderMaxDragRate(float rate);

    /**
     * 设置上啦最大高度和Footer高度的比率（将会影响可以上啦的最大高度）
     */
    RefreshLayout setFooterMaxDragRate(float rate);

    /**
     * 设置 触发刷新距离 与 HeaderHieght 的比率
     */
    RefreshLayout setHeaderTriggerRate(float rate);

    /**
     * 设置 触发加载距离 与 FooterHieght 的比率
     */
    RefreshLayout setFooterTriggerRate(float rate);

    /**
     * 设置回弹显示插值器
     */
    RefreshLayout setReboundInterpolator(Interpolator interpolator);

    /**
     * 设置回弹动画时长
     */
    RefreshLayout setReboundDuration(int duration);

    /**
     * 设置是否启用上啦加载更多（默认启用）
     */
    RefreshLayout setEnableLoadmore(boolean enable);

    /**
     * 是否启用下拉刷新（默认启用）
     */
    RefreshLayout setEnableRefresh(boolean enable);

    /**
     * 设置是否启在下拉Header的同时下拉内容
     */
    RefreshLayout setEnableHeaderTranslationContent(boolean enable);

    /**
     * 设置是否启在上拉Footer的同时上拉内容
     */
    RefreshLayout setEnableFooterTranslationContent(boolean enable);

    /**
     * 设置是否开启在刷新时候禁止操作内容视图
     */
    RefreshLayout setDisableContentWhenRefresh(boolean disable);

    /**
     * 设置是否开启在加载时候禁止操作内容视图
     */
    RefreshLayout setDisableContentWhenLoading(boolean disable);

    /**
     * 设置是否监听列表在滚动到底部时触发加载事件（默认true）
     */
    RefreshLayout setEnableAutoLoadmore(boolean enable);

    /**
     * 标记数据全部加载完成，将不能再次触发加载功能（true）
     *
     * @deprecated 请使用 finishLoadmoreWithNoMoreData 和 resetNoMoreData 代替
     */
    @Deprecated
    RefreshLayout setLoadmoreFinished(boolean finished);

    /**
     * 设置指定的Footer
     */
    RefreshLayout setRefreshFooter(RefreshFooter footer);

    /**
     * 设置指定的Footer
     */
    RefreshLayout setRefreshFooter(RefreshFooter footer, int width, int height);


    /**
     * 设置指定的Content
     */
    RefreshLayout setRefreshContent(View content);

    /**
     * 设置指定的Content
     */
    RefreshLayout setRefreshContent(View content, int width, int height);

    /**
     * 设置是否启用越界回弹
     */
    RefreshLayout setEnableOverScrollBounce(boolean enable);

    /**
     * 设置是否开启纯滚动模式
     */
    RefreshLayout setEnablePureScrollMode(boolean enable);

    /**
     * 设置是否在加载更多完成之后滚动内容显示新数据
     */
    RefreshLayout setEnableScrollContentWhenLoaded(boolean enable);

    /**
     * 是否在刷新完成之后滚动内容显示新数据
     */
    RefreshLayout setEnableScrollContentWhenRefreshed(boolean enable);

    /**
     * 设置在内容不满一页的时候，是否可以上拉加载更多
     */
    RefreshLayout setEnableLoadmoreWhenContentNotFull(boolean enable);

    /**
     * 设置是否启用越界拖动（仿苹果效果）
     */
    RefreshLayout setEnableOverScrollDrag(boolean enable);

    /**
     * 设置是否在全部加载结束之后Footer跟随内容
     */
    RefreshLayout setEnableFooterFollowWhenLoadFinished(boolean enable);

    /**
     * 设置是会否启用嵌套滚动功能（默认关闭+智能开启）
     */
    RefreshLayout setEnableNestedScroll(boolean enabled);


    /**
     * 单独设置加载监听器
     */
    RefreshLayout setOnLoadmoreListener(OnLoadmoreListener listener);

    /**
     * 同时设置刷新和加载监听器
     */
    RefreshLayout setOnRefreshLoadmoreListener(OnRefreshLoadmoreListener listener);

    /**
     * 设置主题颜色
     */
    RefreshLayout setPrimaryColorsId(@ColorRes int... primaryColorId);

    /**
     * 设置主题颜色
     */
    RefreshLayout setPrimaryColors(int... colors);


    /**
     * 完成刷新
     */
    RefreshLayout finishRefresh();

    /**
     * 完成加载
     */
    RefreshLayout finishLoadmore();

    /**
     * 完成刷新
     */
    RefreshLayout finishRefresh(int delayed);

    /**
     * 完成加载
     *
     * @param success 数据是否成功刷新 （会影响到上次更新时间的改变）
     */
    RefreshLayout finishRefresh(boolean success);

    /**
     * 完成刷新
     */
    RefreshLayout finishRefresh(int delayed, boolean success);

    /**
     * 完成加载
     */
    RefreshLayout finishLoadmore(int delayed);

    /**
     * 完成加载
     */
    RefreshLayout finishLoadmore(boolean success);

    /**
     * 完成加载
     */
    RefreshLayout finishLoadmore(int delayed, boolean success);

    /**
     * 完成加载
     */
    RefreshLayout finishLoadmore(int delayed, boolean success, boolean noMoreData);

    /**
     * 完成加载并标记没有更多数据
     */
    RefreshLayout finishLoadmoreWithNoMoreData();

    /**
     * 恢复没有更多数据的原始状态
     */
    RefreshLayout resetNoMoreData();


    /**
     * 获取当前 Footer
     */
    @Nullable
    RefreshFooter getRefreshFooter();

    /**
     * 获取当前状态
     */
    RefreshState getState();

    /**
     * 获取实体布局视图
     */
    ViewGroup getLayout();

    /**
     * 是否正在刷新
     */
    boolean isRefreshing();

    /**
     * 是否正在加载
     */
    boolean isLoading();

    /**
     * 自动刷新
     */
    boolean autoRefresh();

    /**
     * 自动刷新
     *
     * @param delayed 开始延时
     */
    boolean autoRefresh(int delayed);

    /**
     * 自动刷新
     *
     * @param delayed  开始延时
     * @param duration 拖拽动画持续时间
     * @param dragrate 拉拽的高度比率（要求 ≥ 1 ）
     */
    boolean autoRefresh(int delayed, int duration, float dragrate);

    /**
     * 自动加载
     */
    boolean autoLoadmore();

    /**
     * 自动加载
     *
     * @param delayed 开始延时
     */
    boolean autoLoadmore(int delayed);

    /**
     * 自动加载
     *
     * @param delayed  开始延时
     * @param duration 拖拽动画持续时间
     * @param dragrate 拉拽的高度比率（要求 ≥ 1 ）
     */
    boolean autoLoadmore(int delayed, int duration, float dragrate);

    boolean isEnableRefresh();

    boolean isEnableLoadmore();

    boolean isLoadmoreFinished();

    boolean isEnableAutoLoadmore();

    boolean isEnableOverScrollBounce();

    boolean isEnablePureScrollMode();

    boolean isEnableScrollContentWhenLoaded();
}
