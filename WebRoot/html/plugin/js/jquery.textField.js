/*
 * by zhangxinxu welcome to my personal website http://www.zhangxinxu
 * textField.js 文本域相关操作插件
 * 2010-10-24 v1.0 实现焦点获取失去样式切换，字数输入提示，按钮自动可用禁用，历史以及Ajax自动保存功能
*/
(function($){
	$.fn.textField = function(options) {
		var defaults = {
			mini: 50,
			maxs: 500,
			remindId: "",
			buttonId: "",
			historyId: "",
			autoSaveId: "",
			safeColor: "green",
			warnColor: "red",
			historyNum: 5,
			historyWord: 100,
			ajaxUrl: "",
			ajaxKey: "value",
			autoSaveTime: 30000, //30秒
			defaultText: "",
			focusClass: "",
			disabledCall: function() {
				$.noop();
			},
			enabledCall: function() {
				$.noop();
			}
		};
		var sets = $.extend(defaults, options || {});
		var pms = {
			rem: null,
			btn: null,
			his: null,
			auto: null,
			txt: sets.defaultText,
			cl: sets.focusClass,
			conArr: [],
			timeArr: [],
			create: false,
			ajaxCache: "",
			time: parseInt(sets.autoSaveTime, 10)
		};
		//一些需要的jQuery对象
		if(sets.remindId){
			pms.rem = $("#"+sets.remindId);
		}
		if(sets.buttonId){
			pms.btn = $("#"+sets.buttonId);
		}
		if(sets.historyId){
			pms.his = $("#"+sets.historyId);
		}
		if(sets.autoSaveId){
			pms.auto = $("#"+sets.autoSaveId);
		}
		
		//当前文本域对象
		var self = $(this).eq(0);
		
		//一些方法
		var f = {
			//获取文本域值
			getValue: function() {
				return self.val();
			},
			getNow: function() {
				var now = new Date(), nowTime = now.toLocaleTimeString();
				return nowTime;
			},
			//输入文字之按钮和提示变化
			remindObj: function(status, num) {
				if (pms.rem && pms.rem.size()) {
					var rem = pms.rem;
					if (status === 0) {
						//文字个数不足的情况
						rem.html('您还需要输入<span style="color:'+sets.warnColor+';">'+num+'</span>字');
					} else if (status === 1) {
						rem.html("您还可以输入<span style='color:"+sets.safeColor+";'>"+num+"</span>字");
					} else if (status === 2) {
						rem.html("<span style='color:"+sets.warnColor+";'>您已经超出"+num+"字</span>");
					} else {
						rem.empty();
					}
				}
			},
			buttonObj: function(status) {
				if (pms.btn && pms.btn.size()) {
					var btn = pms.btn;
					if (status === 1){
						//如果数值范围正常
						btn.removeAttr("disabled");
						sets.enabledCall.call(self);
					} else {
						btn.attr("disabled","disabled");
						sets.disabledCall.call(self);
					}
				}
			},
			//创建历史面板
			historyCreate: function() {
				var top = self.offset().top + self.outerHeight(), left = self.offset().left, width = self.outerWidth() - 2;
				if (!pms.create) {
					//首次显示历史，装载主体结构
					$('<div id="hisOutBox" class="history_out_box" style="display:none;left:'+left+'px;top:'+top+'px;width:'+width+'px;"><ol id="hisOlBox" class="history_ol_box"></ol><div class="history_operate"><a id="hisRemove" href="javascript:;">清空</a><a id="hisClose" href="javascript:;">关闭</a></div></div>').appendTo($("body"));
					pms.create = true;
				}
				//创建历史列表
				var out = $("#hisOutBox"), ol = $("#hisOlBox"), hisLiHtml = "", new_value = "", limit = parseInt(sets.historyWord, 10);
				ol.html("");
				$.each(pms.conArr, function(index, value) {
					if (value.length >= limit) {
						var half = Math.floor(limit / 2);
						new_value = value.slice(0, half) + "..." + value.slice(-half);
					} else {
						new_value = value;
					}
					hisLiHtml = '<li id="'+index+'" class="history_li_list">'+new_value+'<div class="history_time">'+pms.timeArr[index]+'</div></li>' + hisLiHtml;
				});
				if (hisLiHtml) {
					ol.html(hisLiHtml);
				}
				//一些操作事件
				//列表经过与点击
				$(".history_li_list").hover(function() {
					$(this).addClass("history_list_hover");								 
				}, function() {
					$(this).removeClass("history_list_hover");	
				}).click(function() {
					var id = Number($(this).attr("id")), getHtml = pms.conArr[id].replace(/<br>/gi, "\n").replace(/&lt;/g, "<").replace(/&gt;/g, ">").replace(/&amp;/g, "&");
					self.val(getHtml);
					f.keyupEvt();
					out.slideUp();	
				});
				//关闭
				$("#hisClose").click(function(){
					out.slideUp();
					return false;
				});
				//清除
				$("#hisRemove").click(function(){
					pms.conArr = [];
					pms.timeArr = [];
					out.hide();
					pms.his.hide();
					return false;
				});
			},
			//显示历史面板
			historyShow: function() {
				if (!pms.create || $("#hisOutBox").css("display") === "none") {
					f.historyCreate();
				}
				$("#hisOutBox").slideToggle();
				return false;
			},
			//ajax自动保存
			autoSave: function() {
				var v = f.getValue();
				if (!v || v === pms.ajaxCache || v === pms.txt) {
					return;	
				} else {
					pms.ajaxCache = v;	
				}
				var post_v = escape(f.getValue());
				$.ajax({
					type: "POST",
					url: sets.ajaxUrl,
					data: sets.ajaxKey + post_v,
					success: function(msg){
						if (pms.auto && pms.auto.size()) {
							pms.auto
								.text("已于" + f.getNow() + "自动保存")
								.show("fast", function() {
									setTimeout(function(){
										pms.auto.hide();					
									}, 5000);														
							});	
						}
					}
				});
			},
			//加载检测
			loadCheck: function() {
				var v = f.getValue();
				if (v && v !== pms.txt) {
					//默认状态下，文本域中有值（可能是浏览器自己的缓存，或是Ajax的自动保存）
					//触发键盘事件
					self.trigger("keyup");
				}
			},
			focusEvt: function() {
				var v = f.getValue();
				if (pms.txt && v === pms.txt) {
					self.val("");	
				}
				if (pms.cl) {
					self.addClass(pms.cl);
				}
			},
			blurEvt: function() {
				var v = f.getValue(), l = v.length, his = pms.his, lArr = pms.conArr.length;
				if (pms.txt && v === "") {
					self.val(pms.txt);
				}
				if (pms.cl) {
					self.removeClass(pms.cl);
				}
				//缓存数据，历史备用
				if (l && his && his.size()) {
					//有历史，且文本域有值
					var new_v = v.replace(/&/g, "&amp;").replace(/</g, "&lt;").replace(/>/g, "&gt;").replace(/\n/g, "<br>");
					//当前时间(00:00:00)
					var nowTime = f.getNow();
					if (lArr) {
						his.show();
						if (pms.conArr[lArr-1] !== new_v) {
							pms.conArr.push(new_v);
							pms.timeArr.push(nowTime);
						}
					} else {
						pms.conArr.push(new_v);
						pms.timeArr.push(nowTime);
					}
					var delnum = pms.conArr.length - sets.historyNum;
					if ( delnum > 0 ) {
						pms.conArr.splice(0, delnum);
						pms.timeArr.splice(0, delnum);
					}
					//如果历史层当前显示，刷新历史记录
					if (pms.create && $("#hisOutBox").css("display") === "block") {
						f.historyCreate();
					}
 				}
			},
			keyupEvt: function() {
				var v = f.getValue(), l = v.length, num = 0, mini = parseInt(sets.mini, 10) || 0, maxs = parseInt(sets.maxs, 10);
				if (!maxs || mini > maxs) {
					return;	
				}
				if (l < mini) {
					num = mini - l;
					f.remindObj(0, num);
					f.buttonObj(0);
				} else if (l <= maxs) {
					//在限制范围内
					num = maxs - l;
					f.remindObj(1, num);
					f.buttonObj(1);
				} else {
					num = l - maxs;	
					f.remindObj(2, num);
					f.buttonObj(2);
				}
			}
		};
		
		//文本域相关事件绑定
		self.bind("focus", f.focusEvt).bind("blur", f.blurEvt).bind("keyup", f.keyupEvt);
		//文本域鼠标右键粘贴
		self.get(0).onpaste = function() {
			setTimeout(f.keyupEvt, 100);
		};
		//历史按钮绑定
		if (pms.his) {
			pms.his.bind("click", f.historyShow);	
		}
		//ajax触发
		if (sets.ajaxUrl && pms.time) {
			setInterval(f.autoSave, pms.time);
		}
		//加载检测
		f.loadCheck();
	};
})(jQuery);