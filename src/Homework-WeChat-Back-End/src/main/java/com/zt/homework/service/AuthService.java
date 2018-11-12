package com.zt.homework.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.zt.homework.config.wechat.WechatAuthProperties;
import com.zt.homework.dao.UserDao;
import com.zt.homework.dto.WechatAuthCodeResponse;
import com.zt.homework.dto.WechatAuthenticationResponse;
import com.zt.homework.entity.User;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class AuthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    /*
    * 服务器第三方session有效时间， 单位秒，默认1天
    * */
    private static final Long EXPIRES = 86400L;

    private RestTemplate wxAuthRestTemplate = new RestTemplate();

    @Autowired
    private WechatAuthProperties wechatAuthProperties;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserDao userDao;

    /**
     * 客户端登陆处理
     * @param code
     * @return
     */
    public WechatAuthenticationResponse wechatLogin(String code) {
        WechatAuthCodeResponse response = getWxSession(code);

        String wxOpenId = response.getOpenid();
        String wxSessionKey = response.getSessionKey();
        User user = new User();
        user.setOpenid(wxOpenId);
        Integer userId = loginOrRegisterUser(user);

        Long expires = response.getExpiresIn();
        String thirdSession = create3rdSession(wxOpenId, wxSessionKey, expires, userId);

        HashMap status = userStatus(userId);
        Boolean setting = (Boolean) status.get("setting");
        Boolean workMail = (Boolean) status.get("workMail");

        return new WechatAuthenticationResponse(thirdSession, setting, workMail);
    }

    /**
     * 向微信服务器发起请求，获得openid和session_key
     * @param code
     * @return
     */
    public WechatAuthCodeResponse getWxSession(String code) {
        LOGGER.info(code);
        Map<String, String> map = new HashMap<>();
        map.put("appid", wechatAuthProperties.getAppId());
        map.put("secret", wechatAuthProperties.getSecret());
        map.put("code", code);
        map.put("grantType", wechatAuthProperties.getGrantType());
        LOGGER.info(String.valueOf(map));
        String urlString = "?appid={appid}&secret={secret}&js_code={code}&grant_type={grantType}";
        String response = wxAuthRestTemplate.getForObject(
                wechatAuthProperties.getSessionHost() + urlString, String.class, map);

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectReader objectReader = objectMapper.readerFor(WechatAuthCodeResponse.class);
        WechatAuthCodeResponse res;
        try {
            res = objectReader.readValue(response);
        } catch (IOException e) {
            res = null;
            LOGGER.error("反序列化失败", e);
        }
        LOGGER.info(response);
        if (null == res) {
            throw new RuntimeException("调用微信接口失败");
        }
        if (res.getErrcode() != null) {
            throw new RuntimeException(res.getErrmsg());
        }
        res.setExpiresIn(res.getExpiresIn() != null ? res.getExpiresIn() : EXPIRES);
        return res;
    }

    /**
     * 创建3rcSession
     * @param wxOpenId
     * @param wxSessionKey
     * @param expires
     * @return
     */
    public String create3rdSession(String wxOpenId, String wxSessionKey, Long expires, Integer userId) {
        String thirdSessionKey = RandomStringUtils.randomAlphanumeric(64);

        stringRedisTemplate.opsForValue().set(thirdSessionKey, wxSessionKey + "#" + wxOpenId + "*" + userId, expires, TimeUnit.SECONDS);
        return thirdSessionKey;
    }

    /**
     * 检测用户的状态
     * @param userId
     * @return
     */
    private HashMap userStatus(Integer userId) {
        User user = userDao.queryUserByUserId(userId);
        HashMap<String, Boolean> status = new HashMap<>();
        Boolean setting = false;
        Boolean workMail = false;
        if(user != null) {
            if(user.getPersonalId() != null && !user.getPersonalId().equals("") && user.getPersonalMail() != null && !user.getPersonalMail().equals("")) {
                setting = true;
            }
            if(user.getWorkMail() != null && !user.getWorkMail().equals("") && user.getWorkMailPwd() != null && !user.getWorkMailPwd().equals("")) {
                workMail = true;
            }
        }

        status.put("setting", setting);
        status.put("workMail", workMail);

        return status;
    }

    /**
     * 判断是否是新用户
     * @param user
     */
    private Integer loginOrRegisterUser(User user) {
        User user1 = userDao.queryUserByOpenId(user.getOpenid());
        if(null == user1) {
            userDao.insertUser(user);
            return user.getUserId();
        } else {
            return user1.getUserId();
        }
    }
}
