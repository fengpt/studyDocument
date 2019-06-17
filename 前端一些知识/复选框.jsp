<script type="text/javascript">
	jQuery(document).ready(function(){
		
	});
	var isSelectAll = false;
	function selectAll(id) {
		var i=0;
		jQuery("input[name='"+id+"']").each(function(){
			jQuery("input[name='"+id+"']").get(i).checked=true;
			i++;
		});
		isSelectAll=true;
	}
	function cancelSelectAll(id) {
		var i=0;
		jQuery("input[name='"+id+"']").each(function(){
			jQuery("input[name='"+id+"']").get(i).checked=false;
			i++;
		});
		isSelectAll=false;
	}
	function allSelectedOrnot(id){
		if(isSelectAll){
			cancelSelectAll(id);
		}else{
			selectAll(id);
		}
	}
	function isSelect(obj,id){
		var isfalg=true;
		jQuery("input[name='"+id+"']").each(function(){
			if(!jQuery(this).attr('checked')){
				isfalg=false;
			}
		});
		if(isfalg){
			jQuery("input[name=all"+id+"]").attr("checked",true);
			isSelectAll=true;
		}else{
			jQuery("input[name=all"+id+"]").removeAttr("checked");
			isSelectAll=false;
		}
	}
	
</script>
<div>
	<div style="width:40%;">
		<div style="font-weight: bold;">选择学校:</div>
		<div><label><input type="checkbox" name="allschool" id="allschool" value="" onclick="allSelectedOrnot('school')">全选&nbsp;&nbsp;|</label></div>
		<div style="margin-left: 70px;margin-top: -22px;">
		$for{school:listSchool}
			<label><input type="checkbox" name="school" id="school" value="${school.id }" onclick="isSelect(this,'school')">${school.name }</label><br>
		$end
		</div>
	</div>
	<div style="width:60%;">
	
	
	
	</div>


</div>