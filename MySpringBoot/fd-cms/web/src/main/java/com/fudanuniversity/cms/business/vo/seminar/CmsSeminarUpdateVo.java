package com.fudanuniversity.cms.business.vo.seminar;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;
import java.util.Date;


/**
 * 演讲
 * <p>
 * Created by Xinyue.Tang at 2021-05-02
 */
@Data
@NoArgsConstructor

@ToString
public class CmsSeminarUpdateVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull
    private Long id;

    /**
     * 演讲用户学号
     */

    private String speakerName;

    private String speakerStuId;

    /**
     * 演讲时间
     */
    private Date date;

    /**
     * 演讲主题
     */


}

