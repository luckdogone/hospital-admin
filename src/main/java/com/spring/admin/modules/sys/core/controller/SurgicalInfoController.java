package com.spring.admin.modules.sys.core.controller;

import com.spring.admin.base.R;
import com.spring.admin.modules.sys.core.model.entity.SurgicalInfo;
import com.spring.admin.modules.sys.core.model.query.SurgicalInfoQuery;
import com.spring.admin.modules.sys.core.service.SurgicalInfoService;
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
 * @date 2024/8/16
 */
@RestController
@RequestMapping("/core/surgical")
@Tag(name = "功能：手术相关")
@RequiredArgsConstructor
public class SurgicalInfoController {

    private final SurgicalInfoService surgicalInfoService;

    @GetMapping("/query/list")
    @Operation(summary = "列表")
    @PreAuthorize("hasAuthority('surgical:query')")
    public R<List<SurgicalInfo>> getList(HttpServletRequest request, @ParameterObject SurgicalInfoQuery query) {
        List<SurgicalInfo> res = surgicalInfoService.queryList(query);
        return R.OK(res);
    }

    @PostMapping("/save")
    @Operation(summary = "保存信息")
//    @PreAuthorize("hasAuthority('org:save')")
    public R<SurgicalInfo> save(HttpServletRequest request, @RequestBody SurgicalInfo vo) {
        return surgicalInfoService.saveSurgicalInfo(vo);
    }

    @PutMapping("/update")
    @Operation(summary = "更新信息")
    @PreAuthorize("hasAuthority('surgical:update')")
    public R<SurgicalInfo> update(HttpServletRequest request, String id,
                              @RequestBody SurgicalInfo vo) {
        vo.setModifiedBy(SecurityUtil.getCurrentUsername());
        vo.setModified(LocalDateTime.now());
        return surgicalInfoService.updateById(id, vo);
    }

    @DeleteMapping("/del")
    @Operation(summary = "删除信息")
//    @PreAuthorize("hasAuthority('org:del')")
    public R<String> del(HttpServletRequest request, @RequestBody List<String> ids) {
        return surgicalInfoService.batchDel(ids);
    }
}
