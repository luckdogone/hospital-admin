CREATE TABLE patient_info (
    id VARCHAR(32) NOT NULL COMMENT '患者ID',
    name VARCHAR(32) NOT NULL COMMENT '患者姓名',
    sex TINYINT NOT NULL COMMENT '性别 (0: 女, 1: 男)',
    age INTEGER NOT NULL COMMENT '年龄',
    address VARCHAR(200) COMMENT '住址 (不超过50个汉字)',
    phone VARCHAR(20) COMMENT '电话',
    contact_phone VARCHAR(20) COMMENT '联系人电话',
    height DECIMAL(5,2) COMMENT '身高',
    weight DECIMAL(5,2) COMMENT '体重',
    input_status INTEGER NOT NULL COMMENT '录入状态 (0: 未录入, 1: 录入中, 2: 已录入)',
    created_by VARCHAR(32) COMMENT '创建者',
    created DATETIME COMMENT '创建时间',
    modified_by VARCHAR(32) COMMENT '更新者',
    modified DATETIME COMMENT '更新时间',
    is_enable TINYINT NOT NULL COMMENT '是否启用 (0: 禁用, 1: 启用)',
    is_del TINYINT NOT NULL COMMENT '是否删除 (0: 删除, 1: 正常)',
    PRIMARY KEY (id)
) comment '基础资料';

INSERT INTO patient_info (id, name, sex, age, address, phone, contact_phone, height, weight, input_status, created_by, created, modified_by, modified, is_enable, is_del) VALUES
('1', '张三', '0', 30, '北京市海淀区', '13800138000', '13900139000', 175.00, 70.00, 1, 'admin', '2024-08-01 10:00:00', 'admin', '2024-08-01 10:00:00', 1, 0),
('2', '李四', '1', 25, '上海市浦东新区', '13800238000', '13900239000', 165.00, 55.00, 1, 'admin', '2024-08-01 10:05:00', 'admin', '2024-08-01 10:05:00', 1, 0),
('3', '王五', '0', 40, '广州市天河区', '13800338000', '13900339000', 180.00, 80.00, 1, 'admin', '2024-08-01 10:10:00', 'admin', '2024-08-01 10:10:00', 1, 0),
('4', '赵六', '1', 35, '深圳市南山区', '13800438000', '13900439000', 160.00, 50.00, 1, 'admin', '2024-08-01 10:15:00', 'admin', '2024-08-01 10:15:00', 1, 0),
('5', '周七', '0', 50, '杭州市西湖区', '13800538000', '13900539000', 170.00, 75.00, 1, 'admin', '2024-08-01 10:20:00', 'admin', '2024-08-01 10:20:00', 1, 0);

CREATE TABLE general_info (
    id VARCHAR(32) NOT NULL COMMENT 'ID',
    surgical_num VARCHAR(32) COMMENT '手术编号',
    patient_id VARCHAR(32) NOT NULL COMMENT '患者ID，和基础信息关联',
    case_no VARCHAR(32) COMMENT '病案号',
    input_status INTEGER NOT NULL COMMENT '录入状态 (0: 未录入, 1: 录入中, 2: 已录入)',
    created_by VARCHAR(32) COMMENT '创建者',
    created DATETIME COMMENT '创建时间',
    modified_by VARCHAR(32) COMMENT '更新者',
    modified DATETIME COMMENT '更新时间',
    is_enable TINYINT NOT NULL COMMENT '是否启用 (0: 禁用, 1: 启用)',
    is_del TINYINT NOT NULL COMMENT '是否删除 (0: 删除, 1: 正常)',
    PRIMARY KEY (id)
) COMMENT '一般资料';

INSERT INTO general_info (id, surgical_num, patient_id, case_no, input_status, created_by, created, modified_by, modified, is_enable, is_del) VALUES
('1', 'SN001', '1', 'CN001', 2, 'user1', '2024-08-07 10:00:00', 'user2', '2024-08-07 12:00:00', 1, 1),
('2', 'SN002', '2', 'CN002', 1, 'user3', '2024-08-07 11:00:00', 'user4', '2024-08-07 13:00:00', 1, 1),
('3', 'SN003', '3', 'CN003', 0, 'user5', '2024-08-07 09:00:00', 'user6', '2024-08-07 14:00:00', 0, 0),
('4', 'SN004', '4', 'CN004', 2, 'user7', '2024-08-07 08:00:00', 'user8', '2024-08-07 15:00:00', 1, 1),
('5', 'SN005', '5', 'CN005', 1, 'user9', '2024-08-07 07:00:00', 'user10', '2024-08-07 16:00:00', 1, 0);

