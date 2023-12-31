package com.hl.hhuserinfo.controllers;

import com.hl.hhuserinfo.entity.ResultData;
import com.hl.hhuserinfo.entity.dto.FollowUser;
import com.hl.hhuserinfo.entity.dto.ModifiedUserInfo;
import com.hl.hhuserinfo.entity.dto.ReportInfo;
import com.hl.hhuserinfo.entity.dto.UserId;
import com.hl.hhuserinfo.entity.po.UserInfo;
import com.hl.hhuserinfo.repository.FollowUserRepository;
import com.hl.hhuserinfo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private FollowUserRepository followUserRepository;

    @GetMapping("/getUserInfo")
    public ResultData getUserInfo(Integer userId){
        // TODO 因为使用的是sa-token登录，那么如何获得当前会话的userID呢？
        // TODO 这里先传入userId测试使用，后面要换成从前端或者会话当中获取ID
        System.out.println(userId);
        UserInfo user = userInfoService.getUserInfoById(userId);
        return userInfoService.setReturnDataForGetUserInfo(user);
    }

    @GetMapping("/getDetails")
    public ResultData getDetails(Integer frontUserId,Integer sessionUserId){
        // TODO session当中的Id怎么获取
        return userInfoService.getDetailedUserInfo(frontUserId,sessionUserId);
    }

    @PostMapping("/modifyUserInfo")
    public ResultData modifyUserInfo(@RequestBody ModifiedUserInfo modifiedUserInfo){
        Integer sessionUserId = 3;
        return userInfoService.modifyUserInfo(modifiedUserInfo,sessionUserId);
    }

    @PostMapping("/uploadAvatar")
    public ResultData uploadAvatar(List<MultipartFile> file){
        // TODO 前端传文件的时候设置每个文件在form表单当中的key为file
        String url = userInfoService.uploadImage(file.get(0));
        if(url == null)
            return ResultData.fail("上传图片失败");
        // TODO sessionID问题
        return userInfoService.setNewAvatar(url,3,null);
    }

    @PostMapping("/uploadDoctorApproval")
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

    @PostMapping("/followUser")
    public ResultData followUser(@RequestBody FollowUser followUserID){
        Integer fUserId = Integer.parseInt(followUserID.getFollowUserID());
        Integer sUserId = 3;
        // TODO sessionId问题的处理
        return userInfoService.followUser(fUserId,sUserId);
    }

    @PostMapping("/unfollowUser")
    public ResultData unfollowUser(@RequestBody FollowUser followUserID){
        Integer fUserId = Integer.parseInt(followUserID.getFollowUserID());
        Integer sUserId = 3;
        // TODO sessionId问题的处理
        return userInfoService.unfollowUser(fUserId,sUserId);
    }

    @PostMapping("/fetchUserPosts")
    public ResultData fetchUserPosts(@RequestBody UserId userId){
        return userInfoService.fetchUserPosts(userId.getUser_id());
    }

    @GetMapping("/Report")
    public ResultData getReport(Integer sessionId){
        return userInfoService.report(sessionId);
    }
}
