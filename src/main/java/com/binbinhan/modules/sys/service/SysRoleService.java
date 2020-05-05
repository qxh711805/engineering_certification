package com.binbinhan.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.binbinhan.common.utils.PageUtils;
import com.binbinhan.modules.sys.entity.SysRoleEntity;

import java.util.List;
import java.util.Map;


/**
 * 角色
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:35:57
 */
public interface SysRoleService extends IService<SysRoleEntity> {
    PageUtils queryPage(Map<String, Object> params);

    void saveRole(SysRoleEntity role);

    void update(SysRoleEntity role);

    void deleteBatch(Long[] roleIds);
}