CREATE TABLE case_info (
    id VARCHAR(32) NOT NULL COMMENT '病历ID，主键',
    patient_id VARCHAR(32) NOT NULL COMMENT '患者ID，外键关联患者表',

    -- 入院超声检查
    ultrasound_status TINYINT COMMENT '入院超声状态（0: 未做, 1: 左, 2: 右, 3: 双侧）',
    ultrasound_size FLOAT COMMENT '超声大小，单位cm',
    ultrasound_blood_flow TINYINT COMMENT '是否有血流信号',
    ultrasound_birads TINYINT COMMENT 'BIRADS分类（超声可以填写字母，有限取值）',

    -- 入院钼靶检查
    mammography_status TINYINT COMMENT '入院钼靶状态（（0: 未做, 1: 左, 2: 右, 3: 双侧）未做或不做会有下列属性的值）',
    mammography_size FLOAT COMMENT '钼靶大小，单位cm',
    mammography_aggregation TINYINT COMMENT '是否有细小钙化影',
    mammography_birads TINYINT COMMENT 'BIRADS分类',

    -- 入院乳腺核磁检查
    mri_status TINYINT COMMENT '入院乳腺核磁（（0: 未做, 1: 左, 2: 右, 3: 双侧）未做、左、右、双侧；未做状态不会有下列属性的值）',
    mri_size FLOAT COMMENT '乳腺大小，单位cm',
    mri_blood_flow TINYINT COMMENT '是否有增强信号',
    mri_birads TINYINT COMMENT 'BIRADS分类',

    -- 血常规检查
    wbc FLOAT COMMENT '白细胞计数',
    rbc FLOAT COMMENT '红细胞计数',
    platelets FLOAT COMMENT '血小板计数',

    -- 肝功能检查
    alt FLOAT COMMENT '谷丙转氨酶',
    ast FLOAT COMMENT '谷草转氨酶',
    alkaline_phosphatase FLOAT COMMENT '碱性磷酸酶',

    -- 肾功能检查
    creatinine FLOAT COMMENT '血肌酐',
    bun FLOAT COMMENT '血清尿素',
    uric_acid FLOAT COMMENT '尿酸',

    -- 血脂检查
    triglycerides FLOAT COMMENT '甘油三酯',
    ldl FLOAT COMMENT '低密度脂蛋白',

    -- D-二聚体检查
    dimer FLOAT COMMENT 'D-二聚体',

    -- 肿瘤标志物检查
    cea FLOAT COMMENT '癌胚抗原（CEA）',
    ca153 FLOAT COMMENT '癌抗原153（CA153）',
    ca125 FLOAT COMMENT '癌抗原125（CA125）',

    -- 乳腺粗针穿刺病理结果
    breast_core_needle TINYINT COMMENT '有无乳腺粗针穿刺（0:无, 1:有 无状态不会有下列属性的值）',
    breast_core_needle_result TEXT COMMENT '乳腺粗针穿刺病理结果',

    -- 腋窝粗针穿刺病理结果
    axillary_core_needle TINYINT COMMENT '有无腋窝粗针穿刺（0:无, 1:有 无状态不会有下列属性的值）',
    axillary_core_needle_result TEXT COMMENT '腋窝粗针穿刺病理结果',

    -- 腋窝细针穿刺病理结果
    axillary_fine_needle TINYINT COMMENT '有无腋窝细针穿刺（0:无, 1:有 无状态不会有下列属性的值）',
    axillary_fine_needle_result TEXT COMMENT '腋窝细针穿刺病理结果',

    -- 免疫组化结果
    ihc_result TINYINT COMMENT '有无免疫组化结果（0:无, 1:有 无状态不会有下列属性的值）',
    er_pct FLOAT COMMENT '雌激素受体表达百分比（ER%）',
    pr_pct FLOAT COMMENT '孕激素受体表达百分比（PR%）',
    her2 ENUM('0', '1+', '2+', '3+') COMMENT '人类表皮生长因子受体2（HER2）',
    ki67_pct FLOAT COMMENT 'Ki-67标志物百分比',
    ar_pct FLOAT COMMENT '雄激素受体表达百分比（AR%）',
    fish_test ENUM('阴性', '阳性') COMMENT 'FISH检测（阴性/阳性）',

    -- TNM分期
    tnm VARCHAR(255) COMMENT 'TNM分期：cT/N/M',
    stage ENUM('无', 'I', 'II', 'III') COMMENT '分期：无/I/II/III',

    -- 亚型分型
    subtype ENUM('三阴性', 'Luminal A', 'Luminal B HER2阴性型', 'Luminal B HER2阳性型') COMMENT '分型：三阴性/Luminal A/Luminal B HER2阴性型/Luminal B HER2阳性型',

    -- 入录信息
    input_status INTEGER NOT NULL COMMENT '录入状态 (0: 未录入, 1: 录入中, 2: 已录入)',
    created_by VARCHAR(32) COMMENT '创建者',
    created DATETIME COMMENT '创建时间',
    modified_by VARCHAR(32) COMMENT '更新者',
    modified DATETIME COMMENT '更新时间',
    is_enable TINYINT NOT NULL COMMENT '是否启用 (0: 禁用, 1: 启用)',
    is_del TINYINT NOT NULL COMMENT '是否删除 (0: 删除, 1: 正常)',

    PRIMARY KEY (id)
) COMMENT '病历资料';

