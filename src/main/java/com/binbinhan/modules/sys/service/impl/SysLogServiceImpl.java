package com.binbinhan.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.binbinhan.modules.sys.dao.SysLogDao;
import com.binbinhan.modules.sys.entity.SysLogEntity;
import com.binbinhan.modules.sys.service.SysLogService;

/**
 * 系统日志
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:35:58
 */
@Service("sysLogService")
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {


}
