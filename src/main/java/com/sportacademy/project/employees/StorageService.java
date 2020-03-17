package com.sportacademy.project.employees;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class StorageService {

  public Path storeFile(MultipartFile file, String id) {
    String dir = System.getProperty("user.dir");
    Path destPath = Paths.get(dir).resolve("files").resolve(id);
    createDirectory(destPath);
    try {
      return multipartFileToFile(file, destPath);
    } catch (IOException e) {
      log.warn("Can not copy file", e);
      return null;
    }
  }

  public Path multipartFileToFile(
      MultipartFile multipart,
      Path dir
  ) throws IOException {
    Path filepath = Paths.get(dir.toString(), multipart.getOriginalFilename());
    multipart.transferTo(filepath);
    return filepath;
  }

  public void createDirectory(Path dest) {
    try {
      Files.createDirectories(dest);
    } catch (IOException e) {
      log.error("Can not create directory", e);
    }
  }

  public void removeFile(String filePath) {
    FileUtils.deleteQuietly(new File(filePath));
  }
}
