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
