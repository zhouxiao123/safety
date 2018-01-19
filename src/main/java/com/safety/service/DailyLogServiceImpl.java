package com.safety.service;




import com.safety.dao.AdminDao;
import com.safety.dao.DailyLogDao;
import com.safety.entity.AdminUser;
import com.safety.entity.DailyLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * 管理员服务类接口实现类
 * Created by QuiteWing_YJ on 2016/12/13.
 */
@Service
public class DailyLogServiceImpl implements  DailyLogService {

    @Autowired
    private DailyLogDao dailyLogDao;


    @Override
    public Page<DailyLog> findDailyLogList(Pageable p) {
        return dailyLogDao.findAll(p);
    }

    @Override
    public DailyLog saveDailyLog(DailyLog dl) {
        return dailyLogDao.save(dl);
    }
}
