package com.binbinhan.modules.sys.controller;

import com.binbinhan.common.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.binbinhan.modules.sys.service.SysLogService;


/**
 * 系统日志
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:35:58
 */
@RestController
@RequestMapping("sys/log")
public class SysLogController extends AbstractController {
    @Autowired
    private SysLogService sysLogService;

}
