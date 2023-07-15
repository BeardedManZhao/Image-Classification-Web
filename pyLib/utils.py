# -*- coding: utf-8 -*-
# @Time : 2023/7/15 10:39
# @Author : zhao
# @Email : liming7887@qq.com
# @File : utils.py
# @Project : main2.py
import json


def save_log_json(out_json_path, his, loss_key, acc_key):
    """
    将一个训练日志数据保存到指定的路径中
    :param out_json_path: 需要被保存的训练日志的路径
    :param his: 需要被保存的训练日志对象
    """
    out_json_file = open(out_json_path, 'wt')
    json.dump(
        {
            'loss': his.history[loss_key],
            'accuracy': his.history[acc_key],
            # 'test_loss': his.history['val_loss'],
            # 'test_acc': his.history['val_acc']
        },
        out_json_file
    )
