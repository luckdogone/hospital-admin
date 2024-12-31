package com.spring.admin.modules.sys.core.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.admin.base.R;
import com.spring.admin.core.service.BaseServiceImpl;
import com.spring.admin.data.domain.BasePage;
import com.spring.admin.modules.sys.core.mapper.PatientInfoMapper;
import com.spring.admin.modules.sys.core.model.entity.*;
import com.spring.admin.modules.sys.core.model.query.GeneralInfoQuery;
import com.spring.admin.modules.sys.core.model.query.PatientInfoQuery;
import com.spring.admin.modules.sys.core.model.vo.PatientInfoVO;
import com.spring.admin.modules.sys.core.model.vo.SearchQueryDTO;
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
    private final SurgicalInfoService surgicalInfoService;
    private final AdjuvantInfoService adjuvantInfoService;;
    private final RadiationInfoService radiationInfoService;
    private final EndocrineInfoService endocrineInfoService;
    private final NeoadjuvantInfoService neoadjuvantInfoService;

    public BasePage<PatientInfo> searchQueryPage(SearchQueryDTO searchQuery) {
        Page<PatientInfo> page = new Page<>(searchQuery.getCurrent(), searchQuery.getSize());
//        System.out.println(searchQuery.getCurrent());
//        System.out.println(searchQuery.getSize());
        List<PatientInfo> records = this.baseMapper.searchQueryPage(page, searchQuery);
//        System.out.println(records);
        return new BasePage<>(page.getCurrent(), page.getSize(), page.getTotal(), records);
    }

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

