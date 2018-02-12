package com.safety.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 管理员用户实体类
 * Created by QuiteWing_YJ on 2016/12/13.
 */
@Entity
@Table(name="checkguzhang")
public class CheckGuzhang {

    //主键
    @Id
    @GeneratedValue
    private Long id;
    private Long checkbiaogeid;
    private String local;
    private String checkuser;
    private Date recordtime;
    private String linkphone;
    private String gzjl;
    private String gzpd;
    private String gzcl;
    private String bz;
    private Date createtime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCheckbiaogeid() {
        return checkbiaogeid;
    }

    public void setCheckbiaogeid(Long checkbiaogeid) {
        this.checkbiaogeid = checkbiaogeid;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getCheckuser() {
        return checkuser;
    }

    public void setCheckuser(String checkuser) {
        this.checkuser = checkuser;
    }

    public Date getRecordtime() {
        return recordtime;
    }

    public void setRecordtime(Date recordtime) {
        this.recordtime = recordtime;
    }

    public String getLinkphone() {
        return linkphone;
    }

    public void setLinkphone(String linkphone) {
        this.linkphone = linkphone;
    }

    public String getGzjl() {
        return gzjl;
    }

    public void setGzjl(String gzjl) {
        this.gzjl = gzjl;
    }

    public String getGzpd() {
        return gzpd;
    }

    public void setGzpd(String gzpd) {
        this.gzpd = gzpd;
    }

    public String getGzcl() {
        return gzcl;
    }

    public void setGzcl(String gzcl) {
        this.gzcl = gzcl;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
