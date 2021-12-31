package cn.sccl.xlibrary.utils;

import org.json.JSONObject;

/**
 * @author: bincao2
 * @date: 2021/12/31 9:57
 * @desc: 描述
 * @updateUser: 更新者
 * @updateDate: 2021/12/31 9:57
 * @updateRemark: 更新说明
 */
public class XJsonUtils {

    /**
     * URL转JSON格式
     *
     * @param paramStr
     * @return
     */
    public static String getJsonStrByQueryUrl(String paramStr) {
        String[] params = paramStr.split("&");
        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < params.length; i++) {
            String[] param = params[i].split("=");
            if (param.length >= 2) {
                String key = param[0];
                String value = param[1];
                for (int j = 2; j < param.length; j++) {
                    value += "=" + param[j];
                }
                try {
                    jsonObject.put(key, value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonObject.toString();
    }
}
