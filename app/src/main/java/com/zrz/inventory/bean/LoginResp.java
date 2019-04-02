package com.zrz.inventory.bean;

/**
 * @author 周瑞忠
 * @description java类作用描述
 * @date 2019/3/31 1:43
 */
public class LoginResp {

    /**
     * 200表示成功
     */
    private String code;
    /**
     * 返回提示信息
     */
    private String message;
    /**
     * 加密的Key
     */
    private String keyName;
    /**
     * 加密的secret
     */
    private String secretName;
    /**
     * 鉴权token
     */
    private String token;
    /**
     * 登陆uuid
     */
    private String loginUuid;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getSecretName() {
        return secretName;
    }

    public void setSecretName(String secretName) {
        this.secretName = secretName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getLoginUuid() {
        return loginUuid;
    }

    public void setLoginUuid(String loginUuid) {
        this.loginUuid = loginUuid;
    }

}
