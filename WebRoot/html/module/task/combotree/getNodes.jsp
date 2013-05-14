<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%
	String id = request.getParameter("node");
	String listNode = "[";
	if(id.length()<5){
		for(int i=1;i<6;i++){
			listNode += "{id:'"+i+"_"+id+"',text:'folder"+i+"_"+id+"'},";
		}
		listNode = listNode.substring(0,listNode.length()-1) + "]";
	}else{
		for(int i=1;i<6;i++){
			listNode += "{id:'"+i+"_"+id+"',text:'node"+i+"_"+id+"',leaf:true},";
		}
		listNode = listNode.substring(0,listNode.length()-1) + "]";;
	}
	out.print(listNode);
	
%>
