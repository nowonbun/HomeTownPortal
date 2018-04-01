<%@page import="authentication.AuthServlet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%AuthServlet.auth(request, response);%>
<div class="container-fluid">

<div>
<ul id="breadcrumb">
  <li><a href="#"><span class="icon icon-home"> </span></a></li>
  <li><a href="#"><span class="icon icon-beaker"> </span> Projects</a></li>
  <li><a href="#"><span class="icon icon-double-angle-right"></span> Breadcrumb</a></li>
  <li><a href="#"><span class="icon icon-rocket"> </span> Getting started</a></li>
  <li><a href="#"><span class="icon icon-arrow-down"> </span> Download</a></li>
</ul>
</div>

</div>