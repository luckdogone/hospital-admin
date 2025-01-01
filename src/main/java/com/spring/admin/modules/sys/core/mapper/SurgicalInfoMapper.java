package com.spring.admin.modules.sys.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.admin.modules.sys.core.model.entity.RadiationInfo;
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

    List<SurgicalInfo> getInfoByPatientId(String patientId, Integer isDel);

    /**
     * 获取相关表的input_status
     *
     * @param patientId 患者ID
     * @return 包含各相关表input_status的Map
     */
    List<Integer> getRelatedInputStatus(@Param("patientId") String patientId);

    /**
     * 更新患者信息的input_status
     *
     * @param id 患者ID
     * @param inputStatus 新的input_status值
     * @return 更新的记录数
     */
    int updatePatientInputStatus(@Param("id") String id, @Param("inputStatus") Integer inputStatus);
}
