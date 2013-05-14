var entitys={};
Ext.onReady(function() {
		//为了理解，下面参数必须传递，否则无效
		var defaults={
				userId:null,
				userName:null,		
		};
		var setting=Base64.decode(Request.QueryString('entitys'));
		entitys=$.extend(true,{},defaults,$.parseJSON(setting));		
		//alert(entitys.wTOther);
		Ext.QuickTips.init();
		Ext.form.Field.prototype.msgTarget = 'side';
		var selectPersonRec=null;
		var selectDeptNode=null;
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
					 //node.getUI().checkbox.checked=false;					
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
		var PersonsSM = new Ext.grid.CheckboxSelectionModel({
			singleSelect:true,
			listeners: {
                rowselect: function(PersonsSM, row, rec) {
                    selectPersonRec = rec;                     
                }
			}
		});		
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
			id:"PersonGrid",
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
	             new Ext.grid.RowNumberer(),PersonsSM,
	             { header: '系统编号', dataIndex: 'FId', width:60,hidden:true, sortable: true },
	             { header: '姓名', dataIndex: 'FName',width:121, sortable: true},
	             { header: '岗位', dataIndex: 'FGroupname',width:121, sortable: true}
	        ])
		});
		var formPost = new Ext.form.FormPanel({
			region: 'south',
            split: false,
            height: 126,
            labelAlign: 'right',
            id:"formPost",
            bodyStyle: 'padding:2px 10px 10px 10px;background-color:#dfe8f6; border-width: 0px 1px 0px 1px;',
            defaultType:"textfield",
            layout:'form',
            items:[{
            	xtype: 'hidden',
                id: 'FManId'                
            },{
            	xtype: 'textfield',
                id: 'FMan',
                anchor: '95%',
                readOnly:true,
                allowBlank:false,
                blankText:"提交人不能为空",
                fieldLabel: '提交人'
            },{
            	xtype:'textarea',
            	id:'FMessage',
            	anchor:'95%',
            	allowBlank:false,
                blankText:"提交人意见不能为空",
                fieldLabel: '提交人意见'
            }],             
			bbar:[{
                iconCls: 'iconPost',
				tooltip: '提交',
				text:'提交',
                handler: function(){
					if(Ext.getCmp("FMessage").getValue()==""){						
						$.jBox.error('提交人意见不能为空！', '温馨提示');
					}else if(!selectPersonRec){						
						$.jBox.error('请选择部门及人员！', '温馨提示');
					}
					else{
						var data={
							acceptUserId:selectPersonRec.get('FId'),
							acceptUserName:selectPersonRec.get('FName'),
							remark:Ext.getCmp("FMessage").getValue(),
							partId:selectDeptNode.id,
							partName:selectDeptNode.text,
							wTOther: entitys.wTOther
						};
						parent.ToPartWT(data);
					}
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
		Ext.getCmp("FMan").setValue(entitys.userName);
		Ext.getCmp("FManId").setValue(entitys.userId);
	});