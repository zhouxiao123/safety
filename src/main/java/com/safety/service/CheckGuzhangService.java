package com.safety.service;





import com.safety.entity.CheckBiaoge;
import com.safety.entity.CheckGuzhang;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 管理员管理服务类接口
 * Created by QuiteWing_YJ on 2016/12/13.
 */
 public interface CheckGuzhangService {



 /**
  * 保存或更新用户信息
  * @param
  * @return
  */
 @Transactional(isolation= Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
 CheckGuzhang saveCheckGuzhang(CheckGuzhang cb);

 @Transactional(isolation= Isolation.READ_COMMITTED,propagation = Propagation.REQUIRED)
 CheckGuzhang findByCheckbiaogeid(Long checkbiaogeid);


}
