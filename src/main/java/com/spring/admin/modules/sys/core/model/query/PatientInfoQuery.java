package com.spring.admin.modules.sys.core.model.query;

import com.spring.admin.data.domain.BasePageQuery;
import io.swagger.v3.oas.annotations.Parameter;
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
public class PatientInfoQuery extends BasePageQuery {

    /**
     * 患者姓名
     */
    @Parameter(description = "患者姓名")
    private String name;

    /**
     * 电话
     */
    @Parameter(description = "电话")
    private String phone;

    /**
     * 联系人电话
     */
    @Parameter(description = "联系人电话")
    private String contactPhone;

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
