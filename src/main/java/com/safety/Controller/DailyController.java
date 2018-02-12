package com.safety.Controller;



import com.safety.entity.AdminUser;
import com.safety.entity.CheckBiaoge;
import com.safety.entity.CheckGuzhang;
import com.safety.entity.DailyLog;
import com.safety.service.AdminService;
import com.safety.service.CheckBiaogeService;
import com.safety.service.CheckGuzhangService;
import com.safety.service.DailyLogService;
import com.safety.util.DateFormatter;
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
import java.util.Map;

/**
 * 基本控制器，控制后台页面跳转
 * Created by Administrator on 2016/12/21.
 */
@Controller
@RequestMapping(value = "daily")
public class DailyController {


    @Autowired
    private DailyLogService dailyLogService;


    @Autowired
    private CheckGuzhangService checkGuzhangService;

    @Autowired
    private CheckBiaogeService checkBiaogeService;

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

    /**
     * 登陆页面
     *
     * @return 登陆页面
     */
    @RequestMapping("/daily_add_biaoge_mobile")
    public String DailyAddBiaogeMobile(Model model) {

        return "back/daily_add_biaoge_mobile";
    }


    /**
     * 登陆页面
     *
     * @return 登陆页面
     */
    @RequestMapping("/biaoge_save")
    public String biaogeSave(Model model,@RequestParam Integer[] selectAs ,
                             @RequestParam String checktime,
                             @RequestParam String checkuser,
                             @RequestParam Integer[] isWrite,
                             @RequestParam String[] jltime,
                             @RequestParam String[] linkphone,
                             @RequestParam String[] gzjl,
                             @RequestParam String[] gzpd,
                             @RequestParam String[] gzcl,
                             @RequestParam String[] bz) {
        CheckBiaoge cb = new CheckBiaoge();
        for(int i=0; i < selectAs.length;i++){
            if(i%11==0) {
                cb.setTag1(selectAs[i]);
            } else if(i%11==1) {
                cb.setTag2(selectAs[i]);
            } else if(i%11==2) {
                cb.setTag3(selectAs[i]);
            } else if(i%11==3) {
                cb.setTag4(selectAs[i]);
            } else if(i%11==4) {
                cb.setTag5(selectAs[i]);
            } else if(i%11==5) {
                cb.setTag6(selectAs[i]);
            } else if(i%11==6) {
                cb.setTag7(selectAs[i]);
            } else if(i%11==7) {
                cb.setTag8(selectAs[i]);
            } else if(i%11==8) {
                cb.setTag9(selectAs[i]);
            } else if(i%11==9) {
                cb.setTag10(selectAs[i]);
            } else if(i%11==10) {
                cb.setTag11(selectAs[i]);
                cb.setName(getLocal(i));
                cb.setCheckuser(checkuser);

                cb.setTime(DateFormatter.stringToDate(checktime,"yyyy-MM-dd HH:mm:ss"));
                cb.setCreatetime(new Date());
                checkBiaogeService.saveCheckBiaoge(cb);
                Integer t = i/11;
                if(isWrite[t]==1){
                    CheckGuzhang cg = new CheckGuzhang();
                    cg.setCreatetime(new Date());
                    cg.setCheckuser(checkuser);
                    cg.setBz(bz[t]);
                    cg.setCheckbiaogeid(cb.getId());
                    cg.setRecordtime(DateFormatter.stringToDate(jltime[t],"yyyy-MM-dd HH:mm:ss"));
                    cg.setGzcl(gzcl[t]);
                    cg.setGzjl(gzjl[t]);
                    cg.setGzpd(gzpd[t]);
                    cg.setLinkphone(linkphone[t]);
                    cg.setLocal(cb.getName());
                    checkGuzhangService.saveCheckGuzhang(cg);
                }
            }


        }
        return "back/daily_add_biaoge_mobile";
    }

    private static String getLocal(Integer i){
        Integer tag = i/11;
        switch (tag){
            case 0:return "内江";
            case 1:return "自贡";
            case 2:return "宜宾";
            case 3:return "泸州";
            case 4:return "资阳";
            case 5:return "德阳";
            case 6:return "广元";
            case 7:return "南充";
            case 8:return "雅安";
            case 9:return "攀枝花";
            default:return "四川招考网";

        }
    }

}
