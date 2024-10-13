package com.spring.admin.modules.sys.core.controller;


import com.spring.admin.base.R;
import com.spring.admin.data.domain.BasePage;
import com.spring.admin.modules.sys.core.model.entity.*;
import com.spring.admin.modules.sys.core.model.query.PatientInfoQuery;
import com.spring.admin.modules.sys.core.model.vo.SearchQueryDTO;
import com.spring.admin.modules.sys.core.service.PatientInfoService;
import com.spring.admin.security.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 李飞洋
 * @date 2024/8/1
 */

@RestController
@RequestMapping("/core/patient")
@Tag(name = "功能：基本信息")
@RequiredArgsConstructor
public class PatientInfoController {
    private final PatientInfoService patientInfoService;

    @PostMapping("/query/search")
    @PreAuthorize("hasAuthority('search:query')")
    public R<BasePage<PatientInfo>> searchQueryPage(@RequestBody SearchQueryDTO searchQuery) {
        System.out.println(searchQuery);
        BasePage<PatientInfo> page = patientInfoService.searchQueryPage(searchQuery);
        return R.OK(page);
        // 这里 searchQuery 是包含 PatientInfo、GeneralInfo 等信息的对象
//        PatientInfo patientInfo = searchQuery.getPatientInfo();
//        GeneralInfo generalInfo = searchQuery.getGeneralInfo();
//        CaseInfo caseInfo = searchQuery.getCaseInfo();
//        SurgicalInfo surgicalInfo = searchQuery.getSurgicalInfo();
//        AdjuvantInfo adjuvantInfo = searchQuery.getAdjuvantInfo();
//        EndocrineInfo endocrineInfo = searchQuery.getEndocrineInfo();
//        NeoadjuvantInfo neoadjuvantInfo = searchQuery.getNeoadjuvantInfo();
//        RadiationInfo radiationInfo = searchQuery.getRadiationInfo();
//        return R.OK("success");
    }

    @GetMapping("/query/excel")
    @Operation(summary = "列表")
//    @PreAuthorize("hasAuthority('patient:query:list')")
    public R<List<PatientInfo>> getExcelList(HttpServletRequest request, @ParameterObject PatientInfoQuery query) {
        List<PatientInfo> res = patientInfoService.queryList(query);
        return R.OK(res);
    }


    @GetMapping("/query/page")
    @Operation(summary = "分页查询")
    @PreAuthorize("hasAuthority('patient:query:page')")
    public R<BasePage<PatientInfo>> getListPage(HttpServletRequest request, @ParameterObject PatientInfoQuery query) {
        BasePage<PatientInfo> page = patientInfoService.queryPage(query);
        return R.OK(page);
    }

    @GetMapping("/query/list")
    @Operation(summary = "列表")
    @PreAuthorize("hasAuthority('patient:query:list')")
    public R<List<PatientInfo>> getList(HttpServletRequest request, @ParameterObject PatientInfoQuery query) {
        List<PatientInfo> res = patientInfoService.queryList(query);
        return R.OK(res);
    }

    @PostMapping("/save")
    @Operation(summary = "保存信息")
    @PreAuthorize("hasAuthority('patient:save')")
    public R<PatientInfo> save(HttpServletRequest request, @RequestBody PatientInfo vo) {
        return patientInfoService.savePatientInfo(vo);
    }

    @PutMapping("/update")
    @Operation(summary = "更新信息")
    @PreAuthorize("hasAuthority('patient:update')")
    public R<PatientInfo> update(HttpServletRequest request, String id,
                                    @RequestBody PatientInfo vo) {
        vo.setModifiedBy(SecurityUtil.getCurrentUsername());
        vo.setModified(LocalDateTime.now());
        return patientInfoService.updateById(id, vo);
    }

    @DeleteMapping("/del")
    @Operation(summary = "删除信息")
    @PreAuthorize("hasAuthority('patient:del')")
    public R<String> del(HttpServletRequest request, @RequestBody List<String> ids) {
        return patientInfoService.batchDel(ids);
    }
}
