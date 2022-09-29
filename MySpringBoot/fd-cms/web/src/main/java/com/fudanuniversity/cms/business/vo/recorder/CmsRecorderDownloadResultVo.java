package com.fudanuniversity.cms.business.vo.recorder;

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
public class CmsRecorderDownloadResultVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件类型 Content-Type
     */
    private String fileType;

    /**
     * 文件内容
     */
    private byte[] fileContent;

}

