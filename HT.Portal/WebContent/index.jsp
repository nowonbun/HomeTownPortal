<%@page import="HT.Portal.Servlet.InstanceServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="./common/master_header.jsp"></jsp:include>
<%=InstanceServlet.get().tile(request, response) %>
<jsp:include page="./common/master_footer.jsp"></jsp:include>
