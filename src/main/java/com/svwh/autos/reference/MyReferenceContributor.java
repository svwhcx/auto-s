package com.svwh.autos.reference;

import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.PsiLiteralExpression;
import com.intellij.psi.PsiReferenceRegistrar;
import org.jetbrains.annotations.NotNull;

/**
 * @description
 * @Author cxk
 * @Date 2024/5/17 15:34
 */
public class MyReferenceContributor extends com.intellij.psi.PsiReferenceContributor{
    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {

        registrar.registerReferenceProvider(PlatformPatterns.psiElement(), new PsiProvider());

    }
}
