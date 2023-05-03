# -*- coding: utf-8 -*-
# @Time : 2023/5/2 20:03
# @Author : zhao
# @Email : liming7887@qq.com
# @File : train.py
# @Project : main2.py
import sys

import tensorflow as tf
from keras import Sequential
from keras.integration_test.preprocessing_test_utils import BATCH_SIZE
from keras.layers import Convolution2D, Activation, MaxPooling2D, Flatten, Dense
from keras.optimizers import Adam
from keras.utils import np_utils


def fun(train_dir, save_path, class_temp_str, class_path_str):
    print(f"train_dir = {train_dir}")
    print(f"save_path = {save_path}")
    print(f"class_temp_str = {class_temp_str}")
    print(f"class_path_str = {class_path_str}")

    # 获取到list
    class_list = []
    out_class = open(class_path_str, encoding='GBK', mode='wt')
    file = open(class_temp_str, encoding='GBK')
    for line in file.readlines():
        class_list.append(line.rstrip('\n'))
        out_class.write(line)
    file.close()
    out_class.close()

    # 实例化出图像数据生成器，其中的 rescale 参数会被乘到被读取的图像矩阵中，能够将所有的图像颜色设置规整到[0,255]
    image_generator = tf.keras.preprocessing.image.ImageDataGenerator(rescale=1. / 255)

    # 获取到图像数据集，并将训练数据与测试数据准备好
    x1 = image_generator.flow_from_directory(
        # 被读取的目录
        directory=train_dir,
        # 每一批被读取的数据，这里指定的是一个常量 64
        batch_size=BATCH_SIZE,
        # 是否进行随机排序
        shuffle=True,
        # 生成图像的尺寸
        target_size=(87, 87),
        color_mode='grayscale',
        classes=class_list
    )
    # 获取到类别数量
    cn = x1.num_classes
    # 将当前目录进行数据集生成器的加载
    # 将每一个图像对应的类别向量获取到，需要注意的是，当前的类别向量是一维度的扁平化向量，需要进行独热编码矩阵转换
    y1 = x1.classes
    print(f"类别数据向量 = {y1}")
    # 将向量中的类别编号转换成独热编码矩阵，表示类别 在这里共有 cn 个类别
    y1 = np_utils.to_categorical(y1, cn)
    print(f"==================================\n类别独热矩阵\n{y1}", end='\n==================================\n')
    # 获取到模型对象
    model = Sequential()
    # 添加第一层神经元 这里第一层是卷积
    model.add(
        Convolution2D(
            # 指定 32 个滤波器（卷积核）
            filters=32,
            # 指定卷积核大小
            kernel_size=5,
            # 指定生成规则 设成same会自动加padding，保证输出是同样的大小。
            padding='same',
            # 设置卷积层第一次的输入数据维度
            batch_input_shape=(len(y1), 87, 87, 1),
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
    # 添加一层卷积
    model.add(Convolution2D(filters=64, kernel_size=5, padding='same'))
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
    # 开始构建模型
    model.compile(
        optimizer=opt,
        loss='categorical_crossentropy',
        # 指定计算损失的同时还计算一下精度
        metrics=['accuracy']
    )

    # 开始训练 传递 x y 以及训练次数
    model.fit(x=x1, validation_data=y1.all(), epochs=16)
    model.save(save_path)
    print(class_list)
    return True


if __name__ == '__main__':
    fun(sys.argv[1], sys.argv[2], sys.argv[3], sys.argv[4])
