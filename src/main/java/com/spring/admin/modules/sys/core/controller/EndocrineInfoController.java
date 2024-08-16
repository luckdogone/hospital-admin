package com.spring.admin.modules.sys.core.controller;

import com.spring.admin.base.R;
import com.spring.admin.modules.sys.core.model.entity.EndocrineInfo;
import com.spring.admin.modules.sys.core.model.query.EndocrineInfoQuery;
import com.spring.admin.modules.sys.core.service.EndocrineInfoService;
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
@RequestMapping("/core/endocrine")
@Tag(name = "功能：内分泌治疗")
@RequiredArgsConstructor
public class EndocrineInfoController {
    private final EndocrineInfoService endocrineInfoService;

    @GetMapping("/query/list")
    @Operation(summary = "列表")
//    @PreAuthorize("hasAuthority('general:query')")
    public R<List<EndocrineInfo>> getList(HttpServletRequest request, @ParameterObject EndocrineInfoQuery query) {
        List<EndocrineInfo> res = endocrineInfoService.queryList(query);
        return R.OK(res);
    }

    @PostMapping("/save")
    @Operation(summary = "保存信息")
//    @PreAuthorize("hasAuthority('org:save')")
    public R<EndocrineInfo> save(HttpServletRequest request, @RequestBody EndocrineInfo vo) {
        vo.setCreatedBy(SecurityUtil.getCurrentUsername());
        vo.setCreated(LocalDateTime.now());
        vo.setIsDel(1);
        return endocrineInfoService.saveEndocrineInfo(vo);
    }

    @PutMapping("/update")
    @Operation(summary = "更新信息")
    @PreAuthorize("hasAuthority('general:update')")
    public R<EndocrineInfo> update(HttpServletRequest request, String id,
                              @RequestBody EndocrineInfo vo) {
        vo.setModifiedBy(SecurityUtil.getCurrentUsername());
        vo.setModified(LocalDateTime.now());
        return endocrineInfoService.updateById(id, vo);
    }

    @DeleteMapping("/del")
    @Operation(summary = "删除信息")
//    @PreAuthorize("hasAuthority('org:del')")
    public R<String> del(HttpServletRequest request, @RequestBody List<String> ids) {
        return endocrineInfoService.batchDel(ids);
    }
}
