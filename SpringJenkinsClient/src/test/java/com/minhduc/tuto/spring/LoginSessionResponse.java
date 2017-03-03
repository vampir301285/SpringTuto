package com.minhduc.tuto.spring;

import java.util.LinkedHashMap;

public class LoginSessionResponse {

    private LinkedHashMap<String, String> session;
    
    private LinkedHashMap<String, String> loginInfo;

    /**
     * @return the session
     */
    public LinkedHashMap<String, String> getSession() {
        return session;
    }

    /**
     * @param session the session to set
     */
    public void setSession(LinkedHashMap<String, String> session) {
        this.session = session;
    }

    /**
     * @return the loginInfo
     */
    public LinkedHashMap<String, String> getLoginInfo() {
        return loginInfo;
    }

    /**
     * @param loginInfo the loginInfo to set
     */
    public void setLoginInfo(LinkedHashMap<String, String> loginInfo) {
        this.loginInfo = loginInfo;
    }

    @Override
    public String toString() {
        return "JiraLoginResponse [session=" + session + ", loginInfo=" + loginInfo + "]";
    }
    
    
}
