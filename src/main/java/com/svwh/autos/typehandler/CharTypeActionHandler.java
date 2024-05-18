package com.svwh.autos.typehandler;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupManager;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.svwh.autos.contributor.AutoInfoMatch;
import com.svwh.autos.contributor.FieldAutoInfoMatch;
import com.svwh.autos.contributor.FirstAutoInfoMatch;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2024/5/18 11:29
 */
public class CharTypeActionHandler implements TypedActionHandler {

    private  final TypedActionHandler originalTypedHandler;

    private final AutoInfoMatch autoInfoMatch;

    private final AutoInfoMatch fieldAutoInfoMatch;

    public CharTypeActionHandler(TypedActionHandler originalTypedHandler) {
        this.originalTypedHandler = originalTypedHandler;
        this.autoInfoMatch = new FirstAutoInfoMatch();
        this.fieldAutoInfoMatch = new FieldAutoInfoMatch();
    }

    @Override
    public void execute(@NotNull Editor editor, char charTyped, @NotNull DataContext dataContext) {
        this.originalTypedHandler.execute(editor, charTyped, dataContext);
        // 检测当前的字符环境
        if (charTyped == '#' || charTyped == '.'){
            // 手动触发代码补全
            // 首先要获取匹配的补全项
            Project project = editor.getProject();
            if (project == null) return;
            PsiFile psiFile = PsiDocumentManager.getInstance(project).getPsiFile(editor.getDocument());
            if (psiFile == null) return;
            int offset = editor.getCaretModel().getOffset();
            PsiElement element = psiFile.findElementAt(offset);
            if (element == null){
                return;
            }
            if (PsiTreeUtil.getParentOfType(element, PsiAnnotation.class) == null) return;
            // 目前是可以同步的
            // 强制同步文档和psi树
            PsiDocumentManager.getInstance(project).commitDocument(editor.getDocument());
            psiFile = PsiDocumentManager.getInstance(project).getPsiFile(editor.getDocument());
            if (psiFile == null) return;
            element = psiFile.findElementAt(offset);
            if (element == null) return;
            String text = element.getText().substring(1, element.getTextLength() - 1);
            List<LookupElement> lookupElements = autoInfoMatch.getCompletionVariants(element, text);
            if (lookupElements.isEmpty()){
                lookupElements = fieldAutoInfoMatch.getCompletionVariants(element, text);
            }
            if (!lookupElements.isEmpty()) {
                // 触发自动提示
                LookupManager.getInstance(project).showLookup(editor, lookupElements.toArray(new LookupElement[0]));
            }
        }
    }

}
