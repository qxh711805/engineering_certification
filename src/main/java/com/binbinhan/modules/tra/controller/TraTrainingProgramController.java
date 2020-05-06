package com.binbinhan.modules.tra.controller;

import com.binbinhan.common.annotation.SysLog;
import com.binbinhan.common.controller.AbstractController;
import com.binbinhan.common.controller.R;
import com.binbinhan.common.utils.PageUtils;
import com.binbinhan.modules.sys.entity.SysUserEntity;
import com.binbinhan.modules.tra.entity.TraTrainingProgramEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.binbinhan.modules.tra.service.TraTrainingProgramService;

import java.util.Map;


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
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = traTrainingProgramService.queryPage(params);
        System.out.println(page);
        return R.ok().put("page", page);
    }

    @RequestMapping(value = "delete/{trainingId}")
    public R delete(@PathVariable("trainingId") Long trainingId){
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

    @RequestMapping(value = "update/{trainingId}",method = RequestMethod.POST)
    public R update(@PathVariable("trainingId") Long trainingId){
        traTrainingProgramService.removeById(trainingId);
        return R.ok();
    }
}
