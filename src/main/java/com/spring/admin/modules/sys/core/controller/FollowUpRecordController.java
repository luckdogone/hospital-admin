package com.spring.admin.modules.sys.core.controller;


import com.spring.admin.base.R;
import com.spring.admin.data.domain.BasePage;
import com.spring.admin.modules.sys.core.model.entity.FollowUpRecord;
import com.spring.admin.modules.sys.core.model.query.FollowQuery;
import com.spring.admin.modules.sys.core.model.vo.FollowUpRecordVO;
import com.spring.admin.modules.sys.core.model.vo.PatientInfoVO;
import com.spring.admin.modules.sys.core.service.FollowUpRecordService;
import com.spring.admin.security.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/core/follow")
@Tag(name = "功能：随访记录")
@RequiredArgsConstructor
@Slf4j
public class FollowUpRecordController {
    private final FollowUpRecordService followUpRecordService;

    @GetMapping("/query/page")
    @Operation(summary = "分页查询")
    public R<BasePage<PatientInfoVO>> getListPage(HttpServletRequest request, @ParameterObject FollowQuery query) {
        BasePage<PatientInfoVO> page = followUpRecordService.queryPage(query);
        return R.OK(page);
    }

    @GetMapping("/query/list")
    @Operation(summary = "列表查询")
    public R<List<PatientInfoVO>> getList(HttpServletRequest request, @ParameterObject FollowQuery query) {
        List<PatientInfoVO> res = followUpRecordService.queryList(query);
        return R.OK(res);
    }

    @GetMapping("/query/follow")
    @Operation(summary = "通过patientId查询随访记录")
    public R<List<FollowUpRecord>> getListFollow(HttpServletRequest request, @RequestParam String patientId) {
        List<FollowUpRecord> res = followUpRecordService.queryByPatientId(patientId);
        return R.OK(res);
    }

    @GetMapping("/query/pending")
    @Operation(summary = "查询待随访记录")
    public R<BasePage<FollowUpRecordVO>> getPendingVisitRecords(HttpServletRequest request, @ParameterObject FollowQuery query) {
        BasePage<FollowUpRecordVO> page = followUpRecordService.queryPendingVisitRecords(query);
        return R.OK(page);
    }

    @PostMapping("/save")
    @Operation(summary = "保存随访记录")
    public R<FollowUpRecordVO> saveFollowUpRecord(@RequestBody FollowUpRecordVO vo) {
        return followUpRecordService.saveFollowUpRecord(vo);
    }

    @PutMapping("/update")
    @Operation(summary = "更新随访记录")
    public R<FollowUpRecordVO> updateFollowUpRecord(HttpServletRequest request, @RequestParam String id, @RequestBody FollowUpRecordVO vo) {
        vo.setModifiedBy(SecurityUtil.getCurrentUsername());
        vo.setModified(LocalDateTime.now());
        return followUpRecordService.updateFollowUpRecord(id, vo);
    }

    @DeleteMapping("/del")
    @Operation(summary = "删除信息")
    public R<String> del(HttpServletRequest request, @RequestBody List<String> ids) {
        return followUpRecordService.deleteByIds(ids);
    }
}