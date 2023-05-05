import sys

import tensorflow as tf
from keras import Sequential
from keras.layers import Convolution2D, Activation, MaxPooling2D, Dense, Flatten
from keras.optimizers import Adam
from keras.utils import np_utils

if __name__ == '__main__':
    # 训练轮数
    train_epochs = int(sys.argv[1])
    # 模型保存目录
    save_path = sys.argv[2]
    # 类别文件保存路径
    class_path_str = sys.argv[3]
    print(f"train_epochs = {train_epochs}")
    print(f"save_path = {save_path}")
    (train_image, train_label), (test_image, test_label) = tf.keras.datasets.cifar10.load_data()
    # 获取到list
    class_list = ['plane', 'car', 'bird', 'cat', 'deer', 'dog', 'frog', 'horse', 'ship', 'truck']
    file = open(class_path_str, mode='wt', encoding='GBK')
    index = 0
    for line in class_list:
        file.write(line)
        if index != 9:
            file.write('\n')
        index += 1
    file.close()
    cn = len(class_list)

    # 归一化处理
    train_image = train_image / 255
    test_image = test_image / 255
    # train_image = train_image.reshape(len(train_label), 32, 32, 1)
    # test_image = test_image.reshape(len(test_label), 32, 32, 1)
    train_label = np_utils.to_categorical(train_label, num_classes=cn)
    test_label = np_utils.to_categorical(test_label, num_classes=cn)
    # # 构建模型开始训练
    # model = keras.Sequential()
    #
    # # 卷积
    # model.add(layers.Conv2D(64, (3, 3), activation='relu', input_shape=(32, 32, 3)))
    # # 卷积
    # model.add(layers.Conv2D(64, (3, 3), activation='relu'))
    # # 标准化
    # model.add(layers.BatchNormalization())
    # # 池化
    # model.add(layers.MaxPooling2D())
    # # 随机失活
    # model.add(layers.Dropout(0.25))
    #
    # # 卷积
    # model.add(layers.Conv2D(128, (3, 3), activation='relu'))
    # # 卷积
    # model.add(layers.Conv2D(128, (3, 3), activation='relu'))
    # # 标准化
    # model.add(layers.BatchNormalization())
    # # 池化
    # model.add(layers.MaxPooling2D())
    # # 随机失活
    # model.add(layers.Dropout(0.25))
    #
    # # 卷积
    # model.add(layers.Conv2D(256, (3, 3), activation='relu'))
    # # 卷积
    # model.add(layers.Conv2D(256, (1, 1), activation='relu'))
    # # 标准化
    # model.add(layers.BatchNormalization())
    # # 随机失活
    # model.add(layers.Dropout(0.25))
    # # 池化 同时降维
    # model.add(layers.GlobalAveragePooling2D())
    #
    # # 开始全连接
    # model.add(layers.Dense(128))
    # model.add(layers.BatchNormalization())
    # model.add(layers.Dropout(0.5))
    # # 输出类别
    # model.add(layers.Dense(cn, activation='softmax'))
    # model.summary()
    # model.compile(optimizer='adam',
    #               loss='sparse_categorical_crossentropy',
    #               metrics=['acc']
    #               )

    # 获取到模型对象
    model = Sequential()
    # 添加第一层神经元 这里第一层是卷积
    model.add(
        Convolution2D(
            # 指定 32 个滤波器（卷积核）
            filters=64,
            # 指定卷积核大小
            kernel_size=5,
            # 指定生成规则 设成same会自动加padding，保证输出是同样的大小。
            padding='same',
            # 设置卷积层第一次的输入数据维度
            batch_input_shape=train_image.shape,
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
    model.add(Convolution2D(filters=32, kernel_size=5, padding='same'))
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
    opt = Adam(learning_rate=0.001)
    # 开始构建模型
    model.compile(
        optimizer=opt,
        loss='categorical_crossentropy',
        # 指定计算损失的同时还计算一下精度
        metrics=['accuracy']
    )

    # 开始训练 传递 x y 以及训练次数
    history = model.fit(x=train_image, y=train_label, epochs=train_epochs, verbose=2)
    print("===== 开始进行模型准确度评估 =====")
    model.evaluate(test_image, test_label)
    model.evaluate(test_image, test_label)
    model.save(save_path)
    print(class_list)
    print("训练完毕！！！！")
