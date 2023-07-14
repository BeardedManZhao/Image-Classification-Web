/**
 * 生成一个指定区间 指定步长数值的等差list
 * @param start list的左区间
 * @param end list的右区间
 * @param step 步长数值
 * @returns {*[]} 等差数列
 */
function range(start, end, step) {
    const res = []
    let index = start;
    while (index < end) {
        res.push(index)
        index += step
    }
    return res;
}
