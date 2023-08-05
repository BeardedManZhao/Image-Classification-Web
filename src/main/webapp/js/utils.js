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

// 准备url路径提取所需要 正则对象
const urlPathReg1 = new RegExp("\\?")
const urlPathReg2 = new RegExp("/+")

/**
 * 将当前的 url 进行拆分
 * @returns {{pram: string, url: string[]}}  {"url" :[url协议，url主机，路径1，路径2，.... ]， pram :"参数字符串"}
 */
function parseUrl() {
    // 首先获取到url地址
    const url = window.location.href
    // 获取到 请求数据部分 [url和路径，参数1，参数2]
    const strings1 = url.split(urlPathReg1);
    // 然后按照规则获取到{"url" :[url请求部分，路径1，路径2，.... ]， pram :"参数字符串"}
    return {"url": strings1[0].split(urlPathReg2), "pram": strings1[1]};
}

/**
 * 将当前的 url 部分中的servlet请求部分进行替换
 * @param servlet_str 服务类字符串数据
 * @param prams 额外追加的参数字符串
 * @returns {string} 转换之后的新字符串
 */
function setUrlServlet(servlet_str, prams) {
    const d = parseUrl();
    let res = d['url'][0] + '/' + d['url'][1] + '/' + servlet_str + '?';
    const p = d['pram'];
    if (!(p == null || p.length === 0)) {
        res += p;
        res += '&'
    }
    if (prams != null) {
        for (let pram of prams) {
            res += prams
        }
    }
    return res;
}

/**
 * 判断一个对象是否可以进行迭代
 * @param obj 需要被判断的对象
 * @returns {boolean} 如果可迭代则返回 true
 */
function isIterable(obj) {
    return typeof obj === 'object' && obj !== null && !Array.isArray(obj) && typeof obj[Symbol.iterator] === 'function';
}

function isJson(obj) {
    return obj !== null && obj instanceof Object && typeof obj === 'object';
}

/**
 * 将一个 json 字符串中的数据按照指定的 keys 获取
 * @param jsonStr json字符串
 * @param showKeys 需要被提取的值对应的路径
 * @returns {any}
 */
function jsonShow(jsonStr, showKeys) {
    // 首先将字符串转换为 json 然后获取 key 对应的value
    let res = JSON.parse(jsonStr);
    for (let name in showKeys) {
        res = res[name]
    }
    return res;
}

function getWebConf(){
    let res = null;
    // 首先获取到配置数据
    const parseUrl1 = parseUrl();
    // 使用 axios 访问服务器获取数据
    axios(
        {
            url: parseUrl1.url[0] + "//" + parseUrl1.url[1] + '/' + parseUrl1.url[2] + '/JsonServlet',
            // 设置 axios 的请求参数
            params: {
                jsonName: 'confJson'
            }
        }
    ).then(
        (args) => {
            res = args['data']
        }
    )
    return res;
}
