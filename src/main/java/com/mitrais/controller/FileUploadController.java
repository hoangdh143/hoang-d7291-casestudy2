package com.mitrais.controller;

import com.mitrais.exception.DataSourceException;
import com.mitrais.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/file_upload")
public class FileUploadController {

    private static final String TEMP_DIR = "tmp";
    private AccountService accountService;

    @Autowired
    public FileUploadController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public String uploadPage(ModelMap modelMap) {
        return "FileUpload";
    }

    @PostMapping
    public String submit(@RequestParam("file") MultipartFile file, ModelMap modelMap) {
        try {
            String filePath = write(file, "txt");
            accountService.importFromFile(filePath);

        } catch (IOException | DataSourceException e) {
            modelMap.addAttribute("error", e.getMessage());
            return "file-upload";
        }
        modelMap.addAttribute("file", file);
        modelMap.addAttribute("success", true);
        return "file-upload";
    }

    private String write(MultipartFile file, String fileType) throws IOException {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss-"));
        String fileName = date + file.getOriginalFilename();

        String folderPath = TEMP_DIR;
        String filePath = folderPath + "/" + fileName;

        Files.copy(file.getInputStream(), Paths.get(filePath).toAbsolutePath(), StandardCopyOption.REPLACE_EXISTING);
        return filePath;
    }
}
