package com.binbinhan.modules.tra.controller;

import com.binbinhan.common.controller.AbstractController;
import com.binbinhan.common.controller.R;
import com.binbinhan.common.excel.EasyExcelUtils;
import com.binbinhan.common.excel.ImportExcel;
import com.binbinhan.common.exception.RRException;
import com.binbinhan.modules.sys.entity.SysUserEntity;
import com.binbinhan.modules.tra.entity.*;
import com.binbinhan.modules.tra.service.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * 培养方案Excel操作
 *
 * @作者 binbinhan
 * @时间 2020-05-14 10:46
 */

@RestController
@RequestMapping("tra/excel")
public class TraExcelController extends AbstractController {
    //培养方案
    @Autowired
    private TraTrainingProgramService trainingProgramService;

    @Autowired
    private TraCourseCapabilityListService courseCapabilityListService;
    @Autowired
    private TraCourseInfoService courseInfoService;
    @Autowired
    private TraMajorCapabilityService majorCapabilityService;
    @Autowired
    private TraMajorCapabilityTargetService majorCapabilityTargetService;
    @Autowired
    private TraStandardCapabilityService standardCapabilityService;
    @Autowired
    private TraTrainingGoalSupportListService traTrainingGoalSupportListService;

    /**
     * 下载导入模板
     *
     * @param req
     * @param response
     */
    @RequestMapping(value = "getFile/{name}/{trainingId}", method = RequestMethod.GET)
    public void getFile(@PathVariable String name, @PathVariable String trainingId, String p, HttpServletRequest req, HttpServletResponse response) {
        try {
            p = TraTrainingProgramController.class.getResource("/").toURI().getPath() + "excelTemplate/trainTemplate.xls";
            InputStream in = new FileInputStream(new File(p));
            HSSFWorkbook workbook = new HSSFWorkbook(in);
            List<Integer> index = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                String sheetName = workbook.getSheetAt(i).getSheetName();
                if (!sheetName.equals(name)) {
                    index.add(i);
                }
            }
            if (!"培养方案".equals(name)) {
                TraTrainingProgramEntity trainingProgramEntity = trainingProgramService.getBaseMapper().selectById(trainingId);
                HSSFSheet sheet = workbook.getSheet(name);
                HSSFRow row = sheet.getRow(0);
                HSSFCell trainingName = row.getCell(1);
                trainingName.setCellValue(trainingProgramEntity.getTrainingProgram());
                HSSFCell trainingVersion = row.getCell(3);
                trainingVersion.setCellValue(trainingProgramEntity.getVersion());
            }
            for (int i = index.size() - 1; i >= 0; i--) {
                workbook.removeSheetAt(index.get(i));
            }
            name += "导入模板.xls";
            // 写入文件
            String tempPath = TraTrainingProgramController.class.getResource("/").toURI().getPath() + "excelTemplate/trainTemplateTemp.xls";
            FileOutputStream fileOut = new FileOutputStream(tempPath);
            workbook.write(fileOut);
            File file = new File(tempPath);
            //实现下载
            in = new FileInputStream(file);
            name = new String(name.getBytes(), "ISO-8859-1");
            response.setContentType("application/x-msdownload;");
            response.setHeader("Content-Disposition", "attachment; filename=" + name);
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
     * 点击取消或者关闭，移除已经保存在session当中的条线计划实例集合
     *
     * @param request
     */
    @RequestMapping(value = "removeSession/{sessionName}")
    public void removeSession(HttpServletRequest request, @PathVariable("sessionName") String sessionName) {
        request.getSession().removeAttribute(sessionName);
    }

    /**
     * Excel文件上传
     *
     * @return
     */

    @RequestMapping(value = "fileUpload/{trainingId}/{name}")
    @ResponseBody
    public Object importExcel(@PathVariable Long trainingId,@PathVariable String name,  @RequestParam("file") MultipartFile uFile, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            InputStream inputStream = uFile.getInputStream();
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            String sheetName = workbook.getSheetAt(0).getSheetName();
            if (!"培养方案".equals(sheetName)){
                analysisImport(uFile,trainingId,name);
            }
            switch (sheetName) {
                case "培养方案":
                    List<TraTrainingProgramEntity> trainingProgramList = importExcel(uFile, TraTrainingProgramEntity.class);
                    for (TraTrainingProgramEntity o : trainingProgramList) {
                        o.setCreateTime(new Date());
                    }
                    request.getSession().setAttribute("trainingProgram_info_in_session", trainingProgramList);
                    break;
                case "标准能力":
                    List<TraStandardCapabilityEntity> standardCapabilityList = importExcel(uFile, TraStandardCapabilityEntity.class);
                    for (TraStandardCapabilityEntity o : standardCapabilityList) {
                        o.setTrainingId(trainingId);
                        o.setCreateTime(new Date());
                    }
                    request.getSession().setAttribute("standardCapability_info_in_session", standardCapabilityList);
                    break;
                case "专业能力":
                    List<TraMajorCapabilityEntity> majorCapabilityList = importExcel(uFile, TraMajorCapabilityEntity.class);
                    for (TraMajorCapabilityEntity o : majorCapabilityList) {
                        o.setTrainingId(trainingId);
                        o.setCreateTime(new Date());
                    }
                    request.getSession().setAttribute("majorCapability_info_in_session", majorCapabilityList);
                    break;
                case "专业培养目标":
                    List<TraMajorCapabilityTargetEntity> majorCapabilityTargetList = importExcel(uFile, TraMajorCapabilityTargetEntity.class);
                    for (TraMajorCapabilityTargetEntity o : majorCapabilityTargetList) {
                        o.setTrainingId(trainingId);
                        o.setCreateTime(new Date());
                    }
                    request.getSession().setAttribute("majorCapabilityTarget_info_in_session", majorCapabilityTargetList);
                    break;
                case "培养目标支撑清单":
                    List<TraTrainingGoalSupportListEntity> trainingGoalSupportList = importExcel(uFile, TraTrainingGoalSupportListEntity.class);
                    for (TraTrainingGoalSupportListEntity o : trainingGoalSupportList) {
                        o.setTrainingId(trainingId);
                        o.setCreateTime(new Date());
                    }
                    request.getSession().setAttribute("trainingGoalSupport_info_in_session", trainingGoalSupportList);
                    break;
                case "课程":
                    List<TraCourseInfoEntity> courseInfoList = importExcel(uFile, TraCourseInfoEntity.class);
                    for (TraCourseInfoEntity o : courseInfoList) {
                        o.setTrainingId(trainingId);
                        o.setCreateTime(new Date());
                    }
                    request.getSession().setAttribute("courseInfo_info_in_session", courseInfoList);
                    break;
                case "课程能力清单":
                    List<TraCourseCapabilityListEntity> courseCapabilityList = importExcel(uFile, TraCourseCapabilityListEntity.class);
                    for (TraCourseCapabilityListEntity o : courseCapabilityList) {
                        o.setTrainingId(trainingId);
                        o.setCreateTime(new Date());
                    }
                    request.getSession().setAttribute("courseCapability_info_in_session", courseCapabilityList);
                    break;
            }
            map.put("error", "文件上传成功，点击【确定】保存！");
            map.put("isSave", true);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", e.getMessage());
            map.put("isSave", false);
        }
        map.put("fileName", "");
        return map;
    }

