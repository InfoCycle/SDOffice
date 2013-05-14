	//PassStore
	var passdata=[["通过",1],["不通过",0]];
	var passstore=new Ext.data.SimpleStore({
		fields:['name','value'],
		data:passdata
	});
	//PassStore for Query
	var qpassdata=[["--全部--",-1],["通过",1],["不通过",0]];
	var qpassstore=new Ext.data.SimpleStore({
		fields:['name','value'],
		data:qpassdata
	});
	//TypeStore
	var typedata=[["计划",0],["临时",1],["待定",2]];
	var typestore=new Ext.data.SimpleStore({
		fields:['name','value'],
		data:typedata
	});
	//KindStore
	var kinddata=[["--全部--","-1"],["考前培训","考前培训"],["继续教育","继续教育"],["上岗培训","上岗培训"],["其他培训","其他培训"]];
	var kindstore=new Ext.data.SimpleStore({
		fields:['name','value'],
		data:kinddata
	});
	//YEAR
	var yeardata=[["2010年",2010],["2011年",2011],["2012年",2012],["2013年",2013],["2014年",2014],["2015年",2015],["2016年",2016],["2017年",2017]];	
	var yearstore=new Ext.data.SimpleStore({
		fields:['name','value'],
		data:yeardata
	});
	var qyeardata=[["--全部--",-1],["2010年",2010],["2011年",2011],["2012年",2012],["2013年",2013],["2014年",2014],["2015年",2015],["2016年",2016],["2017年",2017]];
	var qyearstore=new Ext.data.SimpleStore({
		fields:['name','value'],
		data:qyeardata
	});
	//PartMode
	var partmodedata=[["自办培训","自办培训"],["委托培训","委托培训"],["合作培训","合作培训"]];	
	var partmodestore=new Ext.data.SimpleStore({
		fields:['name','value'],
		data:partmodedata
	});
	var qpartmodedata=[["-全部-","-1"],["自办培训","自办培训"],["委托培训","委托培训"],["合作培训","合作培训"]];	
	var qpartmodestore=new Ext.data.SimpleStore({
		fields:['name','value'],
		data:qpartmodedata
	});
	var ntoamodedata=[["需审核","1"],["不需审核","0"]];	
	var ntoamodestore=new Ext.data.SimpleStore({
		fields:['name','value'],
		data: ntoamodedata
	});
	
	//AllCodeStore NOT USED
    var CodeStore = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: '/getallcode/query/'
            }),
            reader: new Ext.data.JsonReader({
                totalProperty: "totalProperty",
                root: 'root',
                fields: ["FCodeText", "FCode"]
            })
    });
	