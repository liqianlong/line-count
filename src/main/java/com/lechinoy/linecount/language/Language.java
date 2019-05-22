package com.lechinoy.linecount.language;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liqianlong
 * 2019 2019/5/22 20:13
 */
public abstract class Language {

    /**
     * 获取语言 文件后缀名
     * @return
     */
    public abstract List<String> getFileSuffixName();

    /**
     * 判断是否是符合要求的文件
     * @param name
     * @return
     */
    public  boolean isLanguageFile(String name){

        return getFileSuffixName().stream().anyMatch(suffixName -> !StringUtils.isEmpty(name) && name.contains(suffixName));

    }

    /**
     * 是否是 空行
     * @param line
     * @return
     */
    public boolean isBlankLine(String line){

        return line!=null && StringUtils.isEmpty(line.trim());

    }

    /**
     * 多行注释 开始
     * @return
     */
    public abstract List<String> getStartComment();

    /**
     * 多行注释 结束
     * @return
     */
    public abstract List<String> getEndComment();

    /**
     * 单行注释
     * @return
     */
    public abstract List<String> getSingleComment();

    /**
     * 获取多行注释的标记
     * @return
     */
    public List<String> getMultiComment(){
        List<String> list = new ArrayList<>();
        list.addAll(getStartComment());
        list.addAll(getEndComment());
        return list;
    }



}
