/**
 * 代码源自于： http://xuanfengge.com/demo/201404/share/
 * 参考文章：https://www.xuanfengge.com/social-platform-to-share-your-custom-javascript-components.html
 */
(function ($) {
    $.fn.snsShare = function (options) {
        var defaults = {
            tsina: { // 分享到新浪微博 
                url: encodeURIComponent(window.location.href),		//分享的url默认是当前页面
                title: document.title,								//分享的内容默认是当前页面的title
                appkey: 1343713053,								    //新浪微博的appkey
                pic: 'http://oiwjca89e.bkt.clouddn.com/2018071809395632f8fc7f63d803210b67328508b952'	// 默认图片
            },
            tqzone: { // 分享到QQ空间
                url: encodeURIComponent(window.location.href),		//分享的url默认是当前页面
                title: document.title,								//分享的内容默认是当前页面的title
                summary: '',
                pic: 'http://oiwjca89e.bkt.clouddn.com/2018071809395632f8fc7f63d803210b67328508b952'	// 默认图片
            },
            twitter: { // 分享到twitter
                url: encodeURIComponent(window.location.href),		//分享的url默认是当前页面
                title: document.title,								//分享的内容默认是当前页面的title
                pic: 'http://oiwjca89e.bkt.clouddn.com/2018071809395632f8fc7f63d803210b67328508b952'	// 默认图片
            },
            facebook: { // 分享到facebook
                url: encodeURIComponent(window.location.href),		//分享的url默认是当前页面
                title: document.title,								//分享的内容默认是当前页面的title
                pic: 'http://oiwjca89e.bkt.clouddn.com/2018071809395632f8fc7f63d803210b67328508b952'	// 默认图片
            }
        },
            settings = $.extend(true, {}, defaults, options),
            shareIconName = {
                tsina: '新浪微博',
                tqzone: 'QQ空间',
                twitter: 'Twitter',
                facebook: 'Facebook'
            },
            shareUrl = {
                tsina: 'http://service.weibo.com/share/share.php?url={url}&title={title}&appkey={appkey}&pic={pic}&searchPic=true',
                tqzone: 'http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url={url}&pics={pic}&title={title}&summary={summary}&desc={title}',
                twitter: 'https://twitter.com/intent/tweet?text={title} {url}&url={url}&pic={pic}',
                facebook: 'https://www.facebook.com/sharer/sharer.php?u={url}&t={title}&pic={pic}'
            };


        return $(this).each(function () {
            function openWindow(str) {
                return function () {
                    window.open(formatmodel(shareUrl[str], settings[str]));
                };
            }

            function formatmodel(str, model) {
                for (var k in model) {
                    var re = new RegExp("{" + k + "}", "g");
                    str = str.replace(re, model[k]);
                }
                return str;
            }

            for (snsName in settings) {
                var text = encodeURIComponent(settings[snsName].title);
                settings[snsName].title = text;
            }

            for (sns in shareUrl) {
                $(".share_" + sns).off().on('click', openWindow(sns)).attr("title", "分享到" + shareIconName[sns]).attr("href", "javascript:;");
            }

        });
    };
})(jQuery);