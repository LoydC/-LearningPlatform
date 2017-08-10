<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>学生报告单记录管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
		});
	</script>
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
	<div class="ibox">
	<div class="ibox-title">
		<h5>学生报告单记录列表 </h5>
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
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="studentReportformRecords" action="${ctx}/preview/studentReportformRecords/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<shiro:hasPermission name="preview:studentReportformRecords:add">
				<table:addRow url="${ctx}/preview/studentReportformRecords/form" title="学生报告单记录"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="preview:studentReportformRecords:edit">
			    <table:editRow url="${ctx}/preview/studentReportformRecords/form" title="学生报告单记录" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="preview:studentReportformRecords:del">
				<table:delRow url="${ctx}/preview/studentReportformRecords/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="preview:studentReportformRecords:import">
				<table:importExcel url="${ctx}/preview/studentReportformRecords/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="preview:studentReportformRecords:export">
	       		<table:exportExcel url="${ctx}/preview/studentReportformRecords/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
	       <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
		
			</div>
		<div class="pull-right">
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="search()" ><i class="fa fa-search"></i> 查询</button>
			<button  class="btn btn-primary btn-rounded btn-outline btn-sm " onclick="reset()" ><i class="fa fa-refresh"></i> 重置</button>
		</div>
	</div>
	</div>
	
	<!-- 表格 -->
	<table id="contentTable" class="table table-striped table-bordered table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column reportForm">实验报告单</th>
				<th  class="sort-column spendTime">学习花费时间</th>
				<th  class="sort-column learnDifficulty">学习难度</th>
				<th  class="sort-column learnTarget">学习目标完成度</th>
				<th  class="sort-column existProblem">存在问题</th>
				<th  class="sort-column advice">建议</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="studentReportformRecords">
			<tr>
				<td> <input type="checkbox" id="${studentReportformRecords.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看学生报告单记录', '${ctx}/preview/studentReportformRecords/form?id=${studentReportformRecords.id}','800px', '500px')">
					${studentReportformRecords.reportForm}
				</a></td>
				<td>
					${studentReportformRecords.spendTime}
				</td>
				<td>
					${fns:getDictLabel(studentReportformRecords.learnDifficulty, 'learning_difficulty', '')}
				</td>
				<td>
					${fns:getDictLabel(studentReportformRecords.learnTarget, 'learning_target_degree', '')}
				</td>
				<td>
					${studentReportformRecords.existProblem}
				</td>
				<td>
					${studentReportformRecords.advice}
				</td>
				<td>
					<shiro:hasPermission name="preview:studentReportformRecords:view">
						<a href="#" onclick="openDialogView('查看学生报告单记录', '${ctx}/preview/studentReportformRecords/form?id=${studentReportformRecords.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="preview:studentReportformRecords:edit">
    					<a href="#" onclick="openDialog('修改学生报告单记录', '${ctx}/preview/studentReportformRecords/form?id=${studentReportformRecords.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="preview:studentReportformRecords:del">
						<a href="${ctx}/preview/studentReportformRecords/delete?id=${studentReportformRecords.id}" onclick="return confirmx('确认要删除该学生报告单记录吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
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
	<table border="1">
     <script>
        var reportData={
			reportformID:"101",
			leaningcontent:"学习内容",
			leaningData:[
				{
					learningResource:"s10101001信息",
					learningTarget:"理解",
					questionAndThinking:"问题",
					doubt:"",
				},
				{
					learningResource:"s10101001信息",
					learningTarget:"理解",
					questionAndThinking:"问题",
					doubt:"",
				}
			],
			practiceDutyData:[
				{
					practiceDuty:"信息检索",
					contentAndTarget:"",
					doubt:""
				},
				{
					practiceDuty:"信息检索",
					contentAndTarget:"",
					doubt:""
				}
			],
			leaningfeedback:"学习反馈",
			learningfeedbackData:{
				spendTime:"",
				learnDifficulty:"",
				learnTarget:"",
				existProblem:"",
				advice:""
			}
	}
        var s = '';
        s='<h4>学习任务单</h4>'
        
        
            if (reportData.reportformID) {
                s += '<tr><th>任务单编号</th><th>' + reportData.reportformID 
                + '</th><th>学习内容</th><th colspan="2">'+reportData.leaningcontent+'</th></tr>';
            }
            	s += '<tr><th colspan="5">课前学习任务</th></tr><tr><th>学习资源</th><th>学习目标</th><th colspan="2">问题与思考</th><th>疑问</th></tr>';
            	if(reportData.leaningData.length>0){
            		for(var i = 0; i < reportData.leaningData.length; i++){
            			s += '<tr><th>'
            			+ reportData.leaningData[i].learningResource + '</th><th>'
            			+ reportData.leaningData[i].learningTarget + '</th><th colspan="2">'
            			+ reportData.leaningData[i].questionAndThinking + '</th><th><input type="text">'
            			+ reportData.leaningData[i].doubt 
            			+ '</th></tr>'
            		}           	
            	}
            	s += '<tr><th colspan="5">实习前学习任务</th></tr><tr><th>实习任务</th><th colspan="3">内容与目标</th><th>疑问</th></tr>';
            	if(reportData.practiceDutyData.length>0){
            		for(var i = 0; i < reportData.practiceDutyData.length; i++){
            			s += '<tr><th>'
            			+ reportData.practiceDutyData[i].practiceDuty + '</th><th colspan="3">'
            			+ reportData.practiceDutyData[i].contentAndTarget + '</th><th><input type="text">'
            			+ reportData.practiceDutyData[i].doubt 
            			+ '</th></tr>'            	        	
            	}
            	s += '<tr><th colspan="5">学习反馈</th></tr><tr><th>学习花费时间</th><th>学习难度</th><th>学习目标完成度</th><th>存在问题</th><th>建议</th></tr>';
            	s += '<tr><th><input type="text">'
            			+ reportData.learningfeedbackData.spendTime + '</th><th><input type="text">'
            			+ reportData.learningfeedbackData.learnDifficulty + '</th><th><input type="text">'
            			+ reportData.learningfeedbackData.learnTarget + '</th><th><input type="text">'
            			+ reportData.learningfeedbackData.existProblem + '</th><th><input type="text">'
            			+ reportData.learningfeedbackData.advice
            			+ '</th></tr>'            	         
            }
      
        document.write(s);
    </script> 
</table>
</div>
</body>
</html>