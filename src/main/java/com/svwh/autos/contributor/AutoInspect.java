package com.svwh.autos.contributor;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;

import com.intellij.psi.*;

import com.intellij.psi.util.PsiTreeUtil;

import com.svwh.autos.checkhandler.AutoCheckHandler;
import com.svwh.autos.checkhandler.AutoMatchHandler;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import java.util.List;


/**
 * @description
 * @Author cxk
 * @Date 2024/5/16 21:32
 */
public class AutoInspect extends CompletionContributor {

    private final AutoCheckHandler autoCheckHandler;

    private final List<AutoInfoMatch> autoInfoMatches = new ArrayList<>();


    public AutoInspect() {
        this.autoInfoMatches.add(new AllAutoInfoMatch());
        this.autoCheckHandler = new AutoMatchHandler();
    }


    @Override
    public void fillCompletionVariants(@NotNull CompletionParameters parameters, @NotNull CompletionResultSet result) {
        PsiElement psiElement = parameters.getPosition();
        // 校验是否合格
        if (this.autoCheckHandler.check(psiElement) < 0){
            return;
        }
        // 新建一个空的结果集
        CompletionResultSet result1 = result.withPrefixMatcher(new PlainPrefixMatcher(""));
        // 关闭idea默认的自动提示
        result.stopHere();
        // 寻找注解上面的方法
        PsiMethod psiMethod = PsiTreeUtil.getParentOfType(psiElement, PsiMethod.class);
        if (psiMethod == null) {
            return;
        }
        if (psiMethod.getParameterList().isEmpty()) {
            return;
        }

        // 获取方法的所有参数
        PsiParameter[] methodParameters = psiMethod.getParameterList().getParameters();
        //递归解析匹配值

        // 这里应该是递归进行的。
        // 1. 对正在输入的字符串做正则分割
        String text = psiElement.getText()
                .replace("IntellijIdeaRulezzz ","")
                .replace("\\s","")
                .trim();
        String key = text.substring(1, text.length() - 1);

        for (AutoInfoMatch autoInfoMatch : this.autoInfoMatches) {
            List<LookupElement> lookupElements = autoInfoMatch.getCompletionVariants(psiElement,key);
            if (lookupElements != null && !lookupElements.isEmpty()){
                result1.addAllElements(lookupElements);
                break;
            }
        }
//        String substring = key.substring(2, key.length() - 1);
//        String[] split = substring.split("\\.");
//        System.out.println("split: " + split.length);
//        List<LookupElement> psiParameters = new ArrayList<>();
//        if (split.length == 1){
//            for (PsiParameter methodParameter : methodParameters) {
//                System.out.println("methodParameter: " + methodParameter.getName());
//                System.out.println("split: " + split[0]);
//                if (methodParameter.getName().startsWith(split[0])){
//                    // 已经找到了首项的,那么直接提示
//                    psiParameters.add(LookupElementBuilder
//                            .create(methodParameter, methodParameter.getName())
//                            .withTypeText(methodParameter.getType().getPresentableText())
//                            .withInsertHandler(new AutoTInsertHandler())
//                            .withIcon(AllIcons.Nodes.Field));
//                }
//            }
//        }
//        result1.addAllElements(psiParameters);
        super.fillCompletionVariants(parameters, result1);
    }


    private String getCurrentPrefix(@NotNull CompletionParameters parameters) {
        int offset = parameters.getOffset();
        String text = parameters.getEditor().getDocument().getText().substring(0, offset);
        int start = offset - 1;
        while (start >= 0 && isIdentifierPart(text.charAt(start))) {
            start--;
        }
        return text.substring(start + 1, offset);
    }

    private boolean isIdentifierPart(char c) {
        return Character.isLetterOrDigit(c) || c == '_';
    }



}

