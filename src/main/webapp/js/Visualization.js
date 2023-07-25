// ���ݿ��ӻ� JS �ű� ��Ҫ ��HTML���������������ļ�
/*
    <script type="text/javascript" src="js/utils.js"></script>
    <script type="text/javascript" src="js/Visualization.js"></script>
    <script type="text/javascript" src="js/echarts.min.js"></script>
 */

/**
 * �� json ���ݻ��ֵ����ݽ�����״ͼ���ӻ��ĺ���
 * @param d ��Ҫ��Ϊͼչʾ��ĺ��ӡ�
 * @param titleName ��ı���
 * @param json_data json ���� ���п��Խ��� 4 ��key �ֱ��� loss accuracy test_loss test_acc
 */
function lossAccBar(d, titleName, json_data) {
    // ��ȡ�� ��ʧ ���� ��list
    const lossList = json_data['loss']
    const accList = json_data['accuracy']
    // ��ȡ���Ե���ʧ�뾫�ȵ�list
    const testLossList = json_data['test_loss']
    const testAccList = json_data['test_acc']
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
            formatter: '{b}'
        }
    }
    // ׼����������
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

    // ��ʼ�� Echarts
    const cs = echarts.init(d)
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
                    show: true,
                    // ���ô˹��ߵ���ʾ����
                    title: 'open dataView',
                    // ���ô�������ͼ�Ƿ�ֻ�����������õ��ǿ�д�ɶ�
                    readOnly: true,
                    // ���ô�������ͼ�е�������ʾ������
                    lang: ['Data View', 'close', 'refresh']
                },
                // ��������
                dataZoom: {},
                // ����ͼ�������л�
                magicType: {
                    type: ['bar', 'line']
                }
            }
        },
        series: seriesData
    }
    // װ��
    cs.setOption(option)
}


/**
 * �ӷ������л�ȡ����ǰ�û���ģ�͵�ѵ����־json���ݣ���չʾ��Ϊͼ��
 * @param doc ��Ҫ����Ϊͼ�����Ԫ�ص��ĵ�����
 */
function getLossAcc(doc) {
    const parseUrl1 = parseUrl();
    // ʹ�� axios ���ʷ�������ȡ����
    axios(
        {
            url: parseUrl1.url[0] + "//" + parseUrl1.url[1] + '/' + parseUrl1.url[2] + '/JsonServlet',
            // ���� axios ���������
            params: {
                jsonName: 'lossAcc.json'
            }
        }
    ).then(
        (args) => {
            lossAccBar(doc, "ģ��ѵ����־ͼ��", args.data)
        }
    ).catch(
        (args) => {
            console.log(args)
            if (args.code === 'ERR_BAD_REQUEST') {
                alert("Please train a neural network model first!!!")
            } else {
                alert("Unknown error, unable to use neural network model!!!!")
            }
            window.history.go(-1)
        }
    )
}
