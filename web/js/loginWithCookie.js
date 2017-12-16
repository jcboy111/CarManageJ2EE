var baseUrl = "http://localhost:8075/";
$(".submit").click(function () {
    //以管理员身份登录
    if ($('#admin').is(':checked')) {
        $.ajax({
            url: baseUrl + "admin/adminLogin",
            type: "get",
            dataType: "json",
            data: {username: $(".user").val(), password: $(".password").val()},
            success: function (data) {
                var code = data.code;
                //   alert(code);
                if (code === "200") {
                    alert("登陆成功");
                    $.cookie('username',$(".user").val());
                    alert("username cookie is set");
                    newURL = baseUrl + "admin_main_page.html";
                    $(window).attr('location',newURL);
                } else {
                    alert("账号或密码错误");
                }

            },
            error: function () {
                alert("登录失败,检查一下网络吧");
            }
        });
    }

    //以普通用户身份登录
    if ($('#user').is(':checked')) {
        // do something
        $.ajax({
            url: baseUrl + "user/userLogin",
            type: "get",
            dataType: "json",
            data: {username: $(".user").val(), password: $(".password").val()},
            success: function (data) {
                var code = data.code;
                //   alert(code);
                if (code === "200") {
                    alert("登陆成功");
                    $.cookie('username',$(".user").val());
                    alert("username cookie is set");
                    newURL = baseUrl + "user_main_page.html";
                    $(window).attr('location',newURL);
                } else {
                    alert("账号或密码错误");
                }
            },
            error: function () {
                alert("登录失败,检查一下网络吧");
            }
        });
    }
})