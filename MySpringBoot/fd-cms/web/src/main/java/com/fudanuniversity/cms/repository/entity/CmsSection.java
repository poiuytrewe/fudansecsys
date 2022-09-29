package com.fudanuniversity.cms.repository.entity;
import com.fudanuniversity.cms.commons.validation.constraints.In;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class CmsSection implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Date date;

    private String name;

    private String stuId;

    private String topic;

    private byte[] sectionFile;

    private String sectionFileName;

    private String sectionFileType;

    private Integer isFile;

    private String paperRec;
}
