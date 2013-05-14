var ReplyDetail={
	show:function(setting){
	  var dr=new Array();
	  dr.push('<div><ul style="list-style-type: none;margin:2px 2px 2px 2px;padding:0px">');
	  dr.push('<li>{0}</li>');
	  dr.push('<li><textarea  cols="{1}" rows="{2}" id="w-remark" name="w-remark"></textarea></div></li>');
	  dr.push('<li style="text-align: center;list-style-type: none;margin-top:10px"><input type="button" id="reply" value="确   定" class="com-button" />&nbsp;&nbsp;');
	  dr.push('<input type="button" id="colse" value="取   消" class="com-button" /></li>');
	  dr.push('</ul></div>');
	  
	  $('input[id=reply]').live({click:function(){
		  if($('#w-remark').val()){
			  setting.Fn();
			  $.LightBoxObject.close();
			  $('input[id=reply]').die();
		  }else{
			  $.jBox.info(setting.message,'提示');  
		  }		  
	  }});
	  
	  $('input[id=colse]').live({click:function(){
		  $.LightBoxObject.close();
		  $('input[id=reply]').die();
	  }});
	  
	  var str= dr.join(new String()).format(setting.title,setting.cols,setting.rows);
	  return str;
	},siger:function(setting){
		var dr=new Array();
		  dr.push('<div><ul style="list-style-type: none;padding:0px;line-height:30px">');
		  dr.push('<li style="text-align:left;">{0}</li>');
		  dr.push('<li style="margin-top:10px;text-align:left">{1}：<input type="text" id="txtsiger" style="width:75%"></li>');
		  dr.push('<li style="text-align: center;list-style-type: none;margin-top:20px"><input type="button" id="btn-siger" value="{2}" class="com-button" />&nbsp;&nbsp;');
		  dr.push('<input type="button" id="colse" value="取   消" class="com-button" /></li>');
		  dr.push('</ul></div>');
		  $('input[id=btn-siger]').live({click:function(){
			  if($('#txtsiger').val()){
				  setting.Fn();
				  $.LightBoxObject.close();
				  $('input[id=btn-siger]').die();
			  }else{
				  $.jBox.info(setting.message,'提示');  
			  }		  
		  }});
		  
		  $('input[id=colse]').live({click:function(){
			  $.LightBoxObject.close();
			  $('input[id=btn-siger]').die();
		  }});
		  
		  var str= dr.join(new String()).format(setting.title,setting.name,setting.buttons);
		  return str;
	}

};