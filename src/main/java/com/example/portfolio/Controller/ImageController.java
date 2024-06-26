package com.example.portfolio.Controller;

import com.example.portfolio.Domain.Project;
import com.example.portfolio.Domain.ProjectImg;
import com.example.portfolio.Domain.UserImg;
import com.example.portfolio.Exception.Global.HTTP_INTERNAL_SERVER_ERROR;
import com.example.portfolio.Service.UploadService;
import com.example.portfolio.response.Image.UploadImageResponse;
import com.example.portfolio.response.SuccessResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/image")
@Tag(name = "이미지 API", description = "이미지 업로드 API입니다")
public class ImageController {


    SuccessResponse successResponse = new SuccessResponse();

    @Autowired
    UploadService uploadService;

    @Operation(summary = "프로젝트 이미지 업로드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = UploadImageResponse.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @PostMapping("/project/upload")
    public ResponseEntity<?> uploadProjectImage(@RequestParam("images") MultipartFile multipartFile) throws IOException {
        ProjectImg projectImg = uploadService.projectUpload(multipartFile);
        UploadImageResponse uploadImageResponse = new UploadImageResponse(projectImg);
        return ResponseEntity.ok(uploadImageResponse);
    }

    @Operation(summary = "유저 이미지 업로드")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공",
                    content = {@Content(schema = @Schema(implementation = UploadImageResponse.class))}),
            @ApiResponse(responseCode = "500", description = "서버 에러",
                    content = {@Content(schema = @Schema(implementation = HTTP_INTERNAL_SERVER_ERROR.class))}),
    })
    @PostMapping("/user/upload")
    public ResponseEntity<?> uploadUserImage(@RequestParam("images") MultipartFile multipartFile) throws IOException {
        UserImg userImg = uploadService.userUpload(multipartFile);
        UploadImageResponse uploadImageResponse = new UploadImageResponse(userImg);
        return ResponseEntity.ok(uploadImageResponse);
    }
}
