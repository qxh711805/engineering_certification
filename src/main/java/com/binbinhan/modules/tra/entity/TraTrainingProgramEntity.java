package com.binbinhan.modules.tra.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 培养方案
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:39:25
 */
@Data
@TableName("tra_training_program")
public class TraTrainingProgramEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long trainingId;
	/**
	 * 学校名称
	 */
	@Excel(name = "学校")
	private String schoolName;
	/**
	 * 学院名称
	 */
	@Excel(name = "学院")
	private String collegeName;
	/**
	 * 系名
	 */
	@Excel(name = "系")
	private String department;
	/**
	 * 专业
	 */
	@Excel(name = "专业")
	private String major;
	/**
	 * 专业简称
	 */
	@Excel(name = "专业简称")
	private String majorAbbreviation;
	/**
	 * 专业代码
	 */
	@Excel(name = "专业代码")
	private String majorCode;
	/**
	 * 学科门类
	 */
	@Excel(name = "学科门类")
	private String disciplineCategory;
	/**
	 * 培养方案
	 */
	@Excel(name = "培养方案")
	private String trainingProgram;
	/**
	 * 版本
	 */
	@Excel(name = "版本")
	private String version;
	/**
	 * 状态
	 */
	@Excel(name = "状态")
	private String status;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
