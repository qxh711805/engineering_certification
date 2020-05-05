package com.binbinhan.modules.sys.controller;

import com.binbinhan.common.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.binbinhan.modules.sys.service.SysOssService;


/**
 * 文件上传
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:35:58
 */
@RestController
@RequestMapping("sys/oss")
public class SysOssController extends AbstractController {
    @Autowired
    private SysOssService sysOssService;

}
