package com.spring.admin.modules.sys.core.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.spring.admin.base.R;
import com.spring.admin.core.service.BaseServiceImpl;
import com.spring.admin.modules.sys.core.mapper.RadiationInfoMapper;
import com.spring.admin.modules.sys.core.model.entity.RadiationInfo;
import com.spring.admin.modules.sys.core.model.query.RadiationInfoQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 李飞洋
 * @date 2024/8/17
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RadiationInfoService extends BaseServiceImpl<RadiationInfoMapper, RadiationInfo> {
    /**
     * 获取一般信息
     *
     * @param query .
     * @return .
     */
    public List<RadiationInfo> queryList(RadiationInfoQuery query) {
        return this.baseMapper.queryPage(null, query);
    }

    /**
     * 保存信息
     *
     * @param radiationInfo .
     * @return .
     */
    public R<RadiationInfo> saveRadiationInfo(RadiationInfo radiationInfo) {
        // set Id
        radiationInfo.setId(IdUtil.fastSimpleUUID());
        // save
        save(radiationInfo);
        return R.OK(radiationInfo);
    }

    /**
     * 更新一般信息
     *
     * @param id .
     * @param vo .
     * @return .
     */
    public R<RadiationInfo> updateById(String id, RadiationInfo vo) {
        RadiationInfo radiationInfo = getById(id);
        BeanUtil.copyProperties(vo, radiationInfo, "id");
        updateById(radiationInfo);
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
        List<RadiationInfo> radiationInfos = this.listByIds(ids);
        if (CollectionUtil.isEmpty(radiationInfos)) {
            return R.NG("信息不存在");
        }
        this.removeByIds(ids);
        return R.OK();
    }

    public List<String> getIdsByPatientId(String patientId, Integer isDel) {
        return this.baseMapper.getIdsByPatientId(patientId, isDel);
    }
}
