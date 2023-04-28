package com.logical.auth.services.file;

import org.springframework.web.multipart.MultipartFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
//@Service
public interface FileService {
    public String uploadFile(String path, MultipartFile multipartFile)throws IOException;
    public InputStream getResource(String path,String fileName)throws FileNotFoundException;
}
