package com.binbinhan.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.binbinhan.common.utils.Constant;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.binbinhan.modules.sys.dao.SysUserRoleDao;
import com.binbinhan.modules.sys.entity.SysUserRoleEntity;
import com.binbinhan.modules.sys.service.SysUserRoleService;

import java.util.List;

/**
 * 用户与角色对应关系
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:35:58
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRoleEntity> implements SysUserRoleService {

    @Override
    public void saveOrUpdate(Long userId, List<Long> roleIdList) {
        //先删除用户与角色关系
        this.remove(new QueryWrapper<SysUserRoleEntity>().eq("user_id", userId));

        if(roleIdList == null || roleIdList.size() == 0){
            return ;
        }

        //保存用户与角色关系
        for(Long roleId : roleIdList){
            SysUserRoleEntity sysUserRoleEntity = new SysUserRoleEntity();
            sysUserRoleEntity.setUserId(userId);
            sysUserRoleEntity.setRoleId(roleId);

            this.save(sysUserRoleEntity);
        }

    }

    @Override
    public List<Long> queryRoleIdList(Long userId) {
        List<Long> roleIdList = baseMapper.queryRoleIdList(userId);
        if (userId== Constant.SUPER_ADMIN){
            roleIdList.add(0l);
        }
        return roleIdList;
    }

    @Override
    public int deleteBatch(Long[] roleIds){
        return baseMapper.deleteBatch(roleIds);
    }

}
