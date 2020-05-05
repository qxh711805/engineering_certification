package com.binbinhan.modules.tra.controller;

import com.binbinhan.common.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.binbinhan.modules.tra.service.TraCourseInfoService;


/**
 * 课程信息
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:39:25
 */
@RestController
@RequestMapping("tra/tracourseinfo")
public class TraCourseInfoController extends AbstractController {
    @Autowired
    private TraCourseInfoService traCourseInfoService;

}
