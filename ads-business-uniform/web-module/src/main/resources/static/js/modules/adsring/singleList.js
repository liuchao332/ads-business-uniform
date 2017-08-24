/**
 * Created by Administrator on 2017/8/17.
 */
$(function () {
    $('#jqGrid').jqGrid({
        url: baseURL + 'adsring/singleList',
        datatype: "json",
        colModel: [
            { label: '铃音ID', name: 'id', index: "id", width: 45, key: true },
            { label: '订单编码', name: 'orderId', width: 45, key: true },
            { label: '广告名称', name: 'resourceName', width: 75 },
            { label: '广告文案描述', name: 'resourceDesc', width: 90 },
            { label: '广告主编码', name: 'adserId', width: 90 },
            { label: '状态', name: 'status', width: 80, formatter: function(value, options, row){
                return value === 0 ?
                    '<span class="label label-danger">禁用</span>' :
                    '<span class="label label-success">正常</span>';
            }},
            { label: '创建时间', name: 'createTime', index: "createTime", width: 80},
            { label: '更新时间', name: 'updateTime', index: "updateTime", width: 80}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList : [10,30,50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page",
            rows:"limit",
            order: "order"
        },
        gridComplete:function(){
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
});

