package com.spring.admin.modules.sys.core.model.entity;

import com.spring.admin.data.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author 李飞洋
 * @date 2024/8/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class AdjuvantInfo extends BaseEntity {

    /**
     * 患者ID
     */
    @Schema(description = "患者ID")
    private String patientId;

    /**
     * 是否进行辅助治疗
     */
    @Schema(description = "是否进行辅助治疗")
    private Boolean adjuvantTherapy;

    /**
     * 是否加入临床研究
     */
    @Schema(description = "是否加入临床研究")
    private Boolean clinicalResearch;

    /**
     * 具体临床研究
     */
    @Schema(description = "具体临床研究")
    private String researchDetails;

    /**
     * 具体辅助治疗方案
     */
    @Schema(description = "具体辅助治疗方案")
    private String therapyPlan;

    /**
     * 是否有新辅助治疗后强化治疗应用
     */
    @Schema(description = "是否有新辅助治疗后强化治疗应用")
    private Boolean intensifiedTherapy;

    /**
     * 具体强化辅助治疗方案
     */
    @Schema(description = "具体强化辅助治疗方案")
    private String intensifiedPlan;

    /**
     * 是否有辅助药物剂量调整
     */
    @Schema(description = "是否有辅助药物剂量调整")
    private Boolean doseAdjustment;

    /**
     * 辅助药物剂量调整原因
     */
    @Schema(description = "辅助药物剂量调整原因")
    private String adjustmentReason;

    /**
     * 是否终止辅助治疗
     */
    @Schema(description = "是否终止辅助治疗")
    private Boolean therapyTermination;

    /**
     * 终止辅助治疗原因
     */
    @Schema(description = "终止辅助治疗原因")
    private String terminationReason;

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
