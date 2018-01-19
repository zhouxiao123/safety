package com.safety.util;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

/**
 * Created by Administrator on 2017/3/1.
 */
public class MyPageSupport {


    static public Pageable initPageSupport(HttpServletRequest request){
        Integer pageOffset = 0;
        Integer pageSize = 20;
        if(request.getParameter("PageOffset") != null && request.getParameter("PageOffset")!="")
            pageOffset = Integer.valueOf(request.getParameter("PageOffset"));
        if(request.getParameter("PageSize") != null && request.getParameter("PageSize")!="")
            pageSize = Integer.valueOf(request.getParameter("PageSize"));
        return new PageRequest(pageOffset,pageSize);
    }

    static public void setPageSupport(Model model, Page p){
        model.addAttribute("pageSize",p.getSize());
        model.addAttribute("totalPage",p.getTotalPages());
        model.addAttribute("pageOffset",p.getNumber());
        model.addAttribute("isFirst",p.isFirst());
        model.addAttribute("isLast",p.isLast());
    }
}
