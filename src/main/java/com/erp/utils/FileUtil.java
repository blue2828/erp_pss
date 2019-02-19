package com.erp.utils;

import java.io.File;
import java.util.UUID;

public class FileUtil {
    public static String mkFileName (String fileName) {
        String type = fileName.substring(fileName.lastIndexOf("."));
        fileName = fileName.substring(fileName.lastIndexOf("_") + 1);
        fileName = fileName.substring(0, fileName.lastIndexOf("."));
        return fileName = fileName.concat("_").concat(UUID.randomUUID().toString().replace("-", "_")).concat(type);
    }
    public static void checkDirOrMk (String storeFilePath) {
        File file = new File(storeFilePath);
        if (!file.exists() || file == null)
            file.mkdir();
    }
}
