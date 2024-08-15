package com.spring.admin.modules.sys.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.admin.modules.sys.core.model.entity.CaseInfo;
import com.spring.admin.modules.sys.core.model.query.CaseInfoQuery;
import com.spring.admin.modules.sys.core.model.query.GeneralInfoQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 李飞洋
 * @date 2024/8/13
 */
public interface CaseInfoMapper extends BaseMapper<CaseInfo> {

    /**
     * 分页查询
     *
     * @param page  .
     * @param query .
     * @return .
     */
    List<CaseInfo> queryPage(Page<CaseInfo> page, @Param("query") CaseInfoQuery query);

    List<String> getIdsByPatientId(String patientId, Integer isDel);
}