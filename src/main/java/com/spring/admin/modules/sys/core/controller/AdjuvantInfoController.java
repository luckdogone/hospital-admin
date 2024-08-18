package com.spring.admin.modules.sys.core.controller;

import com.spring.admin.base.R;
import com.spring.admin.modules.sys.core.model.entity.AdjuvantInfo;
import com.spring.admin.modules.sys.core.model.query.AdjuvantInfoQuery;
import com.spring.admin.modules.sys.core.service.AdjuvantInfoService;
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
 * @date 2024/8/17
 */
@RestController
@RequestMapping("/core/adjuvant")
@Tag(name = "功能：辅助治疗")
@RequiredArgsConstructor
public class AdjuvantInfoController {
    private final AdjuvantInfoService adjuvantInfoService;

    @GetMapping("/query/list")
    @Operation(summary = "列表")
//    @PreAuthorize("hasAuthority('general:query')")
    public R<List<AdjuvantInfo>> getList(HttpServletRequest request, @ParameterObject AdjuvantInfoQuery query) {
        List<AdjuvantInfo> res = adjuvantInfoService.queryList(query);
        return R.OK(res);
    }

    @PostMapping("/save")
    @Operation(summary = "保存信息")
//    @PreAuthorize("hasAuthority('org:save')")
    public R<AdjuvantInfo> save(HttpServletRequest request, @RequestBody AdjuvantInfo vo) {
        vo.setCreatedBy(SecurityUtil.getCurrentUsername());
        vo.setCreated(LocalDateTime.now());
        vo.setIsDel(1);
        return adjuvantInfoService.saveAdjuvantInfo(vo);
    }

    @PutMapping("/update")
    @Operation(summary = "更新信息")
    @PreAuthorize("hasAuthority('general:update')")
    public R<AdjuvantInfo> update(HttpServletRequest request, String id,
                              @RequestBody AdjuvantInfo vo) {
        vo.setModifiedBy(SecurityUtil.getCurrentUsername());
        vo.setModified(LocalDateTime.now());
        return adjuvantInfoService.updateById(id, vo);
    }

    @DeleteMapping("/del")
    @Operation(summary = "删除信息")
//    @PreAuthorize("hasAuthority('org:del')")
    public R<String> del(HttpServletRequest request, @RequestBody List<String> ids) {
        return adjuvantInfoService.batchDel(ids);
    }
}
