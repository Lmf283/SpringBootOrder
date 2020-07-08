<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜品管理</title>
 <link rel="stylesheet" href="/css/bootstrap.min.css">
</head>
<body>
<%
	response.setHeader("Access-Control-Allow-Origin", "*");
	
%>

<jsp:include page="header.jsp"></jsp:include>
<jsp:include page="nav.jsp"></jsp:include>
<script src="/js/bootstrap-table.js"></script>
<script src="/js/bootstrap-table-zh-CN.min.js"></script>

<script type="text/javascript">
//页面加载
$(function(){
	 $(".navbar-page-title").html("菜品信息管理页面");
	var url="";
     initTable(url);
     $('#proTable').bootstrapTable('hideColumn', 'productId');
     document.querySelector("#icon").onchange = function(){ 
    	 document.querySelector("#img").src = getFileUrl(this.id);
    	 };
    	 $.ajax({
			type:"get",
			url: '/category/findAllCate',
			async:false,
			dataType: 'json',
			success:function(data){
				console.log(data);
				for(var i=0;i<data.length;i++){
					$("#type").append('<option value="'+data[i].categoryType+'">'+data[i].categoryName+'</option>');
				}
			},
		});
    
 
});
//初始化表格及分页
function initTable(url) {
	if(url==""){
		url="/product/findAll";
	}
    $('#proTable').bootstrapTable('destroy');
    $("#proTable").bootstrapTable({
        url:url,
        pageNumber:1,//当前页数
        pageSize:30,//每页条数
        pagination: true,
        pageList:[10,20,30,40],//如果设置了分页，设置可供选择的页面数据条数。设置为All 则显示所有记录。
        sidePagination:"server",
        method:'get',//发送请求的方式
        contentType:"application/x-www-form-urlencoded",//必须的否则条件查询时会乱码
        queryParamsType:' ',
        queryParams:queryParams,
        search: false, 
        toolbar:'#toolbar',//工具栏
        toolbarAlign:'left',//工具栏的位置
        columns: [{
            checkbox: true
       		 },{
                field: 'productId',
                title: '菜品编号',
                width: '10%'
            },{
                field: 'categoryType',
                title: '分类编号',
                width: '35%'
            },{
                field: 'productName',
                title: '菜品名字',
                width: '5%'
            },{
                field: 'productPrice',
                title: '菜品价格',
                width: '15%'
            },{
                field: 'productIcon',
                title: '菜品图片',
                width: '10%',
                formatter:function(value, row, index){
                	if(value!=null){
                		return '<img style="width:50px;height:50px" src="'+ value+'" />';
                	}
                }
            },{
            	field: 'productDescription',
                title: '菜品描述',
                width: '15%'
            },{
                field: 'productStock',
                title: '菜品库存',
                width: '15%'
            },{
                field: 'productStatus',
                title: '状态',
                width: '10%',
                formatter:function(value, row, index){
                    if("1"==value){
                        return '下架';
                    }
                    return '上架';
                }
            },
            {
                field: '',
                title: '上/下架',
                width: '15%',
                formatter:function(value, row, index){
                	var status=row.productStatus;
             		var op='';
             		if(status=="1"){
             			op="上架";
                 		return '<button class="btn btn-primary" type="button" id="upOrdown" onclick="upOrdown(\''+row.productId+'\')">'+op+'</button>';
             		}else{
             			op="下架";
                 		return '<button class="btn btn-secondary" type="button" id="upOrdown" onclick="upOrdown(\''+row.productId+'\')">'+op+'</button>';
             		}
                }
            },{
             	field:'',
             	title:'操作',
             	width:'10%',
             	formatter:function(value, row, index){
             		var status=row.productStatus;
             		var op='';
             		if(status=="1"){
             			op="下架";
             		}else{
             			op="上架";
             		}
             		return '<button class="btn btn-primary" onclick="updatePro(\''+row+'\','+index+')"  data-toggle="modal" data-target="#proModal">修改';
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
//条件查询
 function refresh() {
    	
    	window.location.reload();
	}
function updatePro(row,index) {
	$("#myModalLabel").html("修改菜品信息");
	 var data = JSON.stringify($("#proTable").bootstrapTable('getData'));
	 var data_json = JSON.parse(data);
	 console.log(data_json);
	var id = data_json[index].productId;
	 var type = data_json[index].categoryType;
	 var name = data_json[index].productName;
	 var price = data_json[index].productPrice;
	 var icon = data_json[index].productIcon;
	 var description = data_json[index].productDescription;
	 var stock = data_json[index].productStock;
	 var status = data_json[index].productStatus;
	 
	 $('#id').val(id);
	  $('#type').val(type);
     $('#name').val(name);
     $('#price').val(price);
     $('#img').attr("src",icon);
     $('#description').val(description);
     $('#stock').val(stock);
     $('#status').val(status);
}
function delPro(obj) {
    var checked = [];   //定义一个空数组
    var $chkBoxes = $('#proTable').find('input:checked'); 
    //找到被选中的checkbox集
    
    if ($chkBoxes.length == 0) {         //如果不勾选弹出警告框
      alert('请至少选择一项要删除的内容');
      return false;
    }
    var data = JSON.stringify($("#proTable").bootstrapTable('getData'));
    var data_json = JSON.parse(data);
    //遍历被选中的checkbox集
    $('input:checkbox:checked').each(function() {
    var index = $(this).attr("data-index")
    var id = data_json[index].productId;
	checked.push(id);
	});
    alert(checked);
 	debugger
	$.ajax({
		type:"delete",
		url:'/product/product?id='+checked,
		success:function(){
			refresh();
		}
	});
}

function openNew(obj) {
	$("#myModalLabel").html("新增菜品信息");
	$("input").val("");
	$("#img").removeAttr("src");
	$("#status option[value='0']").prop("selected",true);
	
	
}
function addPro(){
	var select = document.getElementById("status");
	var formData = new FormData();
	debugger;
	formData.append( "img", $('#img').attr("src"));
	formData.append("icon",$("#icon")[0].files[0]);
	formData.append( "type",$('#type').val());
	formData.append( "name",$('#name').val());
	formData.append( "price",$('#price').val());
	formData.append( "description",$('#description').val());
	formData.append("stock",$('#stock').val());
	formData.append( "status",select.value);
	formData.append("id",$('#id').val());
	debugger;
	 $.ajax({
         type: 'post',
         url: '/product/product',
         processData:false,
         contentType: false,
         cache: false,
         data: formData,
         success: function (data) {
        	
       },
       error:function(){
    	   
       }
     });
	 
}

function fileSelect() {
	$("#icon").click();
}

function getFileUrl(sourceId) {
	debugger;
	var url;
	if (navigator.userAgent.indexOf("MSIE")>=1) { // IE 
		url = document.getElementById(sourceId).value; 
		} else if(navigator.userAgent.indexOf("Firefox")>0) { // Firefox
			url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0)); 
			} else if(navigator.userAgent.indexOf("Chrome")>0) { // Chrome 
				url = window.URL.createObjectURL(document.getElementById(sourceId).files.item(0)); 
				}
	return url;
}

function find() {
	var name=$("#findContent").val();
	debugger;
	var url="/product/findByName?name="+name;
	$.ajax({
		type:'get',
		url:url,
		success:function(data){
			console.log(data);
			$('#proTable').bootstrapTable('removeAll');
			$('#proTable').bootstrapTable('append',data);
		}
	});
	
	
}

function upOrdown(id) {
	var status = $("#upOrdown").val();
	
	if(status == "上架"){
		$("#upOrdown").val("下架");
	}else if (status == "下架") {
		$("#upOrdown").val("上架");
	}
	$.ajax({
		type:"post",
		url:"/product/upOrdown?id="+id,
		success:function(){
			refresh();
		}
	});
}
</script> 
       <div class="lyear-layout-content">
       <div class="container-fluid">
       <!-- 工具栏 -->
       <div >
       	<button class="btn btn-primary" data-toggle="modal" data-target="#proModal" onclick="openNew(this)">新增</button>
        <button class="btn btn-danger" onclick="delPro(this)">删除</button>
        <form class="pull-right search-bar" method="get" action="#!" role="form">
             <div class="input-group">
               <div class="input-group-btn">
                 <input type="hidden" name="search_field" id="search-field" value="title">
                 <button class="btn btn-default dropdown-toggle" id="search-btn"  type="button" onclick="find()">
               	  搜索 
                 </button>
               </div>
               <input type="text" class="form-control" value="" name="keyword" placeholder="请输入菜品名称" id="findContent">
             </div>
           </form>
       </div>
        
        <div class="modal fade" id="proModal" role="dialog" aria-hidden="true">
          <div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
						&times;
					</button>
					<h4 class="modal-title" id="myModalLabel">
						编辑菜品信息
					</h4>
				</div>
				<div class="modal-body">
					<form id="form_data"  enctype="multipart/form-data" method="post">
						<input type="hidden" name="id" id="id" placeholder="id" class="name form-control" required />
						菜品分类：
						<div class="form-controls">
		                      <select name="type" class="form-control" id="type">
		                        
		                      </select>
		                    </div>
<!-- 						<input type="text" name="type" id="type" placeholder="编号" class="name form-control" required />
 -->						菜品名称：<input type="text" name="name" id="name" placeholder="名称" class="name form-control" required />
						菜品价格：<input type="text" name="price" id="price" placeholder="价格" class="name form-control" required />
						菜品图片：
						<div >
						<img  id="img" name="img" width="100%" onclick="fileSelect()" style="width: 20%;height: 30%" required/>
						<input type="file" name="icon" id="icon" placeholder="图片"/>
						</div>
						菜品描述：<input type="text" name="description" id="description" placeholder="描述" class="name form-control" required />
						菜品库存：<input type="text" name="stock" id="stock" placeholder="库存" class="name form-control" required />
						菜品状态：
							<div class="form-controls">
		                      <select name="status" class="form-control" id="status">
		                        <option value="0">上架</option>
		                        <option value="1">下架</option>
		                      </select>
		                    </div>
					
					<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭
					</button>
					<button type="submit" class="btn btn-primary" onclick="addPro()">
						提交
					</button>
					</form>
					</div>
					
				</div>
				
			</div><!-- /.modal-content -->
		</div><!-- /.modal -->
	 </div>
  <table class="table table-bordered" id="proTable" ></table>
</div>
<!--End 页面主要内容-->
    </div>

</body>
</html>