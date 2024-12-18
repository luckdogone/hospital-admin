package com.spring.admin.modules.sys.system.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**树形结构-机构信息
 * 
 * @date 2023/6/3
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class OrganizationTree extends OrganizationVO {
    @Schema(description = "子节点")
    private List<OrganizationTree> children=new ArrayList<>();

}
