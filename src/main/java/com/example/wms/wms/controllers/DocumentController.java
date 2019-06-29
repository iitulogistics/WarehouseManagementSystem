package com.example.wms.wms.controllers;

import com.example.wms.wms.doc.Excel;
import com.example.wms.wms.entities.ProductEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.util.IOUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "Документы", description = "Контроль документов")
public class DocumentController {

    @ApiOperation("Документация к поступающему товару")
    @PostMapping("docExpectedProduct")
    public ResponseEntity<?> docExpectedProduct(@RequestParam MultipartFile file) throws IOException {
        StringBuilder info = new StringBuilder();
        for (ProductEntity product : Excel.readFromExcelFile(file)) {
            info.append(product.toString()).append("\n\n");
        }
        return ResponseEntity.ok(info.toString());
    }

    @ApiOperation("Сгенерировать документацию к отгружаемому товару")
    @PostMapping("docShippingProduct")
    public HttpEntity<byte[]> docShippingProduct(@RequestParam String company_name) throws IOException {
        List<ProductEntity> list = new ArrayList<>();


        File file = Excel.createExelFile(list);

        FileInputStream input = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile("file",
                file.getName(), "text/plain", IOUtils.toByteArray(input));

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.MULTIPART_FORM_DATA);
        header.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + multipartFile.getOriginalFilename());
        header.setContentLength(multipartFile.getSize());

        return new HttpEntity<byte[]>(multipartFile.getBytes(),
                header);

//        return new FileSystemResource();
    }
}
