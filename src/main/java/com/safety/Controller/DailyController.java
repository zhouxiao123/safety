package com.safety.Controller;



import com.safety.entity.AdminUser;
import com.safety.entity.DailyLog;
import com.safety.service.AdminService;
import com.safety.service.DailyLogService;
import com.safety.util.FileUploadUtils;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * 基本控制器，控制后台页面跳转
 * Created by Administrator on 2016/12/21.
 */
@Controller
@RequestMapping(value = "daily")
public class DailyController {


    @Autowired
    private DailyLogService dailyLogService;

    /**
     * 登陆页面
     *
     * @return 登陆页面
     */
    @RequestMapping("/daily_list")
    public String DailyList(Model model) {
        Pageable p = new PageRequest(0,20);
        Page<DailyLog> dl = dailyLogService.findDailyLogList(p);
        model.addAttribute("logList",dl);
        return "back/daily_list";
    }

    /**
     * 登陆页面
     *
     * @return 登陆页面
     */
    @RequestMapping("/daily_add")
    public String DailyAdd(Model model) {

        return "back/daily_add";
    }

    @RequestMapping("/daily_save")
    public @ResponseBody String DailySave(Model model, @RequestParam("file") MultipartFile file) {

        String path = FileUploadUtils.uploadImg(file);
        DailyLog dl = new DailyLog();
        dl.setCreateTime(new Date());
        String filename = file.getOriginalFilename();
        dl.setLogName(filename.substring(0,filename.lastIndexOf(".")));
        dl.setLogPath(FileUploadUtils.moveFileToDir(path));
        dailyLogService.saveDailyLog(dl);
        return "ok";
    }

    /**
     * 登陆页面
     *
     * @return 登陆页面
     */
    @RequestMapping("/daily_add_biaoge")
    public String DailyAddBiaoge(Model model) {

        return "back/daily_add_biaoge";
    }

}
