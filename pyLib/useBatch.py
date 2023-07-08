# -*- coding: gbk -*-
# @Time : 2023/7/8 15:24
# @Author : zhao
# @Email : liming7887@qq.com
# @File : useBatch.py
# @Project : main2.py
import json
import os
import sys

import cv2
import tensorflow as tf


def get_phone(path_photo, image_w, image_h, color_channel):
    # 得到文件夹下的所有文件名称，存在字符串列表中
    files_list = os.listdir(path_photo)
    res_list = []
    # 将文件夹下面的所有文件名称与文件目录结合，获取到所有图
    if color_channel == 1:
        for phone_name in files_list:
            image_path = path_photo + '/' + phone_name
            # 直接使用 openCV 读取到图的数组矩阵
            res_list.append(cv2.resize(
                cv2.imread(image_path, cv2.IMREAD_GRAYSCALE),
                (image_w, image_h)
            ))
    else:
        for phone_name in files_list:
            image_path = path_photo + '/' + phone_name
            # 直接使用 openCV 读取到图的数组矩阵
            res_list.append(cv2.resize(
                cv2.imread(image_path),
                (image_w, image_h)
            ))
    # 返回 张量 以及 图像文件的名称
    return tf.convert_to_tensor(res_list), files_list


def fun(model_path, data_path, image_w, image_h, color_channel):
    """
    :param model_path: 计算时需要使用的模型目录
    :param data_path:  需要被计算的数据路径
    :param image_w:    当前模型支持计算的图像宽度
    :param image_h:    当前模型支持计算的图像高度
    :param color_channel: 当前模型支持计算的图像通道数量
    :return: 类别编码
    """
    print(f"data_path   =   {data_path}")
    print(f"model_data  =   {model_path}")
    print(f"image_w     =   {image_w}")
    print(f"image_h     =   {image_h}")
    print(f"color_channel   =   {color_channel}")
    # 获取到我们用于测试的图像数组 以及 所有图像文件名称
    image, image_name = get_phone(data_path, image_w, image_h, color_channel)
    # 使用模型计算结果
    model = tf.keras.models.load_model(model_path)
    print(f"被处理图像数据集维度 = {tf.shape(image)}")
    res = model.predict(tf.reshape(image, [-1, image_w, image_h, color_channel]))
    print(res)
    # 准备结果数据集
    dict_data = {

    }
    # 找到结果向量中最大得分的索引
    index = 0
    for r in res:
        # 获取到所属类别
        c_num = str(tf.argmax(r).numpy())
        # 将此类别做为字典的 key
        if c_num in dict_data:
            dict_data[c_num].append(image_name[index])
        else:
            dict_data[c_num] = [image_name[index]]
        index += 1
    # 使用索引获取到结果类别
    print(dict_data)
    res_json = open(data_path + '/classificationResults.json', 'wt')
    json.dump(dict_data, fp=res_json)
    res_json.close()


if __name__ == '__main__':
    if len(sys.argv) < 5:
        print(
            """
            请按照下面的顺序输入参数：
                1  =  计算时需要使用的模型
                2  =  需要被计算的数据路径
                3  =  被识别的图像宽度
                4  =  被识别的图像高度
                5  =  被识别的图像颜色通道数量
            """
        )
        exit()
    else:
        print("开始批量识别，请注意，如果发生错误或未识别成功，点击下载是无效的。")
        fun(sys.argv[1], sys.argv[2], int(sys.argv[3]), int(sys.argv[4]), int(sys.argv[5]))
        print(
            "当前被识别的图像已保存，您可以开始下载了!!!!!"
        )
        exit()
