# Simple-SPEL-Completion


## 待办
- [x] 注解中简单符号#的自动提示
- [ ] 配置开关功能，可在idea中设置是否开启提示
<!-- Plugin description -->
## 介绍
IDEA的自动提示不会对方法的注解中的字符串中的参数进行自动提示，本插件完成一个简单的SPEL表达式的用于注解的
参数提示。

## 注意
本插件只使用于注解上面的字符的参数提示，目前只支持简单的"#"字符的SPEL表达式，具体格式如下：
```java
@Annotation(value = {"#test,#user.username"})
public void test(String test,User user){
    
}
 ```
<!-- Plugin description end -->

## Installation

- Using the IDE built-in plugin system:

  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>Marketplace</kbd> > <kbd>Search for "Simple-SPEL-Completion"</kbd> >
  <kbd>Install</kbd>

- Manually:
  目前只支持手动下载安装
---
