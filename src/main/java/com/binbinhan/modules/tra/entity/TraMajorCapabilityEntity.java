package com.binbinhan.modules.tra.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 专业能力
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:39:25
 */
@Data
@TableName("tra_major_capability")
public class TraMajorCapabilityEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 标准能力编号
	 */
	private String standardCapacityNumber;
	/**
	 * 支撑权重
	 */
	private Integer supportWeight;
	/**
	 * 能力编号（防止出现1.2.1这类编号所以使用varchar）
	 */
	@TableId
	private String capacityNumber;
	/**
	 * 父节点，对应能力编号
	 */
	private String parentNode;
	/**
	 * 能力描述
	 */
	private String capabilityDescription;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 培养方案ID
	 */
	private Long trainingId;

}
