/*!
 * Ext JS Library 3.4.0
 * Copyright(c) 2006-2011 Sencha Inc.
 * licensing@sencha.com
 * http://www.sencha.com/license
 */

Ext.onReady(function(){
    var tree = new Ext.tree.TreePanel({
        renderTo:'tree-div',
     //   title: document.getElementById('name').value+'——>指派种类',
        align:'center',
        height: 300,
        width: 400,
        useArrows:true,
        autoScroll:true,
        animate:true,
       // enableDD:true,拖拽
        containerScroll: true,
        expanded:true,
        rootVisible: false,
        checkModel:'single',
        onlyLeafCheckable:true,
        frame: true,
        root: {
            nodeType: 'async',
            text:'选择类别' ,
            iconCls:''       
        },//loader 引用插件TreeCheckNodeUI，实现单选功能
      loader: new Ext.tree.TreeLoader({
        dataUrl: 'categorycheckjson.action?productid='+document.getElementById('productid').value,
      baseAttrs: { uiProvider: Ext.ux.TreeCheckNodeUI } //添加 uiProvider 属性
      }),
        
        buttons: [{
            text: '指派种类',
            handler: function(){
                var categoryId = '', selNodes = tree.getChecked();
                var productid=document.getElementById('productid').value;
                if(selNodes.length==0){
                Ext.Msg.alert('提示','请选择指派种类');
                }
                Ext.each(selNodes, function(node){
                    if(categoryId.length > 0){
                        categoryId += ', ';
                    }
                   // msg += node.text;
                    categoryId += node.attributes['categoryId'];
                 
                 Ext.MessageBox.confirm("确认","确定指派种类吗？",function(e){
                if (e == "yes") {
    
              //提交到服务器操作
                 Ext.Ajax.request({ 
                            url: 'asignCategory.action',
                            params: {categoryId:categoryId,productid:productid},
                            method: 'POST',
                            dataType:'json',          
                            success:function(form,action){
                            var obj = Ext.util.JSON.decode(form.responseText);   
                            if(obj.success==true)   
                           {    
                               Ext.Msg.alert('指派成功',obj.msg);   
                               var win = parent.Ext.getCmp('win');
                               if (win) {win.close();}  
                             }   
                          else  
                            {   
                           //Ext.Msg.alert('提示',obj.errors);   
                              Ext.Msg.alert('指派错误',obj.msg);   
                              }     
                            },
                            //提交失败的回调函数
                            failure:function(){                           
                                 Ext.Msg.alert('错误','服务器出现错误请稍后再试！');
                            }
                          });
           
                } else if (e == "no") {
                 // alert("no");
                } else if (e == "cancel") {
                  //alert("cancel");       
                }});
                   
                });
            }
        },{
            text: '关闭',
            handler: function(){
              var win = parent.Ext.getCmp('win');
          //  alert(win2);
            if (win) {win.close();}  
          //  window.parent.location.href='roleGrid.action'
            }
        }]
    });
    tree.getRootNode().expand(true);
     tree.root.getUI().getIconEl().src ='';
//'js/ext/icons/fam/book.png';   
    
    /////////////////////////
    
});