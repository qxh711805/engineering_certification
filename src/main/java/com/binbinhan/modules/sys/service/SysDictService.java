package com.binbinhan.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.binbinhan.common.utils.PageUtils;
import com.binbinhan.modules.sys.entity.SysDictEntity;

import java.util.Map;


/**
 * 数据字典表
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:35:58
 */
public interface SysDictService extends IService<SysDictEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

