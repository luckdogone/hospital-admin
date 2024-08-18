package com.spring.admin.modules.sys.core.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.spring.admin.base.R;
import com.spring.admin.core.service.BaseServiceImpl;
import com.spring.admin.modules.sys.core.mapper.GeneralInfoMapper;
import com.spring.admin.modules.sys.core.model.entity.GeneralInfo;
import com.spring.admin.modules.sys.core.model.query.GeneralInfoQuery;
import com.spring.admin.modules.sys.system.model.entity.SysRole;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 李飞洋
 * @date 2024/8/6
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class GeneralInfoService extends BaseServiceImpl<GeneralInfoMapper, GeneralInfo> {

    /**
     * 获取一般信息
     *
     * @param query .
     * @return .
     */
    public List<GeneralInfo> queryList(GeneralInfoQuery query) {
        return this.baseMapper.queryPage(null, query);
    }

    /**
     * 检查code是否重复
     *
     * @param code .
     * @param id   需要排查的ID，可以为空
     * @return .
     */
    public boolean checkCode(@Nonnull String code, @Nullable String id) {
        LambdaQueryWrapper<GeneralInfo> queryWrapper = Wrappers.lambdaQuery(GeneralInfo.class)
                .eq(GeneralInfo::getSurgicalNum, code)
                //排除已标记删除的记录
                .ne(GeneralInfo::getIsDel, 0);
        if (StrUtil.isNotBlank(id)) {
            queryWrapper.ne(GeneralInfo::getId, id);
        }
        return count(queryWrapper) > 0;
    }

    /**
     * 保存信息
     *
     * @param generalInfo .
     * @return .
     */
    public R<GeneralInfo> saveGeneralInfo(GeneralInfo generalInfo) {
        // set Id
        generalInfo.setId(IdUtil.fastSimpleUUID());
        // save
        save(generalInfo);
        return R.OK(generalInfo);
    }

    /**
     * 更新一般信息
     *
     * @param id .
     * @param vo .
     * @return .
     */
    public R<GeneralInfo> updateById(String id, GeneralInfo vo) {
        GeneralInfo generalInfo = getById(id);
        System.out.println(generalInfo);
        System.out.println(id);
        System.out.println(vo);
        BeanUtil.copyProperties(vo, generalInfo, "id");
        updateById(generalInfo);
        return R.OK(vo);
    }

    /**
     * 批量删除
     *
     * @param ids .
     * @return .
     */
    @Transactional(rollbackFor = Exception.class)
    public R<String> batchDel(List<String> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return R.NG("参数为空");
        }
        List<GeneralInfo> generalInfos = this.listByIds(ids);
        if (CollectionUtil.isEmpty(generalInfos)) {
            return R.NG("信息不存在");
        }
        this.removeByIds(ids);
        return R.OK();
    }

    public List<String> getIdsByPatientId(String patientId, Integer isDel) {
        return this.baseMapper.getIdsByPatientId(patientId, isDel);
    }
}
