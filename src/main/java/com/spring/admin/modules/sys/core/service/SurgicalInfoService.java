package com.spring.admin.modules.sys.core.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.spring.admin.base.R;
import com.spring.admin.core.service.BaseServiceImpl;
import com.spring.admin.modules.sys.core.mapper.CaseInfoMapper;
import com.spring.admin.modules.sys.core.mapper.SurgicalInfoMapper;
import com.spring.admin.modules.sys.core.model.entity.CaseInfo;
import com.spring.admin.modules.sys.core.model.entity.RadiationInfo;
import com.spring.admin.modules.sys.core.model.entity.SurgicalInfo;
import com.spring.admin.modules.sys.core.model.query.SurgicalInfoQuery;
import com.spring.admin.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 李飞洋
 * @date 2024/8/16
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SurgicalInfoService extends BaseServiceImpl<SurgicalInfoMapper, SurgicalInfo> {
    /**
     * 获取一般信息
     *
     * @param query .
     * @return .
     */
    public List<SurgicalInfo> queryList(SurgicalInfoQuery query) {
        return this.baseMapper.queryPage(null, query);
    }

    /**
     * 保存信息
     *
     * @param surgicalInfo .
     * @return .
     */
    public R<SurgicalInfo> saveSurgicalInfo(SurgicalInfo surgicalInfo) {
        surgicalInfo.setCreatedBy(SecurityUtil.getCurrentUsername());
        surgicalInfo.setCreated(LocalDateTime.now());
        surgicalInfo.setInputStatus(0);
        surgicalInfo.setIsEnable(1);
        surgicalInfo.setIsDel(1);
        // set Id
        surgicalInfo.setId(IdUtil.fastSimpleUUID());
        // save
        save(surgicalInfo);
        return R.OK(surgicalInfo);
    }

    /**
     * 更新一般信息
     *
     * @param id .
     * @param vo .
     * @return .
     */
    public R<SurgicalInfo> updateById(String id, SurgicalInfo vo) {
        SurgicalInfo surgicalInfo = getById(id);
        BeanUtil.copyProperties(vo, surgicalInfo, "id","inputStatus","isEnable","isDel");
        surgicalInfo.setInputStatus(2);
        updateById(surgicalInfo);
        List<Integer> allInputStatus = this.baseMapper.getRelatedInputStatus(vo.getPatientId());
        boolean allStatusesAreTwo = allInputStatus.stream().allMatch(status -> status == 2);
        if (allStatusesAreTwo) {
            this.baseMapper.updatePatientInputStatus(vo.getPatientId(), 2);
        } else {
            this.baseMapper.updatePatientInputStatus(vo.getPatientId(), 1);
        }
        return R.OK(vo);
    }

    /**
     * 批量删除
     *
     * @param ids .
     * @return .
     */
    @Transactional(rollbackFor = Exception.class)
    public R<String> batchDel(List<String> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return R.NG("参数为空");
        }
        List<SurgicalInfo> surgicalInfos = this.listByIds(ids);
        if (CollectionUtil.isEmpty(surgicalInfos)) {
            return R.NG("信息不存在");
        }
        this.removeByIds(ids);
        return R.OK();
    }

    public List<String> getIdsByPatientId(String patientId, Integer isDel) {
        return this.baseMapper.getIdsByPatientId(patientId, isDel);
    }

    public List<SurgicalInfo> getInfoByPatientId(String patientId, Integer isDel) {
        return this.baseMapper.getInfoByPatientId(patientId, isDel);
    }
}
