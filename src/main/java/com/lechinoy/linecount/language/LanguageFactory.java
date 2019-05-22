package com.lechinoy.linecount.language;

import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by liqianlong
 * 2019 2019/5/22 22:55
 */
public class LanguageFactory {


    public static List<String> languageSet = Arrays.asList("java","c","python");

    public static Language getLanguageConfig(String name){

        if (StringUtils.isEmpty(name)){
            return new JavaLanguage();
        }
        if (name.equals("c")){
            return new CLanguage();
        }else if (name.equals("java")){
            return new JavaLanguage();
        }else if (name.equals("python")){
            return new PythonLanguage();
        }else {
            throw new RuntimeException("not support this language");
        }

    }



}
