package com.chenjianxiong.cloud.controller;

import com.chenjianxiong.cloud.model.UserFile;
import com.chenjianxiong.cloud.model.UserFilePrimaryKey;
import com.chenjianxiong.cloud.service.FileService;
import com.chenjianxiong.cloud.vo.Result;
import com.chenjianxiong.cloud.model.User;
import com.chenjianxiong.cloud.vo.UserFileVo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

/**
 * @author :
 * @date :
 * @description : 文件控制类
 */
@RequestMapping(value = "/cloud")
@Controller
public class CloudController {
    @Resource
    private FileService fileService;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @ResponseBody
    public Result getFileInfo(@RequestBody Map<String, String> map, HttpSession session) {
        Result result = new Result(-30, "文件信息获取失败，请稍后尝试。");
        try {
            User user = (User) session.getAttribute("USER_LOGIN");
            int userId = user.getId();
            int folderId = Integer.parseInt(map.get("folderId"));
            List<UserFileVo> userFileVoList = this.fileService.getFileInfo(userId, folderId);
            result.setState(0);
            result.setInfo("文件信息获取成功。");
            result.setData(userFileVoList);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(-1);
            result.setInfo("服务器错误，请稍后尝试。");
        }
        return result;
    }

    @RequestMapping(value = "/byfileid", method = RequestMethod.POST)
    @ResponseBody
    public Result getFileInfoByFileIdList(@RequestBody Map<String, Object> map, HttpSession session) {
        Result result = new Result(-30, "文件信息获取失败，请稍后尝试。");
        try {
            User user = (User) session.getAttribute("USER_LOGIN");
            int userId = user.getId();
            int folderId = (int) map.get("folderId");
            List<Integer> fileIdList = (List<Integer>) map.get("fileIdList");
            List<UserFileVo> userFileVoList = this.fileService.getFileInfo(userId, folderId, fileIdList);
            result.setState(0);
            result.setInfo("文件信息获取成功。");
            result.setData(userFileVoList);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(-1);
            result.setInfo("服务器错误，请稍后尝试。");
        }
        return result;
    }

    @RequestMapping(value = "/fileid", method = RequestMethod.POST)
    @ResponseBody
    public Result getFileId(@RequestBody Map<String, String> map, HttpSession session) {
        Result result = new Result(-30, "文件信息获取失败，请稍后尝试。");
        try {
            User user = (User) session.getAttribute("USER_LOGIN");
            int userId = user.getId();
            int folderId = Integer.parseInt(map.get("folderId"));
            List<Integer> fileIdList = this.fileService.getFileId(userId, folderId);
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

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Result upload(@RequestParam("file") MultipartFile file, @RequestParam("folderId") int folderId, @RequestParam("fileName") String fileName, HttpSession session) {
        Result result = new Result(-35, "文件上传失败，请稍后尝试。");
        try {
            User user = (User) session.getAttribute("USER_LOGIN");
            int userId = user.getId();
            result = this.fileService.upload(fileName, folderId, userId, file);
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(-1);
            result.setInfo("服务器错误，请稍后尝试。");
        }
        return result;
    }

    @RequestMapping(value = "/download/{folderId}/{fileId}", method = RequestMethod.GET)
    @ResponseBody
    public Result download(@PathVariable("folderId") Integer folderId, @PathVariable("fileId") Integer fileId, HttpSession session, HttpServletResponse response) {
        Result result = new Result(-30, "文件信息获取失败，请稍后尝试。");
        try {
            User user = (User) session.getAttribute("USER_LOGIN");
            int userId = user.getId();
            System.out.println("download: user_" + userId + " file_" + fileId);
            UserFileVo userFileVo = this.fileService.download(userId, fileId, folderId);
            if (userFileVo != null) {
                File file = new File(userFileVo.getPath());
                if(file.exists()){ //判断文件父目录是否存在
                    response.setContentType("application/octet-stream");
                    response.setCharacterEncoding("UTF-8");
                    response.setHeader("Content-Disposition", "attachment;fileName=" +   java.net.URLEncoder.encode(userFileVo.getFileName(),"UTF-8"));
                    byte[] buffer = new byte[1024];
                    FileInputStream fis = null; //文件输入流
                    BufferedInputStream bis = null;
                    OutputStream os = null; //输出流
                    try {
                        os = response.getOutputStream();
                        fis = new FileInputStream(file);
                        bis = new BufferedInputStream(fis);
                        int i = bis.read(buffer);
                        while(i != -1){
                            os.write(buffer);
                            i = bis.read(buffer);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        assert bis != null;
                        bis.close();
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                result.setState(0);
                result.setInfo("文件信息获取成功。");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(-1);
            result.setInfo("服务器错误，请稍后尝试。");
        }
        return result;
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
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
            userFile = this.fileService.update(userFile);
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

    @RequestMapping(value = "/move", method = RequestMethod.POST)
    @ResponseBody
    public Result move(@RequestBody Map<String, Object> map, HttpSession session) {
        Result result = new Result(-33, "文件移动失败，请稍后尝试。");
        try {
            User user = (User) session.getAttribute("USER_LOGIN");
            int userId = user.getId();
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

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(@RequestBody Map<String, Object> map, HttpSession session) {
        Result result = new Result(-32, "文件删除失败，请稍后尝试。");
        try {
            User user = (User) session.getAttribute("USER_LOGIN");
            int userId = user.getId();
            int folderId = (int) map.get("folderId");
            List<Integer> fileIdList =  (List<Integer>) map.get("fileIdList");
            List<UserFilePrimaryKey> userFileIdList = new ArrayList<UserFilePrimaryKey>();
            for (Iterator<Integer> iter = fileIdList.iterator(); iter.hasNext();) {
                userFileIdList.add(new UserFilePrimaryKey((Integer) iter.next(), userId, folderId));
            }
            if (this.fileService.delete(userFileIdList)) {
                result.setState(0);
                result.setInfo("文件删除成功。");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setState(-1);
            result.setInfo("服务器错误，请稍后尝试。");
        }
        return result;
    }

    @RequestMapping(value = "/createFolder", method = RequestMethod.POST)
    @ResponseBody
    public Result createFolder(@RequestBody Map<String, Object> map, HttpSession session) {
        Result result = new Result(-34, "创建文件夹失败，请稍后尝试。");
        try {
            User user = (User) session.getAttribute("USER_LOGIN");
            int userId = user.getId();
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
}
