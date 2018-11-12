package com.hzu.homework.util;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class TokenUtil {
	
	public static String SECRET = "HomeworkLogin";
	
	/**
     * 生成加密后的token
     * @param isVip 是不是VIP,true表示是VIP，false表示不是VIP。
     * @param username 用户名
     * @param name  姓名
     * @return 加密后的token
     */
    public static String getToken(final boolean isTeacher, final String userName,
            final String userUuid) {
    	//过期时间
    	Calendar nowTime = Calendar.getInstance();
    	nowTime.add(Calendar.MINUTE,1);
    	Date expiresDate =nowTime.getTime();
    	//签发时间
    	Date iatDate =new Date();
        String token = null;
        try {
            Date expiresAt = new Date(System.currentTimeMillis() + 24L * 60L * 3600L * 1000L);
            token = JWT.create()
                .withIssuer("auth0")
                .withClaim("isTeacher", isTeacher)
                .withClaim("userName", userName)
                .withClaim("userUuid", userUuid)
                .withExpiresAt(expiresAt)
                .withIssuedAt(iatDate)//设置签发时间
                // 使用了HMAC256加密算法。
                // mysecret是用来加密数字签名的密钥。
                .sign(Algorithm.HMAC256("mysecret"));
        } catch (JWTCreationException exception){
            //Invalid Signing configuration / Couldn't convert Claims.
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return token;
    }
    
    
    /**
     * 先验证token是否被伪造，然后解码token。
     * @param token 字符串token
     * @return 解密后的DecodedJWT对象，可以读取token中的数据。
     */
    public static DecodedJWT deToken(final String token) {
        DecodedJWT jwt = null;
        try {
            // 使用了HMAC256加密算法。
            // mysecret是用来加密数字签名的密钥。
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256("mysecret"))
                .withIssuer("auth0")
                .build(); //Reusable verifier instance
            jwt = verifier.verify(token);
        } catch (JWTVerificationException exception){
            //Invalid signature/claims
            exception.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return jwt;
    }
    
    
    
    public static Map<String,Claim>  verifyToken(String token) throws Exception{
    	JWTVerifier verifier = JWT.require(Algorithm.HMAC256("mysecret"))
    				.build();
    	DecodedJWT jwt = null;
    	try{
    		jwt = verifier.verify(token);
    	}catch (Exception e){
    		throw new RuntimeException("登录凭证已失效，请重新登录");
    	}
    	
    	return jwt.getClaims();
    	
    }
    
    

}
