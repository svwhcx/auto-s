package com.svwh.autos.contributor;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.PsiElement;

import java.util.List;

/**
 * @description
 * @Author cxk
 * @Date 2024/5/18 0:35
 */
public interface AutoInfoMatch {

    /**
     * 获取匹配的补全提示
     * @param psiElement
     * @return
     */
    List<LookupElement> getCompletionVariants(PsiElement psiElement,String text);

}
