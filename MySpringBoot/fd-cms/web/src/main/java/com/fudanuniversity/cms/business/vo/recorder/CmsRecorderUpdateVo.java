package com.fudanuniversity.cms.business.vo.recorder;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


/**
 * 演讲记录
 * <p>
 * Created by Xinyue.Tang at 2021-05-03
 */
@Data
@NoArgsConstructor
@ToString
public class CmsRecorderUpdateVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @NotNull
    private Long id;

    /**
     * 辅读人员1用户学号
     */
    private String recorder1Name;

    private String recorder2Name;

    private String summarizerName;

    private String recorder;

    private Integer baseScore;

    private Integer isBattle;

}

