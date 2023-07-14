// ���ݿ��ӻ� JS �ű� ��Ҫ ��HTML���������������ļ�
/*
    <script type="text/javascript" src="js/utils.js"></script>
    <script type="text/javascript" src="js/Visualization.js"></script>
    <script type="text/javascript" src="js/echarts.min.js"></script>
 */

/**
 * �� json ���ݻ��ֵ����ݽ�����״ͼ���ӻ��ĺ���
 * @param doc ��Ҫ��Ϊͼչʾ��ĺ��ӡ�
 * @param titleName ��ı���
 * @param json_data json ����
 */
function lossAccBar(doc, titleName, json_data) {
    // ��ȡ�� ��ʧ ���� ��list
    const lossList = json_data['loss']
    const accList = json_data['accuracy']
    // ��ʼ�� Echarts
    const cs = echarts.init(d)
    // ��ʼ����ǵ�
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
    // ��ʼ����ͼ
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
        // ���ù�����
        toolbox: {
            show: true,
            feature: {
                saveAsImage: {
                    // ���ñ����ͼ��ʽ
                    type: 'jpg',
                    // ���ð�ť����ʾ����
                    title: 'saveAsImage',
                    // ���ñ����ͼ���ļ�������
                    name: 'myImage'
                },
                // ���õڶ������� ������ͼ
                dataView: {
                    // ��ʾ�˹���
                    show:true,
                    // ���ô˹��ߵ���ʾ����
                    title:'open dataView',
                    // ���ô�������ͼ�Ƿ�ֻ�����������õ��ǿ�д�ɶ�
                    readOnly:true,
                    // ���ô�������ͼ�е�������ʾ������
                    lang: ['Data View', 'close', 'refresh']
                }
            }
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
    // װ��
    cs.setOption(option)
}
