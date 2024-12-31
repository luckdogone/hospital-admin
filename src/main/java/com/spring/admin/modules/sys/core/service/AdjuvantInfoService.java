package com.spring.admin.modules.sys.core.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.spring.admin.base.R;
import com.spring.admin.core.service.BaseServiceImpl;
import com.spring.admin.modules.sys.core.mapper.AdjuvantInfoMapper;
import com.spring.admin.modules.sys.core.model.entity.AdjuvantInfo;
import com.spring.admin.modules.sys.core.model.query.AdjuvantInfoQuery;
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
public class AdjuvantInfoService extends BaseServiceImpl<AdjuvantInfoMapper, AdjuvantInfo> {

    /**
     * 获取一般信息
     *
     * @param query .
     * @return .
     */
    public List<AdjuvantInfo> queryList(AdjuvantInfoQuery query) {
        return this.baseMapper.queryPage(null, query);
    }

    /**
     * 保存信息
     *
     * @param adjuvantInfo .
     * @return .
     */
    public R<AdjuvantInfo> saveAdjuvantInfo(AdjuvantInfo adjuvantInfo) {
        adjuvantInfo.setCreatedBy(SecurityUtil.getCurrentUsername());
        adjuvantInfo.setCreated(LocalDateTime.now());
        adjuvantInfo.setInputStatus(0);
        adjuvantInfo.setIsEnable(1);
        adjuvantInfo.setIsDel(1);
        // set Id
        adjuvantInfo.setId(IdUtil.fastSimpleUUID());
        // save
        save(adjuvantInfo);
        return R.OK(adjuvantInfo);
    }

    /**
     * 更新一般信息
     *
     * @param id .
     * @param vo .
     * @return .
     */
    public R<AdjuvantInfo> updateById(String id, AdjuvantInfo vo) {
        AdjuvantInfo adjuvantInfo = getById(id);
        BeanUtil.copyProperties(vo, adjuvantInfo, "id","inputStatus","isEnable","isDel");
        adjuvantInfo.setInputStatus(2);
        updateById(adjuvantInfo);
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
        List<AdjuvantInfo> adjuvantInfos = this.listByIds(ids);
        if (CollectionUtil.isEmpty(adjuvantInfos)) {
            return R.NG("信息不存在");
        }
        this.removeByIds(ids);
        return R.OK();
    }

    public List<String> getIdsByPatientId(String patientId, Integer isDel) {
        return this.baseMapper.getIdsByPatientId(patientId, isDel);
    }
}
