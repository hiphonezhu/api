package com.java.api.security.impl;

import com.java.api.security.TokenManager;
import com.java.api.util.CodecUtil;
import com.java.api.util.StringUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 默认令牌管理器
 *
 * @author huangyong
 * @since 1.0.0
 */
public class DefaultTokenManager implements TokenManager {

    private static Map<String, String> tokenMap = new ConcurrentHashMap<String, String>();

    @Override
    public String createToken(String username) {
        String token = CodecUtil.createUUID();
        tokenMap.put(token, username);
        return token;
    }

    @Override
    public boolean checkToken(String token) {
        return !StringUtil.isEmpty(token) && tokenMap.containsKey(token);
    }
}