    /**
     * 保存导入的设备信息
     */
    @RequestMapping(value = "/saveImport/{sessionName}")
    @ResponseBody
    public R saveImport(HttpServletRequest request, HttpServletResponse response, Model model, @PathVariable("sessionName") String sessionName) {
        switch (sessionName) {
            case "trainingProgram_info_in_session":
                @SuppressWarnings("unchecked")
                List<TraTrainingProgramEntity> trainingProgramList = (List<TraTrainingProgramEntity>) request.getSession().getAttribute(sessionName);
                if (trainingProgramList == null) {
                    return R.error(-1, "请重新选择文件!");
                }
                try {
                    trainingProgramService.saveImport(trainingProgramList);
                } catch (Exception e) {
                    e.printStackTrace();
                    return R.error(-1, e.getMessage());
                }
                break;
            case "standardCapability_info_in_session":
                @SuppressWarnings("unchecked")
                List<TraStandardCapabilityEntity> standardCapabilityList = (List<TraStandardCapabilityEntity>) request.getSession().getAttribute(sessionName);
                if (standardCapabilityList == null) {
                    return R.error(-1, "请重新选择文件!");
                }
                try {
                    standardCapabilityService.saveImport(standardCapabilityList);
                } catch (Exception e) {
                    e.printStackTrace();
                    return R.error(-1, e.getMessage());
                }
                break;
            case "majorCapability_info_in_session":
                @SuppressWarnings("unchecked")
                List<TraMajorCapabilityEntity> majorCapabilityList = (List<TraMajorCapabilityEntity>) request.getSession().getAttribute(sessionName);

                if (majorCapabilityList == null) {
                    return R.error(-1, "请重新选择文件!");
                }
                try {
                    majorCapabilityService.saveImport(majorCapabilityList);
                } catch (Exception e) {
                    e.printStackTrace();
                    return R.error(-1, e.getMessage());
                }
                break;
            case "majorCapabilityTarget_info_in_session":
                @SuppressWarnings("unchecked")
                List<TraMajorCapabilityTargetEntity> majorCapabilityTargetList = (List<TraMajorCapabilityTargetEntity>) request.getSession().getAttribute(sessionName);
                if (majorCapabilityTargetList == null) {
                    return R.error(-1, "请重新选择文件!");
                }
                try {
                    majorCapabilityTargetService.saveImport(majorCapabilityTargetList);
                } catch (Exception e) {
                    e.printStackTrace();
                    return R.error(-1, e.getMessage());
                }
                break;
            case "trainingGoalSupport_info_in_session":
                @SuppressWarnings("unchecked")
                List<TraTrainingGoalSupportListEntity> trainingGoalSupportList = (List<TraTrainingGoalSupportListEntity>) request.getSession().getAttribute(sessionName);
                if (trainingGoalSupportList == null) {
                    return R.error(-1, "请重新选择文件!");
                }
                try {
                    traTrainingGoalSupportListService.saveImport(trainingGoalSupportList);
                } catch (Exception e) {
                    e.printStackTrace();
                    return R.error(-1, e.getMessage());
                }
                break;
            case "courseInfo_info_in_session":
                @SuppressWarnings("unchecked")
                List<TraCourseInfoEntity> courseInfoList = (List<TraCourseInfoEntity>) request.getSession().getAttribute(sessionName);
                if (courseInfoList == null) {
                    return R.error(-1, "请重新选择文件!");
                }
                try {
                    courseInfoService.saveImport(courseInfoList);
                } catch (Exception e) {
                    e.printStackTrace();
                    return R.error(-1, e.getMessage());
                }
                break;
            case "courseCapability_info_in_session":
                @SuppressWarnings("unchecked")
                List<TraCourseCapabilityListEntity> courseCapabilityList = (List<TraCourseCapabilityListEntity>) request.getSession().getAttribute(sessionName);
                if (courseCapabilityList == null) {
                    return R.error(-1, "请重新选择文件!");
                }
                try {
                    courseCapabilityListService.saveImport(courseCapabilityList);
                } catch (Exception e) {
                    e.printStackTrace();
                    return R.error(-1, e.getMessage());
                }
                break;
        }

        //所有内容保存完毕后移除sesion中实例集合
        request.getSession().removeAttribute(sessionName);
        return R.ok("保存导入的设备成功");

    }

