package com.spring.admin.modules.sys.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.admin.modules.sys.core.model.entity.CaseInfo;
import com.spring.admin.modules.sys.core.model.entity.NeoadjuvantInfo;
import com.spring.admin.modules.sys.core.model.query.NeoadjuvantInfoQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 李飞洋
 * @date 2024/8/16
 */
public interface NeoadjuvantInfoMapper extends BaseMapper<NeoadjuvantInfo> {
    /**
     * 分页查询
     *
     * @param page  .
     * @param query .
     * @return .
     */
    List<NeoadjuvantInfo> queryPage(Page<NeoadjuvantInfo> page, @Param("query") NeoadjuvantInfoQuery query);

    List<String> getIdsByPatientId(String patientId, Integer isDel);
}
