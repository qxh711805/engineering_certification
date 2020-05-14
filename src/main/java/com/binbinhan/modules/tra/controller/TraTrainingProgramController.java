package com.binbinhan.modules.tra.controller;

import com.binbinhan.common.annotation.SysLog;
import com.binbinhan.common.controller.AbstractController;
import com.binbinhan.common.controller.R;
import com.binbinhan.common.excel.EasyExcelUtils;
import com.binbinhan.common.utils.PageUtils;
import com.binbinhan.modules.tra.entity.TraTrainingProgramEntity;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("tra/training/program")
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
     *
     * @return
     */

    @RequestMapping(value = "fileUpload")
    @ResponseBody
    public Object importExcel(@RequestParam("file") MultipartFile uFile, HttpServletRequest request) throws Exception {

        //解析excel，
//        List<TraTrainingProgramEntity> personList = FileUtil.importExcel(uFile, 0, 1, TraTrainingProgramEntity.class);
        List<TraTrainingProgramEntity> personList = EasyExcelUtils.importExcel(uFile, 0, 2, 0, false, TraTrainingProgramEntity.class);

        System.out.println(personList);
        String error = "";
        for (TraTrainingProgramEntity traTrainingProgramEntity : personList) {
            traTrainingProgramService.save(traTrainingProgramEntity);
        }
        Map map = new HashMap();
        error = "文件上传成功，点击【确定】保存！";
        map.put("error", error);
        return map;

    }
//未修改

}
