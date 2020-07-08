<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<%@ include file="nav.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单分析</title>
<script type="text/javascript" src="/js/echarts.js"></script>

</head>
<body>
<%-- <jsp:include page="header.jsp"></jsp:include>
<jsp:include page="nav.jsp"></jsp:include> --%>
    <div class="lyear-layout-content">
      <div>
      	<select name="type" class="form-control" id="type">
          <option value="0" selected="selected">本周</option>
          <option value="1">本月</option>
          <option value="2">本季</option>
          <option value="3">本年</option>
        </select>
      </div>
      <div id="mainLeft" style="width: 400px;height:400px;float: left;"> </div>
      <div id="mainRight" style="width: 400px;height:400px;float: right;"> </div>
    </div>

<script type="text/javascript">

function callbackFn(myChart,jsonURL){
	var text;
	var subtext;
	if(jsonURL.indexOf("cate")>=0){
		text="点餐系统菜品分类分析";
		 subtext="菜品分类分析"; 
	}else{
		 text="点餐系统订单分析";
		 subtext="订单分析"; 
	}
	$.ajax({
		 url:jsonURL,
         dataType: "json",
         success:function(jsonData){
	         var data=[];
	         for(var i=0;i<jsonData.name.length;i++){
	        	 if(jsonData.value[i]>0){
	        		 var obj=new Object();
	                 obj.name=jsonData.name[i]; 
	                 obj.value=jsonData.value[i];
	                 data[i]=obj;
	        	 }
	        	 
	         }
	         myChart.setOption({
	        	 
	        	 title : {

	 	            text: text,
	 	            subtext: subtext,
	 	            x:'center'
	 	        },
		        series: [{
		            // 根据名字对应到相应的系列
		           
		            data: data
		            
		        }],
	           legend:{
	        	    data: jsonData.name
	         }
		    });
		    // 设置加载等待隐藏
		    myChart.hideLoading();
         }
     });
}
// 初始化echar报表的方法
function initReport(myChart){
	
	// 显示标题，图例和空的坐标轴
	myChart.setOption({
	   title : {

	            text: '',
	            subtext: '',
	            x:'center'
	        },
	    legend:{
	    	
	    	orient:'vertical',
	    	left: 'left',
	    	data:[]
	    },
	    tooltip : {
	            trigger: 'item',
	            formatter: "{a} <br/>{b} : {c} ({d}%)"
	        },
	    series: {
	    	  name:"订单状态",
              type: 'pie',
	          data: [],
	          label:{
	  	    	show:true,
	  	    	formatter: "{b} : {c} ({d}%)",
	  	    	position:"inner"
	  	    }
	    }
	    
	});
}

function createTestReport(showDivId,jsonURL){
	var myChart = echarts.init(document.getElementById(showDivId));
	// 初始化report对象
	initReport(myChart);
	myChart.showLoading({text: '正在努力的读取数据中...'  });
	// 调用后台获取json数据
	callbackFn(myChart,jsonURL);
}
</script> 
<script type="text/javascript">
 $(document).ready(function(){
	 $(".navbar-page-title").html("订单分析页面");
	var value;
    $("#type").change(function(event){
    	 value = $("#type option:selected").val();
         showDivId = 'mainLeft';
    	 jsonURL = "/sell/order?value="+value;
    	 cateDivId = 'mainRight';
    	 jsonCateURL = "/sell/cate?value="+value;
   		createTestReport(showDivId,jsonURL);
	   	createTestReport(cateDivId,jsonCateURL);
    });
    if (!value) {
       	value= $("#type option:selected").val();
   	}
    var showDivId = 'mainLeft';
	var jsonURL = "/sell/order?value="+value;
	var cateDivId = 'mainRight';
	var jsonCateURL = "/sell/cate?value="+value;
	createTestReport(showDivId,jsonURL);
   	createTestReport(cateDivId,jsonCateURL);
});
</script>
</body>
</html>