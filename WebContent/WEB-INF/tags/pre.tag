<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ attribute name = "value" type="java.lang.String" required="ture" %>
<% 
	value = value.replace("\n", "\n<br>");
	value = value.replace("&", "&amp;");
	value = value.replace("<", "&lt;");
	value = value.replace(",", "&nbsp;");
%>
<%=value %>