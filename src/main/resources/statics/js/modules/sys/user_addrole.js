layui.use('form', function () {
    var form = layui.form();
    // var userIds = $("#userIds").val();
    var roleId = null;
    $("#back").click(function (){
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    });
    $("#add").click(function () {
        $.ajax({
            type: "POST",
            url: baseURL + "sys/user/addRole",
            data:{
                'userIds':location.search.substring(1),
                'roleId':roleId
            },
            success: function (r) {
                if (r.code == 0) {
                  alert("操作成功",function () {
                      var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                      parent.layer.close(index); //再执行关闭
                  });
                } else {
                    alert(r.msg);
                }
            }
        });
    })
    form.on('radio', function(data){
        roleId = data.value; //被点击的radio的value值
    });
    $.get(baseURL + "sys/role/all/list", function (r) {
        if (r.code == 0) {
            let content = '';
            for (let i = 0; i < r.list.length; i++) {
                if (i % 3 == 0) {
                    content += '<tr>';
                }
                content += '<td><input type="radio" name="role"  value="' + r.list[i].roleId + '" title="' + r.list[i].roleName + '"></td>';
                if (i % 3 == 2 || i == r.list.length - 1) {
                    content += '</tr>';
                }
            }
            if (r.list.length == 0) {
                $("#title").attr("color", "#fd0000")
                $("#title").html("请先创建角色！")
            } else {
                $("#roleTable").html(content);
                form.render();
            }
        }
    });
});