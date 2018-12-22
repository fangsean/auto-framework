package com.auto.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.auto.entity.enums.AgeEnum;

import java.util.Date;

/**
 * 用户表
 */
@SuppressWarnings("serial")
public class User extends SuperEntity<User> {

    /**
     * 名称
     */
    private String name;
    /**
     * 年龄
     */
    private int age;
    /**
     * 这里故意演示注解可无
     */
    @TableField("test_type")
    @TableLogic
    private Integer testType;

    /**
     * 测试插入填充
     */
    @TableField(fill = FieldFill.INSERT)
    private Date testDate;

    private Long role;
    private String phone;

    public User() {
    }

    public User(Long id, String name, AgeEnum age, Integer testType) {
        this.setId(id);
        this.name = name;
        this.age = age.getValue();
        this.testType = testType;
    }

    public User(String name, AgeEnum age, Integer testType) {
        this.name = name;
        this.age = age.getValue();
        this.testType = testType;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Integer getTestType() {
        return this.testType;
    }

    public void setTestType(Integer testType) {
        this.testType = testType;
    }

    public Long getRole() {
        return this.role;
    }

    public void setRole(Long role) {
        this.role = role;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    @Override
    public String toString() {
        return "User [id=" + this.getId() + ", name=" + name + ", age=" + age
                + ", testType=" + testType + ", testDate="
                + testDate + ", role=" + role + ", phone=" + phone + "]";
    }

}
