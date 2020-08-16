package com.example.cb.test.kotlin.coroutines.net;

import java.util.List;

public class ZnsListBean {

    private int total;
    private List<DataBean> arr;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<DataBean> getArr() {
        return arr;
    }

    public void setArr(List<DataBean> arr) {
        this.arr = arr;
    }

    public static class DataBean {
        private String propaganda_id;
        private String propaganda_name;
        private String propaganda_type;
        private String cover_type;
        private String cover_url;
        private String act_type;
        private String page_content;
        private String short_desc;
        private String adver_url;
        private String status;
        private String videoUrl;
        private long create_time;

        public String getId() {
            return propaganda_id;
        }

        public void setId(String id) {
            this.propaganda_id = id;
        }

        public long getCreate_time() {
            return create_time;
        }

        public void setCreate_time(long create_time) {
            this.create_time = create_time;
        }

        public String getVideoUrl() {
            return videoUrl;
        }

        public void setVideoUrl(String videoUrl) {
            this.videoUrl = videoUrl;
        }

        public String getPropaganda_name() {
            return propaganda_name;
        }

        public void setPropaganda_name(String propaganda_name) {
            this.propaganda_name = propaganda_name;
        }

        public String getPropaganda_type() {
            return propaganda_type;
        }

        public void setPropaganda_type(String propaganda_type) {
            this.propaganda_type = propaganda_type;
        }

        public String getCover_type() {
            return cover_type;
        }

        public void setCover_type(String cover_type) {
            this.cover_type = cover_type;
        }

        public String getCover_url() {
            return cover_url;
        }

        public void setCover_url(String cover_url) {
            this.cover_url = cover_url;
        }

        public String getAct_type() {
            return act_type;
        }

        public void setAct_type(String act_type) {
            this.act_type = act_type;
        }

        public String getPage_content() {
            return page_content;
        }

        public void setPage_content(String page_content) {
            this.page_content = page_content;
        }

        public String getShort_desc() {
            return short_desc;
        }

        public void setShort_desc(String short_desc) {
            this.short_desc = short_desc;
        }

        public String getAdver_url() {
            return adver_url;
        }

        public void setAdver_url(String adver_url) {
            this.adver_url = adver_url;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
