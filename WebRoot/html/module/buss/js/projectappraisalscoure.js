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
				}else{				
					setActiveStyleSheet("papersother.css");					
					}
			} else if($.browser.mozilla){
				setActiveStyleSheet("papersother.css");
			} else{
				setActiveStyleSheet("papersother.css");
			}
			function setActiveStyleSheet(filename) {
				document.write("<link href=\"..\/..\/css\/"+filename+"\" type=\"text\/css\" rel=\"stylesheet\">");
			}

			var IFrame='<iframe src="{0}" style="width:100%;height:98%;border-width: 0px;overflow-x:hidden;overflow-y:hidden"/>';
			var action;
			var processId;
			var activeId;
			var formPKID;			
			var userobj;
			var state;
			var AcceptUserGrid;
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

			$.fn.extend({        
			   validData :function(msg){   //msg 例如：'不能为空！请仔细填写。'			   
				   var obj=$(this);
				   if(trim(obj.val())=="" || obj.val()=="该输入项为必输项！")
				   {
						obj.addClass("classerror");
					    obj.val("该输入项为必输项！");
					    obj.focus(function(){
							obj.val("");
							obj.removeClass("classerror");
					    });
						todom(this);
					    return false;
				   }else
						return true;
				}				
			});	
		    function InitAcceptUserGrid(deptid){
		    	AcceptUserGrid = $('#AcceptUserGrid').treegrid({
			         url: '/GetCode/OrgUserTreeEasyTree/?DEPTID='+deptid,
			         title: '部门人员列表',
			         rownumbers: true,
			         singleSelect: false,
			         idField: 'id',
			         treeField: 'deptname',
			         width: '98%',
			         height:231,
			         fitColumns: true,
					onLoadSuccess:function(rowIndex, rowData){
						for(var i=0;i<rowData.rows.length;i++){    
							if(rowData.rows[i].userid==0){        
								$("input[type='checkbox']")[i+1].disabled=true;							
							}   
						}     
					},
					onClickRow:function(row)
					{
						if(row.userid==0){						
							$('#AcceptUserGrid').treegrid('unselect',row.id);
						}
					  },
			         columns: [[{title: '序号',field: 'id',align: 'left',hidden:true
			         }, {title: '部门名称',field: 'deptname',width: 180
			         },  {title: '人员编号',field: 'userid',width: 80,align: 'right',checkbox: true
			         }, {title: '人员姓名',field: 'username',width: 280,align: 'right'
			         }, {title: '人员岗位',field: 'userunit',width: 280
			         }]]
			     });
		    }
			function FUN_POSTEASY(){
				 var ids = new Array;
				 var iddept = new Array;
	           	 var selectes = $('#AcceptUserGrid').treegrid('getSelections');
	           	 for(var i=0;i<selectes.length;i++){
					ids.push(selectes[i].id);
					iddept.push(selectes[i].deptname);					
				 }
				if(ids.length==0)
				{
					$.messager.alert('系统提示','人员不能为空,请选择人员！<br/>注意：总工办及生产部,每个部门各选一个人.','info');
					return false;
				}
				if(ids.length!=2){
					$.messager.alert('系统提示','注意：总工办及生产部,每个部门各选一个人！请检查','info');
					return false;
				}
				if(ids.length==2){
					if(iddept[0]==iddept[1]){
						$.messager.alert('系统提示','注意：总工办及生产部,每个部门各选一个人！请检查','info');
						return false;
					}
				}
				if($("#OpinionMessagePost").val() == '')
				{
					$.messager.alert('系统提示','提交人意见不能为空，请填写提交人意见！','info');
					return false;
				}
				//提交到公司打分
				showLoadings();				 	           	
	           	var FID0=getPostStringForIdList("FID0", "1,2,3,4,5,6,7,8,9,10,11,12,13");
	            var FDepartmentScore=getPostStringForIdList("FDepartmentScore", "1,2,3,4,5,6,7,8,9,10,11,12,13");
	            var FDeptScoreThat=getPostStringForIdList("FDeptScoreThat", "1,2,3,4,5,6,7,8,9,10,11,12,13");
	            $.post("/Buss/ProjAppraisalScoureService/8", {
	            	"ProcessId" :  $("#processId").val(),
					"AcceptUserId" : ids.toString(),
					"AboveActId" :  $("#activeId").val(),
					"Remark" : $("#OpinionMessagePost").val(),
					"formPKID": $("#formPKID").val(),
	            	"FID0": FID0,
	            	"FDepartmentScore": FDepartmentScore,
					"FDeptScoreThat": FDeptScoreThat
	            	}, function(data) {
					if (data.success) {
						MessageInfo('系统提示', '提交成功！');
						setButtonState("#aT_OK,#aT_Post,#aT_Return,#aT_CuiBan,#aB_OK,#aB_Post,#aB_Return,#aB_CuiBan",false);
						setButtonState("#aT_CuiBan,#aB_CuiBan",true);
						$("#getNumber,#aSelectTask").hide();
						$("#divpost").dialog('close');
						hideLoadings();

					}else
					{				
						MessageInfo('系统提示', '提交异常，请检查网络或联系系统管理员！');						
						$("#divpost").dialog('close');
						hideLoadings();
						return false;
					}
				}, "json");
			}
			function FUN_COMLETE(){
				if($("#isAcceptance").val()=="0"){
					 $.messager.alert('系统提示','提交操作，需要签收后才能操作！','info');
					 return ;
				}
				$.messager.confirm('系统提示', '您确定提交打分吗？', function(r){
					if (r){
						switch ($("#unitStation").val()) {			       
					        case "1002": {//生产副总
					        	showLoadings();	
					        	var FID0=getPostStringForIdList("FID0", "1,2,3,4,12,13");
					            var FCompanyScore=getPostStringForIdList("FCompanyScore", "1,2,3,4,12,13");
					            var FCTS=getPostStringForIdList("FCTS", "1,2,3,4,12,13");
								$.post("/Buss/ProjAppraisalScoureService/9", {
									"ProcessId" :  $("#processId").val(),
									"AcceptUserId" : "",
									"AboveActId" :  $("#activeId").val(),
									"Remark" : "",
									"formPKID": $("#formPKID").val(),
					            	"FID0": FID0,
					            	"FCompanyScore": FCompanyScore,
									"FCTS": FCTS
					            	}, function(data) {
									if (data.success) {	
										MessageInfo('系统提示', '提交成功！');
										setButtonState("#aT_OK,#aT_QSH,#aT_Post,#aT_Return,#aT_CuiBan,#aB_OK,#aB_QSH,#aB_Post,#aB_Return,#aB_CuiBan",false);
										$("#getNumber,#aSelectTask").hide();
										hideLoadings();
									}else
									{			
										MessageInfo('系统提示', '提交异常，请检查网络或联系系统管理员！');
										hideLoadings();
										return false;
									}
								}, "json");
					            break;
					        }
					        case "1003":
					        case "1006":{//总工办
					        	showLoadings();	
					        	var FID0=getPostStringForIdList("FID0", "5,6,7,8,9,10,11,12,13");
					            var FCompanyScore=getPostStringForIdList("FCompanyScore", "5,6,7,8,9,10,11,12,13");
					            var FCTS=getPostStringForIdList("FCTS", "5,6,7,8,9,10,11,12,13");
								$.post("/Buss/ProjAppraisalScoureService/10", {
									"ProcessId" :  $("#processId").val(),
									"AcceptUserId" : "",
									"AboveActId" :  $("#activeId").val(),
									"Remark" : "",
									"formPKID": $("#formPKID").val(),
					            	"FID0": FID0,
					            	"FCompanyScore": FCompanyScore,
									"FCTS": FCTS
					            	}, function(data) {
									if (data.success) {		
										MessageInfo('系统提示', '提交成功！');
										setButtonState("#aT_OK,#aT_QSH,#aT_Post,#aT_Return,#aT_CuiBan,#aB_OK#aB_QSH,#aB_Post,#aB_Return,#aB_CuiBan",false);
										$("#getNumber,#aSelectTask").hide();
										hideLoadings();
									}else
									{			
										MessageInfo('系统提示', '提交异常，请检查网络或联系系统管理员！');
										hideLoadings();
										return false;
									}
								}, "json");
					            break;
					        }
					        default: {
					            break;
					        }
					    }
					}
				});
			}
			function PostTask(id) {
						var entitys={
							userId:$('#userID').val(),
							userName:$('#userName').val(),
							ID:id
						};
						var entityJSON= Base64.encode($.toJSON(entitys));
						var win = $(IFrame.format('/html/module/controls/PostPAS.html?entitys=' +entityJSON));
						$.lightbox(win, {
							modal  : true,
							width   : 550,
							height  : 350
					   });						
					return false;
				}
			function BLQK(processId){
				var href="../wf/HandleStatus.html?processId="+processId;
				parent.NavigateUrl('wf_handle_'+processId,'办理情况',href);
				return false;
			}

			function getTaskInfo(taskid,taskname,taskservertype,tasknumber,deptmgrid){
				$.LightBoxObject.close();				
				$("#ftaskid").attr("value",taskid);
				$("#ftaskname").attr("value",taskname);	
				showLoadings();
				$.post("/Buss/ProjAppraisalScoureService/7", {"TaskID":taskid}, function(data) {
					if (data.success) {			
						var jsonlist= data.root.toString();						
						var arraylist= jsonlist.split(',');						
						$("#ftaskmanlist").attr("value",arraylist[3].toString());
						$("#pmlist").attr("value",arraylist[2].toString());						 
						$.post("/GetNumber/FunForNumber/", {
							"year": new Date().getFullYear(),
							"taskname": tasknumber.substring(0,tasknumber.length-11), 
							"fid": $("#formPKID").val(),
							"numbercode": "KP"
								}, function(data) {
									if (data.success) {
										$("#FKPNumbers").attr("value",data.Number);
									}else
									{															
										return false;
									}
						}, "json");	
						hideLoadings();
					}else
					{															
						MessageInfo('系统提示', '获取任务异常，请检查网络或联系系统管理员！');
						hideLoadings();
						return false;
					}
				}, "json");
			}
			function getPostStringForIdList(fieldname,idlist) {
			    var idarry = new Array();
			    idarry = idlist.split(',');
			    var temp = "";
			    for (var i = 0; i < idarry.length; i++) {
			        temp += ($("#"+fieldname+idarry[i]).val()==""?" ":$("#"+fieldname+idarry[i]).val())+",";
			    }
			    return temp.substring(0,temp.length-1);
			}
			function ToPart(entitys){				
			    switch ($("#unitStation").val()) {
			        case "1007": {//dept
			        	var FID0=getPostStringForIdList("FID0", "1,2,3,4,5,6,7,8,9,10,11,12,13");
			            var FDepartmentScore=getPostStringForIdList("FDepartmentScore", "1,2,3,4,5,6,7,8,9,10,11,12,13");
			            var FDeptScoreThat=getPostStringForIdList("FDeptScoreThat", "1,2,3,4,5,6,7,8,9,10,11,12,13");
			            $.post("/Buss/ProjAppraisalScoureService/8", {
			            	"ProcessId" :  $("#processId").val(),
							"AcceptUserId" : entitys.acceptUserId,
							"AboveActId" :  $("#activeId").val(),
							"Remark" : entitys.remark,
							"formPKID": $("#formPKID").val(),
			            	"FID0": FID0,
			            	"FDepartmentScore": FDepartmentScore,
							"FDeptScoreThat": FDeptScoreThat
			            	}, function(data) {
							if (data.success) {
								$.LightBoxObject.close();							
								MessageInfo('系统提示', '提交成功！');
								setButtonState("#aT_OK,#aT_Post,#aT_Return,#aT_CuiBan,#aB_OK,#aB_Post,#aB_Return,#aB_CuiBan",false);
								$("#getNumber,#aSelectTask").hide();

							}else
							{				
								$.LightBoxObject.close();								
								MessageInfo('系统提示', '提交异常，请检查网络或联系系统管理员！');
								return false;
							}
						}, "json");
			            break;
			        }
			        case "1002": {//生产副总
			        	var FID0=getPostStringForIdList("FID0", "1,2,3,4,12,13");
			            var FCompanyScore=getPostStringForIdList("FCompanyScore", "1,2,3,4,12,13");
			            var FCTS=getPostStringForIdList("FCTS", "1,2,3,4,12,13");
						$.post("/Buss/ProjAppraisalScoureService/9", {
							"ProcessId" :  $("#processId").val(),
							"AcceptUserId" : entitys.acceptUserId,
							"AboveActId" :  $("#activeId").val(),
							"Remark" : entitys.remark,
							"formPKID": $("#formPKID").val(),
			            	"FID0": FID0,
			            	"FCompanyScore": FCompanyScore,
							"FCTS": FCTS
			            	}, function(data) {
							if (data.success) {	
								$.LightBoxObject.close();								
								MessageInfo('系统提示', '提交成功！');
								setButtonState("#aT_OK,#aT_Post,#aT_Return,#aT_CuiBan,#aB_OK,#aB_Post,#aB_Return,#aB_CuiBan",false);
								$("#getNumber,#aSelectTask").hide();
							}else
							{			
								$.LightBoxObject.close();								
								MessageInfo('系统提示', '提交异常，请检查网络或联系系统管理员！');
								return false;
							}
						}, "json");
			            break;
			        }
			        case "1003": {//总工办
			        	var FID0=getPostStringForIdList("FID0", "5,6,7,8,9,10,11,12,13");
			            var FCompanyScore=getPostStringForIdList("FCompanyScore", "5,6,7,8,9,10,11,12,13");
			            var FCTS=getPostStringForIdList("FCTS", "5,6,7,8,9,10,11,12,13");
						$.post("/Buss/ProjAppraisalScoureService/10", {
							"ProcessId" :  $("#processId").val(),
							"AcceptUserId" : entitys.acceptUserId,
							"AboveActId" :  $("#activeId").val(),
							"Remark" : entitys.remark,
							"formPKID": $("#formPKID").val(),
			            	"FID0": FID0,
			            	"FCompanyScore": FCompanyScore,
							"FCTS": FCTS
			            	}, function(data) {
							if (data.success) {		
								$.LightBoxObject.close();									
								MessageInfo('系统提示', '提交成功！');
								setButtonState("#aT_OK,#aT_Post,#aT_Return,#aT_CuiBan,#aB_OK,#aB_Post,#aB_Return,#aB_CuiBan",false);
								$("#getNumber,#aSelectTask").hide();
							}else
							{			
								$.LightBoxObject.close();								
								MessageInfo('系统提示', '提交异常，请检查网络或联系系统管理员！');
								return false;
							}
						}, "json");
			            break;
			        }
			        default: {
			            break;
			        }
			    }
			}
			function setControlDisabled(name,idlist,flag){
				var tempidlist=idlist.split(",");
				var tempname="";
				for(var i=0;i<tempidlist.length;i++){
					if(i==0)
						tempname=name+tempidlist[i];
					else
						tempname+=tempname+","+tempidlist[i];
				}
				$(tempname).attr("disabled",flag);
			}
			function setHidden(){
				//////////////////////////设置 填写项是否 显示/////////////////////////////////////////////////////////
					 switch ($("#unitStation").val()) {
						case "1007": {//dept
							for(var i=1;i<=13;i++){
								$("#FCTS"+i).hide();
								if(i==1 || i==5 || i==13)
									$("#FCompanyScore"+i).hide();
								else
									$("#spanFCompanyScore"+i).hide();
								$("#FComprehensiveScore"+i).hide();
							}
							break;
						}
						case "1002": {//生产副总
							for(var i=1;i<=13;i++){								
								if(i==1 || i==5 || i==13)
									$("#FDepartmentScore"+i).hide();
								else
									$("#spanFDepartmentScore"+i).hide();
								$("#FDeptScoreThat"+i).hide();
								$("#FComprehensiveScore"+i).hide();
							}
							$("#FCompanyScore5").hide();
							$("#spanFCompanyScore6,#spanFCompanyScore7,#spanFCompanyScore8,#spanFCompanyScore9,#spanFCompanyScore10,#spanFCompanyScore11").hide();
							$("#FCTS5,#FCTS6,#FCTS7,#FCTS8,#FCTS9,#FCTS10,#FCTS11").hide();
							break;
						}
						case "1003":
						case "1006":{//总工办
							for(var i=1;i<=13;i++){								
								if(i==1 || i==5 || i==13)
									$("#FDepartmentScore"+i).hide();
								else
									$("#spanFDepartmentScore"+i).hide();
								$("#FDeptScoreThat"+i).hide();
							}
							$("#FCompanyScore1").hide();
							$("#spanFCompanyScore2,#spanFCompanyScore3,#spanFCompanyScore4,#spanFCompanyScore12").hide();
							$("#FComprehensiveScore1,#FComprehensiveScore2,#FComprehensiveScore3,#FComprehensiveScore4,#FComprehensiveScore12").hide();
							$("#FCTS1,#FCTS2,#FCTS3,#FCTS4,#FCTS12").hide();
							break;
						}					
					}
					if($("#state").val().toString()=="4")
					{
						for(var i=1;i<=13;i++){								
							$("#FCTS"+i).show();
							$("#FDepartmentScore"+i).show();
							$("#spanFDepartmentScore"+i).show();
							$("#FDeptScoreThat"+i).show();
							$("#FComprehensiveScore"+i).show();
							$("#FCompanyScore"+i).show();
							$("#spanFCompanyScore"+i).show();
							$("#FComprehensiveScore"+i).show();							
						}
					}
			}
			function setButtonState(id,state){
				if(state)
					$(id).show();
				else
					$(id).hide();
			}
			function setFormButtonState(us,iaction,istate){
				setButtonState("#aT_OK,#aT_QSH,#aT_Post,#aT_Return,#aT_CuiBan",false);
				setButtonState("#aB_OK,#aB_QSH,#aB_Post,#aB_Return,#aB_CuiBan",false);
				$("#ftaskname,#ftaskmanlist,#FAppraisalGroup,#pmlist").attr("readonly","readonly");
				$("#aSelectTask").hide();
				switch(iaction){
					case "0":{
						setButtonState("#aT_OK,#aB_OK",true);
						$("#aSelectTask").show();
						break;
					}
					case "1":{
						switch(istate){
							case "0":{	//起草
								setButtonState("#aT_OK,#aB_OK",true);
								$("#aSelectTask").show();
								break;
							}
							case "1":{	//正在办理								
								//当前办理人不是登录者时处理
								if($("#userID").val() == $("#fAcceptUser").val()){									
									switch($("#FCurrentStep").val().toString()){
										case "10":
											setButtonState("#aT_OK,#aB_OK",true);
											$("#aSelectTask").show();
											break;
										case "20":
											setButtonState("#aT_QSH,#aT_Post,#aB_QSH,#aB_Post",true);
											break;
										case "30":
											setButtonState("#aT_QSH,#aT_Post,#aB_QSH,#aB_Post",true);
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
										case "20":
											setButtonState("#aT_Post,#aT_QSH,#aB_Post,#aB_QSH",true);
											$('#F_ResultsType').combobox('disable');
											$('#F_SubmitMaterials').combobox('disable');
											break;
										case "30":
											setButtonState("#aT_Post,#aT_QSH,#aB_Post,#aB_QSH",true);
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
			function SetFormValue(data, type) {
				$("#formPKID").attr("value", data.root[0].FPasId);
				$("#FPasId").attr("value", data.root[0].FPasId);
				$("#FKPNumbers").attr("value", data.root[0].FNumber);
				$("#FCurrentStep").attr("value",data.root[0].FCurrentStep);
				$("#FLastStep").attr("value",data.root[0].FLastStep);
				
				var taskid=data.root[0].fkTaskId;
				$.post("/Buss/ProjAppraisalScoureService/7", {"TaskID":taskid}, function(data) {
					if (data.success) {			
						var jsonlist= data.root.toString();						
						var arraylist= jsonlist.split(',');
						$("#ftaskid").attr("value",arraylist[0].toString());
						$("#ftaskname").attr("value",arraylist[1].toString());
						$("#ftaskmanlist").attr("value",arraylist[3].toString());
						$("#pmlist").attr("value",arraylist[2].toString());											
					}
					//TODO 断网未获取数据呢么处理
				}, "json");
				
				var j=0;
				$("#FAppraisalGroup").attr("value",data.root[0].FAppraisalGroup);					
				for(var i=0;i<=12;i++)
				{
					j=i+1;
					$("#FID0"+j).attr("value",data.root[i].FId);
					//部门打分
					if(j==1 || j==5 || j==13)
						$("#FDepartmentScore"+j).numberbox('setValue', data.root[i].FDepartmentScore);
					else
						$("#FDepartmentScore"+j).numberspinner('setValue', data.root[i].FDepartmentScore);
					//公司打分
					if(j==1 || j==5 || j==13)
						$("#FCompanyScore"+j).numberbox('setValue', data.root[i].FCompanyScore);
					else
						$("#FCompanyScore"+j).numberspinner('setValue', data.root[i].FCompanyScore);
					//综合打分无需赋值
					//$("#FComprehensiveScore"+j).numberbox('setValue', data.root[i].FComprehensiveScore);						
					$("#FDeptScoreThat"+j).attr("value",data.root[i].FDeptScoreThat);
					$("#FCTS"+j).attr("value",data.root[i].FCompanyThatScore);							
				}
				setHidden();
				setFormButtonState($("#unitStation").val().toString(),$("#action").val().toString(),$("#fState").val().toString());
			}
			function fn_loadData(){//LoadData(){
				switch(action.toString()){					
					case "0": {
						$.post("/Buss/ProjAppraisalScoureService/3", {
						"ID" : formPKID,
						"processId0": processId,
						"activeId": activeId,
						}, function(data) {							
							SetFormValue(data, "New");
							hideLoadings();
						}, "json");						
						break;
					}
					case "1": {						
						$.post("/Buss/ProjAppraisalScoureService/1", {
						"ID" : formPKID,
						"processId0": processId,
						"activeId": activeId,
						}, function(data) {							
							SetFormValue(data, "Edit");
							hideLoadings();
						}, "json");	
						break;
					}
					case "2": {
						//addDivClass();						
						$.post("/Buss/ProjAppraisalScoureService/1", {
							"ID" : formPKID,
							"processId0": processId,
							"activeId": activeId,
							}, function(data) {							
								SetFormValue(data, "View");
								hideLoadings();
							}, "json");	
							break;	
							//addDivClass();
					}
				}
			}
			function getSaveData(){
				var jsonData;
				var FID0,FDepartmentScore,FDeptScoreThat;
				var FCompanyScore,FCTS;
				switch($("#unitStation").val()){
					case "1007":{
						FID0=getPostStringForIdList("FID0", "1,2,3,4,5,6,7,8,9,10,11,12,13");
						FDepartmentScore=getPostStringForIdList("FDepartmentScore", "1,2,3,4,5,6,7,8,9,10,11,12,13");
						FDeptScoreThat=getPostStringForIdList("FDeptScoreThat", "1,2,3,4,5,6,7,8,9,10,11,12,13");
						jsonData={
							"processId": $("#processId").val(),
							"TaskID": $("#ftaskid").val(),
							"FPasId": $("#FPasId").val(),
							"Title": $("#ftaskname").val(),
							"FNumber": $("#FKPNumbers").val(),
							"WhoSave": 1,
							"FID0": FID0,
		            		"FDepartmentScore": FDepartmentScore,
							"FDeptScoreThat": FDeptScoreThat
						};
						break;
					}
					case "1002":{
						FID0=getPostStringForIdList("FID0", "1,2,3,4,12,13");
						FCompanyScore=getPostStringForIdList("FCompanyScore", "1,2,3,4,12,13");
						FCTS=getPostStringForIdList("FCTS", "1,2,3,4,12,13");
						jsonData={
							"processId": $("#processId").val(),
							"TaskID": $("#ftaskid").val(),
							"FPasId": $("#FPasId").val(),
							"WhoSave": 2, 
							"FID0": FID0,
		            		"FCompanyScore": FCompanyScore,
							"FCTS": FCTS
						};
						break;
					}
					case "1003":
					case "1006":{
						FID0=getPostStringForIdList("FID0", "5,6,7,8,9,10,11,12,13");
						FCompanyScore=getPostStringForIdList("FCompanyScore", "5,6,7,8,9,10,11,12,13");
						FCTS=getPostStringForIdList("FCTS", "5,6,7,8,9,10,11,12,13");
						jsonData={
							"processId": $("#processId").val(),
							"TaskID": $("#ftaskid").val(),
							"FPasId": $("#FPasId").val(),
							"WhoSave": 3,  
							"FID0": FID0,
		            		"FCompanyScore": FCompanyScore,
							"FCTS": FCTS
						};
						break;
					}
					default:
					{
						jsonData={};
						break;
					}
				}
				return jsonData;
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
			
			function FUN_Save() {					
				$.messager.confirm('系统提示', '您确定要保存吗？', function(r){
					if (r){							
						$.post("/Buss/ProjAppraisalScoureService/2",getSaveData(), function(data) {
							if (data.success) {																					
								MessageInfo('系统提示', '保存成功。');
								setButtonState("#aT_Post,#aB_Post",true);
							}else
							{									
								MessageInfo('系统提示', '保存失败，请检查网络或联系系统管理员！');
								//$.LightBoxObject.close();
								return false;
							}
						}, "json");
					}
				});							
			}								
			 
			function FUN_Post() {
				PostTask($('input[name=FPasId]').fieldValue());					
			}
			function loadBLQK(){
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
				$("#FKPNumbers,#ftaskname,#ftaskmanlist").attr({readonly:true});
				$('#FDepartmentScore1,#FDepartmentScore5,#FDepartmentScore13').numberbox('disable');
				$('#FCompanyScore1,#FCompanyScore5,#FCompanyScore13').numberbox('disable');
				$('#FComprehensiveScore1,#FComprehensiveScore2,#FComprehensiveScore3,#FComprehensiveScore4,#FComprehensiveScore5,#FComprehensiveScore6,#FComprehensiveScore7,#FComprehensiveScore8,#FComprehensiveScore9,#FComprehensiveScore10,#FComprehensiveScore11,#FComprehensiveScore12,#FComprehensiveScore13').numberbox('disable');
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
			$(document).ready(function() {
				ValidateUser(Request.QueryString("activeId")); 
				$("#aT_Post,#aB_Post").click(function() {
					if($("#FCurrentStep").val()=="10")
					{
						$("#divpost").dialog('open');
						$("#divpost").dialog('center');
						//InitAcceptUserGrid(-1);
						InitAcceptUserGrid("33,35");
					}else if($("#FCurrentStep").val()=="20")
							{
								FUN_COMLETE();
							}
				});				 

				$("#aT_OK,#aB_OK").click(function() {
					var issave=$("#FKPNumbers").validData("编号不能为空！");
					issave = issave && $("#ftaskname").validData("任务不能为空！");					
					if(issave)
						FUN_Save();
				});				 
				
				$("#aT_BLQK,#aB_BLQK").click(function() {
					BLQK($("#processId").val());
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
		   							}
		   							else
		   								$.messager.alert('系统提示','任务签收失败！请重试。','info');
							}, "json");							
						}
					});
				});
				///计算部门

				$("#FDepartmentScore2,#FDepartmentScore3").numberspinner({
					onChange: function(newValue, oldValue){
						$("#FDepartmentScore1").numberbox("setValue",parseInt($("#FDepartmentScore2").numberspinner("getValue"))+parseInt($("#FDepartmentScore3").numberspinner("getValue")));
						//设置合计
						$("#FDepartmentScore13").numberbox("setValue",
						parseInt($("#FDepartmentScore1").numberbox("getValue"))+parseInt($("#FDepartmentScore4").numberspinner("getValue"))+parseInt($("#FDepartmentScore5").numberbox("getValue"))+parseInt($("#FDepartmentScore12").numberspinner("getValue"))-parseInt($("#FDepartmentScore11").numberspinner("getValue")));
						$("#FComprehensiveScore2").numberbox("setValue",parseInt($("#FDepartmentScore2").numberspinner("getValue"))+parseInt($("#FCompanyScore2").numberspinner("getValue")));
						$("#FComprehensiveScore3").numberbox("setValue",parseInt($("#FDepartmentScore3").numberspinner("getValue"))+parseInt($("#FCompanyScore3").numberspinner("getValue")));
					}
				});
				$("#FDepartmentScore4,#FDepartmentScore12").numberspinner({
					onChange: function (newValue, oldValue) {
						$("#FDepartmentScore13").numberbox("setValue",//.attr("value",
						//parseInt($("#FDepartmentScore1").val())+parseInt($("#FDepartmentScore4").val())+parseInt($("#FDepartmentScore5").val())+parseInt($("#FDepartmentScore12").val()));
						parseInt($("#FDepartmentScore1").numberbox("getValue"))+parseInt($("#FDepartmentScore4").numberspinner("getValue"))+parseInt($("#FDepartmentScore5").numberbox("getValue"))+parseInt($("#FDepartmentScore12").numberspinner("getValue"))-parseInt($("#FDepartmentScore11").numberspinner("getValue")));

						$("#FComprehensiveScore4").numberbox("setValue",parseInt($("#FDepartmentScore4").numberspinner("getValue"))+parseInt($("#FCompanyScore4").numberspinner("getValue")));
						$("#FComprehensiveScore12").numberbox("setValue",parseInt($("#FDepartmentScore12").numberspinner("getValue"))+parseInt($("#FCompanyScore12").numberspinner("getValue")));
					}
				});
				$("#FDepartmentScore1,#FDepartmentScore5").numberbox({
					onChange: function (newValue, oldValue) {						
						$("#FDepartmentScore13").numberbox("setValue",
						parseInt($("#FDepartmentScore1").numberbox("getValue"))+parseInt($("#FDepartmentScore4").numberspinner("getValue"))+parseInt($("#FDepartmentScore5").numberbox("getValue"))+parseInt($("#FDepartmentScore12").numberspinner("getValue"))-parseInt($("#FDepartmentScore11").numberspinner("getValue")));

						$("#FComprehensiveScore1").numberbox("setValue",parseInt($("#FDepartmentScore1").numberbox("getValue"))+parseInt($("#FCompanyScore1").numberbox("getValue")));
						$("#FComprehensiveScore5").numberbox("setValue",parseInt($("#FDepartmentScore5").numberbox("getValue"))+parseInt($("#FCompanyScore5").numberbox("getValue")));
					}
				});

				$("#FDepartmentScore6,#FDepartmentScore7,#FDepartmentScore8,#FDepartmentScore9,#FDepartmentScore10").numberspinner({
					onChange: function (newValue, oldValue) {
						$("#FDepartmentScore5").numberbox("setValue",
						parseInt($("#FDepartmentScore6").numberspinner("getValue"))+parseInt($("#FDepartmentScore7").numberspinner("getValue"))+parseInt($("#FDepartmentScore8").numberspinner("getValue"))+parseInt($("#FDepartmentScore9").numberspinner("getValue"))+parseInt($("#FDepartmentScore10").numberspinner("getValue")));
						
						$("#FComprehensiveScore6").numberbox("setValue",parseInt($("#FDepartmentScore6").numberspinner("getValue"))+parseInt($("#FCompanyScore6").numberspinner("getValue")));
						$("#FComprehensiveScore7").numberbox("setValue",parseInt($("#FDepartmentScore7").numberspinner("getValue"))+parseInt($("#FCompanyScore7").numberspinner("getValue")));
						$("#FComprehensiveScore8").numberbox("setValue",parseInt($("#FDepartmentScore8").numberspinner("getValue"))+parseInt($("#FCompanyScore8").numberspinner("getValue")));
						$("#FComprehensiveScore9").numberbox("setValue",parseInt($("#FDepartmentScore9").numberspinner("getValue"))+parseInt($("#FCompanyScore9").numberspinner("getValue")));
						$("#FComprehensiveScore10").numberbox("setValue",parseInt($("#FDepartmentScore10").numberspinner("getValue"))+parseInt($("#FCompanyScore10").numberspinner("getValue")));

					}
				});
				///////计算公司
				$("#FCompanyScore2,#FCompanyScore3").numberspinner({
					onChange: function(newValue, oldValue){
						$("#FCompanyScore1").numberbox("setValue",parseInt($("#FCompanyScore2").numberspinner("getValue"))+parseInt($("#FCompanyScore3").numberspinner("getValue")));
						//设置合计
						$("#FCompanyScore13").numberbox("setValue",
						parseInt($("#FCompanyScore1").numberbox("getValue"))+parseInt($("#FCompanyScore4").numberspinner("getValue"))+parseInt($("#FCompanyScore5").numberbox("getValue"))+parseInt($("#FCompanyScore12").numberspinner("getValue"))-parseInt($("#FCompanyScore11").numberspinner("getValue")));

						$("#FComprehensiveScore2").numberbox("setValue",parseInt($("#FDepartmentScore2").numberspinner("getValue"))+parseInt($("#FCompanyScore2").numberspinner("getValue")));
						$("#FComprehensiveScore3").numberbox("setValue",parseInt($("#FDepartmentScore3").numberspinner("getValue"))+parseInt($("#FCompanyScore3").numberspinner("getValue")));
					}
				});

				$("#FCompanyScore4,#FCompanyScore12").numberspinner({
					onChange: function (newValue, oldValue) {
						$("#FCompanyScore13").numberbox("setValue",
						parseInt($("#FCompanyScore1").numberbox("getValue"))+parseInt($("#FCompanyScore4").numberspinner("getValue"))+parseInt($("#FCompanyScore5").numberbox("getValue"))+parseInt($("#FCompanyScore12").numberspinner("getValue"))-parseInt($("#FCompanyScore11").numberspinner("getValue")));

						$("#FComprehensiveScore4").numberbox("setValue",parseInt($("#FDepartmentScore4").numberspinner("getValue"))+parseInt($("#FCompanyScore4").numberspinner("getValue")));
						$("#FComprehensiveScore12").numberbox("setValue",parseInt($("#FDepartmentScore12").numberspinner("getValue"))+parseInt($("#FCompanyScore12").numberspinner("getValue")));
					}
				});
				$("#FCompanyScore1,#FCompanyScore5").numberbox({
					onChange: function (newValue, oldValue) {						
						$("#FCompanyScore13").numberbox("setValue",
						parseInt($("#FCompanyScore1").numberbox("getValue"))+parseInt($("#FCompanyScore4").numberspinner("getValue"))+parseInt($("#FCompanyScore5").numberbox("getValue"))+parseInt($("#FCompanyScore12").numberspinner("getValue"))-parseInt($("#FCompanyScore11").numberspinner("getValue")));

						$("#FComprehensiveScore1").numberbox("setValue",parseInt($("#FDepartmentScore1").numberbox("getValue"))+parseInt($("#FCompanyScore1").numberbox("getValue")));
						$("#FComprehensiveScore5").numberbox("setValue",parseInt($("#FDepartmentScore5").numberbox("getValue"))+parseInt($("#FCompanyScore5").numberbox("getValue")));
					}
				});				

				$("#FCompanyScore6,#FCompanyScore7,#FCompanyScore8,#FCompanyScore9,#FCompanyScore10").numberspinner({
					onChange: function (newValue, oldValue) {
						$("#FCompanyScore5").numberbox("setValue",
						parseInt($("#FCompanyScore6").numberspinner("getValue"))+parseInt($("#FCompanyScore7").numberspinner("getValue"))+parseInt($("#FCompanyScore8").numberspinner("getValue"))+parseInt($("#FCompanyScore9").numberspinner("getValue"))+parseInt($("#FCompanyScore10").numberspinner("getValue")));

						$("#FComprehensiveScore6").numberbox("setValue",parseInt($("#FDepartmentScore6").numberspinner("getValue"))+parseInt($("#FCompanyScore6").numberspinner("getValue")));
						$("#FComprehensiveScore7").numberbox("setValue",parseInt($("#FDepartmentScore7").numberspinner("getValue"))+parseInt($("#FCompanyScore7").numberspinner("getValue")));
						$("#FComprehensiveScore8").numberbox("setValue",parseInt($("#FDepartmentScore8").numberspinner("getValue"))+parseInt($("#FCompanyScore8").numberspinner("getValue")));
						$("#FComprehensiveScore9").numberbox("setValue",parseInt($("#FDepartmentScore9").numberspinner("getValue"))+parseInt($("#FCompanyScore9").numberspinner("getValue")));
						$("#FComprehensiveScore10").numberbox("setValue",parseInt($("#FDepartmentScore10").numberspinner("getValue"))+parseInt($("#FCompanyScore10").numberspinner("getValue")));
					}
				});				
				$("#FComprehensiveScore1,#FComprehensiveScore4,#FComprehensiveScore5,#FComprehensiveScore12").numberbox({
					onChange: function (newValue, oldValue) {
						$("#FComprehensiveScore13").numberbox("setValue",						
						parseInt($("#FComprehensiveScore1").numberbox("getValue"))+parseInt($("#FComprehensiveScore4").numberbox("getValue"))+parseInt($("#FComprehensiveScore5").numberbox("getValue"))+parseInt($("#FComprehensiveScore12").numberbox("getValue"))-parseInt($("#FComprehensiveScore11").numberspinner("getValue")));
					}
				});
				//处理扣分
				$("#FDepartmentScore11,#FCompanyScore11 ,#FComprehensiveScore11").numberbox({
					onChange: function (newValue, oldValue) {
						 //设置部门合计
						$("#FDepartmentScore13").numberbox("setValue",
							parseInt($("#FDepartmentScore1").numberbox("getValue"))+parseInt($("#FDepartmentScore4").numberspinner("getValue"))+parseInt($("#FDepartmentScore5").numberbox("getValue"))+parseInt($("#FDepartmentScore12").numberspinner("getValue"))-parseInt($("#FDepartmentScore11").numberspinner("getValue")));
						//设置公司合计
						$("#FCompanyScore13").numberbox("setValue",
							parseInt($("#FCompanyScore1").numberbox("getValue"))+parseInt($("#FCompanyScore4").numberspinner("getValue"))+parseInt($("#FCompanyScore5").numberbox("getValue"))+parseInt($("#FCompanyScore12").numberspinner("getValue"))-parseInt($("#FCompanyScore11").numberspinner("getValue")));
						$("#FComprehensiveScore11").numberbox("setValue",parseInt($("#FDepartmentScore11").numberbox("getValue"))+parseInt($("#FCompanyScore11").numberspinner("getValue")));
						//设置综合合计
						$("#FComprehensiveScore13").numberbox("setValue",						
						parseInt($("#FComprehensiveScore1").numberbox("getValue"))+parseInt($("#FComprehensiveScore4").numberbox("getValue"))+parseInt($("#FComprehensiveScore5").numberbox("getValue"))+parseInt($("#FComprehensiveScore12").numberbox("getValue"))-parseInt($("#FComprehensiveScore11").numberspinner("getValue")));
					}
				});

			});