package com.example.cb.test.rx;

import java.io.Serializable;

/**
 * author : caobin
 * e-mail : 821651400@qq.com
 * time   : 2018/01/25
 * desc   :
 */
public class UserInfoResp implements Serializable {


    private static final long serialVersionUID = -3214503594122853351L;
    /**
     * code : 200
     * message : 成功
     * map : null
     * url : /nfs/portal/QuickMark/15378186979_QRcode.png
     */

    private String code;
    private String message;
    private Object map;
    private UserInforBean userInfor;
    private String url;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getMap() {
        return map;
    }

    public void setMap(Object map) {
        this.map = map;
    }

    public UserInforBean getUserInfor() {
        return userInfor;
    }

    public void setUserInfor(UserInforBean userInfor) {
        this.userInfor = userInfor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static class UserInforBean implements Serializable {
        private static final long serialVersionUID = -2402246111800248345L;
        /**
         * individualId : 1017201
         * partyId : null
         * birthday : null
         * gender : 1
         * partyName : 宋立锵
         * partyAbbrname : null
         * featureUrl : /nfs/portal/QuickMark/15378186979_QRcode.png
         * postName : null
         * phone : 15378186979
         * accessDate : 2018-01-25 14:01:44
         */

        private String individualId;
        private Object partyId;
        private String birthday;
        private String gender;
        private String partyName;
        private Object partyAbbrname;
        private String featureUrl;
        private Object postName;
        private String phone;
        private String accessDate;

        public String getIndividualId() {
            return individualId;
        }

        public void setIndividualId(String individualId) {
            this.individualId = individualId;
        }

        public Object getPartyId() {
            return partyId;
        }

        public void setPartyId(Object partyId) {
            this.partyId = partyId;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getPartyName() {
            return partyName;
        }

        public void setPartyName(String partyName) {
            this.partyName = partyName;
        }

        public Object getPartyAbbrname() {
            return partyAbbrname;
        }

        public void setPartyAbbrname(Object partyAbbrname) {
            this.partyAbbrname = partyAbbrname;
        }

        public String getFeatureUrl() {
            return featureUrl;
        }

        public void setFeatureUrl(String featureUrl) {
            this.featureUrl = featureUrl;
        }

        public Object getPostName() {
            return postName;
        }

        public void setPostName(Object postName) {
            this.postName = postName;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAccessDate() {
            return accessDate;
        }

        public void setAccessDate(String accessDate) {
            this.accessDate = accessDate;
        }
    }
}
