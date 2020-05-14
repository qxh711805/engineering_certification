package com.binbinhan.modules.tra.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.binbinhan.common.utils.PageUtils;
import com.binbinhan.modules.tra.entity.TraMajorCapabilityEntity;
import com.binbinhan.modules.tra.entity.TraMajorCapabilityTargetEntity;

import java.util.Map;


/**
 * 专业培养目标
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:39:25
 */
public interface TraMajorCapabilityTargetService extends IService<TraMajorCapabilityTargetEntity> , TraExcelService<TraMajorCapabilityTargetEntity>{

    PageUtils queryPage(Map<String, Object> params);
}

