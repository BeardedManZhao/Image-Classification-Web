/**
 * 修改指定id对应控件的标签内文本
 * @param id 需修改的标签
 * @param newString 修改之后的新数值
 */
function setTextById(id, newString) {
    const d = document.getElementById(id)
    d.innerText = newString;
}

// 开灯与关灯状态数值 1=灯亮
let status1 = 1;

/**
 * 开启或关闭背景灯，更改主题
 * @param buttonId 修改主题之后需要被修改字体的按钮 Id
 */
function closeOrOpenBackColor(buttonId) {
    // 首先获取到需要被处理的背景
    const body = document.getElementById('body')
    if (body != null) {
        // 判断当前背景灯是否开启
        if (status1 === 1) {
            // 开启状态就关闭
            body.style.backgroundColor = '#2d2d2d'
            body.style.color = '#f3ebeb'
            setTextById(buttonId, '打开背景灯')
            status1 = 0
        } else {
            // 关闭状态就开启
            body.style.backgroundColor = '#FFF'
            body.style.color = '#4b4949'
            setTextById(buttonId, '关闭背景灯')
            status1 = 1
        }
    }
}
