var LatestScore = function(){
	var studentId='';
  return {
    init: function () {
    	studentId= $('#studentId').val();
        $.when(LatestScore.getContent()).then(function(){
    
        	LatestScore.bindViewDetail();
        });
      },
    getContent: function (param) {
      param = param || {};
      param.studentId=studentId;
      return HttpService.get('/scoreReport/items.do', param).then(function (data) {
        $('#tm-term-img').text(data.academicYear);   //显示学年学期
        if(data){
        	if(data.list&&data.list.length>0){
        		Util.render({data: data.list}, '#js-score-tmpl', '#js-score');   
        	}else{
        		$("#tm-latest-title").hide();
        		Util.render({}, '#js-page-no-content-tmpl', '#js-score', {replace: false});     //渲染数据
        	}
        }
        return $.when();
      });
    },
    bindViewDetail: function () {                     //查看详情时的点击事件
      $('.tm-btn-detail').click(function(){
        var obj = {
          courseId: $(this).data('courseId'),
          itemId: $(this).data('itemId'),
          status: $(this).data('status'),
          view:'latestScore'
        };
        App.router.push({name: 'checkDetail', param: obj});  //将课程的ID发送给后台
      });
    }
  };
}();