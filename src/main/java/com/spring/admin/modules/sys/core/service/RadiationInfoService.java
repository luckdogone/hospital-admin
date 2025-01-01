package com.spring.admin.modules.sys.core.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.spring.admin.base.R;
import com.spring.admin.core.service.BaseServiceImpl;
import com.spring.admin.modules.sys.core.mapper.RadiationInfoMapper;
import com.spring.admin.modules.sys.core.model.entity.NeoadjuvantInfo;
import com.spring.admin.modules.sys.core.model.entity.RadiationInfo;
import com.spring.admin.modules.sys.core.model.query.RadiationInfoQuery;
import com.spring.admin.security.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
        radiationInfo.setCreatedBy(SecurityUtil.getCurrentUsername());
        radiationInfo.setCreated(LocalDateTime.now());
        radiationInfo.setInputStatus(0);
        radiationInfo.setIsEnable(1);
        radiationInfo.setIsDel(1);
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
        BeanUtil.copyProperties(vo, radiationInfo, "id","inputStatus","isEnable","isDel");
        radiationInfo.setInputStatus(2);
        updateById(radiationInfo);
        List<Integer> allInputStatus = this.baseMapper.getRelatedInputStatus(vo.getPatientId());
        boolean allStatusesAreTwo = allInputStatus.stream().allMatch(status -> status == 2);
        if (allStatusesAreTwo) {
            this.baseMapper.updatePatientInputStatus(vo.getPatientId(), 2);
        } else {
            this.baseMapper.updatePatientInputStatus(vo.getPatientId(), 1);
        }
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

    public List<RadiationInfo> getInfoByPatientId(String patientId, Integer isDel) {
        return this.baseMapper.getInfoByPatientId(patientId, isDel);
    }
}
