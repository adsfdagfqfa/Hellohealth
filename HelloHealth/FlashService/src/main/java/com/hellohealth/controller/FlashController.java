package com.hellohealth.controller;

import com.hellohealth.entity.dto.TagGroupDTO;
import com.hellohealth.entity.po.FlashTag;
import com.hellohealth.entity.request.ModifyFlashRequest;
import com.hellohealth.service.FlashService;
import com.hellohealth.service.Impl.FlashServiceImpl;
import com.hellohealth.entity.po.Flash;
import com.hellohealth.entity.returnParam.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

@RequestMapping("/api/v1/flash")
@RestController
public class FlashController {
    @Autowired
    private FlashServiceImpl flashService;

    /**
     * 获取热点新闻
     *
     * @return {@link Result}<{@link List}<{@link Flash}>>
     */
    @GetMapping("/hotNews")
    public Result<List<Flash>> getHotNews() {
        List<Flash> hotNews = flashService.getHotNews();
        if(hotNews.isEmpty()) {
            return new Result<List<Flash>>().isFail("热搜新闻列表为空");
        }
        else{
            return new Result<List<Flash>>().isSuccess(hotNews);
        }
    }

    /**
     * 按 ID 获取 Flash
     *
     * @param flash_id 新闻 ID
     * @return {@link Result}<{@link Flash}>
     */
    @GetMapping("/{flash_id}")
    public Result<Flash> getFlashById(@PathVariable("flash_id") String flash_id) {
        if(flash_id==null){
            return new Result<Flash>().isFail("新闻id不能为空");
        }
        try {
            Integer.parseInt(flash_id);
        } catch (NumberFormatException e) {
            return new Result<Flash>().isFail("id必须为数字");
        }

        Flash flash = flashService.getFlashById(Integer.parseInt(flash_id));
        if(flash == null) {
            return new Result<Flash>().isFail("新闻不存在");
        }
        else{
            return new Result<Flash>().isSuccess(flash);
        }
    }

    /**
     * 按 ID 删除新闻
     *
     * @param flash_id 新闻 ID
     * @return {@link Result}
     */
    @DeleteMapping("/{flash_id}")
    public Result deleteFlashById(@PathVariable("flash_id") String flash_id) {
        if(flash_id==null){
            return new Result().isFail("id不能为空");
        }
        try {
            Integer.parseInt(flash_id);
        } catch (NumberFormatException e) {
            return new Result().isFail("id必须为数字");
        }

        if(flashService.getFlashById(Integer.parseInt(flash_id))==null){
            return new Result().isFail("问题不存在");
        }
        else{
            if(flashService.deleteFlashById(Integer.parseInt(flash_id))){
                return new Result().isSuccess();
            }
            else{
                return new Result().isFail("删除失败");
            }
        }
    }

    /**
     * 获取标签列表
     *
     * @return {@link Result}<{@link List}<{@link TagGroupDTO}>>
     */
    @GetMapping("/tagList")
    public Result<List<TagGroupDTO>> getTagList() {
        List<TagGroupDTO>  tagList = flashService.getAllTagGroup();
        if(tagList.isEmpty()) {
            return new Result<List<TagGroupDTO>>().isFail("热搜新闻列表为空");
        }
        else{
            return new Result<List<TagGroupDTO>>().isSuccess(tagList);
        }
    }

    /**
     * 获取所有子标签
     *
     * @return {@link Result}<{@link List}<{@link FlashTag}>>
     */
    @GetMapping("/childTags")
    public Result<List<FlashTag>> getAllChildTags() {
        List<FlashTag> childTags = flashService.getAllChildTags();
        if(childTags.isEmpty()) {
            return new Result<List<FlashTag>>().isFail("子标签列表为空");
        }
        else{
            return new Result<List<FlashTag>>().isSuccess(childTags);
        }
    }

    /**
     * 按 ID 修改新闻
     *
     * @param modifyFlashRequest 修改新闻请求
     * @return {@link Result}
     */
    @PutMapping()
    public Result ModifyFlashById(@RequestBody ModifyFlashRequest modifyFlashRequest) {
        Flash flash = new Flash();
        flash.setFlashContent(modifyFlashRequest.getContent());
        flash.setFlashTitle(modifyFlashRequest.getTitle());
        //将图片设成由content中http开头,的第一个图片
        String content = modifyFlashRequest.getContent();
        int start = content.indexOf("http");
        int end = content.indexOf("\"",start);
        flash.setFlashImage(content.substring(start,end));

        //将flash的time设置为当前系统时间
        flash.setFlashTime(new java.sql.Date(new Date().getTime()));
        flash.setAdminId(41);//TODO:这里的admin_id需要从登录子系统获取
        ArrayList<String> tags = modifyFlashRequest.getTags();
        //如果id为-1，说明是新建的flash，否则是修改已有的flash
        if(modifyFlashRequest.getFlash_being_edited_id()==-1){
            //flashId在数据库中是自增的，所以这里不需要设置
            return flashService.addFlash(flash,tags);
        }
        else{
            flash.setFlashId(modifyFlashRequest.getFlash_being_edited_id());
            return flashService.modifyFlash(flash,tags);
        }

    }


    /**
     * 通过标签获取新闻
     *
     * @param tagId 标签ID
     * @return {@link Result}<{@link List}<{@link Flash}>>
     */
    @GetMapping( "/newsByTag/{tag_id}")
    public Result<List<Flash>> getNewsByTag(@PathVariable("tag_id") String tagId) {
        if(tagId==null){
            return new Result<List<Flash>>().isFail("id不能为空");
        }
        try {
            Integer.parseInt(tagId);//判断id是否为数字
        } catch (NumberFormatException e) {
            return new Result<List<Flash>>().isFail("id必须为数字");
        }

        List<Flash> tagNews = flashService.getTagNews(tagId);
        if(tagNews.isEmpty()) {
            return new Result<List<Flash>>().isFail("新闻列表为空");
        }
        else{
            return new Result<List<Flash>>().isSuccess(tagNews);
        }
    }

    @GetMapping()
    public Result<List<Flash>> getFlashList() {
        List<Flash> flashList = flashService.getAllFlash();
        if(flashList.isEmpty()) {
            return new Result<List<Flash>>().isFail("新闻列表为空");
        }
        else{
            return new Result<List<Flash>>().isSuccess(flashList);
        }
    }

    @GetMapping("/admin/{admin_id}")
    public Result<List<Flash>> getFlashListByAdminId(@PathVariable("admin_id") String admin_id) {
        if(admin_id==null){
            return new Result<List<Flash>>().isFail("id不能为空");
        }
        try {
            Integer.parseInt(admin_id);//判断id是否为数字
        } catch (NumberFormatException e) {
            return new Result<List<Flash>>().isFail("id必须为数字");
        }

        List<Flash> flashList = flashService.getFlashListByAdminId(Integer.parseInt(admin_id));
        if(flashList.isEmpty()) {
            return new Result<List<Flash>>().isFail("新闻列表为空");
        }
        else{
            return new Result<List<Flash>>().isSuccess(flashList);
        }
    }



}


