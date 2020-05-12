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
        back: function () {
            parent.vm.navTitle = "培养方案";
            $("iframe", window.parent.document).attr("src", "modules/tra/train.html");
        },
        jump: function (src) {
            $("#details_iframe").attr("src", src);
        }
    }
});