package com.binbinhan.modules.tra.controller;

import com.binbinhan.common.annotation.SysLog;
import com.binbinhan.common.controller.AbstractController;
import com.binbinhan.common.controller.R;
import com.binbinhan.common.excel.EasyExcelUtils;
import com.binbinhan.common.excel.ExcelUtils;
import com.binbinhan.common.excel.ImportExcel;
import com.binbinhan.common.utils.PageUtils;
import com.binbinhan.modules.sys.controller.SysUserController;
import com.binbinhan.modules.sys.entity.ClassEntity;
import com.binbinhan.modules.sys.entity.SysUserEntity;
import com.binbinhan.modules.tra.entity.FileUtil;
import com.binbinhan.modules.tra.entity.TraTrainingProgramEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.binbinhan.modules.tra.service.TraTrainingProgramService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 培养方案
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:39:25
 */
@RestController
@RequestMapping("tra/tratrainingprogram")
public class TraTrainingProgramController extends AbstractController {
    @Autowired
    private TraTrainingProgramService traTrainingProgramService;


    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = traTrainingProgramService.queryPage(params);
        System.out.println(page);
        return R.ok().put("page", page);
    }

    @RequestMapping(value = "delete/{trainingId}")
    public R delete(@PathVariable("trainingId") Long trainingId) {
        traTrainingProgramService.removeById(trainingId);
        return R.ok();
    }

    @SysLog("保存用户")
    @RequestMapping("/save")
