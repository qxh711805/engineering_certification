package com.binbinhan.modules.sys.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 班级
 *
 * @作者 binbinhan
 * @时间 2020-05-06 21:12
 */
@Data
public class ClassEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 专业
     */
    private String major;
    /**
     * 班级名称
     */
    private String className;
    /**
     * 年级
     */
    private String grade;
    /**
     * 开班时间
     */
    private String openingTime;;
    /**
     * 培养层次
     */
    private String arrangement;
    /**
     * 学制
     */
    private Integer educationalSystem;
    /**
     * 培养方案
     */
    private String trainingProgram;
    /**
     * 学校
     */
    private String schoolName;
}
