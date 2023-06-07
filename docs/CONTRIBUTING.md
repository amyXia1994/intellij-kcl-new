## 基本信息：
the group name: com.kusionstack.kcl
the artifact name: intellij-kcl (=rootProject.name in settings.gradle.kts) （最好改名为 kcl，这样在各种命名的地方不会违反规范，比如在 src/resources/META-INF/plugin.xml 里的 name 属性，不能带 intellij 这个词）（不过项目名字可以和插件名字不同，这个没关系）





## 操作手册

### 环境要求

- 需要安装 intellij IDEA
- 需要安装 jdk11

### 构建、运行、测试
- 构建：./gradlew build 构建完毕后显示 BUILD SUCCESSFUL，并且在 build/distributions 目录下产出名为 intellij-kcl-<version>.zip 的发布包
- 本地运行插件，进行debug：./gradlew runIDE（或：在gradle工具栏选择 Run Plugin）
- 测试：./gradlew test （或在 gradle 工具栏窗口选择 Run Tests）测试结果输出到:
  - 应该是 Junit 规范的 xml 格式的测试报告：build/test-results/test/TEST-xx.xml
  - html 格式的一些记录，看起来不像是测试覆盖率：build/reports/configuration-cache/xx/xx/configuration-cache-report.html
- 另一个 runIdeForUiTests：行为上看起来和 runIDE 相似，都是本地启动一个安装了插件的ide

### 生成器使用



## 耗时步骤：
- 首次执行 ./gradlew build 会相当耗时，因为需要下载 ideaIC，大几百兆，如果出现超时但下载未完成的情况，重试即可，会断点续传

## 错误解决：

### 警告：
1. The IDE is not configured for using custom VM options (jb.vmOptionsFile=null)
   java.io.IOException: The IDE is not configured for using custom VM options (jb.vmOptionsFile=null)：./gradlew runIDE 时报错（警告），无需解决


## 参考资料：

- 关于 jdk 兼容性： Select JDK 11. This JDK will be the default JRE used to run Gradle, and the JDK version used to compile the plugin sources.
  Java 17 is required (blog post when targeting 2022.2 or later only.
  Java 11 is required (blog post) when targeting 2020.3 and later only.

- 关于 intellij 插件项目目录结构说明：
  https://plugins.jetbrains.com/docs/intellij/creating-plugin-project.html#components-of-a-wizard-generated-gradle-intellij-platform-plugin

- 关于 build.gradle.kts 文件与 intellij 相关的配置和task：https://plugins.jetbrains.com/docs/intellij/configuring-plugin-project.html

- 关于注册和发布到插件市场：https://plugins.jetbrains.com/docs/intellij/plugin-signing.html#provide-secrets-to-ide https://plugins.jetbrains.com/docs/intellij/publishing-plugin.html

- 我们的插件是自定义语言插件，相关参考：https://plugins.jetbrains.com/docs/intellij/custom-language-support.html
