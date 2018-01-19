package com.safety.config;

import org.springframework.beans.factory.annotation.Value;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by Administrator on 2017/3/3.
 */
@WebListener
public class MyHttpSessionListener implements HttpSessionListener {
    @Value("${base.img.url}")
    private String imgUrl;

    @Value("${base.qiniu.image.url}")
    private String qiNiuImgUrl;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
            se.getSession().setAttribute("img_display_url",imgUrl);
            se.getSession().setAttribute("img_qiniu_url",qiNiuImgUrl);
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("ServletContex初始化");
    }


}
