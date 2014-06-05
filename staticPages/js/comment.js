//得到文档对象
function getDocumentElement(doc) {
	doc = doc || document;
	return (doc.compatMode != "CSS1Compat") ? doc.body : doc.documentElement;
}

//判断浏览器
function browser() {
	var ua = navigator.userAgent.toLowerCase();
	return {
		VERSION: ua.match(/(msie|rv|firefox|webkit|opera)[\/:\s](\d+)/) ? RegExp.$2 : '0',
		IE: ((ua.indexOf('msie') > -1 && ua.indexOf('opera') == -1) || ua.match(/rv:([\d.]+)\) like gecko/)),
		GECKO: (ua.indexOf('gecko') > -1 && ua.indexOf('khtml') == -1),
		WEBKIT: (ua.indexOf('applewebkit') > -1),
		OPERA: (ua.indexOf('opera') > -1)
	};
}

var minTop, minLeft;   //窗体的滑动条的偏移位置
function setLimitNumber() {
	var docEl = getDocumentElement();
	var pos = getScrollPos();
	minTop = pos.y;
	minLeft = pos.x;
}

//得到滑动条滑动的x和y的偏移量
function getScrollPos() {
	var x, y;
	if (browser().IE || browser().OPERA) {
		var el = this.getDocumentElement();
		x = el.scrollLeft;
		y = el.scrollTop;
	} else {
		x = window.scrollX || 0; 
		y = window.scrollY || 0;
	}
	return {x : x, y : y};
}
$(function(){
	$(window).scroll(function(){
		setLimitNumber();
		if(getScrollPos().y > 300)
	  	   document.getElementById("gototop").style.display="block";
		else
	 	   document.getElementById("gototop").style.display="none";
    });
});