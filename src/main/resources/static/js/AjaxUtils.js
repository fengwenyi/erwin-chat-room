function ajaxPost(jQuery, layer, url, data,  funcSuc) {
    let layerIndex;
    jQuery.ajax({
        url: url,
        type: HTTP_POST,
        // 请求的媒体类型
        contentType: "application/json;charset=UTF-8",
        data: data,
        beforeSend:function () {
            layerIndex = layer.load(0, { shade: 0.1 });
        },
        success: funcSuc,
        complete: function () {
            layer.close(layerIndex);
        },
    });
    return false;
}