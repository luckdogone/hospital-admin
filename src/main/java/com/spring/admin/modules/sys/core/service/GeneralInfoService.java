package com.spring.admin.modules.sys.core.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.spring.admin.base.R;
import com.spring.admin.core.service.BaseServiceImpl;
import com.spring.admin.modules.sys.core.mapper.FollowUpRecordMapper;
import com.spring.admin.modules.sys.core.mapper.GeneralInfoMapper;
import com.spring.admin.modules.sys.core.model.entity.FollowUpRecord;
import com.spring.admin.modules.sys.core.model.entity.GeneralInfo;
import com.spring.admin.modules.sys.core.model.query.GeneralInfoQuery;
import com.spring.admin.security.util.SecurityUtil;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author 李飞洋
 * @date 2024/8/6
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class GeneralInfoService extends BaseServiceImpl<GeneralInfoMapper, GeneralInfo> {

    @Autowired
    private final FollowUpRecordMapper followUpRecordMapper;

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
     * 检查SurgicalNum是否重复
     *
     * @param code .
     * @param id   需要排查的ID，可以为空
     * @return .
     */
    public boolean checkSurgicalNum(@Nonnull String code, @Nullable String id) {
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
     * 检查CaseNo是否重复
     *
     * @param code .
     * @param id   需要排查的ID，可以为空
     * @return .
     */
    public boolean checkCaseNo(@Nonnull String code, @Nullable String id) {
        LambdaQueryWrapper<GeneralInfo> queryWrapper = Wrappers.lambdaQuery(GeneralInfo.class)
                .eq(GeneralInfo::getCaseNo, code)
                //排除已标记删除的记录
                .ne(GeneralInfo::getIsDel, 0);
        if (StrUtil.isNotBlank(id)) {
            queryWrapper.ne(GeneralInfo::getId, id);
        }
        return count(queryWrapper) > 0;
    }

    /**
     * 解析手术编号以获取手术日期
     * 假设手术编号格式为 A + 日期(YYYYMMDD) + 序号
     *
     * @param surgicalNum 手术编号
     * @return 解析出的手术日期
     * @throws IllegalArgumentException 如果手术编号格式不正确
     */
    public LocalDate parseSurgicalNumForDate(String surgicalNum) {
        // 验证手术编号长度
        if (surgicalNum == null || !surgicalNum.matches("A\\d{8}\\d{2}")) {
//            throw new IllegalArgumentException("手术编号格式不正确");
            return null;
        }

        // 提取日期部分 (例如 "20240520")
        String datePart = surgicalNum.substring(1, 9);

        try {
            // 将日期部分转换为 Date 对象
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            return LocalDate.parse(datePart, formatter);
        } catch (DateTimeParseException e) {
//            throw new IllegalArgumentException("手术编号中的日期格式不正确", e);
            return null;
        }
    }

    /**
     * 保存信息
     *
     * @param generalInfo .
     * @return .
     */
    @Transactional
    public R<GeneralInfo> saveGeneralInfo(GeneralInfo generalInfo) {

        System.out.println("执行saveGeneralInfo啦");
        // 设置基础信息
        generalInfo.setCreatedBy(SecurityUtil.getCurrentUsername());
        generalInfo.setCreated(LocalDateTime.now());
        generalInfo.setInputStatus(0);
        generalInfo.setIsEnable(1);
        generalInfo.setIsDel(1);

        // 保存 GeneralInfo
        generalInfo.setId(IdUtil.fastSimpleUUID());  // 生成唯一 ID
        save(generalInfo);

        // 返回成功信息
        return R.OK(generalInfo);
    }

    /**
     * 更新一般信息
     *
     * @param id .
     * @param vo .
     * @return .
     */
    @Transactional
    public R<GeneralInfo> updateById(String id, GeneralInfo vo) {
        // 根据 id 获取 GeneralInfo
        GeneralInfo generalInfo = getById(id);

        // 将 vo 中的属性复制到 generalInfo 对象中，排除部分字段
        BeanUtil.copyProperties(vo, generalInfo, "id", "inputStatus", "isEnable", "isDel");

        // 获取手术编号
        String surgicalNum = generalInfo.getSurgicalNum();

//        // 解析手术日期
//        LocalDate surgeryDate;
//        try {
//            surgeryDate = parseSurgicalNumForDate(surgicalNum);  // 使用解析函数获取手术日期
//        } catch (IllegalArgumentException e) {
//            System.out.println("保存失败，手术日期编号格式不正确");
//            // 如果解析失败，返回错误信息
//            return R.NG("保存失败，手术日期编号格式不正确");
//        }
        // 解析手术日期
        LocalDate surgeryDate = parseSurgicalNumForDate(surgicalNum);  // 使用解析函数获取手术日期
        if (surgeryDate == null) {
            // 如果解析失败，返回错误信息
            return R.NG("保存失败，手术日期编号格式不正确");
        }

        // 创建新的 FollowUpRecord 对象
        FollowUpRecord followUpRecord = new FollowUpRecord();

        // 计算下次随访日期并生成新的随访记录
        LocalDate nextFollowUpDate = calculateNextFollowUpDate(surgeryDate, surgeryDate, followUpRecord);

        // 计算手术日期和下次随访日期的月份差异
        long diffInMonths = ChronoUnit.MONTHS.between(surgeryDate, nextFollowUpDate);

        followUpRecord.setPatientId(generalInfo.getPatientId());  // 假设 generalInfo 中有 patientId
        followUpRecord.setFollowUpDate(nextFollowUpDate);  // 设置随访日期
        followUpRecord.setFollowUpStatus("等待中");
        followUpRecord.setAfterSurgeryDate(Math.toIntExact(diffInMonths));
        followUpRecord.setCreatedBy(SecurityUtil.getCurrentUsername());
        followUpRecord.setCreated(LocalDateTime.now());
        followUpRecord.setInputStatus(2);
        followUpRecord.setIsEnable(1);
        followUpRecord.setIsDel(1);

        // 尝试保存 FollowUpRecord 对象
        try {
            followUpRecordMapper.addFollowUpRecord(followUpRecord);  // 调用保存函数
            generalInfo.setInputStatus(2);
        } catch (Exception e) {
            // 如果存储失败，回滚事务并返回错误
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();  // 回滚事务
            return R.NG("保存失败，请稍后再试");
        }

        // 更新 GeneralInfo 对象
        updateById(generalInfo);

        List<Integer> allInputStatus = this.baseMapper.getRelatedInputStatus(vo.getPatientId());
        System.out.println(allInputStatus);
        boolean allStatusesAreTwo = allInputStatus.stream().allMatch(status -> status == 2);
        if (!allInputStatus.isEmpty() && allStatusesAreTwo) {
            System.out.println(666666);
            this.baseMapper.updatePatientInputStatus(vo.getPatientId(), 2);
        } else {
            this.baseMapper.updatePatientInputStatus(vo.getPatientId(), 1);
        }

        // 返回成功响应
        return R.OK(vo);
    }

    /**
     * 计算下次随访日期
     *
     * @param surgeryDate   手术日期
     * @param currentDate   当前随访日期
     * @return 下次随访日期
     */
    private LocalDate calculateNextFollowUpDate(LocalDate surgeryDate, LocalDate currentDate, FollowUpRecord followUpRecord) {
        // 计算手术日期和当前日期之间的月份差异
        long diffInMonths = ChronoUnit.MONTHS.between(surgeryDate, currentDate);

        // 将时间差(以月为单位)存储在 afterSurgeryDate 属性中
        followUpRecord.setAfterSurgeryDate(Math.toIntExact(diffInMonths));

        // 根据时间间隔确定随访时间规则
        int monthsToNextVisit;
        if (diffInMonths < 24) {
            // 两年内每三个月随访一次
            monthsToNextVisit = 3;
        } else if (diffInMonths < 60) {
            // 两年至五年内每六个月随访一次
            monthsToNextVisit = 6;
        } else {
            // 五年后每年随访一次
            monthsToNextVisit = 12;
        }

        // 计算下次随访日期
        return currentDate.plusMonths(monthsToNextVisit);
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

    public List<GeneralInfo> getInfoByPatientId(String patientId, Integer isDel) {
        return this.baseMapper.getInfoByPatientId(patientId, isDel);
    }
}
