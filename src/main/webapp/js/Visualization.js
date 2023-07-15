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
 * @param json_data json 数据 其中可以接收 4 个key 分别是 loss accuracy test_loss test_acc
 */
function lossAccBar(doc, titleName, json_data) {
    // 获取到 损失 精度 的list
    const lossList = json_data['loss']
    const accList = json_data['accuracy']
    // 获取测试的损失与精度的list
    const testLossList = json_data['test_loss']
    const testAccList = json_data['test_acc']
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
            formatter: '{b}'
        }
    }
    // 准备数据容器
    const seriesData = [
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
            markPoint: p
        },
    ]
    if (testLossList != null) {
        seriesData.push(
            {
                type: 'bar',
                name: 'test_loss',
                data: testLossList,
                markPoint: p
            }
        )
    }
    if (testAccList != null) {
        seriesData.push(
            {
                type: 'bar',
                name: 'test_acc',
                data: testAccList,
                markPoint: p
            }
        )
    }

    // 初始化 Echarts
    const cs = echarts.init(d)
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
        // 设置工具箱
        toolbox: {
            show: true,
            feature: {
                saveAsImage: {
                    // 设置保存的图格式
                    type: 'jpg',
                    // 设置按钮的显示文字
                    title: 'saveAsImage',
                    // 设置保存的图像文件的名称
                    name: 'myImage'
                },
                // 设置第二个工具 数据视图
                dataView: {
                    // 显示此工具
                    show: true,
                    // 设置此工具的显示文字
                    title: 'open dataView',
                    // 设置此数据视图是否只读：这里设置的是可写可读
                    readOnly: true,
                    // 设置此数据视图中的三个显示的文字
                    lang: ['Data View', 'close', 'refresh']
                },
                // 设置缩放
                dataZoom: {},
                // 设置图表类型切换
                magicType: {
                    type: ['bar', 'line']
                }
            }
        },
        series: seriesData
    }
    // 装载
    cs.setOption(option)
}
