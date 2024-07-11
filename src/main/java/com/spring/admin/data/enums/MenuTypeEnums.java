package com.spring.admin.data.enums;

/**
 * 菜单类型
 *
 * 
 * @date 2023/1/12
 */
public enum MenuTypeEnums implements ValueEnum<Integer> {
    dir("目录", 0),
    menu("菜单", 1),
    permission("权限", 2),
    ;
    private final String name;
    private final Integer value;

    MenuTypeEnums(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
