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
import java.util.List;
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

    @RequestMapping("/daily_list_biaoge_mobile")
    public String DailyListBiaogeMobile(Model model,@RequestParam (required = false) Integer add) {

        List<CheckBiaoge> cs = checkBiaogeService.findCheckBiaoge();
        model.addAttribute("cs",cs);
        if(add!=null && add==1){
            model.addAttribute("msg","保存成功!");
        }
        return "back/daily_list_biaoge_mobile";
    }


    /**
     * 登陆页面
     *
     * @return 登陆页面
     */
    @RequestMapping("/biaoge_save")
    public String biaogeSave(Model model,@RequestParam Integer[] selectAs1 ,
                             @RequestParam Integer[] selectAs2 ,
                             @RequestParam Integer[] selectAs3 ,
                             @RequestParam Integer[] selectAs4 ,
                             @RequestParam Integer[] selectAs5 ,
                             @RequestParam Integer[] selectAs6 ,
                             @RequestParam Integer[] selectAs7 ,
                             @RequestParam Integer[] selectAs8 ,
                             @RequestParam Integer[] selectAs9 ,
                             @RequestParam Integer[] selectAs10 ,
                             @RequestParam Integer[] selectAs11 ,
                             @RequestParam Integer[] selectAs12 ,
                             @RequestParam String checktime,
                             @RequestParam String checkuser,
                             @RequestParam Integer[] isWrite,
                             @RequestParam String[] jltime,
                             @RequestParam String[] linkphone,
                             @RequestParam String[] gzjl,
                             @RequestParam String[] gzpd,
                             @RequestParam String[] gzcl,
                             @RequestParam String[] bz) {

        checkBiaogeService.deleteCheckBiaogeByTime(DateFormatter.stringToDate(checktime,"yyyy-MM-dd"));
        //一
        CheckBiaoge cb = new CheckBiaoge();
        cb.setTag1(selectAs1[0]);
        cb.setTag2(selectAs1[1]);
        cb.setTag3(selectAs1[2]);
        cb.setTag4(selectAs1[3]);
        cb.setTag5(selectAs1[4]);
        cb.setTag6(selectAs1[5]);
        cb.setTag7(selectAs1[6]);
        cb.setTag8(selectAs1[7]);
        cb.setTag9(selectAs1[8]);
        cb.setTag10(selectAs1[9]);
        cb.setTag11(selectAs1[10]);
        cb.setName(getLocal(0));
        cb.setCheckuser(checkuser);

        cb.setTime(DateFormatter.stringToDate(checktime,"yyyy-MM-dd"));
        cb.setCreatetime(new Date());
        checkBiaogeService.saveCheckBiaoge(cb);

        if(isWrite[0]==1){
            CheckGuzhang cg = new CheckGuzhang();
            cg.setCreatetime(new Date());
            cg.setCheckuser(checkuser);
            cg.setBz(bz[0]);
            cg.setCheckbiaogeid(cb.getId());
            cg.setRecordtime(DateFormatter.stringToDate(jltime[0],"yyyy-MM-dd"));
            cg.setGzcl(gzcl[0]);
            cg.setGzjl(gzjl[0]);
            cg.setGzpd(gzpd[0]);
            cg.setLinkphone(linkphone[0]);
            cg.setLocal(cb.getName());
            checkGuzhangService.saveCheckGuzhang(cg);
        }
        //二
        //cb.setId(null);
        CheckBiaoge cb2 = new CheckBiaoge();
        cb2.setTag1(selectAs2[0]);
        cb2.setTag2(selectAs2[1]);
        cb2.setTag3(selectAs2[2]);
        cb2.setTag4(selectAs2[3]);
        cb2.setTag5(selectAs2[4]);
        cb2.setTag6(selectAs2[5]);
        cb2.setTag7(selectAs2[6]);
        cb2.setTag8(selectAs2[7]);
        cb2.setTag9(selectAs2[8]);
        cb2.setTag10(selectAs2[9]);
        cb2.setTag11(selectAs2[10]);
        cb2.setName(getLocal(1));
        cb2.setCheckuser(checkuser);

        cb2.setTime(DateFormatter.stringToDate(checktime,"yyyy-MM-dd"));
        cb2.setCreatetime(new Date());
        checkBiaogeService.saveCheckBiaoge(cb2);

        if(isWrite[1]==1){
            CheckGuzhang cg = new CheckGuzhang();
            cg.setCreatetime(new Date());
            cg.setCheckuser(checkuser);
            cg.setBz(bz[1]);
            cg.setCheckbiaogeid(cb2.getId());
            cg.setRecordtime(DateFormatter.stringToDate(jltime[1],"yyyy-MM-dd"));
            cg.setGzcl(gzcl[1]);
            cg.setGzjl(gzjl[1]);
            cg.setGzpd(gzpd[1]);
            cg.setLinkphone(linkphone[1]);
            cg.setLocal(cb2.getName());
            checkGuzhangService.saveCheckGuzhang(cg);
        }


        //三
        CheckBiaoge cb3 = new CheckBiaoge();
        cb3.setTag1(selectAs3[0]);
        cb3.setTag2(selectAs3[1]);
        cb3.setTag3(selectAs3[2]);
        cb3.setTag4(selectAs3[3]);
        cb3.setTag5(selectAs3[4]);
        cb3.setTag6(selectAs3[5]);
        cb3.setTag7(selectAs3[6]);
        cb3.setTag8(selectAs3[7]);
        cb3.setTag9(selectAs3[8]);
        cb3.setTag10(selectAs3[9]);
        cb3.setTag11(selectAs3[10]);
        cb3.setName(getLocal(2));
        cb3.setCheckuser(checkuser);

        cb3.setTime(DateFormatter.stringToDate(checktime,"yyyy-MM-dd"));
        cb3.setCreatetime(new Date());
        checkBiaogeService.saveCheckBiaoge(cb3);

        if(isWrite[2]==1){
            CheckGuzhang cg = new CheckGuzhang();
            cg.setCreatetime(new Date());
            cg.setCheckuser(checkuser);
            cg.setBz(bz[2]);
            cg.setCheckbiaogeid(cb3.getId());
            cg.setRecordtime(DateFormatter.stringToDate(jltime[2],"yyyy-MM-dd"));
            cg.setGzcl(gzcl[2]);
            cg.setGzjl(gzjl[2]);
            cg.setGzpd(gzpd[2]);
            cg.setLinkphone(linkphone[2]);
            cg.setLocal(cb3.getName());
            checkGuzhangService.saveCheckGuzhang(cg);
        }

        //四
        CheckBiaoge cb4 = new CheckBiaoge();
        cb4.setTag1(selectAs4[0]);
        cb4.setTag2(selectAs4[1]);
        cb4.setTag3(selectAs4[2]);
        cb4.setTag4(selectAs4[3]);
        cb4.setTag5(selectAs4[4]);
        cb4.setTag6(selectAs4[5]);
        cb4.setTag7(selectAs4[6]);
        cb4.setTag8(selectAs4[7]);
        cb4.setTag9(selectAs4[8]);
        cb4.setTag10(selectAs4[9]);
        cb4.setTag11(selectAs4[10]);
        cb4.setName(getLocal(3));
        cb4.setCheckuser(checkuser);

        cb4.setTime(DateFormatter.stringToDate(checktime,"yyyy-MM-dd"));
        cb4.setCreatetime(new Date());
        checkBiaogeService.saveCheckBiaoge(cb4);

        if(isWrite[3]==1){
            CheckGuzhang cg = new CheckGuzhang();
            cg.setCreatetime(new Date());
            cg.setCheckuser(checkuser);
            cg.setBz(bz[3]);
            cg.setCheckbiaogeid(cb4.getId());
            cg.setRecordtime(DateFormatter.stringToDate(jltime[3],"yyyy-MM-dd"));
            cg.setGzcl(gzcl[3]);
            cg.setGzjl(gzjl[3]);
            cg.setGzpd(gzpd[3]);
            cg.setLinkphone(linkphone[3]);
            cg.setLocal(cb4.getName());
            checkGuzhangService.saveCheckGuzhang(cg);
        }

        //五
        CheckBiaoge cb5 = new CheckBiaoge();
        cb5.setTag1(selectAs5[0]);
        cb5.setTag2(selectAs5[1]);
        cb5.setTag3(selectAs5[2]);
        cb5.setTag4(selectAs5[3]);
        cb5.setTag5(selectAs5[4]);
        cb5.setTag6(selectAs5[5]);
        cb5.setTag7(selectAs5[6]);
        cb5.setTag8(selectAs5[7]);
        cb5.setTag9(selectAs5[8]);
        cb5.setTag10(selectAs5[9]);
        cb5.setTag11(selectAs5[10]);
        cb5.setName(getLocal(4));
        cb5.setCheckuser(checkuser);

        cb5.setTime(DateFormatter.stringToDate(checktime,"yyyy-MM-dd"));
        cb5.setCreatetime(new Date());
        checkBiaogeService.saveCheckBiaoge(cb5);

        if(isWrite[4]==1){
            CheckGuzhang cg = new CheckGuzhang();
            cg.setCreatetime(new Date());
            cg.setCheckuser(checkuser);
            cg.setBz(bz[4]);
            cg.setCheckbiaogeid(cb5.getId());
            cg.setRecordtime(DateFormatter.stringToDate(jltime[4],"yyyy-MM-dd"));
            cg.setGzcl(gzcl[4]);
            cg.setGzjl(gzjl[4]);
            cg.setGzpd(gzpd[4]);
            cg.setLinkphone(linkphone[4]);
            cg.setLocal(cb5.getName());
            checkGuzhangService.saveCheckGuzhang(cg);
        }

        //六
        CheckBiaoge cb6 = new CheckBiaoge();
        cb6.setTag1(selectAs6[0]);
        cb6.setTag2(selectAs6[1]);
        cb6.setTag3(selectAs6[2]);
        cb6.setTag4(selectAs6[3]);
        cb6.setTag5(selectAs6[4]);
        cb6.setTag6(selectAs6[5]);
        cb6.setTag7(selectAs6[6]);
        cb6.setTag8(selectAs6[7]);
        cb6.setTag9(selectAs6[8]);
        cb6.setTag10(selectAs6[9]);
        cb6.setTag11(selectAs6[10]);
        cb6.setName(getLocal(5));
        cb6.setCheckuser(checkuser);

        cb6.setTime(DateFormatter.stringToDate(checktime,"yyyy-MM-dd"));
        cb6.setCreatetime(new Date());
        checkBiaogeService.saveCheckBiaoge(cb6);

        if(isWrite[5]==1){
            CheckGuzhang cg = new CheckGuzhang();
            cg.setCreatetime(new Date());
            cg.setCheckuser(checkuser);
            cg.setBz(bz[5]);
            cg.setCheckbiaogeid(cb6.getId());
            cg.setRecordtime(DateFormatter.stringToDate(jltime[5],"yyyy-MM-dd"));
            cg.setGzcl(gzcl[5]);
            cg.setGzjl(gzjl[5]);
            cg.setGzpd(gzpd[5]);
            cg.setLinkphone(linkphone[5]);
            cg.setLocal(cb6.getName());
            checkGuzhangService.saveCheckGuzhang(cg);
        }

        //柒
        CheckBiaoge cb7 = new CheckBiaoge();
        cb7.setTag1(selectAs7[0]);
        cb7.setTag2(selectAs7[1]);
        cb7.setTag3(selectAs7[2]);
        cb7.setTag4(selectAs7[3]);
        cb7.setTag5(selectAs7[4]);
        cb7.setTag6(selectAs7[5]);
        cb7.setTag7(selectAs7[6]);
        cb7.setTag8(selectAs7[7]);
        cb7.setTag9(selectAs7[8]);
        cb7.setTag10(selectAs7[9]);
        cb7.setTag11(selectAs7[10]);
        cb7.setName(getLocal(6));
        cb7.setCheckuser(checkuser);

        cb7.setTime(DateFormatter.stringToDate(checktime,"yyyy-MM-dd"));
        cb7.setCreatetime(new Date());
        checkBiaogeService.saveCheckBiaoge(cb7);

        if(isWrite[6]==1){
            CheckGuzhang cg = new CheckGuzhang();
            cg.setCreatetime(new Date());
            cg.setCheckuser(checkuser);
            cg.setBz(bz[6]);
            cg.setCheckbiaogeid(cb7.getId());
            cg.setRecordtime(DateFormatter.stringToDate(jltime[6],"yyyy-MM-dd"));
            cg.setGzcl(gzcl[6]);
            cg.setGzjl(gzjl[6]);
            cg.setGzpd(gzpd[6]);
            cg.setLinkphone(linkphone[6]);
            cg.setLocal(cb7.getName());
            checkGuzhangService.saveCheckGuzhang(cg);
        }

        //八
        CheckBiaoge cb8 = new CheckBiaoge();
        cb8.setTag1(selectAs8[0]);
        cb8.setTag2(selectAs8[1]);
        cb8.setTag3(selectAs8[2]);
        cb8.setTag4(selectAs8[3]);
        cb8.setTag5(selectAs8[4]);
        cb8.setTag6(selectAs8[5]);
        cb8.setTag7(selectAs8[6]);
        cb8.setTag8(selectAs8[7]);
        cb8.setTag9(selectAs8[8]);
        cb8.setTag10(selectAs8[9]);
        cb8.setTag11(selectAs8[10]);
        cb8.setName(getLocal(7));
        cb8.setCheckuser(checkuser);

        cb8.setTime(DateFormatter.stringToDate(checktime,"yyyy-MM-dd"));
        cb8.setCreatetime(new Date());
        checkBiaogeService.saveCheckBiaoge(cb8);

        if(isWrite[7]==1){
            CheckGuzhang cg = new CheckGuzhang();
            cg.setCreatetime(new Date());
            cg.setCheckuser(checkuser);
            cg.setBz(bz[7]);
            cg.setCheckbiaogeid(cb8.getId());
            cg.setRecordtime(DateFormatter.stringToDate(jltime[7],"yyyy-MM-dd"));
            cg.setGzcl(gzcl[7]);
            cg.setGzjl(gzjl[7]);
            cg.setGzpd(gzpd[7]);
            cg.setLinkphone(linkphone[7]);
            cg.setLocal(cb8.getName());
            checkGuzhangService.saveCheckGuzhang(cg);
        }

        //九
        CheckBiaoge cb9 = new CheckBiaoge();
        cb9.setTag1(selectAs9[0]);
        cb9.setTag2(selectAs9[1]);
        cb9.setTag3(selectAs9[2]);
        cb9.setTag4(selectAs9[3]);
        cb9.setTag5(selectAs9[4]);
        cb9.setTag6(selectAs9[5]);
        cb9.setTag7(selectAs9[6]);
        cb9.setTag8(selectAs9[7]);
        cb9.setTag9(selectAs9[8]);
        cb9.setTag10(selectAs9[9]);
        cb9.setTag11(selectAs9[10]);
        cb9.setName(getLocal(8));
        cb9.setCheckuser(checkuser);

        cb9.setTime(DateFormatter.stringToDate(checktime,"yyyy-MM-dd"));
        cb9.setCreatetime(new Date());
        checkBiaogeService.saveCheckBiaoge(cb9);

        if(isWrite[8]==1){
            CheckGuzhang cg = new CheckGuzhang();
            cg.setCreatetime(new Date());
            cg.setCheckuser(checkuser);
            cg.setBz(bz[8]);
            cg.setCheckbiaogeid(cb9.getId());
            cg.setRecordtime(DateFormatter.stringToDate(jltime[8],"yyyy-MM-dd"));
            cg.setGzcl(gzcl[8]);
            cg.setGzjl(gzjl[8]);
            cg.setGzpd(gzpd[8]);
            cg.setLinkphone(linkphone[8]);
            cg.setLocal(cb9.getName());
            checkGuzhangService.saveCheckGuzhang(cg);
        }

        //十
        CheckBiaoge cb10 = new CheckBiaoge();
        cb10.setTag1(selectAs10[0]);
        cb10.setTag2(selectAs10[1]);
        cb10.setTag3(selectAs10[2]);
        cb10.setTag4(selectAs10[3]);
        cb10.setTag5(selectAs10[4]);
        cb10.setTag6(selectAs10[5]);
        cb10.setTag7(selectAs10[6]);
        cb10.setTag8(selectAs10[7]);
        cb10.setTag9(selectAs10[8]);
        cb10.setTag10(selectAs10[9]);
        cb10.setTag11(selectAs10[10]);
        cb10.setName(getLocal(9));
        cb10.setCheckuser(checkuser);

        cb10.setTime(DateFormatter.stringToDate(checktime,"yyyy-MM-dd"));
        cb10.setCreatetime(new Date());
        checkBiaogeService.saveCheckBiaoge(cb10);

        if(isWrite[9]==1){
            CheckGuzhang cg = new CheckGuzhang();
            cg.setCreatetime(new Date());
            cg.setCheckuser(checkuser);
            cg.setBz(bz[9]);
            cg.setCheckbiaogeid(cb10.getId());
            cg.setRecordtime(DateFormatter.stringToDate(jltime[9],"yyyy-MM-dd"));
            cg.setGzcl(gzcl[9]);
            cg.setGzjl(gzjl[9]);
            cg.setGzpd(gzpd[9]);
            cg.setLinkphone(linkphone[9]);
            cg.setLocal(cb10.getName());
            checkGuzhangService.saveCheckGuzhang(cg);
        }

//十一
        CheckBiaoge cb11 = new CheckBiaoge();
        cb11.setTag1(selectAs11[0]);
        cb11.setTag2(selectAs11[1]);
        cb11.setTag3(selectAs11[2]);
        cb11.setTag4(selectAs11[3]);
        cb11.setTag5(selectAs11[4]);
        cb11.setTag6(selectAs11[5]);
        cb11.setTag7(selectAs11[6]);
        cb11.setTag8(selectAs11[7]);
        cb11.setTag9(selectAs11[8]);
        cb11.setTag10(selectAs11[9]);
        cb11.setTag11(selectAs11[10]);
        cb11.setName(getLocal(10));
        cb11.setCheckuser(checkuser);

        cb11.setTime(DateFormatter.stringToDate(checktime,"yyyy-MM-dd"));
        cb11.setCreatetime(new Date());
        checkBiaogeService.saveCheckBiaoge(cb11);

        if(isWrite[10]==1){
            CheckGuzhang cg = new CheckGuzhang();
            cg.setCreatetime(new Date());
            cg.setCheckuser(checkuser);
            cg.setBz(bz[10]);
            cg.setCheckbiaogeid(cb11.getId());
            cg.setRecordtime(DateFormatter.stringToDate(jltime[10],"yyyy-MM-dd"));
            cg.setGzcl(gzcl[10]);
            cg.setGzjl(gzjl[10]);
            cg.setGzpd(gzpd[10]);
            cg.setLinkphone(linkphone[10]);
            cg.setLocal(cb11.getName());
            checkGuzhangService.saveCheckGuzhang(cg);
        }

        //十二
        CheckBiaoge cb12 = new CheckBiaoge();
        cb12.setTag1(selectAs12[0]);
        cb12.setTag2(selectAs12[1]);
        cb12.setTag3(selectAs12[2]);
        cb12.setTag4(selectAs12[3]);
        cb12.setTag5(selectAs12[4]);
        cb12.setTag6(selectAs12[5]);
        cb12.setTag7(selectAs12[6]);
        cb12.setTag8(selectAs12[7]);
        cb12.setTag9(selectAs12[8]);
        cb12.setTag10(selectAs12[9]);
        cb12.setTag11(selectAs12[10]);
        cb12.setName(getLocal(11));
        cb12.setCheckuser(checkuser);

        cb12.setTime(DateFormatter.stringToDate(checktime,"yyyy-MM-dd"));
        cb12.setCreatetime(new Date());
        checkBiaogeService.saveCheckBiaoge(cb12);

        if(isWrite[11]==1){
            CheckGuzhang cg = new CheckGuzhang();
            cg.setCreatetime(new Date());
            cg.setCheckuser(checkuser);
            cg.setBz(bz[11]);
            cg.setCheckbiaogeid(cb12.getId());
            cg.setRecordtime(DateFormatter.stringToDate(jltime[11],"yyyy-MM-dd"));
            cg.setGzcl(gzcl[11]);
            cg.setGzjl(gzjl[11]);
            cg.setGzpd(gzpd[11]);
            cg.setLinkphone(linkphone[11]);
            cg.setLocal(cb12.getName());
            checkGuzhangService.saveCheckGuzhang(cg);
        }


        return "redirect:daily_list_biaoge_mobile?add=1";
    }

    private static String getLocal(Integer i){
        //Integer tag = i/11;
        switch (i){
            case 0:return "内江";
            case 1:return "自贡";
            case 2:return "宜宾";
            case 3:return "泸州";
            case 4:return "资阳";
            case 5:return "德阳";
            case 6:return "绵阳";
            case 7:return "广元";
            case 8:return "南充";
            case 9:return "雅安";
            case 10:return "攀枝花";
            default:return "四川招考网";

        }
    }

}
