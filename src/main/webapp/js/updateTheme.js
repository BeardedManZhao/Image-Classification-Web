/**
 * �޸�ָ��id��Ӧ�ؼ��ı�ǩ���ı�
 * @param id ���޸ĵı�ǩ
 * @param newString �޸�֮�������ֵ
 */
function setTextById(id, newString) {
    const d = document.getElementById(id)
    d.innerText = newString;
}

// ������ص�״̬��ֵ 1=����
let status1 = 1;

/**
 * ������رձ����ƣ���������
 * @param buttonId �޸�����֮����Ҫ���޸�����İ�ť Id
 */
function closeOrOpenBackColor(buttonId) {
    // ���Ȼ�ȡ����Ҫ������ı���
    const body = document.getElementById('body')
    if (body != null) {
        // �жϵ�ǰ�������Ƿ���
        if (status1 === 1) {
            // ����״̬�͹ر�
            body.style.backgroundColor = '#2d2d2d'
            body.style.color = '#f3ebeb'
            setTextById(buttonId, '�򿪱�����')
            status1 = 0
        } else {
            // �ر�״̬�Ϳ���
            body.style.backgroundColor = '#FFF'
            body.style.color = '#4b4949'
            setTextById(buttonId, '�رձ�����')
            status1 = 1
        }
    }
}
