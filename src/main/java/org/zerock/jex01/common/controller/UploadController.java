package org.zerock.jex01.common.controller;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.zerock.jex01.common.dto.UploadResponseDTO;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@Log4j2
public class UploadController {

    @GetMapping("/sample/upload")
    public void uploadGet() {
        //내용없이 화면을 보이기 위해서 쓰이는 컨트롤러
    }

    @ResponseBody
    @PostMapping("/removeFile")
    public ResponseEntity<String> removeFile(@RequestBody Map<String, String> data) throws Exception {

        File file = new File("C:\\upload" + File.separator + data.get("fileName"));

        boolean checkImage = Files.probeContentType(file.toPath()).startsWith("image");

        file.delete();

        if (checkImage) {
            File thumbnail = new File(file.getParent(), "s_" + file.getName());
            log.info(thumbnail);
            thumbnail.delete();
        }
        return ResponseEntity.ok().body("deleted");
    }

    @GetMapping("/downFile")
    public ResponseEntity<byte[]> download(@RequestParam("file") String fileName) throws Exception {

        File file = new File("C:\\upload" + File.separator + fileName);

        String originalFileName = fileName.substring(fileName.indexOf("_") + 1);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/octet-stream");
        headers.add("Content-Disposition", "attachment; filename="
                + new String(originalFileName.getBytes(StandardCharsets.UTF_8), "ISO-8859-1"));
        byte[] data = FileCopyUtils.copyToByteArray(file);

        return ResponseEntity.ok().headers(headers).body(data);

    }

    //이미지 조회코드(다운로드)
    @GetMapping("/viewFile")
    @ResponseBody
    public ResponseEntity<byte[]> viewFile(@RequestParam("file") String fileName) throws Exception { //파라미터로 받을때는 file로 받지만 실제 변수는 fileName으로 받겠다

        // C:\\upload\\20201/09/08/kuki.jpg -> 경로 설정 위해 밑에서 작성
        File file = new File("C://upload" + File.separator + fileName);

        log.info(file);

        ResponseEntity<byte[]> result = null;

        byte[] data = FileCopyUtils.copyToByteArray(file);

        //mimetype -> probeContentType을 이용하여 파일 타입을 알아옴
        String mimeType = Files.probeContentType(file.toPath());

        log.info("mimeType : " + mimeType);

        //ok는 200응답
        result = ResponseEntity.ok().header("Content-Type", mimeType).body(data);

        return result;
    }

    @ResponseBody //obj를 json으로 자동변환해주는 어노테이션 -> 파라미터에도 사용이 가능함
    @PostMapping("/upload")
    public List<UploadResponseDTO> uploadPost(MultipartFile[] uploadFiles) { //formData에 넘겨준 키의 이름으로 파라미터를 받아야함

        log.info("----------------------------------");
        if (uploadFiles != null && uploadFiles.length > 0) {

            List<UploadResponseDTO> uploadedList = new ArrayList<>();

            for (MultipartFile multipartFile : uploadFiles) {
                try {
                    uploadedList.add(uploadProcess(multipartFile));

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }//for end
            return uploadedList;

        }//if end
        return null;
    }

    private UploadResponseDTO uploadProcess(MultipartFile multipartFile) throws Exception {
        String uploadPath = "C:\\upload";

        String folderName = makeFolder(uploadPath);
        String fileName = multipartFile.getOriginalFilename();
        String uuid = UUID.randomUUID().toString(); //uuid
        String originalFileName = fileName;

        fileName = uuid + "_" + fileName;

        //'path\folderName'로 이동하고 fileName으로 만듦
        File savedFile = new File(uploadPath + File.separator + folderName, fileName);


        FileCopyUtils.copy(multipartFile.getBytes(), savedFile); //바이트로 savedFile을 넘겨라 -> 파일 저장 코드

        //Thumbnail 처리
        String mimeType = multipartFile.getContentType(); //타입이 무엇인지,,
        boolean checkImage = mimeType.startsWith("image"); //이미지 여부(image로 시작되면 true)

        if (checkImage) {
            File thumbnailFile = new File(uploadPath + File.separator + folderName, "s_" + fileName);
            Thumbnailator.createThumbnail(savedFile, thumbnailFile, 100, 100);
        }

        return UploadResponseDTO.builder()
                .uuid(uuid)
                .uploadPath(folderName.replace(File.separator, "/")) //윈도우와 맥의 차이점을 통일하기 위해서 사용
                .fileName(originalFileName)
                .image(checkImage)
                .build();
    }


    private String makeFolder(String uploadPath) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String str = simpleDateFormat.format(date); //2021-09-07
        String folderName = str.replace("-", File.separator);
        File uploadFolder = new File(uploadPath, folderName);

        if (uploadFolder.exists() == false) {//폴더가 없다면
            uploadFolder.mkdirs(); //폴더를 새로 만들어라
        }

        return folderName;
    }

}