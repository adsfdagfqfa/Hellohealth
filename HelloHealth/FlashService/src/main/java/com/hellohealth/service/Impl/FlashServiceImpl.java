package com.hellohealth.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hellohealth.entity.dto.TagGroupDTO;
import com.hellohealth.entity.po.Flash;
import com.hellohealth.entity.po.FlashTag;
import com.hellohealth.entity.po.FlashTagTrait;
import com.hellohealth.entity.po.FlashTagetGroup;
import com.hellohealth.entity.returnParam.Result;
import com.hellohealth.mapper.FlashTagMapper;
import com.hellohealth.mapper.FlashTagTraitMapper;
import com.hellohealth.mapper.FlashTagetGroupMapper;
import com.hellohealth.service.FlashService;
import com.hellohealth.mapper.FlashMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;


@Service
public class FlashServiceImpl implements FlashService {
    @Resource
    private FlashMapper flashMapper;

    @Override
    public List<Flash> getHotNews() {
        // 获取所有新闻
        List<Flash> allNews = flashMapper.selectList(null);

        // 如果新闻总数小于等于4，直接返回所有新闻
        if (allNews.size() <= 4) {
            return allNews;
        }

        // 随机选择4条新闻
        Random random = new Random();
        return random.ints(0, allNews.size())//[0, allNews.size()) 内的随机整数。
                .distinct()//通过这一步，确保生成的随机整数是唯一的，即去重。
                .limit(4)//限制最多4个
                .mapToObj(allNews::get)//将随机整数作为下标，从allNews中取出对应的新闻
                .collect(Collectors.toList());//将结果转换为List
    }


    /**
     * 按 ID 根据id获取 Flash
     *
     * @param id 编号
     * @return {@link Flash}
     */
    public Flash getFlashById(int id) {
        return flashMapper.selectById(id);
    }

    @Resource
    private FlashTagMapper flashTagMapper;
    @Resource
    private FlashTagetGroupMapper flashTagetGroupMapper;

    //返回所有的TagGroup
    public List<TagGroupDTO> getAllTagGroup() {
        List<FlashTagetGroup> allTagGroup = flashTagetGroupMapper.selectList(null);
        List<TagGroupDTO> tagGroupDTOS = new ArrayList<>();
        for (FlashTagetGroup flashTagetGroup : allTagGroup) {
            TagGroupDTO tagGroupDTO = new TagGroupDTO();
            tagGroupDTO.setGroup(flashTagetGroup);
            List<FlashTag> allTagByGroupId = flashTagMapper.selectList(new QueryWrapper<FlashTag>().eq("GROUP_ID", flashTagetGroup.getGroupId()));
            tagGroupDTO.setTags((ArrayList<FlashTag>) allTagByGroupId);
            tagGroupDTOS.add(tagGroupDTO);
        }
        return tagGroupDTOS;
    }


    public boolean deleteFlashById(int i) {
        boolean isDelete = true;
        int count = flashTagTraitMapper.delete(new QueryWrapper<FlashTagTrait>().eq("FLASH_ID", i));
        if (count <= 0) {
            isDelete = false;
        }
        int count1 = flashMapper.deleteById(i);
        if (count1 <= 0) {
            isDelete = false;
        }
        return isDelete;
    }

    public List<FlashTag> getAllChildTags() {
        return flashTagMapper.selectList(null);
    }

    @Resource
    private FlashTagTraitMapper flashTagTraitMapper;

    //返回指定tag的新闻
    public List<Flash> getTagNews(String id) {
        List<Flash> flashList = new ArrayList<>();
            QueryWrapper<FlashTagTrait> queryWrapper = new QueryWrapper<>();//构造查询条件
            queryWrapper.eq("TAG_ID", id);//查询条件
            List<FlashTagTrait> flashTagTraits = flashTagTraitMapper.selectList(queryWrapper);//查询结果
            // 从结果列表中提取FLASH_ID值
            List<Integer> flashIds = flashTagTraits.stream()//将结果列表转换为流
                    .map(FlashTagTrait::getFlashId)//提取FLASH_ID值
                    .toList();//将提取的FLASH_ID值转换为List
            for (Integer flashId : flashIds) {
                flashList.add(flashMapper.selectById(flashId));
            }
        return flashList;
    }

    public Result addFlash(Flash flash, ArrayList<String> tags) {
        if (flashMapper.insert(flash) == 1) {
            for (String tag : tags) {
                FlashTagTrait flashTagTrait = new FlashTagTrait();
//                flashTagTrait.setFlashId(flash.getFlashId());
                flashTagTrait.setFlashId(flashMapper.selectOne(new QueryWrapper<Flash>().eq("FLASH_TITLE", flash.getFlashTitle())).getFlashId());
                flashTagTrait.setTagId(Integer.parseInt(tag));
                flashTagTraitMapper.insert(flashTagTrait);
            }
            return new Result().isSuccess();
        } else {
            return new Result().isFail("添加失败");
        }
    }

    public Result modifyFlash(Flash flash, ArrayList<String> tags) {
        if (flashMapper.updateById(flash) == 1) {
            flashTagTraitMapper.delete(new QueryWrapper<FlashTagTrait>().eq("FLASH_ID", flash.getFlashId()));
            for (String tag : tags) {
                FlashTagTrait flashTagTrait = new FlashTagTrait();
                flashTagTrait.setFlashId(flashMapper.selectOne(new QueryWrapper<Flash>().eq("FLASH_TITLE", flash.getFlashTitle())).getFlashId());
                flashTagTrait.setTagId(Integer.parseInt(tag));
                flashTagTraitMapper.insert(flashTagTrait);
            }
            return new Result().isSuccess();
        } else {
            return new Result().isFail("修改失败");
        }
    }

    public List<Flash> getAllFlash() {
        return flashMapper.selectList(null);
    }

    public List<Flash> getFlashListByAdminId(int i) {
        return flashMapper.selectList(new QueryWrapper<Flash>().eq("ADMIN_ID", i));
    }
}

