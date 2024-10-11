package com.spring.admin.modules.sys.core.model.entity;

import com.spring.admin.data.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class CaseInfo extends BaseEntity {

    @Schema(description = "患者ID")
    private String patientId;

    @Schema(description = "入院超声状态（0: 未做, 1: 左, 2: 右, 3: 双侧）")
    private Integer ultrasoundStatus;

    @Schema(description = "超声大小，单位cm")
    private Double ultrasoundLeftSize;

    @Schema(description = "是否有血流信号")
    private Integer ultrasoundLeftBloodFlow;

    @Schema(description = "BIRADS分类（超声）")
    private String ultrasoundLeftBirads;

    @Schema(description = "超声大小，单位cm")
    private Double ultrasoundRightSize;

    @Schema(description = "是否有血流信号")
    private Integer ultrasoundRightBloodFlow;

    @Schema(description = "BIRADS分类（超声）")
    private String ultrasoundRightBirads;

    @Schema(description = "入院钼靶状态（0: 未做, 1: 左, 2: 右, 3: 双侧）")
    private Integer mammographyStatus;

    @Schema(description = "钼靶大小，单位cm")
    private Double mammographyLeftSize;

    @Schema(description = "是否有细小钙化影")
    private Integer mammographyLeftAggregation;

    @Schema(description = "BIRADS分类（钼靶）")
    private String mammographyLeftBirads;

    @Schema(description = "钼靶大小，单位cm")
    private Double mammographyRightSize;

    @Schema(description = "是否有细小钙化影")
    private Integer mammographyRightAggregation;

    @Schema(description = "BIRADS分类（钼靶）")
    private String mammographyRightBirads;

    @Schema(description = "入院乳腺核磁状态（0: 未做, 1: 左, 2: 右, 3: 双侧）")
    private Integer mriStatus;

    @Schema(description = "乳腺大小，单位cm")
    private Double mriLeftSize;

    @Schema(description = "是否有增强信号")
    private Integer mriLeftBloodFlow;

    @Schema(description = "BIRADS分类（乳腺核磁）")
    private String mriLeftBirads;

    @Schema(description = "乳腺大小，单位cm")
    private Double mriRightSize;

    @Schema(description = "是否有增强信号")
    private Integer mriRightBloodFlow;

    @Schema(description = "BIRADS分类（乳腺核磁）")
    private String mriRightBirads;

    @Schema(description = "白细胞计数")
    private Float wbc;

    @Schema(description = "红细胞计数")
    private Float rbc;

    @Schema(description = "血小板计数")
    private Float platelets;

    @Schema(description = "谷丙转氨酶")
    private Float alt;

    @Schema(description = "谷草转氨酶")
    private Float ast;

    @Schema(description = "碱性磷酸酶")
    private Float alkalinePhosphatase;

    @Schema(description = "血肌酐")
    private Float creatinine;

    @Schema(description = "血清尿素")
    private Float bun;

    @Schema(description = "尿酸")
    private Float uricAcid;

    @Schema(description = "甘油三酯")
    private Float triglycerides;

    @Schema(description = "低密度脂蛋白")
    private Float ldl;

    @Schema(description = "D-二聚体")
    private Float dimer;

    @Schema(description = "癌胚抗原（CEA）")
    private Float cea;

    @Schema(description = "癌抗原153（CA153）")
    private Float ca153;

    @Schema(description = "癌抗原125（CA125）")
    private Float ca125;

    @Schema(description = "有无乳腺粗针穿刺（0: 无, 1: 有）")
    private Integer breastCoreNeedle;

    @Schema(description = "乳腺粗针穿刺病理结果")
    private String breastCoreNeedleResult;

    @Schema(description = "有无腋窝粗针穿刺（0: 无, 1: 有）")
    private Integer axillaryCoreNeedle;

    @Schema(description = "腋窝粗针穿刺病理结果")
    private String axillaryCoreNeedleResult;

    @Schema(description = "有无腋窝细针穿刺（0: 无, 1: 有）")
    private Integer axillaryFineNeedle;

    @Schema(description = "腋窝细针穿刺病理结果")
    private String axillaryFineNeedleResult;

    @Schema(description = "有无免疫组化结果（0: 无, 1: 有）")
    private Integer ihcResult;

    @Schema(description = "雌激素受体表达百分比（ER%）")
    private Float erPct;

    @Schema(description = "孕激素受体表达百分比（PR%）")
    private Float prPct;

    @Schema(description = "人类表皮生长因子受体2（HER2）")
    private String her2;

    @Schema(description = "Ki-67标志物百分比")
    private Float ki67Pct;

    @Schema(description = "雄激素受体表达百分比（AR%）")
    private Float arPct;

    @Schema(description = "FISH检测（阴性/阳性）")
    private String fishTest;

    @Schema(description = "TNM分期：cT/N/M")
    private String tnm;

    @Schema(description = "分期：无/I/II/III")
    private String stage;

    @Schema(description = "分型：三阴性/Luminal A/Luminal B HER2阴性型/Luminal B HER2阳性型")
    private String subtype;

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
