$#embed{"/common/xq/xqSelect.jsp"}
<div id="dwrStackTraceDialog" title="查看详细错误信息" style="display: none"></div>
${&com.app.aq.config.dto.ConfigDTO.getValue('footer-html')}
</body>
<script type="text/javascript" src="${resource_url}script/commonTeacherSelect.js?201211221704"></script>
<script type="text/javascript" src="${resource_url}script/UserSetting.js"></script>
<script type="text/javascript" src="${resource_url}script/page-init.js"></script>
<script type="text/javascript" src="${resource_url}thirdparty/jquery/DD_roundies/DD_roundies_0.0.2a.js"></script>
<script type="text/javascript"><!--
jQuery().ready( function() {
	try{
		DD_roundies.addRule('.corner-patch', '5px');
	}catch (e) {
		// TODO: handle exception
	}
});
-->
</script>
<script type="text/javascript"><!--
jQuery().ready(function(){  
	
	AppUtils.initDateFields(); 
	jQuery('form').submit(function(){
		return false;
	});
});	

//处理dwr异常
function viewDwrStackTraceInfo(){
	jQuery.unblockUI();
	jQuery('#dwrStackTraceDialog').dialog({
		width:'90%',
		height: 400,
		zIndex:99999,
		position: ['center', 'top'],
		buttons:{
			'关闭': function(){
				jQuery(this).dialog( "close" )
			}
		}
	});
}
dwr.engine.setErrorHandler(function(errorStr, exception){
	if(errorStr){

		if(errorStr.indexOf('read input') >= 0 || errorStr.indexOf('Not Found') >= 0 || errorStr.indexOf('Bad Gateway') >= 0){

			//忽略异常：Fail to read input,一般是因为网络问题引起的

			//忽略异常：Not Found/Bad Gateway,一般是重启服务器引起的

			return;

		}

	}
	var stackText = errorStr + "<br/>";
	if(exception.stackTrace) {
		for(var i = 0; i < exception.stackTrace.length; i++) {
			var st = exception.stackTrace[i];
			stackText += st.className + '.' + st.methodName + "(" + (st.fileName == null? 'Unknown Source' : st.fileName + ":" + st.lineNumber) + ")<br/>";
		}
	}
	jQuery('#dwrStackTraceDialog').html(stackText);
	jQuery.blockUI({
		message: '<div class="ui-widget"><div class="ui-state-error ui-corner-all" style="padding: 0.7em;background:#f2f2f2"><table><tr><td><img src="${resource_url}images/404_tu.jpg"></td><td style="text-align:left"><div style="color:#2C5B87;font-size: 18px;font-weight: bold;font-size: 16px;">错误信息如下：</div><p style="margin:15px 5px 5px 20px;font-size:14px">' + errorStr + '</p></td></tr></table><table width="100%"><tr><td align="left"><a href="javascript:viewDwrStackTraceInfo()" style="color: gray;text-decoration: none;">详细错误信息</a></td><td align="right"><a href="javascript:jQuery.unblockUI()" style="text-decoration: none;">关闭</a></td></tr></table></div></div>',
		css: {
			border: 'none',
			background: 'transparent',
			cursor: 'default',
			width: '500px',
			top: '20%'
		}
	});
});
</script>
</html>