INSERT INTO case_info (
    id, patient_id, ultrasound_status, ultrasound_size, ultrasound_blood_flow, ultrasound_birads,
    mammography_status, mammography_size, mammography_aggregation, mammography_birads,
    mri_status, mri_size, mri_blood_flow, mri_birads,
    wbc, rbc, platelets, alt, ast, alkaline_phosphatase,
    creatinine, bun, uric_acid, triglycerides, ldl, dimer,
    cea, ca153, ca125, breast_core_needle, breast_core_needle_result,
    axillary_core_needle, axillary_core_needle_result, axillary_fine_needle, axillary_fine_needle_result,
    ihc_result, er_pct, pr_pct, her2, ki67_pct, ar_pct, fish_test,
    tnm, stage, subtype, input_status, created_by, created, modified_by, modified, is_enable, is_del
) VALUES
('C002', 'P002', 1, 1.5, 0, 2,
 1, 2.0, 0, 2,
 0, NULL, NULL, NULL,
 6.2, 4.2, 280, 40, 45, 85,
 1.0, 6.5, 340, 2.2, 2.8, 0.3,
 2.5, 18.4, 12.3, 0, NULL,
 0, NULL, 0, NULL,
 0, NULL, NULL, NULL, NULL, NULL, NULL,
 'cT1/N0/M0', 'I', 'Luminal A', 2, 'Dr. Zhang', '2024-08-11 09:30:00', 'Dr. Li', '2024-08-11 10:15:00', 1, 1),

('C003', 'P003', 2, 3.0, 1, 4,
 3, 4.5, 1, 5,
 2, 3.5, 1, 4,
 7.1, 4.8, 270, 38, 34, 92,
 1.3, 7.2, 355, 1.9, 2.3, 0.6,
 3.4, 26.1, 16.9, 1, '恶性肿瘤',
 1, '腋窝阳性结果', 0, NULL,
 1, 90.0, 60.0, '2+', 25.0, 15.0, '阴性',
 'cT3/N2/M0', 'III', '三阴性', 2, 'Dr. Wang', '2024-08-11 11:00:00', 'Dr. Huang', '2024-08-11 12:00:00', 1, 1),

('C004', 'P004', 3, 2.2, 1, 3,
 2, 2.8, 0, 3,
 1, 2.5, 0, 3,
 8.0, 4.9, 265, 42, 50, 100,
 1.5, 6.8, 300, 1.7, 2.5, 0.4,
 3.0, 22.0, 15.5, 0, NULL,
 1, '阳性结果', 1, '阴性结果',
 1, 85.0, 55.0, '3+', 18.0, 11.0, '阳性',
 'cT2/N1/M0', 'II', 'Luminal B HER2阳性型', 2, 'Dr. Sun', '2024-08-12 08:45:00', 'Dr. Zhao', '2024-08-12 09:30:00', 1, 1),

('C005', 'P005', 0, NULL, NULL, NULL,
 0, NULL, NULL, NULL,
 0, NULL, NULL, NULL,
 5.8, 4.0, 245, 30, 32, 80,
 0.9, 5.5, 325, 1.5, 1.8, 0.2,
 2.0, 15.0, 10.0, 0, NULL,
 0, NULL, 0, NULL,
 0, NULL, NULL, NULL, NULL, NULL, NULL,
 'cT1/N0/M0', 'I', 'Luminal A', 1, 'Dr. Li', '2024-08-10 14:00:00', 'Dr. Liu', '2024-08-10 15:00:00', 1, 1),

('C006', 'P006', 3, 2.7, 1, 4,
 3, 4.0, 1, 4,
 3, 3.8, 1, 5,
 8.5, 5.1, 280, 45, 52, 105,
 1.6, 7.4, 360, 2.1, 2.9, 0.7,
 3.6, 28.0, 19.5, 1, '阳性结果',
 1, '腋窝阳性结果', 1, '阴性结果',
 1, 95.0, 65.0, '3+', 30.0, 20.0, '阳性',
 'cT3/N2/M0', 'III', '三阴性', 2, 'Dr. Liu', '2024-08-12 11:00:00', 'Dr. Chen', '2024-08-12 12:00:00', 1, 1);

