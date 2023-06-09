## 基本信息：
the group name: com.kusionstack.kcl
the artifact name: intellij-kcl (=rootProject.name in settings.gradle.kts) （最好改名为 kcl，这样在各种命名的地方不会违反规范，比如在 src/resources/META-INF/plugin.xml 里的 name 属性，不能带 intellij 这个词）（不过项目名字可以和插件名字不同，这个没关系）





## 操作手册

### 环境要求

- 需要安装 intellij IDEA
- 需要安装 jdk11
- 设置 intellij idea：Preference > File Encoding > Properties files(*.properties) > Default encoding for properties files 设置为 UTF-8

### 构建、运行、测试
- 构建：./gradlew build 构建完毕后显示 BUILD SUCCESSFUL，并且在 build/distributions 目录下产出名为 intellij-kcl-<version>.zip 的发布包
- 本地运行插件，进行debug：./gradlew runIDE（或：在gradle工具栏选择 Run Plugin）
- 测试：./gradlew test （或在 gradle 工具栏窗口选择 Run Tests）测试结果输出到:
  - 应该是 Junit 规范的 xml 格式的测试报告：build/test-results/test/TEST-xx.xml
  - html 格式的一些记录，看起来不像是测试覆盖率：build/reports/configuration-cache/xx/xx/configuration-cache-report.html
- 另一个 runIdeForUiTests：行为上看起来和 runIDE 相似，都是本地启动一个安装了插件的ide
- 升级：gradle.properties 文件中的 （pluginSinceBuild，pluginUntilBuild），platformVersion 字段声明了插件的兼容版本和构建版本，目前我们没有设置 pluginUntilBuild 字段，因此如果不存在兼容性问题的情况下，构建出的插件将允许在未来任意新版本的 intellij IDEA 上安装。在 intellij IDEA 发布新版本时，我们需要将 pluginUntilBuild 设置为新版本的版本号（例如2023.1则对应版本号为231），并通过 gradle 运行 verifyPluginConfiguration 任务（./gradlew verifyPluginConfiguration），查看兼容性校验结果。结果类似于：
    > Task :verifyPluginConfiguration
    [gradle-intellij-plugin :verifyPluginConfiguration] The following plugin configuration issues were found:
    The 'since-build' property is lower than the target IntelliJ Platform major version: 191 < 221.
    The Java configuration specifies targetCompatibility=11 but since-build='191' property requires targetCompatibility=1.8.
    The Kotlin configuration specifies jvmTarget=11 but since-build='191' property requires jvmTarget=1.8.
    See: https://jb.gg/intellij-platform-versions

### 生成器使用

#### parser generator
- 安装相关插件：grammar kit
- 在 src/main/java/io/kusionstack/kcl/kcl.bnf 文件上右键菜单栏选择 `Generate Parser Code`
- 生成后，为使得 IDEA 能够将 gen 包标记为一个已生成的源目录，从而正常显示代码提示，需要在 IDEA 内在生成的目录 src/main/gen 上方，右键选择 "Mark Directory as" > "Generated Sources Root"

#### lexer generator
- 安装相关插件：grammar kit
- （无需操作）安装 jflex-1.7.0-2.jar 和 idea-flex.skeleton，已经放置在项目根目录下，无需额外安装
- 在 src/main/java/io/kusionstack/kcl/kcl.flex 文件上右键菜单栏选择 `Run Jflex Generator`


## 耗时步骤：
- 首次执行 ./gradlew build 会相当耗时，因为需要下载 ideaIC，大几百兆，如果出现超时但下载未完成的情况，重试即可，会断点续传

## 错误解决：

### 报错：
- 删除某个测试类后，重新构建时 IntelliJ IDEA 仍然在尝试运行已删除的测试类。需要清理缓存

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

- 搜索使用了某个扩展点的插件仓库，寻找示例时很有用：https://plugins.jetbrains.com/intellij-platform-explorer/extensions?extensions=com.intellij.lineIndentProvider