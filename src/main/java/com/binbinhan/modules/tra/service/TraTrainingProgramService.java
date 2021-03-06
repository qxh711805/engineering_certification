package com.binbinhan.modules.tra.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.binbinhan.common.utils.PageUtils;
import com.binbinhan.modules.tra.entity.TraTrainingProgramEntity;

import java.util.Map;


/**
 * 培养方案
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:39:25
 */
public interface TraTrainingProgramService extends IService<TraTrainingProgramEntity> , TraExcelService<TraTrainingProgramEntity>{

    PageUtils queryPage(Map<String, Object> params);
}

