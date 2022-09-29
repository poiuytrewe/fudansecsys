package com.fudanuniversity.cms.business.vo.seminar;

import jakarta.validation.constraints.NotEmpty;
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
public class CmsSeminarAddVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 演讲用户学号
     */
    private Integer groupId;
}

