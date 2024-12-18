package com.spring.admin.modules.sys.system.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * 菜单路由 mate
 *
 * 
 * @date 2023/2/6
 */
@Data
@EqualsAndHashCode
@ToString
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class VueMenuRouteMeta implements Serializable {
    /**
     * title
     */
    @Schema(description = "title")
    private String title;
    /**
     * 菜单图标
     */
    @Schema(description = "菜单图标")
    private String icon;
    /**
     * 是否显示该菜单
     */
    @Schema(description = "是否显示该菜单")
    private Boolean showLink;
    /**
     * 菜单排序，值越高排的越后（只针对顶级路由）
     */
    @Schema(description = "菜单排序")
    private Integer rank;
    /**
     * 是否显示父级菜单
     */
    @Schema(description = "是否显示父级菜单")
    private Boolean showParent;
    /**
     * 是否缓存该路由页面（开启后，会保存该页面的整体状态，刷新后会清空状态）
     */
    @Schema(description = "是否缓存该路由页面")
    private Boolean keepAlive;
    /**
     * 需要内嵌的iframe链接地址
     */
    @Schema(description = "需要内嵌的iframe链接地址")
    private String frameSrc;
}
