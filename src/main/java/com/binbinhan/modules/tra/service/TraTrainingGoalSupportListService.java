package com.binbinhan.modules.tra.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.binbinhan.common.utils.PageUtils;
import com.binbinhan.modules.tra.entity.TraTrainingGoalSupportListEntity;

import java.util.Map;


/**
 * 培养目标支撑清单
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:39:25
 */
public interface TraTrainingGoalSupportListService extends IService<TraTrainingGoalSupportListEntity>, TraExcelService<TraTrainingGoalSupportListEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