    private <T> List<T> importExcel(MultipartFile file, Class<T> c) throws IOException {
        List<T> list = EasyExcelUtils.importExcel(file, 1, 1, 0, false, c);
        return list;
    }

    /**
     * 上传数据校验
     *
     * @param trainingId
     * @return
     */
    void analysisImport(MultipartFile uFile, Long trainingId,String name) {
        try {
            InputStream inputStream = uFile.getInputStream();
            HSSFWorkbook workbook = new HSSFWorkbook(inputStream);
            HSSFSheet sheet = workbook.getSheetAt(0);
            if ( sheet == null || !sheet.getSheetName().equals(name)){
                throw new RRException("请上传正确的Excel");
            }
            HSSFRow row = sheet.getRow(0);
            String trainingName = row.getCell(1).getStringCellValue();
            String trainingVersion = row.getCell(3).getStringCellValue();
            TraTrainingProgramEntity trainingEntity = trainingProgramService.getBaseMapper().selectById(trainingId);
            if (
                    (StringUtils.isBlank(trainingName) || !trainingName.equals(trainingEntity.getTrainingProgram())) ||
                            (StringUtils.isBlank(trainingVersion) && !trainingVersion.equals(trainingEntity.getVersion()))
            ) {
                throw new RRException("培养方案输入错误");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
