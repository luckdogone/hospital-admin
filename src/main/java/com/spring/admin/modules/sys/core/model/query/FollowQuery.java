package com.spring.admin.modules.sys.core.model.query;


import com.spring.admin.data.domain.BasePageQuery;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class FollowQuery extends BasePageQuery {

    /**
     * 姓名
     */
    @Parameter(description = "姓名")
    private String name;

    /**
     * 电话
     */
    @Parameter(description = "电话")
    private String phone;

    /**
     * 病案号
     */
    @Parameter(description = "病案号")
    private String caseNo;

    /**
     * 手术编号
     */
    @Parameter(description = "手术编号")
    private String surgicalNum;

//    /**
//     * 手术日期
//     */
//    @Parameter(description = "手术日期")
//    private Date surgeryDate;
}
