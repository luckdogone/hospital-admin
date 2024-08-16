package com.spring.admin.modules.sys.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.admin.modules.sys.core.model.entity.SurgicalInfo;
import com.spring.admin.modules.sys.core.model.query.SurgicalInfoQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 李飞洋
 * @date 2024/8/16
 */
public interface SurgicalInfoMapper extends BaseMapper<SurgicalInfo> {

    /**
     * 分页查询
     *
     * @param page  .
     * @param query .
     * @return .
     */
    List<SurgicalInfo> queryPage(Page<SurgicalInfo> page, @Param("query") SurgicalInfoQuery query);

    List<String> getIdsByPatientId(String patientId, Integer isDel);
}
