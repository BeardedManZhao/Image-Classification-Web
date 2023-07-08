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
    # �õ��ļ����µ������ļ����ƣ������ַ����б���
    files_list = os.listdir(path_photo)
    res_list = []
    # ���ļ�������������ļ��������ļ�Ŀ¼��ϣ���ȡ������ͼ
    if color_channel == 1:
        for phone_name in files_list:
            image_path = path_photo + '/' + phone_name
            # ֱ��ʹ�� openCV ��ȡ��ͼ���������
            res_list.append(cv2.resize(
                cv2.imread(image_path, cv2.IMREAD_GRAYSCALE),
                (image_w, image_h)
            ))
    else:
        for phone_name in files_list:
            image_path = path_photo + '/' + phone_name
            # ֱ��ʹ�� openCV ��ȡ��ͼ���������
            res_list.append(cv2.resize(
                cv2.imread(image_path),
                (image_w, image_h)
            ))
    # ���� ���� �Լ� ͼ���ļ�������
    return tf.convert_to_tensor(res_list), files_list


def fun(model_path, data_path, image_w, image_h, color_channel):
    """
    :param model_path: ����ʱ��Ҫʹ�õ�ģ��Ŀ¼
    :param data_path:  ��Ҫ�����������·��
    :param image_w:    ��ǰģ��֧�ּ����ͼ����
    :param image_h:    ��ǰģ��֧�ּ����ͼ��߶�
    :param color_channel: ��ǰģ��֧�ּ����ͼ��ͨ������
    :return: ������
    """
    print(f"data_path   =   {data_path}")
    print(f"model_data  =   {model_path}")
    print(f"image_w     =   {image_w}")
    print(f"image_h     =   {image_h}")
    print(f"color_channel   =   {color_channel}")
    # ��ȡ���������ڲ��Ե�ͼ������ �Լ� ����ͼ���ļ�����
    image, image_name = get_phone(data_path, image_w, image_h, color_channel)
    # ʹ��ģ�ͼ�����
    model = tf.keras.models.load_model(model_path)
    print(f"������ͼ�����ݼ�ά�� = {tf.shape(image)}")
    res = model.predict(tf.reshape(image, [-1, image_w, image_h, color_channel]))
    print(res)
    # ׼��������ݼ�
    dict_data = {

    }
    # �ҵ�������������÷ֵ�����
    index = 0
    for r in res:
        # ��ȡ���������
        c_num = str(tf.argmax(r).numpy())
        # ���������Ϊ�ֵ�� key
        if c_num in dict_data:
            dict_data[c_num].append(image_name[index])
        else:
            dict_data[c_num] = [image_name[index]]
        index += 1
    # ʹ��������ȡ��������
    print(dict_data)
    res_json = open(data_path + '/classificationResults.json', 'wt')
    json.dump(dict_data, fp=res_json)
    res_json.close()


if __name__ == '__main__':
    if len(sys.argv) < 5:
        print(
            """
            �밴�������˳�����������
                1  =  ����ʱ��Ҫʹ�õ�ģ��
                2  =  ��Ҫ�����������·��
                3  =  ��ʶ���ͼ����
                4  =  ��ʶ���ͼ��߶�
                5  =  ��ʶ���ͼ����ɫͨ������
            """
        )
        exit()
    else:
        print("��ʼ����ʶ����ע�⣬������������δʶ��ɹ��������������Ч�ġ�")
        fun(sys.argv[1], sys.argv[2], int(sys.argv[3]), int(sys.argv[4]), int(sys.argv[5]))
        print(
            "��ǰ��ʶ���ͼ���ѱ��棬�����Կ�ʼ������!!!!!"
        )
        exit()
