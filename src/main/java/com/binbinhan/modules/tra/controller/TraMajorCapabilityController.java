package com.binbinhan.modules.tra.controller;

import com.binbinhan.common.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.binbinhan.modules.tra.service.TraMajorCapabilityService;


/**
 * 专业能力
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:39:25
 */
@RestController
@RequestMapping("tra/tramajorcapability")
public class TraMajorCapabilityController extends AbstractController {
    @Autowired
    private TraMajorCapabilityService traMajorCapabilityService;

}
