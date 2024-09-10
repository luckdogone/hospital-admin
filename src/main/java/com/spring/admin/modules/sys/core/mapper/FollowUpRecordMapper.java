package com.spring.admin.modules.sys.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.admin.modules.sys.core.model.entity.FollowUpRecord;
import com.spring.admin.modules.sys.core.model.query.FollowQuery;
import com.spring.admin.modules.sys.core.model.vo.FollowUpRecordVO;
import com.spring.admin.modules.sys.core.model.vo.PatientInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface FollowUpRecordMapper extends BaseMapper<FollowUpRecord> {

    /**
     * 分页查询
     *
     * @param page  .
     * @param query .
     * @return .
     */
    List<PatientInfoVO> queryPage(Page<PatientInfoVO> page, @Param("query") FollowQuery query);

    /**
     * 通过patientId获取该患者的全部随访记录
     *
     * @param patientId  .
     * @return .
     */
    List<FollowUpRecord> queryByPatientId(@Param("patientId") String patientId);

    /**
     * 获取全部待随访的记录
     *
     * @return .
     */
    List<FollowUpRecordVO> queryPendingVisitRecords(Page<FollowUpRecordVO> page, @Param("query") FollowQuery query);

    int addFollowUpRecord(FollowUpRecord followUpRecord);

}
