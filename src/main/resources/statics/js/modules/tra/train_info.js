$(function () {
    let h = $("#rrapp").height() - $(".grid-btn").height()- $("#jqGridPager").height()  -95;
    $("#jqGrid").jqGrid({
        url: baseURL + 'tra/tramajorcapability/list',
        datatype: "json",
        colModel: [
            {label: 'ID', name: 'id', index: "id", width: 40, key: true,hidden:true},
            {label: '培养方案编号ID', name: 'trainingId', index: "trainingId", width: 40},
            {label: '标准能力编号', name: 'standardCapacityNumber', width: 90},
            {label: '支撑权重', name: 'supportWeight', width: 70},
            {label: '能力编号', name: 'capacityNumber', width: 70},
            {label: '父节点', name: 'parentNode', sortable: false, width: 70},
            {label: '能力描述', name: 'capabilityDescription', sortable: false, width: 70},
            {
                label: '操作', name: 'caozuo', width: 120,
                formatter: function (value, options, row) {
                    let r =
                        '<a title="修改" class="btn btn-xs btn-primary" onclick="vm.update(' + row.id + ')"><i class="fa fa-pencil-square-o"></i></a>' +
                        '<a title="删除" class="btn btn-xs btn-primary" onclick="vm.del(' + row.id + ')" style="margin-left: 5px"><i class="fa fa-trash-o"></i></i></a>'
                    return r;
                }
            }
        ],
        viewrecords: true,
        height: h,
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
            trainingId: ""
        },
        trainingId: parent.location.search.substring(1),
        showList: true,
        title: null,
        roleList: {},
        traMajorCapabilityEntity: {
            trainingId: null,
            standardCapacityNumber: null,
            supportWeight: null,
            capacityNumber: null,
            parentNode: null,
            capabilityDescription: null,
        },
        file: ""
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.traMajorCapabilityEntity= {
                    trainingId: null,
                    standardCapacityNumber: null,
                    supportWeight: null,
                    capacityNumber: null,
                    parentNode: null,
                    capabilityDescription: null,
            }
        },

        importExcel: function () {
            var formData = new FormData();
            formData.append("file", file);
            // formData.append("service",'App.Passion.UploadFile');

            $.ajax({
                url: '"sys/user/importExcel"', /*接口域名地址*/
                type: 'post',
                data: formData,
                contentType: false,
                processData: false,
                success: function (res) {
                    console.log(res.data);
                    if(res.data["code"]=="succ"){
                        alert('成功');
                    }else if(res.data["code"]=="err"){
                        alert('失败');
                    }else{
                        console.log(res);
                    }
                }
            })
        },
        update: function (trainingId) {
            if (trainingId == null) {
                trainingId = getSelectedRow();
            }
            if (trainingId == null) {
                return;
            }

            vm.showList = false;
            vm.title = "修改";

            vm.getUser(trainingId);
        },
        permissions: function () {
            var userId = getSelectedRow();
            if (userId == null) {
                return;
            }

            window.location.href = baseURL + "sys/permissions/index/" + userId;
        },

        del: function (userIds) {

            if (userIds == null) {
                userIds = getSelectedRows();
            }
            // var userIds = getSelectedRows();

            if (userIds == null) {
                return;
            }
            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    // type: "POST",
                    url: baseURL + "tra/tramajorcapability/delete/" + userIds,
                    // contentType: "application/json",
                    // data: JSON.stringify(userIds),
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
            var url = vm.traMajorCapabilityEntity.trainingId == null ? "tra/tramajorcapability/save" : "tra/tramajorcapability/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.traMajorCapabilityEntity),
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
        getUser: function (trainingId) {
            $.get(baseURL + "tra/tramajorcapability/info/" + trainingId, function (r) {
                console.log(r)
                vm.traMajorCapabilityEntity = r.traMajorCapabilityEntity;
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
                content: "train_info_excel_import.html",
                scrollbar: false,
                btn: ['确定', '关闭'],
                yes: function (index, layero) {
                    //loading层
                    var loadingIndex = layer.load(2, {shade: [0.1, '#fff']});

                    vm.reload()
                    // $.get(baseURL + 'sys/user/saveImport/deviceInfo_in_session', function (r) {
                    //     if (r.code < 0) {
                    //         layer.close(loadingIndex);
                    //         alert(r.msg);
                    //     } else {
                    //         msgSuccess('导入成功', function (index) {
                    //             vm.reload();
                    //             layer.close(loadingIndex);
                    //             layer.close(diaindx);
                    //         });
                    //     }
                    // });
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
                postData: {'trainingId': vm.q.trainingId},
                page: page
            }).trigger("reloadGrid");
        }
    }
});