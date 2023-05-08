import sys
from typing import TextIO

import tensorflow as tf
from keras.utils import np_utils

import cnn

if __name__ == '__main__':
    # 训练轮数 模型保存目录 类别文件保存路径
    train_epochs, save_path, class_path_str = int(sys.argv[1]), sys.argv[2], sys.argv[3]
    print(f"train_epochs = {train_epochs}")
    print(f"save_path = {save_path}")
    (train_image, train_label), (test_image, test_label) = tf.keras.datasets.mnist.load_data()
    train_image = train_image.reshape(-1, 28, 28, 1)
    test_image = test_image.reshape(-1, 28, 28, 1)
    train_label = np_utils.to_categorical(train_label, num_classes=10)
    test_label = np_utils.to_categorical(test_label, num_classes=10)
    # 获取到list
    class_list = []
    file: TextIO = open(class_path_str, mode='wt', encoding='GBK')
    for line in range(10):
        num = str(line)
        class_list.append(num)
        file.write(num)
        if line != 9:
            file.write('\n')
    file.close()
    cn = len(class_list)

    # 构建模型开始训练
    model = cnn.precise_cnn(64, 1, 64, 2, 28, 28, cc=1)

    # 开始训练 传递 x y 以及训练次数
    history = model.fit(x=train_image, y=train_label, epochs=train_epochs, verbose=1)
    print("===== 开始进行 MNIST 模型准确度评估 =====")
    model.evaluate(test_image, test_label)
    model.evaluate(test_image, test_label)
    model.save(save_path)
    print(class_list)
    print("训练完毕！！！！")
