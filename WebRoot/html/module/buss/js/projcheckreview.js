//$(document).bind("contextmenu",function(){return false;});
		$(document).bind("selectstart",function(){return false;});  
		function  addDivClass(){
			$("#divDisable").addClass("div-disabled");
		}
	  	function showLoadings(){
			jQuery('#MainDiv').showLoading({ 'addClass': 'loading-indicator-bars'});
		}
		function hideLoadings(){
			jQuery('#MainDiv').hideLoading();
		}
		showLoadings();
		if($.browser.msie){
			if($.browser.version<=8){
				setActiveStyleSheet("papers.css");
			}else				
				setActiveStyleSheet("papersother.css");
		} else if($.browser.mozilla){
			setActiveStyleSheet("papersother.css");
		} else{
			setActiveStyleSheet("papersother.css");
		}
		function setActiveStyleSheet(filename) {
			document.write("<link href=\"..\/..\/css\/"+filename+"\" type=\"text\/css\" rel=\"stylesheet\">");
		}
		var IFrame='<iframe id="fileUplaod" src="{0}" style="width:100%;height:98%;border-width: 0px;overflow-x:hidden;overflow-y:hidden"/>';
		var action;
		var processId;
		var activeId;
		var formPKID;
		var state;
		var userobj;
		var operate;
		function BLQK(processId){
				var href="../wf/HandleStatus.html?processId="+processId;
				parent.NavigateUrl('wf_handle_'+processId,'办理情况',href);
				return false;
		}
		
		function SelectTask() {
				//ISHISTORY:0,1,2
			    var Parameter={
			    	ISHISTORY: 0
				};
				var ParameterJSON= Base64.encode($.toJSON(Parameter));				
				var win = $(IFrame.format('/html/module/public/SelectTask.html?Parameter='+ParameterJSON));
				$.lightbox(win, {
					modal  : true,
					width   : 560,
					height  : 385
			   }); 
				return false;
			}			 
			function MessageInfo(Title,Msg){
				$.messager.show({
					title:Title,
					msg:Msg,
					showType:'fade',
					timeout:3000,
					style:{
						right:'',
						bottom:''
					}
				});
			}
			function MsgBox(title,msg){  
				$.messager.show({  
					title: title,  
					msg: msg,  
					showType:'fade', 
					timeout:1000,
					style:{  
						right:'',  
						bottom:''  
					}  
				});  
			}
			function todom(dom){
				var body = (window.opera) ? (document.compatMode == "CSS1Compat" ? $('html') : $('body')) : $('html,body');
				body.animate({scrollTop: $(dom).offset().top}, 500);
			}

			/**  
		    判断输入框中输入的日期格式是否为 yyyy-mm-dd   或yyyy-m-d
		  */  
		function isDate(dateString){
		  var flag=false;		
		  if(dateString.trim()=="") return flag;
		  //年月日正则表达式
		  var r=dateString.match(/^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})$/);
		        if(r==null){
		        	//MessageInfo("系统提示","请输入格式正确的日期\n\r日期格式：yyyy-mm-dd\n\r例    如：2008-08-08\n\r");
		        	flag=false;
		        }else
		        	flag=true;
		   return flag;
		  }
		function getEasyValue(obj,type){
			var value;
			switch (type) {
				case "combo":
					value=obj.combo('getValue');
					break;
				case "combobox":
					value=obj.combobox('getValue');
					break;
				case "combotree":
					value=obj.combotree('getValue');
					break;
				case "numberbox":
					value=obj.numberbox('getValue');
					break;
				case "datebox":
					value=obj.datebox('getValue');
					break;
				case "datetimebox":
					value=obj.datetimebox('getValue');
					if(!isDate(value))
						value="";
					break;
			}
			return value;
		}
		function setEasyRequired(obj,type){
			switch (type) {
				case "combo":
					obj.combo({required:true});
					break;
				case "combobox":
					obj.combobox({required:true});
					break;
				case "combotree":
					obj.combotree({required:true});
					break;
				case "numberbox":
					obj.numberbox({required:true});
					break;
				case "datebox":
					obj.datebox({required:true});
					break;
				case "datetimebox":
					obj.datetimebox({required:true});
					break;
			}
		}
		$.fn.extend({        
		   validData :function(msg,isShowMsg,easytype,maxChar){   //msg 例如：'不能为空！请仔细填写。'			   
			   var obj=$(this);
		   	   var value;	   	   
		   	   if(isShowMsg==1){
		   			if(easytype!="")
			   			value=	getEasyValue(obj,easytype); 
		   			else
		   				value= obj.val();	   			
			   		if(value=="" || value==null)
					   {	if(easytype=="datetimebox")
						   		MessageInfo("系统提示",msg+",请输入格式正确的日期\n\r日期格式：yyyy-mm-dd\n\r例    如：2008-08-08\n\r");
					   		else{
					   			MessageInfo('系统提示',msg);
					   		}
							if(easytype!="")
								setEasyRequired(obj,easytype);						
						    return false;
					   }else{
						    if(maxChar>0){
							    if(value.length>maxChar){
							    	MessageInfo('系统提示',msg+"或长度超过"+maxChar);
									return false;
							    }else
							    	return true;
						    }else
						    	return true;
					   }
		   	   }else
		   		{
					   if(trim(obj.val())=="" || obj.val()=="该输入项为必输项！")
					   {
							obj.addClass("classerror");
						    obj.val("该输入项为必输项！");									
						    obj.focus(function(){
								obj.removeClass("classerror");
						    });							
							todom(this);
						    return false;
					   }else{						   
							   if(maxChar>0){
								    if(obj.val().length>maxChar){
								    	MessageInfo('系统提示',msg+"或长度超过"+maxChar);
										return false;
								    }else
								    	return true;
							   
							   }else
							    	return true;
						   }
		   		}
		   	   
			}				
		});			
			function PostCR(id) {
				var entitys={
					userId:$('#userID').val(),
					userName:$('#userName').val(),
					ID:id
				};
				var entityJSON= Base64.encode($.toJSON(entitys));
				var win = $(IFrame.format('/html/module/controls/PostTask.html?entitys=' +entityJSON));
				$.lightbox(win, {
					modal  : true,
					width   : 550,
					height  : 350
			   });				
				return false;
			}
			function WTFUN(id,wtype){				
				var entitys={
					userId:$('#userID').val(),
					userName:$('#userName').val(),
					ID:id,
					wTOther:wtype
				};
				var entityJSON= Base64.encode($.toJSON(entitys));
				var win = $(IFrame.format('/html/module/controls/WTHandle.html?entitys=' +entityJSON));
				$.lightbox(win, {
					modal  : true,
					width   : 550,
					height  : 350
			   });				
				return false;
			}
			//上传成果文件
			function FUN_LOADRESULT(id){								
				var url='/html/module/public/ResultsFileLoadMgt.html?fkCheckReviewId=' +id;
				var win = $(IFrame.format(url));
					$.lightbox(win, {
						modal  : true,
						width   : 721,
						height  : 390,
						onClose : function(){
							$("#fileUplaod").attr("src","");
							$("#ResultFileIframe").attr("src","/html/module/public/ResultsFileList.html?fkCheckReviewId="+id+"&isView=0");
						}
				   });
				return false;
			}

			function getUserHave(userid){				
				temp=$("#userIDList1").val().split(",");				
				for(var i=0;i<temp.length;i++){					
					if($.trim(userid)==$.trim(temp[i].toString()))
					{					
						return "1";break;
					}
				}
			    var temp;				
				temp=$("#userIDList2").val().split(",");
				for(var i=0;i<temp.length;i++){
					if($.trim(userid)==$.trim(temp[i]).toString())
					{return "2";break;}
				}
				temp=$("#userIDList3").val().split(",");
				for(var i=0;i<temp.length;i++){
					if($.trim(userid)==$.trim(temp[i]).toString())
					{return "3";break;}
				}
				return "0";
			}
			function ToSuccess(msg,postname){
				$("#submitSuccessDiv").css('display','inline');
				$("#MainDiv").css('display','none');
				if(postname=="SAVE"){
					$("#labelmsg").text(msg);
				}else if(postname=="BJ"){
					$("#labelmsg").text(msg);
				}else
					$("#labelmsg").text("已成功提交到："+postname+" 请注意办理情况。");
			}
			function ToPartWT(entitys){
				showLoadings();
				$.post("/Buss/CheckReviewService/8", {
									"ProcessId" : $("#processId").val(),
									"AcceptUserId" : entitys.acceptUserId,
									"AboveActId" :  $("#activeId").val(),
									"Remark" :entitys.remark,
									"formPKID":  $("#FId").val(),
									"wTOther":entitys.wTOther
								}, function(data) {
									if(data.success){
										$.LightBoxObject.close();										
										MessageInfo('系统提示', data.msg);
										setButtonState("#aT_OK,#aT_QSH,#aT_Post,#aT_BJ,#aT_ZGBHJ,#aT_BACKTO",false);
										setButtonState("#aB_OK,#aB_QSH,#aB_Post,#aB_BJ,#aB_ZGBHJ,#aB_BACKTO",false);
										setButtonState("#aT_CuiBan,#aB_CuiBan",true);
										hideLoadings()
										//ToSuccess(data.msg,entitys.acceptUserName);
									}else{
										$.LightBoxObject.close();
										MessageInfo('系统提示',  data.msg);
										hideLoadings()
									}
				 }, "json");
			}
			function ToPart(entitys){
				switch($("#FCurrentStep").val().toString()){
								case "10" :
								case "11" :{
									$.post("/Buss/CheckReviewService/6", {
											"ProcessId" : $("#processId").val(),
											"AcceptUserId" : entitys.acceptUserId,
											"AboveActId" :  $("#activeId").val(),
											"Remark" :entitys.remark,
											"formPKID":  $("#FId").val(),
											"WhoPost":1,
											"FPmroReviewMan":$("#F_PMRO_ReviewMan").val(),
											"FPmroReviewTime":$("#F_PMRO_ReviewTime").datetimebox('getValue'),
											"FPmroProblems":$("#F_PMRO_Problems").val(),
											"FPmroRectification":$("#F_PMRO_Rectification").val(),
											"FPmroDiscuss":$("#F_PMRO_Discuss").val()
										}, function(data) {
											if(data.success){
												$.LightBoxObject.close();
												//$.jBox.info('提交成功！', '系统提示');
												MessageInfo('系统提示', data.msg);
												ToSuccess(data.msg,entitys.acceptUserName);

											}else{
												$.LightBoxObject.close();
												MessageInfo('系统提示',  data.msg);
											}
									}, "json"); 
									break;
								}
								case "20" :
								case "21" :{
									$.post("/Buss/CheckReviewService/6", {
											"ProcessId" : $("#processId").val(),
											"AcceptUserId" : entitys.acceptUserId,
											"AboveActId" :  $("#activeId").val(),
											"Remark" :entitys.remark,
											"formPKID":  $("#FId").val(),
											"WhoPost":2,
											"FDmroReviewMan":$("#F_DMRO_ReviewMan").val(),
											"FDmroReviewTime":$("#F_DMRO_ReviewTime").datetimebox('getValue'),
											"FDmroProblems":$("#F_DMRO_Problems").val(),
											"FDmroRectification":$("#F_DMRO_Rectification").val(),
											"FDmroDiscuss":$("#F_DMRO_Discuss").val()
										}, function(data) {
											if(data.success){
												$.LightBoxObject.close();
												//$.jBox.info('提交成功！', '系统提示');
												MessageInfo('系统提示', data.msg);
												ToSuccess(data.msg,entitys.acceptUserName);
											}else{
												$.LightBoxObject.close();
												MessageInfo('系统提示',  data.msg);
											}
									}, "json"); 
									break;
								}
								case "30" :{
									$.post("/Buss/CheckReviewService/6", {
											"ProcessId" : $("#processId").val(),
											"AcceptUserId" : entitys.acceptUserId,
											"AboveActId" :  $("#activeId").val(),
											"Remark" :entitys.remark,
											"formPKID":  $("#FId").val(),
											"WhoPost":3,
											"FCglroFinalCost":$("#F_CGLRO_FinalCost").val(),
											"FCglroProblems":$("#F_CGLRO_Problems").val(),
											"FCglroRectification":$("#F_CGLRO_Rectification").val(),
											"FCglroDiscuss":$("#F_CGLRO_Discuss").val(),
											"FReviewMan":$("#F_ReviewMan").val(),
											"FReviewManTime": $("#F_ReviewMan_Time").text()
										}, function(data) {
											if(data.success){
												$.LightBoxObject.close();
												//$.jBox.info('提交成功！', '系统提示');
												MessageInfo('系统提示', data.msg);
												ToSuccess(data.msg,entitys.acceptUserName);
											}else{
												$.LightBoxObject.close();
												MessageInfo('系统提示',  data.msg);
											}
									}, "json");
									break;
								}
				 }				
			}
			function getTaskInfo(taskid,taskname,taskservertype,tasknumber,deptmgrid){							
				$("#ftaskid").attr("value",taskid);
				$("#ftaskname").attr("value",taskname);				
				$.post("/Buss/CheckReviewService/5", {"TaskID":taskid}, function(data) {
					if (data.success) {									
						var jsonlist= data.root.toString();						
						var arraylist= jsonlist.split(',');						
						$("#ftaskid").attr("value",arraylist[0].toString());											
						$("#fwlb").text(arraylist[3].toString());
						$("#wtdw").text(arraylist[2].toString());
						$("#fxmgm").text(arraylist[4].toString()+" "+arraylist[5].toString());												
						$("#fdeptmgid").attr("value",arraylist[7].toString());
						$("#fdeptid").attr("value",arraylist[8].toString());
						var tasknumber=arraylist[6].toString();
						$.post("/GetNumber/FunForNumber/", {
							"year": new Date().getFullYear(),
							"taskname": tasknumber.substring(0,tasknumber.length-11), 
							"fid": $("#formPKID").val(),
							"numbercode": "FH"
								}, function(data) {
									if (data.success) {
										$("#FFHNumbers").attr("value",data.Number);
									}else
									{															
										return false;
									}
						}, "json");
						$.LightBoxObject.close();
					}else
					{
						//MessageInfo('系统提示', '获取任务异常，请检查网络或联系系统管理员！');
						return false;
					}
				}, "json");				
			}
			function loadBLQK(processId){
				$("#qkTable").datagrid({
						title:'办理情况(近4次)',
						width: '98%',
						showHeader:false,
						nowrap:false,//false设置数据自动换行
						columns:[
							[{/*title:'类型',*/field:'FStyle',width:66,align:'center',
								formatter: function(value,row,index){
									if(value=="1")
										return "办理情况";
									else if(value=="2")
										return "催办情况";
									else if(value=="3")
										return "打回情况";
									else if(value=="-1")
										return "";
									else if(value=="-2")
										return "";
									else if(value=="-3")
										return "";
								}},
							{/*title:'发送人员',*/field:'sendUser',width:60,hidden:false},
							{/*title:'发送时间',*/field:'FSendTime',width:131,align:'center'},
							{/*title:'接收人员',*/field:'acceptUser',width:60,align:'center'},
							{/*title:'接收时间',*/field:'FAcceptTime',width:131,align:'center'},
							{/*title:'办理状态',*/field:'FStateText',width:70,align:'center'},
							{/*title:'完成时间',*/field:'completeTime',width:131,align:'center'},
							{/*title:'备注',*/field:'FRemark',width:150,align:'center'}						
							]],
						url: '/Buss/CommonOpinionService/6/?processId='+processId+"&toprecord="+4,
						rownumbers:false,fit:true,singleSelect:true,
						//pagination:true,
						onSelect: function(index,row){				
							$("#FId").attr("value",row.FId);
							$("#FContentT").text(row.FContent);
						},
						onLoadSuccess:function(){
						  //所有列进行合并操作
						  //$(this).datagrid("autoMergeCells");
						  //指定列进行合并操作
						  $(this).datagrid("autoMergeCells",['FStyle','sendUser','FStateText']);
						  mergeCellsByField("qkTable","FAcceptTime","FStateText","FStateText","completeTime","FRemark");
						},
						rowStyler:function(index,row){
							if(row.FStyle=="-1" || row.FStyle=="-2" || row.FStyle=="-3")
								return 'background-color: #fafafa;'+
										  'background: -webkit-linear-gradient(top,#fdfdfd 0,#f5f5f5 100%);'+
										  'background: -moz-linear-gradient(top,#fdfdfd 0,#f5f5f5 100%);'+
										  'background: -o-linear-gradient(top,#fdfdfd 0,#f5f5f5 100%);'+
										  'background: linear-gradient(to bottom,#fdfdfd 0,#f5f5f5 100%);'+
										  'background-repeat: repeat-x;'+
										  'filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#fdfdfd,endColorstr=#f5f5f5,GradientType=0);'+
										  'color:blue;font-weight:bold;';
						}
					});
				}
		function fn_loadCode(){
			var FPData=[{"FId":"0","FText":"项目检查复核申报起草"},{"FId":"1","FText":"部门经理复核意见"},{"FId":"2","FText":"公司总工办复核意见"}];
			var FRData=[{"FId":"1","FText":"部门经理"},{"FId":"2","FText":"公司总工办"}];
			if($("#FCurrentStep").val()=="20" || $("#FCurrentStep").val()=="21" || $("#FCurrentStep").val()=="30")
				FRData=[{"FId":"2","FText":"公司总工办"}];			
			$('#PostTo').combobox({
				data:FRData,  
				valueField:'FId', 
				editable:false,
				textField:'FText',
				panelheight:'auto',
				onSelect: function(record){
					var deptid;
					if(record.FId==1)
						deptid=$("#fdeptid").val();
					else if(record.FId==2)
						deptid=33;
					if($("#FCurrentStep").val()=="30") //提交到总工办会签，人员多选
					{
						$('#acceptUserId').combotree ({								
							url: "/GetCode/OrgUserTreeExt/0/?DeptId="+deptid,
							panelWidth: 251,
							multiple:true,
							cascadeCheck:false,
							required:true,
							onBeforeSelect:function(node){								
								if(!$(this).tree('isLeaf',node.target)){
									return false;
								}
							},
							onCheck: function(node){
								if(node.attributes.isLeaf)
									$('#acceptUserId').combotree('showPanel');								 
							},
							onClick:function(node){
								if(!$(this).tree('isLeaf',node.target)){
									$('#acceptUserId').combotree('showPanel');
									MessageInfo('系统提示','部门不能选择！');
								}
							}
						});
					}
					else{		//人员单选
						$('#acceptUserId').combotree ({								
							url: "/GetCode/OrgUserTreeExt/0/?DeptId="+deptid,
							panelWidth: 251,
							cascadeCheck:false,
							required:true,
							onBeforeSelect:function(node){
								if(!$(this).tree('isLeaf',node.target)){
									return false;
								}
							},
							onClick:function(node){
								if(!$(this).tree('isLeaf',node.target)){
									$('#acceptUserId').combo('showPanel');
								}
							}
						});
					}
				}
			});
			$('#F_ResultsType').combobox({
						url:'/GetCode/Query/',  
						valueField:'FId', 
						editable:false,
						textField:'FCodeText',
						panelheight:'auto',
						onSelect: function(record){
							$('#F_ResultsType_Name').val($('#F_ResultsType').combobox('getText'));
						},
						onBeforeLoad: function(param){
							param.action = "getAppCode";
							param.CodeTypeId = 381;
							param.State = 1;
						}
			});	
			$('#F_SubmitMaterials').combobox({
						url:'/GetCode/Query/',  
						valueField:'FId', 
						editable:false,
						textField:'FCodeText',
						multiple:true,
						panelheight:'auto',						
						onBeforeLoad: function(param){
							param.action = "getAppCode";
							param.CodeTypeId = 390;
							param.State = 1;
						}
			});
		}
		function loadRTAndSMCode(){
			$('#F_ResultsType').combobox({
				url:'/GetCode/Query/',  
				valueField:'FId', 
				editable:false,
				textField:'FCodeText',
				panelheight:'auto',
				onSelect: function(record){
					$('#F_ResultsType_Name').val($('#F_ResultsType').combobox('getText'));
				},
				onBeforeLoad: function(param){
					param.action = "getAppCode";
					param.CodeTypeId = 381;
					param.State = 1;
				}
			});	
			$('#F_SubmitMaterials').combobox({
						url:'/GetCode/Query/',  
						valueField:'FId', 
						editable:false,
						textField:'FCodeText',
						multiple:true,
						panelheight:'auto',						
						onBeforeLoad: function(param){
							param.action = "getAppCode";
							param.CodeTypeId = 390;
							param.State = 1;
						}
			});
		}
		function setButtonState(id,state){
			if(state)
				$(id).show();
			else
				$(id).hide();
		}
		
		function setElementState(id,state){			
			if(state)
				$(id).attr({readonly:false});
			else
				$(id).attr({readonly:true});
		}
		function getPostData(){
			return {
				FId: $("#FId").val(),
				FConstructionUnit:$("#F_ConstructionUnit").val(),
				FResultTypeName:$("#F_ResultsType_Name").val(),
				FSubmitMaterialsName:$("#F_SubmitMaterials_Name").val(),
				FProjectCost:$("#F_ProjectCost").val(),
				FUnitCost:$("#F_UnitCost").val(),
				FCglroFinalCost:$("#F_CGLRO_FinalCost").val()
			};
		}
		function setFormButtonState(us,iaction,istate){
			setButtonState("#aT_OK,#aT_QSH,#aT_Post,#aT_BJ,#aT_LOADRESULT,#aT_CuiBan,#aT_ZGBHJ,#aT_BACKTO",false);
			setButtonState("#aB_OK,#aB_QSH,#aB_Post,#aB_BJ,#aB_CuiBan,#aB_ZGBHJ,#aB_BACKTO",false);			
			$("#getCheckReviewNumber,#aSelectTask").hide();
			switch(iaction){
				case "0":{
					setButtonState("#aT_OK,#aT_LOADRESULT,#aB_OK",true);
					$("#aSelectTask").show();
					break;
				}
				case "1":{
					switch(istate){
						case "0":{	//起草
							setButtonState("#aT_OK,#aT_LOADRESULT,#aB_OK",true);
							$("#aSelectTask").show();
							break;
						}
						case "1":{	//正在办理								
							//当前办理人不是登录者时处理
							if($("#userID").val() == $("#fAcceptUser").val()){									
								switch($("#FCurrentStep").val().toString()){
									case "10":
										setButtonState("#aT_OK,#aT_LOADRESULT,#aB_OK",true);
										$("#aSelectTask").show();
										break;
									case "11": //项目经理委托办理
										setButtonState("#aT_Post,#aT_QSH,#aT_BACKTO,#aB_Post,#aB_BACKTO,#aB_QSH",true);
										break;
									case "20":
										setButtonState("#aT_Post,#aT_QSH,#aT_BACKTO,#aB_Post,#aB_QSH,#aB_BACKTO",true);
										$('#F_ResultsType').combobox('disable');
										$('#F_SubmitMaterials').combobox('disable');
										break;
									case "21": //项目经理委托办理
										setButtonState("#aT_Post,#aT_QSH,#aT_BACKTO,#aB_Post,#aB_BACKTO,aB_QSH",true);
										break;
									case "30":
										setButtonState("#aT_Post,#aT_QSH,#aT_BACKTO,#aB_Post,#aB_QSH,#aB_BACKTO",true);
										$('#F_ResultsType').combobox('disable');
										$('#F_SubmitMaterials').combobox('disable');
										break;
									case "40":
										setButtonState("#aT_ZGBHJ,#aT_QSH,#aB_ZGBHJ,#aB_QSH",true);										
										setButtonState("#aTgmgrLink",true);	
										$('#F_ResultsType').combobox('disable');
										$('#F_SubmitMaterials').combobox('disable');
										break;
								}
							}else
								{
									setButtonState("#aT_CuiBan,#aB_CuiBan",true);
									$('#F_ResultsType').combobox('disable');
									$('#F_SubmitMaterials').combobox('disable');
								}
							break;
						}
						case "2":{	//业务退回
							if($("#userID").val() == $("#fAcceptUser").val()){
								switch($("#FCurrentStep").val().toString()){
									case "10":
										setButtonState("#aT_OK,#aT_Post,#aT_LOADRESULT,#aB_OK,#aB_Post",true);
										$("#aSelectTask").show();
										break;
									case "11": //项目经理委托办理
										setButtonState("#aT_Post,#aT_QSH,#aB_Post,#aB_QSH",true);
										break;
									case "20":
										setButtonState("#aT_Post,#aT_QSH,#aB_Post,#aB_QSH",true);
										$('#F_ResultsType').combobox('disable');
										$('#F_SubmitMaterials').combobox('disable');
										break;
									case "21": //项目经理委托办理
										setButtonState("#aT_Post,#aT_QSH,#aB_Post,#aB_QSH",true);
										break;
									case "30":
										setButtonState("#aT_Post,#aT_QSH,#aB_Post,#aB_QSH",true);
										$('#F_ResultsType').combobox('disable');
										$('#F_SubmitMaterials').combobox('disable');
										break;
									case "40":
										setButtonState("#aT_Post,#aT_QSH,#aB_Post,#aB_QSH",true);									
										setButtonState("#aTgmgrLink",true);	
										$('#F_ResultsType').combobox('disable');
										$('#F_SubmitMaterials').combobox('disable');
										break;
								}
							}else{
									setButtonState("#aT_CuiBan,#aB_CuiBan",true);
								}
							break;
						}
						case "3":{	//业务终止
							setButtonState("#aT_BLQK,#aB_BLQK",true);
							break;
						}
						case "4":{	//办理完成
							setButtonState("#aT_BLQK,#aB_BLQK",true);
							break;
						}
						case "5":{	//已归档
							setButtonState("#aT_BLQK,#aB_BLQK",true);
							break;
						}											
					}
					break;
				}
				case "2":{
					setButtonState("#aT_BLQK,#aB_BLQK",true);
					setButtonState("#aT_QSH,#aB_QSH",false);
					break;
				}								
			}
			if($("#isAcceptance").val()=="1") {//已签收
				setButtonState("#aT_QSH,#aB_QSH",false);
			}
		 }
		//打回当前步骤的上一步
		function FUN_Back(){
			if($("#OpinionMessageReturn").val() == '')
			{
				$.messager.alert('系统提示','打回意见不能为空，请填写打回意见！','info');
				return false;
			}
			$.post("/Buss/CheckReviewService/13", {
						"AboveActId" : $("#activeId").val(),
						"FormFId": $("#FId").val(),
						"Remark" : $("#OpinionMessageReturn").val().trim()
					}, function(data) {
						if(data.success){
							$.messager.alert('系统提示','打回成功。','info');
							setButtonState("#aT_Post,#aT_QSH,#aT_BACKTO,#aB_Post,#aB_QSH,#aB_BACKTO",false);
							setButtonState("#aT_CuiBan,#aB_CuiBan",true);
							$('#divreturn').dialog('close')
						}else
							$.messager.alert('系统提示','打回失败！请重试。','info');
			}, "json");
		}
		function LoadTaskInfo(taskid){
			$.post("/Buss/CheckReviewService/5", {"TaskID":taskid}, function(data) {
				if (data.success) {									
					var jsonlist= data.root.toString();						
					var arraylist= jsonlist.split(',');						 
						$("#ftaskid").attr("value",arraylist[0].toString());
						$("#ftaskname").attr("value",arraylist[1].toString());						
						$("#fwlb").text(arraylist[3].toString());
						$("#wtdw").text(arraylist[2].toString());
						$("#fxmgm").text(arraylist[4].toString()+" "+arraylist[5].toString());
						$("#fdeptmgid").attr("value",arraylist[7].toString());
						$("#fdeptid").attr("value",arraylist[8].toString());
					if($("#ftaskid").val()==0)
						$("#ftaskname").attr("value","");	
				}else
				{																					
					MessageInfo('系统提示', '获取任务异常，请检查网络或联系系统管理员！');
					return false;
				}
			}, "json");
		}
		function SetFormValue(data, type) {
				$("#FId").attr("value", data.root[0].FId);
				$("#FCurrentStep").attr("value",data.root[0].FCurrentStep);
				$("#FLastStep").attr("value",data.root[0].FLastStep);
				fn_loadCode();
				$("#FKPNumbers").attr("value", data.root[0].FNumber);					
				var taskid=data.root[0].fkTaskId;
				LoadTaskInfo(taskid);
				$("#ftaskid").attr("value",data.root[0].fkTaskId);
				$("#FFHNumbers").attr("value",data.root[0].FNumbers);				
				$("#F_ConstructionUnit").attr("value",data.root[0].FConstructionUnit);
				if(data.root[0].FResultsType!="")
					$("#F_ResultsType").combobox("setValue",data.root[0].FResultsType);
				$("#F_ResultsType_Name").attr("value",data.root[0].FResultTypeName);
				if(data.root[0].FSubmitMaterials!="")
					$("#F_SubmitMaterials").combobox("setValues",data.root[0].FSubmitMaterials.split(","));
				$("#F_SubmitMaterials_Name").attr("value",data.root[0].FSubmitMaterialsName);
				$("#F_ProjectCost").numberbox("setValue",data.root[0].FProjectCost);
				$("#F_UnitCost").numberbox("setValue",data.root[0].FUnitCost);
				$("#F_PMRO_ReviewMan").attr("value",data.root[0].FPmroReviewMan);
				$("#F_PMRO_ReviewTime").datetimebox('setValue',data.root[0].FPmroReviewTime);
				$("#F_PMRO_Problems").attr("value",data.root[0].FPmroProblems);
				$("#F_PMRO_Rectification").attr("value",data.root[0].FPmroRectification);
				$("#F_PMRO_Discuss").attr("value",data.root[0].FPmroDiscuss);
				$("#F_DMRO_ReviewMan").attr("value",data.root[0].FDmroReviewMan);
				$("#F_DMRO_ReviewTime").datetimebox('setValue',data.root[0].FDmroReviewTime);
				$("#F_DMRO_Problems").attr("value",data.root[0].FDmroProblems);
				$("#F_DMRO_Rectification").attr("value",data.root[0].FDmroRectification);
				$("#F_DMRO_Discuss").attr("value",data.root[0].FDmroDiscuss);
				$("#F_CGLRO_FinalCost").numberbox("setValue",data.root[0].FCglroFinalCost);
				$("#F_CGLRO_Problems").attr("value",data.root[0].FCglroProblems);
				$("#F_CGLRO_Rectification").attr("value",data.root[0].FCglroRectification);
				$("#F_CGLRO_Discuss").attr("value",data.root[0].FCglroDiscuss);
				$("#F_ReviewMan").attr("value",data.root[0].FReviewMan);
				$("#F_ReviewMan_Time").text(data.root[0].FReviewManTime);
				$("#F_GLOSign").val(data.root[0].FGlosign);
				$("#F_Glosign_Time").text(data.root[0].FGlosignTime);
				setFormButtonState($("#unitStation").val().toString(),$("#action").val().toString(),$("#fState").val().toString());
			}
			function fn_loadData(){
				switch(action.toString()){					
						case "0": {
							$.post("/Buss/CheckReviewService/3", {
							"ID" : formPKID,
							"processId0": processId,
							"activeId": activeId,
							}, function(data) {								
								$("#FId").val(data.id);						
								$("#formPKID").attr("value",data.id);
								$("#FCurrentStep").attr("value","10");
								setFormState();								
								$("#ResultFileIframe").attr("src","/html/module/public/ResultsFileList.html?fkCheckReviewId="+$("#formPKID").val()+"&isView=0");
								setFormButtonState($("#unitStation").val().toString(),$("#action").val().toString(),$("#fState").val().toString());
								//loadRTAndSMCode();
								fn_loadCode();
								hideLoadings();
							}, "json");						
							break;
						}
						case "1": {						
							$.post("/Buss/CheckReviewService/1", {
							"ID" : formPKID,
							"processId0": processId,
							"activeId": activeId,
							}, function(data) {
								$("#FFHNumbers").attr({readonly:true});
								SetFormValue(data, "Edit");
								setFormState();
								var isView=1;
								if($("#FCurrentStep").val()=="10")
									isView=0;								
								$("#ResultFileIframe").attr("src","/html/module/public/ResultsFileList.html?fkCheckReviewId="+$("#formPKID").val()+"&isView="+isView);	
								hideLoadings();
							}, "json");	
							break;
						}
						case "2": {
							//addDivClass();
							$.post("/Buss/CheckReviewService/1", {
								"ID" : formPKID,
								"processId0": processId,
								"activeId": activeId,
								}, function(data) {
									$("#FFHNumbers").attr({readonly:true});
									SetFormValue(data, "View");
									setFormState();
									$("#ResultFileIframe").attr("src","/html/module/public/ResultsFileList.html?fkCheckReviewId="+$("#formPKID").val()+"&isView=1");	
									hideLoadings();
								}, "json");	
								break;
							break;
						}
					}
				}
				function setFormState(){
					setElementState("#F_ProjectCost,#F_UnitCost,#F_CGLRO_FinalCost",false);
					switch($("#state").val().toString()){
						case "0":{ //起草项目经理、部门经理和总工办意见为只读							
							$("#F_PMRO_ReviewMan,#F_PMRO_ReviewTime,#F_PMRO_Problems,#F_PMRO_Rectification,#F_PMRO_Discuss").attr({readonly:false});
							$("#F_DMRO_ReviewMan,#F_DMRO_ReviewTime,#F_DMRO_Problems,#F_DMRO_Rectification,#F_DMRO_Discuss").attr({readonly:true});
							$("#F_CGLRO_FinalCost,#F_CGLRO_Problems,#F_CGLRO_Rectification,#F_CGLRO_Discuss").attr({readonly:true});
							$("#F_ReviewMan,#F_GLOSign").attr({readonly:true});
							$("#bwtbutn").hide();							
							$("#F_PMRO_ReviewMan").attr("value",$("#userName").val());
							$("#F_PMRO_ReviewTime").datetimebox('setValue',new Date().toLocaleTimeString());
							setElementState("#F_ProjectCost,#F_UnitCost",true);
							break;
						}
						case "1":
						case "2":{
							//办理中，根据登录者岗位设置，部门经理为项目经理时？总工办为项目经理时？
							switch($("#FCurrentStep").val().toString()){
								case "10" :{
									$("#F_PMRO_ReviewMan,#F_PMRO_ReviewTime,#F_PMRO_Problems,#F_PMRO_Rectification,#F_PMRO_Discuss").attr({readonly:false});
									$("#F_DMRO_ReviewMan,#F_DMRO_ReviewTime,#F_DMRO_Problems,#F_DMRO_Rectification,#F_DMRO_Discuss").attr({readonly:true});
									$("#F_CGLRO_FinalCost,#F_CGLRO_Problems,#F_CGLRO_Rectification,#F_CGLRO_Discuss").attr({readonly:true});
									if($("#userID").val() == $("#fAcceptUser").val()){
										$("#F_PMRO_ReviewMan").attr("value",$("#userName").val());
										$("#F_PMRO_ReviewTime").datetimebox('setValue',new Date().toLocaleTimeString());
									}
									setElementState("#F_ProjectCost,#F_UnitCost",true);
									$("#bwtbutn").hide();
									break;
								}
								case "11" :{
									$("#F_PMRO_ReviewMan,#F_PMRO_ReviewTime,#F_PMRO_Problems,#F_PMRO_Rectification,#F_PMRO_Discuss").attr({readonly:false});
									$("#F_DMRO_ReviewMan,#F_DMRO_ReviewTime,#F_DMRO_Problems,#F_DMRO_Rectification,#F_DMRO_Discuss").attr({readonly:true});
									$("#F_CGLRO_FinalCost,#F_CGLRO_Problems,#F_CGLRO_Rectification,#F_CGLRO_Discuss").attr({readonly:true});
									if($("#userID").val() == $("#fAcceptUser").val()){
										$("#F_PMRO_ReviewMan").attr("value",$("#userName").val());
										$("#F_PMRO_ReviewTime").datetimebox('setValue',new Date().toLocaleTimeString());
									}
									$("#pwtbutn,#bwtbutn").hide();
									break;
								}
								case "20" :{
									$("#F_PMRO_ReviewMan,#F_PMRO_ReviewTime,#F_PMRO_Problems,#F_PMRO_Rectification,#F_PMRO_Discuss").attr({readonly:true});
									$("#F_DMRO_ReviewMan,#F_DMRO_ReviewTime,#F_DMRO_Problems,#F_DMRO_Rectification,#F_DMRO_Discuss").attr({readonly:false});
									$("#F_CGLRO_FinalCost,#F_CGLRO_Problems,#F_CGLRO_Rectification,#F_CGLRO_Discuss").attr({readonly:true});
									if($("#userID").val() == $("#fAcceptUser").val()){
										$("#F_DMRO_ReviewMan").attr("value",$("#userName").val());
										$("#F_DMRO_ReviewTime").datetimebox('setValue',new Date().toLocaleTimeString());
									}
									$("#pwtbutn").hide();
									break;
								}
								case "21" :{
									$("#F_PMRO_ReviewMan,#F_PMRO_ReviewTime,#F_PMRO_Problems,#F_PMRO_Rectification,#F_PMRO_Discuss").attr({readonly:true});
									$("#F_DMRO_ReviewMan,#F_DMRO_ReviewTime,#F_DMRO_Problems,#F_DMRO_Rectification,#F_DMRO_Discuss").attr({readonly:false});
									$("#F_CGLRO_FinalCost,#F_CGLRO_Problems,#F_CGLRO_Rectification,#F_CGLRO_Discuss").attr({readonly:true});
									if($("#userID").val() == $("#fAcceptUser").val()){
										$("#F_DMRO_ReviewMan").attr("value",$("#userName").val());
										$("#F_DMRO_ReviewTime").datetimebox('setValue',new Date().toLocaleTimeString());
									}
									$("#pwtbutn,#bwtbutn").hide();
									break;
								}
								case "30" :{
									$("#F_PMRO_ReviewMan,#F_PMRO_ReviewTime,#F_PMRO_Problems,#F_PMRO_Rectification,#F_PMRO_Discuss").attr({readonly:true});
									$("#F_DMRO_ReviewMan,#F_DMRO_ReviewTime,#F_DMRO_Problems,#F_DMRO_Rectification,#F_DMRO_Discuss").attr({readonly:true});
									$("#F_CGLRO_FinalCost,#F_CGLRO_Problems,#F_CGLRO_Rectification,#F_CGLRO_Discuss").attr({readonly:false});
									if($("#userID").val() == $("#fAcceptUser").val()){
										$("#F_ReviewMan").attr("value",$("#userName").val());
										var cdate=new Date();			   								
		   								$("#F_ReviewMan_Time").text((cdate.getFullYear()+"-"+(cdate.getMonth()+1)+"-"+cdate.getDate()).replace(/\b(\w)\b/g, '0$1'));
										//$("#F_ReviewMan_Time").text(new Date().toLocaleString());
									}
									$("#pwtbutn,#bwtbutn").hide();
									break;
								}
								case "40" :{
									$("#F_PMRO_ReviewMan,#F_PMRO_ReviewTime,#F_PMRO_Problems,#F_PMRO_Rectification,#F_PMRO_Discuss").attr({readonly:true});
									$("#F_DMRO_ReviewMan,#F_DMRO_ReviewTime,#F_DMRO_Problems,#F_DMRO_Rectification,#F_DMRO_Discuss").attr({readonly:true});
									$("#F_CGLRO_FinalCost,#F_CGLRO_Problems,#F_CGLRO_Rectification,#F_CGLRO_Discuss").attr({readonly:true});									
									$("#F_ReviewMan,#F_GLOSign").attr({readonly:true});
									$("#pwtbutn,#bwtbutn").hide();
									break;
								}
							}
							break;
						}
						default :{//其他，都设置为只读
							$("#F_PMRO_ReviewMan,#F_PMRO_ReviewTime,#F_PMRO_Problems,#F_PMRO_Rectification,#F_PMRO_Discuss").attr({readonly:true});
							$("#F_DMRO_ReviewMan,#F_DMRO_ReviewTime,#F_DMRO_Problems,#F_DMRO_Rectification,#F_DMRO_Discuss").attr({readonly:true});
							$("#F_CGLRO_FinalCost,#F_CGLRO_Problems,#F_CGLRO_Rectification,#F_CGLRO_Discuss").attr({readonly:true});
							$("#F_ReviewMan,#F_GLOSign").attr({readonly:true});
							$("#pwtbutn,#bwtbutn").hide();
							break;
						}
					}
					if($("#userID").val() != $("#fAcceptUser").val()){
						$("#F_PMRO_ReviewMan,#F_PMRO_ReviewTime,#F_PMRO_Problems,#F_PMRO_Rectification,#F_PMRO_Discuss").attr({readonly:true});
						$("#F_DMRO_ReviewMan,#F_DMRO_ReviewTime,#F_DMRO_Problems,#F_DMRO_Rectification,#F_DMRO_Discuss").attr({readonly:true});
						$("#F_CGLRO_FinalCost,#F_CGLRO_Problems,#F_CGLRO_Rectification,#F_CGLRO_Discuss").attr({readonly:true});					    
						$("#F_ReviewMan,#F_GLOSign").attr({readonly:true});
						$("#pwtbutn,#bwtbutn").hide();
					}
					$("#F_PMRO_ReviewMan,#F_DMRO_ReviewMan,#F_ReviewMan").attr({readonly:true});
		}
		function fn_initpage(){								
			action = Request.QueryString("action");
			processId = Request.QueryString("processId");
			activeId = Request.QueryString("activeId");
			formPKID = Request.QueryString("formPKID");
			state = Request.QueryString("state");			
			$("#action").attr("value",action);
			$("#processId").attr("value",processId);
			$("#activeId").attr("value",activeId);
			$("#formPKID").attr("value",formPKID);
			$("#state").attr("value",state);
			$("#ftaskname,#FFHNumbers,#F_GLOSign").attr({readonly : 'true'});
			
			$("#F_ProjectCost").numberbox({  
		        min:0,
		        required: true,
		        max:1000000000000,
		        precision:2
		    }); 
		    $("#F_UnitCost").numberbox({  
		        min:0,
		        required: true,
		        max:1000000000000,
		        precision:2  
		    }); 
		    $("#F_CGLRO_FinalCost").numberbox({  
		        min:0,
		        required: true,
		        max:1000000000000,
		        precision:2  
		    });
		}
		function fn_error(){
			MessageInfo('加载数据错误！请重试。', '系统提示');
		}
		function fn_success(data){
			if(data){
				if (data.success) {			
					 $("#userID").attr("value",data.root.userID);
					 $("#userName").attr("value",data.root.userName);
					 $("#userOrgID").attr("value",data.root.userOrgID);
					 $("#unitStation").attr("value",data.root.unitStation);
					 $("#fAcceptUser").attr("value",data.root.fAcceptUser);
					 $("#fState").attr("value",data.root.fState);
					 $("#isAcceptance").attr("value",data.root.isAcceptance);
					 fn_initpage();				 
					 fn_loadData();
					 //fn_loadCode();
					 loadBLQK(processId);
				}else
				{
						userobj=null;					
						return false;
				}
			}
		}
		function ValidateUser(activeId){
			$.ajax({
				url:'/Buss/CommonService/1',
				type:'POST',
				data:{"activeId":activeId},
				dataType: 'json',
				timeout: 5000,
				error: fn_error,				
				success: fn_success
			});
		}
		function FUN_CuiBan(){
			if($("#OpinionMessageCuiBan").val() == '')
			{
				$.messager.alert('系统提示','催办意见不能为空，请填写催办意见！','info');
				return false;
			}
			$.post("/Buss/CheckReviewService/11", {
						"AboveActId" : $("#activeId").val(),
						"Remark" : $("#OpinionMessageCuiBan").val().trim()
					}, function(data) {
						if(data.success){
							$.messager.alert('系统提示','催办成功。','info');
							$("#divcuiban").dialog('close');
						}else
							$.messager.alert('系统提示','催办失败！请重试.','info');
			}, "json");
		}
		 function FUN_POST(){			 	
				var ispost=false;
				switch($("#FCurrentStep").val().toString()){
					case "10" :{
						ispost= $("#FFHNumbers").validData("编号不能为空！",0,"",0);
						ispost = ispost && $("#ftaskname").validData("任务不能为空！",0,"",0);
						ispost= ispost && $("#F_ConstructionUnit").validData("施工单位不能为空！",0,"",0);
						ispost= ispost && $("#F_ResultsType").validData("成果类型不能为空！",1,"combobox",0);
						ispost= ispost && $("#F_SubmitMaterials").validData("提交材料不能为空！",1,"combobox",0);
						ispost= ispost && $("#F_ProjectCost").validData("项目造价(资金)不能为空！",1,"numberbox",0);
						ispost= ispost && $("#F_UnitCost").validData("单位造价不能为空！",1,"numberbox",0);
						if($("#F_PMRO_Problems").val()!=""){
							ispost= ispost && $("#F_PMRO_Problems").validData("发现的问题",1,"",1000);
						}
						if($("#F_PMRO_Rectification").val()!=""){
							ispost= ispost && $("#F_PMRO_Rectification").validData("整改情况",1,"",1000);
						}
						if($("#F_PMRO_Discuss").val()!=""){
							ispost= ispost && $("#F_PMRO_Discuss").validData("需进一步讨论",1,"",1000);
						}												
						break;
					}
					case "11" :{
						ispost=$("#F_PMRO_ReviewMan").validData("复核人不能为空！");
						if($("#F_PMRO_Problems").val()!=""){
							ispost= ispost && $("#F_PMRO_Problems").validData("发现的问题",1,"",1000);
						}
						if($("#F_PMRO_Rectification").val()!=""){
							ispost= ispost && $("#F_PMRO_Rectification").validData("整改情况",1,"",1000);
						}
						if($("#F_PMRO_Discuss").val()!=""){
							ispost= ispost && $("#F_PMRO_Discuss").validData("需进一步讨论",1,"",1000);
						}
						break;
					}
					case "20" :{
						ispost=$("#F_DMRO_ReviewMan").validData("复核人不能为空！");	
						if($("#F_DMRO_Problems").val()!=""){
							ispost= ispost && $("#F_DMRO_Problems").validData("发现的问题",1,"",1000);
						}
						if($("#F_DMRO_Rectification").val()!=""){
							ispost= ispost && $("#F_DMRO_Rectification").validData("整改情况",1,"",1000);
						}
						if($("#F_DMRO_Discuss").val()!=""){
							ispost= ispost && $("#F_DMRO_Discuss").validData("需进一步讨论",1,"",1000);
						}
						break;
					}
					case "21" :{
						ispost=$("#F_DMRO_ReviewMan").validData("复核人不能为空！");
						if($("#F_DMRO_Problems").val()!=""){
							ispost= ispost && $("#F_DMRO_Problems").validData("发现的问题",1,"",1000);
						}
						if($("#F_DMRO_Rectification").val()!=""){
							ispost= ispost && $("#F_DMRO_Rectification").validData("整改情况",1,"",1000);
						}
						if($("#F_DMRO_Discuss").val()!=""){
							ispost= ispost && $("#F_DMRO_Discuss").validData("需进一步讨论",1,"",1000);
						}
						break;
					}
					case "30" :{
						ispost=$("#F_CGLRO_FinalCost").validData("最终报价不能为空！");									 
						ispost=ispost && $("#F_ReviewMan").validData("复核人不能为空！");	
						if($("#F_CGLRO_Problems").val()!=""){
							ispost= ispost && $("#F_CGLRO_Problems").validData("发现的问题",1,"",20);
						}
						if($("#F_CGLRO_Rectification").val()!=""){
							ispost= ispost && $("#F_CGLRO_Rectification").validData("整改情况",1,"",1000);
						}
						if($("#F_CGLRO_Discuss").val()!=""){
							ispost= ispost && $("#F_CGLRO_Discuss").validData("需进一步讨论",1,"",1000);
						}
						break;
					}
					case "40" :{									
						break;
					}
				}				
				if(ispost){
					if($("#FCurrentStep").val().toString()=="10")
						//判断成果文件是否已经上传
						FUN_ResultFileIsLoad($("#F_SubmitMaterials_Name").val(),$("#formPKID").val());
					else
						PostCR($('input[name=FId]').fieldValue());
				}
		}
		 
		function FUN_ResultFileIsLoad(sm,id)
		{
			$.post("/Buss/ResultsLoadFileService/4",{SubmitMaterials:sm,ID:id}, function(data) {
				if (!data.success) {						
					$.messager.alert('系统提示',"<font style='color:red;'>"+"<br/><br/>"+data.msg+"</font>",'warning');					
				}else{
					FUN_POSTOK($('input[name=FId]').fieldValue());
				}
			}, "json");
		}
		function FUN_POSTOK(id){
			var remark=$("#OpinionMessagePost").val();
			var PostTo=$("#PostTo").combobox('getValue');
			var PostAcceptUserId=$("#acceptUserId").combotree('getValue');
			switch($("#FCurrentStep").val().toString()){
					case "10" :
					case "11" :{
						$.post("/Buss/CheckReviewService/6", {
								"ProcessId" : $("#processId").val(),
								"AcceptUserId" : PostAcceptUserId,
								"AboveActId" :  $("#activeId").val(),
								"Remark" :	remark,
								"formPKID":  $("#FId").val(),
								"WhoPost":1,
								"PostTo":PostTo,
								"FPmroReviewMan":$("#F_PMRO_ReviewMan").val(),
								"FPmroReviewTime":$("#F_PMRO_ReviewTime").datetimebox('getValue'),
								"FPmroProblems":$("#F_PMRO_Problems").val(),
								"FPmroRectification":$("#F_PMRO_Rectification").val(),
								"FPmroDiscuss":$("#F_PMRO_Discuss").val()
							}, function(data) {
								if(data.success){
									MessageInfo('系统提示', data.msg);
									setButtonState("#aT_OK,#aT_QSH,#aT_Post,#aT_BJ,#aT_ZGBHJ,#aT_BACKTO",false);
									setButtonState("#aB_OK,#aB_QSH,#aB_Post,#aB_BJ,#aB_ZGBHJ,#aB_BACKTO",false);
									setButtonState("#aT_CuiBan,#aB_CuiBan",true);
									$("#aSelectTask").hide();
									$("#divPost").dialog('close');
								}else{
									$.LightBoxObject.close();
									MessageInfo('系统提示',  data.msg);
								}
						}, "json"); 
						break;
					}
					case "20" :
					case "21" :{
						$.post("/Buss/CheckReviewService/6", {
								"ProcessId" : $("#processId").val(),
								"AcceptUserId" : PostAcceptUserId,
								"AboveActId" :  $("#activeId").val(),
								"Remark" : remark,
								"formPKID":  $("#FId").val(),
								"WhoPost":2,
								"PostTo":PostTo,
								"FDmroReviewMan":$("#F_DMRO_ReviewMan").val(),
								"FDmroReviewTime":$("#F_DMRO_ReviewTime").datetimebox('getValue'),
								"FDmroProblems":$("#F_DMRO_Problems").val(),
								"FDmroRectification":$("#F_DMRO_Rectification").val(),
								"FDmroDiscuss":$("#F_DMRO_Discuss").val()
							}, function(data) {
								if(data.success){
									MessageInfo('系统提示', data.msg);
									setButtonState("#aT_OK,#aT_QSH,#aT_Post,#aT_BJ,#aT_ZGBHJ,#aT_BACKTO",false);
									setButtonState("#aB_OK,#aB_QSH,#aB_Post,#aB_BJ,#aB_ZGBHJ,#aB_BACKTO",false);
									setButtonState("#aT_CuiBan,#aB_CuiBan",true);
									$("#divPost").dialog('close');
								}else{
									$.LightBoxObject.close();
									MessageInfo('系统提示',  data.msg);
								}
						}, "json"); 
						break;
					}
					case "30" :{
						PostAcceptUserId=$("#acceptUserId").combotree('getValues');						
						$.post("/Buss/CheckReviewService/6", {
								"ProcessId" : $("#processId").val(),
								"AcceptUserId" : PostAcceptUserId.toString(),//必须转换为字符串，否则将是空串
								"AboveActId" :  $("#activeId").val(),
								"Remark" : remark,
								"formPKID":  $("#FId").val(),
								"WhoPost":3,
								"PostTo":PostTo,
								"FCglroFinalCost":$("#F_CGLRO_FinalCost").val(),
								"FCglroProblems":$("#F_CGLRO_Problems").val(),
								"FCglroRectification":$("#F_CGLRO_Rectification").val(),
								"FCglroDiscuss":$("#F_CGLRO_Discuss").val(),
								"FReviewMan":$("#F_ReviewMan").val(),
								"FReviewManTime": $("#F_ReviewMan_Time").text()
							}, function(data) {
								if(data.success){
									MessageInfo('系统提示', data.msg);
									setButtonState("#aT_OK,#aT_QSH,#aT_Post,#aT_BJ,#aT_ZGBHJ,#aT_BACKTO",false);
									setButtonState("#aB_OK,#aB_QSH,#aB_Post,#aB_BJ,#aB_ZGBHJ,#aB_BACKTO",false);
									setButtonState("#aT_CuiBan,#aB_CuiBan",true);
									$("#divPost").dialog('close');
								}else{
									MessageInfo('系统提示',  data.msg);
								}
						}, "json");
						break;
					}
			}
		}
		function FUN_POSTPCR(){
			var ispost=false;			
			if($("#PostTo").combobox('getText')=="")
				{
					$.messager.alert('系统提示',"请选择到部门还是总工办！",'warning');
					return false;
				}
			if($("#acceptUserId").combotree('getText')=="")
			{
				$.messager.alert('系统提示',"请选择接收人！接收人为空,请选择接收人。",'warning');
				return false;
			}
			if($("#OpinionMessagePost").val()=="")
			{
				$.messager.alert('系统提示',"请填写意见！意见不能为空,请填写。",'warning');
				return false;
			}
			switch($("#FCurrentStep").val().toString()){
				case "10" :{
					ispost= $("#FFHNumbers").validData("编号不能为空！",0,"",0);
					ispost = ispost && $("#ftaskname").validData("任务不能为空！",0,"",0);
					ispost= ispost && $("#F_ConstructionUnit").validData("施工单位不能为空！",0,"",0);
					ispost= ispost && $("#F_ResultsType").validData("成果类型不能为空！",1,"combobox",0);
					ispost= ispost && $("#F_SubmitMaterials").validData("提交材料不能为空！",1,"combobox",0);
					ispost= ispost && $("#F_ProjectCost").validData("项目造价(资金)不能为空！",1,"numberbox",0);
					ispost= ispost && $("#F_UnitCost").validData("单位造价不能为空！",1,"numberbox",0);
					if($("#F_PMRO_Problems").val()!=""){
						ispost= ispost && $("#F_PMRO_Problems").validData("发现的问题可为空",1,"",1000);
					}
					if($("#F_PMRO_Rectification").val()!=""){
						ispost= ispost && $("#F_PMRO_Rectification").validData("整改情况可为空",1,"",1000);
					}
					if($("#F_PMRO_Discuss").val()!=""){
						ispost= ispost && $("#F_PMRO_Discuss").validData("需进一步讨论可为空",1,"",1000);
					}
					break;
				}
				case "11" :{
					ispost=$("#F_PMRO_ReviewMan").validData("复核人不能为空！",0,"",0);
					if($("#F_PMRO_Problems").val()!=""){
						ispost= ispost && $("#F_PMRO_Problems").validData("发现的问题可为空",1,"",1000);
					}
					if($("#F_PMRO_Rectification").val()!=""){
						ispost= ispost && $("#F_PMRO_Rectification").validData("整改情况可为空",1,"",1000);
					}
					if($("#F_PMRO_Discuss").val()!=""){
						ispost= ispost && $("#F_PMRO_Discuss").validData("需进一步讨论可为空",1,"",1000);
					}
					break;
				}
				case "20" :{
					ispost=$("#F_DMRO_ReviewMan").validData("复核人不能为空！",0,"",0);
					if($("#F_DMRO_Problems").val()!=""){
						ispost= ispost && $("#F_DMRO_Problems").validData("发现的问题可为空",1,"",1000);
					}
					if($("#F_DMRO_Rectification").val()!=""){
						ispost= ispost && $("#F_DMRO_Rectification").validData("整改情况可为空",1,"",1000);
					}
					if($("#F_DMRO_Discuss").val()!=""){
						ispost= ispost && $("#F_DMRO_Discuss").validData("需进一步讨论可为空",1,"",1000);
					}
					break;
				}
				case "21" :{
					ispost=$("#F_DMRO_ReviewMan").validData("复核人不能为空！",0,"",0);
					if($("#F_DMRO_Problems").val()!=""){
						ispost= ispost && $("#F_DMRO_Problems").validData("发现的问题可为空",1,"",1000);
					}
					if($("#F_DMRO_Rectification").val()!=""){
						ispost= ispost && $("#F_DMRO_Rectification").validData("整改情况可为空",1,"",1000);
					}
					if($("#F_DMRO_Discuss").val()!=""){
						ispost= ispost && $("#F_DMRO_Discuss").validData("需进一步讨论可为空",1,"",1000);
					}
					break;
				}
				case "30" :{
					ispost=$("#F_CGLRO_FinalCost").validData("最终报价不能为空！",0,"",0);									 
					ispost=ispost && $("#F_ReviewMan").validData("复核人不能为空！",0,"",0);
					if($("#F_CGLRO_Problems").val()!=""){
						ispost= ispost && $("#F_CGLRO_Problems").validData("发现的问题可为空",1,"",1000);
					}
					if($("#F_CGLRO_Rectification").val()!=""){
						ispost= ispost && $("#F_CGLRO_Rectification").validData("整改情况可为空",1,"",1000);
					}
					if($("#F_CGLRO_Discuss").val()!=""){
						ispost= ispost && $("#F_CGLRO_Discuss").validData("需进一步讨论可为空",1,"",1000);
					}
					break;
				}
				case "40" :{	//总工办会签								
					break;
				}
			}
			if(ispost){
				if($("#FCurrentStep").val().toString()=="10")
					//判断成果文件是否已经上传
					FUN_ResultFileIsLoad($("#F_SubmitMaterials_Name").val(),$("#formPKID").val());
				else{					
						FUN_POSTOK($('input[name=FId]').fieldValue());					
				}
			}
		}
		//记录修改记录
		function FUN_ModifyOk(){
			$.post("/Buss/CheckReviewService/15", {
				ProcessId: $("#processId").val(),
				ModifyMessage: $("#ModifyMessage").val()
				}, function(data) {
					if(data.success){						 
						switch (operate){
							case "save":
								FUN_Save();
								break;
							case "post":
								FUN_Post();
								break;
							case "zppm":
								FUN_ZPPM();
								break;
						} 
						$('#divmodifyinfo').dialog('close');
						return;
					}else
						$.messager.alert('系统提示','保存失败！请重试.','info');
					$('#divmodifyinfo').dialog('close');
			}, "json");
		}
		/*保存复核申报*/
		function FUN_Save() {
			$('#F_SubmitMaterials_Name').val($('#F_SubmitMaterials').combobox('getText'));
			$.messager.confirm('系统提示', '您确定要保存吗？', function(r){
				if (r){						
					var dataJson={
						processId:parseInt(processId),
						TaskId: $("#ftaskid").val(),
						TaskName:$("#ftaskname").val(),
						FFHNumbers:$("#FFHNumbers").val(),
						FId:$("#FId").val(),
						FConstructionUnit:$("#F_ConstructionUnit").val(),
						FResultsType:$("#F_ResultsType").combobox('getValue'),
						FSubmitMaterials:$("#F_SubmitMaterials").combobox('getValues').toString(),
						FResultsTypeName:$("#F_ResultsType_Name").val().toString(),
						FSubmitMaterialsName:$("#F_SubmitMaterials_Name").val().toString(),
						FProjectCost:$("#F_ProjectCost").numberbox('getValue'),
						FUnitCost:$("#F_UnitCost").numberbox('getValue')
					};						
					$.post("/Buss/CheckReviewService/2",$.evalJSON($.toJSON(dataJson)), function(data) {
							if (data.success) {
								setButtonState("#aT_Post,#aB_Post",true);
								MessageInfo('系统提示', '项目检查复核申报,保存成功。');
								//ToSuccess("项目检查复核申报，保存成功。请注意办理情况。","SAVE");
							}else
							{									
								MessageInfo('系统提示', '项目检查复核申报,保存失败，请检查网络重试或联系系统管理员！');
								return false;
							}
					}, "json");
				}
			});
		}
		///////////////////////////////////////////////////////////////////////////////////////////////////
		$(document).ready(function() {
			ValidateUser(Request.QueryString("activeId"));
			$("#getCheckReviewNumber").click(function() {
					if($("#ftaskname").val()=="")
					{
						$.messager.alert('系统提示','请先选择任务!','error');
						return true;
					}					
					$.post("/GetNumber/ForNumberRule/", {
						"Code": "FH",
						"Title": $("#ftaskname").val(),
						"isHaveSub": 0
							}, function(data) {
								if (data.success) {
									$("#FFHNumbers").attr("value",data.Number);
									$.messager.alert('系统提示','编号生成成功!','info');
								}else
								{															
									$.messager.alert('系统提示','编号生成失败，请重试!','error');
									return false;
								}
					}, "json");
				});
			
			
           
			$("#aT_OK,#aB_OK").click(function() {
				var issave= $("#FFHNumbers").validData("编号不能为空！",0,"",150);
				issave = issave && $("#ftaskname").validData("任务不能为空！",0,"",0);
				issave= issave && $("#F_ConstructionUnit").validData("施工单位不能为空！",0,"",150);
				issave= issave && $("#F_ResultsType").validData("成果类型不能为空！",1,"combobox",0);
				issave= issave && $("#F_SubmitMaterials").validData("提交材料不能为空！",1,"combobox",0);
				issave= issave && $("#F_ProjectCost").validData("项目造价(资金)不能为空！",1,"numberbox",0);
				issave= issave && $("#F_UnitCost").validData("单位造价不能为空！",1,"numberbox",0);
				if(issave)
				{
					operate="save";
					if($("#state").val()=="2"){
						$.post("/Buss/CheckReviewService/14", {
								"JSONDATA" : JSON.stringify(getPostData())							
							}, function(data) {
									if(data.success){
										if(data.msg=="")
											FUN_Save();
										else{
										  //记录历史
										  $('#divmodifyinfo').dialog('open');
										  $('#divmodifyinfo').dialog('center');
										  $('#ModifyMessage').attr('value',data.msg);
										  hideLoadings();
										}
									}
							}, "json");
					}else
						FUN_Save();
						
				}
			});
			$("#aT_BLQK,#aB_BLQK").click(function() {
					BLQK($("#processId").val());
			});			
			
			$("#aT_Post,#aB_Post").click(function() {
				if($("#FFHNumbers").val()=="" || $("#ftaskname").val()==""){
					$.messager.alert('系统提示','提交前,请先保存！','info');
					return ;
				}
				
				if($("#F_SubmitMaterials").combobox('getText')!=$("#F_SubmitMaterials_Name").val())
				{
					$.messager.alert('系统提示',"<font style='color:red;'>"+"<br/><br/>“提交材料”被修改请先“保存”，然后“提交”。</font>",'warning');	
					return false;
				}
				else
				{
					if($("#isAcceptance").val()=="0" && $("#FCurrentStep").val()!="10"){
						 $.messager.alert('系统提示','提交操作，需要签收后才能操作！','info');
						 return ;
					}
					else{						
						//FUN_POST();
						$("#divPost").dialog({
							title: '提交操作'
						});
						$("#divPost").dialog('open');
						$("#divPost").dialog('center');
					}
				}
			});

			$("#aT_LOADRESULT").click(function() {
				FUN_LOADRESULT($('input[name=FId]').fieldValue());
			});
			function FUN_WT(wtype){
				var ispost= $("#FFHNumbers").validData("编号不能为空！",0,"",0);
				ispost = ispost && $("#ftaskname").validData("任务不能为空！",0,"",0);
				ispost= ispost && $("#F_ConstructionUnit").validData("施工单位不能为空！",0,"",0);
				ispost= ispost && $("#F_ResultsType").validData("成果类型不能为空！",1,"combobox",0);
				ispost= ispost && $("#F_SubmitMaterials").validData("提交材料不能为空！",1,"combobox",0);
				ispost= ispost && $("#F_ProjectCost").validData("项目造价(资金)不能为空！",1,"numberbox",0);
				ispost= ispost && $("#F_UnitCost").validData("单位造价不能为空！",1,"numberbox",0);
				if(ispost)
					WTFUN($('input[name=FId]').fieldValue(),wtype);
			}
			$("#pwtbutn").click(function(){
				FUN_WT("1");
			});
			$("#bwtbutn").click(function(){
				FUN_WT("2");
			});
			$("#aT_BJ,#aB_BJ").click(function() {
				 $.messager.confirm('系统提示', '您确定要办结吗？办结后项目检查复核申报将不能修改！', function(r){
					if (r){										 
						$.post("/Buss/CheckReviewService/9", {
							"ProcessId" : processId,
							"AboveActId" : activeId,										
							"formPKID": formPKID								
						 }, function(data) {
								if (data.success) {												
									MessageInfo('系统提示', '办结成功。');
									ToSuccess("已成功办结，项目检查复核申报将不能修改。","BJ");
								}else
								{									
									MessageInfo('系统提示', '办结失败，请检查网络或联系系统管理员！');								
									return false;
								}
							}, "json");										
						}
					});
			});
			$("#aT_CuiBan,#aB_CuiBan").click(function() {
				$("#divcuiban").dialog('open');
				$("#divcuiban").dialog('center');
			});
			$("#aT_QSH,#aB_QSH").click(function() {				
				$.messager.confirm('系统提示', '您确定要签收该任务吗？', function(r){
					if (r){							
						var Id = $("#FId").val();
						$.post("/Buss/CheckReviewService/10", {
								"AboveActId" : activeId,
								"Remark":""
	   						}, function(data) {
	   							if (data.success){
	   								$.messager.alert('系统提示','签收成功。','info');	   							
									$("#isAcceptance").val("1");
									setButtonState("#aT_QSH,#aB_QSH",false);
	   							}
	   							else
	   								$.messager.alert('系统提示','任务签收失败！请重试。','info');
						}, "json");							
					}
				});
			});
			
			$("#aT_BACKTO,#aB_BACKTO").click(function() {
				if($("#isAcceptance").val()=="1"){
					$("#divreturn").dialog('open');
					$("#divreturn").dialog('center');
				}else
					$.messager.alert('系统提示','打回操作，需要签收后才能操作！','info');	
			});
			$("#aT_ZGBHJ,#aB_ZGBHJ").click(function() {
				var tempqz=$("#F_GLOSign").val().toString();
				var username=$("#userName").val().toString();
				if(tempqz.indexOf(username)>=0){
					$.messager.confirm('系统提示', '您已经签字,是否要重新签字？', function(r){
						if (r){
							//提交
							tempqz=tempqz.replace(username,username);
							$("#F_GLOSign").attr("value",tempqz);
							var cdate=new Date();			   								
							$("#F_Glosign_Time").text((cdate.getFullYear()+"-"+(cdate.getMonth()+1)+"-"+cdate.getDate()).replace(/\b(\w)\b/g, '0$1'));
							$.post("/Buss/CheckReviewService/12", {
									ProcessId: $("#processId").val(),
									AboveActId: $("#activeId").val(),
									Remark: "",
									formPKID: $("#FId").val(),
									FGLOSign: $("#F_GLOSign").val(),
									FGlosignTime: $("#F_Glosign_Time").text()
		   						}, function(data) {
		   							if (data.success){
		   								$.messager.alert('系统提示','签字成功。','info');	   							
		   							}
		   							else
		   								$.messager.alert('系统提示','签字失败！请重试。','info');
							}, "json");
						}
					});
				}else
					{						
						if(tempqz=="")
							tempqz=username;
						else
							tempqz=tempqz+","+username;						
						$.messager.confirm('系统提示', '您确定要签字吗？', function(r){
							if (r){
								$("#F_GLOSign").attr("value",tempqz);
								var cdate=new Date();			   								
								$("#F_Glosign_Time").text((cdate.getFullYear()+"-"+(cdate.getMonth()+1)+"-"+cdate.getDate()).replace(/\b(\w)\b/g, '0$1'));
								$.post("/Buss/CheckReviewService/12", {
										ProcessId: $("#processId").val(),
										AboveActId: $("#activeId").val(),
										Remark: "",
										formPKID: $("#FId").val(),
										FGLOSign: $("#F_GLOSign").val(),
										FGlosignTime: $("#F_Glosign_Time").text()
			   						}, function(data) {
			   							if (data.success){
			   								$.messager.alert('系统提示','签字成功。','info');	   							
			   							}
			   							else
			   								$.messager.alert('系统提示','签字失败！请重试。','info');
								}, "json");							
							}
						});
					}
			});
		});