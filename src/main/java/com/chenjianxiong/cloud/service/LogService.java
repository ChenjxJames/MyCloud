package com.chenjianxiong.cloud.service;

import com.chenjianxiong.cloud.model.*;
import com.chenjianxiong.cloud.vo.LogVo;

import java.util.List;

/**
 * @author :
 * @date :
 * @description : 日志服务接口
 */
public interface LogService {

    public void updateFolder(List<Integer> fileIdList, int fileCreatorUser, int folderId, int newFolderId);

    public List<LogVo> getFile(int folderId, int fileId, int fileCreatorUser);

    public Log add(Log log);
}
