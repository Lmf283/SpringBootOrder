<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<%@ include file="nav.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="/css/bootstrap-table.css">
</head>
<body>
<%-- <jsp:include page="header.jsp"></jsp:include>
<jsp:include page="nav.jsp"></jsp:include> --%>
<script src="/js/bootstrap-table.js"></script>
<script src="/js/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript">
$(function(){
	$(".navbar-page-title").html("订单管理页面");
    initTable();
    $('#proTable').bootstrapTable('hideColumn', 'productId');
});
//初始化表格及分页
function initTable() {
   $('#orderTable').bootstrapTable('destroy');
   $("#orderTable").bootstrapTable({
       url:"/order/findAll",
       pagination:true,//开启分页
       paginationLoop:false,
       pageNumber:1,//当前页数
       pageSize:30,//每页条数
       pageList:[5,10,15,20],//如果设置了分页，设置可供选择的页面数据条数。设置为All 则显示所有记录。
       sidePagination:"server",
       method:'get',//发送请求的方式
       contentType:"application/x-www-form-urlencoded",//必须的否则条件查询时会乱码
       queryParams:queryParams,
       search: false,
       columns: [
       	{   
       		checkbox: true
       		
       	},{
               field: 'orderId',
               title: '订单编号',
               width: '10%'
           },{
               field: 'buyerName',
               title: '买家名称',
               width: '35%'
           },{
               field: 'buyerOpenid',
               title: '买家openid',
               width: '5%'
           },{
               field: 'orderAmount',
               title: '总计',
               width: '15%'
           },{
               field: 'payStayus',
               title: '支付状态',
               width: '10%',
              formatter:function(value, row, index){
                   if("1"==value){
                       return '支付成功';
                   }
                   return '等待支付';
               }
           },{
           	field: 'buyerPhone',
               title: '买家电话',
               width: '15%'
           },{
               field: 'buyerAddress',
               title: '买家地址',
               width: '15%'
           },{
               field: 'orderDetailList',
               title: '详情',
               width: '10%',
               formatter:function(value,row,index){
            	   var id = row.orderId;
              		return '<a href="/buyer/findOrder?id='+id+'">查看详情</a>';
              		
              	}
           },{
               field: 'orderStatus',
               title: '操作',
               width: '10%',
               formatter:function(value,row,index){
            	   var str = JSON.stringify(row);
            	   console.log(value);
            	   if(value=="0"){
					return '<button class="btn btn-primary" type="button" id="finish" onclick="finish(\''+row.orderId+'\')">完成订单</button>';

            	   }
            	   if (value=="1") {
   					return '已完成';
					}
            	   if(value=="2")
              		return '已取消';
              	}
           }]
   })
}
//得到查询的参数
function queryParams(params) {
   var temp = {
       page:this.pageNumber,
       rows:this.pageSize,
   };
   return temp;
};

function find() {
	var name = $("#findContent").val();
	debugger;
	var url="/order/findByName?name="+name;
	$.ajax({
		type:'get',
		url:url,
		success:function(data){
			console.log(data);
			$('#orderTable').bootstrapTable('removeAll');
			for(var i=0;i<data.length;i++){
				$('#orderTable').bootstrapTable('append',data[i]);
			}
			
		}
	});
}

function enterPress(e) {
	e=e||event; 
	if(e.keyCode == 13){
		find();
	}
}

function finish(id){
	debugger
	$.ajax({
		type:"post",
		url:"/order/finish?id="+id,
		success:function(){
			refresh();
		}
	});
}

function refresh(){
	window.location.reload();
}
//条件查询
</script>
    <div class="lyear-layout-content">
       <div class="container-fluid">
         <div id="toolbar">
         	<!-- <button id="search-btn" type="button" onclick="find()" >
               	  搜索 
                 </button>
         	
         	<input type="text" value="" name="keyword" placeholder="请输入买家名称" id="findContent" style="width:200px;display: inline;">
       	 -->
	 		
               <div class="input-group" style="width:215px;float: right; " >
                 <input type="text" class="form-control" placeholder="请输入买家名称" id="findContent" onkeydown="enterPress(e)">
                 <span class="input-group-btn">
                   <button class="btn btn-default" type="button" onclick="find()">搜索</button>
                 </span>
               </div>
             
       	</div>
        <!--新订单  -->
        <table id="orderTable"></table>
       </div>
    <!--End 页面主要内容-->
    </div>

</body>
</html>