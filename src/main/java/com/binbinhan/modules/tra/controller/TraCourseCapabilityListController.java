package com.binbinhan.modules.tra.controller;

import com.binbinhan.common.controller.AbstractController;
import com.binbinhan.common.controller.R;
import com.binbinhan.common.utils.PageUtils;
import com.binbinhan.modules.tra.entity.TraCourseCapabilityListEntity;
import com.binbinhan.modules.tra.entity.TraCourseCapabilityListEntity;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.binbinhan.modules.tra.service.TraCourseCapabilityListService;

import java.util.Map;


/**
 * 课程能力清单
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:39:25
 */
@RestController
@RequestMapping("tra/course/capability")
public class TraCourseCapabilityListController extends AbstractController {
    @Autowired
    private TraCourseCapabilityListService traCourseCapabilityListService;
    /**
     * 所有课程能力清单
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = traCourseCapabilityListService.queryPage(params);

        return R.ok().put("page", page);
    }


    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        TraCourseCapabilityListEntity data = traCourseCapabilityListService.getById(id);
        return R.ok().put("data", data);
    }

    @RequestMapping("/save")
    public R save(@RequestBody TraCourseCapabilityListEntity data) {
        traCourseCapabilityListService.save(data);
        return R.ok();
    }

    @RequestMapping("/update")
    public R update(@RequestBody TraCourseCapabilityListEntity data) {
        traCourseCapabilityListService.updateById(data);
        return R.ok();
    }

    @RequestMapping("/delete")
    public R delete(@RequestBody Long id) {
        traCourseCapabilityListService.removeById(id);
        return R.ok();
    }
}
