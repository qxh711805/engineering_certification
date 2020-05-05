package com.binbinhan.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.binbinhan.common.utils.PageUtils;
import com.binbinhan.modules.sys.entity.SysUserEntity;

import java.util.List;
import java.util.Map;


/**
 * 系统用户
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:35:57
 */
public interface SysUserService extends IService<SysUserEntity> {


    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId,Long roleId);

    /**
     * 保存用户
     */
    void saveUser(SysUserEntity user);

    /**
     * 修改用户
     */
    void update(SysUserEntity user);

    /**
     * 修改密码
     * @param userId       用户ID
     * @param password     原密码
     * @param newPassword  新密码
     */
    boolean updatePassword(Long userId, String password, String newPassword);
}

