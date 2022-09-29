package com.fudanuniversity.cms.business.vo.section;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Data //自动生成相关的get和set方法
@NoArgsConstructor //生成无参构造函数
@ToString //该注解作用于类，啥作用，网上查了半天也看不明白
public class CmsSectionVo implements Serializable { //Serializable是一个对象序列化接口
    private static final long serialVersionUID = 1L; //流标识符，用于反序列化

    private Long id;

    private Date date;

    private String name;

    private String stuId;

    private String topic;

    private String sectionFileName;

    private String sectionFileType;

    private Integer isFile;

    private String paperRec;

}
