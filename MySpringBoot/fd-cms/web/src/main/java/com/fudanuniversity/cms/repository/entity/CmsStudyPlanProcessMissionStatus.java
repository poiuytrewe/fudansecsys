package com.fudanuniversity.cms.repository.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
@Data
@NoArgsConstructor
@ToString
public class CmsStudyPlanProcessMissionStatus implements Serializable {
    private Integer count;
    private String stuId;
}
