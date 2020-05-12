$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/user/list',
        datatype: "json",
        colModel: [
            {label: '用户ID', name: 'userId', index: "user_id", width: 40, key: true,hidden: true},
            // { label: '用户名', name: 'username', width: 75 },
            {label: '用户名', name: 'username', width: 70},
            {label: '姓名', name: 'name', width: 70},
            {label: '学校名称', name: 'schoolName', sortable: false, width: 70},
            {label: '学校简码', name: 'schoolCode', sortable: false, width: 70},
            {label: '学院名称', name: 'collegeName', sortable: false, width: 70},
            {label: '系名', name: 'department', sortable: false, width: 70},
            {label: '专业', name: 'major', sortable: false, width: 70},
            {label: '工号/学号', name: 'jobNumber', sortable: false, width: 70},
            {label: '身份证号', name: 'idNumber', sortable: false, width: 70},
            {label: '邮箱', name: 'email', width: 80},
            {label: '手机号', name: 'mobile', width: 90},
            {label: '用户身份', name: 'rolename', width: 90},
            {
                label: '状态', name: 'status', width: 50, formatter: function (value, options, row) {
                    return value === 0 ?
                        '<span class="label label-danger">禁用</span>' :
                        '<span class="label label-success">正常</span>';
                }
            },
            {label: '创建时间', name: 'createTime', index: "create_time", width: 80},
            {
                label: '操作', name: 'caozuo', width: 120,
                formatter: function (value, options, row) {
                    let r =
                        '<a title="修改用户" class="btn btn-xs btn-primary" onclick="vm.update(' + row.userId + ')"><i class="fa fa-pencil-square-o"></i></a>' +
                        '<a title="删除用户" class="btn btn-xs btn-primary" onclick="vm.del(' + row.userId + ')" style="margin-left: 5px"><i class="fa fa-trash-o"></i></i></a>' +
                        '<a title="添加角色" class="btn btn-xs btn-primary" onclick="vm.addRole(' + row.userId + ')" style="margin-left: 5px"><i class="fa fa-check"></i></a>' +
                        '<a title="删除角色" class="btn btn-xs btn-primary" onclick="vm.delRole(' + row.userId + ')" style="margin-left: 5px"><i class="fa fa-times"></i></a>';
                    return r;
                }
            }
        ],
        viewrecords: true,
        height: '420',
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
});
var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "deptId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url: "nourl"
        }
    }
};
var ztree;

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            keyword: null
        },
        showList: true,
        title: null,
        roleList: {},
        user: {
            status: 1,
            deptId: null,
            deptName: null,
            roleIdList: []
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.user = {deptName: null, deptId: null, status: 1, roleIdList: []};
        },
        update: function (userId) {
            if (userId == null) {
                userId = getSelectedRow();
            }
            if (userId == null) {
                return;
            }

            vm.showList = false;
            vm.title = "修改";

            vm.getUser(userId);
        },
        permissions: function () {
            var userId = getSelectedRow();
            if (userId == null) {
                return;
            }

            window.location.href = baseURL + "sys/permissions/index/" + userId;
        },
        addRole: function (userId) {
            if (userId == null) {
                userId = getSelectedRows();
                if (userId == null) {
                    return;
                }
            }
            layer.open({
                type: 2,
                // offset: ['50px', '100px'], // 弹出位置
                area: ["500px", "310px"],
                title: "选择要添加的角色",
                shade: [0.1, '#000'],
                maxmin: false, //开启最大化最小化按钮
                content: "user_addrole.html?"+userId.toString(),
                scrollbar: false,
                success: function (layero, index) {
                    // let body = layer.getChildFrame('body', index);
                    // console.log(userId.toString())
                    // body.find("#userIds").val(userId.toString());
                },
                end: function(index, layero){
                    vm.reload();
                }
            });
        },
        delRole: function (userId) {
            if (userId == null) {
                userId = getSelectedRows();
                if (userId == null) {
                    return;
                }
            }
            confirm('确定要删除这些用户角色？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/user/deleteRole",
                    data: {userId: userId.toString()},
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function () {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        del: function (userIds) {

            if (userIds == null) {
                userIds = getSelectedRows();
                if (userIds == null) {
                    return;
                }
            }else {
                userIds = [userIds];
            }
            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/user/delete",
                    contentType: "application/json",
                    data: JSON.stringify(userIds),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function () {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        saveOrUpdate: function () {
            var url = vm.user.userId == null ? "sys/user/save" : "sys/user/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.user),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function () {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        getUser: function (userId) {
            $.get(baseURL + "sys/user/info/" + userId, function (r) {
                vm.user = r.user;
                vm.user.password = null;

            });
        },
        excelImp: function (event) {
            var diaindx = layer.open({
                type: 2,
                // offset: ['50px', '100px'], // 弹出位置
                area: ["500px", "310px"],
                title: "选择上传文件",
                shade: [0.1, '#000'],
                maxmin: false, //开启最大化最小化按钮
                content: "user_excel_import.html",
                scrollbar: false,
                btn: ['确定', '关闭'],
                yes: function (index, layero) {
                    //loading层
                    var loadingIndex = layer.load(2, {shade: [0.1, '#fff']});
                    $.get(baseURL + 'sys/user/saveImport/deviceInfo_in_session', function (r) {
                        if (r.code < 0) {
                            layer.close(loadingIndex);
                            alert(r.msg);
                        } else {
                            msgSuccess('导入成功', function (index) {
                                vm.reload();
                                layer.close(loadingIndex);
                                layer.close(diaindx);
                            });
                        }
                    });
                },
                success: function (layero, index) {
                    var iframeWin = layero.find('iframe')[0];
                    if (typeof iframeWin.contentWindow.init == 'function') {
                        iframeWin.contentWindow.init({});
                    }
                },
                cancel: function (index, layero) {
                    // $.get(baseURL + 'dev/device/removeSession/deviceInfo_in_session', function(r) {});
                },
                end: function (index, layero) {
                    // $.get(baseURL + 'dev/device/removeSession/deviceInfo_in_session', function(r) {});
                }
            });
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'keyword': vm.q.keyword},
                page: page
            }).trigger("reloadGrid");
        }
    }
});