package com.binbinhan.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.binbinhan.common.validator.group.AddGroup;
import com.binbinhan.common.validator.group.UpdateGroup;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 系统用户
 *
 * @作者 binbinhan
 * @date 2020-05-05 00:35:57
 */
@Data
@TableName("sys_user")
public class SysUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long userId;
	/**
	 * 用户名(用户登录的学号/工号)
	 */
	@NotBlank(message="用户名不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String username;
	/**
	 * 用户姓名
	 */
	@NotBlank(message="用户姓名不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String name;
	/**
	 * 密码（初始密码：123456）
	 */
	@NotBlank(message="密码不能为空", groups = AddGroup.class)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	/**
	 * 盐（密码加密验证）
	 */
	private String salt;
	/**
	 * 学校名称
	 */
	private String schoolName;
	/**
	 * 学校简码
	 */
	private String schoolCode;
	/**
	 * 学院名称
	 */
	private String collegeName;
	/**
	 * 系名
	 */
	private String department;
	/**
	 * 专业
	 */
	private String major;
	/**
	 * 工号/学号
	 */
	@NotBlank(message="工号/学号不能为空", groups = {AddGroup.class, UpdateGroup.class})
	private String jobNumber;
	/**
	 * 身份证号
	 */
	private String idNumber;
	/**
	 * 邮箱
	 */
	@Email(message="邮箱格式不正确", groups = {AddGroup.class, UpdateGroup.class})
	private String email;
	/**
	 * 手机号
	 */
	private String mobile;
	/**
	 * 状态  0：禁用   1：正常
	 */
	private Integer status;
	/**
	 * 角色ID列表
	 */
	@TableField(exist=false)
	private List<Long> roleIdList;

	/**
	 * 登录角色ID
	 */
	@TableField(exist=false)
	private Long loginRoleId;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
