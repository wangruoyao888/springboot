package com.example.wry_springboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/***********
 * 配置虚拟地址,让上传的文件存储的本地，不占用项目资源。
 */
@Configuration
public class UpLoadFileConfig extends WebMvcConfigurerAdapter {
    @Value("${upLoadPath}")
    private String filePath;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/file/**").addResourceLocations("file:"+filePath);
        super.addResourceHandlers(registry);
    }


}
