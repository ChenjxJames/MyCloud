package com.chenjianxiong.cloud.service.impl;

import com.chenjianxiong.cloud.dao.LogRepository;
import com.chenjianxiong.cloud.dao.UserRepository;
import com.chenjianxiong.cloud.model.Log;
import com.chenjianxiong.cloud.model.User;
import com.chenjianxiong.cloud.service.LogService;
import com.chenjianxiong.cloud.vo.LogVo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Resource
    private LogRepository logRepository;

    @Resource
    private UserRepository userRepository;

    @Override
    public void updateFolder(List<Integer> fileIdList, int fileCreatorUser, int folderId, int newFolderId) {
        for(Integer fileId: fileIdList) {
            List<Log> logList = this.logRepository.findByFile(folderId, fileId, fileCreatorUser);
            for(Log log: logList) {
                log.setFolderId(newFolderId);
                this.logRepository.save(log);
            }
        }
    }

    @Override
    public List<LogVo> getFile(int folderId, int fileId, int fileCreatorUser) {
        List<Log> logList = this.logRepository.findByFile(folderId, fileId, fileCreatorUser);
        List<LogVo> logVoList = new ArrayList<>();
        for(Log log: logList) {
            User user = this.userRepository.findById(log.getShareUser());
            logVoList.add(new LogVo(log, user.getUsername()));
        }
        return logVoList;
    }

    @Override
    public Log add(Log log) {
        return this.logRepository.save(log);
    }
}