CREATE TABLE surgical_info (
    id VARCHAR(32) NOT NULL COMMENT 'id',
    patient_id VARCHAR(32) NOT NULL COMMENT '患者id，和基础信息关联',
    surgery_time DATETIME COMMENT '手术时间',

    -- 乳腺相关字段
    is_total_partial TINYINT COMMENT '是否全切/保乳',
    is_breast_conserve TINYINT COMMENT '是否保乳整形',
    is_nipple_spare TINYINT COMMENT '是否保留乳头乳晕',
    is_endoscopic TINYINT COMMENT '是否腔镜',
    is_reconstruction TINYINT COMMENT '是否重建',
    recon_method VARCHAR(10) COMMENT '重建方式: 自体/假体',
    tumor_size_cm DECIMAL(10, 2) COMMENT '大小: cm',
    has_lymph_invasion TINYINT COMMENT '有无脉管癌栓',
    has_nerve_invasion TINYINT COMMENT '有无神经侵犯',
    postop_path_grade VARCHAR(5) COMMENT '术后病理分级: 无/I/II/III',
    is_multifocal TINYINT COMMENT '是否多发',
    tumor_location ENUM('中央区', '外上象限', '内上象限', '外下象限', '内下象限') COMMENT '肿瘤位置: 中央区/外上象限/内上象限/外下象限/内下象限',


    -- 淋巴结相关字段
    has_sln_biopsy TINYINT COMMENT '是否前哨淋巴结活检',
    use_ns TINYINT COMMENT '是否使用核素',
    pre_tad TINYINT COMMENT '是否新辅助前TAD',
    sln_count INT COMMENT '前哨淋巴结数量',
    sln_meta TINYINT COMMENT '是否前哨淋巴结转移',
    sln_meta_count INT COMMENT '前哨淋巴结转移数量',
    sln_micro_meta_count INT COMMENT '前哨微转移数量',
    sln_itc_count INT COMMENT '前哨孤立肿瘤细胞数量',
    ald TINYINT COMMENT '是否腋窝清扫',
    ald_stage VARCHAR(10) COMMENT '腋窝清扫：I/II/III站',
    ald_count INT COMMENT '腋窝清扫淋巴结数量',
    ald_meta_count INT COMMENT '腋窝淋巴结转移数量',
    ald_micro_meta_count INT COMMENT '腋窝微转移数量',
    ald_itc_count INT COMMENT '腋窝孤立肿瘤细胞数量',
    apex_meta TINYINT COMMENT '是否有腋尖淋巴结转移',
    im_meta TINYINT COMMENT '是否有肌间淋巴结转移',

    -- 免疫组化结果
    ihc_result TINYINT COMMENT '有无免疫组化结果（0:无, 1:有 无状态不会有下列属性的值）',
    er_pct FLOAT COMMENT '雌激素受体表达百分比（ER%）',
    pr_pct FLOAT COMMENT '孕激素受体表达百分比（PR%）',
    her2 ENUM('0', '1+', '2+', '3+') COMMENT '人类表皮生长因子受体2（HER2）',
    ki67_pct FLOAT COMMENT 'Ki-67标志物百分比',
    ar_pct FLOAT COMMENT '雄激素受体表达百分比（AR%）',
    fish_test ENUM('阴性', '阳性') COMMENT 'FISH检测（阴性/阳性）',

    -- TNM分期
    tnm VARCHAR(255) COMMENT 'TNM分期：cT/N/M',
    stage ENUM('无', 'I', 'II', 'III') COMMENT '分期：无/I/II/III',

    -- 亚型分型
    subtype ENUM('三阴性', 'Luminal A', 'Luminal B HER2阴性型', 'Luminal B HER2阳性型') COMMENT '分型：三阴性/Luminal A/Luminal B HER2阴性型/Luminal B HER2阳性型',

    -- MP级别/级
    mp_level ENUM('MP0', 'MP1', 'MP2', 'MP3', 'MP4') COMMENT 'MP级别: 无浸润(MP0)/微浸润(MP1)/中度浸润(MP2)/重度浸润(MP3)/完全浸润(MP4)',

    -- 入录信息
    input_status INTEGER NOT NULL COMMENT '录入状态 (0: 未录入, 1: 录入中, 2: 已录入)',
    created_by VARCHAR(32) COMMENT '创建者',
    created DATETIME COMMENT '创建时间',
    modified_by VARCHAR(32) COMMENT '更新者',
    modified DATETIME COMMENT '更新时间',
    is_enable TINYINT NOT NULL COMMENT '是否启用 (0: 禁用, 1: 启用)',
    is_del TINYINT NOT NULL COMMENT '是否删除 (0: 删除, 1: 正常)',

    PRIMARY KEY (id)
) COMMENT '手术相关';

