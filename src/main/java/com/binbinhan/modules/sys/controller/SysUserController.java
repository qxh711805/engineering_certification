package com.binbinhan.modules.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.binbinhan.common.annotation.SysLog;
import com.binbinhan.common.controller.AbstractController;
import com.binbinhan.common.controller.R;
import com.binbinhan.common.excel.ExcelUtils;
import com.binbinhan.common.excel.ImportExcel;
import com.binbinhan.common.utils.PageUtils;
import com.binbinhan.common.validator.Assert;
import com.binbinhan.modules.sys.entity.ClassEntity;
import com.binbinhan.modules.sys.entity.SysUserEntity;
import com.binbinhan.modules.sys.service.SysUserRoleService;
import com.binbinhan.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.binbinhan.modules.sys.service.SysUserService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 系统用户
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:35:57
 */
@RestController
@RequestMapping("sys/user")
public class SysUserController extends AbstractController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /**
     * 所有用户列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:user:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = sysUserService.queryPage(params);

        return R.ok().put("page", page);
    }

    /**
     * 获取登录的用户信息
     */
    @RequestMapping("/info")
    public R info() {
        return R.ok().put("user", getUser());
    }

    /**
     * 修改登录用户密码
     */
    @SysLog("修改密码")
    @RequestMapping("/password")
    public R password(String password, String newPassword) {
        Assert.isBlank(newPassword, "新密码不为能空");

        //原密码
        password = ShiroUtils.sha256(password, getUser().getSalt());
        //新密码
        newPassword = ShiroUtils.sha256(newPassword, getUser().getSalt());

        //更新密码
        boolean flag = sysUserService.updatePassword(getUserId(), password, newPassword);
        if (!flag) {
            return R.error("原密码不正确");
        }

        return R.ok();
    }

    /**
     * 用户信息
     */
    @RequestMapping("/info/{userId}")
    @RequiresPermissions("sys:user:info")
    public R info(@PathVariable("userId") Long userId) {
        SysUserEntity user = sysUserService.getById(userId);

        //获取用户所属的角色列表
        List<Long> roleIdList = sysUserRoleService.queryRoleIdList(userId);
        user.setRoleIdList(roleIdList);

        return R.ok().put("user", user);
    }

    /**
     * 保存用户
     */
    @SysLog("保存用户")
    @RequestMapping("/save")
    @RequiresPermissions("sys:user:save")
    public R save(@RequestBody SysUserEntity user) {
        if (user.getUsername() == null || "".equals(user.getUsername())) {
            user.setUsername(user.getJobNumber());
        }
//        ValidatorUtils.validateEntity(user, AddGroup.class);
        sysUserService.saveUser(user);
        return R.ok();
    }

    /**
     * 删除用户角色
     */
    @SysLog("保存用户")
    @RequestMapping("/deleteRole")
    @RequiresPermissions("sys:user:update")
    public R deleteRole(@RequestBody SysUserEntity user) {
        if (user.getUserId() == null) {
            R.error("用户选择错误！");
        }
        sysUserService.saveUser(user);
        return R.ok();
    }

    /**
     * 添加用户角色
     */
    @SysLog("保存用户")
    @RequestMapping("/addRole")
    public R addRole(@RequestBody SysUserEntity user) {
        if (user.getUserId() == null) {
            R.error("用户选择错误！");
        }
//        sysUserService.saveUser(user);
        return R.ok();
    }

    /**
     * 修改用户
     */
    @SysLog("修改用户")
    @RequestMapping("/update")
    @RequiresPermissions("sys:user:update")
    public R update(@RequestBody SysUserEntity user) {
        if (user.getUsername() == null || "".equals(user.getUsername())) {
            user.setUsername(user.getJobNumber());
        }
//        ValidatorUtils.validateEntity(user, UpdateGroup.class);

        sysUserService.update(user);

        return R.ok();
    }

    /**
     * 删除用户
     */
    @SysLog("删除用户")
    @RequestMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public R delete(@RequestBody Long[] userIds) {
        if (ArrayUtils.contains(userIds, 1L)) {
            return R.error("系统管理员不能删除");
        }

        if (ArrayUtils.contains(userIds, getUserId())) {
            return R.error("当前用户不能删除");
        }

        sysUserService.removeByIds(Arrays.asList(userIds));

        return R.ok();
    }

    /**
     * 下载导入模板
     *
     * @param req
     * @param response
     */
    @RequestMapping(value = "getFile")
    public void getFile(String n, String p, HttpServletRequest req, HttpServletResponse response) {
        try {
            n = "教学管理导入模板.xls";
            p = SysUserController.class.getResource("/").toURI().getPath() + "excelTemplate/userTemplate.xls";
            InputStream in = new FileInputStream(new File(p));
            HSSFWorkbook workbook = new HSSFWorkbook(in);
            HSSFSheet sheet = workbook.getSheetAt(0);
            // 写入文件
            String tempPath = SysUserController.class.getResource("/").toURI().getPath() + "excelTemplate/userTemplateTemp.xls";
            FileOutputStream fileOut = new FileOutputStream(tempPath);
            workbook.write(fileOut);
            File file = new File(tempPath);
            //实现下载
            in = new FileInputStream(file);
            n = new String(n.getBytes(), "ISO-8859-1");
            response.setContentType("application/x-msdownload;");
            response.setHeader("Content-Disposition", "attachment; filename=" + n);
            response.setContentType("application/octet-stream");

            BufferedOutputStream bout = new BufferedOutputStream(response.getOutputStream());
            BufferedInputStream bin = new BufferedInputStream(in);

            int len = 0;
            while ((len = bin.read()) != -1) {
                bout.write(len);
                bout.flush();
            }
            bout.close();
            bin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Excel文件上传
     *
     * @param uFile
     * @param fileId
     * @param request
     * @param model
     * @param response
     * @return
     */
    @RequestMapping(value = "fileUpload")
    @ResponseBody
    public Object fileUpload(@RequestParam(value = "file", required = false) MultipartFile uFile, String fileId,
                             HttpServletRequest request, Model model, HttpServletResponse response) {

        Map<String, Object> map = new HashMap<String, Object>();
        if (uFile.getSize() > 0) {
            String file_name = "";
            try {
                ImportExcel studentImportExcel = new ImportExcel(uFile, 1, 2);
                ImportExcel teacherImportExcel = new ImportExcel(uFile, 1, 0);
                ImportExcel classImportExcel = new ImportExcel(uFile, 1, 1);

                List<SysUserEntity> teacherList = teacherImportExcel.getUserDataList(null);
                List<ClassEntity> classEntityList = classImportExcel.getClassDataList();
                Map<String, ClassEntity> classEntityMap = classEntityList.stream().collect(Collectors.toMap(ClassEntity::getClassName, c -> c));
                List<SysUserEntity> studentList = studentImportExcel.getUserDataList(classEntityMap);
                String error = "";
                String e ="";
                if (teacherList != null  && teacherList.size()>0) {
                    e = analysisImport(teacherList);
                    if (StringUtils.isNotBlank(e)) {
                        error+="教师名册：\n"+e;
                    }
                }
                if (studentList != null && studentList.size()>0) {
                    e = analysisImport(studentList);
                    if (StringUtils.isNotBlank(e)) {
                        error+="学生名册：\n"+e;
                    }
                }
                e = ExcelUtils.analysisImportFile(teacherImportExcel, "教师名册", "姓名,身份证号,人员工号,工作单位,", 4);
                if (StringUtils.isNotBlank(e)) {
                    error = e;
                }
                e = ExcelUtils.analysisImportFile(classImportExcel, "班级", "专业,班级名称,年级,开班时间,培养层次,学制,培养方案,学校,", 8);
                if (StringUtils.isNotBlank(e)) {
                    error = e;
                }
                e = ExcelUtils.analysisImportFile(studentImportExcel, "学生名册", "班级名称,学号,学生姓名,学校,", 4);
                if (StringUtils.isNotBlank(e)) {
                    error = e;
                }
                if (StringUtils.isNotBlank(error)) {
                    map.put("isSave", false);
                } else {
                    error = "文件上传成功，点击【确定】保存！";
                    Map<String,List<SysUserEntity>> userMap = new HashMap<>();
                    userMap.put("teacherList",teacherList);
                    userMap.put("studentList",studentList);
                    request.getSession().setAttribute("deviceInfo_in_session", userMap);
                    map.put("isSave", true);
                }
                map.put("error", error);
                map.put("fileName", file_name);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("fileName", file_name);
                map.put("error", "上传文件错误,请按照要求编辑Excel");
            }

        }
        return map;
    }

    /**
     * 保存导入的设备信息
     */
    @RequestMapping(value = "/saveImport/{sessionName}")
    @ResponseBody
    public R saveImport(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("sessionName") String sessionName) {

        @SuppressWarnings("unchecked")
        Map<String,List<SysUserEntity>>  userMap = (Map<String,List<SysUserEntity>> ) request.getSession().getAttribute(sessionName);


        if (userMap == null) {
            return R.error(-1, "请重新选择文件!");
        }
        try {
            sysUserService.saveImport(userMap);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error(-1, e.getMessage());
        }
        //所有内容保存完毕后移除sesion中实例集合
        request.getSession().removeAttribute(sessionName);
        return R.ok("保存导入的设备成功");

    }

    /**
     * 上传数据校验
     * @param list
     * @return
     */
    String analysisImport(List<SysUserEntity> list) {
        List<String> usernames = list.stream().map(SysUserEntity::getUsername).collect(Collectors.toList());
        QueryWrapper<SysUserEntity> deviceWrapper = new QueryWrapper<>();
        deviceWrapper.in("username", usernames);
        List<SysUserEntity> userEntityList = sysUserService.list(deviceWrapper);
        List<String> usernameList = userEntityList.stream().map(SysUserEntity::getUsername).collect(Collectors.toList());
        int i = 1;
        String error = "";
        for (SysUserEntity s : list) {
            if (usernameList.contains(s.getUsername())) {
                error += "第" + i + "行设备标识符IMEI重复！\n";
            }
            i++;
        }
        return error;
    }
}
