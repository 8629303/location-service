$(document).ready(function () {
    console.log("页面加载完成");
});

// IP 归属地查询
function queryIpLocation() {
    let ip = $("#ipInput").val();
    $.get("/api/ip/location", {ip: ip}, function (data) {
        // $("#ipResult").html(`
        //     <p><strong>国家:</strong> ${data.country || '未知'}</p>
        //     <p><strong>地区:</strong> ${data.region === '0' ? '未知' : data.region || '未知'}</p>
        //     <p><strong>省份:</strong> ${data.province || '未知'}</p>
        //     <p><strong>城市:</strong> ${data.city || '未知'}</p>
        //     <p><strong>运营商:</strong> ${data.isp || '未知'}</p>
        // `);
        $("#ipResult").empty();
        $("#ipResult").append(`国家: ${data.country || '未知'}, `);
        $("#ipResult").append(`地区: ${data.region === '0' ? '未知' : data.region || '未知'}, `);
        $("#ipResult").append(`省份: ${data.province || '未知'}, `);
        $("#ipResult").append(`城市: ${data.city || '未知'}, `);
        $("#ipResult").append(`运营商: ${data.isp || '未知'}<br>`);
    }).fail(function () {
        alert("查询失败");
    });
}

// 地址解析
function parseAddress() {
    let text = $("#addressText").val();
    $.get("/api/address/parse", {text: text}, function (data) {
        $("#parsedResult").html(`
            <p><strong>姓名:</strong> ${data.name || '未知'},   <strong>电话:</strong> ${data.phone || '未知'}</p>
            <p><strong>地址:</strong> ${data.address || '未知'}</p>
        `);
    }).fail(function () {
        alert("解析失败，请检查输入");
    });
}

// 地址转经纬度
function geocode() {
    let address = $("#addressInput").val();
    $.get("/api/address/geocode", {address: address}, function (data) {
            // 清空之前的结果
            $("#geoResult").empty();

            // 检查返回的状态
            if (data.status === "1") {
                // 遍历 geocodes 数组
                for (let i = 0; i < data.geocodes.length; i++) {
                    let location = data.geocodes[i].location; // 获取位置
                    let formattedAddress = data.geocodes[i].formatted_address; // 获取格式化地址

                    // 输出经纬度和格式化地址
                    $("#geoResult").append(`经度: ${location.split(',')[0]}, 纬度: ${location.split(',')[1]}, 地址: ${formattedAddress}<br>`);
                }
            } else {
                alert("查询失败: " + data.info);
            }
        }
    ).fail(function () {
        alert("查询失败");
    });
}

// 经纬度反查地址
function reGeocode() {
    let lat = $("#latitude").val();
    let lon = $("#longitude").val();
    $.get("/api/address/regeocode", {latitude: lat, longitude: lon}, function (data) {
        // 清空之前的结果
        $("#regeoResult").empty();

        // 检查返回的状态
        if (data.status === "1") {
            // 假设我们将 formatted_address 放入一个数组中
            let addresses = [data.regeocode.formatted_address]; // 这里可以添加更多地址

            // 遍历地址数组
            for (let i = 0; i < addresses.length; i++) {
                $("#regeoResult").append(`详细地址: ${addresses[i]}<br>`);
            }
        } else {
            alert("查询失败: " + data.info);
        }
    }).fail(function () {
        alert("查询失败");
    });
}