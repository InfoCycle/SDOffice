Ext.QuickTips.init();
var selectPersonRec=null;
var PersonsSM=null;
var userid=parent.document.getElementById("userID").value;
var username=parent.document.getElementById("userName").value; 
function SetPM(t,c){	
	if(selectPersonRec){
		if(t==1){			
			Ext.getCmp("FProjMgrId").setValue(selectPersonRec.get("FId"));
			Ext.getCmp("FProjMgrName").setValue(selectPersonRec.get("FName"));
			if(Ext.getCmp("FProjMgrId").getValue() == Ext.getCmp("FProjMgrViceId").getValue())
			{
				$.jBox.error('主项目经理和副项目经理，不能为同一个人！请重新选择。', '温馨提示');
				Ext.getCmp("FProjMgrId").setValue("");
				Ext.getCmp("FProjMgrName").setValue("");
			}
		}else if(t==2){
			Ext.getCmp("FProjMgrViceId").setValue(selectPersonRec.get("FId"));
			Ext.getCmp("FProjMgrViceName").setValue(selectPersonRec.get("FName"));
			if(Ext.getCmp("FProjMgrId").getValue() == Ext.getCmp("FProjMgrViceId").getValue())
			{
				$.jBox.error('主项目经理和副项目经理，不能为同一个人！请重新选择。', '温馨提示');
				Ext.getCmp("FProjMgrViceId").setValue("");
				Ext.getCmp("FProjMgrViceName").setValue("");				
			}
		}
	}
}
Ext.onReady(function() {		
		Ext.form.Field.prototype.msgTarget = 'side';		
		var treeloader =new Ext.tree.TreeLoader({
			dataUrl:'/GetCode/AppOrgTree/0',
			baseAttrs: { uiProvider: Ext.ux.TreeCheckNodeUI } //添加 uiProvider 属性
		});
		var root = new Ext.tree.AsyncTreeNode({
			id:"-100",
			draggable:false,
			expanded:true,
			text:'昆明松岛工程造价咨询有限公司-部门列表'
		});
		var depttree = new Ext.tree.TreePanel({
			id : 'deptTree',			
			border : true,
			margins : '0 2 0 0',
			region : 'west',
			split : true,
			root :root,
			width: 262,
			animate:true,
			enableDD:false,
			autoScroll:true,
			checkModel:'single',
	        onlyLeafCheckable:true,
			loader:treeloader,
			listeners : {
				expandnode: function(node,e){
					//e.stopEvent();
					/*if(node.isLeaf){
				        node.getUI().getIconEl().src="../../images/subject.png";  
				    }*/
					if (!node.isLeaf()) {						
						this.on('beforeload',
								function(node){
									this.loader.dataUrl='/GetCode/AppOrgTree/'+node.id;				
						});
						this.render();
						node.expand();
						node.select();
					}
				},
				click : function(node, e) {
					e.stopEvent();
					if (!node.isLeaf()) {						
						this.on('beforeload',
								function(node){
									this.loader.dataUrl='/GetCode/AppOrgTree/'+node.id;				
						});
						this.render();
						node.expand();
						node.select();
					}
				},dblclick:function(node,e){
					
				},
				"checkchange": function(node, state) {									
					//查询项目经理
					PersonsStore.lastOptions=null;
					PersonsStore.baseParams.FOrgId=node.attributes.id;
					PersonsStore.reload({callback:function(){
						selectDeptNode=node;
	        			if(PersonsStore.getCount()>0){
	        				Ext.getCmp("PersonGrid").getSelectionModel().selectRow(0);
	        			}
	        		}})
                }
			}
		});
		////
		PersonsSM = new Ext.grid.CheckboxSelectionModel({
			singleSelect:true,
			listeners: {
                rowselect: function(PersonsSM, row, rec) {
                    selectPersonRec = rec;                     
                }
			}
		});
		//PersonsSM.handleMouseDown = Ext.emptyFn;
		var PersonsStore = new Ext.data.JsonStore({
			url: '/GetCode/GroupUser/',
		    idProperty: 'FId',
		    root: 'root',
		    totalProperty: 'totalProperty',
		    autoLoad:true,
		    fields: [
		        {name: 'FId'},
		        {name: 'FName'},
		        {name: 'FUserCode'},
		        {name: 'FPassword'},
		        {name: 'FCardid'},
		        {name: 'FPhone'},
		        {name: 'FCreateTime'},
		        {name: 'FSort'},
		        {name: 'FState'},
		        {name: 'FUnitStation'},
		        {name: 'FGroupname'}
		    ]
		});
		var persongrid = new Ext.grid.GridPanel({
			store: PersonsStore,
			id:'PersonGrid',
			enableColumnMove: false,   //支持列移动
	        enableColumnResize: true,
	        autoExpandColumn: true,
	        trackMouseOver: true,
	        loadMask: true,
	        region : 'center',
	        viewConfig: {
	           forceFit: true
	        },
	        sm: PersonsSM,
	        cm: new Ext.grid.ColumnModel([
	             new Ext.grid.RowNumberer(),//PersonsSM,
	             {header: '主', dataIndex: 'F_ID', width: 60, sortable: true, hidden: false, renderer: renderMain },
	             {header: '副', dataIndex: 'F_ID', width: 60, sortable: true, hidden: false, renderer: renderVice },
	             { header: '系统编号', dataIndex: 'FId', width:60,hidden:true, sortable: true },
	             { header: '姓名', dataIndex: 'FName',width:121, sortable: true},
	             { header: '岗位', dataIndex: 'FGroupname',width:121, sortable: true}
	        ])
		});
		function renderMain(value, metadata, record, rowIndex, colIndex, store) {						
			selectPersonRec = record;
            return "<a href=\"javascript:SetPM(1,"+colIndex+");\"><img border=0 src='../../images/user.png' style='cursor:hand' alt='指派主项目经理'></a>";
        }
		function renderVice(value, metadata, record, rowIndex, colIndex, store) {
			selectPersonRec = record;
            return "<a href=\"javascript:SetPM(2,"+colIndex+");\"><img border=0 src='../../images/login.png' style='cursor:hand' alt='指派副项目经理'></a>";
        }
		var formPost = new Ext.form.FormPanel({
			region: 'south',
            split: false,
            height: 151,
            labelAlign: 'right',
            id:"formPost",
            bodyStyle: 'padding:5px 10px 10px 10px;background-color:#dfe8f6; border-width: 0px 1px 0px 1px;',
            defaultType:"textfield",
            layout:'form',
            items:[{
            	xtype: 'hidden',
                id: 'FManId'                
            },{
                xtype: 'container',
                height: 36,                
                layout: 'column',
                items: [{
                	xtype: 'container',
                    height: 33,width: 186,                    
                    layout: 'form',
                    items: [{
                    	xtype: 'textfield',
                        id: 'FMan',
                        anchor: '98%',
                        readOnly:true,
                        allowBlank:false,
                        blankText:"提交人不能为空",
                        fieldLabel: '提交人'
                    }]
                },{
                	xtype: 'container',
                    height: 33,width: 186,                    
                    layout: 'form',
                    items: [{
        				xtype: 'textfield',
                        id: 'FProjMgrName',
                        anchor: '98%',
                        readOnly:true,
                        allowBlank:false,
                        blankText:"主项目经理不能为空",
                        fieldLabel: '主项目经理'
        			}]
                },{
                	xtype: 'container',
                    height: 33,width: 186,                    
                    layout: 'form',
                    items: [{
        				xtype: 'textfield',
                        id: 'FProjMgrViceName',
                        anchor: '98%',
                        readOnly:true,
                        allowBlank:true,                
                        fieldLabel: '副项目经理'
        			}]
                }]
            },{
				xtype: 'hidden',
                id: 'FProjMgrId' 
			},{
				xtype: 'hidden',
                id: 'FProjMgrViceId' 
			},{
            	xtype:'textarea',
            	id:'FMessage',
            	anchor:'96%',
            	allowBlank:false,
                blankText:"提交人意见不能为空",
                fieldLabel: '提交人意见'
            }],             
			bbar:[{
                iconCls: 'iconPost',
				tooltip: '提交',
				id:'btnPost',
				text:'提交',
                handler: function(){
					if(Ext.getCmp("FProjMgrId").getValue()=="" && Ext.getCmp("FProjMgrName").getValue()==""  ){						
						$.jBox.error('请选择主项目经理！主项目经理不能为空。', '温馨提示');
					}else if(Ext.getCmp("FMessage").getValue()==""){				
						$.jBox.error('提交人意见不能为空！', '温馨提示');
					}
					else{
						var submit = function(v, h, f) {
									if (v == 'ok') {
										$.jBox.tip("正在指派项目经理，请稍等...", 'loading');
										$.post("/Buss/TaskService/7", {
											"ProcessId" : parent.document.getElementById("processId").value,
											"AcceptUserId" : selectPersonRec.get('FId'),//Ext.getCmp("FManId").getValue(),
											"AboveActId" : parent.document.getElementById("activeId").value,
											"Remark" : Ext.getCmp("FMessage").getValue(),
											"formPKID": parent.document.getElementById("formPKID").value,
											"FProjMgrId": Ext.getCmp("FProjMgrId").getValue(),
											"FProjMgrName": Ext.getCmp("FProjMgrName").getValue(),
											"FProjMgrViceId": Ext.getCmp("FProjMgrViceId").getValue(),
											"FProjMgrViceName": Ext.getCmp("FProjMgrViceName").getValue()
										}, function(data) {
											if(data.success){
												Ext.getCmp("btnPost").setDisabled(true);
												Ext.getCmp("btnCancle").setDisabled(true);
												//$.jBox.success('提交成功。', '温馨提示');
												$.jBox.tip('提交成功。', 'success');
											}
											else
												//$.jBox.error('提交失败，请检查网络或联系系统管理员！', '温馨提示');
												$.jBox.tip('提交失败，请检查网络或联系系统管理员！', 'error');
										}, "json");
										
									} else if (v == 'cancel') {
										// 取消
									}
									return true;
									//window.location.reload();
								};
						$.jBox.confirm("确定要提交吗？", "重要提示", submit);						
					}
                }
            },{
            	iconCls: 'iconRefresh',
				tooltip: '重置信息',
				id:'btnCancle',
				text:'重置',
                handler: function(){
                	Ext.getCmp("FProjMgrId").setValue("");
    				Ext.getCmp("FProjMgrName").setValue("");
                	Ext.getCmp("FProjMgrViceId").setValue("");
    				Ext.getCmp("FProjMgrViceName").setValue(""); 
    				Ext.getCmp("FMessage").setValue("");
                }
            }]
		});

		var mainViewport = new Ext.Viewport({
			id : 'mainViewport',
			layout : 'fit',
			border : false,
			items : [ {
				xtype : 'panel',
				id : 'SDMPanel',
				layout : 'border',
				border : false,
				items : [depttree,persongrid,formPost]
			} ]
		});
		Ext.getCmp("FMan").setValue(username);
		Ext.getCmp("FManId").setValue(userid);
	});