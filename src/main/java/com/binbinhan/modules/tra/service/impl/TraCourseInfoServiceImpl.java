package com.binbinhan.modules.tra.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.binbinhan.common.exception.RRException;
import com.binbinhan.common.utils.PageUtils;
import com.binbinhan.common.utils.Query;
import com.binbinhan.modules.tra.entity.TraCourseCapabilityListEntity;
import com.binbinhan.modules.tra.entity.TraCourseInfoEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.binbinhan.modules.tra.dao.TraCourseInfoDao;
import com.binbinhan.modules.tra.entity.TraCourseInfoEntity;
import com.binbinhan.modules.tra.service.TraCourseInfoService;

import java.util.List;
import java.util.Map;

/**
 * 课程信息
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:39:25
 */
@Service("traCourseInfoService")
public class TraCourseInfoServiceImpl extends ServiceImpl<TraCourseInfoDao, TraCourseInfoEntity> implements TraCourseInfoService {


    @Override
    public void saveImport(List<TraCourseInfoEntity> dataList) {
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
        QueryWrapper<TraCourseInfoEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(trainingId!=null,"training_id",trainingId);
        queryWrapper.like(StringUtils.isNotBlank(keyword),"course_name",keyword);
        IPage<TraCourseInfoEntity> page = baseMapper.selectPage( new Query<TraCourseInfoEntity>().getPage(params),queryWrapper);

        return new PageUtils(page);
    }
}
