package com.spring.admin.modules.sys.core.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.spring.admin.base.R;
import com.spring.admin.core.service.BaseServiceImpl;
import com.spring.admin.modules.sys.core.mapper.NeoadjuvantInfoMapper;
import com.spring.admin.modules.sys.core.model.entity.CaseInfo;
import com.spring.admin.modules.sys.core.model.entity.NeoadjuvantInfo;
import com.spring.admin.modules.sys.core.model.query.NeoadjuvantInfoQuery;
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
public class NeoadjuvantInfoService extends BaseServiceImpl<NeoadjuvantInfoMapper, NeoadjuvantInfo> {
    /**
     * 获取一般信息
     *
     * @param query .
     * @return .
     */
    public List<NeoadjuvantInfo> queryList(NeoadjuvantInfoQuery query) {
        return this.baseMapper.queryPage(null, query);
    }

    /**
     * 保存信息
     *
     * @param neoadjuvantInfo .
     * @return .
     */
    public R<NeoadjuvantInfo> saveNeoadjuvantInfo(NeoadjuvantInfo neoadjuvantInfo) {
        // set Id
        neoadjuvantInfo.setId(IdUtil.fastSimpleUUID());
        // save
        save(neoadjuvantInfo);
        return R.OK(neoadjuvantInfo);
    }

    /**
     * 更新一般信息
     *
     * @param id .
     * @param vo .
     * @return .
     */
    public R<NeoadjuvantInfo> updateById(String id, NeoadjuvantInfo vo) {
        NeoadjuvantInfo neoadjuvantInfo = getById(id);
        BeanUtil.copyProperties(vo, neoadjuvantInfo, "id");
        updateById(neoadjuvantInfo);
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
        List<NeoadjuvantInfo> neoadjuvantInfos = this.listByIds(ids);
        if (CollectionUtil.isEmpty(neoadjuvantInfos)) {
            return R.NG("信息不存在");
        }
        this.removeByIds(ids);
        return R.OK();
    }

    public List<String> getIdsByPatientId(String patientId, Integer isDel) {
        return this.baseMapper.getIdsByPatientId(patientId, isDel);
    }
}
