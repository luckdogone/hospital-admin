package com.spring.admin.modules.sys.core.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.spring.admin.base.R;
import com.spring.admin.core.service.BaseServiceImpl;
import com.spring.admin.modules.sys.core.mapper.CaseInfoMapper;
import com.spring.admin.modules.sys.core.model.entity.AdjuvantInfo;
import com.spring.admin.modules.sys.core.model.entity.CaseInfo;
import com.spring.admin.modules.sys.core.model.entity.GeneralInfo;
import com.spring.admin.modules.sys.core.model.query.CaseInfoQuery;
import com.spring.admin.modules.sys.core.model.query.GeneralInfoQuery;
import com.spring.admin.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 李飞洋
 * @date 2024/8/13
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CaseInfoService extends BaseServiceImpl<CaseInfoMapper, CaseInfo> {

    /**
     * 获取一般信息
     *
     * @param query .
     * @return .
     */
    public List<CaseInfo> queryList(CaseInfoQuery query) {
        return this.baseMapper.queryPage(null, query);
    }

    /**
     * 保存信息
     *
     * @param caseInfo .
     * @return .
     */
    public R<CaseInfo> saveCaseInfo(CaseInfo caseInfo) {
        caseInfo.setCreatedBy(SecurityUtil.getCurrentUsername());
        caseInfo.setCreated(LocalDateTime.now());
        caseInfo.setInputStatus(0);
        caseInfo.setIsEnable(1);
        caseInfo.setIsDel(1);
        // set Id
        caseInfo.setId(IdUtil.fastSimpleUUID());
        // save
        save(caseInfo);
        return R.OK(caseInfo);
    }

    /**
     * 更新一般信息
     *
     * @param id .
     * @param vo .
     * @return .
     */
    public R<CaseInfo> updateById(String id, CaseInfo vo) {
        CaseInfo caseInfo = getById(id);
        System.out.println(caseInfo);
        BeanUtil.copyProperties(vo, caseInfo, "id","inputStatus","isEnable","isDel");
        caseInfo.setInputStatus(2);
        updateById(caseInfo);
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
        List<CaseInfo> generalInfos = this.listByIds(ids);
        if (CollectionUtil.isEmpty(generalInfos)) {
            return R.NG("信息不存在");
        }
        this.removeByIds(ids);
        return R.OK();
    }

    public List<String> getIdsByPatientId(String patientId, Integer isDel) {
        return this.baseMapper.getIdsByPatientId(patientId, isDel);
    }

    public List<CaseInfo> getInfoByPatientId(String patientId, Integer isDel) {
        return this.baseMapper.getInfoByPatientId(patientId, isDel);
    }
}
