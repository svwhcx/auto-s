package com.svwh.autos.contributor;

import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.svwh.autos.factory.LookUpElementFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 匹配.时候的自动补全
 * @description
 * @Author cxk
 * @Date 2024/5/18 0:47
 */
public class FieldAutoInfoMatch implements AutoInfoMatch{
    @Override
    public List<LookupElement> getCompletionVariants(PsiElement psiElement, String text) {
        text = text.substring(1);
        if (!text.endsWith(".")){
            return null;
        }
        // 递归查找匹配每一个，看是否有符合的，这里只是匹配名称，较为简单

        // 1. 先获取所有的参数
        PsiMethod psiMethod = PsiTreeUtil.getParentOfType(psiElement, PsiMethod.class);
        if (psiMethod == null) return List.of();
        PsiParameter[] parameters = psiMethod.getParameterList().getParameters();
        if (parameters.length == 0) return List.of();
        String[] strings = text.split("\\.");
        List<LookupElement> lookupElements = new ArrayList<>();
        // 这里匹配时应该一级一级地进行匹配从0,1,2一次匹配
        dfsQuery(parameters,strings,lookupElements);
        return lookupElements;
    }

    /**
     * 递归解析参数
     * @param parameters
     * @param text
     * @param lookupElements
     */
    private void dfsQuery(PsiParameter[] parameters, String[] strings, List<LookupElement> lookupElements){
        // 如果length为0则说明是第一个
        for (PsiParameter parameter : parameters) {
            String name = parameter.getName();
            // 已经匹配到第一个，那么就根据这第一个一次匹配
            if (name.equals(strings[0])){
               // 相等的情况，说明只有一个参数，就用这个一直递归下去
                dfsQuery(parameter,1,strings,lookupElements);
                break;
            }
        }
        // 如果一个都没有匹配则说明没有匹配到
    }

    private void dfsQuery(PsiParameter parameters,int level,String[] strings,List<LookupElement> lookupElements){
        // 判断是否是一个类
        // 判断后面还有没有属性值
        if (parameters.getType() instanceof PsiClassType){
            if (level == strings.length){
                // 获取所有的属性
                PsiClass psiClass = ((PsiClassType) parameters.getType()).resolve();
                if (psiClass != null){
                    PsiField[] fields = psiClass.getFields();
                    for (PsiField field : fields) {
                        lookupElements.add(LookUpElementFactory.createLookupElement(field));
                    }
                }
            }else{
                // 遍历parameter的fields
                // 只有名称相同的才能继续匹配
                PsiClass psiClass = ((PsiClassType) parameters.getType()).resolve();
                if (psiClass != null){
                    PsiField[] fields = psiClass.getFields();
                    for (PsiField field : fields) {
                        if (field.getName().equals(strings[level-1])) {
                            dfsQuery(parameters,level+1,strings,lookupElements);
                            break;
                        }
                    }
                }
            }
        }
    }
}
