package com.spring.admin.data.domain;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

/**
 * 分页查询基类
 *
 * 
 * @date 2023/1/12
 */
@Data
@EqualsAndHashCode
@ToString
public class BasePageQuery implements Serializable {
    @Parameter(description = "分页size")
    @Schema(description = "分页size")
    private long size;
    @Parameter(description = "当前页数")
    @Schema(description = "当前页数")
    private long current;
}
