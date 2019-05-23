package com.lechinoy.linecount.count;

import com.lechinoy.linecount.file.FileList;
import com.lechinoy.linecount.language.JavaLanguage;
import com.lechinoy.linecount.language.Language;
import com.lechinoy.linecount.language.LanguageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by liqianlong
 * 2019 2019/5/22 23:00
 */
@Service
public class CountService {

    @Autowired
    private ThreadPoolExecutor lineCountThreadPool;

    /**
     * @param language
     * @param path
     * @return
     */
    public LineCount getLineCount(String language, String path) {

        final LineCount[] lineCount = {new LineCount()};

        Language lan = LanguageFactory.getLanguageConfig(language);

        List<String> filePathList = FileList.getFilePathList(new File(path), new ArrayList<>(), (lan::isLanguageFile));

        filePathList.stream()
                .map(filePath -> lineCountThreadPool.submit(new LineCountCallable(filePath, lan)))
                .forEach(lineCountFuture -> {
                    LineCount result = new LineCount();
                    try {
                        result = lineCountFuture.get(60, TimeUnit.SECONDS);
                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        e.printStackTrace();
                    }

                    lineCount[0] = lineCount[0].add(result);
                });

        return lineCount[0];
    }


}
