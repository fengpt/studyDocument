<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" class="ext-strict">
<head>
	<!-- ${request.requestURI} -->
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${title}</title>
	<base href="${request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request['contextPath']}/"></base>
	<link rel="icon" type="image/png" href="${resource_url}images/favicon.png">	
	<script type="text/javascript" src="dwr/engine.js"></script>
	<script type="text/javascript" src="dwr/util.js"></script>
	<script type="text/javascript" src="dwr/interface/configRemoteCallController.js?${VERSION}"></script>
	<script type="text/javascript" src="dwr/interface/SelectRemoteCallController.js?${VERSION}"></script>
	<script type="text/javascript" src="dwr/interface/DataSelectRemoteCallController.js?${VERSION}"></script>
	<script type="text/javascript" src="${resource_url}thirdparty/LABjs/LAB.min.js"></script>
	
	<link href="${resource_url}themes/resource/redmond/jquery.ui.all.css" rel="stylesheet" type="text/css">
	<link href="${resource_url}thirdparty/jquery/jquery.jqGrid-4.3.1/css/ui.jqgrid.css" rel="stylesheet" type="text/css">
	
	<link href="${resource_url}thirdparty/jquery/validate/css/screen.css" rel="stylesheet" type="text/css">
	
	<!--$if{jqueryVersion}-->
		<script type="text/javascript" src="${resource_url}thirdparty/jquery/${jqueryVersion}"></script>
	<!--$else-->
		<script type="text/javascript" src="${resource_url}thirdparty/jquery/jquery-1.8.3.min.js"></script>	
	<!--$end-->
	<script type="text/javascript" src="${resource_url}thirdparty/jquery/jsTree/_lib.js"></script>
	<script type="text/javascript" src="${resource_url}thirdparty/jquery/jquery.layout.min.js"></script>
	<script type="text/javascript" src="${resource_url}thirdparty/jquery/jquery.blockUI.js"></script>
	
	<script type="text/javascript" src="${resource_url}thirdparty/jquery/ui/i18n/ui.datepicker-zh-CN.js"></script>
	<script type="text/javascript" src="${resource_url}thirdparty/jquery/ui/jquery-ui.min.js"></script>
	
	<script type="text/javascript" src="${resource_url}thirdparty/jquery/validate/jquery.validate.js"></script>
	
	<link rel="stylesheet" type="text/css" href="${resource_url}thirdparty/jquery/jsTree/tree_component.css" />
	<script type="text/javascript" src="${resource_url}thirdparty/jquery/jsTree/tree_component.js"></script>
	
	<!-- éç¥ -->
	<link type="text/css" href="${resource_url}thirdparty/jquery/noty/css/jquery.noty.css" rel="stylesheet" />
	<link type="text/css" href="${resource_url}thirdparty/jquery/noty/css/noty_theme_twitter.css" rel="stylesheet" />
	<script type="text/javascript" src="${resource_url}thirdparty/jquery/noty/js/jquery.noty.js"></script>
	
	<script type="text/javascript" src="${resource_url}thirdparty/jquery/jquery.jqGrid-4.3.1/jquery.jqGrid.all.min.js"></script>

	<script type="text/javascript" src="${resource_url}thirdparty/fg-menu/fg.menu.js"></script>
	
	<script type='text/javascript' src='${resource_url}thirdparty/lhgdialog/lhgdialog-extra-v5.js'></script>
	<script type='text/javascript' src='${resource_url}script/DialogUtilNew-v5.js?${VERSION}'></script>
	
	<script type="text/javascript" src="${resource_url}thirdparty/jquery/qtip/jquery.qtip-1.0.0-rc3.min.js"></script>
	<!-- jquery template -->
	<script type="text/javascript" src="${resource_url}thirdparty/jquery/template/jquery.tmpl.min.js"></script>
	
	<script type='text/javascript' src='${resource_url}thirdparty/json2.js'></script>
     
	<script type="text/javascript" src="${resource_url}script/themeswitcher.js?${VERSION}"></script>

	<!-- èªå®ä¹å¬å±js -->
	<script type="text/javascript" src="${resource_url}script/AppUtils.js?${VERSION}"></script>
	<script type="text/javascript" src="${resource_url}script/Message.js?${VERSION}"></script>
	<script type="text/javascript" src="${resource_url}script/AppSelect.js?${VERSION}"></script>
	<script type="text/javascript" src="${resource_url}script/general.js?${VERSION}"></script>
	<script type="text/javascript" src="${resource_url}script/jquery.my.js?${VERSION}"></script>
	<script type="text/javascript" src="${resource_url}script/jquery.aspanel.js?${VERSION}"></script>
	<script type="text/javascript" src="${resource_url}script/jquery.dropdown.js?${VERSION}"></script>
	<script type="text/javascript" src="${resource_url}script/JsTreeUtil.js?${VERSION}"></script>
	<script type="text/javascript" src="${resource_url}thirdparty/jquery/validate/jquery-validation-my.js?${VERSION}"></script>
	
	<link href="${resource_url}thirdparty/jquery/smartwizard/styles/smart_wizard.css" rel="stylesheet" type="text/css">
	<script type="text/javascript" src="${resource_url}thirdparty/jquery/smartwizard/js/jquery.smartWizard-2.0.js"></script>
	<script type="text/javascript" src="${resource_url}thirdparty/jquery/ui/ui.tabs.paging.js"></script>
	<script type="text/javascript" src="${resource_url}thirdparty/thirdparty_default_setting.js"></script>
	<!--[if lt IE 7]>
        <script type="text/javascript" src="${resource_url}thirdparty/ie7/src/IE7.js"></script>
    <![endif]-->
	<script type="text/javascript">
	 var isIE = !!window.ActiveXObject;
	 var isIE6 = isIE && !window.XMLHttpRequest;
	 var isIE9 = isIE && navigator.userAgent.toLocaleLowerCase().match(/msie ([\d.]+)/)[1] == '9.0';
	</script>
	<script type="text/javascript">
		var contextPath = "${request['contextPath']}",
			resourceContextPath = '${resource_url}',
			jwContextPath = '${CONTEXTPATH_JW}',
			teacherContextPath = '${CONTEXTPATH_TEACHER}',
			ptContextPath = '${CONTEXTPATH_PT}',
			oaContextPath = '${CONTEXTPATH_OA}',
			dpContextPath = '${CONTEXTPATH_DP}',
			rsContextPath = '${CONTEXTPATH_RS}',
			zyContextPath = '${CONTEXTPATH_ZY}',
			siteContextPath = '${CONTEXTPATH_SITE}',
			cjfxContextPath = '${CONTEXTPATH_CJFX}';
		var P = window.parent;
		var D = null;
		if(P && AppUtils.isSameDomain() && typeof P.loadinndlg != 'undefined'){
			D = P.loadinndlg();
		}
		var EcampusGlobalSetting = {};

		<!--$if{CUSTOM_LABEL_JS != null}-->

		    ${CUSTOM_LABEL_JS}

		<!--$end-->
	</script>
	
	<link href="${resource_url}thirdparty/ext/resources/css/form.css" type="text/css" rel="stylesheet">
	<!--[if lt IE 7]>
	<script src="${resource_url}thirdparty/DD_belatedPNG.js" type="text/javascript"></script>
	<![endif]-->
	<script type="text/javascript" src="${resource_url}script/getDate.js?${VERSION}"></script>
	<script type="text/javascript" src="${resource_url}thirdparty/jquery/jquery-ui-timepicker-addon.js"></script>
</head>
<body class="body-content">
<input type="hidden" id="GLOBAL_CURRENT_USER_ID" value="${&com.app.aq.SecurityFacade.getCurUserIdNotThrowExceptionIfAnonymous()}"/>