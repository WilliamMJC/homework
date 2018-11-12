package com.zt.homework.filters;

import com.zt.homework.config.AppContext;
import com.zt.homework.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class ThirdSessionAuthFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        // 获取请求头部的Authorization
        String authHeader = httpServletRequest.getHeader(this.tokenHeader);
        String url = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length());

        if (url.equals("/auth") || url.equals("/test")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        if(null == authHeader || !authHeader.startsWith("Bearer") || authHeader.equals("Bearer")) {
            LOGGER.error("非法访问用户");
            httpServletRequest.getRequestDispatcher("/error/unAuth").forward(httpServletRequest, httpServletResponse);
            return;
        }
        // the part after "Bearer "
        final String thirdSessionId = authHeader.substring(tokenHead.length());
        String wxSessionObj = stringRedisTemplate.opsForValue().get(thirdSessionId);
        if(StringUtils.isEmpty(wxSessionObj)) {
            LOGGER.warn("用户身份已过期");
            httpServletRequest.getRequestDispatcher("/error/authExpired").forward(httpServletRequest, httpServletResponse);
            return;
        }

        // 设置当前登陆用户
        try (AppContext appContext = new AppContext(Integer.parseInt(wxSessionObj.substring(wxSessionObj.indexOf("*") + 1)))) {
            Long expireTime = stringRedisTemplate.getExpire(thirdSessionId, TimeUnit.SECONDS);
            expireTime += 600;
            stringRedisTemplate.expire(thirdSessionId, expireTime, TimeUnit.SECONDS);
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
