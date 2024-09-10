package com.spring.admin.modules.sys.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.admin.modules.sys.core.model.entity.GeneralInfo;
import com.spring.admin.modules.sys.core.model.query.GeneralInfoQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 李飞洋
 * @date 2024/8/6
 */
public interface GeneralInfoMapper extends BaseMapper<GeneralInfo> {

    /**
     * 分页查询
     *
     * @param page  .
     * @param query .
     * @return .
     */
    List<GeneralInfo> queryPage(Page<GeneralInfo> page, @Param("query") GeneralInfoQuery query);

    List<String> getIdsByPatientId(String patientId, Integer isDel);

    List<GeneralInfo> getInfoByPatientId(String patientId, Integer isDel);
}