//    private GeneralInfo addGeneralInfo() {
//        GeneralInfo generalInfo = new GeneralInfo();
//        generalInfo.setCreatedBy(SecurityUtil.getCurrentUsername());
//        generalInfo.setCreated(LocalDateTime.now());
//        generalInfo.setInputStatus(0);
//        generalInfo.setIsEnable(1);
//        generalInfo.setIsDel(1);
//        return generalInfo;
//    }
//
//    private CaseInfo addCaseInfo() {
//        CaseInfo caseInfo = new CaseInfo();
//        caseInfo.setCreatedBy(SecurityUtil.getCurrentUsername());
//        caseInfo.setCreated(LocalDateTime.now());
//        caseInfo.setInputStatus(0);
//        caseInfo.setIsEnable(1);
//        caseInfo.setIsDel(1);
//        return caseInfo;
//    }

    /**
     * 保存信息
     *
     * @param patientInfoVO .
     * @return .
     */
    @Transactional(rollbackFor = Exception.class)
    public R<PatientInfo> savePatientInfo(PatientInfoVO patientInfoVO) {
        if (patientInfoVO == null) {
            return R.NG("患者信息不能为空");
        }
        patientInfoVO.setCreatedBy(SecurityUtil.getCurrentUsername());
        patientInfoVO.setCreated(LocalDateTime.now());
        patientInfoVO.setInputStatus(1);
        patientInfoVO.setIsEnable(1);
        patientInfoVO.setIsDel(1);

        try {
            // 设置患者ID
            patientInfoVO.setId(IdUtil.fastSimpleUUID());

            // 设置通用字段
            setCommonFields(patientInfoVO);

            PatientInfo patientInfo = new PatientInfo();

            BeanUtil.copyProperties(patientInfoVO, patientInfo);

            // 保存患者信息
            if (!save(patientInfo)) {
                return R.NG("保存患者信息失败");
            }

//            String patientId = patientInfo.getId();

            // 创建并保存各模块信息
            if (!createAndSaveModuleInfo(patientInfoVO)) {
                throw new RuntimeException("保存模块信息失败");
            }

            return R.OK(patientInfo);
        } catch (Exception e) {
            log.error("保存患者信息时出错： ", e);
            throw new RuntimeException("新增患者失败，请重试", e);
        }
    }

    private void setCommonFields(Object entity) {
        if (entity instanceof PatientInfo) {
            PatientInfo info = (PatientInfo) entity;
            info.setCreatedBy(SecurityUtil.getCurrentUsername());
            info.setCreated(LocalDateTime.now());
            info.setInputStatus(0);
            info.setIsEnable(1);
            info.setIsDel(1);
        }
    }

    private boolean createAndSaveModuleInfo(PatientInfoVO patientInfoVO) {
        return createAndSaveGeneralInfo(patientInfoVO) &&
                createAndSaveCaseInfo(patientInfoVO.getId()) &&
                createAndSaveSurgicalInfo(patientInfoVO.getId()) &&
                createAndSaveAdjuvantInfo(patientInfoVO.getId()) &&
                createAndSaveRadiationInfo(patientInfoVO.getId()) &&
                createAndSaveEndocrineInfo(patientInfoVO.getId()) &&
                createAndSaveNeoadjuvantInfo(patientInfoVO.getId());
    }

    private boolean createAndSaveGeneralInfo(PatientInfoVO patientInfoVO) {
        GeneralInfo generalInfo = new GeneralInfo();
        setCommonFields(generalInfo);
        generalInfo.setPatientId(patientInfoVO.getId());
        generalInfo.setSurgicalNum(patientInfoVO.getSurgicalNum());
        generalInfo.setCaseNo(patientInfoVO.getCaseNo());
        return generalInfoService.saveGeneralInfo(generalInfo) != null;
    }

    private boolean createAndSaveCaseInfo(String patientId) {
        CaseInfo caseInfo = new CaseInfo();
        setCommonFields(caseInfo);
        caseInfo.setPatientId(patientId);
        return caseInfoService.saveCaseInfo(caseInfo) != null;
    }

    private boolean createAndSaveSurgicalInfo(String patientId) {
        SurgicalInfo surgicalInfo = new SurgicalInfo();
        setCommonFields(surgicalInfo);
        surgicalInfo.setPatientId(patientId);
        return surgicalInfoService.saveSurgicalInfo(surgicalInfo) != null;
    }

    private boolean createAndSaveAdjuvantInfo(String patientId) {
        AdjuvantInfo adjuvantInfo = new AdjuvantInfo();
        setCommonFields(adjuvantInfo);
        adjuvantInfo.setPatientId(patientId);
        return adjuvantInfoService.saveAdjuvantInfo(adjuvantInfo) != null;
    }

    private boolean createAndSaveRadiationInfo(String patientId) {
        RadiationInfo radiationInfo = new RadiationInfo();
        setCommonFields(radiationInfo);
        radiationInfo.setPatientId(patientId);
        return radiationInfoService.saveRadiationInfo(radiationInfo) != null;
    }

    private boolean createAndSaveEndocrineInfo(String patientId) {
        EndocrineInfo endocrineInfo = new EndocrineInfo();
        setCommonFields(endocrineInfo);
        endocrineInfo.setPatientId(patientId);
        return endocrineInfoService.saveEndocrineInfo(endocrineInfo) != null;
    }

    private boolean createAndSaveNeoadjuvantInfo(String patientId) {
        NeoadjuvantInfo neoadjuvantInfo = new NeoadjuvantInfo();
        setCommonFields(neoadjuvantInfo);
        neoadjuvantInfo.setPatientId(patientId);
        return neoadjuvantInfoService.saveNeoadjuvantInfo(neoadjuvantInfo) != null;
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
        BeanUtil.copyProperties(vo, patientInfo, "id","inputStatus","isEnable","isDel");
        List<Integer> allInputStatus = this.baseMapper.getRelatedInputStatus(id);
        boolean allStatusesAreTwo = allInputStatus.stream().allMatch(status -> status == 2);
        if (allStatusesAreTwo) {
            patientInfo.setInputStatus(2);
        } else {
            patientInfo.setInputStatus(1);
        }
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

        try {
            List<PatientInfo> patientInfos = this.listByIds(ids);
            if (CollectionUtil.isEmpty(patientInfos)) {
                return R.NG("信息不存在");
            }

            // 删除患者信息
            if (!this.removeByIds(ids)) {
                throw new RuntimeException("删除患者信息失败");
            }

            // 删除所有相关模块的信息
            deleteRelatedModuleInfo(ids);

            return R.OK("批量删除成功");
        } catch (Exception e) {
            log.error("批量删除患者信息时出错： ", e);
            throw new RuntimeException("批量删除失败，请重试", e);
        }
    }

    private void deleteRelatedModuleInfo(List<String> patientIds) {
        for (String patientId : patientIds) {
            deleteGeneralInfo(patientId);
            deleteCaseInfo(patientId);
            deleteSurgicalInfo(patientId);
            deleteAdjuvantInfo(patientId);
            deleteRadiationInfo(patientId);
            deleteEndocrineInfo(patientId);
            deleteNeoadjuvantInfo(patientId);
        }
    }

    private void deleteGeneralInfo(String patientId) {
        List<String> ids = generalInfoService.getIdsByPatientId(patientId, 1);
        if (!ids.isEmpty()) {
            generalInfoService.batchDel(ids);
        }
    }

    private void deleteCaseInfo(String patientId) {
        List<String> ids = caseInfoService.getIdsByPatientId(patientId, 1);
        if (!ids.isEmpty()) {
            caseInfoService.batchDel(ids);
        }
    }

    private void deleteSurgicalInfo(String patientId) {
        List<String> ids = surgicalInfoService.getIdsByPatientId(patientId, 1);
        if (!ids.isEmpty()) {
            surgicalInfoService.batchDel(ids);
        }
    }

    private void deleteAdjuvantInfo(String patientId) {
        List<String> ids = adjuvantInfoService.getIdsByPatientId(patientId, 1);
        if (!ids.isEmpty()) {
            adjuvantInfoService.batchDel(ids);
        }
    }

    private void deleteRadiationInfo(String patientId) {
        List<String> ids = radiationInfoService.getIdsByPatientId(patientId, 1);
        if (!ids.isEmpty()) {
            radiationInfoService.batchDel(ids);
        }
    }

    private void deleteEndocrineInfo(String patientId) {
        List<String> ids = endocrineInfoService.getIdsByPatientId(patientId, 1);
        if (!ids.isEmpty()) {
            endocrineInfoService.batchDel(ids);
        }
    }

    private void deleteNeoadjuvantInfo(String patientId) {
        List<String> ids = neoadjuvantInfoService.getIdsByPatientId(patientId, 1);
        if (!ids.isEmpty()) {
            neoadjuvantInfoService.batchDel(ids);
        }
    }
}
