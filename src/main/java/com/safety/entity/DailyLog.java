package com.safety.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 管理员用户实体类
 * Created by QuiteWing_YJ on 2016/12/13.
 */
@Entity
@Table(name="dailylog")
public class DailyLog {

    //主键
    @Id
    @GeneratedValue
    private Long logid;
    //登录名
    @Column(name="logname")
    private String logName;

    //角色
    @Column(name="logpath")
    private String logPath;

    @Column(name="createtime")
    private Date createTime;

    public Long getLogid() {
        return logid;
    }

    public void setLogid(Long logid) {
        this.logid = logid;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
