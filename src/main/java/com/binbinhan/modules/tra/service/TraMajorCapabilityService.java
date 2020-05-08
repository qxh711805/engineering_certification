package com.binbinhan.modules.tra.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.binbinhan.common.utils.PageUtils;
import com.binbinhan.modules.tra.entity.TraMajorCapabilityEntity;

import java.util.Map;


/**
 * 专业能力
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:39:25
 */
public interface TraMajorCapabilityService extends IService<TraMajorCapabilityEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

