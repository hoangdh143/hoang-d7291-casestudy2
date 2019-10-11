package com.mitrais.controller;

import com.mitrais.config.DataSource;
import com.mitrais.config.FileDataSource;
import com.mitrais.exception.DataSourceException;
import com.mitrais.model.Account;
import com.mitrais.repository.AccountRepository;
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
    private AccountRepository accountRepository;

    @Autowired
    public FileUploadController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping
    public String uploadPage(ModelMap modelMap) {
        return "FileUpload";
    }

    @PostMapping
    public String submit(@RequestParam("file") MultipartFile file, ModelMap modelMap) {
        try {
            String filePath = write(file, "txt");
            System.out.println(filePath);
            DataSource<Account> dataSource = new FileDataSource(filePath);
            dataSource.saveToRepo(accountRepository);
        } catch (IOException | DataSourceException e) {
            modelMap.addAttribute("error", e.getMessage());
            return "FileUpload";
        }
        modelMap.addAttribute("file", file);
        return "Login";
    }

    private String write(MultipartFile file, String fileType) throws IOException {
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss-"));
        String fileName = date + file.getOriginalFilename();

        // folderPath here is /sismed/temp/exames
        String folderPath = TEMP_DIR;
        String filePath = folderPath + "/" + fileName;

        // Copies Spring's multipartfile inputStream to /sismed/temp/exames (absolute path)
        Files.copy(file.getInputStream(), Paths.get(filePath).toAbsolutePath(), StandardCopyOption.REPLACE_EXISTING);
        return filePath;
    }
}
