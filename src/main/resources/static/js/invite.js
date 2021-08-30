layui.use(function () {
    var layer = layui.layer
        , form = layui.form
        , laypage = layui.laypage
        , element = layui.element
        , laydate = layui.laydate
        , util = layui.util
        , jQuery = layui.jquery;

    userInit(jQuery);

    // 拒绝
    function handleRefuse() {

    }

    // 加入
    function handleJoinIn() {
        /**
         * 有没有uid
         * 有：
         * 没有：初始化
         *
         * 有没有昵称
         * 有：
         * 没有：设置昵称
         */
        let nickname = getNickname();
        if (isEmpty(nickname)) {
            // 弹窗

        }
    }


});