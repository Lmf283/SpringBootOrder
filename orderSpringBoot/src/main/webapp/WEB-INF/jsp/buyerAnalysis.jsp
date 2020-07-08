<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

<title>买家个性化分析</title>
<script type="text/javascript" src="/js/jquery.min.js"></script>
<script type="text/javascript" src="/js/echarts.js"></script>
<style type="text/css">
.pro-name{
display: inline-block;
margin-right: 20px;

}
.pro-value{
display: inline-block;
margin-left: 20px;
}
#sort{
margin-left: 20px;
}
</style>
</head>
<body>
	<div id="analysisDiv" style="width: 100%;height:400px;">
	</div>
	<div id="sort" style="width: 100%;height:400px;"></div>
	<div id="test" style="width: 100%;height:400px;"></div>
<script type="text/javascript">
function callbackFn(myChart,jsonURL){
	var Jsondata =${buyerAnalysis};
	var data=[];
	for(var i=0;i<Jsondata.name.length;i++){
		if(Jsondata.value[i]>0){
			var obj=new Object();
	        obj.name=Jsondata.name[i]; 
	        obj.value=Jsondata.value[i];
	        data[i]=obj;
		}
    }
	myChart.setOption({
	       series: [{
	           // 根据名字对应到相应的系列
	           data: data
	           
	       }],
	      legend:{
	   	    data: Jsondata.name
	    }
	   });
	   // 设置加载等待隐藏
	 myChart.hideLoading();
}
function initReport(myChart){
		
		// 显示标题，图例和空的坐标轴
		myChart.setOption({
		   title : {

		            text: '用户个性化分析',
		            subtext: '菜品的喜爱度',
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
	
	function createTestReport(showDivId,JsonURL){
		var myChart = echarts.init(document.getElementById(showDivId));
		// 初始化report对象
		initReport(myChart);
		myChart.showLoading({text: '正在努力的读取数据中...'  });
		// 调用后台获取json数据
		callbackFn(myChart,JsonURL);
	}
	</script>
<script type="text/javascript">
$(document).ready(function(){
	var openid='${openid}';
	var showDivId = 'analysisDiv';
	var jsonURL ="";
	createTestReport(showDivId,jsonURL);
	 $.ajax({
		type:"get",
		url:"/buyer/best?openid="+openid,
	    success:function(data){
	    	for(var i=0;i<data.length;i++){
	    		var str= data[i];
	    		for(var k in str){
	    			$("#sort").append('<div> <div class="pro-name">'+k+'</div> <div class="pro-value">'+str[k]+'</div></div><br />');
	    		}
	    	}
	    	
	    }
	}) 
});
</script>
<script type="text/javascript">

</script>
</body>
</html>