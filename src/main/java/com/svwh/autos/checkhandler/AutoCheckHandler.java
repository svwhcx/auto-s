package com.svwh.autos.checkhandler;

import com.intellij.psi.PsiElement;

/**
 * @description
 * @Author cxk
 * @Date 2024/5/17 21:22
 */
public interface AutoCheckHandler {

    /**
     * 校验
     * @param psiElement
     * @return
     */
    int check(PsiElement psiElement);

}
