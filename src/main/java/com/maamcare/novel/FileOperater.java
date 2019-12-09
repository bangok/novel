package com.maamcare.novel;

import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

/**
 * 写文件的工具
 * t : 写入文件的内容
 * 注意：修改path
 * */
@Component
public class FileOperater {

    public Boolean writefileinfo(String t,String fileUrl) {
        FileSystemResource resource = new FileSystemResource(fileUrl);
        try {
            //后面参数为true为追加
            FileWriter fileWriter = (new FileWriter(resource.getFile(),true));
            fileWriter.write(t);
            fileWriter.close();
        } catch (IOException e) {
            //todo loginfo
            return false;
        }
        return true;
    }
}

