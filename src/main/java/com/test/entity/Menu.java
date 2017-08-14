package com.test.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class Menu{


	private String id ;
	private String parent_id;		// 父级编号
	private String parent_ids;		// 所有父级编号
	private String name;			// 菜单名称
	private String href;			// 链接
	private String target;			// 目标
	private String icon;			// 图标
	private int sort;				// 排序（升序）
	private String is_show;			// 是否在菜单中显示
	private String is_activiti;		// 是否同步工作流
	private String permission;		// 权限标识
	private String version = "2.0";			// 1.0: 幼儿园管理版；2.0：大众版；后续可增加


	public Menu() {
		super();
	}

	public Menu(String id) {
		this();
		this.id = id;
	}

	@NotEmpty(message = "父类菜单ID不能为空")
	@Length(min=1, max=64)
	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	@NotEmpty(message = "parent_ids不能为空")
	@Length(min=1, max=2000)
	public String getParent_ids() {
		return parent_ids;
	}

	public void setParent_ids(String parent_ids) {
		this.parent_ids = parent_ids;
	}

	@NotEmpty(message = "菜单名称不能为空")
	@Length(min=1, max=100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min=1, max=255)
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	@Length(min=1, max=20)
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	@Length(min=1, max=100)
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	@NotEmpty(message = "是否显示不能为空")
	@Length(min=1, max=1)
	public String getIs_show() {
		return is_show;
	}

	public void setIs_show(String is_show) {
		this.is_show = is_show;
	}

	@Length(min=1, max=1)
	public String getIs_activiti() {
		return is_activiti;
	}

	public void setIs_activiti(String is_activiti) {
		this.is_activiti = is_activiti;
	}

	@Length(min=1, max=200)
	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
