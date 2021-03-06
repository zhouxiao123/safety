package com.safety.Controller;




import com.safety.entity.CheckBiaoge;
import com.safety.entity.CheckGuzhang;
import com.safety.entity.DailyLog;
import com.safety.service.CheckBiaogeService;
import com.safety.service.CheckGuzhangService;
import com.safety.service.DailyLogService;
import com.safety.util.DateFormatter;
import com.safety.util.FileUploadUtils;
import com.safety.util.FileUtils;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
        cb.setIswrite(isWrite[0]);
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
        cb2.setIswrite(isWrite[1]);
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
        cb3.setIswrite(isWrite[2]);
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
        cb4.setIswrite(isWrite[3]);
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
        cb5.setIswrite(isWrite[4]);
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
        cb6.setIswrite(isWrite[5]);
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
        cb7.setIswrite(isWrite[6]);
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
        cb8.setIswrite(isWrite[7]);
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
        cb9.setIswrite(isWrite[8]);
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
        cb10.setIswrite(isWrite[9]);
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
        cb11.setIswrite(isWrite[10]);
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
        cb12.setIswrite(isWrite[11]);
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

    /**
     * 导出excel
     */
    @RequestMapping("/export_excel")
    public void exportExcel(HttpServletResponse response,@RequestParam String name,@RequestParam String start,@RequestParam String end){
        Map<String, Object> param = new HashMap<String, Object>();
        List<CheckBiaoge> checkBiaoges = checkBiaogeService.findCheckBiaogeByNameAndStartAndEndTime("%"+name+"%",start,end);

        Integer len = checkBiaoges.size();
        /*if(checkBiaoges.size()==0){

        }*/
        String sep = System.getProperty("file.separator");
        String fileDir = FileUploadUtils.tempPath;// 存放文件文件夹名称
        String path=fileDir;
        String excelPath = path + sep + "excel";
        File dirPath = new File(excelPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
        try{

            XSSFWorkbook work = new XSSFWorkbook ();

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            FileOutputStream fileOut = new FileOutputStream(excelPath + sep
                    + "excel" + ".xlsx");

			/*fm.getPl_name() + "_" + fm.getPl_section_name()
			+ df.format(fm.getCreate_date())*/

            XSSFSheet sheet1 = work.createSheet("sheet1");

            sheet1.addMergedRegion(new CellRangeAddress(0,0,0,len+1));
            //sheet1.addMergedRegion(new CellRangeAddress(2,len+2,13,len+2));
            if(len!=0) {
                sheet1.addMergedRegion(new CellRangeAddress(1, 1, 2, len + 1));
            }
            sheet1.addMergedRegion(new CellRangeAddress(3,7,0,0));
            sheet1.addMergedRegion(new CellRangeAddress(8,10,0,0));
            sheet1.addMergedRegion(new CellRangeAddress(11,12,0,0));
            sheet1.addMergedRegion(new CellRangeAddress(14,14,0,1));

            XSSFCell cell;

            //设置列宽度
            sheet1.setColumnWidth(0, 10*256);
            sheet1.setColumnWidth(1, 20*256);
            sheet1.setColumnWidth(2, 20*256);
            sheet1.setColumnWidth(3, 20*256);
            sheet1.setColumnWidth(4, 20*256);
            sheet1.setColumnWidth(5, 20*256);
            sheet1.setColumnWidth(6, 20*256);
            sheet1.setColumnWidth(7, 20*256);


            // 标题格式
            CellStyle cellStyle = work.createCellStyle();
            // 表头格式
            CellStyle titleStyle = work.createCellStyle();
            // 内容格式
            CellStyle dataStyle = work.createCellStyle();

            //居中
            cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
            cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            cellStyle.setBorderRight(CellStyle.BORDER_THIN);
            cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
            cellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle.setBorderTop(CellStyle.BORDER_THIN);
            cellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
            cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
            cellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

            titleStyle.setAlignment(CellStyle.ALIGN_CENTER);
            titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            titleStyle.setWrapText(true);
            titleStyle.setBorderRight(CellStyle.BORDER_THIN);
            titleStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
            titleStyle.setBorderLeft(CellStyle.BORDER_THIN);
            titleStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            titleStyle.setBorderTop(CellStyle.BORDER_THIN);
            titleStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
            titleStyle.setBorderBottom(CellStyle.BORDER_THIN);
            titleStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

            dataStyle.setAlignment(CellStyle.ALIGN_CENTER);
            dataStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            dataStyle.setWrapText(true);
            dataStyle.setAlignment(CellStyle.ALIGN_CENTER);
            dataStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            dataStyle.setWrapText(true);
            dataStyle.setBorderRight(CellStyle.BORDER_THIN);
            dataStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
            dataStyle.setBorderLeft(CellStyle.BORDER_THIN);
            dataStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
            dataStyle.setBorderTop(CellStyle.BORDER_THIN);
            dataStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
            dataStyle.setBorderBottom(CellStyle.BORDER_THIN);
            dataStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());

            // 标题字体
            Font font = work.createFont();
            // 表头字体
            Font titlefont = work.createFont();
            // 内容字体
            Font datafont = work.createFont();

            font.setFontHeightInPoints((short) 24);
            font.setFontName("方正仿宋简体");
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);

            titlefont.setFontHeightInPoints((short) 16);
            titlefont.setFontName("方正仿宋简体");
            titlefont.setBoldweight(Font.BOLDWEIGHT_BOLD);
            titlefont.setColor(IndexedColors.RED.getIndex());


            datafont.setFontHeightInPoints((short) 16);
            datafont.setFontName("方正仿宋简体");
            datafont.setBoldweight(Font.BOLDWEIGHT_BOLD);
            //datafont.setColor(HSSFColor.RED.index);

            //把字体加入到格式中
            cellStyle.setFont(font);
            titleStyle.setFont(titlefont);
            dataStyle.setFont(datafont);

            //int row_index = 0;
            XSSFRow row;
            /*row_index++;
            for(int i = 0; i < 14; i++) {
                cell = row.createCell(i);
                cell.setCellStyle(dataStyle);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
            }*/
            for(int i = 0; i < 15; i++) {
                row = sheet1.createRow(i);
            }
            /**
             * 第一行
             */
            row = sheet1.getRow(0);
            cell = row.createCell(0);
            cell.setCellStyle(cellStyle);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue("招考网信息系统安全检查日志-四川昭信教育安全运维组-导出时间"+df.format(new Date()));

            /**
             * 第二行
             */
            row = sheet1.getRow(1);
            //第一列
            cell = row.createCell(0);
            cell.setCellStyle(dataStyle);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue("地区");
            //第二列
            cell = row.createCell(1);
            cell.setCellStyle(titleStyle);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue(name);
            //第三列
            cell = row.createCell(2);
            cell.setCellStyle(dataStyle);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue("检查情况（正常打“√”，异常打“╳”）");


            /**
             *第三行
             **/
            row = sheet1.getRow(2);
            //第一列
            cell = row.createCell(0);
            cell.setCellStyle(dataStyle);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue("名称");
            //第二列
            cell = row.createCell(1);
            cell.setCellStyle(dataStyle);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue("检查项目");


            /**
             *第四行
             **/
            row = sheet1.getRow(3);
            //第一列
            cell = row.createCell(0);
            cell.setCellStyle(dataStyle);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue("网页及内容安全检查");
            //第二列
            cell = row.createCell(1);
            cell.setCellStyle(dataStyle);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue("网页访问是否正常");

            /**
             *第五行
             **/
            row = sheet1.getRow(4);

            //第二列
            cell = row.createCell(1);
            cell.setCellStyle(dataStyle);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue("网站链接是否正常");

            /**
             *第六行
             **/
            row = sheet1.getRow(5);

            //第二列
            cell = row.createCell(1);
            cell.setCellStyle(dataStyle);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue("安全漏洞检查");

            /**
             *第七行
             **/
            row = sheet1.getRow(6);

            //第二列
            cell = row.createCell(1);
            cell.setCellStyle(dataStyle);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue("内容信息检查(抽样)");

            /**
             *第八行
             **/
            row = sheet1.getRow(7);

            //第二列
            cell = row.createCell(1);
            cell.setCellStyle(dataStyle);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue("网页篡改、植入恶意代码检查(抽样)");



            /**
             *第九行
             **/
            row = sheet1.getRow(8);
            //第一列
            cell = row.createCell(0);
            cell.setCellStyle(dataStyle);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue("运行安全");
            //第二列
            cell = row.createCell(1);
            cell.setCellStyle(dataStyle);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue("相关网络设备的运行检查");

            /**
             *第十行
             **/
            row = sheet1.getRow(9);

            //第二列
            cell = row.createCell(1);
            cell.setCellStyle(dataStyle);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue("病毒检查");

            /**
             *第十一行
             **/
            row = sheet1.getRow(10);

            //第二列
            cell = row.createCell(1);
            cell.setCellStyle(dataStyle);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue("是否定时巡查");


            /**
             *第十二行
             **/
            row = sheet1.getRow(11);
            //第一列
            cell = row.createCell(0);
            cell.setCellStyle(dataStyle);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue("数据库安全");
            //第二列
            cell = row.createCell(1);
            cell.setCellStyle(dataStyle);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue("数据库检查（被攻击、盗取、泄露）");

            /**
             *第十三行
             **/
            row = sheet1.getRow(12);

            //第二列
            cell = row.createCell(1);
            cell.setCellStyle(dataStyle);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue("备份与恢复机制检查");

            /**
             *第十四行
             **/
            row = sheet1.getRow(13);
            //第一列
            cell = row.createCell(0);
            cell.setCellStyle(dataStyle);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue("信息安全");
            //第二列
            cell = row.createCell(1);
            cell.setCellStyle(dataStyle);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue("后台账户和口令检查");

            /**
             *第十五行
             **/
            row = sheet1.getRow(14);
            //第一列
            cell = row.createCell(0);
            cell.setCellStyle(dataStyle);
            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
            cell.setCellValue("检查人");
            //第二列
            cell = row.createCell(1);
            cell.setCellStyle(dataStyle);




            Integer j = 0;
            for(CheckBiaoge cb : checkBiaoges){

                    //第二行
                    row = sheet1.getRow(1);
                    cell = row.createCell(2+j);
                    cell.setCellStyle(dataStyle);
                    cell.setCellValue("检查情况（正常打“√”，异常打“╳”）");

                    //第三行
                    row = sheet1.getRow(2);
                    cell = row.createCell(2+j);
                    cell.setCellStyle(dataStyle);
                    cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(df.format(cb.getTime()));

                    //第四行
                    row = sheet1.getRow(3);
                    cell = row.createCell(2+j);
                    cell.setCellStyle(dataStyle);
                    cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(cb.getTag1()==1?"√":cb.getTag1()==2?"×":"无权限");
                    //第五行
                    row = sheet1.getRow(4);
                    cell = row.createCell(2+j);
                    cell.setCellStyle(dataStyle);
                    cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(cb.getTag2()==1?"√":cb.getTag2()==2?"×":"无权限");
                    //第六行
                    row = sheet1.getRow(5);
                    cell = row.createCell(2+j);
                    cell.setCellStyle(dataStyle);
                    cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(cb.getTag3()==1?"√":cb.getTag3()==2?"×":"无权限");
                    //第七行
                    row = sheet1.getRow(6);
                    cell = row.createCell(2+j);
                    cell.setCellStyle(dataStyle);
                    cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(cb.getTag4()==1?"√":cb.getTag4()==2?"×":"无权限");
                    //第八行
                    row = sheet1.getRow(7);
                    cell = row.createCell(2+j);
                    cell.setCellStyle(dataStyle);
                    cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(cb.getTag5()==1?"√":cb.getTag5()==2?"×":"无权限");
                    //第九行
                    row = sheet1.getRow(8);
                    cell = row.createCell(2+j);
                    cell.setCellStyle(dataStyle);
                    cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(cb.getTag6()==1?"√":cb.getTag6()==2?"×":"无权限");
                    //第十行
                    row = sheet1.getRow(9);
                    cell = row.createCell(2+j);
                    cell.setCellStyle(dataStyle);
                    cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(cb.getTag7()==1?"√":cb.getTag7()==2?"×":"无权限");
                    //第十一行
                    row = sheet1.getRow(10);
                    cell = row.createCell(2+j);
                    cell.setCellStyle(dataStyle);
                    cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(cb.getTag8()==1?"√":cb.getTag8()==2?"×":"无权限");
                    //第十二行
                    row = sheet1.getRow(11);
                    cell = row.createCell(2+j);
                    cell.setCellStyle(dataStyle);
                    cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(cb.getTag9()==1?"√":cb.getTag9()==2?"×":"无权限");
                    //第十三行
                    row = sheet1.getRow(12);
                    cell = row.createCell(2+j);
                    cell.setCellStyle(dataStyle);
                    cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(cb.getTag10()==1?"√":cb.getTag10()==2?"×":"无权限");
                    //第十四行
                    row = sheet1.getRow(13);
                    cell = row.createCell(2+j);
                    cell.setCellStyle(dataStyle);
                    cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(cb.getTag11()==1?"√":cb.getTag11()==2?"×":"无权限");
                    //第十五行
                    row = sheet1.getRow(14);
                    cell = row.createCell(2+j);
                    cell.setCellStyle(dataStyle);
                    cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(cb.getCheckuser());
                    j++;

                    if(cb.getIswrite()==1){
                        CheckGuzhang cg = checkGuzhangService.findByCheckbiaogeid(cb.getId());

                        XSSFWorkbook work2 = new XSSFWorkbook ();

                        FileOutputStream fileOut2 = new FileOutputStream(excelPath + sep
                                + DateFormatter.dateToString(cb.getTime(), "yyyy-MM-dd") + name + "故障单.xlsx");


                        XSSFSheet sheet2 = work2.createSheet("sheet1");

                        sheet2.addMergedRegion(new CellRangeAddress(2,3,0,0));
                        //sheet1.addMergedRegion(new CellRangeAddress(2,len+2,13,len+2));

                        sheet2.addMergedRegion(new CellRangeAddress(2, 3, 1, 3));
                        sheet2.addMergedRegion(new CellRangeAddress(4,4,1,2));
                        sheet2.addMergedRegion(new CellRangeAddress(4,7,0,0));
                        sheet2.addMergedRegion(new CellRangeAddress(5,7,1,2));

                        XSSFCell cell2;

                        //设置列宽度

                        sheet2.setColumnWidth(0,30*256);
                        sheet2.setColumnWidth(1,30*256);
                        sheet2.setColumnWidth(2,30*256);
                        sheet2.setColumnWidth(3,30*256);
                        sheet2.setDefaultRowHeight((short)(50*20));


                        // 表头格式
                        CellStyle titleStyle2 = work2.createCellStyle();
                        // 内容格式
                        CellStyle dataStyle2 = work2.createCellStyle();


                        titleStyle2.setAlignment(CellStyle.ALIGN_CENTER);
                        titleStyle2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
                        titleStyle2.setWrapText(true);
                        titleStyle2.setBorderRight(CellStyle.BORDER_THIN);
                        titleStyle2.setRightBorderColor(IndexedColors.BLACK.getIndex());
                        titleStyle2.setBorderLeft(CellStyle.BORDER_THIN);
                        titleStyle2.setLeftBorderColor(IndexedColors.BLACK.getIndex());
                        titleStyle2.setBorderTop(CellStyle.BORDER_THIN);
                        titleStyle2.setTopBorderColor(IndexedColors.BLACK.getIndex());
                        titleStyle2.setBorderBottom(CellStyle.BORDER_THIN);
                        titleStyle2.setBottomBorderColor(IndexedColors.BLACK.getIndex());

                        dataStyle2.setAlignment(CellStyle.ALIGN_CENTER);
                        dataStyle2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
                        dataStyle2.setWrapText(true);
                        dataStyle2.setAlignment(CellStyle.ALIGN_CENTER);
                        dataStyle2.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
                        dataStyle2.setWrapText(true);
                        dataStyle2.setBorderRight(CellStyle.BORDER_THIN);
                        dataStyle2.setRightBorderColor(IndexedColors.BLACK.getIndex());
                        dataStyle2.setBorderLeft(CellStyle.BORDER_THIN);
                        dataStyle2.setLeftBorderColor(IndexedColors.BLACK.getIndex());
                        dataStyle2.setBorderTop(CellStyle.BORDER_THIN);
                        dataStyle2.setTopBorderColor(IndexedColors.BLACK.getIndex());
                        dataStyle2.setBorderBottom(CellStyle.BORDER_THIN);
                        dataStyle2.setBottomBorderColor(IndexedColors.BLACK.getIndex());

                        // 表头字体
                        Font titlefont2 = work2.createFont();
                        // 内容字体
                        Font datafont2 = work2.createFont();



                        titlefont2.setFontHeightInPoints((short) 16);
                        titlefont2.setFontName("方正仿宋简体");
                        titlefont2.setBoldweight(Font.BOLDWEIGHT_BOLD);
                        titlefont2.setColor(IndexedColors.RED.getIndex());


                        datafont2.setFontHeightInPoints((short) 16);
                        datafont2.setFontName("方正仿宋简体");
                        datafont2.setBoldweight(Font.BOLDWEIGHT_BOLD);
                        //datafont.setColor(HSSFColor.RED.index);

                        //把字体加入到格式中

                        titleStyle2.setFont(titlefont2);
                        dataStyle2.setFont(datafont2);




                        XSSFRow row2;

                        for(int i = 0; i < 8; i++) {
                            row2 = sheet2.createRow(i);
                        }
                        row2 = sheet2.getRow(0);
                        //第一行
                        //第一列
                        cell2 = row2.createCell(0);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell2.setCellValue("地区");
                        //第二列
                        cell2 = row2.createCell(1);
                        cell2.setCellStyle(titleStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell2.setCellValue(cg.getLocal());

                        //第三列
                        cell2 = row2.createCell(2);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell2.setCellValue("记录时间");

                        //第四列
                        cell2 = row2.createCell(3);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell2.setCellValue(DateFormatter.dateToString(cg.getRecordtime(), "yyyy-MM-dd"));

                        //第二行
                        row2 = sheet2.getRow(1);

                        //第一列
                        cell2 = row2.createCell(0);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell2.setCellValue("检查人");
                        //第二列
                        cell2 = row2.createCell(1);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell2.setCellValue(cg.getCheckuser());

                        //第三列
                        cell2 = row2.createCell(2);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell2.setCellValue("联系电话");

                        //第四列
                        cell2 = row2.createCell(3);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell2.setCellValue(cg.getLinkphone());

                        //第三行
                        row2 = sheet2.getRow(2);
                        //第一列
                        cell2 = row2.createCell(0);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell2.setCellValue("故障记录");

                        //第二列
                        cell2 = row2.createCell(1);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell2.setCellValue(cg.getGzjl());

                        //第三列
                        cell2 = row2.createCell(2);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);

                        //第四列
                        cell2 = row2.createCell(3);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);

                        //第四行
                        row2 = sheet2.getRow(3);
                        //第一列
                        cell2 = row2.createCell(0);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);


                        //第二列
                        cell2 = row2.createCell(1);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);

                        //第三列
                        cell2 = row2.createCell(2);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);

                        //第四列
                        cell2 = row2.createCell(3);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);


                        //第五行
                        row2 = sheet2.getRow(4);
                        //第一列
                        cell2 = row2.createCell(0);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell2.setCellValue("故障处理");

                        //第二列
                        cell2 = row2.createCell(1);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell2.setCellValue("故障判断");

                        //第三列
                        cell2 = row2.createCell(2);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);


                        //第四列
                        cell2 = row2.createCell(3);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell2.setCellValue("故障处理");

                        //第六行
                        row2 = sheet2.getRow(5);
                        //第一列
                        cell2 = row2.createCell(0);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);


                        //第二列
                        cell2 = row2.createCell(1);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell2.setCellValue(cg.getGzpd());

                        //第三列
                        cell2 = row2.createCell(2);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);


                        //第四列
                        cell2 = row2.createCell(3);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell2.setCellValue(cg.getGzcl());

                        //第七行
                        row2 = sheet2.getRow(6);
                        //第一列
                        cell2 = row2.createCell(0);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);


                        //第二列
                        cell2 = row2.createCell(1);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);

                        //第三列
                        cell2 = row2.createCell(2);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);


                        //第四列
                        cell2 = row2.createCell(3);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell2.setCellValue("备注");


                        //第八行
                        row2 = sheet2.getRow(7);
                        //第一列
                        cell2 = row2.createCell(0);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);


                        //第二列
                        cell2 = row2.createCell(1);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);

                        //第三列
                        cell2 = row2.createCell(2);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);


                        //第四列
                        cell2 = row2.createCell(3);
                        cell2.setCellStyle(dataStyle2);
                        cell2.setCellType(XSSFCell.CELL_TYPE_STRING);
                        cell2.setCellValue(cg.getBz());



                        work2.write(fileOut2);
                        fileOut2.close();
                    }





                /*for(OrderCommodity oc : order.getDetailList()) {
                    if(!isBegin) {
                        row = sheet1.createRow(row_index);
                    }
                    cell = row.createCell(5);
                    cell.setCellStyle(dataStyle);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(oc.getName());
                    cell = row.createCell(6);
                    cell.setCellStyle(dataStyle);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(oc.getArt_no());
                    cell = row.createCell(7);
                    cell.setCellStyle(dataStyle);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(oc.getPrice());
                    cell = row.createCell(8);
                    cell.setCellStyle(dataStyle);
                    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                    cell.setCellValue(oc.getQuantity());
                    isBegin = false;
                    row_index++;
                }*/
            }

            //将创建好的excel存到指定文件夹下
            work.write(fileOut);
            fileOut.close();
            //压缩文件夹并下载，下载后删除文件夹
            FileUtils.createZip(response, excelPath, DateFormatter.dateToString(new Date(), "yyyy-MM-dd_HH:mm:ss:SSS"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
