//$(document).bind("contextmenu",function(){return false;});
		$(document).bind("selectstart",function(){return false;});  
		function showLoadings(){
			jQuery('#MainDiv').showLoading({ 'addClass': 'loading-indicator-bars'});
		}
		function hideLoadings(){
			jQuery('#MainDiv').hideLoading();
		}
		showLoadings();
		var action;
		var processId;
		var activeId;
		var formPKID;
		var state;
		var userobj;
		var menuFServiceCategory;
		var menuFBusinessCategory;
		var menuFIndustryCategoryName;
		var  options;
		var operate; // 记录操作
		var today = new Date();
		function  addDivClass(){
			$("#divDisable").addClass("div-disabled");
		}
		if($.browser.msie){
			if($.browser.version<=8){
				setActiveStyleSheet("papers.css");
			}else{				
				setActiveStyleSheet("papersother.css");
			}
		} else if($.browser.mozilla){
			    setActiveStyleSheet("papersother.css");
			}else
				setActiveStyleSheet("papersother.css");
		
		function setActiveStyleSheet(filename) {
			document.write("<link href=\"..\/..\/css\/"+filename+"\" type=\"text\/css\" rel=\"stylesheet\">");
		}
		var IFrame='<iframe src="{0}" style="width:100%;height:98%;border-width: 0px;overflow-x:hidden;overflow-y:hidden"/>';		
		function closeJBox(){
			// $.jBox.close(true);
		}
				
		function closeLightBox(){
			$.LightBoxObject.close();
		}
		function MessageInfo(Title,Msg){
			$.messager.show({
				title:Title,
				msg:Msg,
				showType:'fade',
				timeout:2000,
				style:{
					right:'',
					bottom:''
				}
			});
		}		
		function SelectEntrustUnit() {         
			var win = $(IFrame.format('/html/module/task/SelectEntrustUnit.html'));
			$.lightbox(win, {
			    modal  : true,
				width   : 560,
			    height  : 351
		   });
			return false;
		}
		function SelectDept() {
			var entitys={
		    		userId:0,
		    		userName:0,
		    		Station:"1007"
		    		// /可以加过滤条件
			};
			var entityJSON= Base64.encode($.toJSON(entitys));
			var win = $(IFrame.format('/html/module/controls/SelectDept.html?entitys=' +entityJSON));
			$.lightbox(win, {
			    modal  : true,
				width   : 560,
			    height  : 351
		   });
			return false;
		}
		function PostTask(id) {
			var entitys={
	    		userId:$('#userID').val(),
	    		userName:$('#userName').val(),
	    		Station:"ALL",
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
		function PostTaskExt() {
			
		}
		
		function todom(dom){
			var body = (window.opera) ? (document.compatMode == "CSS1Compat" ? $('html') : $('body')) : $('html,body');
			body.animate({scrollTop: $(dom).offset().top}, 500);
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
			   validData :function(msg,isShowMsg,easytype,maxChar){   // msg
																		// 例如：'不能为空！请仔细填写。'
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
		function SelectPM(id) {		
			$.jBox.open("iframe:/html/module/task/AppointTaskPM.html?ID=" + id, "选择项目经理", 610, 431, { buttons: { '关闭': true} });
			return false;
		}
		function BLQK(processId){
			var href="../wf/HandleStatus.html?processId="+processId;
			parent.NavigateUrl('wf_handle_'+processId,'办理情况',href);
			return false;
		}
		
		function FUN_Back(){
			if($("#OpinionMessageReturn").val() == '')
			{
				$.messager.alert('系统提示','打回意见不能为空，请填写打回意见！','info');
				return false;
			}
			$("#aBackbutn").attr("disabled",true);			
			$.post("/Buss/TaskService/6", {
						"AboveActId" : $("#activeId").val(),
						"FormFId": $("#FId").val(),
						"Remark" : $("#OpinionMessageReturn").val().trim()
					}, function(data) {
						if(data.success){
							$.messager.alert('系统提示','打回成功。请注意办理情况。','info');
							setButtonState("#aT_QSH,#aT_BACKTO,#aT_ZPPMBan,#aT_ADDPLAN,#aB_QSH,#aB_BACKTO,#aB_ZPPMBan,#aB_ADDPLAN",false);
							$("#divreturn").dialog('close');
						}else{
							$.messager.alert('系统提示','打回失败！检查网络状况是否正常,再重试.','info');
							$("#aBackbutn").attr("disabled",false);
						}
			}, "json");
		}
		
		function FUN_CuiBan(){
			if($("#OpinionMessageCuiBan").val() == '')
			{
				$.messager.alert('系统提示','催办意见不能为空，请填写催办意见！','info');
				return false;
			}
			$("#aCuiBanbutn").attr("disabled",true);
			$.post("/Buss/TaskService/10", {
						"AboveActId" : $("#activeId").val(),
						"Remark" : $("#OpinionMessageCuiBan").val()
					}, function(data) {
						if(data.success){
							$.messager.alert('系统提示','催办成功。请注意办理情况','info');
							$("#divcuiban").dialog('close');
						}
						else{
							$.messager.alert('系统提示','催办失败！检查网络状况是否正常,再重试.','info');
							$("#aCuiBanbutn").attr("disabled",false);
						}
			}, "json");
		}
		
		
		function progress(){
			
			/*
			 * setTimeout(function(){ $.messager.progress('close'); },5000)
			 */
		}
		
		// 记录修改记录
		function FUN_ModifyOk(){
			$("#ModifyOkbuttn").attr("disabled",true);
			$.post("/Buss/TaskService/14", {
				ProcessId: $("#processId").val(),
				ModifyMessage: $("#ModifyMessage").val()
				}, function(data) {
					if(data.success){						 
						switch (operate){
							case "save":
								var form = $("#formtask");
								form.ajaxSubmit(options);
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
					}else{
						$.messager.alert('系统提示','保存失败！检查网络状况是否正常,再重试.','info');
						$("#ModifyOkbuttn").attr("disabled",false);
					}
					$('#divmodifyinfo').dialog('close');
			}, "json");
		}
		
		function FN_SelectDept(entitys){			
			$("#FDepartmentId").val(entitys.deptId);
			$("#FDepartmentName").val(entitys.deptName);
			$("#FDeptMgrId").val(entitys.userId);
			$("#FDeptMgrName").val(entitys.userName);
			$.LightBoxObject.close();
		}
		var postStyle=1;
		//此方法未被使用
		function ToPart(entitys){
					if(postStyle==1){
						$.post("/Buss/TaskService/5", {
								"ProcessId" :  Request.QueryString("processId"),
								"AcceptUserId" : entitys.acceptUserId,
								"AboveActId" :  Request.QueryString("activeId"),
								"Remark" :entitys.remark,
								"formPKID":  Request.QueryString("formPKID"),
								"PFDepartmentId": entitys.partId,
								"PFDepartmentName": entitys.partName
							}, function(data) {
								if(data.success){
									$.LightBoxObject.close();
									MessageInfo('系统提示', '提交成功,请注意办理情况。');
									setButtonState("#aT_OK,#aT_Post,#aT_ZPPMBan,#aT_Cancel,#aB_OK,#aB_Post,#aB_Cancel,#aB_ZPPMBan",false);
								}else{
									$.LightBoxObject.close();
									MessageInfo('系统提示', '提交失败！检查网络状况是否正常,再重试.');
								}
						}, "json");
					}
					if(postStyle==2){
						var pmvidlist="";
						pmvidlist=  $("#FProjMgrViceId").combotree('getValues').toString();						
						$.post("/Buss/TaskService/7", {
							"ProcessId" :  Request.QueryString("processId"),
							"AcceptUserId" :  entitys.acceptUserId,
							"AboveActId" :  Request.QueryString("activeId"),
							"Remark" :entitys.remark,
							"formPKID":  Request.QueryString("formPKID"),
							"FProjMgrId": $("#FProjMgrId").combotree('getValue'),
							"FProjMgrName": $("#FProjMgrName").val(),
							"FProjMgrViceIdList": pmvidlist,
							"FProjMgrViceName": $("#FProjMgrViceId").val()
						}, function(data) {
								if(data.success){
									$.LightBoxObject.close();
									MessageInfo('系统提示', '提交成功,请注意办理情况。');
									setButtonState("#aT_OK,#aT_Post,#aT_ZPPMBan,#aT_Cancel,#aB_OK,#aB_Post,#aB_Cancel,#aB_ZPPMBan",false);
								}else{
									$.LightBoxObject.close();
									MessageInfo('系统提示', '提交失败！检查网络状况是否正常,再重试.');
								}
						}, "json");
					}
		}
		/*
		 * 设置是否读写
		 */
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
		function setFormButtonState(us,iaction,istate){				
			    fn_loadDeptUser($("#FDepartmentId").val()==0?-1:$("#FDepartmentId").val());
				setButtonState("#aT_OK,#aT_QSH,#aT_Post,#aT_ZPPMBan,#aT_Cancel,#aT_BACKTO,#aT_CuiBan,#aT_ADDPLAN",false);
				setButtonState("#aB_OK,#aB_QSH,#aB_Post,#aB_ZPPMBan,#aB_Cancel,#aB_BACKTO,#aB_CuiBan,#aB_ADDPLAN",false);
				setButtonState("#aSelectDept,#getTaskNumbers,#aSelectEU,#FServiceCategoryB,#FBusinessCategoryB,#FIndustryCategoryNameB",false);
				setElementState("#FTaskNumbers,#FTaskName,#FTaskShortName,#FEntrustUnitName,#FEntrustUnitShortName,#FProjectScale,#FStructureType,#FServiceCategory,#FBusinessCategory,"+
						"#FIndustryCategoryName,#FGivePersonName,#FGiveTime,#FReceivingTaskTime,#FContractYjCharge",false);
				$('#FProjectScaleUnit').combobox('disable');
				switch(iaction){
					case "0":{
						setButtonState("#aT_OK,#aT_Cancel,#aB_OK,#aB_Cancel",true);
						setButtonState("#getTaskNumbers,#aSelectEU,#FServiceCategoryB,#FBusinessCategoryB,#FIndustryCategoryNameB",true);										
						setElementState("#FTaskName,#FTaskShortName,#FProjectScale,#FReceivingTaskTime,#FContractYjCharge",true);
						$('#FStructureTypeId').combobox('enable');
						$('#FYjstartTime').datetimebox('enable');
						$('#FYjfinishTime').datetimebox('enable');
						$('#FContractYjCharge').numberbox('enable');
						$('#FProjectScaleUnit').combobox('enable');
						break;
					}
					case "1":{
						switch(istate){
							case "0":{	// 起草
								setButtonState("#aT_OK,#aT_Cancel,#aB_OK,#aB_Cancel",true);
								setButtonState("#getTaskNumbers,#aSelectEU,#FServiceCategoryB,#FBusinessCategoryB,#FIndustryCategoryNameB",true);
								setElementState("#FTaskName,#FTaskShortName,#FProjectScale,#FReceivingTaskTime,#FContractYjCharge",true);
								$('#FStructureTypeId').combobox('enable');
								$('#FYjstartTime').datetimebox('enable');
								$('#FYjfinishTime').datetimebox('enable');
								$('#FContractYjCharge').numberbox('enable');
								$('#FProjectScaleUnit').combobox('enable');
								break;
							}
							case "1":{	// 正在办理
								// 当前办理人不是登录者时处理
								if($("#userID").val() == $("#fAcceptUser").val()){
									setButtonState("#aSelectEU,#getTaskNumbers",false);
									switch($("#FCurrentStep").val().toString()){
										case "10":
											setButtonState("#aT_QSH,#aT_BACKTO,#aT_ZPPMBan,#aB_QSH,#aB_BACKTO,#aB_ZPPMBan",true);
											setButtonState("#aSelectDept",true);
											setElementState("#FTaskName,#FTaskShortName,#FProjectScale,#FReceivingTaskTime,#FContractYjCharge",true);
											break;
										case "20":
											setButtonState("#aT_QSH,#aT_BACKTO,#aT_ZPPMBan,#aB_QSH,#aB_BACKTO,#aB_ZPPMBan",true);
											if($("#isAcceptance").val()=="1"){
												$('#FProjMgrId').combotree('enable');
												$('#FProjMgrViceId').combotree('enable');
											}
											break;
										case "30":
											setButtonState("#aT_QSH,#aT_BACKTO,#aT_ADDPLAN,#aB_QSH,#aB_BACKTO,#aB_ADDPLAN",true);
											break;
										case "40":
											setButtonState("#aT_BLQK,#aB_BLQK",true);
											break;
									}
								}else
									{
										setButtonState("#aT_CuiBan,#aB_CuiBan",true);
									}
								break;
							}
							case "2":{	// 业务退回
								if($("#userID").val() == $("#fAcceptUser").val()){
									switch($("#FCurrentStep").val().toString()){
										case "10":
											setButtonState("#aT_OK,#aT_QSH,#aT_Post,#aB_OK,#aB_QSH,#aB_Post",true);
											setButtonState("#aSelectDept",true);
											
											setButtonState("#getTaskNumbers,#aSelectEU,#FServiceCategoryB,#FBusinessCategoryB,#FIndustryCategoryNameB",true);										
											setElementState("#FTaskName,#FTaskShortName,#FProjectScale,#FReceivingTaskTime,#FContractYjCharge",true);
											$('#FStructureTypeId').combobox('enable');
											$('#FYjstartTime').datetimebox('enable');
											$('#FYjfinishTime').datetimebox('enable');
											$('#FContractYjCharge').numberbox('enable');
											$('#FProjectScaleUnit').combobox('enable');
											break;
										case "20":
											setButtonState("#aT_QSH,#aT_ZPPMBan,#aB_QSH,#aB_ZPPMBan",true);
											setButtonState("#aSelectDept",false);
											if($("#isAcceptance").val()=="1"){
												$('#FProjMgrId').combotree('enable');
												$('#FProjMgrViceId').combotree('enable');
											}
											break;
										case "30":
											setButtonState("#aT_QSH,#aT_BACKTO,#aT_ADDPLAN,#aB_QSH,#aB_BACKTO,#aB_ADDPLAN",true);
											break;
										case "40":
											setButtonState("#aT_BLQK,#aB_BLQK",true);
											break;
									}
								}else{
										setButtonState("#aT_CuiBan,#aB_CuiBan",true);
									}
								break;
							}
							case "3":{	// 业务终止
								setButtonState("#aT_BLQK,#aB_BLQK",true);
								break;
							}
							case "4":{	// 办理完成
								setButtonState("#aT_BLQK,#aB_BLQK",true);
								break;
							}
							case "5":{	// 已归档
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
				if($("#isAcceptance").val()=="1") {// 已签收
					setButtonState("#aT_QSH,#aB_QSH",false);
				}				
		}
		function fn_initpage(){								
			$("#TitleYear").text(today.getFullYear());
			$("#FEntrustUnitName,#FStructureType,#FTaskNumbers,#FYjstartTime,#FYjfinishTime,#FGiveTime,#FDeptMgrName,#FProjMgrName,#FDepartmentName,#FGivePersonName,#FReceivingTaskTime").attr({readonly : 'true'});
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
			$("#FEntrustUnitShortName").attr({readonly:'true'});
			//$("#FServiceCategoryB,#FBusinessCategoryB,#FIndustryCategoryNameB").attr({readonly:'true'});			
			$("#FYjstartTime").datetimebox({
				value: '2012-01-01',  
				required: true,  
				showSeconds: false,
				editable: false
			});
			$("#FYjfinishTime").datetimebox({
				value: '2012-01-01',  
				required: true, 
				showSeconds: false,
				editable: false
			});
			$('#FYjstartTime').datetimebox('disable');
			$('#FYjfinishTime').datetimebox('disable');
			$('#FContractYjCharge').numberbox('disable');
		}
		function loadBLQK(processId){
			$("#qkTable").datagrid({
				title:'办理情况(近4次)',
				width: '98%',
				showHeader:false,
				nowrap:false,// false设置数据自动换行
				// height: 221,
				columns:[
					[{/* title:'类型', */field:'FStyle',width:66,align:'center',
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
					{/* title:'发送人员', */field:'sendUser',width:60,hidden:false},
					{/* title:'发送时间', */field:'FSendTime',width:131,align:'center'},
					{/* title:'接收人员', */field:'acceptUser',width:60,align:'center'},
					{/* title:'接收时间', */field:'FAcceptTime',width:131,align:'center'},
					{/* title:'办理状态', */field:'FStateText',width:70,align:'center'},
					{/* title:'完成时间', */field:'completeTime',width:131,align:'center'},
					{/* title:'备注', */field:'FRemark',width:135,align:'center'}						
					]],
				url: '/Buss/CommonOpinionService/6/?processId='+processId+"&toprecord="+4,
				rownumbers:false,fit:true,singleSelect:true,
				// pagination:true,
				onSelect: function(index,row){				
					$("#FId").attr("value",row.FId);
					$("#FContentT").text(row.FContent);
				},
				onLoadSuccess:function(){
				  // 所有列进行合并操作
				  // $(this).datagrid("autoMergeCells");
				  // 指定列进行合并操作
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
		function getPostData(){
			return {
				FId: $("#FId").val(),
				FTaskNumbers:$("#FTaskNumbers").val(),
				FTaskName:$("#FTaskName").val(),
				FTaskShortName:$("#FTaskShortName").val(),
				FEntrustUnitId:$("#FEntrustUnitId").val(),
				FEntrustUnitName:$("#FEntrustUnitName").val(),
				FEntrustUnitShortName:$("#FEntrustUnitShortName").val(),
				FProjectScale:$("#FProjectScale").val(),
				FContractYjCharge:$("#FContractYjCharge").numberbox('getValue'),
				FGivePersonId:$("#FGivePersonId").val(),
				FGivePersonName:$("#FGivePersonName").val(),
				FDeptMgrId:$("#FDeptMgrId").val(),
				FDeptMgrName:$("#FDeptMgrName").val(),
				FDepartmentId:$("#FDepartmentId").val(),
				FDepartmentName:$("#FDepartmentName").val(),
				FStructureTypeId:$("#FStructureTypeId").combobox('getValue'),
				FStructureType:$("#FStructureType").val(),
				FProjectScaleUnit:$("#FProjectScaleUnit").combobox('getValue'),
				FServiceCategory:$("#FServiceCategory").val(),
				FServiceCategoryId:$("#FServiceCategoryId").val(),
				FReceivingTaskTime:$("#FReceivingTaskTime").val(),			
				FYjfinishTime:$("#FYjfinishTime").val(),
				FGiveTime:$("#FGiveTime").val(),					
				FYjfinishTime:$("#FYjfinishTime").datetimebox('getValue'),
				FYjstartTime:$("#FYjstartTime").datetimebox('getValue'),								
				FIndustryCategoryName:$("#FIndustryCategoryName").val(),
				FIndustryCategoryId:$("#FIndustryCategoryId").val(),
				FBusinessCategory:$("#FBusinessCategory").val(),
				FBusinessCategoryId:$("#FBusinessCategoryId").val(),
				FProjMgrName:$("#FProjMgrId").combotree('getText').toString(),
				FProjMgrViceName:$("#FProjMgrViceId").combotree('getText').toString()
			};
		}
		function SetFormValue(data, type) {			
			$("#FId").attr("value", data.root[0].FId);
			$("#FCurrentStep").attr("value", data.root[0].FCurrentStep);
			$("#FLastStep").attr("value", data.root[0].FLastStep);
			$("#FTaskNumbers").attr("value",data.root[0].FTaskNumbers);
			$("#FTaskName").attr("value",data.root[0].FTaskName);
			$("#FTaskShortName").attr("value",data.root[0].FTaskShortName);
			$("#FEntrustUnitId").attr("value",data.root[0].FEntrustUnitId);
			$("#FEntrustUnitName").attr("value",data.root[0].FEntrustUnitName);
			$("#FEntrustUnitShortName").attr("value",data.root[0].FEntrustUnitShortName);
			$("#FProjectScale").attr("value",data.root[0].FProjectScale);
			// $("#FContractYjCharge").attr("value",data.root[0].FContractYjCharge);
			$('#FContractYjCharge').numberbox('setValue', data.root[0].FContractYjCharge);
			$("#FGivePersonId").attr("value",data.root[0].FGivePersonId);
			$("#FGivePersonName").attr("value",data.root[0].FGivePersonName);
			$("#FDeptMgrId").attr("value",data.root[0].FDeptMgrId);
			$("#FDeptMgrName").attr("value",data.root[0].FDeptMgrName);
			// $("#FProjMgrName").attr("value",data.root[0].FProjMgrName);
			/*
			 * 项目经理存入子表 $("#FProjMgrId").attr("value",data.root[0].FProjMgrId);
			 * $("#FProjMgrName").attr("value",data.root[0].FProjMgrName);
			 * if(data.root[0].FProjMgrViceId != "0"){
			 * $("#FProjMgrViceId").attr("value",data.root[0].FProjMgrViceId);
			 * $("#FProjMgrViceName").attr("value",data.root[0].FProjMgrViceName); }
			 */
			$("#FDepartmentId").val(data.root[0].FDepartmentId);
			$("#FDepartmentName").val(data.root[0].FDepartmentName);
			if(data.root[0].FStructureTypeId>0)				
				$("#FStructureTypeId").combobox('setValue', data.root[0].FStructureTypeId);
			$("#FStructureType").val(data.root[0].FStructureType);
			$("#FProjectScaleUnit").combobox('setValue', data.root[0].FProjectScaleUnit);	
			
			$("#FServiceCategory").val(data.root[0].FServiceCategory);// combotree('setValue',data.root[0].FServiceCategory);
			$("#FServiceCategoryId").val(data.root[0].FServiceCategoryId==0?"":data.root[0].FServiceCategoryId);
			$("#FReceivingTaskTime").val(data.root[0].FReceivingTaskTime);			
			$("#FYjfinishTime").val(data.root[0].FYjfinishTime);
			$("#FGiveTime").val(data.root[0].FGiveTime);								
			$("#FYjfinishTime").datetimebox('setValue', data.root[0].FYjfinishTime);
			$("#FYjstartTime").datetimebox('setValue', data.root[0].FYjstartTime);								
			
			$("#FIndustryCategoryName").val(data.root[0].FIndustryCategoryName);
			$("#FIndustryCategoryId").val( data.root[0].FIndustryCategoryId==0?"":data.root[0].FIndustryCategoryId);// combotree('setValues',
																													// data.root[0].FIndustryCategoryId.split(','));
			$("#FBusinessCategory").val(data.root[0].FBusinessCategory);// combotree('setValue',data.root[0].FBusinessCategory);
			$("#FBusinessCategoryId").val(data.root[0].FBusinessCategoryId==0?"":data.root[0].FBusinessCategoryId);
			if($("#FCurrentStep").val()=="10")				
			{
				menuFServiceCategory=CreateSelectMenu("/GetCode/Query/",{"action":"getAppTreeCodeToMenu","id":2,"isMulti":0},"FServiceCategoryId","FServiceCategory","FServiceCategoryB","s");
				menuFBusinessCategory=CreateSelectMenu("/GetCode/Query/",{"action":"getAppTreeCodeToMenu","id":2,"isMulti":0},"FBusinessCategoryId","FBusinessCategory","FBusinessCategoryB","s");
				menuFIndustryCategoryName=CreateSelectMenu("/GetCode/Query/",{"action":"getAppTreeCodeToMenu","id":3,"isMulti":0},"FIndustryCategoryId","FIndustryCategoryName","FIndustryCategoryNameB","m");
			}
			setFormButtonState($("#unitStation").val().toString(),$("#action").val().toString(),$("#fState").val().toString());
		}
		
		function fn_loadData(){
			if (action == 0) {
				$.post("/Buss/TaskService/3", {
					"ID" : formPKID,					
					"processId0": processId,
					"activeId": activeId
			     	}, function(data) {
			     		$("#FId").val(data.id);						
						$("#formPKID").attr("value",data.id);
						$("#FGivePersonId").val($("#userID").val());
						$("#FGivePersonName").val($("#userName").val());
						$("#FCurrentStep").val("10");
						$("#FLastStep").val("10");
						var cdate=new Date();						
						$("#FGiveTime").val((cdate.getFullYear()+"-"+(cdate.getMonth()+1)+"-"+cdate.getDate()).replace(/\b(\w)\b/g, '0$1'));						
						setFormButtonState($("#unitStation").val().toString(),$("#action").val().toString(),$("#fState").val().toString());
						if($("#FCurrentStep").val()=="10")				
						{							
							menuFServiceCategory=CreateSelectMenu("/GetCode/Query/",{"action":"getAppTreeCodeToMenu","id":2,"isMulti":0},"FServiceCategoryId","FServiceCategory","FServiceCategoryB","s");
							menuFBusinessCategory=CreateSelectMenu("/GetCode/Query/",{"action":"getAppTreeCodeToMenu","id":2,"isMulti":0},"FBusinessCategoryId","FBusinessCategory","FBusinessCategoryB","s");
							menuFIndustryCategoryName=CreateSelectMenu("/GetCode/Query/",{"action":"getAppTreeCodeToMenu","id":3,"isMulti":0},"FIndustryCategoryId","FIndustryCategoryName","FIndustryCategoryNameB","m");
						}
						hideLoadings();
					}, "json");
			} else if (action == 1 || action == 2) {
				$.post("/Buss/TaskService/1", {
					"ID" :  formPKID,
					"activeId":  activeId
				}, function(data) {
					if (data.success) {	
						var resultdata=data;
						$.post("/Buss/TaskService/8", {
							"ID" :  formPKID
						}, function(data) {								
							var idlist=new Array();
							var namelist=new Array();
							var i=0;
							if(data.success){								
								$.each(data.root,function(index,content)
								{		
									if(content.FPmType==1)
									{
										$('#FProjMgrId').combotree('setValue',content.FPmId);
										$('#FProjMgrName').attr("value",content.FPmName);
									}
									else
									{
										idlist[i]=content.FPmId;
										namelist[i]=content.FPmName;
										i++;
									}
								});								    
							    if(idlist!=-1){
									$('#FProjMgrViceId').combotree('setValues',idlist);
									$('#FProjMgrViceName').attr("value",namelist.toString());
							    }
							    hideLoadings();
							}
						},"json");						
						
					} else {										
						MessageInfo('系统提示', '任务获取失败!检查网络状况是否正常,再重试.');
						hideLoadings();
						return false;
					}
					SetFormValue(resultdata, "Edit");
					hideLoadings();
				}, "json");
			}
		}
		function fn_loadDeptUser(deptid){			
			$('#FProjMgrId').combotree ({								
				url: "/GetCode/OrgUserTreeExt/0/?DeptId="+deptid,
					panelWidth: 251,
					cascadeCheck:false,							
					required:false,
					onBeforeSelect:function(node){
						if(!$(this).tree('isLeaf',node.target)){
							return false;
						}
					},
					onClick:function(node){
						if(!$(this).tree('isLeaf',node.target)){
							$('#FProjMgrId').combo('showPanel');
							MessageInfo('系统提示','注意大类不能选择！');
						}
					}
				});
			$('#FProjMgrViceId').combotree ({								
				url: "/GetCode/OrgUserTreeExt/0/?DeptId="+deptid,
				panelWidth: 251,
				cascadeCheck:false,
				required:false,
				onBeforeSelect:function(node){
					if(!$(this).tree('isLeaf',node.target)){
						return false;
					}
				},
				onClick:function(node){
					if(!$(this).tree('isLeaf',node.target)){
						$('#FProjMgrViceId').combo('showPanel');
					}
				}
			});					
			$("#FProjMgrId").combotree('disable');
			$("#FProjMgrViceId").combotree('disable');
		}
		function fn_loadCode(){	 
			$('#FStructureTypeId').combobox({
				url:'/GetCode/Query/',  
				valueField:'FId',  
				textField:'FCodeText',
				editable: false,
				onBeforeLoad: function(param){
					param.action = "getAppCode";
					param.CodeTypeId = 314;
					param.State = 1;
				}
			});
			$('#FProjectScaleUnit').combobox({
				url:'/GetCode/Query/',  
				valueField:'FId',
				panelWidth: 151,
				textField:'FCodeText',
				editable: false,
				onBeforeLoad: function(param){
					param.action = "getAppCode";
					param.CodeTypeId = 422;
					param.State = 1;
				}
			});
			$('#FStructureTypeId').combobox('disable');			
		}
		// 判断任务名称是否存在
		function FUN_TASKISHAVE(taskname){			 
			$.post("/Buss/TaskService/12", {
				TaskName:taskname,
				FId: $("#formPKID").val()
				}, function(data) {
					if(data.success){
						$("#FTaskName").attr("value","");
						$("#FTaskName").focus();
						MessageInfo('系统提示', '任务名称已经存在！请重新命名。');
						return;
					}
			}, "json");
		}
		/**
		 * 提交到部门经理
		 */
		function FUN_Post() {
			
			$.messager.confirm('系统提示', '您确定要提交该任务到部门经理吗？<br/>承接部门为：'+$("#FDepartmentName").val()+", 部门经理为："+$("#FDeptMgrName").val()
				, function(r){
				if (r){
					showLoadings();	
					$.post("/Buss/TaskService/5", {
							"ProcessId" :  Request.QueryString("processId"),									
							"AboveActId" :  Request.QueryString("activeId"),
							"Remark" : "请尽快任命项目经理",
							"formPKID":  $("#FId").val(),// Request.QueryString("formPKID"),
							"PFDepartmentId": $("#FDepartmentId").val(),
							"PFDepartmentName": $("#FDepartmentName").val(),
							"PFDeptMgrName": $("#FDeptMgrName").val(),
							"AcceptUserId": $("#FDeptMgrId").val()
						}, function(data) {
							if(data.success){
								hideLoadings();
								MessageInfo('系统提示', '已成功提交到:'+$("#FDepartmentName").val()+' 接收人为:'+$("#FDeptMgrName").val()+',请注意办理情况。');
								setButtonState("#aT_OK,#aT_Post,#aT_ZPPMBan,#aT_Cancel,#aB_OK,#aB_Post,#aB_Cancel,#aB_ZPPMBan",false);
								setButtonState("#aT_CuiBan,#aB_CuiBan",true);
							}else{
								MessageInfo('系统提示', '提交失败！检查网络状况是否正常,再重试.');
								hideLoadings();
							}
					}, "json");
				}
			});
		}
		// 任命项目经理
		function FUN_ZPPM() {						
			if($('#FProjMgrId').combotree('getValue')==0 || $('#FProjMgrId').combotree('getValue')==""){							
				$.messager.alert('系统提示','项目经理不能为空,请选择项目经理。','info');
				return false;
			}
			showLoadings();
			var pmvidlist="";
			pmvidlist=  $("#FProjMgrViceId").combotree('getValues').toString();						
			$.post("/Buss/TaskService/7", {
				"ProcessId" :  Request.QueryString("processId"),
				"AcceptUserId" : $('#FProjMgrId').combotree('getValue'), // entitys.acceptUserId,
				"AboveActId" :  Request.QueryString("activeId"),
				"Remark" : "项目经理已经指派。",// entitys.remark,
				"formPKID":  Request.QueryString("formPKID"),
				"FProjMgrId": $("#FProjMgrId").combotree('getValue'),
				"FProjMgrName": $("#FProjMgrId").combotree('getText'),// $("#FProjMgrName").val(),
				"FProjMgrViceIdList": pmvidlist,
				"FProjMgrViceName": $("#FProjMgrViceId").combotree('getText'),// $("#FProjMgrViceName").val()
			}, function(data) {
					if(data.success){
						MessageInfo('系统提示', '项目经理任命成功。请注意办理情况。');
						hideLoadings();
						setButtonState("#aT_OK,#aT_Post,#aT_ZPPMBan,#aT_Cancel,#aT_CuiBan,#aT_BACKTO,#aB_OK,#aB_Post,#aB_Cancel,#aB_ZPPMBan,#aB_CuiBan,#aB_BACKTO",false);
						setButtonState("#aT_CuiBan,#aB_CuiBan",true);
					}else{
						MessageInfo('系统提示', '项目经理任命提交失败！检查网络状况是否正常,再重试.');
						hideLoadings();
					}
			}, "json");	
		}
		function fn_error(){
			MessageInfo('加载数据错误！检查网络状况是否正常,再重试.', '系统提示');
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
					fn_loadCode();
					fn_loadData();
					loadBLQK(processId);
				}else
				{
						userobj=null;
						hideLoadings();
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
		$(document).ready(function() {							
					ValidateUser(Request.QueryString("activeId"));					
					options = {
						target : '#output2',
						beforeSubmit : showRequest,
						success : showResponse,
							url : '/Buss/TaskService/2',
							type : 'post',
							dataType : 'json'
						};
					function showRequest(formData, jqForm, options) {
							/*
							 * if (!$("#formtask").validationEngine('validate')) {
							 * return false; }
							 */
					}
					// post-submit callback
					function showResponse(responseText, statusText,xhr, $form) {
						if (statusText == "success") {
							if (responseText.success) {
									$("#FId").attr("value", responseText.Id);										
									MessageInfo('系统提示', '任务保存成功。<br/>下一步请选择承接部门后,提交到部门经理。');	
									setButtonState("#aSelectDept",true);
									setButtonState("#aT_Post,#aB_Post",true);									
									hideLoadings();
							} else										
								MessageInfo('系统提示', '任务保存失败！检查网络状况是否正常,再重试.');
								hideLoadings();
							}
						}
					function FUN_Save() {
						// $("#FIndustryCategoryName").attr('value',$('#FIndustryCategoryId').combotree('getText'));
						 if($("#state").val()=="2")
							if($("#isAcceptance").val()=="0"){								 
								$.messager.alert('系统提示','保存操作，需要签收后才能操作！','info');
								return;
							}
						$.messager.confirm('系统提示', '您确定要保存该任务吗？', function(r){
							if (r){
								showLoadings();								
								var Id = $('input[name=FId]').fieldValue();								
								if (Id == "" || Id != null) {									
									var form = $("#formtask");
									// 判断是否进入流程，如果进入流程判断修改情况
									if($("#state").val()=="2"){
										$.post("/Buss/TaskService/13", {
												"JSONDATA" : JSON.stringify(getPostData())							
											}, function(data) {
													if(data.success){
														if(data.msg=="")
															form.ajaxSubmit(options);
														else{
														  // 记录历史
														  $('#divmodifyinfo').dialog('open');
														  $('#divmodifyinfo').dialog('center');
														  $('#ModifyMessage').attr('value',data.msg);
														  hideLoadings();
														}
													}
											}, "json");
									}else
										form.ajaxSubmit(options);
								}							
							}
						});
					}
			
					$("#aT_OK,#aB_OK").click(function() {
						var issave=false;						
						issave= $("#FTaskNumbers").validData("编号不能为空！",0,"",300);
						issave= issave && $("#FStructureTypeId").validData("结构类型不能为空！",1,"combobox",0);
						issave= issave && $("#FTaskName").validData("任务不能为空！",0,"",300);
						issave= issave && $("#FTaskShortName").validData("任务简称不能为空！",0,"",150);
						issave= issave && $("#FEntrustUnitName").validData("委托单位不能为空！",0,"",0);
						issave= issave && $("#FProjectScale").validData("项目规模不能为空！",0,"",100);
						issave= issave && $("#FContractYjCharge").validData("合同预计收费不能为空！",0,"",50);
						// 针对easyui 重写。
						// issave= issave &&
						// $("#FStructureType").validData("结构类型不能为空！",1,0);
						issave= issave && $("#FServiceCategory").validData("服务类别不能为空！",1,"",0);
						issave= issave && $("#FBusinessCategory").validData("业务类别不能为空！",1,"",0);
						issave= issave && $("#FIndustryCategoryId").validData("行业类别不能为空！",1,"",0);
						issave= issave && $("#FYjstartTime").validData("预计开始时间不能为空！",1,"datetimebox",0);
						issave= issave && $("#FYjfinishTime").validData("预计结束时间不能为空！",1,"datetimebox",0);
						if(issave){							
							$("#FStructureType").val($("#FStructureTypeId").combobox("getText"));
							operate="save";
							FUN_Save();
						}							
					});
					 
					$("#aT_Post,#aB_Post").click(function() {
						if($("#state").val()=="2")
							if($("#isAcceptance").val()=="0"){								 
								$.messager.alert('系统提示','提交操作，需要签收后才能操作！','info');
								return;
						}
						setButtonState("#aSelectDept",true);
						var issave=false;						
						issave= $("#FTaskNumbers").validData("编号不能为空！",0,"",300);
						issave= issave && $("#FStructureTypeId").validData("结构类型不能为空！",1,"combobox",0);
						issave= issave && $("#FTaskName").validData("任务不能为空！",0,"",300);
						issave= issave && $("#FTaskShortName").validData("任务简称不能为空！",0,"",150);
						issave= issave && $("#FEntrustUnitName").validData("委托单位不能为空！",0,"",0);
						issave= issave && $("#FProjectScale").validData("项目规模不能为空！",0,"",100);
						issave= issave && $("#FContractYjCharge").validData("合同预计收费不能为空！",0,"",50);
						// 针对easyui 重写。
						// issave= issave &&
						// $("#FStructureType").validData("结构类型不能为空！",1,0);
						issave= issave && $("#FServiceCategory").validData("服务类别不能为空！",1,"",0);
						issave= issave && $("#FBusinessCategory").validData("业务类别不能为空！",1,"",0);
						issave= issave && $("#FIndustryCategoryId").validData("行业类别不能为空！",1,"",0);
						issave= issave && $("#FYjstartTime").validData("预计开始时间不能为空！",1,"datetimebox",0);
						issave= issave && $("#FYjfinishTime").validData("预计结束时间不能为空！",1,"datetimebox",0);
						issave= issave && $("#FDepartmentName").validData("承接部门不能为空！,请选择承接部门.",0,"",0);
						issave= issave && $("#FDeptMgrName").validData("部门经理不能为空！",0,"",0);						 
						if(issave){
							operate="post";
							// 判断是否进入流程，如果进入流程判断修改情况
							if($("#state").val()=="2"){
								$.post("/Buss/TaskService/13", {
										"JSONDATA" : JSON.stringify(getPostData())							
									}, function(data) {
											if(data.success){
												if(data.msg=="")
													FUN_Post();
												else{
												  // 记录历史
												  $('#divmodifyinfo').dialog('open');
												  $('#divmodifyinfo').dialog('center');
												  $('#ModifyMessage').attr('value',data.msg);
												  hideLoadings();
												}
											}
									}, "json");
							}else
								FUN_Post();
						}
					});
					 
					function FUN_Cancel() {
						$.messager.confirm('系统提示', '您确定要撤销该任务吗？撤销后将彻底删除任务信息。', function(r){
							if (r){							
								var Id = $("#FId").val();
								$.post("/Buss/TaskService/4", {
									"ID" : Id,
									"ProcessId" : $("#processId").val(),
									"ActiveId" : $("#activeId").val()
			   						}, function(data) {
			   							if(data.success){	   						 				   							
				   							setButtonState("#aT_OK,#aT_QSH,#aT_Post,#aT_ZPPMBan,#aT_Cancel,#aT_BACKTO,#aT_CuiBan,#aT_ADDPLAN",false);
				   							setButtonState("#aB_OK,#aB_QSH,#aB_Post,#aB_ZPPMBan,#aB_Cancel,#aB_BACKTO,#aB_CuiBan,#aB_ADDPLAN",false);
				   							setButtonState("#aSelectDept,#getTaskNumbers,#aSelectEU,#FServiceCategoryB,#FBusinessCategoryB,#FIndustryCategoryNameB",false);	
			   							}else
			   								$.messager.alert('系统提示','任务撤销失败！检查网络状况是否正常,再重试.','info');
								}, "json");							
							}
						});					
					}
					$("#aT_Cancel,#aB_Cancel").click(function() {
						FUN_Cancel();
					});				 
					
					// 任命项目经理
					$("#aT_ZPPMBan,#aB_ZPPMBan").click(function() {
						if($("#isAcceptance").val()=="1"){
							operate="zppm";
							// 判断是否进入流程，如果进入流程判断修改情况
							if($("#state").val()=="2"){
								$.post("/Buss/TaskService/13", {
										"JSONDATA" : JSON.stringify(getPostData())							
									}, function(data) {
											if(data.success){
												if(data.msg=="")
													FUN_ZPPM();	
												else{
												  // 记录历史
												  $('#divmodifyinfo').dialog('open');
												  $('#divmodifyinfo').dialog('center');
												  $('#ModifyMessage').attr('value',data.msg);
												  hideLoadings();
												}
											}
									}, "json");
							}else
								FUN_ZPPM();	
						}else
							$.messager.alert('系统提示','任命项目经理操作，需要签收后才能操作！','info');	
					});				
									
					$("#aT_BLQK,#aB_BLQK").click(function() {
						BLQK($("#processId").val());
					});
					
					$("#aT_CuiBan,#aB_CuiBan").click(function() {
						$("#divcuiban").dialog('open');
						$("#divcuiban").dialog('center');
					});
					$("#aT_BACKTO,#aB_BACKTO").click(function() {
						if($("#isAcceptance").val()=="1"){
							$("#divreturn").dialog('open');
							$("#divreturn").dialog('center');
						}else
							$.messager.alert('系统提示','打回操作，需要签收后才能操作！','info');	
					});
					$("#FTaskName").blur(function(){
						// 判断任务名称是否重名
						FUN_TASKISHAVE($("#FTaskName").val().trim());
					});
					// 起草计划
					$("#aT_ADDPLAN,#aB_ADDPLAN").click(function() {									
						if($("#isAcceptance").val()=="0")
							{
								$.messager.alert('系统提示','起草任务计划操作，需要签收后才能操作！','info');								 
								return false;
							}						
						$.messager.confirm('系统提示', '您确定要起草任务计划吗？', function(r){
							if (r){															
								$.post("/GetNumber/FunForNumber/", {
											"year": today.getFullYear(),
											"taskname": "KMSD-"+$("#FEntrustUnitShortName").val()+"-"+$("#FTaskShortName").val(), 
											"numbercode": "JH"									
										}, function(data) {
											if (data.success) {
												var JHNmubers=data.Number;
												$.post("/Buss/TaskService/11", {
													"TaskNumber" :  $("#FTaskNumbers").val(),
													"TaskID" : $("#FId").val(),
													"TaskName" : $("#FTaskName").val(), 
													"ProcessId" : $("#processId").val(),									
													"AboveActId" :  $("#activeId").val(),
													"JHNmubers": JHNmubers
						   						}, function(data) {
						   							if (data.success){
						   								$.messager.alert('系统提示','任务计划起草成功。 请到待办事项中编制计划。','info');		   								
						   								setButtonState("#aT_QSH,#aT_BACKTO,#aT_ADDPLAN,#aB_QSH,#aB_BACKTO,#aB_ADDPLAN",false);			   								
						   							}
						   							else{
						   								$.messager.alert('系统提示','任务计划起草失败！检查网络状况是否正常,再重试.','info');
						   								setButtonState("#aT_QSH,#aT_BACKTO,#aT_ADDPLAN,#aB_QSH,#aB_BACKTO,#aB_ADDPLAN",true);
						   							}
												}, "json");
											}else
											{															
												$.messager.alert('系统提示','任务计划起草失败！检查网络状况是否正常,再重试.','error');
												setButtonState("#aT_QSH,#aT_BACKTO,#aT_ADDPLAN,#aB_QSH,#aB_BACKTO,#aB_ADDPLAN",true);
												return false;
											}
								}, "json");														
							}
						});
					});
					$("#aT_QSH,#aB_QSH").click(function() {
						var isUpdateOther=1;
						if($("#FReceivingTaskTime").val()=="")
							isUpdateOther=1;
						else
							if($("#FProjMgrName").val()=="")
								isUpdateOther=0;
						$.messager.confirm('系统提示', '您确定要签收任务吗？', function(r){
							if (r){							
								var Id = $("#FId").val();
								$.post("/Buss/TaskService/9", {
										"AboveActId" : activeId,
										"FormFId":Id,
										"isUpdateOther": isUpdateOther
			   						}, function(data) {
			   							if (data.success){
			   								$.messager.alert('系统提示','任务签收成功。','info');
			   								if(isUpdateOther==1){
				   								var cdate=new Date();			   								
				   								$("#FReceivingTaskTime").val((cdate.getFullYear()+"-"+(cdate.getMonth()+1)+"-"+cdate.getDate()).replace(/\b(\w)\b/g, '0$1'));
			   								}
			   								setButtonState("#aT_QSH,#aB_QSH",false);
			   								$('#FProjMgrId').combotree('enable');
											$('#FProjMgrViceId').combotree('enable');
											$("#isAcceptance").val("1");
			   							}
			   							else
			   								$.messager.alert('系统提示','任务签收失败！检查网络状况是否正常,再重试.','info');
								}, "json");							
							}
						});
					});
					$("#getTaskNumbers").click(function() {
						if($("#FTaskName").val()=="")
						{
							$.messager.alert('系统提示','任务名称名称!请先输入。','error');
							return true;
						}
						if($("#FTaskShortName").val()=="")
						{
							$.messager.alert('系统提示','任务简称不能为空!请输入。','error');
							return true;
						}
						// KMSD——昆供电办——通泰新城锦绣园工程
						$.post("/GetNumber/FunForNumber/", {
							"year": today.getFullYear(),
							"fid": $("#formPKID").val(),
							"taskname": "KMSD-"+$("#FEntrustUnitShortName").val()+"-"+$("#FTaskShortName").val(), 
							"numbercode": "RW"
								}, function(data) {
									if (data.success) {
										$("#FTaskNumbers").attr("value",data.Number);
									}else
									{															
										$.messager.alert('系统提示','编号生成失败！检查网络状况是否正常,再重试.','error');
										return false;
									}
						}, "json");
					});
		});	