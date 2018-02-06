package com.cb.xlibrary.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ListView;

/**
 * author : caobin
 * e-mail : 821651400@qq.com
 * time   : 2017/12/21
 * desc   : 多选弹窗
 */
public class XMultipleChoseDialog {

    public interface XMultipleClickListener {
        void choseCallBack(String name, String position);
    }

    private String title = "请选择";//标题
    private String[] items;//item
    private boolean[] checkedItems;//每个item是否默认选中
    private Context mContext;

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private ListView mListView;
    private XMultipleClickListener multipleClickListener;


    public XMultipleChoseDialog(Context context) {
        this.mContext = context;
    }

    /**
     * 展示
     */
    public XMultipleChoseDialog show() {
        showMultipleDialog();
        dialog = builder.create();
        dialog.show();
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        Display d = ((Activity) mContext).getWindowManager().getDefaultDisplay();
        params.height = (int) (d.getHeight() * 0.8);
        mListView = dialog.getListView();
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
        builder.setTitle(title);//new boolean[]{true, false, false, true, false,true, false, false, true, false,true, false, false, true, false}
        builder.setMultiChoiceItems(items, checkedItems,
                new DialogInterface.OnMultiChoiceClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which, boolean isChecked) {
                    }
                });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                StringBuilder stringBuilder1 = new StringBuilder();
                StringBuilder stringBuilder2 = new StringBuilder();
                // 扫描所有的列表项，如果当前列表项被选中，名称和位置已逗号隔开
                for (int i = 0; i < items.length; i++) {
                    if (mListView.getCheckedItemPositions().get(i)) {
                        stringBuilder1.append(mListView.getAdapter().getItem(i) + ",");
                        stringBuilder2.append(i + ",");
                    }
                }
                if (multipleClickListener != null) {
                    multipleClickListener.choseCallBack(stringBuilder1.toString(), stringBuilder2.toString());
                }
            }
        });
        builder.setNegativeButton("取消", null);
    }

    public static class Builder {

        private XMultipleChoseDialog choseDialog;

        public Builder(Context context) {
            choseDialog = new XMultipleChoseDialog(context);
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
         * 每个是否默认选中
         *
         * @param checkedItems
         * @return
         */
        public Builder checkedItems(boolean[] checkedItems) {
            choseDialog.checkedItems = checkedItems;
            return this;
        }

        /**
         * 选择回调
         *
         * @param multipleClickListener
         * @return
         */
        public Builder multipleClickListener(XMultipleClickListener multipleClickListener) {
            choseDialog.multipleClickListener = multipleClickListener;
            return this;
        }

        /**
         * 通过Builder类设置完属性后构造对话框的方法
         */
        public XMultipleChoseDialog create() {
            return choseDialog;
        }
    }
}


//                        // 用户至少选择了一个列表项,点击事件
//                        if (lv.getCheckedItemPositions().size() > 0) {
//                            new AlertDialog.Builder(PopActivity.this)
//                                    .setMessage(s).show();
//                            System.out.println(lv.getCheckedItemPositions().size());
//                        }
//
//                        // 用户未选择任何列表项
//                        else if (lv.getCheckedItemPositions().size() <= 0) {
//                            new AlertDialog.Builder(PopActivity.this)
//                                    .setMessage("您未选择任何省份").show();
//                        }
