package com.safety.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 管理员用户实体类
 * Created by QuiteWing_YJ on 2016/12/13.
 */
@Entity
@Table(name="checkbiaoge")
public class CheckBiaoge {

    //主键
    @Id
    @GeneratedValue
    private Long id;

    private String name;


    private String checkuser;

    private Integer tag1;

    private Integer tag2;
    private Integer tag3;
    private Integer tag4;
    private Integer tag5;
    private Integer tag6;
    private Integer tag7;
    private Integer tag8;
    private Integer tag9;
    private Integer tag10;
    private Integer tag11;
    private Integer iswrite;
    private Date time;
    private Date createtime;

    public Integer getIswrite() {
        return iswrite;
    }

    public void setIswrite(Integer iswrite) {
        this.iswrite = iswrite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCheckuser() {
        return checkuser;
    }

    public void setCheckuser(String checkuser) {
        this.checkuser = checkuser;
    }

    public Integer getTag1() {
        return tag1;
    }

    public void setTag1(Integer tag1) {
        this.tag1 = tag1;
    }

    public Integer getTag2() {
        return tag2;
    }

    public void setTag2(Integer tag2) {
        this.tag2 = tag2;
    }

    public Integer getTag3() {
        return tag3;
    }

    public void setTag3(Integer tag3) {
        this.tag3 = tag3;
    }

    public Integer getTag4() {
        return tag4;
    }

    public void setTag4(Integer tag4) {
        this.tag4 = tag4;
    }

    public Integer getTag5() {
        return tag5;
    }

    public void setTag5(Integer tag5) {
        this.tag5 = tag5;
    }

    public Integer getTag6() {
        return tag6;
    }

    public void setTag6(Integer tag6) {
        this.tag6 = tag6;
    }

    public Integer getTag7() {
        return tag7;
    }

    public void setTag7(Integer tag7) {
        this.tag7 = tag7;
    }

    public Integer getTag8() {
        return tag8;
    }

    public void setTag8(Integer tag8) {
        this.tag8 = tag8;
    }

    public Integer getTag9() {
        return tag9;
    }

    public void setTag9(Integer tag9) {
        this.tag9 = tag9;
    }

    public Integer getTag10() {
        return tag10;
    }

    public void setTag10(Integer tag10) {
        this.tag10 = tag10;
    }

    public Integer getTag11() {
        return tag11;
    }

    public void setTag11(Integer tag11) {
        this.tag11 = tag11;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}
