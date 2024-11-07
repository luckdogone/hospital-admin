package com.spring.admin.modules.sys.core.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.spring.admin.data.domain.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;

/**
 * @author 李飞洋
 * @date 2024/8/16
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class SurgicalInfo extends BaseEntity {

    /**
     * 患者ID
     */
    @Schema(description = "患者ID")
    private String patientId;

    /**
     * 手术时间
     */
    @Schema(description = "手术时间")
    private LocalDate surgeryTime;

    /**
     * 手术部位
     */
    @Schema(description = "手术部位")
    private String surgicalSite;

    // 乳腺相关字段
    @Schema(description = "是否全切/保乳")
    private Integer isTotalPartial;

    @Schema(description = "是否保乳整形")
    private Integer isBreastConserve;

    @Schema(description = "是否保留乳头乳晕")
    private Integer isNippleSpare;

    @Schema(description = "是否腔镜")
    private Integer isEndoscopic;

    @Schema(description = "是否重建")
    private Integer isReconstruction;

    @Schema(description = "重建方式: 自体/假体")
    private String reconMethod;

    @Schema(description = "大小: cm")
    private Double tumorSizeCm;

    @Schema(description = "有无脉管癌栓")
    private Integer hasLymphInvasion;

    @Schema(description = "有无神经侵犯")
    private Integer hasNerveInvasion;

    @Schema(description = "术后病理分级: 无/I/II/III")
    private String postopPathGrade;

    @Schema(description = "是否多发")
    private Integer isMultifocal;

    @Schema(description = "肿瘤位置: 中央区/外上象限/内上象限/外下象限/内下象限")
    private String tumorLocation;

    // 淋巴结相关字段
    @Schema(description = "是否前哨淋巴结活检")
    private Integer hasSlnBiopsy;

    @Schema(description = "是否使用核素")
    private Integer useNs;

    @Schema(description = "是否新辅助前TAD")
    private Integer preTad;

    @Schema(description = "前哨淋巴结数量")
    private Integer slnCount;

    @Schema(description = "是否前哨淋巴结转移")
    private Integer slnMeta;

    @Schema(description = "前哨淋巴结转移数量")
    private Integer slnMetaCount;

    @Schema(description = "前哨微转移数量")
    private Integer slnMicroMetaCount;

    @Schema(description = "前哨孤立肿瘤细胞数量")
    private Integer slnItcCount;

    @Schema(description = "是否腋窝清扫")
    private Integer ald;

    @Schema(description = "腋窝清扫：I/II/III站")
    private String aldStage;

    @Schema(description = "腋窝清扫淋巴结数量")
    private Integer aldCount;

    @Schema(description = "腋窝淋巴结转移数量")
    private Integer aldMetaCount;

    @Schema(description = "腋窝微转移数量")
    private Integer aldMicroMetaCount;

    @Schema(description = "腋窝孤立肿瘤细胞数量")
    private Integer aldItcCount;

    @Schema(description = "是否有腋尖淋巴结转移")
    private Integer apexMeta;

    @Schema(description = "是否有肌间淋巴结转移")
    private Integer imMeta;

    // 免疫组化结果
    @Schema(description = "有无免疫组化结果（0:无, 1:有 无状态不会有下列属性的值）")
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

    // TNM分期
    @Schema(description = "TNM分期：cT/N/M")
    private String tnm;

    @Schema(description = "分期：无/I/II/III")
    private String stage;

    // 亚型分型
    @Schema(description = "分型：三阴性/Luminal A/Luminal B HER2阴性型/Luminal B HER2阳性型")
    private String subtype;

    // 特殊类型肿瘤
    @Schema(description = "特殊类型肿瘤")
    private String specialTypeTumors;

    // MP级别/级
    @Schema(description = "MP级别: 无浸润(MP0)/微浸润(MP1)/中度浸润(MP2)/重度浸润(MP3)/完全浸润(MP4)")
    private Integer mpLevel;

    @Schema(description = "RCB评分(选项：0,1,2,3)")
    private Integer rcb;

    @Schema(description = "P120")
    @TableField("p_120")
    private String p120;

    @Schema(description = "E-cad")
    private String eCad;

    @Schema(description = "CK5/6")
    @TableField("ck_5_6")
    private String ck56;

    @Schema(description = "GATA3")
    @TableField("gata_3")
    private Integer gata3;

    @Schema(description = "P63")
    @TableField("p_63")
    private String p63;

    @Schema(description = "P53")
    @TableField("p_53")
    private String p53;

    @Schema(description = "TRPS1")
    @TableField("trps_1")
    private String trps1;

    @Schema(description = "SAM")
    private String sam;

    @Schema(description = "EGFR")
    private String egfr;

    // 入录信息
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
