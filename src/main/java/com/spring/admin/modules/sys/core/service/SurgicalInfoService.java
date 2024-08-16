package com.spring.admin.modules.sys.core.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.spring.admin.base.R;
import com.spring.admin.core.service.BaseServiceImpl;
import com.spring.admin.modules.sys.core.mapper.CaseInfoMapper;
import com.spring.admin.modules.sys.core.mapper.SurgicalInfoMapper;
import com.spring.admin.modules.sys.core.model.entity.CaseInfo;
import com.spring.admin.modules.sys.core.model.entity.SurgicalInfo;
import com.spring.admin.modules.sys.core.model.query.SurgicalInfoQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 李飞洋
 * @date 2024/8/16
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class SurgicalInfoService extends BaseServiceImpl<SurgicalInfoMapper, SurgicalInfo> {
    /**
     * 获取一般信息
     *
     * @param query .
     * @return .
     */
    public List<SurgicalInfo> queryList(SurgicalInfoQuery query) {
        return this.baseMapper.queryPage(null, query);
    }

    /**
     * 保存信息
     *
     * @param surgicalInfo .
     * @return .
     */
    public R<SurgicalInfo> saveSurgicalInfo(SurgicalInfo surgicalInfo) {
        // set Id
        surgicalInfo.setId(IdUtil.fastSimpleUUID());
        // save
        save(surgicalInfo);
        return R.OK(surgicalInfo);
    }

    /**
     * 更新一般信息
     *
     * @param id .
     * @param vo .
     * @return .
     */
    public R<SurgicalInfo> updateById(String id, SurgicalInfo vo) {
        SurgicalInfo surgicalInfo = getById(id);
        System.out.println(surgicalInfo);
        System.out.println(id);
        System.out.println(vo);
        BeanUtil.copyProperties(vo, surgicalInfo, "id");
        updateById(surgicalInfo);
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
        List<SurgicalInfo> surgicalInfos = this.listByIds(ids);
        if (CollectionUtil.isEmpty(surgicalInfos)) {
            return R.NG("信息不存在");
        }
        this.removeByIds(ids);
        return R.OK();
    }

    public List<String> getIdsByPatientId(String patientId, Integer isDel) {
        return this.baseMapper.getIdsByPatientId(patientId, isDel);
    }
}
