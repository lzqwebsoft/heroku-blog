//得到文档对象
function getDocumentElement(doc) {
	doc = doc || document;
	return (doc.compatMode != "CSS1Compat") ? doc.body : doc.documentElement;
}

//判断浏览器
function browser() {
	var ua = navigator.userAgent.toLowerCase();
	return {
		VERSION: ua.match(/(msie|firefox|webkit|opera)[\/:\s](\d+)/) ? RegExp.$2 : '0',
		IE: (ua.indexOf('msie') > -1 && ua.indexOf('opera') == -1),
		GECKO: (ua.indexOf('gecko') > -1 && ua.indexOf('khtml') == -1),
		WEBKIT: (ua.indexOf('applewebkit') > -1),
		OPERA: (ua.indexOf('opera') > -1)
	};
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

//这样来得到窗体的初始代居中显示坐标
function getPos(width, height) {
	var widthMargin = 30;
	var heightMargin = 100;
	var width = width + widthMargin;
	var height = height + heightMargin;
	var x = 0, y = 0;
	var el = getDocumentElement();
	var scrollPos = getScrollPos();
	x = Math.round(scrollPos.x + (el.clientWidth - width) / 2);
	y = Math.round(scrollPos.y + (el.clientHeight - height) / 2);
	x = x < 0 ? 0 : x;
	y = y < 0 ? 0 : y;
	return {x : x, y : y};
}

var minTop, minLeft;   //窗体的滑动条的偏移位置
function setLimitNumber() {
	var docEl = getDocumentElement();
	var pos = getScrollPos();
	minTop = pos.y;
	minLeft = pos.x;
}

//得到鼠标的坐标
function getCoords(ev) {
	ev = ev || window.event;
	return {
		x : ev.clientX,
		y : ev.clientY
	};
}

//窗体拖动函数
function drag(mousedownObj, moveObj, func) {
	//当鼠标按下时调用
	mousedownObj.onmousedown = function(e) {
		var self = this;
		e = e || window.event;
		//得到鼠标的坐标
		var pos = getCoords(e);
		//得到窗体的当前坐标
		var objTop = parseInt(moveObj.style.top);
		var objLeft = parseInt(moveObj.style.left);
		//得到窗体的高与宽
		var objWidth = moveObj.style.width;
		var objHeight = moveObj.style.height;
		//处理当窗体高宽为百分比的情况
		if (objWidth.match(/%$/)) objWidth = moveObj.offsetWidth + 'px';
		if (objHeight.match(/%$/)) objHeight = moveObj.offsetHeight + 'px';
		objWidth = parseInt(objWidth);
		objHeight = parseInt(objHeight);
		
		var mouseTop = pos.y;
		var mouseLeft = pos.x;
		//窗体由滑动条产生的偏移量
		var scrollPos = getScrollPos();
		var scrollTop = scrollPos.y;
		var scrollLeft = scrollPos.x;
		var dragFlag = true;
		//鼠标移动时调用
		function moveListener(e) {
			if (dragFlag) {
				var pos = getCoords(e);
				var scrollPos = getScrollPos();
				var top = parseInt(pos.y - mouseTop - scrollTop + scrollPos.y);
				var left = parseInt(pos.x - mouseLeft - scrollLeft + scrollPos.x);
				func(objTop, objLeft, objWidth, objHeight, top, left);
			}
		}
		//鼠标释放时
		function upListener(e) {
			dragFlag = false;
			if (self.releaseCapture) self.releaseCapture();
			mousedownObj.onmousemove=null;
			mousedownObj.onmouseup=null;
			return false;
		}
		mousedownObj.onmousemove=function(e){moveListener(e);};
		mousedownObj.onmouseup=function(e){upListener(e);};
		if (self.setCapture) self.setCapture();
		return false;
	};
}

// 作用:创建一个按钮
function Button(arg) {
	arg = arg || {};
	doc = arg.doc || document;
	var span = doc.createElement('span');
	span.className = 'farme-button-common farme-button-outer ' + (arg.className || '');
	span.title = arg.text;
	btn = doc.createElement('input');
	btn.className = 'farme-button-common farme-button';
	btn.type = arg.type;
	btn.value = arg.text || '';
	if (arg.clickFn) {
		btn.onclick = function() {
			arg.clickFn(arg.parent);
		};
	}
	span.appendChild(btn);
	return {span : span, btn : btn};
}

// 创建一个窗体
function createFrame(arg) {
	var width=arg.width||500;
	var height=arg.height||300;
	var frame=document.createElement('div');
	frame.className="dialog_frame dialog_frame_shadow";
	frame.style.minWidth = width+"px";
	frame.style.minHeight= height+"px";
	frame.style.zIndex=arg.zIndex||999;
	// 设置窗体显示的坐标
	var pos = getPos(width, height);
	frame.style.top = pos.y + "px";
	frame.style.left = pos.x + "px";
	
	//创建一个窗体的标题div
	var titleDiv = document.createElement('div');
	titleDiv.className="dialog_frame_title";
	titleDiv.innerHTML = arg.title;
	
	//窗体上的中的关闭按钮
	var span = document.createElement('span');
	span.className = 'dialog_frame_close';
	span.alt = "关闭";
	span.title = "关闭";
	span.innerHTML="X";
	
	//关闭窗体
	hide = function() {
		document.body.removeChild(frame);
	};
	span.onclick = function () {
		//隐藏窗体
		self.hide();
	};
	
	titleDiv.appendChild(span);
	
	setLimitNumber();
	window.onresize=function(){setLimitNumber();};
	window.onscroll=function(){
		setLimitNumber();
		if(getScrollPos().y > 300)
	  	   document.getElementById("gototop").style.display="block";
		else
	 	   document.getElementById("gototop").style.display="none";
	};
	// 为标题添加拖动事件
	drag(titleDiv, frame, function(objTop, objLeft, objWidth, objHeight, top, left) {
		setLimitNumber();
		top = objTop + top;
		left = objLeft + left;
		if (top < minTop) top = minTop;
		if (left < minLeft) left = minLeft;
		frame.style.top = top + 'px';
		frame.style.left = left + 'px';
	});
	frame.appendChild(titleDiv);
	
	//窗体的主体面板
	var bodyDiv = document.createElement('div');
	bodyDiv.className = 'dialog_frame_body';
	frame.appendChild(bodyDiv);
	
	//窗体的下半按钮部分
	var bottomDiv = document.createElement('div');
	bottomDiv.className = 'dialog_frame_bottom';
	var noButton = null;
	var yesButton = null;
	
	//确定按钮
	if (arg.yesButton) {
		var btn = new Button({
			className : 'dialog_frame_yes',
			text : arg.yesButton||'Yes',
			type : "button",
			parent : frame,
			clickFn : arg.buttonClick
		});
		yesButton = btn.btn;
		bottomDiv.appendChild(btn.span);
	}
	//取消按钮
	if (arg.noButton) {
		var btn = new Button({
			className : 'dialog_frame_no',
			text : arg.noButton||"No",
			type : "button",
			clickFn : function() {
				self.hide();
			}
		});
		noButton = btn.btn;
		bottomDiv.appendChild(btn.span);
	}
	if(arg.yesButton||arg.noButton) {
		frame.appendChild(bottomDiv);
	}
	
	//将窗体加入到页面中
    document.body.appendChild(frame);
	window.focus();
	
	return bodyDiv;
}