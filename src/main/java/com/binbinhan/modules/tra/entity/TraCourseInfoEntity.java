package com.binbinhan.modules.tra.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 课程信息
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:39:25
 */
@Data
@TableName("tra_course_info")
public class TraCourseInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long courseId;
	/**
	 * 课程编号
	 */
	private String courseNumber;
	/**
	 * 课程名称
	 */
	private String courseName;
	/**
	 * 理论学时
	 */
	private Integer theoreticalHours;
	/**
	 * 实践学时
	 */
	private Integer practicalHours;
	/**
	 * 学分
	 */
	private Integer credit;
	/**
	 * 课程大类
	 */
	private String courseBigCategories;
	/**
	 * 课程小类
	 */
	private String courseSmallCategories;
	/**
	 * 选修方式
	 */
	private String electiveMode;
	/**
	 * 起始时间
	 */
	private Date startTime;
	/**
	 * 终止时间
	 */
	private Date endTime;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 培养方案ID
	 */
	private Long trainingId;

}
