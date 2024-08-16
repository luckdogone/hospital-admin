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
public class RadiationInfo extends BaseEntity {

    /**
     * 患者ID
     */
    @Schema(description = "患者ID")
    private String patientId;

    /**
     * 是否进行放射治疗
     */
    @Schema(description = "是否进行放射治疗")
    private Boolean radiationTreatment;

    /**
     * 是否加入临床研究
     */
    @Schema(description = "是否加入临床研究")
    private Boolean clinicalResearch;

    /**
     * 具体临床研究
     */
    @Schema(description = "具体临床研究")
    private String clinicalResearchDetails;

    /**
     * 具体放疗方案
     */
    @Schema(description = "具体放疗方案")
    private String treatmentPlan;

    /**
     * 放疗部位
     */
    @Schema(description = "放疗部位")
    private String treatmentLocation;

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
