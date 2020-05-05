package com.binbinhan.modules.tra.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 专业培养目标
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:39:25
 */
@Data
@TableName("tra_major_capability_target")
public class TraMajorCapabilityTargetEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId
	private Integer number;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 培养方案ID
	 */
	private Long trainingId;

}
