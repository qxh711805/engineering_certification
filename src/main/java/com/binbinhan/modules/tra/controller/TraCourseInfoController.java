package com.binbinhan.modules.tra.controller;

import com.binbinhan.common.controller.AbstractController;
import com.binbinhan.common.controller.R;
import com.binbinhan.common.utils.PageUtils;
import com.binbinhan.modules.tra.entity.TraCourseInfoEntity;
import com.binbinhan.modules.tra.entity.TraCourseInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.binbinhan.modules.tra.service.TraCourseInfoService;

import java.security.KeyStore;
import java.util.Map;


/**
 * 课程信息
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:39:25
 */
@RestController
@RequestMapping("tra/course/info")
public class TraCourseInfoController extends AbstractController {
    @Autowired
    private TraCourseInfoService traCourseInfoService;
    /**
     * 所有课程信息
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = traCourseInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        TraCourseInfoEntity data = traCourseInfoService.getById(id);
        return R.ok().put("data", data);
    }

    @RequestMapping("/save")
    public R save(@RequestBody TraCourseInfoEntity data) {
        traCourseInfoService.save(data);
        return R.ok();
    }

    @RequestMapping("/update")
    public R update(@RequestBody TraCourseInfoEntity data) {
        traCourseInfoService.updateById(data);
        return R.ok();
    }

    @RequestMapping("/delete")
    public R delete(@RequestBody Long id) {
        traCourseInfoService.removeById(id);
        return R.ok();
    }
}
