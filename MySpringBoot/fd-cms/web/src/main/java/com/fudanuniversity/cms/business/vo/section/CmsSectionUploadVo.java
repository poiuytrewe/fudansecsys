package com.fudanuniversity.cms.business.vo.section;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@NoArgsConstructor
@ToString
public class CmsSectionUploadVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id; //接收的文件Id

    private MultipartFile sectionFile;
}
