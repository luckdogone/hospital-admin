package com.spring.admin.modules.sys.core.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.spring.admin.base.R;
import com.spring.admin.core.service.BaseServiceImpl;
import com.spring.admin.modules.sys.core.mapper.EndocrineInfoMapper;
import com.spring.admin.modules.sys.core.model.entity.EndocrineInfo;
import com.spring.admin.modules.sys.core.model.entity.GeneralInfo;
import com.spring.admin.modules.sys.core.model.query.EndocrineInfoQuery;
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
public class EndocrineInfoService extends BaseServiceImpl<EndocrineInfoMapper, EndocrineInfo> {

    /**
     * 获取一般信息
     *
     * @param query .
     * @return .
     */
    public List<EndocrineInfo> queryList(EndocrineInfoQuery query) {
        return this.baseMapper.queryPage(null, query);
    }

    /**
     * 保存信息
     *
     * @param endocrineInfo .
     * @return .
     */
    public R<EndocrineInfo> saveEndocrineInfo(EndocrineInfo endocrineInfo) {
        endocrineInfo.setCreatedBy(SecurityUtil.getCurrentUsername());
        endocrineInfo.setCreated(LocalDateTime.now());
        endocrineInfo.setInputStatus(0);
        endocrineInfo.setIsEnable(1);
        endocrineInfo.setIsDel(1);
        // set Id
        endocrineInfo.setId(IdUtil.fastSimpleUUID());
        // save
        save(endocrineInfo);
        return R.OK(endocrineInfo);
    }

    /**
     * 更新一般信息
     *
     * @param id .
     * @param vo .
     * @return .
     */
    public R<EndocrineInfo> updateById(String id, EndocrineInfo vo) {
        EndocrineInfo endocrineInfo = getById(id);
        BeanUtil.copyProperties(vo, endocrineInfo, "id","inputStatus","isEnable","isDel");
        endocrineInfo.setInputStatus(2);
        updateById(endocrineInfo);
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
        List<EndocrineInfo> endocrineInfos = this.listByIds(ids);
        if (CollectionUtil.isEmpty(endocrineInfos)) {
            return R.NG("信息不存在");
        }
        this.removeByIds(ids);
        return R.OK();
    }

    public List<String> getIdsByPatientId(String patientId, Integer isDel) {
        return this.baseMapper.getIdsByPatientId(patientId, isDel);
    }

    public List<EndocrineInfo> getInfoByPatientId(String patientId, Integer isDel) {
        return this.baseMapper.getInfoByPatientId(patientId, isDel);
    }
}
