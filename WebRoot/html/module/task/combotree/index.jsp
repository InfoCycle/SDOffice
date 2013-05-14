<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>Customizing ComboBoxTree</title>
		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/ext-2.3.0/resources/css/ext-all.css" />
	 	<script src="<%=request.getContextPath() %>/ext-2.3.0/adapter/ext/ext-base.js"></script>
	 	<script src="<%=request.getContextPath() %>/ext-2.3.0/ext-all.js"></script>
	    <script src="<%=request.getContextPath() %>/js/ComboBoxTree.js"></script>
		
		<script type="text/javascript">
			var comboBoxTree;
			Ext.BLANK_IMAGE_URL = 'ext-2.3.0/resources/images/default/s.gif';
			Ext.onReady(function(){
				comboBoxTree = new Ext.ux.ComboBoxTree({
					renderTo : 'comboBoxTree',
					width : 250,
					tree : {
						xtype:'treepanel',
						bbar: ['名称：',{xtype:'trigger',id: 'searchName',width:200,triggerClass:'x-form-search-trigger',onTriggerClick:search}],
						loader: new Ext.tree.TreeLoader({dataUrl:'getNodes.jsp'}),
			       	 	root : new Ext.tree.AsyncTreeNode({id:'0',text:'根结点'})
			    	},
					//all:所有结点都可选中
					//exceptRoot：除根结点，其它结点都可选(默认)
					//folder:只有目录（非叶子和非根结点）可选
					//leaf：只有叶子结点可选
					selectNodeModel:'leaf'
				});
			});
			function showValue(){
				alert("显示值="+comboBoxTree.getRawValue()+"  真实值="+comboBoxTree.getValue());
			}
			function search(){
				var searchName = Ext.getDom('searchName').value;
				alert("查询字符串："+searchName);
			}
		</script>
	</head>
	<body>
		<table>
			<tr>
				<td>&nbsp;</td>
				<td> <div id="comboBoxTree"></div> </td>
				<td> <input type='button' value='值' onclick='showValue()'> </td>
			</tr>
		</table>
	</body>
</html>