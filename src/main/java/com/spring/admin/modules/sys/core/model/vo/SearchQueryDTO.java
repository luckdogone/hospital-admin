package com.spring.admin.modules.sys.core.model.vo;

import com.spring.admin.data.domain.BasePageQuery;
import com.spring.admin.modules.sys.core.model.entity.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString
public class SearchQueryDTO extends BasePageQuery {
    private PatientInfo patientInfo;
    private CaseInfo caseInfo;
    private AdjuvantInfo adjuvantInfo;
    private GeneralInfo generalInfo;
    private EndocrineInfo endocrineInfo;
    private NeoadjuvantInfo neoadjuvantInfo;
    private RadiationInfo radiationInfo;
    private SurgicalInfo surgicalInfo;
}
