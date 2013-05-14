var Modal = {
	Position : null,
	Top : null,
	Left : null,
	Dialog : function(msg) {
		Position = ($.browser.msie && parseInt($.browser.version) <= 6)
				? 'absolute'
				: 'fixed';
		var Parts = '<div id="pmsg" style="position:' + Position
				+ ';padding:0;margin:0;z-index:99999">';
		Parts += '<table width="350px" cellspacing="0" cellpadding="0" border="0" onselectstart="return false;" style="-moz-user-select: none; font-size: 12px; line-height: 1.4;">  <tr>';
		Parts += '<td width="13" height="33" style="background-image: url(../images/images/dialog_lt.png) !important; background: url(../images/images/dialog_lt.gif) no-repeat 0 0;"></td>';
		Parts += '<td id="darg" height="33" style="background-image: url(../images/images/dialog_ct.png) !important; background: url(../images/images/dialog_ct.gif) repeat-x top; cursor: move">';
		Parts += '<div style="padding: 9px 0 0 4px; float: left; font-weight: bold; color: #fff;"><img align="absmiddle" src="../images/images/icon_dialog.gif" alt="" /><span>信息提示</span></div></td>';
		Parts += '<td width="13" height="33" style="background-image: url(../images/images/dialog_rt.png) !important; background: url(../images/images/dialog_rt.gif) no-repeat right 0;"></td></tr>';
		Parts += '<tr valign="top"><td width="13" style="background-image: url(../images/images/dialog_mlm.png) !important; background: url(../images/images/dialog_mlm.gif) repeat-y left;"></td>';
		Parts += '<td align="center"><table width="100%" cellspacing="0" cellpadding="0" border="0" bgcolor="#ffffff"><tr><td valign="top" height="50">';
		Parts += '<table height="100%" border="0" align="center" cellpadding="10" cellspacing="0"><tr><td align="right"><img  src="../images/images/icon_alert.gif" width="34" height="34" align="absmiddle"></td>';
		Parts += '<td align="left"  style="font-size:12px;color: #063967; line-height: 150%; "><span id="content" name="content" style="font-family: 微软雅黑;">'
				+ msg + '</span></td></tr>	</table></td></tr>';
		Parts += '</table></td><td width="13" style="background-image: url(../images/images/dialog_mrm.png) !important; background: url(../images/images/dialog_mrm.gif) repeat-y right;">';
		Parts += '</td></tr><tr><td width="13" height="13" style="background-image: url(../images/images/dialog_lb.png) !important; background: url(../images/images/dialog_lb.gif) no-repeat 0 bottom;">';
		Parts += '</td><td style="background-image: url(../images/images/dialog_cb.png) !important; background: url(../images/images/dialog_cb.gif) repeat-x bottom;"></td>';
		Parts += '<td width="13" height="13" style="background-image: url(../images/images/dialog_rb.png) !important; background: url(../images/images/dialog_rb.gif) no-repeat right bottom;">';
		Parts += '</td></tr></table>';
		Parts += '</div>';
		$("BODY").append(Parts);

		var top = (($(window).height() / 2) - ($("#pmsg").outerHeight() / 2))
				+ (-75);
		var left = (($(window).width() / 2) - ($("#pmsg").outerWidth() / 2))
				+ 0;
		if (top < 0)
			top = 0;
		if (left < 0)
			left = 0;
		if ($.browser.msie && parseInt($.browser.version) <= 6)
			top = top + $(window).scrollTop();
		$("#pmsg").css({
					top : top + 'px',
					left : left + 'px'
				});

		$("#msgok").click(function() {
					$("#pmsg").remove();
				});
	}
}

function DialogMsg(m, url) {
	$("#content").empty();
	$("#content").append("恭喜你，注册成功！<br/><span style='color:red'>"+m + "</span>秒自动跳转到主页！");
	Modal.Dialog("恭喜你，注册成功！<br/><span style='color:red'>"+m + "</span>秒自动跳转到主页！");
	if (--m > 0) {
		setTimeout("DialogMsg(" + m + ",'" + url + "')", 2000);
	} else {
		location.href = url;
	}
}

function DialogAlertMsg(m, url) {
	$("#content").empty();
	$("#content").append("保存成功！请等待培训机构审批，或者打印报名表到报名地点进行审核！<br/><span style='color:red'>"+m + "</span>秒自动跳转到报名列表！");
	Modal.Dialog("保存成功！请等待培训机构审批，或者打印报名表到报名地点进行审核！<br/><span style='color:red'>"+m + "</span>秒自动跳转到报名列表！");
	if (--m > 0) {
		setTimeout("DialogAlertMsg(" + m + ",'" + url + "')", 2000);
	} else {
		location.href = url;
	}
}

function DialogAlertPassMsg(m, url) {
	$("#content").empty();
	$("#content").append("保存成功！<br/><span style='color:red'>"+m + "</span>秒自动跳转到报名列表,进行数据查看！");
	Modal.Dialog("保存成功！<br/><span style='color:red'>"+m + "</span>秒自动跳转到报名列表,进行数据查看！");
	if (--m > 0) {
		setTimeout("DialogAlertPassMsg(" + m + ",'" + url + "')", 2000);
	} else {
		location.href = url;
	}
}