package com.binbinhan.modules.sys.controller;

import java.util.*;

import com.binbinhan.common.annotation.SysLog;
import com.binbinhan.common.controller.AbstractController;
import com.binbinhan.common.controller.R;
import com.binbinhan.common.utils.PageUtils;
import com.binbinhan.common.validator.ValidatorUtils;
import com.binbinhan.modules.sys.entity.SysRoleEntity;
import com.binbinhan.modules.sys.service.SysRoleMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.binbinhan.modules.sys.service.SysRoleService;


/**
 * 角色
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:35:57
 */
@RestController
@RequestMapping("sys/role")
public class SysRoleController extends AbstractController {
    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /**
     * 角色列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:role:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sysRoleService.queryPage(params);

        return R.ok().put("page", page);
    }
    /**
     * 角色列表
     */
    @RequestMapping("/all/list")
    public R allList(@RequestParam Map<String, Object> params){
        List<SysRoleEntity> list = sysRoleService.list();
        return R.ok().put("list", list);
    }
    /**
     * 角色列表(select组件使用)
     */
    @RequestMapping("/login/select")
    public R loginSelect(){
        List<SysRoleEntity> list = sysRoleService.list();
        if (list == null || list.size() == 0){
            list = new ArrayList<>();
        }
        SysRoleEntity roleEntity = new SysRoleEntity();
        roleEntity.setRoleId(0l);
        roleEntity.setRoleName("请选择登录身份");
        list.add(roleEntity);
        Collections.sort(list, new Comparator<SysRoleEntity>() {
            @Override
            public int compare(SysRoleEntity o1, SysRoleEntity o2) {
                Integer l = (int)(o1.getRoleId() - o2.getRoleId());
                return l;
            }
        });
        return R.ok().put("roleList", list);
    }

    /**
     * 角色信息
     */
    @RequestMapping("/info/{roleId}")
    @RequiresPermissions("sys:role:info")
    public R info(@PathVariable("roleId") Long roleId){
        SysRoleEntity role = sysRoleService.getById(roleId);

        //查询角色对应的菜单
        List<Long> menuIdList = sysRoleMenuService.queryMenuIdList(roleId);
        role.setMenuIdList(menuIdList);


        return R.ok().put("role", role);
    }

    /**
     * 保存角色
     */
    @SysLog("保存角色")
    @RequestMapping("/save")
    @RequiresPermissions("sys:role:save")
    public R save(@RequestBody SysRoleEntity role){
        ValidatorUtils.validateEntity(role);

        sysRoleService.saveRole(role);

        return R.ok();
    }

    /**
     * 修改角色
     */
    @SysLog("修改角色")
    @RequestMapping("/update")
    @RequiresPermissions("sys:role:update")
    public R update(@RequestBody SysRoleEntity role){
        ValidatorUtils.validateEntity(role);

        sysRoleService.update(role);

        return R.ok();
    }

    /**
     * 删除角色
     */
    @SysLog("删除角色")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:role:delete")
    public R delete(@RequestBody Long[] roleIds){
        sysRoleService.deleteBatch(roleIds);

        return R.ok();
    }
}
