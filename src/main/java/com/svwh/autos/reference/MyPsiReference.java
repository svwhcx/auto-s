package com.svwh.autos.reference;

import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.svwh.autos.checkhandler.AutoCheckHandler;
import com.svwh.autos.checkhandler.ReferenceCheckHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * @description
 * @Author cxk
 * @Date 2024/5/17 15:32
 */
public class MyPsiReference extends PsiReferenceBase<PsiElement> implements PsiPolyVariantReference {


    private AutoCheckHandler autoCheckHandler;

    public MyPsiReference(@NotNull PsiElement element) {
        super(element);
        autoCheckHandler = new ReferenceCheckHandler();
    }

    @Override
    public ResolveResult @NotNull [] multiResolve(boolean incompleteCode) {
        return new ResolveResult[0];
    }

    @Override
    public @Nullable PsiElement resolve() {
        // 字符有效性的判断
        PsiElement element = getElement();

        // 不符合条件的不解析
        if (this.autoCheckHandler.check(element) < 0){
            return null;
        }
        String key = getElement().getText();
        String substring = key.substring(2, key.length() - 1);
        String[] nameTree = substring.split("\\.");
        if (nameTree.length == 0) return null;
        // 获取该方法的参数

        PsiParameter[] parameters = Objects.requireNonNull(PsiTreeUtil.getParentOfType(getElement(), PsiMethod.class)).getParameterList().getParameters();
        if (parameters.length == 0) return null;
        return dfsResolve(nameTree,parameters);
    }

    /**
     * 递归深度解析一个合格的字符串
     * @param nameTree 已经经过分割的字符串数组，例如[user.username]进行分割
     * @param parameters
     * @return
     */
    private PsiElement dfsResolve(String[] nameTree,PsiParameter[] parameters){
        PsiParameter firstElement = null;
        for (PsiParameter parameter : parameters) {
            if (parameter.getName().equals(nameTree[0])) {
                firstElement =  parameter;
            }
        }
        // 没有解析到任何一个合格的字段属性定义
        if (firstElement == null) return null;
        if (nameTree.length == 1) return firstElement;
        // 现在应该使用这个firstElement;
        return dfsResolvePsi(1,nameTree,firstElement);
    }
    private PsiElement dfsResolvePsi(int level,String[] splits,PsiParameter psiParameter){
        if (psiParameter.getType() instanceof PsiClassType){
            PsiClass psiClass = ((PsiClassType) psiParameter.getType()).resolve();
            if (psiClass == null){
                return null;
            }
            for (PsiField field : psiClass.getFields()) {
                if (field.getName().equals(splits[level])) {
                    // 如果是最后一个，则直接结束
                    if (level == splits.length-1) return field;
                    // 如果字段名匹配，则继续向下匹配
                    if (field instanceof PsiParameter){
                        return dfsResolvePsi(level+1,splits,(PsiParameter) field);
                    }

                }
            }
        }
        return null;
    }
}
