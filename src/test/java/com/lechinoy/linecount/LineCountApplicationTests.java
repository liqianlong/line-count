package com.lechinoy.linecount;

import com.lechinoy.linecount.count.LineCount;
import com.lechinoy.linecount.count.LineCountCallable;
import com.lechinoy.linecount.file.FileList;
import com.lechinoy.linecount.language.JavaLanguage;
import com.lechinoy.linecount.language.Language;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class LineCountApplicationTests {

    @Test
    public void fileListTest() {

        String path = "/Users/liqianlong/Documents/ideawork/line-count/src/main/java/com/lechinoy/linecount/test";

        List<String> list = FileList.getFilePathList(new File(path), new ArrayList<>(), (name -> new JavaLanguage().isLanguageFile(name)));

        System.out.println(list);

    }

    @Test
    public void commentTest() throws ExecutionException, InterruptedException, IOException {

        String filePath = "/Users/liqianlong/Documents/ideawork/line-count/src/main/java/com/lechinoy/linecount/test/TestFile.java";
        Language language = new JavaLanguage();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(filePath)));

        long codeLine = 0L;
        long blankLine = 0L;
        long commentLine = 0L;

        String line;
        //多行注释符标识符号
        boolean multiComment = false;

        while ((line = bufferedReader.readLine()) != null) {
            line = line.trim();
            //是否是多行注释 单行使用
            boolean oneLineComment = oneLineComment(line, language.getStartComment(), language.getEndComment());
            // 注释结束
            boolean endLineComment = language.getEndComment().stream().anyMatch(line::startsWith);
            //注释开始 和结束 行
            boolean bothLineComment = language.getMultiComment().stream().anyMatch(line::startsWith);

            //单行注释
            boolean singleComment = language.getSingleComment().stream().anyMatch(line::startsWith);


            if (language.isBlankLine(line) && !multiComment) {
                blankLine++;
            } else if (singleComment || oneLineComment || (multiComment && !endLineComment)) {
                commentLine++;
            } else if (bothLineComment) {
                multiComment = !multiComment;
                commentLine++;
            } else {
                codeLine++;
            }
        }

        System.out.println("code---->" + codeLine);
        System.out.println("blank--->" + blankLine);
        System.out.println("comment---->" + commentLine);


    }

    private boolean oneLineComment(String line, List<String> startComment, List<String> endComment) {

        boolean oneLineComment = false;
        for (int i = 0; i < startComment.size(); i++) {
            oneLineComment = line.startsWith(startComment.get(i)) && line.endsWith(endComment.get(i)) && line.length() > startComment.get(0).length();
            break;
        }
        return oneLineComment;
    }

    @Test
    public void callableTest() {

        ExecutorService executor = new ThreadPoolExecutor(10, 100, 60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100), new ThreadPoolExecutor.CallerRunsPolicy());

        String path = "/Users/liqianlong/Documents/ideawork/line-count/src/main/java/com/lechinoy/linecount/test";

        final LineCount[] lineCount = {new LineCount()};

        List<String> filePathList = FileList.getFilePathList(new File(path), new ArrayList<>(), (name -> new JavaLanguage().isLanguageFile(name)));

        filePathList.stream()
                .map(filePath -> executor.submit(new LineCountCallable(filePath, new JavaLanguage())))
                .forEach(lineCountFuture -> {
                    LineCount result = new LineCount();
                    try {
                        result = lineCountFuture.get(60, TimeUnit.SECONDS);
                    } catch (InterruptedException | ExecutionException | TimeoutException e) {
                        e.printStackTrace();
                    }

                    lineCount[0] = lineCount[0].add(result);
                });

        System.out.println(lineCount[0]);

    }


}
