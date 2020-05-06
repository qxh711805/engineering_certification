package com.binbinhan.modules.tra.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.binbinhan.common.utils.Constant;
import com.binbinhan.common.utils.PageUtils;
import com.binbinhan.common.utils.Query;
import com.binbinhan.modules.sys.entity.SysUserEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.binbinhan.modules.tra.dao.TraTrainingProgramDao;
import com.binbinhan.modules.tra.entity.TraTrainingProgramEntity;
import com.binbinhan.modules.tra.service.TraTrainingProgramService;

import java.util.Map;

/**
 * 培养方案
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:39:25
 */
@Service("traTrainingProgramService")
public class TraTrainingProgramServiceImpl extends ServiceImpl<TraTrainingProgramDao, TraTrainingProgramEntity> implements TraTrainingProgramService {


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TraTrainingProgramEntity> page = this.page(
                new Query<TraTrainingProgramEntity>().getPage(params),
                new QueryWrapper<TraTrainingProgramEntity>()
                        .apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
        );
        return new PageUtils(page);
    }
}
