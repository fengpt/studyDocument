// // 判断当前浏览类型是否为谷歌浏览器，并得到其版本
// function getVersion()
// {
//   var userAgent = navigator.appName; //取得浏览器的userAgent字符串
//   var isChrome;
//     if( userAgent === "Netscape" ) isChrome = 1;
//       else isChrome = -1;      //判断是否为Chrome浏览器
//     if (isChrome) {
//       var b_version = navigator.appVersion; //取得浏览器的版本
//       var version = parseFloat(b_version);
//       }
//       else version = -1;
//   return version;
// }


//判断当前浏览类型是否为IE浏览器，并得到其版本
function getVersion(){
  var rv = -1;

  if (navigator.appName == 'Microsoft Internet Explorer'){

    var ua = navigator.userAgent,
      re  = new RegExp("MSIE ([0-9]{1,}[\\.0-9]{0,})");

    if (re.exec(ua) !== null){
      rv = parseFloat( RegExp.$1 );
    }
  }
  else if(navigator.appName == "Netscape"){
    /// in IE 11 the navigator.appVersion says 'trident'
    /// in Edge the navigator.appVersion does not say trident
    if(navigator.appVersion.indexOf('Trident') === -1) rv = 12;
    else rv = 11;
  }

  return rv;
}

//若版本不需要更新，则不提示； 若需要更新，则先设置cookie,之后提示用户更新
  try {
    document.addEventListener('DOMContentLoaded', $bu, false)
  } catch (err) {
    window.attachEvent('onload', $bu)
  }

  function $bu () {
    var ver = getVersion();
    if ( ver > -1 )
      if ( ver <= 9)  {
        var username = document.cookie.split(";")[0].split("=")[1];
        if (username !== 'zhy') {
          update();
        }
      }

  }

      // 提示用户更新
      function update() {
        var div = document.createElement("div");
        div.id = "remind";
        div.className = "remind";
        var updatePath = 'application/process/common/compat/update.html'
        var style = '<style>.remind {background: #FDF2AB no-repeat 18px center url(application/process/common/compat/img/icon/warning.png); transition:height 2s; }</style>';
        var t = '<b>您的网页浏览器已过期</b>。更新您的浏览器，以提高安全性和舒适性，并获得访问本网站的最佳体验。<a href="'+ updatePath +'" target="_blank" id="upd">更新浏览器</a> <a id="ignore">忽略</a>';
        style += "<style> .remind {background-position: 8px 14px; z-index:111111; width:100%; top:0px; left:0px; text-align:left; cursor:pointer; font: 16px Helvetica,Arial,sans-serif; box-shadow: 0 0 5px rgba(0,0,0,0.2);}  .remind div { padding: 5px 12px 5px 30px;  line-height: 2.0em; }    .remind div a,.remind div a:visited{   font-size: 16px; text-transform: uppercase;text-indent: 0; color: #fff;    text-decoration: none;    box-shadow: 0 0 2px rgba(0,0,0,0.4);    padding: 1px 10px;    border-radius: 2px;    font-weight: normal;    background: #5ab400;    white-space: nowrap;    margin: 0 2px; display: inline-block;}  #ignore { background-color: gray;} @media only screen and (max-width: 700px){.remind div { padding:5px 12px 5px 35px;line-height: 1.6em;}.remind {background-position: 8px 8px;}.remind div a {display:inline-block; margin:5px 2px 2px}}</style>";
        div.innerHTML = '<div>' + t + '</div>' + style;
        document.body.insertBefore(div, document.body.firstChild);

        //忽略的点击事件
          var ign = document.getElementById("ignore");
        ign.onclick = function () {
          // 点击忽略按钮之后，设置cookie
          document.cookie = 'name=zhy; expires=' + new Date(new Date().getTime() + 10 * 1000).toGMTString() + '; path=/';
          div.style.display = "none";
        }

          //更新浏览器的点击事件
          var upd = document.getElementById("upd");
          upd.onclick = function () {
            div.style.display = "none";
          }
      }

