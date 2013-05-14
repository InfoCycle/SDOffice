Ext.onReady(function() {
		Ext.QuickTips.init();
		Ext.form.Field.prototype.msgTarget = 'side';
		var treeloader =new Ext.tree.TreeLoader({
			dataUrl:'/GetCode/ClientTree/0',
			baseAttrs: { uiProvider: Ext.ux.TreeCheckNodeUI } //添加 uiProvider 属性
		});
		var root = new Ext.tree.AsyncTreeNode({
			id:"-100",
			draggable:false,
			expanded:true,
			text:'分类别委托单位'
		});
		var eutree = new Ext.tree.TreePanel({
			id : 'EUTree',			
			border : true,			
			margins : '0 2 0 0',
			region : 'center',
			split : true,
			root :root,
			animate:true,
			enableDD:false,
			autoScroll:true,
			checkModel:'single',
	        onlyLeafCheckable:true,
			loader:treeloader,
			bbar:[{
                iconCls: 'icon-expand-all',
				tooltip: '选择确定',
				text:'确定',
                handler: function(){ 
                	//this.root.expand(true);
                	var Node;
                	selNodes = eutree.getChecked();
                    Ext.each(selNodes, function(node){
                        //alert(selNodes.length)
                        //if(selNodes.)
                        Node=node;                    	
                    });
                    if(!Node)
                    {
                    	Ext.Msg.show({
	                        title: '温馨提示', 
	                        msg: '未选择委托单位，请选择一个委托单位！',
	                        icon: Ext.Msg.INFO,
	                        minWidth: 200,
	                        buttons: Ext.Msg.OK
	                    });
                    	return false;
                    }                   
                    clientid=Node.attributes.FClientId;
                    clientname=Node.attributes.text;
                    choose(clientid,clientname,Node.attributes.ShortName);                    
                },
                scope: this
            }],
			listeners : {
				expandnode: function(node,e){
					//e.stopEvent();
					/*if(node.isLeaf){
				        node.getUI().getIconEl().src="../../images/subject.png";  
				    }*/
					if (!node.isLeaf()) {						
						this.on('beforeload',
								function(node){
									this.loader.dataUrl='/GetCode/ClientTree/'+node.id;				
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
									this.loader.dataUrl='/GetCode/ClientTree/'+node.id;				
						});
						this.render();
						node.expand();
						node.select();
					}
				},dblclick:function(node,e){
					if(node.attributes.isClient==0)
					{
						if(node.isLeaf())
							{
								node.getUI().checkbox.checked=false;
								Ext.Msg.show({
			                        title: '温馨提示', 
			                        msg: node.text+' 不是委托单位，请重新选择！',
			                        icon: Ext.Msg.INFO,
			                        minWidth: 200,
			                        buttons: Ext.Msg.OK
			                    });	
							}
					} 
				},
				"checkchange": function(node, state) {									
					if(node.attributes.isClient==0)
					{
						node.getUI().checkbox.checked=false;
						Ext.Msg.show({
	                        title: '温馨提示', 
	                        msg: node.text+' 不是委托单位，请重新选择！',
	                        icon: Ext.Msg.INFO,
	                        minWidth: 200,
	                        buttons: Ext.Msg.OK
	                    });							
					}                    
                }
			}
		});
		var mainViewport = new Ext.Viewport({
			id : 'mainViewport',
			layout : 'fit',
			border : false,
			items : [ {
				xtype : 'panel',
				id : 'EUPanel',
				layout : 'border',
				border : false,
				items : [eutree]
			} ]
		});		
	});