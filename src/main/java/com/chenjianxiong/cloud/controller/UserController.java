package com.chenjianxiong.cloud.controller;

import com.chenjianxiong.cloud.model.User;
import com.chenjianxiong.cloud.service.UserService;
import com.chenjianxiong.cloud.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Map;

/**
 * @author ：
 * @date ：Created in 2020/6/14 17:18
 * @description：用户控制类
 */
@RequestMapping(value = "/user")
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public Result index(HttpSession session) {
        Result result = new Result(-20, "请登录！");
        User user = (User) session.getAttribute("USER_LOGIN");
        if (user != null) {
            result.setState(0);
            result.setInfo("获取用户信息成功。");
            result.setData(user);
        }
        return result;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Result register(@RequestBody User user) {
        Result result = new Result(-22, "注册失败。");
        try {
            if (this.userService.findByUsername(user.getUsername()) != null) {
                result.setState(-23);
                result.setInfo("注册失败，该用户名已被使用。");
            } else if (this.userService.findByEmail(user.getEmail()) != null) {
                result.setState(-24);
                result.setInfo("注册失败，该邮箱已注册。");
            } else {
                user.setCreateTime(new Date());
                User resultUser = this.userService.register(user);
                result.setState(0);
                result.setInfo("注册成功！");
                result.setData(resultUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(-1);
            result.setInfo("服务器错误，请稍后尝试。");
        }
        return result;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Result login(@RequestBody Map<String, String> map, HttpSession session) {
        Result result = new Result(-21, "登录失败，用户名或密码错误。");
        try {
            String username = map.get("username");
            String password = map.get("password");
            String keeplogin = map.get("keeplogin");
            User user = this.userService.login(username, password);
            if (user.getId() != 0) {
                result.setState(0);
                result.setInfo("登录成功！");
                result.setData(user);
                session.setAttribute("USER_LOGIN", user);
                if (Boolean.parseBoolean(keeplogin)) {  // keep login 30 day
                    result.setInfo("登陆成功,保持登陆。");
                    session.setMaxInactiveInterval(30*24*60*60);  //set session time
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(-1);
            result.setInfo("服务器错误，请稍后尝试。");
        }
        return result;
    }

    @RequestMapping(value = "/setpassword", method = RequestMethod.POST)
    @ResponseBody
    public Result changePassword(@RequestBody Map<String, String> map, HttpSession session) {
        Result result = new Result(-25, "密码更改失败，请稍后尝试。");
        try {
            User user = (User) session.getAttribute("USER_LOGIN");
            String password = map.get("password");
            String newPassword = map.get("newPassword");
            if (this.userService.login(user.getUsername(), user.getPassword()).getId() != 0) {
                if (password.equalsIgnoreCase(user.getPassword())) {
                    user.setPassword(newPassword);
                    if (this.userService.changeInfo(user).getId() != 0) {
                        result.setState(0);
                        result.setInfo("密码更改成功。");
                    }
                } else {
                    result.setState(-26);
                    result.setInfo("密码更改失败，旧密码输入错误。");
                }
            } else {
                result.setState(-20);
                result.setInfo("请登录！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(-1);
            result.setInfo("服务器错误，请稍后尝试。");
        }
        return result;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public Result logout(HttpSession session) {
        session.invalidate();  //delete session
        return new Result(0, "注销成功");
    }
}
