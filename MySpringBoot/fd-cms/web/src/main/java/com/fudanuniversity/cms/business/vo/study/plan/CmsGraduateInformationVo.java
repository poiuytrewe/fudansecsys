package com.fudanuniversity.cms.business.vo.study.plan;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class CmsGraduateInformationVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String stuName;

    private String stuId;

    private String stuType;

    //预计毕业时间
    private String graduateTime;

    //目前的工作
    private String currentJob;

    //毕业的前置工作是否完成
    private String isDone;

    //未完成的工作集合
    private List<String> undoneList;

}
