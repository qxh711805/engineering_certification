package com.binbinhan.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.binbinhan.common.utils.PageUtils;
import com.binbinhan.common.utils.Query;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.binbinhan.modules.sys.dao.SysDictDao;
import com.binbinhan.modules.sys.entity.SysDictEntity;
import com.binbinhan.modules.sys.service.SysDictService;

import java.util.Map;

/**
 * 数据字典表
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:35:58
 */
@Service("sysDictService")
public class SysDictServiceImpl extends ServiceImpl<SysDictDao, SysDictEntity> implements SysDictService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");

        IPage<SysDictEntity> page = this.page(
                new Query<SysDictEntity>().getPage(params),
                new QueryWrapper<SysDictEntity>()
                        .like(StringUtils.isNotBlank(name),"name", name)
        );

        return new PageUtils(page);
    }


}
