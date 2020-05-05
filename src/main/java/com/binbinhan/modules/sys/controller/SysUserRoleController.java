package com.binbinhan.modules.sys.controller;

import com.binbinhan.common.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.binbinhan.modules.sys.service.SysUserRoleService;


/**
 * 用户与角色对应关系
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:35:58
 */
@RestController
@RequestMapping("sys/userrole")
public class SysUserRoleController extends AbstractController {
    @Autowired
    private SysUserRoleService sysUserRoleService;

}
