package com.safety.service;




import com.safety.dao.CheckBiaogeDao;
import com.safety.dao.CheckGuzhangDao;
import com.safety.entity.CheckBiaoge;
import com.safety.entity.CheckGuzhang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 管理员服务类接口实现类
 * Created by QuiteWing_YJ on 2016/12/13.
 */
@Service
public class CheckGuzhangServiceImpl implements  CheckGuzhangService {

    @Autowired
    private CheckGuzhangDao checkGuzhangDao;


    @Override
    public CheckGuzhang saveCheckGuzhang(CheckGuzhang cb) {
        return checkGuzhangDao.save(cb);
    }

    @Override
    public CheckGuzhang findByCheckbiaogeid(Long checkbiaogeid) {
        return checkGuzhangDao.findByCheckbiaogeid(checkbiaogeid);
    }
}
