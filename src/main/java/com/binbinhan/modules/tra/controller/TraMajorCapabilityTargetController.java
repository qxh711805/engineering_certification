package com.binbinhan.modules.tra.controller;

import com.binbinhan.common.controller.AbstractController;
import com.binbinhan.common.controller.R;
import com.binbinhan.common.utils.PageUtils;
import com.binbinhan.modules.tra.entity.TraMajorCapabilityTargetEntity;
import com.binbinhan.modules.tra.entity.TraMajorCapabilityTargetEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.binbinhan.modules.tra.service.TraMajorCapabilityTargetService;

import java.util.Map;


/**
 * 专业培养目标
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:39:25
 */
@RestController
@RequestMapping("tra/major/capability/target")
public class TraMajorCapabilityTargetController extends AbstractController {
    @Autowired
    private TraMajorCapabilityTargetService traMajorCapabilityTargetService;
    /**
     * 所有专业培养目标
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = traMajorCapabilityTargetService.queryPage(params);
        
        return R.ok().put("page", page);
    }



    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        TraMajorCapabilityTargetEntity data = traMajorCapabilityTargetService.getById(id);
        return R.ok().put("data", data);
    }

    @RequestMapping("/save")
    public R save(@RequestBody TraMajorCapabilityTargetEntity data) {
        traMajorCapabilityTargetService.save(data);
        return R.ok();
    }

    @RequestMapping("/update")
    public R update(@RequestBody TraMajorCapabilityTargetEntity data) {
        traMajorCapabilityTargetService.updateById(data);
        return R.ok();
    }

    @RequestMapping("/delete")
    public R delete(@RequestBody Long id) {
        traMajorCapabilityTargetService.removeById(id);
        return R.ok();
    }
}
