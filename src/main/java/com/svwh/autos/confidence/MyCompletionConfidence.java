package com.svwh.autos.confidence;

import com.intellij.codeInsight.completion.CompletionConfidence;
import com.intellij.psi.*;


import com.intellij.util.ThreeState;
import com.svwh.autos.checkhandler.AutoCheckHandler;
import com.svwh.autos.checkhandler.SkipDefaultHandler;
import org.jetbrains.annotations.NotNull;

/**
 * @description
 * @Author cxk
 * @Date 2024/5/17 16:03
 */
public class MyCompletionConfidence extends CompletionConfidence {

    private AutoCheckHandler autoCheckHandler = null;

    public MyCompletionConfidence() {
        this.autoCheckHandler = new SkipDefaultHandler();
    }

    @Override
    public @NotNull ThreeState shouldSkipAutopopup(@NotNull PsiElement contextElement, @NotNull PsiFile psiFile, int offset) {
        int check = this.autoCheckHandler.check(contextElement);
        return switch (check) {
            case 0 -> ThreeState.UNSURE;
            case -1 -> ThreeState.YES;
            default -> ThreeState.NO;
        };
    }
}
