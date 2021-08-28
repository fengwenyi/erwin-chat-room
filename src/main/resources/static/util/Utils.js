/**
 * 字符串判空
 * @param x
 * @returns {boolean}
 */
function isEmpty(x) {
    return x === null
        || x === undefined
        || x.trim() === ''
        || x.trim() === 'null'
        || x.trim() === 'undefined';
}

/**
 * 字符串判非空
 * @param x
 * @returns {boolean}
 */
function isNotEmpty(x) {
    return !isEmpty(x);
}

/**
 * 对象判空
 * @param x
 * @returns {boolean}
 */
function isNull(x) {
    return x === null
        || x === undefined;
}

/**
 * 对象判非空
 * @param x
 * @returns {boolean}
 */
function nonNull(x) {
    return !isNull(x)
}

/**
 * 随机颜色
 * @returns {string}
 */
function randomColor(){
    let colorAngle = Math.floor(Math.random()*360);
    return 'hsla('+ colorAngle +',100%,50%,1)';
}

/**
 * 推荐颜色
 * @returns {string}
 */
function recommendColor() {
    let colors = [
        "#0099e5"
        , "#ff4c4c"
        , "#34bf49"
        , "#00a98f"
        , "#000000"
        , "#be0027"
        , "#cf8d2e"
        , "#2c9f45"
        , "#371777"
        , "#52325d"
        , "#511378"
        , "#6a737b"
        , "#00a4e4"
    ];
    let index = Math.floor(Math.random() * (colors.length - 1));
    return colors[index];
}