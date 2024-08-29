package com.spring.admin.modules.sys.core.controller;

import com.spring.admin.base.R;
import com.spring.admin.modules.sys.core.model.entity.GeneralInfo;
import com.spring.admin.modules.sys.core.model.query.GeneralInfoQuery;
import com.spring.admin.modules.sys.core.service.GeneralInfoService;
import com.spring.admin.security.util.SecurityUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    @PreAuthorize("hasAuthority('general:query')")
    public R<List<GeneralInfo>> getList(HttpServletRequest request, @ParameterObject GeneralInfoQuery query) {
        List<GeneralInfo> res = generalInfoService.queryList(query);
        return R.OK(res);
    }

    @GetMapping("/check/code")
    @Operation(summary = "校验code是否重复", parameters = {
            @Parameter(name = "id", required = false, description = "id"),
            @Parameter(name = "code", required = true, description = "手术编号")
    })
    public R<Boolean> checkCode(@RequestParam(value = "id", required = false) String id,
                                @RequestParam(value = "code") String code) {
        boolean checkCode = generalInfoService.checkCode(code, id);
        return R.OK(checkCode);
    }

    @PostMapping("/save")
    @Operation(summary = "保存信息")
//    @PreAuthorize("hasAuthority('org:save')")
    public R<GeneralInfo> save(HttpServletRequest request, @RequestBody GeneralInfo vo) {
        return generalInfoService.saveGeneralInfo(vo);
    }

    @PutMapping("/update")
    @Operation(summary = "更新信息")
    @PreAuthorize("hasAuthority('general:update')")
    public R<GeneralInfo> update(HttpServletRequest request, String id,
                                 @RequestBody GeneralInfo vo) {
        vo.setModifiedBy(SecurityUtil.getCurrentUsername());
        vo.setModified(LocalDateTime.now());
        return generalInfoService.updateById(id, vo);
    }

    @DeleteMapping("/del")
    @Operation(summary = "删除信息")
//    @PreAuthorize("hasAuthority('org:del')")
    public R<String> del(HttpServletRequest request, @RequestBody List<String> ids) {
        return generalInfoService.batchDel(ids);
    }
}
