package com.chenjianxiong.cloud.controller;

import com.chenjianxiong.cloud.model.*;
import com.chenjianxiong.cloud.service.UserService;
import com.chenjianxiong.cloud.service.FileService;
import com.chenjianxiong.cloud.service.FileShareService;
import com.chenjianxiong.cloud.vo.FileShareVo;
import com.chenjianxiong.cloud.vo.Result;

import com.chenjianxiong.cloud.vo.UserFileVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

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
    public Result add(@RequestBody Map<String, Object> map, HttpSession session) {
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

    @RequestMapping(value = "/my/by_file", method = RequestMethod.POST)
    @ResponseBody
    public Result getMyShareByUserFile(@RequestBody Map<String, Integer> map, HttpSession session) {
        Result result = new Result(-42, "获取我分享的列表失败，请稍后尝试。");
        try {
            int fileId = map.get("fileId");
            int folderId = map.get("folderId");
            User user = (User) session.getAttribute("USER_LOGIN");
            UserFilePrimaryKey userFilePrimaryKey = new UserFilePrimaryKey(fileId, user.getId(), folderId);
            List<FileShareVo> fileShareVoList = this.fileShareService.getMyShareByUserFile(userFilePrimaryKey);
            if (fileShareVoList != null) {
                result.setState(0);
                result.setInfo("获取我分享的列表成功。");
                result.setData(fileShareVoList);
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

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody String jsonArray, HttpSession session) {
        Result result = new Result(-44, "分享删除失败，请稍后尝试。");
        try {
            User user = (User) session.getAttribute("USER_LOGIN");
            List<FileSharePrimaryKey> fileSharePrimaryKeyList = JSONObject.parseArray(jsonArray, FileSharePrimaryKey.class);
            for(FileSharePrimaryKey fileSharePrimaryKey: fileSharePrimaryKeyList) {
                fileSharePrimaryKey.setCreateUser(user.getId());
            }
            if (this.fileShareService.delete(fileSharePrimaryKeyList)) {
                result.setState(0);
                result.setInfo("分享删除成功。");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(-1);
            result.setInfo("服务器错误，请稍后尝试。");
        }
        return result;
    }

    @RequestMapping(value = "/get_file_id/by_folder_id", method = RequestMethod.POST)
    @ResponseBody
    public Result getFileId(@RequestBody Map<String, String> map) {
        Result result = new Result(-45, "分享给我的文件id获取失败，请稍后尝试。");
        try {
            int folderId = Integer.parseInt(map.get("folderId"));
            List<Integer> fileIdList = this.fileShareService.getFileId(folderId);
            result.setState(0);
            result.setInfo("文件编号列表获取成功。");
            result.setData(fileIdList);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(-1);
            result.setInfo("服务器错误，请稍后尝试。");
        }
        return result;
    }

    @RequestMapping(value = "/rename", method = RequestMethod.POST)
    @ResponseBody
    public Result update(@RequestBody Map<String, String> map, HttpSession session) {
        Result result = new Result(-31, "文件名更改失败，请稍后尝试。");
        try {
            User user = (User) session.getAttribute("USER_LOGIN");
            int userId = user.getId();
            int folderId = Integer.parseInt(map.get("folderId"));
            int fileId =  Integer.parseInt(map.get("fileId"));
            String fileName = map.get("fileName");
            UserFilePrimaryKey userFilePrimaryKey = new UserFilePrimaryKey(fileId, userId, folderId);
            UserFile userFile = new UserFile();
            userFile.setId(userFilePrimaryKey);
            userFile.setFileName(fileName);
            userFile = this.fileShareService.rename(userFile);
            result.setState(0);
            result.setInfo("文件名更改成功。");
            result.setData(userFile);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(-1);
            result.setInfo("服务器错误，请稍后尝试。");
        }
        return result;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile file, @RequestParam("folderId") int folderId, @RequestParam("fileName") String fileName,  @RequestParam("userId") Integer userId, HttpSession session) {
        Result result = new Result(-35, "文件上传失败，请稍后尝试。");
        try {
            result = this.fileService.upload(fileName, folderId, userId, file);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(-1);
            result.setInfo("服务器错误，请稍后尝试。");
        }
        return result;
    }

    @RequestMapping(value = "/create_folder", method = RequestMethod.POST)
    @ResponseBody
    public Result createFolder(@RequestBody Map<String, Object> map) {
        Result result = new Result(-34, "创建文件夹失败，请稍后尝试。");
        try {
            int userId = (int) map.get("userId");
            int folderId = (int) map.get("folderId");
            String fileName = (String) map.get("fileName");
            UserFileVo userFileVo = this.fileService.createFolder(userId, folderId, fileName);
            if (userFileVo != null) {
                result.setState(0);
                result.setInfo("文件夹创建成功。");
                result.setData(userFileVo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(-1);
            result.setInfo("服务器错误，请稍后尝试。");
        }
        return result;
    }

    @RequestMapping(value = "/move", method = RequestMethod.POST)
    @ResponseBody
    public Result move(@RequestBody Map<String, Object> map, HttpSession session) {
        Result result = new Result(-33, "文件移动失败，请稍后尝试。");
        try {
            int userId = (int) map.get("userId");
            List<Integer> fileIdList =  (List<Integer>) map.get("fileIdList");
            int folderId = (int) map.get("folderId");
            int newFolderId = (int) map.get("newFolderId");
            if (this.fileService.move(fileIdList, userId, folderId, newFolderId)) {
                result.setState(0);
                result.setInfo("文件移动成功。");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(-1);
            result.setInfo("服务器错误，请稍后尝试。");
        }
        return result;
    }
}
