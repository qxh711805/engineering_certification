package com.binbinhan.modules.sys.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.binbinhan.modules.sys.entity.SysUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 系统用户
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:35:57
 */
@Mapper
public interface SysUserDao extends BaseMapper<SysUserEntity> {
    /**
     * 查询用户的所有权限
     * @param userId  用户ID
     */
    List<String> queryAllPerms(Long userId);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(Long userId,Long roleId);

    List<SysUserEntity> queryPage(IPage<SysUserEntity> page, Long loginUserId,String keyword);
}
