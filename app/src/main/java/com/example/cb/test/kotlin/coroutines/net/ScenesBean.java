package com.example.cb.test.kotlin.coroutines.net;

import java.util.List;

public class ScenesBean {

    /**
     * code : 1000
     * msg : 成功
     * data : {"rows":[{"scenarioId":"1296081969583116288","createTime":1.597844996E12,"descs":"","familyId":"1019047515184193536","icon":"ic_scene_image1","name":"测试","notifyType":1,"remark":null,"status":0,"permission":null,"preset":3,"tempId":null}],"total":1,"pageCount":1}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * rows : [{"scenarioId":"1296081969583116288","createTime":1.597844996E12,"descs":"","familyId":"1019047515184193536","icon":"ic_scene_image1","name":"测试","notifyType":1,"remark":null,"status":0,"permission":null,"preset":3,"tempId":null}]
         * total : 1.0
         * pageCount : 1.0
         */

        private double total;
        private double pageCount;
        private List<RowsBean> rows;

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public double getPageCount() {
            return pageCount;
        }

        public void setPageCount(double pageCount) {
            this.pageCount = pageCount;
        }

        public List<RowsBean> getRows() {
            return rows;
        }

        public void setRows(List<RowsBean> rows) {
            this.rows = rows;
        }

        public static class RowsBean {
            /**
             * scenarioId : 1296081969583116288
             * createTime : 1.597844996E12
             * descs :
             * familyId : 1019047515184193536
             * icon : ic_scene_image1
             * name : 测试
             * notifyType : 1.0
             * remark : null
             * status : 0.0
             * permission : null
             * preset : 3.0
             * tempId : null
             */

            private String scenarioId;
            private double createTime;
            private String descs;
            private String familyId;
            private String icon;
            private String name;
            private double notifyType;
            private Object remark;
            private double status;
            private Object permission;
            private double preset;
            private Object tempId;

            public String getScenarioId() {
                return scenarioId;
            }

            public void setScenarioId(String scenarioId) {
                this.scenarioId = scenarioId;
            }

            public double getCreateTime() {
                return createTime;
            }

            public void setCreateTime(double createTime) {
                this.createTime = createTime;
            }

            public String getDescs() {
                return descs;
            }

            public void setDescs(String descs) {
                this.descs = descs;
            }

            public String getFamilyId() {
                return familyId;
            }

            public void setFamilyId(String familyId) {
                this.familyId = familyId;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public double getNotifyType() {
                return notifyType;
            }

            public void setNotifyType(double notifyType) {
                this.notifyType = notifyType;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public double getStatus() {
                return status;
            }

            public void setStatus(double status) {
                this.status = status;
            }

            public Object getPermission() {
                return permission;
            }

            public void setPermission(Object permission) {
                this.permission = permission;
            }

            public double getPreset() {
                return preset;
            }

            public void setPreset(double preset) {
                this.preset = preset;
            }

            public Object getTempId() {
                return tempId;
            }

            public void setTempId(Object tempId) {
                this.tempId = tempId;
            }
        }
    }
}
