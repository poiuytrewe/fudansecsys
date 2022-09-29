package com.fudanuniversity.cms.repository.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString
public class CmsProcessTinyCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer type;

    private Integer studyType;

    private String processCategory;
}
