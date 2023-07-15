# -*- coding: utf-8 -*-
# @Time : 2023/5/6 15:58
# @Author : zhao
# @Email : liming7887@qq.com
# @File : cnn.py
# @Project : main2.py
from keras import Sequential
from keras.applications.densenet import layers
from keras.layers import Convolution2D, Activation, MaxPooling2D, Flatten, Dense
from keras.optimizers import Adam


def performance_cnn(cn, convolutional_count, init_filters=32, filters_b=2, image_w=100, image_h=100):
    """
    性能优先的方式构建出一个神经网络模型，并将构建好的神经网络模型返回出去。
    :param image_h: 输入数据样本难度宽度。
    :param image_w: 输入数据样本难度高度。
    :param cn: 输出层神经元数量，此参数通常代表的就是数据输出结果的描述数值。
    :param convolutional_count: 卷积层数量 此参数是一个 int 类型的数值
                                需要注意的是 模型中自带一层输入卷积，其代表了在本神经网络模型中包含的卷积层数量（每个卷积层由 卷积 relu激活 池化 三层神经网络组成）
    :param init_filters 初始卷积层中的卷积核数量
    :param filters_b: 卷积核数量 此参数是一个 int 参数 代表不同层的卷积核等比。
    :return: 按照参数构建好的神经网络训练模型。
    """
    model = Sequential()
    # 添加第一层神经元 这里第一层是卷积
    model.add(
        Convolution2D(
            # 指定 初始 个滤波器（卷积核）
            filters=init_filters,
            # 指定卷积核大小
            kernel_size=2,
            # 指定生成规则 设成same会自动加padding，保证输出是同样的大小。
            padding='same',
            # 设置卷积层第一次的输入数据维度
            batch_input_shape=(None, image_w, image_h, 1),
        )
    )
    # 添加一层激活函数
    model.add(Activation('relu'))
    # 添加一层池化层
    model.add(
        MaxPooling2D(
            # 指定池化层核的尺寸 这里是 2x2
            pool_size=2,
            # 指定步长 2x2
            strides=2,
            # 指定池化层生成规则
            padding='same'
        )
    )

    # 添加所有剩余层的卷积层
    if filters_b == 2:
        for i in range(convolutional_count):
            # 添加一层卷积
            init_filters <<= 1
            model.add(Convolution2D(filters=init_filters, kernel_size=2, padding='same'))
            # 添加一层激活函数
            model.add(Activation("relu"))
            # 添加一层池化
            model.add(MaxPooling2D(pool_size=2, padding='same'))
    else:
        for i in range(convolutional_count):
            # 添加一层卷积
            init_filters *= filters_b
            model.add(Convolution2D(filters=init_filters, kernel_size=2, padding='same'))
            # 添加一层激活函数
            model.add(Activation("relu"))
            # 添加一层池化
            model.add(MaxPooling2D(pool_size=2, padding='same'))

    # 将矩阵扁平化准备全连接
    model.add(Flatten())
    # 正式进入全连接神经网络，添加全连接神经元(具有1024个神经元的层)
    model.add(Dense(1024))
    # 添加激活函数
    model.add(Activation("relu"))
    # 再一次添加一层 8 个神经元的网络层(每个神经元代表一个类别)
    model.add(Dense(cn))
    # 添加激活函数 softmax 用于计算概率得分
    model.add(Activation("softmax"))

    # 准备模型构建，在这里指定学习率
    opt = Adam(learning_rate=1e-4)

    model.summary()
    # 开始构建模型
    model.compile(
        optimizer=opt,
        loss='categorical_crossentropy',
        # 指定计算损失的同时还计算一下精度
        metrics=['accuracy']
    )
    return model


def precise_cnn(cn, convolutional_count, init_filters=32, filters_b=2, image_w=100, image_h=100, cc=3,
                loss='sparse_categorical_crossentropy'):
    """
    精确度优先的方式构建出一个神经网络模型，并将构建好的神经网络模型返回出去，该模型相较于性能优先训练模型进行了一个优化操作，其进行了神经元失活等操作有效的处理了过拟合问题。
    :param loss: 构建的模型使用的损失函数计算方式，默认为 sparse_categorical_crossentropy
    :param cc: 模型能够支持的颜色通道数值
    :param image_h: 输入数据样本难度宽度。
    :param image_w: 输入数据样本难度高度。
    :param cn: 输出层神经元数量，此参数通常代表的就是数据输出结果的描述数值。
    :param convolutional_count: 卷积层数量 此参数是一个 int 类型的数值
                                需要注意的是 模型中自带一层输入卷积，其代表了在本神经网络模型中包含的卷积层数量（每个卷积层由 卷积 relu激活 池化 三层神经网络组成）
    :param init_filters 初始卷积层中的卷积核数量
    :param filters_b: 卷积核数量 此参数是一个 int 参数 代表不同层的卷积核等比。
    :return: 按照参数构建好的神经网络训练模型。
    """
    model = Sequential()
    # 卷积
    if cc == 3:
        model.add(layers.Conv2D(init_filters, (3, 3), activation='relu', input_shape=(image_w, image_h, 3)))
    else:
        model.add(layers.Conv2D(init_filters, (3, 3), activation='relu', input_shape=(image_w, image_h, 1)))
    # 卷积
    model.add(layers.Conv2D(init_filters, (3, 3), activation='relu'))
    # 标准化
    model.add(layers.BatchNormalization())
    # 池化
    model.add(layers.MaxPooling2D())
    # 随机失活
    model.add(layers.Dropout(0.25))
    for i in range(convolutional_count):
        if filters_b == 2:
            init_filters <<= 1
        else:
            init_filters *= filters_b
        # 卷积
        model.add(layers.Conv2D(init_filters, (3, 3), activation='relu'))
        # 卷积
        model.add(layers.Conv2D(init_filters, (3, 3), activation='relu'))
        # 标准化
        model.add(layers.BatchNormalization())
        # 池化
        model.add(layers.MaxPooling2D())
        # 随机失活
        model.add(layers.Dropout(0.25))
    # 计算出最后一层的卷积神经网络的核数
    num = init_filters << 1
    # 卷积
    model.add(layers.Conv2D(num, (3, 3), activation='relu'))
    # 卷积
    model.add(layers.Conv2D(num, (1, 1), activation='relu'))
    # 标准化
    model.add(layers.BatchNormalization())
    # 随机失活
    model.add(layers.Dropout(0.25))
    # 池化 同时降维
    model.add(layers.GlobalAveragePooling2D())

    # 开始全连接
    model.add(layers.Dense(128))
    model.add(layers.BatchNormalization())
    model.add(layers.Dropout(0.5))
    # 输出类别
    model.add(layers.Dense(cn, activation='softmax'))
    model.summary()
    model.compile(optimizer='adam',
                  loss=loss,
                  metrics=['accuracy']
                  )
    return model
