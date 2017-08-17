<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>预习报告单管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
	})
	</script>
	<script type="text/javascript">
		function GetDateT()
 			{
			  var d,s;
			  d = new Date();
			 	   
			  return(d);  			  
		} 
		
		function compaertime(id){

			var currenttime = GetDateT();
			currenttime=DateUtil.Format("yyyy/MM/dd",currenttime)
			//var td = td.next("td")[0];
			var endtime = $("#"+id).parent().prev().text();
			endtime = DateUtil.Format("yyyy/MM/dd",endtime);
			
			if(currenttime>endtime){
				document.getElementById(id).style.display="none";
			}
		}
		function DateUtil(){}  
DateUtil.Format=function(fmtCode,date){  
var result,d,arr_d;  

var patrn_now_1=/^y{4}-M{2}-d{2}\sh{2}:m{2}:s{2}$/;  
var patrn_now_11=/^y{4}-M{1,2}-d{1,2}\sh{1,2}:m{1,2}:s{1,2}$/;  

var patrn_now_2=/^y{4}\/M{2}\/d{2}\sh{2}:m{2}:s{2}$/;  
var patrn_now_22=/^y{4}\/M{1,2}\/d{1,2}\sh{1,2}:m{1,2}:s{1,2}$/;  

var patrn_now_3=/^y{4}年M{2}月d{2}日\sh{2}时m{2}分s{2}秒$/;  
var patrn_now_33=/^y{4}年M{1,2}月d{1,2}日\sh{1,2}时m{1,2}分s{1,2}秒$/;  

var patrn_date_1=/^y{4}-M{2}-d{2}$/;  
var patrn_date_11=/^y{4}-M{1,2}-d{1,2}$/;  

var patrn_date_2=/^y{4}\/M{2}\/d{2}$/;  
var patrn_date_22=/^y{4}\/M{1,2}\/d{1,2}$/;  

var patrn_date_3=/^y{4}年M{2}月d{2}日$/;  
var patrn_date_33=/^y{4}年M{1,2}月d{1,2}日$/;  

var patrn_time_1=/^h{2}:m{2}:s{2}$/;  
var patrn_time_11=/^h{1,2}:m{1,2}:s{1,2}$/;  
var patrn_time_2=/^h{2}时m{2}分s{2}秒$/;  
var patrn_time_22=/^h{1,2}时m{1,2}分s{1,2}秒$/;  

if(!fmtCode){fmtCode="yyyy/MM/dd hh:mm:ss";}  
if(date){  
d=new Date(date);  
if(isNaN(d)){  
msgBox("时间参数非法\n正确的时间示例:\nThu Nov 9 20:30:37 UTC+0800 2006\n或\n2006/      10/17");  
return;}  
}else{  
d=new Date();  
}  

if(patrn_now_1.test(fmtCode))  
{  
arr_d=splitDate(d,true);  
result=arr_d.yyyy+"-"+arr_d.MM+"-"+arr_d.dd+" "+arr_d.hh+":"+arr_d.mm+":"+arr_d.ss;  
}  
else if(patrn_now_11.test(fmtCode))  
{  
arr_d=splitDate(d);  
result=arr_d.yyyy+"-"+arr_d.MM+"-"+arr_d.dd+" "+arr_d.hh+":"+arr_d.mm+":"+arr_d.ss;  
}  
else if(patrn_now_2.test(fmtCode))  
{  
arr_d=splitDate(d,true);  
result=arr_d.yyyy+"/"+arr_d.MM+"/"+arr_d.dd+" "+arr_d.hh+":"+arr_d.mm+":"+arr_d.ss;  
}  
else if(patrn_now_22.test(fmtCode))  
{  
arr_d=splitDate(d);  
result=arr_d.yyyy+"/"+arr_d.MM+"/"+arr_d.dd+" "+arr_d.hh+":"+arr_d.mm+":"+arr_d.ss;  
}  
else if(patrn_now_3.test(fmtCode))  
{  
arr_d=splitDate(d,true);  
result=arr_d.yyyy+"年"+arr_d.MM+"月"+arr_d.dd+"日"+" "+arr_d.hh+"时"+arr_d.mm+"分"+arr_d.ss+"秒";  
}  
else if(patrn_now_33.test(fmtCode))  
{  
arr_d=splitDate(d);  
result=arr_d.yyyy+"年"+arr_d.MM+"月"+arr_d.dd+"日"+" "+arr_d.hh+"时"+arr_d.mm+"分"+arr_d.ss+"秒";  
}  

else if(patrn_date_1.test(fmtCode))  
{  
arr_d=splitDate(d,true);  
result=arr_d.yyyy+"-"+arr_d.MM+"-"+arr_d.dd;  
}  
else if(patrn_date_11.test(fmtCode))  
{  
arr_d=splitDate(d);  
result=arr_d.yyyy+"-"+arr_d.MM+"-"+arr_d.dd;  
}  
else if(patrn_date_2.test(fmtCode))  
{  
arr_d=splitDate(d,true);  
result=arr_d.yyyy+"/"+arr_d.MM+"/"+arr_d.dd;  
}  
else if(patrn_date_22.test(fmtCode))  
{  
arr_d=splitDate(d);  
result=arr_d.yyyy+"/"+arr_d.MM+"/"+arr_d.dd;  
}  
else if(patrn_date_3.test(fmtCode))  
{  
arr_d=splitDate(d,true);  
result=arr_d.yyyy+"年"+arr_d.MM+"月"+arr_d.dd+"日";  
}  
else if(patrn_date_33.test(fmtCode))  
{  
arr_d=splitDate(d);  
result=arr_d.yyyy+"年"+arr_d.MM+"月"+arr_d.dd+"日";  
}  
else if(patrn_time_1.test(fmtCode)){  
arr_d=splitDate(d,true);  
result=arr_d.hh+":"+arr_d.mm+":"+arr_d.ss;  
}  
else if(patrn_time_11.test(fmtCode)){  
arr_d=splitDate(d);  
result=arr_d.hh+":"+arr_d.mm+":"+arr_d.ss;  
}  
else if(patrn_time_2.test(fmtCode)){  
arr_d=splitDate(d,true);  
result=arr_d.hh+"时"+arr_d.mm+"分"+arr_d.ss+"秒";  
}  
else if(patrn_time_22.test(fmtCode)){  
arr_d=splitDate(d);  
result=arr_d.hh+"时"+arr_d.mm+"分"+arr_d.ss+"秒";  
}  
else{  
msgBox("没有匹配的时间格式!");  
return;  
}  

return result;  
};  
function splitDate(d,isZero){  
var yyyy,MM,dd,hh,mm,ss;  
if(isZero){  
yyyy=d.getYear();  
MM=(d.getMonth()+1)<10?"0"+(d.getMonth()+1):d.getMonth()+1;  
dd=d.getDate()<10?"0"+d.getDate():d.getDate();  
hh=d.getHours()<10?"0"+d.getHours():d.getHours();  
mm=d.getMinutes()<10?"0"+d.getMinutes():d.getMinutes();  
ss=d.getSeconds()<10?"0"+d.getSeconds():d.getSeconds();  
}else{  
yyyy=d.getYear();  
MM=d.getMonth()+1;  
dd=d.getDate();  
hh=d.getHours();  
mm=d.getMinutes();  
ss=d.getSeconds();    
}  
return {"yyyy":yyyy,"MM":MM,"dd":dd,"hh":hh,"mm":mm,"ss":ss};    
}  
function msgBox(msg){  
window.alert(msg);  
}
</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>预习报告单列表 </h5>
		<div class="ibox-tools">
			<a class="collapse-link">
				<i class="fa fa-chevron-up"></i>
			</a>
			<a class="dropdown-toggle" data-toggle="dropdown" href="#">
				<i class="fa fa-wrench"></i>
			</a>
			<ul class="dropdown-menu dropdown-user">
				<li><a href="#">选项1</a>
				</li>
				<li><a href="#">选项2</a>
				</li>
			</ul>
			<a class="close-link">
				<i class="fa fa-times"></i>
			</a>
		</div>
	</div>
    
    <div class="ibox-content">
	<sys:message content="${message}"/>
	

	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column experimentReportName">名称</th>
				<th  class="sort-column experimentReportPath">下载实验报告</th>
				<th  class="sort-column backupFielda">上传实验报告</th>
				<th  class="sort-column score">成绩</th>
				<th  class="sort-column endTime">截止时间</th>
				<!-- <th  class="sort-column remarks">备注信息</th> -->
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="experimentReport" varStatus="status">
			<tr>							
				<td> <input type="checkbox" id="${experimentReport.id}" class="i-checks"></td>
				<td>
					${experimentReport.experimentReportName}
				</a></td>
				<td>
					${experimentReport.experimentReportPath}
				</td>
				<td>
					<input type="file" name="file"><input type="submit" value="upload"/>${experimentReport.backupFielda}
				</td>
				<td>
					${fns:getDictLabel(experimentReport.score, 'experiment_score', '')}
				</td>
				<td>
					<%-- ${experimentReport.endTime} --%>
					<fmt:formatDate value="${experimentReport.endTime}" pattern="yyyy-MM-dd"/>
				</td>
				<td style="display:none">
					${reportForm.endTime}
				</td>
				<%-- <td>
					${experimentReport.remarks}
				</td> --%>
				<td>
					<button type="submit">提交</button>
						<%-- <button id="${status.index}" onclick="parent.openTab('${ctx}/preview/reportForm/studentForm?id=${reportForm.id}','填写预习报告单',false)" class="btn btn-success btn-xs" display="block"><i class="fa fa-search-plus"></i> 填写</button>
						id="${status.index}"
						<script type="text/javascript">
							/* 判断是否在截止时间之前 */
							compaertime("${status.index}");
							var score = "${fns:getDictLabel(reportForm.score, 'preview_score', '')}";
							/*老师批改之后不得提交  */
							function iscorrect(){
								if(score!=""){
									document.getElementById(${status.index}).style.display="none";
								}
							}  
							iscorrect();
						</script> --%>
					
					<!--<shiro:hasPermission name="preview:reportForm:edit">
    					<a href="#" onclick="openDialog('修改预习报告单', '${ctx}/preview/reportForm/form?id=${reportForm.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="preview:reportForm:del">
						<a href="${ctx}/preview/reportForm/delete?id=${reportForm.id}" onclick="return confirmx('确认要删除该预习报告单吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>-->
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
	<br/>
	</div>
	</div>
</div>

</body>
</html>