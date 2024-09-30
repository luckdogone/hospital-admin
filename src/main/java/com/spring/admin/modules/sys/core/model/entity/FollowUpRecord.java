package com.spring.admin.modules.sys.core.model.entity;

import com.spring.admin.data.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class FollowUpRecord extends BaseEntity {

    @Schema(description = "患者ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "患者ID不能为空")
    private String patientId;

    @Schema(description = "术后月份")
    private Integer afterSurgeryDate;

    @Schema(description = "随访日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "随访日期不能为空")
    private Date followUpDate;

    @Schema(description = "随访状态", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "随访状态不能为空")
    private String followUpStatus;

    @Schema(description = "复查超声是否异常")
    private Integer ultrasoundAbnormal;

    @Schema(description = "超声异常明细")
    private String ultrasoundAbnormalDetail;

    @Schema(description = "是否需进一步穿刺")
    private Integer needFurtherBiopsy;

    @Schema(description = "复查穿刺结果")
    private String biopsyResult;

    @Schema(description = "是否有转移")
    private Integer metastasis;

    @Schema(description = "转移部位")
    private Integer metastasisLocation;

    @Schema(description = "是否已故")
    private Integer deceased;

    @Schema(description = "已故原因")
    private String causeOfDeath;

    @Schema(description = "是否术后患侧上肢水肿")
    private Integer armEdema;

    @Schema(description = "是否行针对上肢水肿的治疗")
    private Integer armEdemaTreatment;

    @Schema(description = "X月复查胸部CT是否异常")
    private Integer chestCtAbnormal;

    @Schema(description = "X月复查胸部CT异常结果")
    private String chestCtAbnormalResult;

    @Schema(description = "X月复查头颅MR是否异常")
    private Integer headMrAbnormal;

    @Schema(description = "X月复查头颅MR异常结果")
    private String headMrAbnormalResult;

    @Schema(description = "X月复查全身骨显像是否异常")
    private Integer boneScanAbnormal;

    @Schema(description = "X月复查全身骨显像异常结果")
    private String boneScanAbnormalResult;

    @Schema(description = "备注")
    private String notes;

    /**
     * 录入状态
     */
    @Schema(description = "录入状态 (0: 未录入, 1: 录入中, 2: 已录入)")
    private Integer inputStatus;

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
}
