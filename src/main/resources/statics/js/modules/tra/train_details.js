var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        trainingId: decodeURI(location.search.substring(1).split("&")[0]),
        trainingProgram: decodeURI(location.search.substring(1).split("&")[1]),
        version: decodeURI(location.search.substring(1).split("&")[2]),
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
        jump: function (src, event) {
            $("#title").html(event.currentTarget.innerHTML);
            $("#details_iframe").attr("src", src);
        }
    }
});