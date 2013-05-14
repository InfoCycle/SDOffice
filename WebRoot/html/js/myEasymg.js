
		var _m1;
		$.loadmg=function (titled,message){
			if(_m1){
				_m1.dialog("close");
				_m1.parent().remove();
				_m1="";
			}
			$("body").append("<div id='divload' align='centert'><table id='123' valign='Center'><tr><td><img id='imgLoad' height='30px' width='30px' src='../../images/large-loading.gif'/>"
					+"</td><td>"+message+"</td></tr></table></div>");
			var wid=60;
			if(message.length<10){
				wid+=11*message.length;
			}else{
				wid=200;
			}
			$("#divload").dialog({
				width:wid,
				title:titled,
				iconCls:'title-esaytitle',
				modal:true,
				closed:true,
				closable:false,
				shadow:false
			});
			_m1=$("#divload");
			_m1.dialog("open");
		}
		$.tipmg=function(titled,message,type,speed){
			if(_m1){
				_m1.dialog("close");
				_m1.parent().remove();
				_m1="";
			}
			var wid=60;
			if(message.length<10){
				wid+=11*message.length;
			}else{
				wid=200;
			}
			if(!speed){
				speed=1000;
			}
			if(type=="success"){
				$("body").append("<div id='divmessage' align='centert'><table id='123' valign='Center'><tr><td><img id='imgLoad' height='30px' width='30px' src='../../images/submitsuccess.png'/>"
						+"</td><td><font>"+message+"</font></td></tr></table></div>");
			}else if(type=="error"){
				$("body").append("<div id='divmessage' align='centert'><table id='123' valign='Center'><tr><td><img id='imgLoad' height='30px' width='30px' src='../../images/wrong.gif'/>"
						+"</td><td><font>"+message+"</font></td></tr></table></div>");
			}else if(type=="warning"){
				$("body").append("<div id='divmessage' align='centert'><table id='123' valign='Center'><tr><td><img id='imgLoad' height='30px' width='30px' src='../../images/error_128.png'/>"
						+"</td><td><font>"+message+"</font></td></tr></table></div>");
			}
			$("#divmessage").dialog({
				width:wid,
				title:titled,
				modal:true,
				iconCls:'title-esaytitle',
				closed:true,
				closable:false,
				shadow:false
			});
			$("#divmessage").dialog("open");
			_m1=$("#divmessage");
			setTimeout("_m1.dialog('close')",speed);
		}
		$.loadmg.close=function(){
			$("#divload").dialog("close");
		}