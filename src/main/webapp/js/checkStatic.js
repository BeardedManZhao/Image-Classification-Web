let status = 1;

/**
 * ����Ƿ��Ѿ���������ύ
 * @param y �������ύ֮��ᵯ������ʾ���е�����
 * @param n ���Ѿ��ύ��һ��֮��ᵯ������ʾ�������
 */
function check(y, n) {
    if (status === 0) {
        alert(n);
        return false;
    } else {
        status = 0;
        alert(y)
        return true;
    }
}

/**
 * ��ȡ��ָ��ָ���ĺܶ���еĵ�һ����ѡ�е���ֵ��
 * @param select_Name ͬһ name �ĵ����ѡ�ؼ���
 * @returns {{checked}|HTMLElement} ѡ�е��ĵ�Ԫ�ض���
 */
function checkFirstSelected(select_Name) {
    for (let elementsByNameElement of document.getElementsByName(select_Name)) {
        if (elementsByNameElement.checked) {
            return elementsByNameElement;
        }
    }
    return null;
}

/**
 * �����վ����״̬��������Ӧ״̬�������������
 * @param running_status_select_Name ��վ״̬����
 * @param Id ��վ״̬��ʾ�Ƶ�id
 */
function checkRunningStatus(running_status_select_Name, Id) {
    const obj1 = checkFirstSelected(running_status_select_Name)
    let obj2 = document.getElementById(Id);
    if (obj1.value === "closed") {
        obj2.style.backgroundColor = '#691d1d';
        // �رձ���ɫ
        const body = document.getElementById("body")
        if (body != null) {
            body.style.backgroundColor = '#2d2d2d'
            body.style.color = '#f3ebeb'
        }
    } else {
        obj2.style.backgroundColor = '#20ecbc';
        // ���´򿪱���ɫ
        const body = document.getElementById("body")
        if (body != null) {
            body.style.backgroundColor = '#FFF'
            body.style.color = '#4b4949'
        }
    }

    return false;
}

