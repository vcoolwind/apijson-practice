package com.stone.apijson.demo.jdbc.controller;

import apijson.JSONResponse;
import apijson.Log;
import apijson.framework.APIJSONController;
import apijson.framework.APIJSONParser;
import apijson.framework.APIJSONVerifier;
import apijson.framework.BaseModel;
import apijson.orm.JSONRequest;
import apijson.orm.exception.NotExistException;
import com.alibaba.fastjson.JSONObject;
import com.stone.apijson.demo.jdbc.model.Credential;
import com.stone.apijson.demo.jdbc.model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

import static apijson.RequestMethod.*;
import static apijson.framework.APIJSONConstant.*;

@RestController
@RequestMapping("/usr/")
public class UserController extends APIJSONController {

    // 登入、登出、注册
    public static final String USER_CLASS_NAME = User.class.getSimpleName();
    public static final String CREDENTIAL_CLASS_NAME = Credential.class.getSimpleName();

    public static final String LOGIN_ENDPOINT = "login";
    public static final String LOGOUT_ENDPOINT = "logout";
    public static final String REGISTER_ENDPOINT = "register";

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    @PostMapping(LOGIN_ENDPOINT)
    public JSONObject login(@RequestBody String request, HttpSession session) {

        JSONObject requestObject = null;

        String username, password;

        // 框架信息，暂时可以忽略
        int version; // 全局默认版本号
        Boolean format; // 全局默认格式化配置
        JSONObject defaults; // 给每个请求JSON最外层加的字段

        // 提取信息
        try {
            requestObject = APIJSONParser.parseRequest(request);

            username = requestObject.getString(KEY_USERNAME);
            password = requestObject.getString(KEY_PASSWORD);

            version = requestObject.getIntValue(VERSION);
            format = requestObject.getBoolean(FORMAT);
            defaults = requestObject.getJSONObject(DEFAULTS);
            requestObject.remove(VERSION);
            requestObject.remove(FORMAT);
            requestObject.remove(DEFAULTS);
        } catch (Exception e) {
            return APIJSONParser.extendErrorResult(requestObject, e);
        }

        // 检查用户存在
        JSONObject userExistObj = new APIJSONParser(GETS, false).parseResponse(
                new JSONRequest(USER_CLASS_NAME,
                        new apijson.JSONObject(
                                new User().setUsername(username))
                                .setJson("friends")
                )
        );
        // 请求中间出错了
        if (!JSONResponse.isSuccess(userExistObj)) {
            return APIJSONParser.newResult(
                    userExistObj.getIntValue(JSONResponse.KEY_CODE),
                    userExistObj.getString(JSONResponse.KEY_MSG));
        }
        // 没有获取到 User
        if (!userExistObj.containsKey(USER_CLASS_NAME)) {
            return APIJSONParser.newErrorResult(new NotExistException("user not exist"));
        }

        User user = new JSONResponse(userExistObj).getObject(User.class);

        // 验证密码并获取权限
        // 这里是明文密码，实际上可以用 BCrypt 之类的 Encryptor 加密
        JSONObject pwdMatchObj = new APIJSONParser(GETS, false).parseResponse(
                new JSONRequest(
                        CREDENTIAL_CLASS_NAME,
                        new apijson.JSONObject(
                                new Credential().setId(user.getId()).setPwdHash(password))
                )
        );
        if (!JSONResponse.isSuccess(pwdMatchObj)) {
            return APIJSONParser.newResult(
                    pwdMatchObj.getIntValue(JSONResponse.KEY_CODE),
                    pwdMatchObj.getString(JSONResponse.KEY_MSG));
        }
        if (!pwdMatchObj.containsKey(CREDENTIAL_CLASS_NAME)) {
            return APIJSONParser.newErrorResult(new NotExistException("credential not match"));
        }


        // 注册用户 session
        super.login(session, user, version, format, defaults);
        session.setAttribute(USER_ID, user.getId());
        session.setAttribute(USER_CLASS_NAME, user);

        JSONResponse returnResp = new JSONResponse(userExistObj);
        returnResp.put(DEFAULTS, defaults);

        return returnResp;
    }

