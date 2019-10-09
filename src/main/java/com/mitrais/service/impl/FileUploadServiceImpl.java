package com.mitrais.service.impl;

import com.mitrais.service.FileUploadService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FileUploadServiceImpl implements FileUploadService {
    @Override
    public Boolean validateAccounts(MultipartFile multipartFile) throws IOException {

        return null;
    }

    @Override
    public void saveAccountsToAccountRepo(MultipartFile multipartFile) {

    }
}
