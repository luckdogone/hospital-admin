package com.spring.admin.modules.sys.core.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.admin.base.R;
import com.spring.admin.core.service.BaseServiceImpl;
import com.spring.admin.data.domain.BasePage;
import com.spring.admin.modules.sys.core.mapper.PatientInfoMapper;
import com.spring.admin.modules.sys.core.model.entity.PatientInfo;
import com.spring.admin.modules.sys.core.model.query.PatientInfoQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 李飞洋
 * @date 2024/8/1
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class PatientInfoService extends BaseServiceImpl<PatientInfoMapper, PatientInfo> {

    /**
     * 获取基础信息分页
     *
     * @param query .
     * @return .
     */
    public BasePage<PatientInfo> queryPage(PatientInfoQuery query) {
        Page<PatientInfo> page = new Page<>(query.getCurrent(), query.getSize());
        List<PatientInfo> records = this.baseMapper.queryPage(page, query);
        return new BasePage<>(page.getCurrent(), page.getSize(), page.getTotal(), records);
    }

    /**
     * 获取基础信息
     *
     * @param query .
     * @return .
     */
    public List<PatientInfo> queryList(PatientInfoQuery query) {
        return this.baseMapper.queryPage(null, query);
    }

    /**
     * 保存信息
     *
     * @param patientInfo .
     * @return .
     */
    public R<PatientInfo> savePatientInfo(PatientInfo patientInfo) {
        // set Id
        patientInfo.setId(IdUtil.fastSimpleUUID());
        // save
        save(patientInfo);
        return R.OK(patientInfo);
    }

    /**
     * 更新基础信息
     *
     * @param id .
     * @param vo .
     * @return .
     */
    public R<PatientInfo> updateById(String id, PatientInfo vo) {
        PatientInfo patientInfo = getById(id);
        BeanUtil.copyProperties(vo, patientInfo, "id");
        updateById(patientInfo);
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
        List<PatientInfo> patientInfos = this.listByIds(ids);
        if (CollectionUtil.isEmpty(patientInfos)) {
            return R.NG("信息不存在");
        }
        this.removeByIds(ids);
        return R.OK();
    }
}
