package com.fudanuniversity.cms.business.vo.recorder;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;


/**
 * 演讲记录
 * <p>
 * Created by Xinyue.Tang at 2021-05-03
 */
@Data
@NoArgsConstructor
@ToString
public class CmsRecorderAddVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 发布时间
     */
    @NotNull
    private Date date;


}


