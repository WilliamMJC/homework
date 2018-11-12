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
     * ���ɼ��ܺ��token
     * @param isVip �ǲ���VIP,true��ʾ��VIP��false��ʾ����VIP��
     * @param username �û���
     * @param name  ����
     * @return ���ܺ��token
     */
    public static String getToken(final boolean isTeacher, final String userName,
            final String userUuid) {
    	//����ʱ��
    	Calendar nowTime = Calendar.getInstance();
    	nowTime.add(Calendar.MINUTE,1);
    	Date expiresDate =nowTime.getTime();
    	//ǩ��ʱ��
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
                .withIssuedAt(iatDate)//����ǩ��ʱ��
                // ʹ����HMAC256�����㷨��
                // mysecret��������������ǩ������Կ��
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
     * ����֤token�Ƿ�α�죬Ȼ�����token��
     * @param token �ַ���token
     * @return ���ܺ��DecodedJWT���󣬿��Զ�ȡtoken�е����ݡ�
     */
    public static DecodedJWT deToken(final String token) {
        DecodedJWT jwt = null;
        try {
            // ʹ����HMAC256�����㷨��
            // mysecret��������������ǩ������Կ��
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
    		throw new RuntimeException("��¼ƾ֤��ʧЧ�������µ�¼");
    	}
    	
    	return jwt.getClaims();
    	
    }
    
    

}
