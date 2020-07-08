<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<%@ include file="nav.jsp" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜品分类管理</title>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/main.min.js"></script>
</head>
<!--进入页面加载所有的分类信息  -->
<body>
<script type="text/javascript">
$(function () {
	 $(".navbar-page-title").html("菜品分类信息管理页面");
})
    	function addcate(){
    		
    		 $.ajax({
                 type: 'post',
                 url: '/category/add',
                 dataType: 'json',
                 data: {
                     type: $('#type').val(),
                     name: $('#name').val(),
                     id:$('#id').val(),
                     
                 },
                 success: function (data) {
                	 refreshCate();
                 }
             });
    		
    	}
    function delCate() {
    	
        var checked = [];   //定义一个空数组
        var $chkBoxes = $('#cateTable').find('input:checked');    //找到被选中的checkbox集
        if ($chkBoxes.length == 0) {         //如果不勾选弹出警告框
          alert('请至少选择一项要删除的内容');
          return false;
        }
		
        //遍历被选中的checkbox集
        $('input:checkbox:checked').each(function() {
		checked.push($(this).attr("id"));
		});
     	
		$.ajax({
			type:"delete",
			url:'/category/del?id='+checked,
			success:function(){
				refreshCate();
			}
		});
     	
	}
    function refreshCate() {
    	
    	window.location.reload();
	}
    function clearModal() {
    	$(".modal-body input").val("");
	}
    
    </script>
<script >
    function updateCate(obj){
    	var id = $(obj).parents('tr').children('td').eq(1).text();
    	 var type = $(obj).parents('tr').children('td').eq(2).text();
    	 var name = $(obj).parents('tr').children('td').eq(3).text();
    	 $("#type").val(type);
    	 $("#name").val(name);
    	 $("#id").val(id);
    }
</script>
<%-- <jsp:include page="header.jsp"></jsp:include>
   <jsp:include page="nav.jsp"></jsp:include> --%>
   
    <div class="lyear-layout-content">
       <div class="container-fluid">
        <div class="row">
          <div class="col-lg-12">
            <div class="card">
              <div class="card-toolbar clearfix">
                
                <div class="toolbar-btn-action">
                  <button class="btn btn-primary m-r-5" data-toggle="modal" data-target="#myModal" onclick="clearModal()" ><i class="mdi mdi-plus"></i> 新增</a>
                  <button class="btn btn-danger m-r-5" onclick="delCate()" ><i class="mdi mdi-window-close" ></i> 删除</a>
                   <button class="btn btn-primary " onclick="refreshCate()" ><i class="mdi mdi-replay"></i>刷新</a>
                </div>
                <div   class="modal fade" id="myModal" tabindex="-1" role="dialog">
                	<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
									&times;
								</button>
								<h4 class="modal-title" id="myModalLabel">
									菜品分类编辑
								</h4>
							</div>
							<div class="modal-body">
								<form id="form_data">
									<input type="hidden" name="id" id="id" placeholder="id" class="name form-control" required />
									菜品分类编号：<input type="text" name="type" id="type" placeholder="编号" class="name form-control" required />
									菜品分类名称：<input type="text" name="name" id="name" placeholder="名称" class="name form-control" required />
								</form>
								<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">关闭
								</button>
								<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="addcate()">
									提交
								</button>
								</div>
								
							</div>
							
						</div><!-- /.modal-content -->
					</div><!-- /.modal -->
                </div>
              </div>
              <div class="card-body">
                
                <div class="table-responsive">
                  <table class="table table-bordered" id="cateTable">
                    <thead>
                      <tr>
                        <th>
                          <label class="lyear-checkbox checkbox-primary">
                            <input type="checkbox" id="check-all"><span></span>
                          </label>
                        </th>
                        <th>分类id</th>
                        <th>分类编号</th>
                        <th>分类名称</th>
                        <th>操作</th>
                        
                      </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${cateList}" var="item">
						
                      <tr>
                        <td>
                          <label class="lyear-checkbox checkbox-primary">
                            <input type="checkbox" id="${item.categoryId}" value="10"><span></span>
                          </label>
                        </td>
                        <td>${item.categoryId}</td>
                        <td>${item.categoryType}</td>
                        <td>${item.categoryName}</td>
                        <td>
                          <div class="btn-group">
                            <button id="addcat" class="btn btn-xs btn-default" title="编辑"  data-toggle="modal" data-target="#myModal" onclick="updateCate(this)"><i class="mdi mdi-pencil"></i></a>
                            
                          </div>
                        </td>
                      </tr>
                      </c:forEach>
                    </tbody>
                  </table>
                </div>
              </div>
            </div>
          </div>
        </div>
        
      </div>
    <!--End 页面主要内容-->
    
    </div>
   
    <!--addcate函数  -->

   
</body>
</html>