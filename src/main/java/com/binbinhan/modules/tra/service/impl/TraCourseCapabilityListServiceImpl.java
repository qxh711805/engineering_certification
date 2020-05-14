package com.binbinhan.modules.tra.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.binbinhan.common.exception.RRException;
import com.binbinhan.common.utils.PageUtils;
import com.binbinhan.common.utils.Query;
import com.binbinhan.modules.tra.entity.TraStandardCapabilityEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.binbinhan.modules.tra.dao.TraCourseCapabilityListDao;
import com.binbinhan.modules.tra.entity.TraCourseCapabilityListEntity;
import com.binbinhan.modules.tra.service.TraCourseCapabilityListService;

import java.util.List;
import java.util.Map;

/**
 * 课程能力清单
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:39:25
 */
@Service("traCourseCapabilityListService")
public class TraCourseCapabilityListServiceImpl extends ServiceImpl<TraCourseCapabilityListDao, TraCourseCapabilityListEntity> implements TraCourseCapabilityListService {


    @Override
    public void saveImport(List<TraCourseCapabilityListEntity> dataList) {
        String msg = "";
        for (int i = 0; i < dataList.size(); i++) {
            try {
                this.save(dataList.get(i));
            } catch (Exception e) {
                e.printStackTrace();
                msg += "\n第" + (i + 1) + "条数据保存失败\n";
            }
        }
        if (!"".equals(msg)) {
            throw new RRException(msg);
        }
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String keyword = (String) params.get("keyword");
        String trainingId = (String) params.get("trainingId");
        QueryWrapper<TraCourseCapabilityListEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(trainingId!=null,"training_id",trainingId);
        queryWrapper.like(StringUtils.isNotBlank(keyword),"course_name",keyword);
        IPage<TraCourseCapabilityListEntity> page = baseMapper.selectPage( new Query<TraCourseCapabilityListEntity>().getPage(params),queryWrapper);

        return new PageUtils(page);
    }
}
