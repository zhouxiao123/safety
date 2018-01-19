package com.safety.filter;





import com.safety.util.FileUploadUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Administrator on 2017/3/24.
 */
@Configuration
public class ImageFilter extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/displayImg/**").addResourceLocations("file:"+ FileUploadUtils.realPath);
        super.addResourceHandlers(registry);
    }
}
