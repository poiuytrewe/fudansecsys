package com.fudanuniversity.cms.business.vo.seminar;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
@Data
@NoArgsConstructor

@ToString
public class CmsSeminarUploadVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private MultipartFile file;

}
