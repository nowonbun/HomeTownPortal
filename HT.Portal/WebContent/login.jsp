<%@page import="HT.Portal.Servlet.InstanceServlet"%>
<%@page import="HT.Portal.common.PropertyMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	InstanceServlet.get().login(request, response);
%>
<!DOCTYPE html>
<html>
<head>  
	<jsp:include page="./common/header.jsp"></jsp:include>
	<link href="./css/login.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <div class="row">
            <div class="col-md-4 col-md-offset-4">
                <div class="login-panel panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Sign In</h3>
                    </div>
                    <div class="panel-body">
                        <form role="form" id="loginform" method="get" action="https://accounts.google.com/o/oauth2/auth">
                            <input type="hidden" name="scope" value="https://www.googleapis.com/auth/plus.login https://www.googleapis.com/auth/plus.me" />
				            <input type="hidden" name="client_id" value="<%=PropertyMap.getInstance().getProperty("googleApiConfig", "client_id")%>" />
				            <input type="hidden" name="redirect_uri" value="<%=PropertyMap.getInstance().getProperty("googleApiConfig", "redirect_url")%>" />
				            <input type="hidden" name="access_type" value="offline" />
				            <input type="hidden" name="response_type" value="code" />
				            <input type="hidden" name="approval_prompt" value="force" />
				            <input type="image" class="loginBtn" src="./contents/loginBtn.png">
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="./common/footer.jsp"></jsp:include>
</body>
</html>
