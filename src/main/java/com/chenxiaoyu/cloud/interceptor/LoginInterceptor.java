package com.chenxiaoyu.cloud.interceptor;

import com.chenxiaoyu.cloud.model.User;
import com.chenxiaoyu.cloud.service.UserService;
import com.chenxiaoyu.cloud.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * @author ：
 * @date ：Created in 2020/6/14 18:32
 * @description：登录验证拦截器
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        // 获取session
        HttpSession session=request.getSession();
        User user = (User) session.getAttribute("USER_LOGIN");
        if(user != null) {  // session中存在用户信息
            return true;  // session登录成功
        }
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        Result result = new Result(-20, "请登录。");
        writer.print(result);
        writer.close();
        response.flushBuffer();
        return false;  // session登录失败
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

