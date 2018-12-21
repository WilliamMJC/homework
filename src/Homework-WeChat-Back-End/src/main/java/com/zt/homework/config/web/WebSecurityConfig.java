package com.zt.homework.config.web;

import com.zt.homework.Utils.JwtAuthenticationEntryPoint;
import com.zt.homework.filters.ThirdSessionAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationEntryPoint unauthorizedHandler;

    @Bean
    public ThirdSessionAuthFilter authenticationTokenFilterBean() throws Exception {
        return new ThirdSessionAuthFilter();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 由于使用的是JWT，所以不需要csrf
        .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
                // 基于token，所以不需要session
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
                // 允许对test的无授权访问
        .antMatchers(HttpMethod.GET, "/test").permitAll()
                // 允许所有获取压缩文件的访问
                // todo 这里不应该允许所有人可以访问，但是暂时不知道该怎么设置权限
        .antMatchers(HttpMethod.GET, "/construction/zip/**").permitAll()
                // 对于获取token的rest api要允许匿名访问
        .antMatchers("/auth").permitAll();

        // 添加本地第三方session filter
        httpSecurity
                .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);

        // 禁用缓存
        httpSecurity.headers().cacheControl();

    }

}
