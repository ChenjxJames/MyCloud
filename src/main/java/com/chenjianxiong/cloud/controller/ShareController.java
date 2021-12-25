package com.chenjianxiong.cloud.controller;

import com.chenjianxiong.cloud.model.FileShare;
import com.chenjianxiong.cloud.model.User;
import com.chenjianxiong.cloud.service.UserService;
import com.chenjianxiong.cloud.service.FileService;
import com.chenjianxiong.cloud.service.FileShareService;
import com.chenjianxiong.cloud.vo.Result;

import com.chenjianxiong.cloud.vo.UserFileVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @author :
 * @date :
 * @description : 分享控制类
 */
@RequestMapping(value = "/share")
@Controller
public class ShareController {

    @Resource
    private UserService userService;

    @Resource
    private FileService fileService;

    @Resource
    private FileShareService fileShareService;


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Result createFolder(@RequestBody Map<String, Object> map, HttpSession session) {
        Result result = new Result(-40, "分享失败，请稍后尝试。");
        try {
            int shareUserRole = (int) map.get("shareUserRole");
            int fileId = (int) map.get("fileId");
            int folderId = (int) map.get("folderId");
            String username = (String) map.get("username");
            User user = (User) session.getAttribute("USER_LOGIN");
            if (user.getUsername().equals(username)) {
                result.setState(-41);
                result.setInfo("不能分享文件给自己。");
            } else {
                User shareUser = this.userService.findByUsername(username);
                FileShare fileShare = this.fileShareService.share(folderId, fileId, user, shareUser, shareUserRole);
                if (fileShare != null) {
                    result.setState(0);
                    result.setInfo("分享成功。");
                    result.setData(fileShare);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(-1);
            result.setInfo("服务器错误，请稍后尝试。");
        }
        return result;
    }

    @RequestMapping(value = "/my", method = RequestMethod.POST)
    @ResponseBody
    public Result getMyShare(HttpSession session) {
        Result result = new Result(-42, "获取我分享的列表失败，请稍后尝试。");
        try {
            User user = (User) session.getAttribute("USER_LOGIN");
            List<UserFileVo> userFileVoList = this.fileShareService.getMyShare(user.getId());
            if (userFileVoList != null) {
                result.setState(0);
                result.setInfo("获取我分享的列表成功。");
                result.setData(userFileVoList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(-1);
            result.setInfo("服务器错误，请稍后尝试。");
        }
        return result;
    }

    @RequestMapping(value = "/to_me", method = RequestMethod.POST)
    @ResponseBody
    public Result getShareToMe(HttpSession session) {
        Result result = new Result(-43, "获取分享给我的列表失败，请稍后尝试。");
        try {
            User user = (User) session.getAttribute("USER_LOGIN");
            List<UserFileVo> userFileVoList = this.fileShareService.getShareToMe(user.getId());
            if (userFileVoList != null) {
                result.setState(0);
                result.setInfo("获取分享给我的列表成功。");
                result.setData(userFileVoList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(-1);
            result.setInfo("服务器错误，请稍后尝试。");
        }
        return result;
    }
}
