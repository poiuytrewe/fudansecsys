package com.fudanuniversity.cms.repository.query;

import com.fudanuniversity.cms.commons.model.query.BaseQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

/**
 * 查询对象
 * <p>
 * Created by Xinyue.Tang at 2021-05-03
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class CmsRecorderQuery extends BaseQuery {

    private static final long serialVersionUID = 1L;

    /**
     * 字段备注:id <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:是
     */
    private Long id;

    /**
     * 字段备注:id
     * 数据库字段长度:(19,0)
     * 索引字段:是
     */
//    private Long gtId;
//
//    /**
//     * 字段备注:id <p>
//     * 数据库字段长度:(19,0) <p>
//     * 索引字段:是
//     */
//    private List<Long> idList;

    /**
     * 字段备注:演讲时间 <p>
     * 数据库字段长度:(10,0) <p>
     * 索引字段:是
     */
    private Date date;

    /**
     * 字段备注:辅读人员1用户id <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:不是
     */

    private Integer groupId;

    private String recorder1Name;

    /**
     * 字段备注:辅读人员1文件名 <p>
     * 数据库字段长度:(128,0) <p>
     * 索引字段:不是
     */
    private String recorder2Name;

    /**
     * 字段备注:辅读人员1文件类型 <p>
     * 数据库字段长度:(128,0) <p>
     * 索引字段:不是
     */

    private String summarizerName;

    /**
     * 字段备注:记录人员文件名 <p>
     * 数据库字段长度:(128,0) <p>
     * 索引字段:不是
     */
    private byte[] summarizerFile;

    private String summarizerFileName;

    private String summarizerFileType;

    /**
     * 字段备注:记录人员文件类型 <p>
     * 数据库字段长度:(128,0) <p>
     * 索引字段:不是
     */
    private String recorder;

    /**
     * 字段备注:记录人员记录内容 <p>
     * 数据库字段长度:(2147483647,0) <p>
     * 索引字段:不是
     */
    private byte[] recorderFile;

    private String recorderFileName;

    private String recorderFileType;

    private Integer isSummarizerFile;

    private Integer isRecorderFile;

    private Integer baseScore;

    private Integer isBattle;

    /**
     * 字段备注:小于等于创建时间 <p>
     * 数据库字段长度:(19,0) <p>
     * 索引字段:不是
     */
    private Date modifyTime;

    public static CmsRecorderQuery singletonQuery() {
        CmsRecorderQuery query = new CmsRecorderQuery();
        query.setLimit(1);
        return query;
    }

    public static CmsRecorderQuery listQuery() {
        CmsRecorderQuery query = new CmsRecorderQuery();
        query.setLimit(MAX_ROWS);
        return query;
    }
}