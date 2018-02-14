package com.safety.dao;


import com.safety.entity.CheckBiaoge;
import com.safety.entity.CheckGuzhang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * 管理员数据访问接口
 * Created by QuiteWing_YJ on 2016/12/13.
 */
public interface CheckBiaogeDao extends JpaRepository<CheckBiaoge,Long> {

    @Query(value = "SELECT * FROM checkbiaoge WHERE  DATE_FORMAT(`time`,'%Y-%m-%d') = DATE_FORMAT(?1,'%Y-%m-%d')",nativeQuery = true)
    List<CheckBiaoge> findCheckBiaogeByTime(Date time);


    @Query(value = "SELECT * FROM checkbiaoge GROUP BY `time`",nativeQuery = true)
    List<CheckBiaoge> findCheckBiaoge();

    @Modifying
    @Query(value = "delete from checkbiaoge  WHERE  DATE_FORMAT(`time`,'%Y-%m-%d') = DATE_FORMAT(?1,'%Y-%m-%d')",nativeQuery = true)
    void deleteCheckBiaogeByTime(Date time);

}
