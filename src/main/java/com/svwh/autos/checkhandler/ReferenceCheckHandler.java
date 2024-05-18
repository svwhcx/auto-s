package com.svwh.autos.checkhandler;

import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;

/**
 * @description
 * @Author cxk
 * @Date 2024/5/17 23:22
 */
public class ReferenceCheckHandler implements AutoCheckHandler{
    @Override
    public int check(PsiElement psiElement) {
        if (!(psiElement instanceof PsiLiteralExpression)) {
            return -1;
        }
        // 判断是否是注解中
        PsiAnnotation psiAnnotation = PsiTreeUtil.getParentOfType(psiElement, PsiAnnotation.class);
        if (psiAnnotation == null) {
            return -1;
        }
        // 判断字符串中的注解中的字符串是否以#开头，并且只含有一个#。
        if (!psiElement.getText().startsWith("#") && psiElement.getText().chars().filter(c -> c == '#').count() > 1) {
            return -1;
        }
        return 1;
    }
}
