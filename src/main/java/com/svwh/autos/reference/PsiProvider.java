package com.svwh.autos.reference;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiReferenceProvider;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

/**
 * @description
 * @Author cxk
 * @Date 2024/5/17 15:30
 */
public class PsiProvider extends PsiReferenceProvider {
    @Override
    public PsiReference @NotNull [] getReferencesByElement(@NotNull PsiElement element, @NotNull ProcessingContext context) {
        if (element instanceof PsiLiteralExpression){
            // 检查字符串是否包含 #
            String text = element.getText();
            System.out.println("text = " + text);
            if (text.contains("#")){
                System.out.println("element: " + element);
                return new PsiReference[]{new MyPsiReference(element)};
            }
        }
        return  PsiReference.EMPTY_ARRAY;
    }
}
