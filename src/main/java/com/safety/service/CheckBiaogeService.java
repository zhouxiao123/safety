package com.safety.service;





import com.safety.entity.CheckBiaoge;
import com.safety.entity.DailyLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 管理员管理服务类接口
 * Created by QuiteWing_YJ on 2016/12/13.
 */
 public interface CheckBiaogeService {



 /**
  * 保存或更新用户信息
  * @param
  * @return
  */
 @Transactional(isolation= Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
 CheckBiaoge saveCheckBiaoge(CheckBiaoge cb);

 @Transactional(isolation= Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
 List<CheckBiaoge> findCheckBiaoge();

 @Transactional(isolation= Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
 void deleteCheckBiaogeByTime(Date time);

 @Transactional(isolation= Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
 List<CheckBiaoge> findCheckBiaogeByNameAndStartAndEndTime(String name,String start,String end);


}
