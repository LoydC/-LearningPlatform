<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
	<title>实验报告管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		var validateForm;
		function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
		  if(validateForm.form()){
			  $("#inputForm").submit();
			  return true;
		  }
	
		  return false;
		}
		$(document).ready(function() {
			validateForm = $("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			
					laydate({
			            elem: '#endTime', //目标元素。由于laydate.js封装了一个轻量级的选择器引擎，因此elem还允许你传入class、tag但必须按照这种方式 '#id .class'
			            event: 'focus' //响应事件。如果没有传入event，则按照默认的click
			        });
		});
	</script>
</head>
<body class="hideScroll">
		<form:form id="inputForm" modelAttribute="experimentReport" action="${ctx}/experimentreport/experimentReport/save" enctype="multipart/form-data" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>实验报告名称：</label></td>
					<td class="width-35">
						<form:input path="experimentReportName" htmlEscape="false"    class="form-control required"/>
					</td>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>实验报告路径：</label></td>
					<%-- <td class="width-35">
						<form:hidden id="experimentReportPath" path="experimentReportPath" htmlEscape="false" maxlength="64" class="form-control"/>
						<sys:ckfinder input="experimentReportPath" type="files" uploadPath="/experimentreport/experimentReport" selectMultiple="true"/>
					</td> --%>
					<td class="width-35">
						<form name="Form2" action="${ctx}/experimentreport/experimentReport/fileUpload" method="post"  enctype="multipart/form-data">
                        	<input type="file" name="file">
							<input type="submit" value="upload"/>
						</form>
                    </td>
				</tr>
				<tr>
					<%-- <td class="width-15 active"><label class="pull-right">成绩：</label></td>
					<td class="width-35">
						<form:select path="score" class="form-control required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('experiment_score')}" itemLabel="label" itemValue="value" htmlEscape="false"/>							
						</form:select>
					</td> --%>
					<td class="width-15 active"><label class="pull-right"><font color="red">*</font>截止时间：</label></td>
					<td class="width-35">
						<input id="endTime" name="endTime" type="text" maxlength="20" class="laydate-icon form-control layer-date required"
							value="<fmt:formatDate value="${experimentReport.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"/>
					</td>
				</tr>
				<tr>
					<td class="width-15 active"><label class="pull-right">备注信息：</label></td>
					<td class="width-35">
						<form:textarea path="remarks" htmlEscape="false" rows="4"    class="form-control "/>
					</td>
					<td class="width-15 active"></td>
		   			<td class="width-35" ></td>
		  		</tr>
		 	</tbody>
		</table>
	</form:form>
</body>
</html>