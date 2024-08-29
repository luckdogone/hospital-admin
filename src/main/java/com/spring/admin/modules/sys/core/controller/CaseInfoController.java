package com.spring.admin.modules.sys.core.controller;

import com.spring.admin.base.R;
import com.spring.admin.modules.sys.core.model.entity.CaseInfo;
import com.spring.admin.modules.sys.core.model.entity.GeneralInfo;
import com.spring.admin.modules.sys.core.model.query.CaseInfoQuery;
import com.spring.admin.modules.sys.core.service.CaseInfoService;
import com.spring.admin.modules.sys.core.service.GeneralInfoService;
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
 * @date 2024/8/13
 */
@RestController
@RequestMapping("/core/case")
@Tag(name = "功能：病历信息")
@RequiredArgsConstructor
public class CaseInfoController {
    private final CaseInfoService caseInfoService;

    @GetMapping("/query/list")
    @Operation(summary = "列表")
//    @PreAuthorize("hasAuthority('general:query')")
    public R<List<CaseInfo>> getList(HttpServletRequest request, @ParameterObject CaseInfoQuery query) {
        List<CaseInfo> res = caseInfoService.queryList(query);
        return R.OK(res);
    }

    @PostMapping("/save")
    @Operation(summary = "保存信息")
//    @PreAuthorize("hasAuthority('org:save')")
    public R<CaseInfo> save(HttpServletRequest request, @RequestBody CaseInfo vo) {
        return caseInfoService.saveCaseInfo(vo);
    }

    @PutMapping("/update")
    @Operation(summary = "更新信息")
    @PreAuthorize("hasAuthority('general:update')")
    public R<CaseInfo> update(HttpServletRequest request, String id,
                                 @RequestBody CaseInfo vo) {
        vo.setModifiedBy(SecurityUtil.getCurrentUsername());
        vo.setModified(LocalDateTime.now());
        return caseInfoService.updateById(id, vo);
    }

    @DeleteMapping("/del")
    @Operation(summary = "删除信息")
//    @PreAuthorize("hasAuthority('org:del')")
    public R<String> del(HttpServletRequest request, @RequestBody List<String> ids) {
        return caseInfoService.batchDel(ids);
    }
}
