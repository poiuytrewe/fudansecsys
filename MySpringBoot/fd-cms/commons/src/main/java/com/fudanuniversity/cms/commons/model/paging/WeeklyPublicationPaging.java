package com.fudanuniversity.cms.commons.model.paging;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@ToString
@NoArgsConstructor
@Data
public class WeeklyPublicationPaging<T> {
    private static final long serialVersionUID = 1L;

    private List<T> unDoneStudentList;

    private int unDoneCount;
}
