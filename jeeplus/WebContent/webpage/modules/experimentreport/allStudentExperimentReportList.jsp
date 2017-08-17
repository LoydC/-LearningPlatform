<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>预习报告单管理</title>
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
		<h5>学生实验报告列表 </h5>
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
				<th  class="sort-column backupFieldb">学生姓名</th>
				<th  class="sort-column score">成绩</th>
				<th  class="sort-column backupFielda">下载学生实验报告</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="reportForm">
			<tr>
				<td> <input type="checkbox" id="${experimentReport.id}" class="i-checks"></td>	
				<td>
					${experimentReport.backupFieldb}
				</td>			
				<td>
					${fns:getDictLabel(experimentReport.score, 'experiment_score', '')}
				</td>
				<td>
					${experimentReport.backupFielda}
				</td>
				<td>
					<button type="submit">提交</button>
					<!--<shiro:hasPermission name="preview:reportForm:view">
						<a href="#" onclick="openDialogView('查看预习报告单', '${ctx}/preview/reportForm/studentForm?id=${reportForm.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>-->
					<%-- <shiro:hasPermission name="preview:reportForm:edit">
    					<a href="#" onclick="openDialog('批改预习报告单', '${ctx}/preview/reportForm/form?id=${reportForm.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 打分</a>
    				</shiro:hasPermission> --%>
    				<!--<shiro:hasPermission name="preview:reportForm:del">
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