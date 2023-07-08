import sys

import tensorflow as tf

import cnn

if __name__ == '__main__':
    if len(sys.argv) < 3:
        print("""
        请按照下面的顺序输入参数：
            1  =  内置数据模型训练的轮数
            2  =  训练出的模型保存的目录
            3  =  训练模型的类别文件存储目录
        """)
        exit()
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

    # 构建模型开始训练
    model = cnn.precise_cnn(10, 1, 64, 2, 32, 32)

    # 开始训练 传递 x y 以及训练次数
    history = model.fit(x=train_image, y=train_label, epochs=train_epochs, verbose=2)
    print("===== 开始进行 CIFAR-10 模型准确度评估 =====")
    model.evaluate(test_image, test_label)
    model.evaluate(test_image, test_label)
    model.save(save_path)
    print(class_list)
    print("训练完毕！！！！")
