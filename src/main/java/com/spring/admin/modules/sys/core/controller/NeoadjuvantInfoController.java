package com.spring.admin.modules.sys.core.controller;

import com.spring.admin.base.R;
import com.spring.admin.modules.sys.core.model.entity.NeoadjuvantInfo;
import com.spring.admin.modules.sys.core.model.query.NeoadjuvantInfoQuery;
import com.spring.admin.modules.sys.core.service.NeoadjuvantInfoService;
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
@RequestMapping("/core/neoadjuvant")
@Tag(name = "功能：新辅助治疗")
@RequiredArgsConstructor
public class NeoadjuvantInfoController {
    private final NeoadjuvantInfoService neoadjuvantInfoService;

    @GetMapping("/query/list")
    @Operation(summary = "列表")
//    @PreAuthorize("hasAuthority('general:query')")
    public R<List<NeoadjuvantInfo>> getList(HttpServletRequest request, @ParameterObject NeoadjuvantInfoQuery query) {
        List<NeoadjuvantInfo> res = neoadjuvantInfoService.queryList(query);
        return R.OK(res);
    }

    @PostMapping("/save")
    @Operation(summary = "保存信息")
//    @PreAuthorize("hasAuthority('org:save')")
    public R<NeoadjuvantInfo> save(HttpServletRequest request, @RequestBody NeoadjuvantInfo vo) {
        vo.setCreatedBy(SecurityUtil.getCurrentUsername());
        vo.setCreated(LocalDateTime.now());
        vo.setIsDel(1);
        return neoadjuvantInfoService.saveNeoadjuvantInfo(vo);
    }

    @PutMapping("/update")
    @Operation(summary = "更新信息")
    @PreAuthorize("hasAuthority('general:update')")
    public R<NeoadjuvantInfo> update(HttpServletRequest request, String id,
                              @RequestBody NeoadjuvantInfo vo) {
        vo.setModifiedBy(SecurityUtil.getCurrentUsername());
        vo.setModified(LocalDateTime.now());
        return neoadjuvantInfoService.updateById(id, vo);
    }

    @DeleteMapping("/del")
    @Operation(summary = "删除信息")
//    @PreAuthorize("hasAuthority('org:del')")
    public R<String> del(HttpServletRequest request, @RequestBody List<String> ids) {
        return neoadjuvantInfoService.batchDel(ids);
    }
}
