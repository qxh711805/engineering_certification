package com.binbinhan.modules.sys.dao;

import com.binbinhan.modules.sys.entity.SysLogEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统日志
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:35:58
 */
@Mapper
public interface SysLogDao extends BaseMapper<SysLogEntity> {
	
}
