$(".logout").click(
    function() {
        $.cookie('username', null);
        alert("您已退出");
        $(window).attr('location','http://localhost:8074/');
    }
)