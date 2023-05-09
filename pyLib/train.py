# -*- coding: utf-8 -*-
# @Time : 2023/5/2 20:03
# @Author : zhao
# @Email : liming7887@qq.com
# @File : train.py
# @Project : main2.py
import sys

from keras.integration_test.preprocessing_test_utils import BATCH_SIZE
from keras.preprocessing.image import ImageDataGenerator
from keras.utils import np_utils

import cnn


def fun(train_dir,
        save_path,
        class_temp_str,
        class_path_str,
        train_epochs=16,
        image_w=100, image_h=100,
        use_performance=True,
        convolutional_count=2,
        init_filters=32, filters_b=2):
    print(f"train_dir = {train_dir}")
    print(f"save_path = {save_path}")
    print(f"class_temp_str = {class_temp_str}")
    print(f"class_path_str = {class_path_str}")
    print(f"train_epochs = {train_epochs}")
    print(f"image_w = {image_w}")
    print(f"image_h = {image_h}")
    print(f"use_performance = {use_performance}")
    print(f"convolutional_count = {convolutional_count}")
    print(f"init_filters = {init_filters}")
    print(f"filters_b = {filters_b}")

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
    # 指定被训练图像目录的生成器对象
    image_generator = ImageDataGenerator(
        # 重缩放因子 会被乘到每一个图像矩阵中
        rescale=1 / 255
    )

    # 获取到图像数据集，并将训练数据与测试数据准备好
    x1 = image_generator.flow_from_directory(
        # 被读取的目录
        directory=train_dir,
        # 每一批被读取的数据，这里指定的是一个常量 64
        batch_size=BATCH_SIZE,
        # 是否进行随机排序
        shuffle=True,
        # 生成图像的尺寸
        target_size=(image_w, image_h),
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
    if use_performance:
        model = cnn.performance_cnn(
            cn=cn, convolutional_count=convolutional_count, init_filters=init_filters, filters_b=filters_b,
            image_h=image_h, image_w=image_w,
        )
    else:
        model = cnn.precise_cnn(
            cn=cn, convolutional_count=convolutional_count, init_filters=init_filters, filters_b=filters_b,
            image_h=image_h, image_w=image_w,
            cc=1, loss='categorical_crossentropy'
        )
    # 开始训练 传递 x y 以及训练次数
    model.fit(x=x1, validation_data=y1.all(), epochs=train_epochs, verbose=2)
    model.save(save_path)
    print(class_list)
    return True


if __name__ == '__main__':
    if len(sys.argv) < 11:
        print(f"""
        请安装下面的顺序输入参数：
            1  =  被训练文件所在目录，需要保证其二级目录是类别目录。
            2  =  训练模型保存路径，需要保证其存在与文件系统中。
            3  =  被训练数据所包含的类别描述文件路径。
            4  =  被训练模型所包含的类别描述文件输出路径。
            5  =  训练模型的时候，要进行的训练批次。
            6  =  被训练的学习样本的图像尺寸 - 宽。
            7  =  被训练的学习样本的图像尺寸 - 高。
            8  =  是否使用性能优先模型进行训练。
            9  =  模型在训练的时候需要使用的卷积层数。
            10 =  初始卷积层对应的卷积核的数量。
            11 =  从初始卷积层开始，每一层卷积核数量的公比数值。
        """)
        exit()
    fun(
        sys.argv[1],
        sys.argv[2],
        sys.argv[3],
        sys.argv[4],
        int(sys.argv[5]),
        int(sys.argv[6]), int(sys.argv[7]),
        sys.argv[8] == 'True',
        int(sys.argv[9]),
        int(sys.argv[10]),
        int(sys.argv[11])
    )
