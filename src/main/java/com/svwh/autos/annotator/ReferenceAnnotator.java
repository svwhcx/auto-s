package com.svwh.autos.annotator;

import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.psi.JavaTokenType;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiJavaToken;
import com.intellij.psi.PsiReference;
import com.svwh.autos.checkhandler.AutoCheckHandler;
import com.svwh.autos.checkhandler.AutoMatchHandler;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * @description
 * @Author cxk
 * @Date 2024/5/17 23:26
 */
public class ReferenceAnnotator implements Annotator {

    private final AutoCheckHandler autoCheckHandler = new AutoMatchHandler();

    private static final TextAttributesKey SUCCESSFUL_REFERENCE = TextAttributesKey.createTextAttributesKey(
            "SUCCESSFUL_REFERENCE",
            new TextAttributes(new Color(255, 215, 0), null, null, null, Font.PLAIN)
    );

    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        if (autoCheckHandler.check(element) < 0) {
            return;
        }

        if (element instanceof PsiJavaToken && ((PsiJavaToken) element).getTokenType() == JavaTokenType.STRING_LITERAL) {
            PsiElement parent = element.getParent();
            for (PsiReference reference : parent.getReferences()) {
                if (reference.resolve() != null) {
                    holder.newAnnotation(HighlightSeverity.INFORMATION, "Click this to field!")
                            .textAttributes(SUCCESSFUL_REFERENCE)
                            .create();
                    return;
                }
            }
            // 没有一个引用匹配上
            holder.newAnnotation(HighlightSeverity.WARNING, "Unresolved reference!")
                    .textAttributes(DefaultLanguageHighlighterColors.INVALID_STRING_ESCAPE)
                    .create();

        }

    }
}
