package com.svwh.autos.checkhandler;

import com.intellij.psi.JavaTokenType;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiJavaToken;
import com.intellij.psi.util.PsiTreeUtil;

/**
 * 是否显示默认的提示
 * @description
 * @Author cxk
 * @Date 2024/5/17 21:23
 */
public class SkipDefaultHandler implements AutoCheckHandler{

    /**
     * 这个方法有三个返回值：
     * 0 ：说明这个交给idea判断是否应该具有代码提示框
     * 1: 说明这个符合规定，必须显示代码提示框
     * -1：说明这个不符合规定，不能显示代码提示框
     * @param psiElement
     * @return
     */
    @Override
    public int check(PsiElement psiElement) {
        // 判断当前是否是Java
        if (!(psiElement instanceof PsiJavaToken)) {
            return 0;
        }

        // 判断是否是字符串中
        if (!((PsiJavaToken) psiElement).getTokenType().equals(JavaTokenType.STRING_LITERAL)) {
            return 0;
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
