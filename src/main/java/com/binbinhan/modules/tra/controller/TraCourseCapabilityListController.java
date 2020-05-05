package com.binbinhan.modules.tra.controller;

import com.binbinhan.common.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.binbinhan.modules.tra.service.TraCourseCapabilityListService;


/**
 * 课程能力清单
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:39:25
 */
@RestController
@RequestMapping("tra/tracoursecapabilitylist")
public class TraCourseCapabilityListController extends AbstractController {
    @Autowired
    private TraCourseCapabilityListService traCourseCapabilityListService;

}
