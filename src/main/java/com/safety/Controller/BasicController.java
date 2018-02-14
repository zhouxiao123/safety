package com.safety.Controller;



import com.safety.entity.AdminUser;
import com.safety.service.AdminService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 基本控制器，控制后台页面跳转
 * Created by Administrator on 2016/12/21.
 */
@Controller
public class BasicController {

    @Autowired
    private AdminService adminService;

    /**
     * 登跳转到登陆页面
     *
     * @return 跳转到登陆页面
     */
    @RequestMapping("/")
    public String index() {
        return "redirect:login";
    }

    /**
     * 登陆页面
     *
     * @return 登陆页面
     */
    @RequestMapping("/login")
    public String login() {
        return "index";
    }


    /**
     * 默认页面
     *
     * @return 默认页面
     */
    @RequestMapping("/default")
    public String defaultPage() {
        return "frame/default";
    }

    /**
     * 登陆成功后跳转到后台首页
     *
     * @return 跳转到后台首页
     */
    @RequestMapping("/main")
    public String main(Model model,HttpServletRequest request) {
        /*UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        AdminUser au = adminService.findByLoginName(userDetails.getUsername());
        model.addAttribute("user",au);*/
        System.out.println("---------");
        return "redirect:daily/daily_list_biaoge_mobile";
    }



    @RequestMapping("/changePassword")
    public String changePassword() {

        return "back/changePassword";
    }



    @RequestMapping("/saveChangePassword")
    public String saveChangePassword(HttpServletRequest request, Model model, @RequestParam String password, @RequestParam String newPassword) {
        if(StringUtils.isEmpty(password)){
            model.addAttribute("msg","原密码不可为空!");
            return "back/changePassword";
        } else if(StringUtils.isEmpty(newPassword)){
            model.addAttribute("msg","新密码不可为空!");
            return "back/changePassword";
        }
        AdminUser au = adminService.findByLoginName("admin");
        BCryptPasswordEncoder pe = new BCryptPasswordEncoder();
        if(pe.matches(password,au.getPassword())){
            au.setPassword(pe.encode(newPassword));
            adminService.saveAdminUser(au);
            model.addAttribute("msg","密码修改成功!");
        } else {
            model.addAttribute("msg","密码错误!");
        }
        return "back/changePassword";
    }

}
