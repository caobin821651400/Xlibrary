package cn.sccl.xlibrary.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;

/**
 * author : caobin
 * e-mail : 821651400@qq.com
 * time   : 2017/12/21
 * desc   : 单选弹窗
 */
public class XSingleChoseDialog {

    public interface OnItemClickListener {
        void onItemClick(String name, int position);
    }

    private String title = "请选择";//标题
    private String[] items;//item
    private int checkedItems=-1;//每个item是否默认选中
    private boolean cancelable = true;//点击消失
    private Context mContext;

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private OnItemClickListener onItemClickListener;


    public XSingleChoseDialog(Context context) {
        this.mContext = context;
    }

    /**
     * 展示
     */
    public XSingleChoseDialog show() {
        showMultipleDialog();
        dialog = builder.create();
        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
//        Display d = ((Activity) mContext).getWindowManager().getDefaultDisplay();
//        params.height = (int) (d.getHeight() * 0.8);
        dialog.getWindow().setAttributes(params);
        return null;
    }

    public void disMiss() {
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
    }

    /**
     * 消失
     */
    private void showMultipleDialog() {
        builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);
        builder.setCancelable(cancelable);
        builder.setSingleChoiceItems(items, checkedItems, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(items[which], which);
                }
                disMiss();
            }
        });
    }

    public static class Builder {

        private XSingleChoseDialog choseDialog;

        public Builder(Context context) {
            choseDialog = new XSingleChoseDialog(context);
        }

        public Builder title(String title) {
            choseDialog.title = title;
            return this;
        }

        /**
         * 每个item
         *
         * @param items
         * @return
         */
        public Builder items(String[] items) {
            choseDialog.items = items;
            return this;
        }

        /**
         * 默认选中item
         *
         * @param checkedItems
         * @return
         */
        public Builder checkedItem(int checkedItems) {
            choseDialog.checkedItems = checkedItems;
            return this;
        }

        /**
         * 是否点击外部消失
         *
         * @param can
         * @return
         */
        public Builder cancelable(boolean can) {
            choseDialog.cancelable = can;
            return this;
        }

        /**
         * 选择回调
         *
         * @param listener
         * @return
         */
        public Builder multipleClickListener(OnItemClickListener listener) {
            choseDialog.onItemClickListener = listener;
            return this;
        }

        /**
         * 通过Builder类设置完属性后构造对话框的方法
         */
        public XSingleChoseDialog create() {
            return choseDialog;
        }
    }
}
