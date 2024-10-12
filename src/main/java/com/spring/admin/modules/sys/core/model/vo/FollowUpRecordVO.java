package com.spring.admin.modules.sys.core.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@EqualsAndHashCode
@ToString
public class FollowUpRecordVO implements Serializable {

    /**
     * ID
     */
    @Schema(description = "主键ID")
    protected String id;

    /**
     * 患者ID
     */
    @Schema(description = "关联patient_info表的id")
    private String patientId;

    private LocalDate surgeryDate;

    /**
     * 术后月份
     */
    @Schema(description = "术后月份")
    private Integer afterSurgeryDate;

    /**
     * 随访日期
     */
    @Schema(description = "随访日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate followUpDate;

    /**
     * 随访状态
     */
    @Schema(description = "随访状态 (pending:等待中, pending_visit:待随访, completed:已完成)")
    private String followUpStatus;

    /**
     * 复查超声是否异常
     */
    @Schema(description = "复查超声是否异常")
    private Integer ultrasoundAbnormal;

    /**
     * 超声异常明细
     */
    @Schema(description = "超声异常明细")
    private String ultrasoundAbnormalDetail;

    /**
     * 是否需进一步穿刺
     */
    @Schema(description = "是否需进一步穿刺")
    private Integer needFurtherBiopsy;

    /**
     * 复查穿刺结果
     */
    @Schema(description = "复查穿刺结果")
    private String biopsyResult;

    /**
     * 是否有转移
     */
    @Schema(description = "是否有转移")
    private Integer metastasis;

    /**
     * 转移部位
     */
    @Schema(description = "转移部位(1:脑, 2:肺, 3:肝, 4:骨, 5:同侧乳腺或胸壁, 6:同侧腋窝淋巴结, 7:同侧锁骨上淋巴结, 8:对侧腋窝淋巴结, 9:对侧锁骨上淋巴结, 0:其他部位)")
    private Integer metastasisLocation;

    /**
     * 是否已故
     */
    @Schema(description = "是否已故")
    private Integer deceased;

    /**
     * 已故原因
     */
    @Schema(description = "已故原因")
    private String causeOfDeath;

    /**
     * 是否术后患侧上肢水肿
     */
    @Schema(description = "是否术后患侧上肢水肿")
    private Integer armEdema;

    /**
     * 是否行针对上肢水肿的治疗
     */
    @Schema(description = "是否行针对上肢水肿的治疗")
    private Integer armEdemaTreatment;

    /**
     * X月复查胸部CT是否异常
     */
    @Schema(description = "X月复查胸部CT是否异常")
    private Integer chestCtAbnormal;

    /**
     * X月复查头颅MR是否异常
     */
    @Schema(description = "X月复查头颅MR是否异常")
    private Integer headMrAbnormal;

    /**
     * X月复查全身骨显像是否异常
     */
    @Schema(description = "X月复查全身骨显像是否异常")
    private Integer boneScanAbnormal;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String notes;

    /**
     * 录入状态
     */
    @Schema(description = "录入状态 (0: 未录入, 1: 录入中, 2: 已录入)")
    private Integer inputStatus;

    /**
     * 创建者
     */
    @Schema(description = "创建者")
    protected String createdBy;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime created;

    /**
     * 更新者
     */
    @Schema(description = "更新者")
    protected String modifiedBy;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    protected LocalDateTime modified;

    /**
     * 是否启用
     */
    @Schema(description = "是否启用 (0: 禁用, 1: 启用)")
    private Integer isEnable;

    /**
     * 是否删除
     */
    @Schema(description = "是否删除 (0: 删除, 1: 正常)")
    private Integer isDel;

    // 从patient_info表中获取的字段

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
     * 住址
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

    // 从general_info表中获取的字段

    /**
     * 手术编号
     */
    @Schema(description = "手术编号")
    private String surgicalNum;

    /**
     * 病案号
     */
    @Schema(description = "病案号")
    private String caseNo;
}
