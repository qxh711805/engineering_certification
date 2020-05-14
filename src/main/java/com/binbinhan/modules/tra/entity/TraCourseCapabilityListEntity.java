package com.binbinhan.modules.tra.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 课程能力清单
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:39:25
 */
@Data
@TableName("tra_course_capability_list")
public class TraCourseCapabilityListEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Long courseId;
	/**
	 * 课程编号
	 */
	@Excel(name = "课程编号")
	private String courseNumber;
	/**
	 * 能力
	 */
	@Excel(name = "能力")
	private Double capability;
	/**
	 * 权重
	 */
	@Excel(name = "权重")
	private Double weight;
	/**
	 * 课程名称
	 */
	@Excel(name = "课程名称")
	private String courseName;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 培养方案ID
	 */
	private Long trainingId;

}
