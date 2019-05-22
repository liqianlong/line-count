package com.lechinoy.linecount.language;

import java.util.Arrays;
import java.util.List;

/**
 * Created by liqianlong
 * 2019 2019/5/22 20:25
 */
public class CLanguage extends Language {

    @Override
    public List<String> getFileSuffixName() {
        return Arrays.asList(".h",".c");
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
