package com.spring.admin.modules.sys.core.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode
@ToString
public class PatientInfoVO implements Serializable {

    @Schema(description = "患者ID")
    protected String id;

    @Schema(description = "患者姓名")
    private String name;

    @Schema(description = "性别 (0: 女, 1: 男)")
    private Integer sex;

    @Schema(description = "年龄")
    private Integer age;

    @Schema(description = "住址 (不超过50个汉字)")
    private String address;

    @Schema(description = "电话")
    private String phone;

    @Schema(description = "联系人电话")
    private String contactPhone;

    @Schema(description = "身高 (单位: 厘米)")
    private BigDecimal height;

    @Schema(description = "体重 (单位: 公斤)")
    private BigDecimal weight;

    @Schema(description = "录入状态 (0: 未录入, 1: 录入中, 2: 已录入)")
    private Integer inputStatus;

    @Schema(description = "创建者")
    private String createdBy;

    @Schema(description = "创建时间")
    private Date created;

    @Schema(description = "更新者")
    private String modifiedBy;

    @Schema(description = "更新时间")
    private Date modified;

    @Schema(description = "是否启用 (0: 禁用, 1: 启用)")
    private Integer isEnable;

    @Schema(description = "是否删除 (0: 删除, 1: 正常)")
    private Integer isDel;

    @Schema(description = "手术编号")
    private String surgicalNum;

    @Schema(description = "病案号")
    private String caseNo;
}
