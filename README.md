# Image-Classification-Web

图像分类系统 网站

## 网站部署方式

- 框架 网站由TomCat服务器框架进行开发，因此需要进行TomCat服务器的安装操作，在这里的服务器版本为 9.0 左右最佳。

- 依赖包 系统依赖诸多第三方库，需要将所有被依赖的包提供给TomCat进行读取，用于服务器依赖包的导入操作，所有需要被导入的依赖如下所示。

- 神经网络环境搭建 系统中的图像分类操作由 Python 对应的 Keras 进行的处理，Keras所依赖的运行环境为当前系统中的python环境版本，需要对python环境进行配置。 下面展示的就是python神经网络需要安装的所有包

```shell
.\venv\Scripts\pip.exe install keras
.\venv\Scripts\pip.exe install opencv-python
# 如果接入CPU就使用下面的命令
.\venv\Scripts\pip.exe install tensorflow -i https://pypi.tuna.tsinghua.edu.cn/simple
# 如果接入GPU就使用下面的命令
.\venv\Scripts\pip.exe install tensorflow-gpu -i https://pypi.tuna.tsinghua.edu.cn/simple
```

- WEB环境配置 TomCat中需要进行一些配置才可进行正常的使用，接下来就是所有需要被配置的东西

1. 配置maven 配置pom文件 刷新maven 依赖坐标如下所示

```xml

<dependencies>
    <dependency>
        <groupId>org.apache.commons</groupId>
        <artifactId>commons-io</artifactId>
        <version>1.3.2</version>
    </dependency>
    <dependency>
        <groupId>io.github.BeardedManZhao</groupId>
        <artifactId>algorithmStar</artifactId>
        <version>1.19</version>
    </dependency>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
        <version>4.0.1</version>
    </dependency>
</dependencies>
```

2. 配置JDK 和 开发时的TomCat
3. 配置Web资源文件目录 是项目中名称为 webapp 的目录文件夹。
4. 修改 Conf 类中的基础配置，所有从根目录开始的目录都需要重新配置，按照自己的服务器配置。
5. 配置服务器图像数据的读取目录，用于读取用户使用模型时上传的文件，便于前端的图像回调。

```xml
<!-- 在 server.xml 中的 Host 节点添加下面的配置 -->
<Host>
    <!-- 使用模型进行识别之后会返回性训练结果，其中有一个被识别图像展示，需要使用到这个虚拟路径 -->
    <Context docBase="[IMAGE_USE_DIR 对应的数值]" path="/IMW/IMW_IMAGE/use/" reloadable="true" />
    <!-- 使用模型进行批量识别后会返回识别结果的json文件，这个json文件需要此路径配置才可以下载 -->
    <Context docBase="[IMAGE_USE_BATCH_DIR 对应的数值]" path="/IMW/IMW_IMAGE/batchUse/" reloadable="false" />
    <!-- 用户个人空间目录的虚拟路径配置 -->
    <Context docBase="[TRAIN_DIR 对应的数值]" path="[IMAGE_TRAIN_DIR] 对应的数值" reloadable="true" />
</Host>
```

6. 点击脚本文件 compile.bat 或者 sh覆写引导文件 进行神经网络系统的覆写，也可以使用网站的引导页面进行自动覆写。