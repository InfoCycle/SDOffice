(function($){
	$.extend($.fn,{
		createTR:function(rowType){
			var insertTemplet=new Array();
			switch(rowType){
			case 1:
				insertTemplet.push('<tr>');
				insertTemplet.push('<td><label id="FPmName{0}" name="{1}">{2}</label><input id="FPmId{0}" type="hidden" value="{1}" /></td>');
				insertTemplet.push('<td style="text-align:left"><input type="text" id="FPmworkRatio{0}" name="FPmworkRatio{0}"  style="width:90%;" group="PM" expression="percentage" empty="工作比例：该项必填！"/></td>');
				insertTemplet.push('<td style="text-align:left"><input type="text" id="FPmManageWage{0}" name="FPmManageWage{0}"  style="width:90%;" group="PM" expression="number" empty="项目经理管理工资：该项必填！"/></td>');
				insertTemplet.push('<td style="text-align:left"><input type="text" id="FPmadjustedMgtWage{0}" name="FPmadjustedMgtWage{0}" style="width:90%;" group="PM" expression="number" empty="调整后管理工资：该项必填！"/></td>');
				insertTemplet.push('<td style="text-align:left"><input type="text" id="FPmrealWage{0}" name="FPmrealWage{0}"  style="width:90%;" group="PM" expression="number" empty="实提工资：该项必填！"/></td>');
				insertTemplet.push('<td style="text-align:left"><input type="text" id="FPmretainedWage{0}" name="FPmretainedWage{0}"  style="width:90%;" group="PM" expression="number" empty="留存工资：该项必填！"/></td>');
				insertTemplet.push('<td colspan="5"></td>');
				insertTemplet.push('</tr>');
				break;
			case 2:
				insertTemplet.push('<tr>');
				insertTemplet.push('<td><label id="FIsname{0}" name="{1}">{2}</label><input id="FIsnameId{0}" type="hidden" value="{1}" /></td>');
				insertTemplet.push('<td><input type="text" id="FIsworkRatio{0}" group="inside"  style="width:90%;" expression="percentage" empty="工作比例：该项必填！"/></td>');
				insertTemplet.push('<td><input type="text" id="FIsshouldTotal{0}" group="inside" style="width:90%;" expression="number" empty="应提总额：该项必填！" /></td>');
				insertTemplet.push('<td><input type="text" id="FIsadjustedTotal{0}" group="inside" style="width:90%;" expression="number" empty="调整后总额：该项必填！"/></td>');
				insertTemplet.push('<td><input type="text" id="FIsrealWage{0}" group="inside" style="width:90%;" expression="number" empty="实提工资：该项必填！"/></td>');
				insertTemplet.push('<td colspan="2"><input type="text" id="FIsretainedWage{0}" group="inside" style="width:90%;" expression="number" empty="留存工资：该项必填！"/></td>');
				//insertTemplet.push('<td><input type="text" id="FIsconfirm{0}"  style="width:90%;" empty="签字确认：该项必填！"/></td>');
				break;
			case 3:
				insertTemplet.push('<td><label id="FOutsourcingName{0}" name="{1}">{2}</label><input id="FOutsourcingNameId{0}" type="hidden" value="{1}" /></td>');
				insertTemplet.push('<td><input type="text" id="FOutsourcingWorkRatio{0}" group="outside" style="width:90%;" expression="percentage" empty="工作比例（造价）：该项必填！"/></td>');
				insertTemplet.push('<td colspan="2"><input type="text" id="FOutsourcingExtractionWage{0}"group="outside"  style="width:90%;" expression="number" empty="提取工资：该项必填！"/></td>');
				//insertTemplet.push('<td><input type="text" id="FOutsourcingConfirm{0}" style="width:90%;" empty="签字确认：该项必填！"/></td>');
				insertTemplet.push('<tr>');
				break;
			case 4:
				insertTemplet.push('<tr>');
				insertTemplet.push('<td><label id="FPmName{0}" name="{1}">{2}</label><input id="FPmId{0}" type="hidden" value="{1}" /></td>');
				insertTemplet.push('<td style="text-align:left"><input type="text" id="FPmworkRatio{0}" name="FPmworkRatio{0}" value="{3}" style="width:90%;" group="PM" expression="percentage" empty="工作比例：该项必填！"/></td>');
				insertTemplet.push('<td style="text-align:left"><input type="text" id="FPmManageWage{0}" name="FPmManageWage{0}" value="{4}" style="width:90%;" group="PM" expression="number" empty="项目经理管理工资：该项必填！"/></td>');
				insertTemplet.push('<td style="text-align:left"><input type="text" id="FPmadjustedMgtWage{0}" name="FPmadjustedMgtWage{0}" value="{5}" style="width:90%;" group="PM" expression="number" empty="调整后管理工资：该项必填！"/></td>');
				insertTemplet.push('<td style="text-align:left"><input type="text" id="FPmrealWage{0}" name="FPmrealWage{0}" value="{6}" style="width:90%;" group="PM" expression="number" empty="实提工资：该项必填！"/></td>');
				insertTemplet.push('<td style="text-align:left"><input type="text" id="FPmretainedWage{0}" name="FPmretainedWage{0}" value="{7}" style="width:90%;" group="PM" expression="number" empty="留存工资：该项必填！"/></td>');
				insertTemplet.push('<td colspan="5"></td>');
				insertTemplet.push('</tr>');
				break;
			case 5:
				insertTemplet.push('<tr>');
				insertTemplet.push('<td><label id="FIsname{0}" name="{1}">{2}</label><input id="FIsnameId{0}" type="hidden" value="{1}" /></td>');
				insertTemplet.push('<td><input type="text" id="FIsworkRatio{0}" group="inside" value="{3}"  style="width:90%;" expression="percentage" empty="工作比例：该项必填！"/></td>');
				insertTemplet.push('<td><input type="text" id="FIsshouldTotal{0}" group="inside" value="{4}" style="width:90%;" expression="number" empty="应提总额：该项必填！" /></td>');
				insertTemplet.push('<td><input type="text" id="FIsadjustedTotal{0}" group="inside" value="{5}" style="width:90%;" expression="number" empty="调整后总额：该项必填！"/></td>');
				insertTemplet.push('<td><input type="text" id="FIsrealWage{0}" group="inside" value="{6}" style="width:90%;" expression="number" empty="实提工资：该项必填！"/></td>');
				insertTemplet.push('<td colspan="2"><input type="text" id="FIsretainedWage{0}" group="inside" value="{7}" style="width:90%;" expression="number" empty="留存工资：该项必填！"/></td>');
				//insertTemplet.push('<td><input type="text" id="FIsconfirm{0}"  style="width:90%;" value="{8}" empty="签字确认：该项必填！"/></td>');
				break;
			case 6:
				insertTemplet.push('<td><label id="FOutsourcingName{0}" name="{1}">{2}</label><input id="FOutsourcingNameId{0}" type="hidden" value="{1}" /></td>');
				insertTemplet.push('<td><input type="text" id="FOutsourcingWorkRatio{0}" group="outside" value="{3}" style="width:90%;" expression="percentage" empty="工作比例（造价）：该项必填！"/></td>');
				insertTemplet.push('<td colspan="2"><input type="text" id="FOutsourcingExtractionWage{0}"group="outside" value="{4}"  style="width:90%;" expression="number" empty="提取工资：该项必填！"/></td>');
				//insertTemplet.push('<td><input type="text" id="FOutsourcingConfirm{0}" style="width:90%;" value="{5}" empty="签字确认：该项必填！"/></td>');
				insertTemplet.push('<tr>');
				break;
			}
			return insertTemplet.join(new String());
		},
		queryWages:function(args){
			$.ajax({
				url:'/Buss/Wage/1',
				type: 'POST',
				data:{FId:args.FId}, 
				dataType: 'json',
				async :  false,
				success:function(data) {
					if(data.success){
					  $.fn.Wages(data.root.entitys);
					  $.fn.ProjectManage(data.root.entityPM);
					  $.fn.Personnal(data.root.entityInside,data.root.entityOutside);
					}
				}
			});
		},
		Wages:function(entity){
			$('label[id=FEntrustUnitName]').text(entity.FEntrustUnitName);
			$('label[id=TContractNumbers]').text(entity.TContractNumbers);
			$('label[id=FServiceMode]').text(entity.FServiceMode);
			$('input[id=fkContractId]').val(entity.FContractId);
		},
		ProjectManage:function(entitys){
			var rows=new Array();
			$(entitys).each(function(i,entity){
				var str=$.fn.createTR(1);
				rows.push(str.format(i,entity.FPmId,entity.FPmName));
			});
			
			$('#Personnel').empty().append(rows.join(new String()));
		},Personnal:function(inside,outside){
			var content=new Array();
			var length=(inside.length)+(outside.length);
			for(var m=0;m<length;m++){
				if((inside.length|outside.length)>m){
					var rows=new Array();
					if(inside.length>m){
						var str=$.fn.createTR(2);
						rows.push(str.format(m,inside[m].FPersonnelId,inside[m].FPersonnelName));
					}else{
						rows.push('<tr><td></td><td></td><td></td><td></td><td></td><td colspan="2"></td>');
					}
						
					if(outside.length>m){
						var str=$.fn.createTR(3);
						rows.push(str.format(m,outside[m].FPersonnelId,outside[m].FPersonnelName));
					}else{
						rows.push('<td></td><td></td><td colspan="2"></td></tr>');
					}
					content.push(rows.join(new String()));
				}else{
					break;
				}
			}
		
			$('#employee').empty().append(content.join(new String()));
		},defaults : {
			number : /^[\-\+]?(([0-9]+)([\.,]([0-9]+))?|([\.,]([0-9]+))?)$/,
			percentage:/^(([0-9]+)([\.,]([0-9]+)([%])+)|(([0-9]+)([%])+))$/,
		},expression : function(method) {
			$(this).poshytip({
				className : 'tip-yellowsimple',
				showOn : 'none',
				alignTo : 'target',
				alignX : 'right',
				alignY : 'center',
				offsetX : 5
			});
			$(this).live({change:function() {
				switch(method){
				case 'number':
					if (!$.fn.defaults.number.test($(this).val())) {
						$(this).poshytip('update','无效数字(格式:20或者20.00)');
						$(this).poshytip('show');
					 }else{
					 	$(this).poshytip('hide');
					 }			
					break;
				case 'percentage':
					if (!$.fn.defaults.percentage.test($(this).val())) {
						$(this).poshytip('update','无效百分数(格式：20%或者20.00%)');
						$(this).poshytip('show');
						
				 	}else{
				 		$(this).poshytip('hide');
				 	}
					break;
				}
			 }
		});
	  },pmTotal:function(){
		  var dr=new Array('FPmworkRatio','FPmManageWage','FPmadjustedMgtWage','FPmrealWage','FPmretainedWage');
		  var entitys=null;
		   var length=this.length/dr.length;
		   var resultValue=0;
		   $(this).live({change:function() {
			   entitys=new Array();
				for(var i=0;i<dr.length;i++){
					for(var n=0;n<length;n++ ){
						var getValue= $('#'+dr[i]+(n.toString())).attr('value').replace('%', '');
						resultValue+= parseFloat($.trim(getValue)?getValue:0);
				  }
				 entitys.push({id:i,value:resultValue.toFixed(2)});
				 resultValue=0;
				}
				
				if(entitys.length>0){
					$('#PMWorkRatioTotal').empty().text(entitys[0].value+'%');
					$('#PMManageWageTotal').empty().text(entitys[1].value);
					$('#PMAdjustedMgtWageTotal').empty().text(entitys[2].value);
					$('#PMRealWageTotal').empty().text(entitys[3].value);
					$('#PMRetainedWageTotal').empty().text(entitys[4].value);
				}			
		   }});
	   },insideTotal:function(){
		   var dr=new Array('FIsworkRatio','FIsshouldTotal','FIsadjustedTotal','FIsrealWage','FIsretainedWage');
		   var entitys=null;
		   var length=this.length/dr.length;
		   var resultValue=0;
		   $(this).live({change:function() {
			   entitys=new Array();
				for(var i=0;i<dr.length;i++){
					for(var n=0;n<length;n++ ){
						var getValue= $('#'+dr[i]+(n.toString())).attr('value').replace('%', '');
						resultValue+= parseFloat($.trim(getValue)?getValue:0);
				  }
				 entitys.push({id:i,value:resultValue.toFixed(2)});
				 resultValue=0;
				}
				
				if(entitys.length>0){
					$('#ISWorkRatioTotal').empty().text(entitys[0].value+'%');
					$('#ISShouldTotal').empty().text(entitys[1].value);
					$('#ISAdjustedTotal').empty().text(entitys[2].value);
					$('#ISRealWage').empty().text(entitys[3].value);
					$('#ISRetainedWage').empty().text(entitys[4].value);
				}			
		   }});
	   },outsideTotal:function(){
		   var dr=new Array('FOutsourcingWorkRatio','FOutsourcingExtractionWage');
		   var entitys=null;
		   var length=this.length/dr.length;
		   var resultValue=0;
		   $(this).live({change:function() {
			   entitys=new Array();
				for(var i=0;i<dr.length;i++){
					for(var n=0;n<length;n++ ){
						var getValue= $('#'+dr[i]+(n.toString())).attr('value').replace('%', '');
						resultValue+= parseFloat($.trim(getValue)?getValue:0);
				  }
				 entitys.push({id:i,value:resultValue.toFixed(2)});
				 resultValue=0;
				}
				
				if(entitys.length>0){
					$('#OutsourcingWorkRatioTotal').empty().text(entitys[0].value+'%');
					$('#OutsourcingExtractionWageTotal').empty().text(entitys[1].value);
				}			
		   }});
	   },wageData:function(){
		   var entity_wage={
					FId:$('input[id=FId]').val(),
					fkTaskId:$('input[id=fkTaskId]').val(),
					fkContractId:$('input[id=fkContractId]').val(),
					FNumbers:$('input[id=FNumbers]').val().concat('-').concat($('label[id=NumbersYear]').text()),
					FReceivables:$('input[id=FReceivables]').val(),
					FAlreadyCollection:$('input[id=FAlreadyCollection]').val(),
					FExtractionBase:$('input[id=FExtractionBase]').val(),
					FBasicProportion:$('input[id=FBasicProportion]').val(),
					FShouldProportion:$('input[id=FShouldProportion]').val(),
					FRealProportion:$('input[id=FRealProportion]').val(),
					FRetainedProportion:$('input[id=FRetainedProportion]').val(),
					FAppraisalScore:$('input[id=FAppraisalScore]').val(),
					FCarryTotal:$('input[id=FCarryTotal]').val(),
					FRealCarryTotal:$('input[id=FRealCarryTotal]').val(),
					FRetainedTotal:$('input[id=FRetainedTotal]').val(),
					FAtoasbapCivil:$('input[id=FAtoasbapCivil]').val(),
					FAtoasbapInstallation:$('input[id=FAtoasbapInstallation]').val(),
					FProjMgr:$('input[id=FProjMgr]').val(),
					FFinanceDept:$('input[id=FFinanceDept]').val(),
					FIntegratedDept:$('input[id=FIntegratedDept]').val(),
					FDeptMgr:$('input[id=FDeptMgr]').val(),
					FProductionChief:$('input[id=FProductionChief]').val(),
					FGeneralMgr:$('input[id=FGeneralMgr]').val(),
					FAtdowmpmeb:$('input[id=FAtdowmpmeb]').val()
					};
				   return entity_wage;
			   },pmData:function(){
				   var entitys=new Array();
				   var length=this.length/5;
				   for(var i=0;i<length;i++){
					entitys.push({
						FId:null,
						fkEfficiencyWageId:$('input[id=FId]').val(),
						FPmId:$('#FPmId'+i.toString()).val(),
						FPmName:$('#FPmName'+i.toString()).text(),
						FPmworkRatio:$('#FPmworkRatio'+i.toString()).val(),
						FPmManageWage:$('#FPmManageWage'+i.toString()).val(),
						FPmadjustedMgtWage:$('#FPmadjustedMgtWage'+i.toString()).val(),
						FPmrealWage:$('#FPmrealWage'+i.toString()).val(),
						FPmretainedWage:$('#FPmretainedWage'+i.toString()).val()
					 });  
				  }
				 return entitys;
			   },insideData:function(){
				   var entitys=new Array();
				   var length=this.length/6;
				   for(var i=0;i<length;i++){
					entitys.push({
						FId:null,
						fkEfficiencyWageId:$('input[id=FId]').val(),
						FIsnameId:$('#FIsnameId'+i.toString()).val(),
						FIsname:$('#FIsname'+i.toString()).text(),
						FIsworkRatio:$('#FIsworkRatio'+i.toString()).val(),
						FIsshouldTotal:$('#FIsshouldTotal'+i.toString()).val(),
						FIsadjustedTotal:$('#FIsadjustedTotal'+i.toString()).val(),
						FIsrealWage:$('#FIsrealWage'+i.toString()).val(),
						FIsretainedWage:$('#FIsretainedWage'+i.toString()).val()
						//FIsconfirm:$('#FIsconfirm'+i.toString()).val()
					 });  
				  }
				 return entitys;
			   },outsideData:function(){
				   var entitys=new Array();
				   var length=this.length/3;
				   for(var i=0;i<length;i++){
					entitys.push({
						FId:null,
						fkEfficiencyWageId:$('input[id=FId]').val(),
						FOutsourcingNameId:$('#FOutsourcingNameId'+i.toString()).val(),
						FOutsourcingName:$('#FOutsourcingName'+i.toString()).text(),
						FOutsourcingWorkRatio:$('#FOutsourcingWorkRatio'+i.toString()).val(),
						FOutsourcingExtractionWage:$('#FOutsourcingExtractionWage'+i.toString()).val()
						//FOutsourcingConfirm:$('#FOutsourcingConfirm'+i.toString()).val()
					 });  
				  }
				 return entitys;  
			  },getQuery:function(){
				  var FId=Request.QueryString('formPKID');
				  if(!$.isEmptyObject(FId)){
					  if(parseInt(FId)>0){
					  $.ajax({
							url:'/Buss/Wage/4',
							type: 'POST',
							data:{
								wId:FId,
								activeId:Request.QueryString('activeId')
								}, 
							dataType: 'json',
							async :  false,
							success:function(data) {
								if(data.success){
									if(!$.isEmptyObject(data.root)){
										$.fn.entityWage(data.root.entitys);
										$.fn.entityPM(data.root.entityPM);
										$.fn.entityPersonnal(data.root.entityInside,data.root.entityOutside);
										$('input[type=text][expression=number]').expression('number');
										$('input[type=text][expression=percentage]').expression('percentage');
										$('input[type=text][group=PM]').pmTotal();
										$('input[type=text][group=inside]').insideTotal();
										$('input[type=text][group=outside]').outsideTotal();
										$('input[type=text][group=PM]').change();
										$('input[type=text][group=inside]').change();
										$('input[type=text][group=outside]').change();
										$.fn.getFiles(FId);
									}								
								}
							}
					}); 
				   }
				 }
			},entityWage:function(entity){
				var str= entity.FNumbers.split('-');
				$('#FId').val(entity.FId);
				$('#fkTaskId').val(entity.fkTaskId);
				$('#FKTaskName').val(entity.FTaskName);
				$('#fkContractId').val(entity.fkContractId);
				$('#FNumbers').val(str[0]);
				$('#NumbersYear').text(str[1]);
				$('#TitleYear').text(str[1]);
				$('#FTaskName').val(entity.FTaskName);
				$('#FEntrustUnitName').text(entity.FEntrustUnitName);
				$('#TContractNumbers').text(entity.FContractNumbers);
				$('#FServiceMode').text(entity.FServiceMode);
				$('#FReceivables').val(entity.FReceivables);
				$('#FAlreadyCollection').val(entity.FAlreadyCollection);
				$('#FExtractionBase').val(entity.FExtractionBase);
				$('#FBasicProportion').val(entity.FBasicProportion);
				$('#FShouldProportion').val(entity.FShouldProportion);
				$('#FRealProportion').val(entity.FRealProportion);
				$('#FRetainedProportion').val(entity.FRetainedProportion);
				$('#FAtoasbapCivil').val(entity.FAtoasbapCivil);
				$('#FAppraisalScore').val(entity.FAppraisalScore);
				$('#FCarryTotal').val(entity.FCarryTotal);
				$('#FRealCarryTotal').val(entity.FRealCarryTotal);
				$('#FRetainedTotal').val(entity.FRetainedTotal);
				$('#FAtoasbapInstallation').val(entity.FAtoasbapInstallation);
				$('#FProjMgr').val(entity.FProjMgr);
				$('#FFinanceDept').val(entity.FFinanceDept);
				$('#FIntegratedDept').val(entity.FIntegratedDept);
				$('#FDeptMgr').val(entity.FDeptMgr);
				$('#FProductionChief').val(entity.FProductionChief);
				$('#FGeneralMgr').val(entity.FGeneralMgr);
			},entityPM:function(entitys){
				var rows=new Array();
				$(entitys).each(function(i,entity){
					var str=$.fn.createTR(4);
					rows.push(str.format(i,entity.FPmId,entity.FPmName,entity.FPmworkRatio,entity.FPmManageWage,entity.FPmadjustedMgtWage,entity.FPmrealWage,entity.FPmretainedWage));
				});
				
				$('#Personnel').empty().append(rows.join(new String()));
			},entityPersonnal:function(inside,outside){
				var content=new Array();
				var length=(inside.length)+(outside.length);
				for(var m=0;m<length;m++){
					if((inside.length|outside.length)>m){
						var rows=new Array();
						if(inside.length>m){
							var str=$.fn.createTR(5);
							rows.push(str.format(m,inside[m].FIsnameId,inside[m].FIsname,
									inside[m].FIsworkRatio,inside[m].FIsshouldTotal,inside[m].FIsadjustedTotal,
									inside[m].FIsrealWage,inside[m].FIsretainedWage));
						}else{
							rows.push('<tr><td></td><td></td><td></td><td></td><td></td><td colspan="2"></td>');
						}
							
						if(outside.length>m){
							var str=$.fn.createTR(6);
							rows.push(str.format(m,outside[m].FOutsourcingNameId,outside[m].FOutsourcingName,
									outside[m].FOutsourcingWorkRatio,outside[m].FOutsourcingExtractionWage));
						}else{
							rows.push('<td></td><td></td><td colspan="2"></td></tr>');
						}
						content.push(rows.join(new String()));
					}else{
						break;
					}
				}
			
				$('#employee').empty().append(content.join(new String()));
			},deleteFile:function(FId,Path,wId){
				 $.ajax({
						url:'/Buss/File/3',
						type: 'POST',
						data:{FId:FId,FPath:Path}, 
						dataType: 'json',
						async :  false,
						success:function(data) {		
							$.fn.getFiles(wId);
							$.jBox.info(data.message,'提示'); 
						}
					});
			},getFiles:function(FId){
				   $.ajax({
						url:'/Buss/Wage/5',
						type: 'POST',
						data:{FTypeId:FId}, 
						dataType: 'json',
						async :  false,
						success:function(data) {						
							$.fn.showFiles(data,FId);
						}
					});
			   },showFiles:function(data,wId){
				   var dr=new Array();
					dr.push('<ul>');
					$(data.root).each(function(i,entity){
						dr.push('<li><div><a rel="sexylightbox[group]" href="'+entity.FPath+'" title="'+entity.FFileName+'">');
						dr.push('<img alt="" src="'+entity.FPath+'">');
						dr.push('</a></div>');
						dr.push('<div><a style="text-decoration: none;" href="javascript:void(0)" onclick="$.fn.deleteFile('+entity.FId+',\''+entity.FPath+'\','+wId+')">删除图片</a></div></li>');
					});
					dr.push('</ul>');
					$('#img_container').empty().append(dr.join(new String()));
					SexyLightbox.initialize({color:'white', dir: '../../plugin/sexy/sexyimages',loading:'../../plugin/sexy/sexyimages'});   
			}
	});
})(jQuery);