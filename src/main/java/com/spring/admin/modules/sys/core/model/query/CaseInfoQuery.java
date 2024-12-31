package com.spring.admin.modules.sys.core.model.query;

import com.spring.admin.data.domain.BasePageQuery;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author 李飞洋
 * @date 2024/8/12
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class CaseInfoQuery extends BasePageQuery {

    /**
     * 患者ID
     */
    @Parameter(description = "患者ID")
    private String patientId;

    /**
     * 录入状态
     */
    @Parameter(description = "录入状态")
    private Integer inputStatus;

    /**
     * 是否启用 (0: 禁用, 1: 启用)
     */
    @Parameter(description = "是否启用")
    private Integer isEnable;

    /**
     * 是否删除 (0: 删除, 1: 正常)
     */
    @Parameter(description = "是否删除")
    private Integer isDel;
}
