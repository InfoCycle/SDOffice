/*
 * 1、如何使用验证控件？
 * 要验证的控件中加入属性：empty="true",绑定如下所示：
 * $('[empty=true]').emptyAll();
 * 要验证的控件中加入属性：exp="number",代表是数字验证,如果是exp="percentage",代表是百分比验证,绑定如下所示：
 * $('[exp]').PatternAll();
 */

(function($) {
	$.extend($.fn, {
		defaults : {
			emptyStyle : 'emptyStyle',
			patternStyle:'patternStyle',
			emptyAttr:'empty',
			emptyTip:'该项信息需要填写！',
			attrTip:'<span style="color:green">信息填写正确！</span>',
			arrEmpty:'<span style="color:green">此信息选填！</span>',
			emptyFn:null,
			patternAttr:'exp',
			patternFn:null,
			parentID:false,
		},
		allrules :function(){
			var rules=new Array();
			rules['integer']={regex : /^[\-\+]?\d+$/,err : '不是有效地整数.'}
			rules['number']={
				regex : /^[\-\+]?(([0-9]+)([\.,]([0-9]+))?|([\.,]([0-9]+))?)$/,
				err : '无效数字(格式:20或者20.00).'
			};
			rules['percentage']={
					regex : /^(([0-9]+)([\.,]([0-9]+)([%])+)|(([0-9]+)([%])+))$/,
					err : '无效百分数(格式：20%或者20.00%).'
			};
			return rules;
		},
		emptyAll : function(options) {
			var err=true;
			var setting =($.isEmptyObject(options))?$.extend({},{defaults:this.defaults}):$.extend(true,{},{defaults:this.defaults}, {defaults:options});
			$(this).each(function(i, entity) {
				$(this).createtip();
				$(this).changeStyle(setting);
				var getValue=$(this).getValue();
				if (!$.trim(getValue)) {
					$(this).addClass(setting.defaults.emptyStyle);
					if(setting.defaults.parentID){
						$(this).parent().addClass(setting.defaults.emptyStyle);
					}
					err &=false;
				} else {
					$(this).removeClass(setting.defaults.emptyStyle);
					if(setting.defaults.parentID){
						$(this).parent().removeClass(setting.defaults.emptyStyle);
					}
				}
			});
			return err;
		},
		changeStyle:function(setting){
			$(this).live({change:function() {
				var getValue=$(this).getValue();
				if($.trim(getValue)){
				   $(this).removeClass(setting.defaults.emptyStyle);
				   $(this).parent().removeClass(setting.defaults.emptyStyle);
				}else{
				   $(this).parent().addClass(setting.defaults.emptyStyle);
				   $(this).addClass(setting.defaults.emptyStyle);
				}
			 },focus:function(){
				 var getValue=$(this).getValue();
				 if($.trim(getValue)){
					 $(this).poshytip('update',setting.defaults.attrTip);
				}else{
					$(this).poshytip('update',setting.defaults.emptyTip);	
				}
			 }});
		},
		PatternAll:function(options){
			var err=true;
			var setting =($.isEmptyObject(options))?$.extend({},{defaults:this.defaults}):$.extend(true,{},{defaults:this.defaults}, {defaults:options});
			var rules=this.allrules();
			$(this).each(function(i,entity){
				$(this).createtip();
				$(this).PatternTip(rules,setting);
				var getValue=$(this).getValue();
				var attr=$(this).attr(setting.defaults.patternAttr);
				if($.trim(getValue)){ 
				   	 if(rules[attr].regex.test(getValue)){
				   		$(this).removeClass(setting.defaults.patternStyle);
						$(this).parent().removeClass(setting.defaults.patternStyle);
				   	 }else{
						$(this).parent().addClass(setting.defaults.patternStyle);
						$(this).addClass(setting.defaults.patternStyle);
						err&=false;	
				   	 }
				   }else{
					   $(this).removeClass(setting.defaults.patternStyle);
					   $(this).parent().removeClass(setting.defaults.patternStyle);
				   }
			 });
		  return err;
		},PatternTip:function(rules,setting){
			$(this).live({change:function(){
				var getValue=$(this).getValue();
				var attr=$(this).attr(setting.defaults.patternAttr);
				if($.trim(getValue)){ 
				   	 if(rules[attr].regex.test(getValue)){
				   		$(this).removeClass(setting.defaults.patternStyle);
						$(this).parent().removeClass(setting.defaults.patternStyle);
				   	 }else{
				   		$(this).parent().addClass(setting.defaults.patternStyle);
						$(this).addClass(setting.defaults.patternStyle);
				   	 }
				   }else{
					   $(this).removeClass(setting.defaults.patternStyle);
					   $(this).parent().removeClass(setting.defaults.patternStyle);
				   }
			 },focus:function(){
				 var getValue=$(this).getValue();
				 var exr=$(this).attr(setting.defaults.emptyAttr);
				 var attr=$(this).attr(setting.defaults.patternAttr);
				 if($.isEmptyObject(exr)){
					 if($.trim(getValue)){
						if(rules[attr].regex.test(getValue)){
							 $(this).poshytip('update',setting.defaults.attrTip);
						}else{
							 $(this).poshytip('update',rules[attr].err);	
						} 
					}else{
						 $(this).poshytip('update',setting.defaults.attrTip);
					}
				 }else{
					 if(!$.isEmptyObject(attr)){
						 if($.trim(getValue)){
							 if(rules[attr].regex.test(getValue)){	
								 $(this).poshytip('update',setting.defaults.attrTip);
							}else{
								 $(this).poshytip('update',rules[attr].err);
							} 
						}else{
							$(this).poshytip('update',setting.defaults.emptyTip);	
						} 
					 }					
				 }
			 }});
		},createtip:function(){
			$(this).each(function(i,entity){
				$(this).poshytip({
					className : 'tip-yellowsimple',
					showOn : 'focus',
					alignTo : 'target',
					alignX : 'right',
					alignY : 'center',
					offsetX : 5
				});
			});
		}
		,getValue:function(){
			var type=$(this).attr('type');
			var dr=new Array();
			switch(type){
			case "text":
            case "password":
            	dr.push($(this).val());
            	break;
            case "textarea":
            	dr.push($(this).val());
				break;
            case "radio":
            case "checkbox":
           	 break;
            case "select":
            	dr.push($(this).find("option:selected").val());
                break;
			}
			return dr.toString();
		}
	});
})(jQuery);