package com.binbinhan.modules.tra.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.binbinhan.common.utils.PageUtils;
import com.binbinhan.modules.tra.entity.TraCourseCapabilityListEntity;

import java.util.Map;


/**
 * 课程能力清单
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:39:25
 */
public interface TraCourseCapabilityListService extends IService<TraCourseCapabilityListEntity> , TraExcelService<TraCourseCapabilityListEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