-- 插入模拟数据的SQL语句
INSERT INTO surgical_info (id, patient_id, surgery_time, is_total_partial, is_breast_conserve, is_nipple_spare, is_endoscopic, is_reconstruction, recon_method, tumor_size_cm, has_lymph_invasion, has_nerve_invasion, postop_path_grade, is_multifocal, tumor_location, has_sln_biopsy, use_ns, pre_tad, sln_count, sln_meta, sln_meta_count, sln_micro_meta_count, sln_itc_count, ald, ald_stage, ald_count, ald_meta_count, ald_micro_meta_count, ald_itc_count, apex_meta, im_meta, ihc_result, er_pct, pr_pct, her2, ki67_pct, ar_pct, fish_test, tnm, stage, subtype, mp_level, input_status, created_by, created, modified_by, modified, is_enable, is_del)
VALUES
('001', 'P001', '2023-07-01 09:00:00', 1, 0, 1, 0, 0, '假体', 2.5, 1, 0, 'II', 0, '中央区', 1, 1, 0, 2, 1, 1, 0, 0, 1, 'I', 10, 3, 0, 0, 0, 0, 1, 75.0, 60.0, '2+', 15.0, 35.0, '阳性', 'cT2N1M0', 'II', 'Luminal B HER2阳性型', 'MP2', 2, 'admin', '2023-07-01 10:00:00', 'admin', '2023-07-01 10:30:00', 1, 1),

('002', 'P002', '2023-07-02 11:00:00', 0, 1, 0, 1, 1, '自体', 3.2, 0, 1, 'I', 1, '外上象限', 1, 0, 1, 3, 1, 2, 1, 1, 1, 'II', 12, 4, 1, 1, 1, 1, 1, 80.0, 50.0, '1+', 20.0, 30.0, '阴性', 'cT1N2M0', 'III', 'Luminal A', 'MP3', 2, 'admin', '2023-07-02 12:00:00', 'admin', '2023-07-02 12:30:00', 1, 1),

('003', 'P003', '2023-07-03 14:00:00', 1, 0, 0, 0, 0, '假体', 1.8, 1, 0, 'III', 0, '内下象限', 0, 1, 0, 1, 0, 0, 0, 0, 0, 'III', 15, 5, 0, 0, 0, 0, 1, 85.0, 40.0, '3+', 25.0, 40.0, '阳性', 'cT3N1M0', 'III', '三阴性', 'MP4', 1, 'admin', '2023-07-03 15:00:00', 'admin', '2023-07-03 15:30:00', 1, 1),

('004', 'P004', '2023-07-04 16:00:00', 0, 1, 1, 1, 1, '自体', 4.0, 0, 1, 'II', 1, '外下象限', 1, 0, 1, 4, 1, 3, 1, 1, 1, 'II', 18, 6, 1, 1, 1, 1, 0, 90.0, 55.0, '0', 30.0, 45.0, '阴性', 'cT2N2M0', 'III', 'Luminal B HER2阴性型', 'MP1', 2, 'admin', '2023-07-04 17:00:00', 'admin', '2023-07-04 17:30:00', 1, 1),

('005', 'P005', '2023-07-05 08:00:00', 1, 0, 1, 0, 0, '假体', 2.0, 1, 0, 'I', 0, '内上象限', 0, 1, 0, 2, 0, 0, 0, 0, 1, 'I', 10, 3, 0, 0, 0, 0, 1, 95.0, 65.0, '2+', 35.0, 55.0, '阳性', 'cT1N0M0', 'I', 'Luminal A', 'MP0', 2, 'admin', '2023-07-05 09:00:00', 'admin', '2023-07-05 09:30:00', 1, 1);

