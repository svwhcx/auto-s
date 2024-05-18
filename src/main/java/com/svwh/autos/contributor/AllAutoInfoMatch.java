package com.svwh.autos.contributor;


import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.svwh.autos.factory.LookUpElementFactory;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * 除了#和.的自动提示，还需要考虑特殊的自动提示,例如 user.user应该提示username
 *
 * @description
 * @Author cxk
 * @Date 2024/5/18 1:07
 */
public class AllAutoInfoMatch implements AutoInfoMatch{


    @Override
    public List<LookupElement> getCompletionVariants(PsiElement psiElement, String text) {
        if (text.endsWith(".") || text.endsWith("#") || !text.startsWith("#")){
            return List.of();
        }

        PsiMethod psiMethod = PsiTreeUtil.getParentOfType(psiElement, PsiMethod.class);
        if (psiMethod == null) return List.of();
        PsiParameter[] parameters = psiMethod.getParameterList().getParameters();
        if (parameters.length == 0) return List.of();
        // 现在开始进行全词匹配
        text = text.substring(1);
        String[] strings = text.split("\\.");
        List<LookupElement> lookupElements = new ArrayList<>();
        dfsQuery(parameters,strings,lookupElements);
        return lookupElements;
    }



    private void dfsQuery(PsiParameter[] psiParameters,String[] strings,List<LookupElement> lookupElements){
        // 如果是1则模糊匹配
        if (strings.length == 1){
            for (PsiParameter psiParameter : psiParameters) {
                if (psiParameter.getName().startsWith(strings[0])){
                    lookupElements.add(LookUpElementFactory.createLookupElement(psiParameter));
                }
            }
        }else {
            // 如果不是，则说明要递归了
            // 先找到第一个匹配的参数
            for (PsiParameter psiParameter : psiParameters) {
                if (psiParameter.getName().equals(strings[0])){
                    // 找到匹配的参数，则进行递归
                    dfsQuery(psiParameter,1,strings,lookupElements);
                    break;
                }
            }
        }
    }

    private void dfsQuery(PsiParameter psiParameter,int level,String[] strings,List<LookupElement> lookupElements){
        // 判断是否是一个类
        // 判断后面还有没有属性值
        if (psiParameter.getType() instanceof PsiClassType){
            if (level == strings.length - 1){
                // 获取所有的属性
                PsiClass psiClass = ((PsiClassType) psiParameter.getType()).resolve();
                if (psiClass != null){
                    PsiField[] fields = psiClass.getFields();
                    for (PsiField field : fields) {
                        if (field.getName().startsWith(strings[level])){
                            lookupElements.add(LookUpElementFactory.createLookupElement(field));
                        }
                    }
                }
            }else{
                // 遍历parameter的fields
                // 只有名称相同的才能继续匹配
                PsiClass psiClass = ((PsiClassType) psiParameter.getType()).resolve();
                if (psiClass != null){
                    PsiField[] fields = psiClass.getFields();
                    for (PsiField field : fields) {
                        if (field.getName().equals(strings[level-1])) {
                            dfsQuery(psiParameter,level+1,strings,lookupElements);
                            break;
                        }
                    }
                }
            }
        }
    }



}
