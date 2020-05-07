package com.binbinhan.modules.tra.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 培养目标支撑清单
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:39:25
 */
@Data
@TableName("tra_training_goal_support_list")
public class TraTrainingGoalSupportListEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 流水号
	 */
	@TableId
	private Long id;
	/**
	 * 培养目标
	 */
	private String trainingObjectives;
	/**
	 * 毕业要求
	 */
	private Integer graduationRequirements;
	/**
	 * 权重
	 */
	private Double weight;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 培养方案ID
	 */
	private Long trainingId;

}
