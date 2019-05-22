package com.lechinoy.linecount.count;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by liqianlong
 * 2019 2019/5/22 20:26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LineCount {

    /**
     * 代码行
     */
    private long codeLine;

    /**
     * 空行
     */
    private long blankLine;

    /**
     * 注释行
     */
    private long commentLine;

    /**
     * 累加
     * @return
     */
    public LineCount add(LineCount lineCount){
        return new LineCount(this.codeLine+lineCount.getCodeLine(),this.blankLine+lineCount.getBlankLine(),this.commentLine+lineCount.getCommentLine());
    }


}
