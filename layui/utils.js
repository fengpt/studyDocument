var HttpService = function() {
  return {
    get: function(url, params) {
      params = params || {};
      // 所有请求都需带的参数：sign
      _.merge(params, {sign: App.STORE.sign,time:new Date()});
      return $.ajax({
        url: contextPath + url,
        data: params
      });
    },
    post: function(url, params) {
    	  _.merge(params, {time:new Date()});
        return $.ajax({
          url: contextPath + url,
          type:'POST',
          data:params //JSON.stringify(params),
         /* processData: false,
          contentType: 'application/json'*/
        });
      }
  };
}();

var Util = {
  render: function (data, template, container, option) {
    var source = $(template).html();
    var tmpl = Handlebars.compile(source);
    if (option && !option.replace) {
    	return $(container).html(tmpl(data)).attr('data-rendered', true);
    }
    $(container).replaceWith(tmpl(data)).attr('data-rendered', true);
  },
  renderResult: function(data, template) {
	  var source = $(template).html();
	  var tmpl = Handlebars.compile(source);
	  return tmpl(data);
  },
  validateInput: function (type, input) {
	  type = type || 'number';
	  var reg1 = null, valid = null,reg2 = null;
	  switch (type) {
	  	case 'score':
	  		/*reg1 = /^[0-9]*\.([0-9]{1,2})?$/;
	  		reg2=/^[1-9][0-9]*$/;*/
	  		var reg=/^([1-9]\d*|[1-9]\d*\.\d{1,2}|0\.\d{1,2})$/;
	  	/*	if(reg1.test(input)||reg2.test(input)){
	  			return true;
	  		}else {
	  			return false;
	  		}*/
	  		if(reg.test(input)){
	  			return true;
	  		}else {
	  			return false;
	  		}
	  	case 'stu_score':
	  		var reg=/^(0|[1-9]\d*|[1-9]\d*\.\d{1,2}|0\.\d{1,2})$/;
	  		/*reg1 = /^[0-9]{1,}\.([0-9]{1,2})?$/;
	  		reg2=/^[0-9]*$/;*/
	  		if(reg.test(input)){
	  			return true;
	  		}else {
	  			return false;
	  		}	
	  	case 'num':
	  		reg1=/^([1-9]\d*|[0]{1,1})$/;
	  		if(reg1.test(input)){
	  			return true;
	  		}else {
	  			return false;
	  		}
	  	case 'negativeNum':
	  		var reg=/^(-?(0|[1-9]\d*|[1-9]\d*\.\d{1,2}|0\.\d{1,2}))$/;
	  		/*reg1 = /^-?([0-9]*\.([0-9]{1,2})?)$/;
	  		reg2=/^-?([0-9]*)$/;
	  		if(reg1.test(input)||reg2.test(input)){
	  			return true;
	  		}else {
	  			return false;
	  		}	*/
	  		if(reg.test(input)){
	  			return true;
	  		}else {
	  			return false;
	  		}
	  	case 'number':	  
	  }
  }
}



// 注册handlebars helper
Handlebars.registerHelper('indexPlusOne', function(value) {
  return value + 1;
});
Handlebars.registerHelper('toFixed', function(p1, p2) {
  return p1.toFixed(p2);
});
Handlebars.registerHelper('include', function(p1, p2, options)  {
  if (_.includes(p1, p2)) {
    return options.fn(this);
  }
  return options.inverse(this);
});
Handlebars.registerHelper('mod', function(p1, p2, options) {
  if ((p1 + 1) % p2 === 0) {
    return options.fn(this);
  }
  return options.inverse(this);
});
Handlebars.registerHelper('eq', function(p1, p2, options) {
  if (p1 === p2) {
    return options.fn(this);
  }
  return options.inverse(this);
});
Handlebars.registerHelper('gte', function(p1, p2, options) {
  if (p1 >= p2) {
    return options.fn(this);
  }
  return options.inverse(this);
});
Handlebars.registerHelper('gt', function(p1, p2, options) {
  if (p1 > p2) {
    return options.fn(this);
  }
  return options.inverse(this);
});
Handlebars.registerHelper('ne', function(p1, p2, options) {
	  if (p1 != p2) {
	    return options.fn(this);
	  }
	  return options.inverse(this);
	});
Handlebars.registerHelper('lt', function(p1, p2, options) {
  if (p1 < p2) {
    return options.fn(this);
  }
  return options.inverse(this);
});

// 扩展 jquery 代理事件
$.fn.delegates = function(cfg) {
  var el = this;
  for (var key in cfg) {
    var value = cfg[key];
    if (typeof value === 'function') {
      el.on('click', key, value);
    }
  }
  return this;
}

// 显示loading
// 用法： $('selector').showPreloader(text);
$.fn.showPreloader = function (text) {
  text = text || '正在加载内容';
  var tmpl = '<div class="preloader-wrap"><img src="data:image/gif;base64,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" alt="">' +
    '<div class="preloader-text">' + text + '</div></div>';
  return this.each(function () {
    if (!$(this).hasClass('placeloader')) {
      $(this).addClass('placeloader').append(tmpl);
    }
  })
}

// 关闭loading
// 用法： $('selector').hidePreloader();
$.fn.hidePreloader = function () {
  return this.each(function () {
    if ($(this).hasClass('placeloader')) {
      $(this).removeClass('placeloader').find('.preloader-wrap').remove();
    }
  })
}


