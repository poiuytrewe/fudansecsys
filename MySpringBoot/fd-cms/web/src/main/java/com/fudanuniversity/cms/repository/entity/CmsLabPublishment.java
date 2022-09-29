package com.fudanuniversity.cms.repository.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class CmsLabPublishment implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String labName;

    private String labFileName;

    private String labType;

    private String labPublisher;

    private String labPublisherStuId;

    private Date date;
}