    @PostMapping(LOGOUT_ENDPOINT)
    public JSONObject logout(@RequestBody String request, HttpSession session) {
        long userId;
        try {
            userId = APIJSONVerifier.getVisitorId(session);//必须在session.invalidate();前！
            Log.d(TAG, "logout  userId = " + userId + "; session.getId() = " + (session == null ? null : session.getId()));
            // 销毁服务端 session
            super.logout(session);
        } catch (Exception e) {
            return APIJSONParser.newErrorResult(e);
        }

        // 返回登出成功 response
        JSONObject result = APIJSONParser.newSuccessResult();
        JSONObject user = APIJSONParser.newSuccessResult();
        user.put(ID, userId);
        user.put("logout", "success");
        result.put(USER_CLASS_NAME, user);

        return result;
    }

    public static final String KEY_REALNAME = "realname";
    public static final String REGISTER_REQ_TAG = "api_register";

    @PostMapping(REGISTER_ENDPOINT)
    public JSONObject register(@RequestBody String request, HttpSession session) {
        JSONObject requestObject = null;

        String username, password, realname;

        // 提取信息
        try {
            requestObject = APIJSONParser.parseRequest(request);
            username = requestObject.getString(KEY_USERNAME);
            password = requestObject.getString(KEY_PASSWORD);
            realname = requestObject.getString(KEY_REALNAME);
        } catch (Exception e) {
            return APIJSONParser.extendErrorResult(requestObject, e);
        }

        // 检查用户名冲突
        JSONObject userExistObj = new APIJSONParser(GETS, false).parseResponse(
                new JSONRequest(new User().setUsername(username))
        );
        // 请求中间出错了
        if (!JSONResponse.isSuccess(userExistObj)) {
            return APIJSONParser.newResult(
                    userExistObj.getIntValue(JSONResponse.KEY_CODE),
                    userExistObj.getString(JSONResponse.KEY_MSG));
        }
        // 已经存在相同用户名
        if (userExistObj.containsKey(USER_CLASS_NAME)) {
            return APIJSONParser.newErrorResult(new NotExistException("user already exist"));
        }


        // 构建新用户
        User user = new User()
                .setUsername(username)
                .setRealname(realname)
                .setBio("registered via api");
        Credential credential = new Credential()
                .setPwdHash(password);

        // 序列化的顺序很重要！！！！！！
        JSONRequest registerReq = new JSONRequest()
                .puts(USER_CLASS_NAME, user)
                .puts(CREDENTIAL_CLASS_NAME, credential)
                .puts(JSONRequest.KEY_TAG, REGISTER_REQ_TAG);


        JSONObject tmpReq = new APIJSONParser(POST,false).setNeedVerifyContent(true).parseResponse(registerReq);
        JSONResponse registerRsp = new JSONResponse(tmpReq);

        // 验证用户注册成功
        user = registerRsp.getObject(User.class);
        long userId = user==null ? 0 : BaseModel.value(user.getId());
        credential = registerRsp.getObject(Credential.class);
        long userId2 = credential==null ? 0 : BaseModel.value(credential.getId());
        RuntimeException e = null;
        System.out.println("userId = " + userId);
        System.out.println("userId2 = " + userId2);
        if (userId <= 0 || userId != userId2) {
            e = new RuntimeException("服务器内部错误！写入User或Privacy失败！" + "userid=" + userId + " userid2=" + userId2);
        }

        if (e != null) { //出现错误，回退
            new APIJSONParser(DELETE, false).parseResponse(
                    new JSONRequest(new User().setId(userId))
            );
            new APIJSONParser(DELETE, false).parseResponse(
                    new JSONRequest(new Credential().setId(userId2))
            );
            throw e;
        }

        return registerRsp;
    }

}