CREATE TABLE neoadjuvant_info (
    id VARCHAR(32) NOT NULL COMMENT 'id',
    patient_id VARCHAR(32) NOT NULL COMMENT '患者id，和基础信息关联',

    neoadj_therapy TINYINT COMMENT '是否进行新辅助治疗',
    clin_research TINYINT COMMENT '是否加入临床研究',
    research_details VARCHAR(255) COMMENT '具体临床研究',

    therapy_plan TEXT COMMENT '具体新辅助治疗方案',
    immunotherapy TINYINT COMMENT '是否有免疫药物应用',

    week_1_size DECIMAL(10, 2) COMMENT '1周期超声示肿物大小',
    week_2_size DECIMAL(10, 2) COMMENT '2周期超声示肿物大小',
    week_3_size DECIMAL(10, 2) COMMENT '3周期超声示肿物大小',
    week_4_size DECIMAL(10, 2) COMMENT '4周期超声示肿物大小',
    week_5_size DECIMAL(10, 2) COMMENT '5周期超声示肿物大小',
    week_6_size DECIMAL(10, 2) COMMENT '6周期超声示肿物大小',
    week_2_3_size DECIMAL(10, 2) COMMENT '2/3周期超声示肿物大小',
    week_4_5_size DECIMAL(10, 2) COMMENT '4/5周期超声示肿物大小',
    week_6_7_size DECIMAL(10, 2) COMMENT '6/7周期超声示肿物大小',
    week_8_size DECIMAL(10, 2) COMMENT '8周期超声示肿物大小',

    dose_adjust TINYINT COMMENT '是否有新辅助药物剂量调整',
    adjust_reason TEXT COMMENT '新辅助药物剂量调整原因',

    therapy_term TINYINT COMMENT '是否终止新辅助治疗',
    term_reason TEXT COMMENT '终止新辅助治疗原因',

    gcsf TINYINT COMMENT '是否有升白针应用',
    long_gcsf TINYINT COMMENT '是否有长效升白针应用',

    -- 入录信息
    input_status INTEGER NOT NULL COMMENT '录入状态 (0: 未录入, 1: 录入中, 2: 已录入)',
    created_by VARCHAR(32) COMMENT '创建者',
    created DATETIME COMMENT '创建时间',
    modified_by VARCHAR(32) COMMENT '更新者',
    modified DATETIME COMMENT '更新时间',
    is_enable TINYINT NOT NULL COMMENT '是否启用 (0: 禁用, 1: 启用)',
    is_del TINYINT NOT NULL COMMENT '是否删除 (0: 删除, 1: 正常)',

    PRIMARY KEY (id)
) COMMENT '新辅助治疗';

-- 插入模拟数据
INSERT INTO neoadjuvant_info (
    id, patient_id, neoadj_therapy, clin_research, research_details,
    therapy_plan, immunotherapy, week_1_size, week_2_size, week_3_size,
    week_4_size, week_5_size, week_6_size, week_2_3_size, week_4_5_size,
    week_6_7_size, week_8_size, dose_adjust, adjust_reason, therapy_term,
    term_reason, gcsf, long_gcsf, input_status, created_by, created,
    modified_by, modified, is_enable, is_del
) VALUES
('1', 'P001', 1, 0, NULL,
 '方案A', 1, 2.5, 2.4, 2.3,
 2.2, 2.1, 2.0, 2.3, 2.2,
 2.1, 2.0, 0, NULL, 0,
 NULL, 1, 0, 2, '医生甲', '2024-08-15 10:00:00',
 '医生乙', '2024-08-15 12:00:00', 1, 1),

('2', 'P002', 1, 1, '研究X',
 '方案B', 0, 3.5, 3.4, 3.3,
 3.2, 3.1, 3.0, 3.4, 3.2,
 3.1, 3.0, 1, '患者出现副作用', 0,
 NULL, 1, 0, 1, '医生丙', '2024-08-16 09:00:00',
 '医生丙', '2024-08-16 09:30:00', 1, 1),

('3', 'P003', 0, 0, NULL,
 '方案C', 1, 4.5, 4.4, 4.3,
 4.2, 4.1, 4.0, 4.4, 4.2,
 4.1, 4.0, 1, '因病情进展增加剂量', 1,
 '由于不良反应停止治疗', 0, 0, 0, '医生丁', '2024-08-16 14:00:00',
 '医生戊', '2024-08-16 16:00:00', 1, 1),

('4', 'P004', 1, 1, '研究Y',
 '方案D', 0, 1.5, 1.4, 1.3,
 1.2, 1.1, 1.0, 1.3, 1.2,
 1.1, 1.0, 0, NULL, 0,
 NULL, 1, 0, 2, '医生己', '2024-08-17 08:00:00',
 '医生庚', '2024-08-17 10:00:00', 1, 1),

('5', 'P005', 1, 1, '研究Z',
 '方案E', 1, 5.5, 5.4, 5.3,
 5.2, 5.1, 5.0, 5.4, 5.2,
 5.1, 5.0, 1, '因患者耐受性降低调整剂量', 1,
 '患者要求终止治疗', 1, 0, 2, '医生辛', '2024-08-17 12:00:00',
 '医生辛', '2024-08-17 12:30:00', 1, 1);

