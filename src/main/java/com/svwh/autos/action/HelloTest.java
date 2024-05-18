package com.svwh.autos.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

/**
 * @description
 * @Author cxk
 * @Date 2024/5/16 21:19
 */
public class HelloTest extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
        Messages.showInfoMessage("Hello","你好这是测试");
    }
}
