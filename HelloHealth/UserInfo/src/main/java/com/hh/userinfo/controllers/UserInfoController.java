package com.hh.userinfo.controllers;

import com.hh.userinfo.entity.ResultData;
import com.hh.userinfo.entity.dto.FollowUser;
import com.hh.userinfo.entity.dto.ModifiedUserInfo;
import com.hh.userinfo.entity.dto.ReportInfo;
import com.hh.userinfo.entity.dto.UserId;
import com.hh.userinfo.entity.po.UserInfo;
import com.hh.userinfo.repository.FollowUserRepository;
import com.hh.userinfo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/userInfoService")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/info")
    public ResultData getUserInfo(Integer userId){
        System.out.println(userId);
        UserInfo user = userInfoService.getUserInfoById(userId);
        return userInfoService.setReturnDataForGetUserInfo(user);
    }

    @GetMapping("/details")
    public ResultData getDetails(Integer frontUserId,Integer sessionUserId){
        return userInfoService.getDetailedUserInfo(frontUserId,sessionUserId);
    }

    @PutMapping("/info")
    public ResultData modifyUserInfo(@RequestBody ModifiedUserInfo modifiedUserInfo){
        Integer sessionUserId = 3;
        return userInfoService.modifyUserInfo(modifiedUserInfo,sessionUserId);
    }

    @PostMapping("/avatar")
    public ResultData uploadAvatar(List<MultipartFile> file){
        String url = userInfoService.uploadImage(file.get(0));
        if(url == null)
            return ResultData.fail("上传图片失败");
        return userInfoService.setNewAvatar(url,3,null);
    }

    @PutMapping("/doctorApproval")
    public ResultData uploadDoctorApproval(List<MultipartFile> files){
        if(files.size()!=2)
            return ResultData.fail("上传图片数目错误");
        // 存放上传的两张图片的url
        String[] urls = new String[2];
        // [1]:url_certification  [2]:url_license
        urls[0] = userInfoService.uploadImage(files.get(0));
        urls[1] = userInfoService.uploadImage(files.get(1));
        if(urls[0] == null || urls[1] == null)
            return ResultData.fail("上传图片失败");
        return userInfoService.setDoctorApprovalInfo(urls,3);
    }

    @PostMapping("/follow")
    public ResultData followUser(@RequestBody FollowUser followUserID){
        Integer fUserId = Integer.parseInt(followUserID.getFollowUserID());
        Integer sUserId = 3;
        return userInfoService.followUser(fUserId,sUserId);
    }

    @PostMapping("/unfollow")
    public ResultData unfollowUser(@RequestBody FollowUser followUserID){
        Integer fUserId = Integer.parseInt(followUserID.getFollowUserID());
        Integer sUserId = 3;
        return userInfoService.unfollowUser(fUserId,sUserId);
    }

    @GetMapping("/posts")
    public ResultData fetchUserPosts(@RequestBody UserId userId){
        return userInfoService.fetchUserPosts(userId.getUser_id());
    }

    @GetMapping("/report")
    public ResultData getReport(Integer sessionId){
        return userInfoService.report(sessionId);
    }
}
