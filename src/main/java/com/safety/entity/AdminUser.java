package com.safety.entity;

import javax.persistence.*;

/**
 * 管理员用户实体类
 * Created by QuiteWing_YJ on 2016/12/13.
 */
@Entity
@Table(name="adminuser")
public class AdminUser {

    //主键
    @Id
    @GeneratedValue
    private Long id;
    //登录名
    @Column(name="loginname")
    private String loginName;
    //登录密码
    private String password;
    //角色
    @Column(name="rolename")
    private String roleName;
    //状态
    @Column(name="isenable")
    private Boolean isEnable;
    //备注
    @Column(name="remark")
    private String Remark;

    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Boolean getEnable() {
        return isEnable;
    }

    public void setEnable(Boolean enable) {
        isEnable = enable;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
}
