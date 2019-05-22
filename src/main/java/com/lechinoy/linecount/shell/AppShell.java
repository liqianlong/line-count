package com.lechinoy.linecount.shell;

import com.alibaba.fastjson.JSON;
import com.lechinoy.linecount.count.CountService;
import com.lechinoy.linecount.count.LineCount;
import com.lechinoy.linecount.language.LanguageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.util.StringUtils;

/**
 * Created by liqianlong
 * 2019 2019/5/22 22:53
 */
@ShellComponent
public class AppShell {


    @Autowired
    private CountService countService;

    @ShellMethod("count")
    public String count(@ShellOption("-lan") String language, @ShellOption("-fp") String filePath) {

        if (!LanguageFactory.languageSet.contains(language)) {
            return "not support this language";
        }

        if (StringUtils.isEmpty(filePath)) {
            return "input filePath";
        }

        try {
            LineCount lineCount = countService.getLineCount(language, filePath);
            return JSON.toJSONString(lineCount);
        } catch (Exception e) {
            return e.getMessage();
        }

    }


}
