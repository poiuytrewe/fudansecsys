package com.fudanuniversity.cms.business.vo.section;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class CmsSectionUpdateVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Date date;

    private String name;

    private String stuId;

    private String topic;

    private String paperRec;
}
