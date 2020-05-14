package com.binbinhan.modules.tra.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.binbinhan.common.utils.PageUtils;
import com.binbinhan.modules.tra.entity.TraMajorCapabilityEntity;
import com.binbinhan.modules.tra.entity.TraStandardCapabilityEntity;

import java.util.Map;


/**
 * 标准能力
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:39:25
 */
public interface TraStandardCapabilityService extends IService<TraStandardCapabilityEntity> , TraExcelService<TraStandardCapabilityEntity>{

    PageUtils queryPage(Map<String, Object> params);
}

