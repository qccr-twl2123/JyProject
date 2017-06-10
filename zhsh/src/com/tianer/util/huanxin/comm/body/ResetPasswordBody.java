package com.tianer.util.huanxin.comm.body;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.tianer.util.huanxin.comm.wrapper.BodyWrapper;


 
/**
 * 
* 类名称：ResetPasswordBody   
* 类描述：   修改密码
* 创建人：魏汉文  
* 创建时间：2016年7月20日 下午2:11:49
 */
public class ResetPasswordBody implements BodyWrapper{
    private String newPassword;

    public ResetPasswordBody(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public ContainerNode<?> getBody() {
        return JsonNodeFactory.instance.objectNode().put("newpassword", newPassword);
    }

    public Boolean validate() {
        return StringUtils.isNotBlank(newPassword);
    }
}
