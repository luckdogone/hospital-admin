package com.spring.admin.modules.sys.core.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import com.spring.admin.base.R;
import com.spring.admin.core.service.BaseServiceImpl;
import com.spring.admin.modules.sys.core.mapper.GeneralInfoMapper;
import com.spring.admin.modules.sys.core.model.entity.GeneralInfo;
import com.spring.admin.modules.sys.core.model.query.GeneralInfoQuery;
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
}