//    @RequiresPermissions("sys:user:save")
    public R save(@RequestBody TraTrainingProgramEntity traTrainingProgramEntity) {
//        if (user.getUsername() == null || "".equals(user.getUsername())) {
//            user.setUsername(user.getJobNumber());
//        }
//        ValidatorUtils.validateEntity(user, AddGroup.class);
        traTrainingProgramService.save(traTrainingProgramEntity);
        return R.ok();
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public R update(@RequestBody TraTrainingProgramEntity traTrainingProgramEntity) {
        System.out.println(traTrainingProgramEntity);
        traTrainingProgramService.updateById(traTrainingProgramEntity);
        return R.ok();
    }

    @RequestMapping(value = "info/{trainingId}")
    public R getById(@PathVariable("trainingId") Long trainingId) {
        TraTrainingProgramEntity traTrainingProgramEntity = traTrainingProgramService.getById(trainingId);

        return R.ok().put("traTrainingProgramEntity", traTrainingProgramEntity);
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
            n = "培养方案导入模板.xls";
            p = TraTrainingProgramController.class.getResource("/").toURI().getPath() + "excelTemplate/trainTemplate.xls";
            InputStream in = new FileInputStream(new File(p));
            HSSFWorkbook workbook = new HSSFWorkbook(in);
            HSSFSheet sheet = workbook.getSheetAt(0);
            // 写入文件
            String tempPath = TraTrainingProgramController.class.getResource("/").toURI().getPath() + "excelTemplate/trainTemplateTemp.xls";
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
     * @return
     */

    @RequestMapping(value = "fileUpload")
    @ResponseBody
    public Object importExcel(@RequestParam("file") MultipartFile uFile, HttpServletRequest request) throws Exception {

        //解析excel，
//        List<TraTrainingProgramEntity> personList = FileUtil.importExcel(uFile, 0, 1, TraTrainingProgramEntity.class);
        List<TraTrainingProgramEntity> personList = EasyExcelUtils.importExcel(uFile, 0, 2, 0,false,TraTrainingProgramEntity.class);

        System.out.println(personList);
        String error = "";
        for (TraTrainingProgramEntity traTrainingProgramEntity : personList) {
            traTrainingProgramService.save(traTrainingProgramEntity);
        }
        Map map=new HashMap();
        error = "文件上传成功，点击【确定】保存！";
        map.put("error",error);
        return map;

/*
        Map<String, Object> map = new HashMap<String, Object>();
        if (uFile.getSize() > 0) {
            String file_name = "";
            try {
                //培养方案
                ImportExcel trainingProgramImportExcel = new ImportExcel(uFile, 1, 0);
                List<TraTrainingProgramEntity> trainingProgramImportDataList = trainingProgramImportExcel.getTrainingProgramImportDataList();
                //标准能力
                ImportExcel standardCapabilityExcel = new ImportExcel(uFile, 1, 1);
                standardCapabilityExcel.getStandardCapabilityDataList();
                //专业能力
                ImportExcel majorCapabilityImportExcel = new ImportExcel(uFile, 1, 2);
                //专业培养目标
                ImportExcel majorCapabilityTargetImportExcel = new ImportExcel(uFile, 1, 3);
                //培养目标支撑清单
                ImportExcel trainingGoalSupportListImportExcel = new ImportExcel(uFile, 1, 4);
                //课程
                ImportExcel courseInfoImportExcel = new ImportExcel(uFile, 1, 5);
                //课程能力清单
                ImportExcel courseCapabilityExcel = new ImportExcel(uFile, 1, 6);
                String error = "";
                String e ="";
//                if (teacherList != null  && teacherList.size()>0) {
//                    e = analysisImport(teacherList);
//                    if (StringUtils.isNotBlank(e)) {
//                        error+="教师名册：\n"+e;
//                    }
//                }
//                if (studentList != null && studentList.size()>0) {
//                    e = analysisImport(studentList);
//                    if (StringUtils.isNotBlank(e)) {
//                        error+="学生名册：\n"+e;
//                    }
//                }
//                e = ExcelUtils.analysisImportFile(teacherImportExcel, "教师名册", "姓名,身份证号,人员工号,工作单位,", 4);
//                if (StringUtils.isNotBlank(e)) {
//                    error = e;
//                }
//                e = ExcelUtils.analysisImportFile(classImportExcel, "班级", "专业,班级名称,年级,开班时间,培养层次,学制,培养方案,学校,", 8);
//                if (StringUtils.isNotBlank(e)) {
//                    error = e;
//                }
//                e = ExcelUtils.analysisImportFile(studentImportExcel, "学生名册", "班级名称,学号,学生姓名,学校,", 4);
//                if (StringUtils.isNotBlank(e)) {
//                    error = e;
//                }
//                if (StringUtils.isNotBlank(error)) {
//                    map.put("isSave", false);
//                } else {
//                    error = "文件上传成功，点击【确定】保存！";
//                    Map<String,List<SysUserEntity>> userMap = new HashMap<>();
//                    userMap.put("teacherList",teacherList);
//                    userMap.put("studentList",studentList);
//                    request.getSession().setAttribute("deviceInfo_in_session", userMap);
//                    map.put("isSave", true);
//                }
//                map.put("error", error);
//                map.put("fileName", file_name);
            } catch (Exception e) {
                e.printStackTrace();
                map.put("fileName", file_name);
                map.put("error", "上传文件错误,请按照要求编辑Excel");
            }

        }
        return map;*/
    }



//    @RequestMapping(value = "fileUpload")
//    @ResponseBody
//    public Object fileUpload(@RequestParam(value = "file", required = false) MultipartFile uFile, String fileId,
//                             HttpServletRequest request, Model model, HttpServletResponse response) {
//
//        Map<String, Object> map = new HashMap<String, Object>();
//        if (uFile.getSize() > 0) {
//            String file_name = "";
//            try {
//                ImportExcel studentImportExcel = new ImportExcel(uFile, 1, 2);
//                ImportExcel teacherImportExcel = new ImportExcel(uFile, 1, 0);
//                ImportExcel classImportExcel = new ImportExcel(uFile, 1, 1);
//
//                List<SysUserEntity> teacherList = teacherImportExcel.getUserDataList(null);
//                List<ClassEntity> classEntityList = classImportExcel.getClassDataList();
//                Map<String, ClassEntity> classEntityMap = classEntityList.stream().collect(Collectors.toMap(ClassEntity::getClassName, c -> c));
//                List<SysUserEntity> studentList = studentImportExcel.getUserDataList(classEntityMap);
//                String error = "";
//                String e ="";
//                if (teacherList != null  && teacherList.size()>0) {
//                    e = analysisImport(teacherList);
//                    if (StringUtils.isNotBlank(e)) {
//                        error+="教师名册：\n"+e;
//                    }
//                }
//                if (studentList != null && studentList.size()>0) {
//                    e = analysisImport(studentList);
//                    if (StringUtils.isNotBlank(e)) {
//                        error+="学生名册：\n"+e;
//                    }
//                }
//                e = analysisImportFile(teacherImportExcel, "教师名册", "姓名,身份证号,人员工号,工作单位,", 4);
//                if (StringUtils.isNotBlank(e)) {
//                    error = e;
//                }
//                e = analysisImportFile(classImportExcel, "班级", "专业,班级名称,年级,开班时间,培养层次,学制,培养方案,学校,", 8);
//                if (StringUtils.isNotBlank(e)) {
//                    error = e;
//                }
//                e = analysisImportFile(studentImportExcel, "学生名册", "班级名称,学号,学生姓名,学校,", 4);
//                if (StringUtils.isNotBlank(e)) {
//                    error = e;
//                }
//                if (StringUtils.isNotBlank(error)) {
//                    map.put("isSave", false);
//                } else {
//                    error = "文件上传成功，点击【确定】保存！";
//                    Map<String,List<SysUserEntity>> userMap = new HashMap<>();
//                    userMap.put("teacherList",teacherList);
//                    userMap.put("studentList",studentList);
//                    request.getSession().setAttribute("deviceInfo_in_session", userMap);
//                    map.put("isSave", true);
//                }
//                map.put("error", error);
//                map.put("fileName", file_name);
//            } catch (Exception e) {
//                e.printStackTrace();
//                map.put("fileName", file_name);
//                map.put("error", "上传文件错误,请按照要求编辑Excel");
//            }
//
//        }
//        return map;
//    }

        }
