package com.spring.admin.modules.sys.core.controller;

import com.spring.admin.base.R;
import com.spring.admin.modules.sys.core.model.entity.RadiationInfo;
import com.spring.admin.modules.sys.core.model.query.RadiationInfoQuery;
import com.spring.admin.modules.sys.core.service.RadiationInfoService;
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
@RequestMapping("/core/radiation")
@Tag(name = "功能：放射治疗")
@RequiredArgsConstructor
public class RadiationInfoController {
    private final RadiationInfoService radiationInfoService;

    @GetMapping("/query/list")
    @Operation(summary = "列表")
//    @PreAuthorize("hasAuthority('general:query')")
    public R<List<RadiationInfo>> getList(HttpServletRequest request, @ParameterObject RadiationInfoQuery query) {
        List<RadiationInfo> res = radiationInfoService.queryList(query);
        return R.OK(res);
    }

    @PostMapping("/save")
    @Operation(summary = "保存信息")
//    @PreAuthorize("hasAuthority('org:save')")
    public R<RadiationInfo> save(HttpServletRequest request, @RequestBody RadiationInfo vo) {
        return radiationInfoService.saveRadiationInfo(vo);
    }

    @PutMapping("/update")
    @Operation(summary = "更新信息")
    @PreAuthorize("hasAuthority('general:update')")
    public R<RadiationInfo> update(HttpServletRequest request, String id,
                              @RequestBody RadiationInfo vo) {
        vo.setModifiedBy(SecurityUtil.getCurrentUsername());
        vo.setModified(LocalDateTime.now());
        return radiationInfoService.updateById(id, vo);
    }

    @DeleteMapping("/del")
    @Operation(summary = "删除信息")
//    @PreAuthorize("hasAuthority('org:del')")
    public R<String> del(HttpServletRequest request, @RequestBody List<String> ids) {
        return radiationInfoService.batchDel(ids);
    }
}
