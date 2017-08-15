package com.test.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class Menu{


	private String id ;
	private String name;			// 菜单名称
	private String href;			// 链接
	private String target;			// 目标

	
	
	

	public Menu(String id, String name, String href, String target) {
        super();
        this.id = id;
        this.name = name;
        this.href = href;
        this.target = target;
    }

    public Menu() {
		super();
	}

	public Menu(String id) {
		this();
		this.id = id;
	}


	@NotEmpty(message = "菜单名称不能为空")
	@Length(min=1, max=100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Length(min=1, max=30,message="菜单链接长度在1-30之间")
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	@Length(min=1, max=5,message="target长度在1-5之间" )
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
}
