package com.lechinoy.linecount.language;

import java.util.Arrays;
import java.util.List;

/**
 * Created by liqianlong
 * 2019 2019/5/22 20:13
 */
public class JavaLanguage extends Language{


    @Override
    public List<String> getFileSuffixName() {
        return Arrays.asList(".java");
    }

    @Override
    public List<String> getStartComment() {
        return Arrays.asList("/*");
    }

    @Override
    public List<String> getEndComment() {
        return Arrays.asList("*/");
    }

    @Override
    public List<String> getSingleComment() {
        return Arrays.asList("//");
    }

}
