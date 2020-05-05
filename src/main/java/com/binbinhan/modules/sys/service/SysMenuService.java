package com.binbinhan.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.binbinhan.modules.sys.entity.SysMenuEntity;
import com.binbinhan.modules.sys.entity.SysUserEntity;

import java.util.List;


/**
 * 菜单管理
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:35:58
 */
public interface SysMenuService extends IService<SysMenuEntity> {

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     * @param menuIdList  用户菜单ID
     */
    List<SysMenuEntity> queryListParentId(Long parentId, List<Long> menuIdList);

    /**
     * 根据父菜单，查询子菜单
     * @param parentId 父菜单ID
     */
    List<SysMenuEntity> queryListParentId(Long parentId);

    /**
     * 获取不包含按钮的菜单列表
     */
    List<SysMenuEntity> queryNotButtonList();

    /**
     * 获取用户菜单列表
     */
    List<SysMenuEntity> getUserMenuList(SysUserEntity userEntity);

    /**
     * 删除
     */
    void delete(Long menuId);
}

