package com.binbinhan.modules.tra.controller;

import com.binbinhan.common.controller.AbstractController;
import com.binbinhan.common.controller.R;
import com.binbinhan.common.utils.PageUtils;
import com.binbinhan.modules.tra.entity.TraTrainingGoalSupportListEntity;
import com.binbinhan.modules.tra.entity.TraTrainingGoalSupportListEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.binbinhan.modules.tra.service.TraTrainingGoalSupportListService;

import java.util.Map;


/**
 * 培养目标支撑清单
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:39:25
 */
@RestController
@RequestMapping("tra/training/goal/support")
public class TraTrainingGoalSupportListController extends AbstractController {
    @Autowired
    private TraTrainingGoalSupportListService traTrainingGoalSupportListService;
    /**
     * 所有培养目标支撑清单
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = traTrainingGoalSupportListService.queryPage(params);

        return R.ok().put("page", page);
    }



    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        TraTrainingGoalSupportListEntity data = traTrainingGoalSupportListService.getById(id);
        return R.ok().put("data", data);
    }

    @RequestMapping("/save")
    public R save(@RequestBody TraTrainingGoalSupportListEntity data) {
        traTrainingGoalSupportListService.save(data);
        return R.ok();
    }

    @RequestMapping("/update")
    public R update(@RequestBody TraTrainingGoalSupportListEntity data) {
        traTrainingGoalSupportListService.updateById(data);
        return R.ok();
    }

    @RequestMapping("/delete")
    public R delete(@RequestBody Long id) {
        traTrainingGoalSupportListService.removeById(id);
        return R.ok();
    }
}
