package com.safety.dao;


import com.safety.entity.CheckBiaoge;
import com.safety.entity.CheckGuzhang;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 管理员数据访问接口
 * Created by QuiteWing_YJ on 2016/12/13.
 */
public interface CheckBiaogeDao extends JpaRepository<CheckBiaoge,Long> {


}
