package com.tianer.entity.app;

import java.io.Serializable;

/**
 * 
 * @ClassName: LoginBean
 * @Description: 存储登录的用户名和密码
 * @author jsx
 * @date 2015年10月28日 下午1:40:34
 * 
 */
public class LoginBean implements Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 4908410946237425013L;
    /**
     * 登录手机号
     */
    private String phone;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 账户名字
     */
    private String username;
    /**
     * 账户名字
     */
    private String imagehead;
    /**
     * 用户id
     */
    private String user_id;
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getImagehead() {
        return imagehead;
    }
    
    public void setImagehead(String imagehead) {
        this.imagehead = imagehead;
    }
    
    public String getUser_id() {
        return user_id;
    }
    
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
