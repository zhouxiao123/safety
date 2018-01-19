package com.safety.service;





import com.safety.entity.AdminUser;
import com.safety.entity.DailyLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 管理员管理服务类接口
 * Created by QuiteWing_YJ on 2016/12/13.
 */
 public interface DailyLogService {

 /**
  * 根据loginusername获取用户信息
  * @return
  */
 @Transactional(isolation= Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
 Page<DailyLog> findDailyLogList(Pageable p);



 /**
  * 保存或更新用户信息
  * @param
  * @return
  */
 @Transactional(isolation= Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
 DailyLog saveDailyLog(DailyLog dl);


}
