package com.mitrais.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface FileUploadService {
    Boolean validateAccounts(MultipartFile multipartFile) throws IOException;
    void saveAccountsToAccountRepo(MultipartFile multipartFile);
}
