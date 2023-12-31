package com.hl.hhuserinfo.service;

import com.hl.hhuserinfo.entity.ResultData;
import com.hl.hhuserinfo.entity.dto.ModifiedUserInfo;
import com.hl.hhuserinfo.entity.po.UserInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface UserInfoService {
    // 根据ID查找用户信息
    UserInfo getUserInfoById(Integer userId);
    ResultData setReturnDataForGetUserInfo(UserInfo user);
    ResultData getDetailedUserInfo(Integer frontUserId,Integer sessionUserId);
    ResultData modifyUserInfo(ModifiedUserInfo modifiedUserInfo,Integer sessionUserId);
    String uploadImage(MultipartFile avatarFile);
    ResultData setNewAvatar(String url,Integer sessionUserId,Integer sessionAdminId);
    // 上传医师资格证之后修改数据库函数
    ResultData setDoctorApprovalInfo(String[] urls,Integer sessionUserId);
    ResultData followUser(Integer followUserId, Integer sessionUserId);
    ResultData unfollowUser(Integer followUserId, Integer sessionUserId);
    ResultData fetchUserPosts(Integer userId);
    ResultData report(Integer sessionId);
}
