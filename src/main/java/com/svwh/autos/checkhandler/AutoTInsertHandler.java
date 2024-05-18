package com.svwh.autos.checkhandler;

import com.intellij.codeInsight.completion.InsertHandler;
import com.intellij.codeInsight.completion.InsertionContext;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import org.jetbrains.annotations.NotNull;

/**
 * 这里计算输入回车时，哪些字符应该被覆盖掉
 * @description
 * @Author cxk
 * @Date 2024/5/17 20:47
 */
public class AutoTInsertHandler implements InsertHandler<LookupElement> {

    private String prefix;

    public AutoTInsertHandler(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public void handleInsert(@NotNull InsertionContext context, @NotNull LookupElement item) {

        Editor editor1 = context.getEditor();
        Document document1 = editor1.getDocument();
        int startOffset = context.getStartOffset()-1;
        int tailOffset = context.getTailOffset();
        // 获取当前正在编辑的PSIElement
        // 删除原来的文本
        char c = document1.getText().charAt(startOffset);
        while (c != '#' && c != '.'){
            c = document1.getText().charAt(--startOffset);
        }
        startOffset++;
        document1.deleteString(startOffset, tailOffset);
        // 插入新的文本
        document1.insertString(startOffset, item.getLookupString());
        // 移动光标
        editor1.getCaretModel().moveToOffset(startOffset + item.getLookupString().length());

    }
}
