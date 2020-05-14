package com.binbinhan.modules.tra.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.binbinhan.common.annotation.SysLog;
import com.binbinhan.common.controller.AbstractController;
import com.binbinhan.common.controller.R;
import com.binbinhan.common.utils.PageUtils;
import com.binbinhan.common.utils.Query;
import com.binbinhan.modules.sys.entity.SysUserEntity;
import com.binbinhan.modules.sys.shiro.ShiroUtils;
import com.binbinhan.modules.tra.entity.TraStandardCapabilityEntity;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.binbinhan.modules.tra.service.TraStandardCapabilityService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 标准能力
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:39:25
 */
@RestController
@RequestMapping("tra/standard/capability")
public class TraStandardCapabilityController extends AbstractController {
    @Autowired
    private TraStandardCapabilityService traStandardCapabilityService;
    /**
     * 所有标准能力
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = traStandardCapabilityService.queryPage(params);
        return R.ok().put("page", page);
    }

    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        TraStandardCapabilityEntity data = traStandardCapabilityService.getById(id);
        return R.ok().put("data", data);
    }

    @RequestMapping("/save")
    public R save(@RequestBody TraStandardCapabilityEntity data) {
        traStandardCapabilityService.save(data);
        return R.ok();
    }
    
    @RequestMapping("/update")
    public R update(@RequestBody TraStandardCapabilityEntity data) {
        traStandardCapabilityService.updateById(data);
        return R.ok();
    }

    @RequestMapping("/delete")
    public R delete(@RequestBody Long id) {
        traStandardCapabilityService.removeById(id);
        return R.ok();
    }
}
