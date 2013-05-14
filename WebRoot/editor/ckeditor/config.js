/*
 * Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function(config) {
	// Define changes to default configuration here. For example:
	config.language = 'zh-cn';
	config.uiColor = '#AADC6E';
	config.skin='office2003'; // 编辑器皮肤样式
	config.font_names = '宋体;仿宋;仿宋_GB2312;楷体;楷体_GB2312;新宋体;黑体;隶书;幼圆;微软雅黑;Arial;Comic Sans MS;Courier New;Tahoma;Times New Roman;Verdana'; 
	// 取消 “拖拽以改变尺寸”功能
	// config.resize_enabled = false;
	// 使用基础工具栏
	// config.toolbar = "Basic";
	// 使用全能工具栏
	config.toolbar = 'Default';

	config.width="778px";
	config.enterMode = CKEDITOR.ENTER_BR;

    config.resize_enabled= false;
	//增加行间距调整
	config.extraPlugins += (config.extraPlugins ?',lineheight' :'lineheight');
	// 使用自定义工具栏
	config.toolbar_Default =
    [
        ['Source','-','Preview','-','Templates'],
        ['Cut','Copy','Paste','PasteText','PasteFromWord','-','Scayt'],
        ['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat'],
        '/',
        ['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
        ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote','CreateDiv'],
        ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
        ['Link','Unlink','Anchor','Image','Table'],
        '/',
        ['Styles','Format','Font','FontSize','lineheight'],
        ['TextColor','BGColor']
    ];
	// 在 CKEditor 中集成 CKFinder，注意 ckfinder 的路径选择要正确。
	config.filebrowserBrowseUrl = '/editor/ckfinder/ckfinder.html';
	config.filebrowserImageBrowseUrl = '/editor/ckfinder/ckfinder.html?type=Images';
	config.filebrowserFlashBrowseUrl = '/editor/ckfinder/ckfinder.html?type=Flash';
	config.filebrowserUploadUrl = '/editor/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files';
	config.filebrowserImageUploadUrl = '/editor/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images';
	config.filebrowserFlashUploadUrl = '/editor/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash';

	config.pasteFromWordRemoveStyle = false;  //是否强制去除word的样式
    config.pasteFromWordRemoveFontStyles = false; //是否强制去除word的字体样式

	config.filebrowserWindowWidth = '760';
	config.filebrowserWindowHeight = '620';	
};
