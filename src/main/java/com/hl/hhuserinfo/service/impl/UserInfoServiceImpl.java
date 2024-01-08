package com.hl.hhuserinfo.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hl.hhuserinfo.entity.ResultData;
import com.hl.hhuserinfo.entity.dto.*;
import com.hl.hhuserinfo.entity.po.*;
import com.hl.hhuserinfo.entity.po.FollowUser;
import com.hl.hhuserinfo.repository.*;
import com.hl.hhuserinfo.service.NotificationService;
import com.hl.hhuserinfo.service.UserInfoService;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;

@Service
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepository userInfoRepository;

    private final NotificationService notificationService;

    private final FollowUserRepository followUserRepository;

    private final AppliedDoctorRepository appliedDoctorRepository;

    private final AdministratorRepository administratorRepository;

    private final AuthenticationCheckRepository authenticationCheckRepository;

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final PostTagRepository postTagRepository;

    private final UserRewardCommentRepository userRewardCommentRepository;

    private final CommentReportRepository commentReportRepository;

    @Value("${smms.secretKey}")
    private String smmsSecret;

    public UserInfoServiceImpl(UserInfoRepository userInfoRepository, NotificationService notificationService, FollowUserRepository followUserRepository, AppliedDoctorRepository appliedDoctorRepository, AdministratorRepository administratorRepository, AuthenticationCheckRepository authenticationCheckRepository, CommentRepository commentRepository, PostRepository postRepository, PostTagRepository postTagRepository, UserRewardCommentRepository userRewardCommentRepository, CommentReportRepository commentReportRepository) {
        this.userInfoRepository = userInfoRepository;
        this.notificationService = notificationService;
        this.followUserRepository = followUserRepository;
        this.appliedDoctorRepository = appliedDoctorRepository;
        this.administratorRepository = administratorRepository;
        this.authenticationCheckRepository = authenticationCheckRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.postTagRepository = postTagRepository;
        this.userRewardCommentRepository = userRewardCommentRepository;
        this.commentReportRepository = commentReportRepository;
    }

    @Override
    public UserInfo getUserInfoById(Integer userId) {
        try{
            Optional<UserInfo> userOrNull = userInfoRepository.findById(userId);
            return userOrNull.orElse(null);
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    @Override
    public ResultData setReturnDataForGetUserInfo(UserInfo user) {
        if(user == null)
            return ResultData.fail("没有该用户");
        Map<String, Object> result = new HashMap<>();
        result.put("login",true);
        result.put("user_phone",user.getPhoneNumber());
        result.put("user_name",user.getUserName());
        result.put("user_id",user.getUserId());
        //用户是否被封禁
        if(user.getIsLocked()==null)
            result.put("locked",false);
        else result.put("locked",true);
        // 用户的群体
        if(user.getIsApproved().equals("y"))
            result.put("user_group","doctor");
        else result.put("user_group","normal");
        // 是否有未读的通知？
        if(notificationService.hasUnReadNotice(user.getUserId(), 0))
            result.put("unread_notification",true);
        else result.put("unread_notification",false);
        // 医生身份是否认证
        if(user.getIsApproved().equals("y"))
            result.put("verified",true);
        else result.put("verified",false);
        return ResultData.ok(result);
    }

    @Override
    public ResultData getDetailedUserInfo(Integer frontUserId,Integer sessionUserId) {
        // map用于存放返回的对象
        Map<String, Object> result = new HashMap<>();
        if(frontUserId == -1)
            frontUserId = sessionUserId; // 表明前端要查看自己的信息
        try{
            if(frontUserId.equals(sessionUserId))
                result.put("isFollowed",false); // 自己不能关注自己
            else {
                // 找到session当中的ID对应的用户
                Optional<UserInfo> sessionUserOrNULL = userInfoRepository.findById(sessionUserId);
                if(sessionUserOrNULL.isEmpty()){
                    return ResultData.notFound("session用户没找到");
                }
                // 尝试找一下sessionUser的关注列表当中，是否有人的用户id为frontUserId
                Integer isFollowed = followUserRepository.findMyFollowUserId(sessionUserId,frontUserId);
                if(isFollowed == null)
                    result.put("isFollowed",false);
                else result.put("isFollowed",true);
            }
            // 判断前端传来要查看的用户是否被封禁
            Optional<UserInfo> frontUserOrNULL = userInfoRepository.findById(frontUserId);
            if(frontUserOrNULL.isEmpty())
                return ResultData.notFound("前端传来的用户没找到");
            else {
                UserInfo frontUser = frontUserOrNULL.get();
                result.put("isLocked", frontUser.getIsLocked() != null);
            }
            // 获得最新的且通过审核的认证证书信息
            List<DetailCerti> certis = appliedDoctorRepository.findAuthCertificationInfo(frontUserId);
            if(certis.isEmpty())
                result.put("certification",null);
            else result.put("certification",certis.get(0));
            // 获取前端传来的用户的信息
            UserInfo frontUser = frontUserOrNULL.get();
            DetailUserInfo userInformation = new DetailUserInfo();
            setUserInformation(userInformation,frontUser); // 设置参数
            result.put("userInfo",userInformation);
            // 获取前端传来的用户的关注者，即该用户关注了谁
            List<UserInfo> followUsers = followUserRepository.findFollowUserInfo(frontUserId);
            if(followUsers.isEmpty())
                result.put("followList",null);
            List<DetailFanFollowInfo> followInfos = new ArrayList<>();
            setFollowListInfo(followUsers,followInfos);
            result.put("followList",followInfos);
            // 获取前端传来的用户的粉丝，即谁关注了该用户
            List<UserInfo> fanUsers = followUserRepository.findFanUserInfo(frontUserId);
            if(fanUsers.isEmpty())
                result.put("fansList",null);
            List<DetailFanFollowInfo> fanInfos = new ArrayList<>();
            setFollowListInfo(fanUsers,fanInfos);
            result.put("fansList",fanInfos);
            return ResultData.ok(result);

        }catch (Exception e){
            System.out.println(e);
            return ResultData.fail("错误");
        }
    }

    @Override
    public ResultData modifyUserInfo(ModifiedUserInfo modifiedUserInfo, Integer sessionUserId) {
        // 先找一下要修改信息的用户是否存在
        Optional<UserInfo> sessionUserOrNULL = userInfoRepository.findById(sessionUserId);
        if(sessionUserOrNULL.isEmpty()){
            return ResultData.notFound("session用户没找到");
        }
        UserInfo changedUser = sessionUserOrNULL.get();
        changedUser.setGender(modifiedUserInfo.getGender().equals("男")?"m":"f");
        changedUser.setEmail(modifiedUserInfo.getEmail());
        changedUser.setSignature(modifiedUserInfo.getSignature());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime tmpBirth = LocalDateTime.parse(modifiedUserInfo.getBirthday() + " 00:00:00",formatter);
        LocalDate birth = tmpBirth.toLocalDate();
        changedUser.setBirthDay(birth);
        try{
            userInfoRepository.save(changedUser);
        }catch (Exception e){
            System.out.println(e);
            return ResultData.fail("存入数据库失败");
        }
        DetailUserInfo result = new DetailUserInfo();
        setUserInformation(result,changedUser);
        return ResultData.ok(result);
    }

    @Override
    public String uploadImage(MultipartFile avatarFile) {
        try{
            File tmpFile = File.createTempFile("temp",null);
            avatarFile.transferTo(tmpFile);
            String baseUrl = "https://smms.app/api/v2/upload";
            HttpResponse<String> response = Unirest.post(baseUrl)
                    .header("Authorization",smmsSecret)
                    .field("smfile",tmpFile)
                    .asString();
            System.out.println(response.getBody());
            tmpFile.delete();
            JSONObject jsonResponse = JSONObject.parseObject(response.getBody());
            if(jsonResponse.getBoolean("success")){
                JSONObject dataJson = jsonResponse.getJSONObject("data");
                return dataJson.getString("url");
            }
            else if(jsonResponse.containsKey("images")){
                return jsonResponse.getString("images");
            }
            else return null;
        }
        catch (Exception e){
            System.out.println(e);
            return null;
        }
    }

    @Override
    public ResultData setNewAvatar(String url, Integer sessionUserId, Integer sessionAdminId) {
        try{
            // 根据传入的参数设置用户的头像的URL
            if(sessionUserId!=null){
                Optional<UserInfo> userInfoOrNULL = userInfoRepository.findById(sessionUserId);
                if(userInfoOrNULL.isEmpty())
                    return ResultData.fail("没有该用户");
                UserInfo userInfo = userInfoOrNULL.get();
                userInfo.setPortait(url);
                userInfoRepository.save(userInfo);
                return ResultData.ok(url);
            }
            else if(sessionAdminId!=null){
                Optional<Administrator> adminInfoOrNULL = administratorRepository.findById(sessionAdminId);
                if(adminInfoOrNULL.isEmpty())
                    return ResultData.fail("没有该管理员");
                Administrator adminInfo = adminInfoOrNULL.get();
                adminInfo.setPortrait(url);
                administratorRepository.save(adminInfo);
                return ResultData.ok(url);
            }
            return ResultData.fail("失败");
        }catch (Exception e){
            System.out.println(e);
            return ResultData.fail("查找数据库失败");
        }
    }

    @Override
    public ResultData setDoctorApprovalInfo(String[] urls, Integer sessionUserId) {
        try {
            AppliedDoctor appliedDoctor = new AppliedDoctor();
            appliedDoctor.setUserId(sessionUserId);
            appliedDoctor.setCertification(urls[0]);
            appliedDoctor.setLicense(urls[1]);
            appliedDoctor.setTitle("空");
            appliedDoctor.setHospitalRank("空");
            appliedDoctor.setDepartment("空");
            appliedDoctorRepository.save(appliedDoctor);
            appliedDoctor = appliedDoctorRepository.findByCertificationAndLicense(
                    appliedDoctor.getCertification(),
                    appliedDoctor.getLicense()
            );
            AuthenticationCheck authenticationCheck = new AuthenticationCheck();
            //把数据插入AppliedDoctor后还需要插入AuthenticationCheck这个表里面去
            authenticationCheck.setApplyId(appliedDoctor.getApplyId());
            authenticationCheck.setAdministratorId(null);
            //认证提交时间由系统自动生成
            authenticationCheck.setReviewTime(null);
            authenticationCheck.setReviewStatus(2);
            authenticationCheck.setReviewReason(null);
            authenticationCheckRepository.save(authenticationCheck);
            return ResultData.ok("修改信息成功");
        }catch (Exception e){
            System.out.println(e);
            return ResultData.fail("访问数据库出错");
        }
    }

    @Override
    public ResultData followUser(Integer followUserId, Integer sessionUserId) {
        FollowUser followUser = new FollowUser();
        FollowUser.FollowUserKeys keys = new FollowUser.FollowUserKeys();
        keys.setUserId(sessionUserId);
        keys.setFollowedId(followUserId);
        followUser.setIds(keys);
        try{
            followUserRepository.save(followUser);
            Optional<UserInfo> userOrNULL = userInfoRepository.findById(sessionUserId);
            Optional<UserInfo> followedUserOrNULL = userInfoRepository.findById(followUserId);
            if(userOrNULL.isEmpty() || followedUserOrNULL.isEmpty())
                return ResultData.fail("用户或被关注的用户不存在");
            // 关注别人的人
            UserInfo user = userOrNULL.get();
            // 被关注的人
            UserInfo followedUser = followedUserOrNULL.get();
            // 关注别人的人关注数量加一
            user.setFollowNumber(user.getFollowNumber()+1);
            // 被关注的人粉丝数量加一
            followedUser.setFanNumber(followedUser.getFanNumber()+1);
            userInfoRepository.save(user);
            userInfoRepository.save(followedUser);
            return ResultData.ok();
        }
        catch (Exception e){
            System.out.println(e);
            return ResultData.fail("数据库操作失败");
        }
    }

    @Override
    @Transactional
    public ResultData unfollowUser(Integer followUserId, Integer sessionUserId) {
        FollowUser.FollowUserKeys keys = new FollowUser.FollowUserKeys();
        keys.setUserId(sessionUserId);
        keys.setFollowedId(followUserId);
        try{
            followUserRepository.deleteById(keys);
            Optional<UserInfo> userOrNULL = userInfoRepository.findById(sessionUserId);
            Optional<UserInfo> followedUserOrNULL = userInfoRepository.findById(followUserId);
            if(userOrNULL.isEmpty() || followedUserOrNULL.isEmpty())
                return ResultData.fail("用户或被关注的用户不存在");
            // 关注别人的人
            UserInfo user = userOrNULL.get();
            // 被关注的人
            UserInfo followedUser = followedUserOrNULL.get();
            // 关注别人的人关注数量加一
            user.setFollowNumber(user.getFollowNumber()-1);
            // 被关注的人粉丝数量加一
            followedUser.setFanNumber(followedUser.getFanNumber()-1);
            userInfoRepository.save(user);
            userInfoRepository.save(followedUser);
            return ResultData.ok();
        }
        catch (Exception e){
            System.out.println(e);
            return ResultData.fail("数据库操作失败");
        }
    }

    @Override
    public ResultData fetchUserPosts(Integer userId) {
        Map<String,Object> results = new HashMap<>();
        try{
            List<Map<String,Object>> posts_list = new ArrayList<>();
            List<MainPostInfo> mainPostInfos = postRepository.findMainPostInfo(userId);
            if(mainPostInfos == null)
                return ResultData.fail("该用户没有发帖子");
            for(MainPostInfo mainPostInfo:mainPostInfos){
                Map<String,Object> result = new HashMap<>();
                result.put("author_name",mainPostInfo.getAuthor_name());
                result.put("author_portrait",mainPostInfo.getAuthor_portrait());
                result.put("post_id",mainPostInfo.getPost_id());
                result.put("post_title",mainPostInfo.getPost_title());
                List<String> displayNames = postTagRepository.findPostAllDisplayName(
                        mainPostInfo.getPost_id()
                );
                result.put("post_tag",displayNames);
                // 可能会没有评论
                List<Integer> commentIds = commentRepository.findCommentIds(
                        mainPostInfo.getPost_id()
                );
                if(commentIds.isEmpty())
                    result.put("post_first_comment_id",null);
                else { // 评论不空的时候
                    Map<String,Object> rewards = new HashMap<>();
                    result.put("post_first_comment_id",commentIds.get(0));

                    CommentLikeAndCoinInfo likeInfo = new CommentLikeAndCoinInfo();
                    Integer likeNum = userRewardCommentRepository.findLikeNum(
                            commentIds.get(0)
                    );
                    likeInfo.setStatus(userId != -1 && likeNum != 0);
                    likeInfo.setNum(likeNum);
                    rewards.put("like",likeInfo);

                    CommentLikeAndCoinInfo coinInfo = new CommentLikeAndCoinInfo();
                    Integer coinNum = userRewardCommentRepository.findCoinNum(
                            commentIds.get(0)
                    );
                    coinInfo.setStatus(userId != -1 && coinNum != 0);
                    coinInfo.setNum(coinNum);
                    rewards.put("coin",coinInfo);

                    result.put("reward",rewards);
                    String postUrl = findCommentPic(commentIds.get(0));
                    if(postUrl==null)
                        result.put("post_pic",mainPostInfo.getAuthor_portrait());
                    else
                        result.put("post_pic",postUrl);
                }
                posts_list.add(result);
            }
            results.put("post_list",posts_list);
            return ResultData.ok(results);
        }
        catch (Exception e){
            System.out.println(e);
            return ResultData.fail("数据库查询失败");
        }
    }

    @Override
    public ResultData report(Integer sessionId) {
        List<ReportInfo> reportInfos = commentReportRepository.findReportInfo(sessionId);
        List<ReportReturnInfo> returnInfos = new ArrayList<>();
        for(ReportInfo reportInfo: reportInfos){
            ReportReturnInfo returnInfo = getReportReturnInfo(reportInfo);
            returnInfos.add(returnInfo);
        }
        Map<String,Object> result = new HashMap<>();
        result.put("reportList",returnInfos);
        return ResultData.ok(result);
    }

    private static ReportReturnInfo getReportReturnInfo(ReportInfo reportInfo) {
        ReportReturnInfo returnInfo = new ReportReturnInfo();
        returnInfo.setReportId(reportInfo.getReportId());
        returnInfo.setReportTime(reportInfo.getReportTime().toString().replace("T"," "));
        returnInfo.setReportStatus(reportInfo.getReportStatus());
        returnInfo.setPostId(reportInfo.getPostId());
        returnInfo.setFloorNumber(reportInfo.getFloorNumber());
        returnInfo.setReportRespondTime(reportInfo.getReportRespondTime().toString().replace("T"," "));
        return returnInfo;
    }

    private String findCommentPic(Integer commentId){
        try{
            Optional<Comment> commentOrNULL = commentRepository.findById(commentId);
            if(commentOrNULL.isEmpty()){
                return null;
            }
            Comment comment = commentOrNULL.get();
            String content = comment.getContent();

            JSONObject jsonObject = JSON.parseObject(content);
            // 获取 content 数组
            JSONArray contentArray = jsonObject.getJSONArray("content");
            // 遍历 content 数组
            for (int i = 0; i < contentArray.size(); i++) {
                JSONObject contentObject = contentArray.getJSONObject(i);
                // 如果 type 是 image，获取 attrs 对象
                if ("image".equals(contentObject.getString("type"))) {
                    JSONObject attrsObject = contentObject.getJSONObject("attrs");
                    // 获取 src 属性
                    return attrsObject.getString("src");
                }
            }
            return null;
        }catch (Exception e){
            return null;
        }
    }

    private void setFollowListInfo(List<UserInfo> followUsers, List<DetailFanFollowInfo> infos){
        for(UserInfo followUser : followUsers){
            DetailFanFollowInfo info = new DetailFanFollowInfo();
            info.setUser_id(followUser.getUserId());
            info.setUser_name(followUser.getUserName());
            info.setAvatar_url(followUser.getPortait());
            info.setVerified(followUser.getIsApproved().equals("y"));
            info.setUser_group(info.isVerified()?"doctor":"normal");
            infos.add(info);
        }
    }

    private void setUserInformation(DetailUserInfo userInformation,UserInfo frontUser){
        userInformation.setUserID(frontUser.getUserId());
        userInformation.setCertified(frontUser.getIsApproved().equals("y"));
        userInformation.setNumOfCoin(frontUser.getHB_number());
        userInformation.setUserName(frontUser.getUserName());
        userInformation.setGender(frontUser.getGender().equals("m")?"男":"女");
        if(frontUser.getBirthDay()!=null)
            userInformation.setBirthday(frontUser.getBirthDay().toString());
        else
            userInformation.setBirthday(null);
        userInformation.setTelephone(frontUser.getPhoneNumber());
        userInformation.setEmail(frontUser.getEmail());
        userInformation.setDescription(frontUser.getSignature());
        userInformation.setAvatarUrl(frontUser.getPortait());
    }
}
