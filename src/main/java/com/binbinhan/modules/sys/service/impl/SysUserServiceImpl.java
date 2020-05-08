package com.binbinhan.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.binbinhan.common.annotation.DataFilter;
import com.binbinhan.common.exception.RRException;
import com.binbinhan.common.utils.Constant;
import com.binbinhan.common.utils.PageUtils;
import com.binbinhan.common.utils.Query;
import com.binbinhan.modules.sys.entity.SysRoleEntity;
import com.binbinhan.modules.sys.entity.SysUserRoleEntity;
import com.binbinhan.modules.sys.service.SysRoleService;
import com.binbinhan.modules.sys.service.SysUserRoleService;
import com.binbinhan.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.binbinhan.modules.sys.dao.SysUserDao;
import com.binbinhan.modules.sys.entity.SysUserEntity;
import com.binbinhan.modules.sys.service.SysUserService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统用户
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:35:57
 */
@Service("sysUserService")
public class SysUserServiceImpl extends ServiceImpl<SysUserDao, SysUserEntity> implements SysUserService {

    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public List<Long> queryAllMenuId(Long userId, Long roleId) {
        return baseMapper.queryAllMenuId(userId, roleId);
    }

    @Override
    @DataFilter(subDept = true, user = false)
    public PageUtils queryPage(Map<String, Object> params) {
        String keyword = (String) params.get("keyword");
        IPage<SysUserEntity> page = new Query<SysUserEntity>().getPage(params);
        List<SysUserEntity> list = baseMapper.queryPage(page, ShiroUtils.getUserId(), keyword);
        page.setRecords(list);
//        IPage<SysUserEntity> page = this.page(
//                new Query<SysUserEntity>().getPage(params),
//                new QueryWrapper<SysUserEntity>()
//                        .ne("user_id", Constant.SUPER_ADMIN)
//                        .like(StringUtils.isNotBlank(keyword), "username", keyword)
//                        .apply(params.get(Constant.SQL_FILTER) != null, (String) params.get(Constant.SQL_FILTER))
//        );
//
//        Map<Long, Long> userRoleMap = sysUserRoleService.list().stream().collect(Collectors.toMap(SysUserRoleEntity::getUserId, SysUserRoleEntity::getRoleId));
//        Map<Long, SysRoleEntity> sysRoleEntityMap = sysRoleService.list().stream().collect(Collectors.toMap(SysRoleEntity::getRoleId, r -> r));
//
//        for (SysUserEntity sysUserEntity : page.getRecords()) {
//            Long userRoleId = userRoleMap.get(sysUserEntity.getUserId());
//            if (userRoleId != null) {
//                sysUserEntity.setRoleName(sysRoleEntityMap.get(userRoleId).getRoleName());
//            }
//        }

        return new PageUtils(page);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(SysUserEntity user) {
        user.setCreateTime(new Date());
        //sha256加密
        String salt = RandomStringUtils.randomAlphanumeric(20);
        user.setSalt(salt);
        user.setPassword(ShiroUtils.sha256("123456", user.getSalt()));
        this.save(user);

        //保存用户与角色关系
//        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysUserEntity user) {
        if (StringUtils.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {
            SysUserEntity userEntity = this.getById(user.getUserId());
            user.setPassword(ShiroUtils.sha256(user.getPassword(), userEntity.getSalt()));
        }
        this.updateById(user);

        //保存用户与角色关系
//        sysUserRoleService.saveOrUpdate(user.getUserId(), user.getRoleIdList());
    }


    @Override
    public boolean updatePassword(Long userId, String password, String newPassword) {
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setPassword(newPassword);
        return this.update(userEntity,
                new QueryWrapper<SysUserEntity>().eq("user_id", userId).eq("password", password));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveImport(Map<String, List<SysUserEntity>> userMap) {
        String msg = "";
        List<SysUserEntity> teacherList = userMap.get("teacherList");
        List<SysUserEntity> studentList = userMap.get("studentList");
        for (int i = 0; i < teacherList.size(); i++) {
            try {
                this.saveUser(teacherList.get(i));
            } catch (Exception e) {
                e.printStackTrace();
                msg += "教师名册：\n第" + (i + 1) + "条数据保存失败\n";
            }
        }
        for (int i = 0; i < studentList.size(); i++) {
            try {
                this.saveUser(studentList.get(i));
            } catch (Exception e) {
                e.printStackTrace();
                msg += "学生名册：\n第" + (i + 1) + "条数据保存失败\n";
            }
        }
        if (!"".equals(msg)) {
            throw new RRException(msg);
        }
    }
}
