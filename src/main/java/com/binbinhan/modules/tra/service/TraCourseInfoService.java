package com.binbinhan.modules.tra.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.binbinhan.common.utils.PageUtils;
import com.binbinhan.modules.tra.entity.TraCourseInfoEntity;

import java.util.Map;


/**
 * 课程信息
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:39:25
 */
public interface TraCourseInfoService extends IService<TraCourseInfoEntity>, TraExcelService<TraCourseInfoEntity>  {

    PageUtils queryPage(Map<String, Object> params);
}

