package com.spring.admin.modules.sys.core.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.spring.admin.data.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author 李飞洋
 * @date 2024/8/1
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class PatientInfo extends BaseEntity {

    /**
     * 患者姓名
     */
    @Schema(description = "患者姓名")
    private String name;

    /**
     * 性别 (0: 女, 1: 男)
     */
    @Schema(description = "性别 (0: 女, 1: 男)")
    private Integer sex;

    /**
     * 年龄
     */
    @Schema(description = "年龄")
    private Integer age;

    /**
     * 住址 (不超过50个汉字)
     */
    @Schema(description = "住址 (不超过50个汉字)")
    private String address;

    /**
     * 电话
     */
    @Schema(description = "电话")
    private String phone;

    /**
     * 联系人电话
     */
    @Schema(description = "联系人电话")
    private String contactPhone;

    /**
     * 身高
     */
    @Schema(description = "身高")
    private Double height;

    /**
     * 体重
     */
    @Schema(description = "体重")
    private Double weight;

    /**
     * bmi
     */
    @TableField(exist = false)
    @Schema(description = "bmi = weight / ( height * height )")
    private Double bmi;

    /**
     * 录入状态 (0: 未录入, 1: 录入中, 2: 已录入)
     */
    @Schema(description = "录入状态 (0: 未录入, 1: 录入中, 2: 已录入)")
    private Integer inputStatus;

    /**
     * 是否启用 (0: 禁用, 1: 启用)
     */
    @Schema(description = "是否启用", defaultValue = "1")
    private Integer isEnable;

    /**
     * 是否删除 (0: 删除, 1: 正常)
     */
    @Schema(description = "是否删除", defaultValue = "1")
    private Integer isDel;
}
