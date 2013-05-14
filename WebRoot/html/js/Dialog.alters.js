var Msg = {
    Position: null,
    Top: null,
    Left: null,
    Width:null,
    Height:null,
    Alert: function (msg,w,h,fn,drag) {
        Position = ($.browser.msie && parseInt($.browser.version) <= 6) ? 'absolute' : 'fixed';
        Width=(w==null)?300:w; Height=(h==null)?100:h;   
        var Parts= '<div id="pmsg" style="position:' + Position + ';padding:0px;margin:0px;z-index:99999;">';
        Parts += '<table width="'+Width+'px;" cellspacing="0" cellpadding="0" border="0" onselectstart="return false;" style="-moz-user-select: none; font-size: 12px; line-height: 1.4;">  <tr>';
        Parts += '<td width="13" height="33" style="background-image: url(/html/images/images/dialog_lt.png) !important; background: url(/html/images/images/dialog_lt.gif) no-repeat 0 0;"></td>';
        Parts += '<td id="darg" height="33" style="background-image: url(/html/images/images/dialog_ct.png) !important; background: url(/html/images/images/dialog_ct.gif) repeat-x top; cursor: move">';
        Parts += '<div style="padding: 9px 0 0 4px; float: left; font-weight: bold; color: #ffffff;"><img align="absmiddle" src="/html/images/images/icon_dialog.gif" alt="" /><span>信息提示</span></div></td>';
        Parts += '<td width="13" height="33" style="background-image: url(/html/images/images/dialog_rt.png) !important; background: url(/html/images/images/dialog_rt.gif) no-repeat right 0;"></td></tr>';
        Parts += '<tr valign="top"><td width="13" style="background-image: url(/html/images/images/dialog_mlm.png) !important; background: url(/html/images/images/dialog_mlm.gif) repeat-y left;"></td>';
        Parts += '<td align="center"><table width="'+Width+'"  cellspacing="0" cellpadding="0" border="0" style="background-color: #ffffff;width:100%"><tr><td valign="top" height="'+Height+'px">';
        Parts += '<table style="background-color: #ffffff;width:100%" height="100%" border="0" align="center" cellpadding="10" cellspacing="0"><tr><td align="right"><img  src="/html/images/images/icon_alert.gif" width="34" height="34" align="absmiddle"></td>';
        Parts += '<td align="left"  style="font-size:12px;color: #063967; line-height: 150%; "><span  style="font-family: 微软雅黑;">' + msg + '</span></td></tr>	</table>';
        Parts += '</td></tr><tr><td height="36"><div style="border-top: 1px solid #DADEE5; padding: 8px 20px; text-align: center; background-color: #f6f6f6;">';
        Parts += '<input id="msgok" type="button" style="font-family: 微软雅黑; width: 70px" value="确  定" />';
        Parts += '</div></td></tr></table></td><td width="13" style="background-image: url(/html/images/images/dialog_mrm.png) !important; background: url(/html/images/images/dialog_mrm.gif) repeat-y right;">';
        Parts += '</td></tr><tr><td width="13" height="13" style="background-image: url(/html/images/images/dialog_lb.png) !important; background: url(/html/images/images/dialog_lb.gif) no-repeat 0 bottom;">';
        Parts += '</td><td style="background-image: url(/html/images/images/dialog_cb.png) !important; background: url(/html/images/images/dialog_cb.gif) repeat-x bottom;"></td>';
        Parts += '<td width="13" height="13" style="background-image: url(/html/images/images/dialog_rb.png) !important; background: url(/html/images/images/dialog_rb.gif) no-repeat right bottom;">';
        Parts += '</td></tr></table>';
        Parts += '</div>';
        $(document.body).append(Parts);
        $(document.body).append("<script src='/html/js/Darg.js'></script>");
        var top = (($(window).height() / 2) - ($("#pmsg").outerHeight() / 2));
        var left = (($(window).width() / 2) - ($("#pmsg").outerWidth() / 2));
        if (top < 0) top = 0;
        if (left < 0) left = 0;
        if ($.browser.msie && parseInt($.browser.version) <= 6) top = top + $(window).scrollTop();
        $("#pmsg").css({
            top: top + 'px',
            left: left + 'px'
        });

        $("#msgok").click(function () {
        		if(fn instanceof Function){
        				fn();
        		}else{
        		$("#pmsg").remove();
        		}
        });
        
        $("#pmsg").easydrag();
        if(drag){
         $("#pmsg").dragOff();
        }
    }
}