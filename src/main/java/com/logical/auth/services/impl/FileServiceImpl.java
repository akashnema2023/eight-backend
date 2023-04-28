package com.logical.auth.services.impl;
import com.logical.auth.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;
@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFile(String path, MultipartFile multipartFile) throws IOException {
        String name=multipartFile.getOriginalFilename();
        String randomId= UUID.randomUUID().toString();
        String fileName=randomId.concat(name.substring((name.lastIndexOf("."))));
        String filePath=path+ File.separator+fileName;
        File f=new File(path);
        if(!f.exists()){
            f.mkdir();
        }

        Files.copy(multipartFile.getInputStream(), Paths.get(filePath));
        return fileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullpath=path+File.separator+fileName;
        InputStream ios=new FileInputStream(fullpath);
        return ios;
    }

}
