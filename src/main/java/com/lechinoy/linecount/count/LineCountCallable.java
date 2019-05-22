package com.lechinoy.linecount.count;

import com.lechinoy.linecount.language.Language;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by liqianlong
 * 2019 2019/5/22 20:30
 */
public class LineCountCallable implements Callable<LineCount> {

    private String filePath;

    private Language language;

    public LineCountCallable(String filePath, Language language) {
        this.filePath = filePath;
        this.language = language;
    }

    @Override
    public LineCount call() throws Exception {

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
            } else if (bothLineComment){
                multiComment = !multiComment;
                commentLine++;
            }else {
                codeLine++;
            }
        }


        return new LineCount(codeLine, blankLine, commentLine);
    }


    private boolean oneLineComment(String line, List<String> startComment, List<String> endComment) {

        boolean oneLineComment = false;
        for (int i = 0; i < startComment.size(); i++) {
            oneLineComment = line.startsWith(startComment.get(i)) && line.endsWith(endComment.get(i)) && line.length() > startComment.get(0).length();
            break;
        }
        return oneLineComment;
    }


}