CREATE TABLE adjuvant_info (
    id VARCHAR(32) NOT NULL COMMENT 'id',
    patient_id VARCHAR(32) NOT NULL COMMENT '患者id，和基础信息关联',

    adjuvant_therapy TINYINT COMMENT '是否进行辅助治疗',
    clinical_research TINYINT COMMENT '是否加入临床研究',
    research_details TEXT COMMENT '具体临床研究',

    therapy_plan TEXT COMMENT '具体辅助治疗方案',

    intensified_therapy TINYINT COMMENT '是否有新辅助治疗后强化治疗应用',
    intensified_plan TEXT COMMENT '具体强化辅助治疗方案',

    dose_adjustment TINYINT COMMENT '是否有辅助药物剂量调整',
    adjustment_reason TEXT COMMENT '辅助药物剂量调整原因',

    therapy_termination TINYINT COMMENT '是否终止辅助治疗',
    termination_reason TEXT COMMENT '终止辅助治疗原因',

    -- 入录信息
    input_status INTEGER NOT NULL COMMENT '录入状态 (0: 未录入, 1: 录入中, 2: 已录入)',
    created_by VARCHAR(32) COMMENT '创建者',
    created DATETIME COMMENT '创建时间',
    modified_by VARCHAR(32) COMMENT '更新者',
    modified DATETIME COMMENT '更新时间',
    is_enable TINYINT NOT NULL COMMENT '是否启用 (0: 禁用, 1: 启用)',
    is_del TINYINT NOT NULL COMMENT '是否删除 (0: 删除, 1: 正常)',

    PRIMARY KEY (id)
) COMMENT '辅助治疗';

INSERT INTO adjuvant_info (
    id, patient_id, adjuvant_therapy, clinical_research, research_details,
    therapy_plan, intensified_therapy, intensified_plan,
    dose_adjustment, adjustment_reason,
    therapy_termination, termination_reason,
    input_status, created_by, created, modified_by, modified, is_enable, is_del
) VALUES
('1', 'P001', 1, 1, '研究A',
 '标准化疗方案', 1, '强化方案A',
 0, NULL,
 0, NULL,
 1, '医生甲', '2024-08-16 10:00:00', '医生乙', '2024-08-16 12:00:00', 1, 0),

('2', 'P002', 0, 0, NULL,
 NULL, 0, NULL,
 0, NULL,
 0, NULL,
 2, '医生乙', '2024-08-15 09:00:00', '医生丙', '2024-08-16 11:00:00', 1, 0),

('3', 'P003', 1, 1, '研究B',
 '辅助治疗方案B', 0, NULL,
 1, '患者对药物不耐受',
 1, '患者意愿终止治疗',
 1, '医生甲', '2024-08-14 08:30:00', '医生丙', '2024-08-16 12:30:00', 1, 0),

('4', 'P004', 1, 0, NULL,
 '标准化疗方案C', 1, '强化方案C',
 1, '药物剂量调整为70%',
 0, NULL,
 2, '医生丙', '2024-08-13 11:00:00', '医生甲', '2024-08-16 14:00:00', 1, 0),

('5', 'P005', 1, 1, '研究C',
 '个体化治疗方案D', 1, '强化方案D',
 1, '副作用较大，调整剂量',
 1, '治疗后复发风险大，提前终止',
 0, '医生乙', '2024-08-12 10:45:00', '医生甲', '2024-08-16 15:15:00', 1, 0);

CREATE TABLE endocrine_info (
    id VARCHAR(32) NOT NULL COMMENT 'id',
    patient_id VARCHAR(32) NOT NULL COMMENT '患者id，和基础信息关联',

    endocrine_treatment TINYINT COMMENT '是否进行内分泌治疗',
    clinical_research TINYINT COMMENT '是否加入临床研究',
    clinical_research_details VARCHAR(255) COMMENT '具体临床研究',
    treatment_plan VARCHAR(255) COMMENT '具体内分泌治疗方案',
    enhanced_treatment TINYINT COMMENT '是否有内分泌强化治疗应用',
    enhanced_plan VARCHAR(255) COMMENT '具体强化内分泌治疗方案',
    dosage_adjustment TINYINT COMMENT '是否有内分泌剂量调整',
    adjustment_reason VARCHAR(255) COMMENT '内分泌药物剂量调整原因',
    medication_adjustment TINYINT COMMENT '是否有内分泌药物调整',
    med_adjust_reason VARCHAR(255) COMMENT '内分泌药物调整原因',

    -- 入录信息
    input_status INTEGER NOT NULL COMMENT '录入状态 (0: 未录入, 1: 录入中, 2: 已录入)',
    created_by VARCHAR(32) COMMENT '创建者',
    created DATETIME COMMENT '创建时间',
    modified_by VARCHAR(32) COMMENT '更新者',
    modified DATETIME COMMENT '更新时间',
    is_enable TINYINT NOT NULL COMMENT '是否启用 (0: 禁用, 1: 启用)',
    is_del TINYINT NOT NULL COMMENT '是否删除 (0: 删除, 1: 正常)',

    PRIMARY KEY (id)
) COMMENT '内分泌治疗';

