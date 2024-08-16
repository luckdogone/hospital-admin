package com.spring.admin.modules.sys.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.admin.modules.sys.core.model.entity.EndocrineInfo;
import com.spring.admin.modules.sys.core.model.query.EndocrineInfoQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 李飞洋
 * @date 2024/8/17
 */
public interface EndocrineInfoMapper extends BaseMapper<EndocrineInfo> {

    /**
     * 分页查询
     *
     * @param page  .
     * @param query .
     * @return .
     */
    List<EndocrineInfo> queryPage(Page<EndocrineInfo> page, @Param("query") EndocrineInfoQuery query);

    List<String> getIdsByPatientId(String patientId, Integer isDel);
}
