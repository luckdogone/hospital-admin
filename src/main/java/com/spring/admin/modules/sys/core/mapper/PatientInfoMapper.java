package com.spring.admin.modules.sys.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.admin.modules.sys.core.model.entity.PatientInfo;
import com.spring.admin.modules.sys.core.model.query.PatientInfoQuery;
import com.spring.admin.modules.sys.core.model.vo.PatientListInfoVO;
import com.spring.admin.modules.sys.core.model.vo.SearchQueryDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 基础信息Mapper
 *
 * @author 李飞洋
 * @date 2024/8/1
 */
public interface PatientInfoMapper extends BaseMapper<PatientInfo> {

    /**
     * 分页查询
     *
     * @param page  .
     * @param query .
     * @return .
     */
    List<PatientListInfoVO> queryPageList(Page<PatientListInfoVO> page, @Param("query") PatientInfoQuery query);

    /**
     * 分页查询
     *
     * @param page  .
     * @param query .
     * @return .
     */
    List<PatientInfo> queryPage(Page<PatientInfo> page, @Param("query") PatientInfoQuery query);

    /**
     * 分页查询
     *
     * @param page  .
     * @param searchQuery .
     * @return .
     */
    List<PatientInfo> searchQueryPage(Page<PatientInfo> page, @Param("searchQuery") SearchQueryDTO searchQuery);

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
