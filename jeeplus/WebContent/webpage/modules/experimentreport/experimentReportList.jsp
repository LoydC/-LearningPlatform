<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>实验报告管理</title>
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
		<h5>实验报告列表 </h5>
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
	<form:form id="searchForm" modelAttribute="experimentReport" action="${ctx}/experimentreport/experimentReport/" method="post" class="form-inline">
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
			<shiro:hasPermission name="experimentreport:experimentReport:add">
				<table:addRow url="${ctx}/experimentreport/experimentReport/form" title="实验报告"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="experimentreport:experimentReport:edit">
			    <table:editRow url="${ctx}/experimentreport/experimentReport/form" title="实验报告" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="experimentreport:experimentReport:del">
				<table:delRow url="${ctx}/experimentreport/experimentReport/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="experimentreport:experimentReport:import">
				<table:importExcel url="${ctx}/experimentreport/experimentReport/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="experimentreport:experimentReport:export">
	       		<table:exportExcel url="${ctx}/experimentreport/experimentReport/export"></table:exportExcel><!-- 导出按钮 -->
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
				<th  class="sort-column experimentReportName">实验报告名称</th>
				<th  class="sort-column experimentReportPath">实验报告路径</th>
				<!-- <th  class="sort-column score">成绩</th> -->
				<th  class="sort-column endTime">截止时间</th>
				<!-- <th  class="sort-column remarks">备注信息</th> -->
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="experimentReport">
			<tr>
				<td> <input type="checkbox" id="${experimentReport.id}" class="i-checks"></td>
				<td><a  href="#" onclick="openDialogView('查看实验报告', '${ctx}/experimentreport/experimentReport/form?id=${experimentReport.id}','800px', '500px')">
					${experimentReport.experimentReportName}
				</a></td>
				<td>
					${experimentReport.experimentReportPath}
				</td>
				<%-- <td>
					${fns:getDictLabel(experimentReport.score, 'experiment_score', '')}
				</td> --%>
				<td>
					<%-- ${experimentReport.endTime} --%>
					<fmt:formatDate value="${experimentReport.endTime}" pattern="yyyy-MM-dd"/>
				</td>
				<%-- <td>
					${experimentReport.remarks}
				</td> --%>
				<td>
					<shiro:hasPermission name="experimentreport:experimentReport:view">
						<a href="#" onclick="openDialogView('查看实验报告', '${ctx}/experimentreport/experimentReport/form?id=${experimentReport.id}','800px', '500px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="experimentreport:experimentReport:edit">
    					<a href="#" onclick="openDialog('修改实验报告', '${ctx}/experimentreport/experimentReport/form?id=${experimentReport.id}','800px', '500px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="experimentreport:experimentReport:del">
						<a href="${ctx}/experimentreport/experimentReport/delete?id=${experimentReport.id}" onclick="return confirmx('确认要删除该实验报告吗？', this.href)"   class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
					</shiro:hasPermission>
					<a href="#" onclick="parent.openTab('${ctx}/experimentreport/experimentReport/findAllReport?id=${experimentReport.id}','批改预习报告单',false)" class="btn btn-warning btn-xs" ><i class="fa fa-edit"></i> 批改</a>
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