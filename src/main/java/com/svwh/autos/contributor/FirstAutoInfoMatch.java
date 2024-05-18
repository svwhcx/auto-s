package com.svwh.autos.contributor;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.icons.AllIcons;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiParameter;
import com.intellij.psi.util.PsiTreeUtil;
import com.svwh.autos.checkhandler.AutoTInsertHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * 对于'#'匹配的处理
 * @description
 * @Author cxk
 * @Date 2024/5/18 0:41
 */
public class FirstAutoInfoMatch implements AutoInfoMatch{
    @Override
    public List<LookupElement> getCompletionVariants(PsiElement psiElement,String text) {
        // 这里的text是处理好后的text
        if (text.startsWith("#") && text.length() == 1){
            PsiMethod psiMethod = PsiTreeUtil.getParentOfType(psiElement, PsiMethod.class);
            if (psiMethod == null){
                return List.of();
            }
            PsiParameter[] parameters = psiMethod.getParameterList().getParameters();
            List<LookupElement> result = new ArrayList<>();
            for (PsiParameter parameter : parameters) {
                result.add(LookupElementBuilder
                        .create(parameter, parameter.getName())
                        .withTypeText(parameter.getType().getPresentableText())
                        .withInsertHandler(new AutoTInsertHandler(null))
                        .withIcon(AllIcons.Nodes.Field));
            }
            return result;
        }
        return List.of();
    }
}
