package com.lechinoy.linecount.file;

import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by liqianlong
 * 2019 2019/5/22 19:50
 */
public class FileList {


    /**
     * 获取所有的文件绝对路径
     * @param file
     * @param filePathList
     * @return
     */
    public static List<String> getFilePathList(File file, List<String> filePathList, Predicate<String> predicate) {

        if (!file.exists()) {
            return new ArrayList<>();
        }
        if (file.isFile()){
            return Collections.singletonList(file.getAbsolutePath());
        }


        File[] files = file.listFiles();

        if (files != null && files.length > 0) {
            Arrays.stream(files).forEach(childFile -> {
                if (childFile.isFile() && predicate.test(childFile.getName())) {
                    filePathList.add(childFile.getAbsolutePath());
                } else if (childFile.isDirectory()) {
                    getFilePathList(childFile,filePathList,predicate);
                }
            });
        }

        return filePathList;
    }


}
