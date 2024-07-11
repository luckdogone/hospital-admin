package com.spring.admin.modules.sys.system.model.query;

import com.spring.admin.data.domain.BasePageQuery;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 机构查询
 *
 * 
 * @date 2023/6/3
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class OrganizationQuery extends BasePageQuery {
    /**
     * 所属机构
     */
    @Parameter(description = "所属机构ID")
    private String parentId;
    /**
     * 机构名称
     */
    @Parameter(description = "机构名称")
    private String name;
    /**
     * 机构编码
     */
    @Parameter(description = "机构编码")
    private String code;
    /**
     * 联系人电话
     */
    @Parameter(description = "联系人电话")
    private String linkTel;
    /**
     * 状态
     */
    @Parameter(description = "状态")
    private Integer isEnable;

}
