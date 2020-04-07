package com.kill.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Servlet;
import javax.sql.DataSource;
import java.util.HashMap;
@Configuration
public class DruidConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource(){
        return new DruidDataSource();
    }

    //后台监控
    @Bean
    public ServletRegistrationBean druid(){
        ServletRegistrationBean<Servlet> servletServletRegistrationBean = new ServletRegistrationBean<>(new StatViewServlet(),"/druid/*");
        HashMap<String,String > initParameters = new HashMap<>();

        //设置后台登录密码
        initParameters.put("loginUsername", "qbb");
        initParameters.put("loginPassword", "723962386");

        //允许谁能访问
        initParameters.put("allow","");
        //禁止谁能访问
        //initParameters.put("name","ip");
        servletServletRegistrationBean.setInitParameters(initParameters);

        return servletServletRegistrationBean;
    }

}
