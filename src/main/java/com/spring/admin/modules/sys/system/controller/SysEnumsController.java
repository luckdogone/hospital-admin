package com.spring.admin.modules.sys.system.controller;

import com.spring.admin.base.R;
import com.spring.admin.data.enums.ConfigTypeEnums;
import com.spring.admin.data.enums.EnabledEnums;
import com.spring.admin.data.enums.GenderEnums;
import com.spring.admin.modules.sys.system.vo.SelectOptionVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 
 * @date 2023/1/11
 */
@RestController
@RequestMapping("/sys/enums")
@RequiredArgsConstructor
@Tag(name = "系统：常量")
public class SysEnumsController {


    /**
     * 状态 options
     *
     * @return .
     */
    @GetMapping("/enabled")
    @Operation(summary = "状态列表")
    public R<List<SelectOptionVO>> enabledOptions() {
        List<SelectOptionVO> res = Stream.of(EnabledEnums.values())
                .map(v -> SelectOptionVO.builder().value(v.getValue() + "").name(v.getName()).build())
                .collect(Collectors.toList());
        return R.OK(res);
    }

    /**
     * 性别
     *
     * @return .
     */
    @GetMapping("/gender")
    @Operation(summary = "性别")
    public R<List<SelectOptionVO>> genderOptions() {
        List<SelectOptionVO> res = Stream.of(GenderEnums.values())
                .map(v -> SelectOptionVO.builder().name(v.getName()).value(v.getValue() + "").build())
                .collect(Collectors.toList());
        return R.OK(res);
    }

    /**
     * 配置类型
     *
     * @return .
     */
    @Operation(summary = "配置类型")
    @GetMapping("/config_type")
    public R<List<SelectOptionVO>> configTypeOptions() {
        List<SelectOptionVO> res = Stream.of(ConfigTypeEnums.values())
                .map(v -> SelectOptionVO.builder().name(v.getName()).value(v.getValue() + "").build())
                .collect(Collectors.toList());
        return R.OK(res);
    }
}
