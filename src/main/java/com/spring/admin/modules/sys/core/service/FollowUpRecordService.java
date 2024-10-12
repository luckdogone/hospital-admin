package com.spring.admin.modules.sys.core.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.admin.base.R;
import com.spring.admin.core.service.BaseServiceImpl;
import com.spring.admin.data.domain.BasePage;
import com.spring.admin.job.MarkPendingVisitRecordsJob;
import com.spring.admin.modules.sys.core.mapper.FollowUpRecordMapper;
import com.spring.admin.modules.sys.core.model.entity.FollowUpRecord;
import com.spring.admin.modules.sys.core.model.entity.GeneralInfo;
import com.spring.admin.modules.sys.core.model.query.FollowQuery;
import com.spring.admin.modules.sys.core.model.vo.FollowUpRecordVO;
import com.spring.admin.modules.sys.core.model.vo.PatientInfoVO;
import com.spring.admin.security.util.SecurityUtil;
import jakarta.annotation.Nonnull;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FollowUpRecordService extends BaseServiceImpl<FollowUpRecordMapper, FollowUpRecord> {

    @Autowired
    private final GeneralInfoService generalInfoService;

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return 分页数据
     */
    public BasePage<PatientInfoVO> queryPage(FollowQuery query) {
        Page<PatientInfoVO> page = new Page<>(query.getCurrent(), query.getSize());
        List<PatientInfoVO> records = this.baseMapper.queryPage(page, query);
        return new BasePage<>(page.getCurrent(), page.getSize(), page.getTotal(), records);
    }

    /**
     * 列表查询
     *
     * @param query 查询条件
     * @return 列表数据
     */
    public List<PatientInfoVO> queryList(FollowQuery query) {
        return this.baseMapper.queryPage(null, query);
    }

    public List<FollowUpRecord> queryByPatientId(String patientId){
        return this.baseMapper.queryByPatientId(patientId);
    }
    /**
     * 查询待随访记录
     *
     * @return 待随访记录列表
     */
    public BasePage<FollowUpRecordVO> queryPendingVisitRecords(FollowQuery query) {
        Page<FollowUpRecordVO> page = new Page<>(query.getCurrent(), query.getSize());
        List<FollowUpRecordVO> records = this.baseMapper.queryPendingVisitRecords(page, query);
        return new BasePage<>(page.getCurrent(), page.getSize(), page.getTotal(), records);
    }

    private LocalDate parseSurgicalNumForDate(String surgicalNum) {
        try {
            // 假设 surgicalNum 格式为 A2024052001
            String dateString = surgicalNum.substring(1, 9); // 20240520
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

            // 将日期字符串解析为 Date 对象
            Date date = sdf.parse(dateString);

            // 将 Date 转换为 LocalDate
            Instant instant = date.toInstant();
            return instant.atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (Exception e) {
            throw new RuntimeException("解析手术编号中的手术日期失败: " + surgicalNum, e);
        }
    }


    private LocalDate extractSurgeryDateFromGeneralInfo(GeneralInfo generalInfo) {
        // 从 GeneralInfo 对象中获取 surgicalNum 属性
        String surgicalNum = generalInfo.getSurgicalNum();

        // 解析 surgicalNum 以获取手术日期
        return parseSurgicalNumForDate(surgicalNum);
    }

    private GeneralInfo getGeneralInfoByPatientId(String patientId, int status) {
        List<GeneralInfo> generalInfos = generalInfoService.getInfoByPatientId(patientId, status);

        // 检查集合中的元素数量是否为1，若不是则抛出异常
        if (CollectionUtil.isEmpty(generalInfos) || generalInfos.size() != 1) {
            throw new RuntimeException("无法获取唯一的 GeneralInfo，数据可能被篡改或不完整。");
        }

        // 返回唯一的 GeneralInfo 对象
        return generalInfos.get(0);
    }

    protected void saveFollowUpRecordEntity(FollowUpRecord followUpRecord) {
        // 执行保存操作
        save(followUpRecord);
    }


    /**
     * 保存随访记录
     *
     * @param followUpRecordVO 随访记录实体
     */
    @Transactional
    public R<FollowUpRecordVO> saveFollowUpRecord(FollowUpRecordVO followUpRecordVO) {

        // 获取 GeneralInfo 对象
        GeneralInfo generalInfo = getGeneralInfoByPatientId(followUpRecordVO.getPatientId(), 1);

        // 提取手术日期并设置到 followUpRecordVO 中
        LocalDate surgeryDate = extractSurgeryDateFromGeneralInfo(generalInfo);
        followUpRecordVO.setSurgeryDate(surgeryDate);

        // 设置其他随访记录的属性
        followUpRecordVO.setFollowUpStatus("等待中");
        followUpRecordVO.setSurgicalNum(generalInfo.getSurgicalNum());
        followUpRecordVO.setCreatedBy(SecurityUtil.getCurrentUsername());
        followUpRecordVO.setCreated(LocalDateTime.now());
        followUpRecordVO.setInputStatus(0);
        followUpRecordVO.setIsEnable(1);
        followUpRecordVO.setIsDel(1);

        // 使用 ChronoUnit 计算手术日期和下次随访日期之间的月份差
        long diffInMonths = ChronoUnit.MONTHS.between(followUpRecordVO.getSurgeryDate(), followUpRecordVO.getFollowUpDate());

        followUpRecordVO.setAfterSurgeryDate(Math.toIntExact(diffInMonths));

        // 转换 VO 为实体对象
        FollowUpRecord followUpRecord = BeanUtil.toBean(followUpRecordVO, FollowUpRecord.class);

        // 调用保存函数保存当前随访记录
        saveFollowUpRecordEntity(followUpRecord);

        // 返回保存的随访记录
        return R.OK(followUpRecordVO);
    }


    /**
     * 更新并同步修改随访状态
     *
     * @param followUpRecordVO 随访记录实体
     */
    @Transactional
    public R<FollowUpRecordVO> updateFollowUpRecord(String id, FollowUpRecordVO followUpRecordVO) {

        // 获取 GeneralInfo 列表
        List<GeneralInfo> generalInfos = generalInfoService.getInfoByPatientId(followUpRecordVO.getPatientId(), 1);

        // 检查集合中的元素数量是否为1，若不是则抛出异常
        if (CollectionUtil.isEmpty(generalInfos) || generalInfos.size() != 1) {
//            throw new RuntimeException("无法获取唯一的 GeneralInfo，数据可能被篡改或不完整。");
            return R.NG("无法获取唯一的 GeneralInfo，数据可能被篡改或不完整。");
        }

        // 获取唯一的 GeneralInfo 对象
        GeneralInfo generalInfo = generalInfos.get(0);

        // 从 GeneralInfo 对象中获取 surgicalNum 属性的值并设置到 followUpRecordVO 中
        followUpRecordVO.setSurgicalNum(generalInfo.getSurgicalNum());

        // 解析 surgicalNum 以获取手术日期
        String surgicalNum = followUpRecordVO.getSurgicalNum();
        LocalDate surgeryDate = parseSurgicalNumForDate(surgicalNum);
        followUpRecordVO.setSurgeryDate(surgeryDate);
        // 获取当前随访记录
        FollowUpRecord followUpRecord = getById(id);
        if (followUpRecord == null) {
            throw new RuntimeException("随访记录未找到");
        }

        // 检查录入状态
//        String followUpStatus = followUpRecord.getFollowUpStatus();

        // 如果录入状态是1 (录入中)，则修改为2 (已录入)
        if (followUpRecord.getFollowUpStatus().equals("待随访")) {
            // 更新当前随访记录
            BeanUtil.copyProperties(followUpRecordVO, followUpRecord, "id", "patientId");
//            System.out.println(followUpRecord.getFollowUpStatus());
//            System.out.println(followUpRecord);
            followUpRecord.setFollowUpStatus("已完成");
            updateById(followUpRecord);

            // 计算下次随访日期并生成新的随访记录
//            Date nextFollowUpDate = calculateNextFollowUpDate(followUpRecordVO.getSurgeryDate(), Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()), followUpRecordVO);

            LocalDate nextFollowUpDate = calculateNextFollowUpDate(followUpRecordVO.getSurgeryDate(), followUpRecordVO.getFollowUpDate(), followUpRecordVO);

            // 使用 ChronoUnit 计算手术日期和下次随访日期之间的月份差
            long diffInMonths = ChronoUnit.MONTHS.between(followUpRecordVO.getSurgeryDate(), nextFollowUpDate);

            // 创建新的 FollowUpRecord 对象
            FollowUpRecord newRecord = new FollowUpRecord();
            // 将时间差(以月为单位)存储在 afterSurgeryDate 属性中
            newRecord.setAfterSurgeryDate(Math.toIntExact(diffInMonths));
            newRecord.setPatientId(followUpRecordVO.getPatientId());
            newRecord.setFollowUpDate(nextFollowUpDate);
            newRecord.setFollowUpStatus("等待中");
            newRecord.setModifiedBy(SecurityUtil.getCurrentUsername());
            newRecord.setModified(LocalDateTime.now());
            newRecord.setInputStatus(0);
            newRecord.setIsEnable(1);
            newRecord.setIsDel(1);
            save(newRecord);

        } else {
            BeanUtil.copyProperties(followUpRecordVO, followUpRecord, "id", "patientId");
            updateById(followUpRecord);
        }
//        else if (followUpRecord.getFollowUpStatus().equals("已完成")) {
//            // 如果录入状态是2 (已录入)，只更新当前记录
//            BeanUtil.copyProperties(followUpRecordVO, followUpRecord, "id", "patientId");
//            updateById(followUpRecord);
//        }
//        else {
//            throw new RuntimeException("无效的录入状态");
//        }

        return R.OK(followUpRecordVO);
    }


    /**
     * 更新随访记录
     *
     * @param followUpRecordVO 随访记录实体
     */
    @Transactional
    public R<FollowUpRecordVO> updateFollowUpInfo(String id, FollowUpRecordVO followUpRecordVO) {

        // 获取 GeneralInfo 列表
        List<GeneralInfo> generalInfos = generalInfoService.getInfoByPatientId(followUpRecordVO.getPatientId(), 1);

        // 检查集合中的元素数量是否为1，若不是则抛出异常
        if (CollectionUtil.isEmpty(generalInfos) || generalInfos.size() != 1) {
//            throw new RuntimeException("无法获取唯一的 GeneralInfo，数据可能被篡改或不完整。");
            return R.NG("无法获取唯一的 GeneralInfo，数据可能被篡改或不完整。");
        }

        // 获取唯一的 GeneralInfo 对象
        GeneralInfo generalInfo = generalInfos.get(0);

        // 从 GeneralInfo 对象中获取 surgicalNum 属性的值并设置到 followUpRecordVO 中
        followUpRecordVO.setSurgicalNum(generalInfo.getSurgicalNum());
        // 解析 surgicalNum 以获取手术日期
        String surgicalNum = followUpRecordVO.getSurgicalNum();
        LocalDate surgeryDate = parseSurgicalNumForDate(surgicalNum);
        followUpRecordVO.setSurgeryDate(surgeryDate);

        // 获取当前随访记录
        FollowUpRecord followUpRecord = getById(id);
        // 使用 ChronoUnit 计算手术日期和随访日期之间的月份差
        long diffInMonths = ChronoUnit.MONTHS.between(followUpRecordVO.getSurgeryDate(), followUpRecordVO.getFollowUpDate());
        System.out.println(diffInMonths);
        followUpRecordVO.setAfterSurgeryDate(Math.toIntExact(diffInMonths));
        BeanUtil.copyProperties(followUpRecordVO, followUpRecord, "id", "patientId");
        updateById(followUpRecord);
        System.out.println(followUpRecord);
        return R.OK(followUpRecordVO);
    }

    /**
     * 删除随访记录
     *
     * @param ids 随访记录ID
     */
    @Transactional(rollbackFor = Exception.class)
    public R<String> deleteByIds(@Nonnull List<String> ids) {
        if (CollectionUtil.isEmpty(ids)) {
            return R.NG("删除失败");
        }
        boolean remove = removeByIds(ids);
        if (remove) {
            return R.OK("删除成功");
        }
        return R.NG("删除失败");

    }

    /**
     * 计算下次随访日期
     *
     * @param surgeryDate   手术日期
     * @param currentDate   当前随访日期
     * @return 下次随访日期
     */
    private LocalDate calculateNextFollowUpDate(LocalDate surgeryDate, LocalDate currentDate, FollowUpRecordVO followUpRecordvo) {
        // 根据手术日期和当前随访日期计算时间间隔（以月份为单位）
        long diffInMonths = ChronoUnit.MONTHS.between(surgeryDate, currentDate);

        // 将时间差(以月为单位)存储在 afterSurgeryDate 属性中
        followUpRecordvo.setAfterSurgeryDate(Math.toIntExact(diffInMonths));

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
     * 标记待随访记录
     *
     * 每天定时扫描数据库中的随访记录,
     * 将followUpDate等于当天日期且followUpStatus为'pending'的记录标记为'pending_visit'
     */
    public void markPendingVisitRecords() {
        // 获取今天的日期,不包含时间部分
        LocalDate today = LocalDate.now();

        // 构造今天的开始和结束时间
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(23, 59, 59);

        // 转换为 Date 对象
        Date startDate = Date.from(startOfDay.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(endOfDay.atZone(ZoneId.systemDefault()).toInstant());

        // 查询待处理的随访记录
        List<FollowUpRecord> pendingRecords = this.lambdaQuery()
                .between(FollowUpRecord::getFollowUpDate, startDate, endDate)
                .eq(FollowUpRecord::getFollowUpStatus, "等待中")
                .list();

        // 打印查询到的记录数量以调试
        log.info("找到 {} 条待处理记录", pendingRecords.size());

        // 将待处理的随访记录标记为 'pending_visit'
        for (FollowUpRecord record : pendingRecords) {
            record.setFollowUpStatus("待随访");
            this.updateById(record);
            log.info("更新记录: {}", record);
        }
    }

}