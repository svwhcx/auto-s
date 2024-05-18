package com.svwh.autos.startup;

import com.intellij.openapi.editor.actionSystem.TypedAction;
import com.intellij.openapi.editor.actionSystem.TypedActionHandler;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.svwh.autos.typehandler.CharTypeActionHandler;
import org.jetbrains.annotations.NotNull;

/**
 * @description
 * @Author cxk
 * @Date 2024/5/18 11:36
 */
public class StartUpActivity implements StartupActivity, DumbAware {
    @Override
    public void runActivity(@NotNull Project project) {
        TypedAction instance = TypedAction.getInstance();
        TypedActionHandler originalHandler = instance.getRawHandler();
        instance.setupRawHandler(new CharTypeActionHandler(originalHandler));
    }
}
