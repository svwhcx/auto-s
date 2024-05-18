package com.svwh.autos.factory;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.icons.AllIcons;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiParameter;
import com.svwh.autos.checkhandler.AutoTInsertHandler;

/**
 * @description
 * @Author cxk
 * @Date 2024/5/18 1:03
 */
public class LookUpElementFactory {

    public static String prefix = null;


    public static LookupElement createLookupElement(PsiParameter parameter){
        return LookupElementBuilder
                .create(parameter, parameter.getName())
                .withTypeText(parameter.getType().getPresentableText())
                .withInsertHandler(new AutoTInsertHandler(prefix))
                .withIcon(AllIcons.Nodes.Field);
    }

    public static LookupElement createLookupElement(PsiField psiField){
        return LookupElementBuilder
                .create(psiField, psiField.getName())
                .withTypeText(psiField.getType().getPresentableText())
                .withInsertHandler(new AutoTInsertHandler(prefix))
                .withIcon(AllIcons.Nodes.Field);
    }





}
