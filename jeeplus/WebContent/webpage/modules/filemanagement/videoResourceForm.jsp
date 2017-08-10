<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/webpage/include/taglib.jsp"%>
<html>
<head>
    <title>教学资源增删改查管理</title>
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
                    form.submit();
                    parent.openTab("${ctx}/video/play","视频播放",false);
                }
            });
            
        });
    </script>
</head>
<body class="hideScroll">
        <form:form id="inputForm" modelAttribute="educationResource" action="${ctx}/filemanagement/fileManagement" target="_blank" method="post" class="form-horizontal">
        <form:hidden path="id"/>
        <sys:message content="${message}"/> 
        <table class="table table-bordered  table-condensed dataTables-example dataTable no-footer">
           <tbody>
                <tr>
                    <td class="width-15 active"><label class="pull-right"><font color="red">*</font>资源名称：</label></td>
                    <td class="width-35">
                        <form:input path="resourceName" htmlEscape="false"    class="form-control required"/>
                    </td>
                </tr>
                <tr>
                    <td class="width-15 active"><label class="pull-right"><font color="red">*</font>资源类型：</label></td>
                    <td class="width-35">
                        <form:select path="type" class="form-control required">
                            <form:option value="" label=""/>
                            <form:options items="${fns:getDictList('resource_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
                        </form:select>
                    </td>
                </tr>
                <tr>
                    <td class="width-15 active"><label class="pull-right">知识点：</label></td>
                    <td class="width-35">
                        <form:textarea path="konwledgePoint" htmlEscape="false" rows="4"    class="form-control "/>
                    </td>
                </tr>
                <tr>
                    <td class="width-15 active"><label class="pull-right">内容介绍：</label></td>
                    <td class="width-35">
                        <form:textarea path="introduction" htmlEscape="false" rows="4"    class="form-control "/>
                    </td>
                </tr>
                <tr>
                    <td class="width-15 active"><label class="pull-right">关键词：</label></td>
                    <td class="width-35">
                        <form:textarea path="keyWords" htmlEscape="false" rows="4"    class="form-control "/>
                    </td>
                </tr>
            </tbody>
        </table>
    </form:form>
</body>
</html>