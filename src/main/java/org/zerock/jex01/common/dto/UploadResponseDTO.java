package org.zerock.jex01.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadResponseDTO {

    private String uuid;
    private String fileName;
    private boolean image;
    private String uploadPath;

    public String getThumbnail() { //썸네일 주소를 리턴
        return uploadPath + "/s_" + uuid + "_" + fileName;
    }

    public String getFileLink() { //원본 파일 주소를 리턴 //
        // 사실 특별한 장치
        return uploadPath + "/" + uuid + "_" + fileName;
    }

}
