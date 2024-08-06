package com.spring.admin.modules.sys.core.controller;

import com.spring.admin.base.R;
import com.spring.admin.modules.sys.core.model.entity.GeneralInfo;
import com.spring.admin.modules.sys.core.model.entity.PatientInfo;
import com.spring.admin.modules.sys.core.model.query.GeneralInfoQuery;
import com.spring.admin.modules.sys.core.model.query.PatientInfoQuery;
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
 * @date 2024/8/7
 *
 */

@RestController
@RequestMapping("/core/general")
@Tag(name = "功能：一般信息")
@RequiredArgsConstructor
public class GeneralInfoController {
    private final GeneralInfoService generalInfoService;

    @GetMapping("/query/list")
    @Operation(summary = "列表")
    public R<List<GeneralInfo>> getList(HttpServletRequest request, @ParameterObject GeneralInfoQuery query) {
        List<GeneralInfo> res = generalInfoService.queryList(query);
        return R.OK(res);
    }

    @PostMapping("/save")
    @Operation(summary = "保存信息")
    @PreAuthorize("hasAuthority('org:save')")
    public R<GeneralInfo> save(HttpServletRequest request, @RequestBody GeneralInfo vo) {
        vo.setCreatedBy(SecurityUtil.getCurrentUsername());
        vo.setCreated(LocalDateTime.now());
        vo.setIsDel(1);
        return generalInfoService.saveGeneralInfo(vo);
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "更新信息")
    @PreAuthorize("hasAuthority('org:update')")
    public R<GeneralInfo> update(HttpServletRequest request, @PathVariable("id") String id,
                                 @RequestBody GeneralInfo vo) {
        vo.setModifiedBy(SecurityUtil.getCurrentUsername());
        vo.setModified(LocalDateTime.now());
        return generalInfoService.updateById(id, vo);
    }

    @DeleteMapping("/del")
    @Operation(summary = "删除信息")
    @PreAuthorize("hasAuthority('org:del')")
    public R<String> del(HttpServletRequest request, @RequestBody List<String> ids) {
        return generalInfoService.batchDel(ids);
    }
}
