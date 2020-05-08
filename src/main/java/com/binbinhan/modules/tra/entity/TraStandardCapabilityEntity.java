package com.binbinhan.modules.tra.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 标准能力
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:39:25
 */
@Data
@TableName("tra_standard_capability")
public class TraStandardCapabilityEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 标准能力
	 */
	@TableId
	private Long id;
	/**
	 * 认证标准
	 */
	private String standard;
	/**
	 * 版本
	 */
	private String version;
	/**
	 * 发布单位
	 */
	private String issuedBy;
	/**
	 * 能力编号（防止出现1.2.1这类编号所以使用varchar）
	 */
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
