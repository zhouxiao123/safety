package com.safety.service;




import com.safety.dao.CheckBiaogeDao;
import com.safety.dao.DailyLogDao;
import com.safety.entity.CheckBiaoge;
import com.safety.entity.DailyLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 管理员服务类接口实现类
 * Created by QuiteWing_YJ on 2016/12/13.
 */
@Service
public class CheckBiaogeServiceImpl implements  CheckBiaogeService {

    @Autowired
    private CheckBiaogeDao checkBiaogeDao;


    @Override
    public CheckBiaoge saveCheckBiaoge(CheckBiaoge cb) {
        return checkBiaogeDao.save(cb);
    }

    @Override
    public void deleteCheckBiaogeByTime(Date time) {
        checkBiaogeDao.deleteCheckBiaogeByTime(time);
    }
}
