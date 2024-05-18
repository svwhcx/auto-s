package com.svwh.autos.checkhandler;

import com.intellij.psi.JavaTokenType;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiJavaToken;
import com.intellij.psi.util.PsiTreeUtil;

/**
 * 在进行自动提示的过滤中，判断哪些提示符合注解的提示条件的
 * @description
 * @Author cxk
 * @Date 2024/5/17 21:35
 */
public class AutoMatchHandler implements AutoCheckHandler{


    /**
     *
     * @param psiElement
     * @return -1: 说明不合格，1：说明合格
     */
    @Override
    public int check(PsiElement psiElement) {
        if (!(psiElement instanceof PsiJavaToken)) {
            return -1;
        }
        // 判断是否是字符串中
        if (!((PsiJavaToken) psiElement).getTokenType().equals(JavaTokenType.STRING_LITERAL)) {
            return -1;
        }
        // 判断是否是注解中
        PsiAnnotation psiAnnotation = PsiTreeUtil.getParentOfType(psiElement, PsiAnnotation.class);
        if (psiAnnotation == null) {
            return -1;
        }
        // 判断字符串中的注解是否包含#
        if (!psiElement.getText().contains("#")) {
            return -1;
        }
        return 1;
    }
}
