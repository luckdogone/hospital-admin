package com.spring.admin.modules.sys.core.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.admin.base.R;
import com.spring.admin.core.service.BaseServiceImpl;
import com.spring.admin.data.domain.BasePage;
import com.spring.admin.modules.sys.core.mapper.PatientInfoMapper;
import com.spring.admin.modules.sys.core.model.entity.CaseInfo;
import com.spring.admin.modules.sys.core.model.entity.GeneralInfo;
import com.spring.admin.modules.sys.core.model.entity.PatientInfo;
import com.spring.admin.modules.sys.core.model.query.GeneralInfoQuery;
import com.spring.admin.modules.sys.core.model.query.PatientInfoQuery;
import com.spring.admin.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 李飞洋
 * @date 2024/8/1
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class PatientInfoService extends BaseServiceImpl<PatientInfoMapper, PatientInfo> {

    private final GeneralInfoService generalInfoService;
    private final CaseInfoService caseInfoService;
    /**
     * 获取基础信息分页
     *
     * @param query .
     * @return .
     */
    public BasePage<PatientInfo> queryPage(PatientInfoQuery query) {
        Page<PatientInfo> page = new Page<>(query.getCurrent(), query.getSize());
        List<PatientInfo> records = this.baseMapper.queryPage(page, query);
        return new BasePage<>(page.getCurrent(), page.getSize(), page.getTotal(), records);
    }

    /**
     * 获取基础信息
     *
     * @param query .
     * @return .
     */
    public List<PatientInfo> queryList(PatientInfoQuery query) {
        return this.baseMapper.queryPage(null, query);
    }

    private GeneralInfo addGeneralInfo() {
        GeneralInfo generalInfo = new GeneralInfo();
        generalInfo.setCreatedBy(SecurityUtil.getCurrentUsername());
        generalInfo.setCreated(LocalDateTime.now());
        generalInfo.setInputStatus(0);
        generalInfo.setIsEnable(1);
        generalInfo.setIsDel(1);
        return generalInfo;
    }

    private CaseInfo addCaseInfo() {
        CaseInfo caseInfo = new CaseInfo();
        caseInfo.setCreatedBy(SecurityUtil.getCurrentUsername());
        caseInfo.setCreated(LocalDateTime.now());
        caseInfo.setInputStatus(0);
        caseInfo.setIsEnable(1);
        caseInfo.setIsDel(1);
        return caseInfo;
    }

    /**
     * 保存信息
     *
     * @param patientInfo .
     * @return .
     */
    @Transactional(rollbackFor = Exception.class)
    public R<PatientInfo> savePatientInfo(PatientInfo patientInfo) {
        try {
            // Set Id
            patientInfo.setId(IdUtil.fastSimpleUUID());
            // 保存患者信息
            save(patientInfo);

            GeneralInfo generalInfo = addGeneralInfo();
            CaseInfo caseInfo = addCaseInfo();

            // 在相关信息中设置患者ID
            generalInfo.setPatientId(patientInfo.getId());
            caseInfo.setPatientId(patientInfo.getId());

            // 保存一般信息和病历信息
            generalInfoService.saveGeneralInfo(generalInfo);
            caseInfoService.saveCaseInfo(caseInfo);

            return R.OK(patientInfo);

        } catch (Exception e) {
            // 记录异常并返回错误响应
            log.error("保存患者信息时出错： ", e);
            return R.NG("新增患者失败，请重试");
        }
    }

    /**
     * 更新基础信息
     *
     * @param id .
     * @param vo .
     * @return .
     */
    public R<PatientInfo> updateById(String id, PatientInfo vo) {
        PatientInfo patientInfo = getById(id);
        BeanUtil.copyProperties(vo, patientInfo, "id");
        updateById(patientInfo);
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
        List<PatientInfo> patientInfos = this.listByIds(ids);
        if (CollectionUtil.isEmpty(patientInfos)) {
            return R.NG("信息不存在");
        }
        this.removeByIds(ids);

        // 初始化集合用于存储关联的 CaseInfo 和 GeneralInfo 的 id
        List<String> caseIds = new ArrayList<>();
        List<String> generalInfoIds = new ArrayList<>();

        // 使用一次循环获取关联的 CaseInfo 和 GeneralInfo 的 id
        for (String id : ids) {
            caseIds.addAll(caseInfoService.getIdsByPatientId(id, 1));
            generalInfoIds.addAll(generalInfoService.getIdsByPatientId(id, 1));
        }

        // 执行批量删除操作
        if (!generalInfoIds.isEmpty()) {
            generalInfoService.batchDel(generalInfoIds);
        }
        if (!caseIds.isEmpty()) {
            caseInfoService.batchDel(caseIds);
        }
        return R.OK();
    }
}
