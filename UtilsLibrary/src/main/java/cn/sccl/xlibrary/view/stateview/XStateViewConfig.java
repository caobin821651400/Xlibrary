package cn.sccl.xlibrary.view.stateview;


import androidx.annotation.LayoutRes;

import com.cb.xlibrary.R;


/**
 * 界面状态参数配置
 */
public class XStateViewConfig {
    private int emptyViewResId = R.layout.x_stateview_empty_cblibrary;
    private int errorViewResId = R.layout.x_stateview_error_cblibrary;
    private int loadingViewResId = R.layout.x_stateview_loading_cblibrary;
    private int noNetworkViewResId = R.layout.x_stateview_no_network_cblibrary;

    public int getEmptyViewResId() {
        return emptyViewResId;
    }

    public XStateViewConfig setEmptyViewResId(@LayoutRes int emptyViewResId) {
        this.emptyViewResId = emptyViewResId;
        return this;
    }

    public int getErrorViewResId() {
        return errorViewResId;
    }

    public XStateViewConfig setErrorViewResId(@LayoutRes int errorViewResId) {
        this.errorViewResId = errorViewResId;
        return this;
    }

    public int getLoadingViewResId() {
        return loadingViewResId;
    }

    public XStateViewConfig setLoadingViewResId(@LayoutRes int loadingViewResId) {
        this.loadingViewResId = loadingViewResId;
        return this;
    }

    public int getNoNetworkViewResId() {
        return noNetworkViewResId;
    }

    public XStateViewConfig setNoNetworkViewResId(@LayoutRes int noNetworkViewResId) {
        this.noNetworkViewResId = noNetworkViewResId;
        return this;
    }
}
