$(function () {
    let h = $("#rrapp").height() - $(".grid-btn").height() - $("#jqGridPager").height() - 95;
    $("#jqGrid").jqGrid({
        url: baseURL + 'tra/major/capability/list',
        datatype: "json",
        colModel: [
            {label: '专业能力流水号', name: 'id', index: "id", width: 40, key: true, hidden: true},
            {label: '标准能力编号', name: 'standardCapacityNumber', width: 70},
            {label: '支撑权重', name: 'supportWeight', width: 70},
            {label: '能力编号', name: 'capacityNumber', sortable: false, width: 70},
            {label: '父节点', name: 'parentNode', sortable: false, width: 70},
            {label: '能力描述', name: 'capabilityDescription', sortable: false, width: 70},
            {label: '创建时间', name: 'createTime', index: "create_time", width: 80},
            {
                label: '操作', name: 'caozuo', width: 120,
                formatter: function (value, options, row) {
                    let r =
                        '<a title="修改标准能力" class="btn btn-xs btn-primary" onclick="vm.update(' + row.id + ')"><i class="fa fa-pencil-square-o"></i></a>' +
                        '<a title="删除标准能力" class="btn btn-xs btn-primary" onclick="vm.del(' + row.id + ')" style="margin-left: 5px"><i class="fa fa-trash-o"></i></i></a>'
                    return r;
                }
            }
        ],
        postData: {
            'trainingId': vm.trainingId
        },
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
var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            keyword: null
        },
        // trainingId: parent.location.search.substring(1),
        trainingId: parent.vm.trainingId,
        showList: true,
        title: '专业能力',
        roleList: {},
        dataInfo: {
            id: '',
            trainingId: parent.vm.trainingId,
            standardCapacityNumber: '',
            supportWeight: '',
            capacityNumber: '',
            parentNode: '',
            capabilityDescription: ''
        }
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.dataInfo = {
                trainingId:vm.trainingId
            };
        },
        update: function (id) {
            if (id == null) {
                id = getSelectedRow();
            }
            if (id == null) {
                return;
            }

            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id);
        },
        del: function (ids) {
            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "tra/major/capability/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
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
            var url = vm.dataInfo.id == null ? "tra/major/capability/save" : "tra/major/capability/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.dataInfo),
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
        getInfo: function (id) {
            $.get(baseURL + "tra/major/capability/info/" + id, function (r) {
                vm.dataInfo = r.data;
            });
        },
        excelImp: function (event) {
            var diaindx = layer.open({
                type: 2,
                // offset: ['50px', '100px'], // 弹出位置
                area: ["510px", "310px"],
                title: "选择上传文件",
                shade: [0.1, '#000'],
                maxmin: false, //开启最大化最小化按钮
                content: "tra_excel_import.html",
                scrollbar: false,
                btn: ['确定', '关闭'],
                yes: function (index, layero) {
                    //loading层
                    var loadingIndex = layer.load(2, {shade: [0.1, '#fff']});
                    $.get(baseURL + 'tra/excel/saveImport/majorCapability_info_in_session', function (r) {
                        if(r.code<0){
                            layer.close(loadingIndex);
                            alert(r.msg);
                        }else{
                            msgSuccess('导入成功', function(index){
                                vm.reload();
                                layer.close(loadingIndex);
                                layer.close(diaindx);
                            });
                        }
                    });
                },
                success: function (layero, index) {
                    layer.setTop(layero); //重点2
                    var iframeWin = layero.find('iframe')[0];
                    if (typeof iframeWin.contentWindow.init == 'function') {
                        iframeWin.contentWindow.init({});
                    }
                },
                cancel: function (index, layero) {
                    $.get(baseURL + 'tra/excel/removeSession/majorCapability_info_in_session', function (r) {
                    });
                },
                end: function (index, layero) {
                    $.get(baseURL + 'tra/excel/removeSession/majorCapability_info_in_session', function (r) {
                    });
                }
            });
        },
        reload: function () {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    'keyword': vm.q.keyword,
                    'trainingId': vm.trainingId
                },
                page: page
            }).trigger("reloadGrid");
        }
    }
});