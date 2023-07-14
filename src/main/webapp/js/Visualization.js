// 数据可视化 JS 脚本 需要 在HTML中引入下面所有文件
/*
    <script type="text/javascript" src="js/utils.js"></script>
    <script type="text/javascript" src="js/Visualization.js"></script>
    <script type="text/javascript" src="js/echarts.min.js"></script>
 */

/**
 * 将 json 数据或字典数据进行柱状图可视化的函数
 * @param doc 需要作为图展示框的盒子。
 * @param titleName 表的标题
 * @param json_data json 数据
 */
function lossAccBar(doc, titleName, json_data) {
    // 获取到 损失 精度 的list
    const lossList = json_data['loss']
    const accList = json_data['accuracy']
    // 初始化 Echarts
    const cs = echarts.init(d)
    // 初始化标记点
    const p = {
        data: [
            {
                name: 'MIN',
                type: 'min',
            },
            {
                name: 'MAX',
                type: 'max'
            }
        ],
        label: {
            formatter:'{b}'
        }
    }
    // 开始绘制图
    const option = {
        title: {
            show: true,
            name: titleName
        },
        xAxis: {
            show: true,
            type: 'category',
            data: range(0, lossList.length, 1)
        },
        yAxis: {
            show: true,
            type: 'value'
        },
        tooltip: {
            show: true
        },
        legend: {
            show: true
        },
        series: [
            {
                type: 'bar',
                name: 'loss',
                data: lossList,
                markPoint: p
            },
            {
                type: 'bar',
                name: 'acc',
                data: accList,
                itemStyle: {
                    color: '#9f2222'
                },
                markPoint: p
            },
        ]
    }
    // 装载
    cs.setOption(option)
}
