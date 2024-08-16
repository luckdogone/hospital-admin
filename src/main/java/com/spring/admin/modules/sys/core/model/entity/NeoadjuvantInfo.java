package com.spring.admin.modules.sys.core.model.entity;

import com.spring.admin.data.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author 李飞洋
 * @date 2024/8/16
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class NeoadjuvantInfo extends BaseEntity {

    /**
     * 患者ID
     */
    @Schema(description = "患者ID")
    private String patientId;

    /**
     * 是否进行新辅助治疗
     */
    @Schema(description = "是否进行新辅助治疗")
    private Boolean neoadjTherapy;

    /**
     * 是否加入临床研究
     */
    @Schema(description = "是否加入临床研究")
    private Boolean clinResearch;

    /**
     * 具体临床研究
     */
    @Schema(description = "具体临床研究")
    private String researchDetails;

    /**
     * 具体新辅助治疗方案
     */
    @Schema(description = "具体新辅助治疗方案")
    private String therapyPlan;

    /**
     * 是否有免疫药物应用
     */
    @Schema(description = "是否有免疫药物应用")
    private Boolean immunotherapy;

    /**
     * 1周期超声示肿物大小
     */
    @Schema(description = "1周期超声示肿物大小")
    private Double week1Size;

    /**
     * 2周期超声示肿物大小
     */
    @Schema(description = "2周期超声示肿物大小")
    private Double week2Size;

    /**
     * 3周期超声示肿物大小
     */
    @Schema(description = "3周期超声示肿物大小")
    private Double week3Size;

    /**
     * 4周期超声示肿物大小
     */
    @Schema(description = "4周期超声示肿物大小")
    private Double week4Size;

    /**
     * 5周期超声示肿物大小
     */
    @Schema(description = "5周期超声示肿物大小")
    private Double week5Size;

    /**
     * 6周期超声示肿物大小
     */
    @Schema(description = "6周期超声示肿物大小")
    private Double week6Size;

    /**
     * 2/3周期超声示肿物大小
     */
    @Schema(description = "2/3周期超声示肿物大小")
    private Double week23Size;

    /**
     * 4/5周期超声示肿物大小
     */
    @Schema(description = "4/5周期超声示肿物大小")
    private Double week45Size;

    /**
     * 6/7周期超声示肿物大小
     */
    @Schema(description = "6/7周期超声示肿物大小")
    private Double week67Size;

    /**
     * 8周期超声示肿物大小
     */
    @Schema(description = "8周期超声示肿物大小")
    private Double week8Size;

    /**
     * 是否有新辅助药物剂量调整
     */
    @Schema(description = "是否有新辅助药物剂量调整")
    private Boolean doseAdjust;

    /**
     * 新辅助药物剂量调整原因
     */
    @Schema(description = "新辅助药物剂量调整原因")
    private String adjustReason;

    /**
     * 是否终止新辅助治疗
     */
    @Schema(description = "是否终止新辅助治疗")
    private Boolean therapyTerm;

    /**
     * 终止新辅助治疗原因
     */
    @Schema(description = "终止新辅助治疗原因")
    private String termReason;

    /**
     * 是否有升白针应用
     */
    @Schema(description = "是否有升白针应用")
    private Boolean gcsf;

    /**
     * 是否有长效升白针应用
     */
    @Schema(description = "是否有长效升白针应用")
    private Boolean longGcsf;

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
