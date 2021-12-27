package com.chenjianxiong.cloud.controller;

import com.chenjianxiong.cloud.model.*;
import com.chenjianxiong.cloud.service.LogService;
import com.chenjianxiong.cloud.vo.LogVo;
import com.chenjianxiong.cloud.vo.Result;
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
@RequestMapping(value = "/log")
@Controller
public class LogController {

    @Resource
    private LogService logService;

    @RequestMapping(value = "/by_file", method = RequestMethod.POST)
    @ResponseBody
    public Result getMyShare(@RequestBody Map<String, Object> map, HttpSession session) {
        Result result = new Result(-51, "日志信息获取失败，请稍后尝试。");
        try {
            User user = (User) session.getAttribute("USER_LOGIN");
            int fileId = (int) map.get("fileId");
            int folderId = (int) map.get("folderId");
            List<LogVo> logVoList = this.logService.getFile(folderId, fileId, user.getId());
            if (logVoList != null) {
                result.setState(0);
                result.setInfo("日志信息获取成功。");
                result.setData(logVoList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(-1);
            result.setInfo("服务器错误，请稍后尝试。");
        }
        return result;
    }
}