INSERT INTO endocrine_info (
    id, patient_id, endocrine_treatment, clinical_research, clinical_research_details,
    treatment_plan, enhanced_treatment, enhanced_plan, dosage_adjustment, adjustment_reason,
    medication_adjustment, med_adjust_reason, input_status, created_by, created,
    modified_by, modified, is_enable, is_del
) VALUES
('1a2b3c4d5e6f7g8h9i0j', 'p12345', 1, 0, NULL,
 '标准治疗方案A', 1, '强化方案B', 0, NULL,
 0, NULL, 2, '用户1', '2024-08-01 10:00:00',
 '用户1', '2024-08-02 12:00:00', 1, 1),

('2b3c4d5e6f7g8h9i0j1a', 'p12346', 0, 1, '研究项目X',
 NULL, 0, NULL, 1, '因耐受性增加调整剂量',
 1, '更换为替代药物', 1, '用户2', '2024-08-05 09:30:00',
 '用户2', '2024-08-06 11:15:00', 1, 0),

('3c4d5e6f7g8h9i0j1a2b', 'p12347', 1, 1, '研究项目Y',
 '自定义治疗方案C', 1, '强化方案D', 1, '因副作用减少剂量',
 0, NULL, 2, '用户3', '2024-08-07 08:45:00',
 '用户3', '2024-08-08 14:20:00', 1, 1),

('4d5e6f7g8h9i0j1a2b3c', 'p12348', 0, 0, NULL,
 '标准治疗方案B', 0, NULL, 0, NULL,
 1, '因过敏反应更换药物', 0, '用户4', '2024-08-09 10:50:00',
 '用户4', '2024-08-10 16:35:00', 1, 1),

('5e6f7g8h9i0j1a2b3c4d', 'p12349', 1, 1, '研究项目Z',
 '实验性治疗方案E', 1, '强化方案F', 0, NULL,
 0, NULL, 2, '用户5', '2024-08-11 09:10:00',
 '用户5', '2024-08-12 15:45:00', 0, 0);

CREATE TABLE radiation_info (
    id VARCHAR(32) NOT NULL COMMENT 'id',
    patient_id VARCHAR(32) NOT NULL COMMENT '患者id，和基础信息关联',

    radiation_treatment TINYINT COMMENT '是否进行放射治疗',
    clinical_research TINYINT COMMENT '是否加入临床研究',
    clinical_research_details VARCHAR(255) COMMENT '具体临床研究',
    treatment_plan VARCHAR(255) COMMENT '具体放疗方案',
    treatment_location VARCHAR(255) COMMENT '放疗部位',

    -- 入录信息
    input_status INTEGER NOT NULL COMMENT '录入状态 (0: 未录入, 1: 录入中, 2: 已录入)',
    created_by VARCHAR(32) COMMENT '创建者',
    created DATETIME COMMENT '创建时间',
    modified_by VARCHAR(32) COMMENT '更新者',
    modified DATETIME COMMENT '更新时间',
    is_enable TINYINT NOT NULL COMMENT '是否启用 (0: 禁用, 1: 启用)',
    is_del TINYINT NOT NULL COMMENT '是否删除 (0: 删除, 1: 正常)',

    PRIMARY KEY (id)
) COMMENT '放射治疗';

INSERT INTO radiation_info (
    id, patient_id, radiation_treatment, clinical_research, clinical_research_details,
    treatment_plan, treatment_location, input_status, created_by, created,
    modified_by, modified, is_enable, is_del
) VALUES
('1a2b3c4d5e6f7g8h9i0j', 'p12345', 1, 0, NULL,
 '标准放疗方案A', '头部', 2, '用户1', '2024-08-01 10:00:00',
 '用户1', '2024-08-02 12:00:00', 1, 1),

('2b3c4d5e6f7g8h9i0j1a', 'p12346', 0, 1, '临床研究X',
 NULL, '胸部', 1, '用户2', '2024-08-05 09:30:00',
 '用户2', '2024-08-06 11:15:00', 1, 0),

('3c4d5e6f7g8h9i0j1a2b', 'p12347', 1, 1, '临床研究Y',
 '个性化放疗方案B', '腹部', 2, '用户3', '2024-08-07 08:45:00',
 '用户3', '2024-08-08 14:20:00', 1, 1),

('4d5e6f7g8h9i0j1a2b3c', 'p12348', 0, 0, NULL,
 '标准放疗方案C', '骨盆', 0, '用户4', '2024-08-09 10:50:00',
 '用户4', '2024-08-10 16:35:00', 1, 1),

('5e6f7g8h9i0j1a2b3c4d', 'p12349', 1, 1, '临床研究Z',
 '实验性放疗方案D', '肺部', 2, '用户5', '2024-08-11 09:10:00',
 '用户5', '2024-08-12 15:45:00', 0, 0);



