let status = 1;

/**
 * 检查是否已经点击过了提交
 * @param y 当正常提交之后会弹出的提示框中的内容
 * @param n 当已经提交过一次之后会弹出的提示框的内容
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
 * 获取到指定指定的很多框中的第一个被选中的数值。
 * @param select_Name 同一 name 的单或多选控件。
 * @returns {{checked}|HTMLElement} 选中的文档元素对象。
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
 * 检查网站运行状态，并将对应状态进行设置与更改
 * @param running_status_select_Name 网站状态数据
 * @param Id 网站状态显示灯的id
 */
function checkRunningStatus(running_status_select_Name, Id) {
    const obj1 = checkFirstSelected(running_status_select_Name)
    let obj2 = document.getElementById(Id);
    if (obj1.value === "closed") {
        obj2.style.backgroundColor = '#691d1d';
        // 关闭背景色
        const body = document.getElementById("body")
        if (body != null) {
            body.style.backgroundColor = '#2d2d2d'
            body.style.color = '#f3ebeb'
        }
    } else {
        obj2.style.backgroundColor = '#20ecbc';
        // 重新打开背景色
        const body = document.getElementById("body")
        if (body != null) {
            body.style.backgroundColor = '#FFF'
            body.style.color = '#4b4949'
        }
    }

    return false;
}

