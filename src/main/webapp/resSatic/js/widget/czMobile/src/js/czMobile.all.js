/**
 * Created by chenmingkang on 16/3/1.
 *
 * 返回顶部
 */
;(function(){
    'use strict';
    angular.module('cz-back-top',[]).directive('backTop', ['$rootScope', '$window','throttleFactory',function($rootScope, $window,throttleFactory) {
        return {
            restrict : 'EA',
            link : function(scope, elm, attrs) {
                function winGoTopShow(){
                    if(window.scrollY > $rootScope.bodyInfo.height){
                        elm.addClass('back-top-show');
                    }else{
                        elm.removeClass('back-top-show');
                    }
                }

                var throttle = throttleFactory(winGoTopShow);   //节点流

                //scope.$on('$destroy', function(){
                //    elm.off();
                //    $win.off();
                //});
                elm.bind('touchstart',function(){
                    $window.scrollTo(0,0);
                }).bind('click',function(){
                    $window.scrollTo(0,0);
                });

                angular.element(window).on('scroll',throttle)
            }
        }
    }]);
}());

/**
 * Created by chenmingkang on 16/3/1.
 *
 * 滚动加载
 */
;(function(){
    'use strict';

    angular.module('cz-bottom-scroll',[]).directive('bottomScroll', ['$rootScope', '$window', '$timeout', function($rootScope, $window, $timeout) {
        var handler;
        return {
            link: function(scope, elem, attrs) {
                $window = angular.element($window);
                handler = function(evt) {
                    if (($window[0].scrollY + $window[0].innerHeight + 300) >= elem[0].offsetTop + elem[0].clientHeight) {
                        return scope.$eval(attrs.bottomScroll);
                    }
                };
                $window.on('scroll',handler);
                scope.$on('$destroy', function() {
                    return $window.off('scroll', handler);
                });
                return $timeout((function() {
                    return handler();
                }), 0);
            }
        };
    }]);
}());

/**
 * Created by chenmingkang on 16/3/1.
 */
;(function(){
    'use strict';
    angular.module('cz-cache',[]).factory('cacheFactory',['$cacheFactory','$rootScope','$window','$timeout', function($cacheFactory,$rootScope,$window,$timeout) {
        var myCache = function(){
            return $cacheFactory('myData');
        }();

        function setCache(datas){
            var scope =  datas.scope || $rootScope.$new();
            if($rootScope.cache){
                scope.$on('$destroy', function() {
                    //$timeout(function(){
                        if(scope.asyns){   //可能在加载数据的时候点下一页，数据还没返回 page还没＋＋，所以就加1
                            if(datas.data.page){
                                datas.data.page++;
                            }

                            if(datas.data.getData && datas.data.getData.page){
                                datas.data.getData.page++;
                            }
                        }
                        datas.data.scrollTop = window.scrollY;
                        myCache.put(datas.name,datas.data);
                    //},100)
                });
            }
        };

        function getCache(name){
            return myCache.get(name)
        };

        return {
            set : setCache,
            get : getCache,
            removeAll : myCache.removeAll
        }
    }])
}());

/**
 * Created by chenmingkang on 16/3/1.
 *
 * textTitle    title头部文本
 * text      文本
 * closeText  关闭文本
 * goText  确定文本
 */
;(function(){
    'use strict';
    angular.module('cz-confirm',[]).factory('confirmFactory',['$rootScope','$compile','$timeout',function($rootScope,$compile,$timeout){  //求出等比例的图片信息
        var getElmBg = function(){
            var getBg = getElm.length ? getElm[0] : document;
            return angular.element(getBg.getElementsByClassName('confirm-bg'));
        };

        var getElm = function(){
            return document.getElementsByClassName('confirm-lay');
        }();
        var $body = function(){
            return angular.element(document.getElementsByTagName('body'));
        }();

        var scope = $rootScope.$new();
        var $win = angular.element(window);
        var $elmBg;

        scope.confirm = {
            textTitle:'',
            text:'',
            closeText:'取消',
            goText:'确定',
            option:{
                go:function(){},
                close:function(){}
            }
        };

        var closeConfirm = function(){                      //清理
            $elmBg.off();
            $win.off('mousewheel');
            if(getElm[0]){
                angular.element(getElm[0]).remove();
            }
        };

        var setCss = function(){
            var getMain = getElm[0].getElementsByClassName('confirm-main')[0];
            getMain.style.marginTop = - (getMain.clientHeight / 2) + 'px';
            getMain = null;
        };

        function init(myData){
            angular.extend(scope.confirm,myData);
            var template = '<div class="confirm-lay confirm-lay-show">' +
                '<div class="confirm">' +
                '<div class="confirm-bg" id="confirm-bg" ng-click="confirmBgClose()"></div>' +
                '<div class="confirm-main">' +
                '<div class="confirm-text">'+
                '<div class="confirm-text-title" ng-bind-html="confirm.textTitle"></div>' +
                '<div class="confirm-text" ng-bind-html="confirm.text"></div>'+
                '</div>' +
                '<div class="confirm-btn">' +
                '<a href="javascript:;" ng-click="confirmClose()" class="confirm-btn-cancel">{{confirm.closeText}}</a>' +
                '<a href="javascript:;" ng-click="confirmGo()">{{confirm.goText}}</a>' +
                '</div>' +
                '</div>' +
                '</div>' +
                '</div>';
            $body.append($compile(template)(scope));
            $elmBg = getElmBg();
            $win.on('mousewheel',function(evt){
                evt.preventDefault();
                evt.stopPropagation();
                return false;
            });
            $elmBg.bind('touchmove', function(evt) {
                evt.preventDefault();
            });
            $timeout(function(){
                setCss();
            },0);
        };

        scope.confirmGo = function(){
            closeConfirm();
            if(!!scope.confirm.option.go){
                scope.confirm.option.go();
            }
        };
        scope.confirmBgClose = function(){
            closeConfirm();
        };
        scope.confirmClose = function(){
            closeConfirm();
            if(!!scope.confirm.option.close){
                scope.confirm.option.close();
            }
        };

        scope.$on('$destroy', function(){
            closeConfirm();
            getElm = $elmBg = null;
        });

        return function(myData){
            init(myData);
        }
    }]);
}());

/**
 * Created by chenmingkang on 16/3/1.
 *
 * czYieldToCallback  回调 暂时没用到   type = function
 * czYieldHideBeforeCallback   一开始的回调可议配置,比如说如果app里面就可以把它remove掉,不让他显示
 * Capture    工厂类,列表项
 *
 * czYieldTo      定义模版的指令
 * czYieldHide    需要隐藏的元素
 * czContentFor   添加模版的指令
 *
 */
;(function(){
    'use strict';

    var startUrl = '';
    angular.module('cz-content-for',[]).provider('czContentForConfig', function() {
        this.options = {
            czYieldToCallback : function(){},
            czYieldHideBeforeCallback : function(){}   //这个是
        };

        this.$get = function() {
            var options = this.options;
            return {
                getOptions: function() {
                    return options;
                }
            };
        };

        this.setOptions = function(options) {
            angular.extend(this.options, options);
        };
    }).run(['Capture','$rootScope',function(Capture, $rootScope) {
        $rootScope.$on('$stateChangeStart', function(evt, current) {
            if(startUrl.split('.')[0] != current.name.split('.')[0]){
                Capture.resetAll();
            }
            startUrl = current.name;
        });
    }
    ]).factory('Capture', ['$compile','czContentForConfig',function($compile,czContentForConfig) {
            var yielders = {};

            return {
                getConfigOption : czContentForConfig.getOptions(),

                resetAll: function() {
                    for (var name in yielders) {
                        this.resetYielder(name);
                    }
                },

                resetYielder: function(name) {
                    var b = yielders[name];
                    b.element.css('display','block');
                    this.setContentFor(name, b.defaultContent, b.defaultScope);
                },

                putYielder: function(name, element, defaultScope, defaultContent) {
                    var yielder = {};
                    yielder.name = name;
                    yielder.element = element;
                    yielder.defaultContent = defaultContent || '';
                    yielder.defaultScope = defaultScope;
                    yielders[name] = yielder;
                },

                getYielder: function(name) {
                    return yielders[name];
                },

                removeYielder: function(name) {
                    delete yielders[name];
                },

                hideYielder: function(name) {
                    var item = this.getYielder(name);
                    item.element.html('').css('display','none');
                },

                setContentFor: function(name, content, scope) {
                    var b = yielders[name];
                    if (!b) {
                        return;
                    }
                    b.element.html(content);
                    $compile(b.element.contents())(scope);
                }
            };
        }
        ])

        .directive('czContentFor', ['Capture',function(Capture) {
            return {
                compile: function(tElem, tAttrs) {
                    var rawContent = tElem.html();
                    if(tAttrs.czDuplicate === null || tAttrs.czDuplicate === undefined) {
                        // no need to compile anything!
                        tElem.html('');
                        tElem.remove();
                    }

                    return function(scope, elem, attrs) {
                        Capture.setContentFor(attrs.czContentFor, rawContent, scope);
                    };
                }
            };
        }
        ]).directive('czYieldTo', ['$compile', 'Capture', function($compile, Capture) {
        return {
            link: function(scope, element, attr) {
                Capture.putYielder(attr.czYieldTo, element, scope, element.html());

                //element.on('$destroy', function(){
                //    Capture.removeYielder(attr.czYieldTo);
                //});
                //
                //scope.$on('$destroy', function(){
                //    Capture.removeYielder(attr.czYieldTo);
                //});
            }
        };
    }
    ]).directive('czYieldHide', ['$compile', 'Capture', function($compile, Capture) {
        return {
            link: function(scope, element, attr) {
                Capture.getConfigOption.czYieldHideBeforeCallback(scope,element);
                Capture.hideYielder(attr.czYieldHide);
            }
        };
    }])
}());

/**
 * Created by chenmingkang on 16/3/1.
 */
;(function(){
    'use strict';

    angular.module('cz-cookie',[]).factory('cookieFactory',function(){
        return {
            setCookie : function(cname, cvalue, exdays,domain) {//设置cookie
                var d = new Date();
                d.setTime(d.getTime() + (exdays*24*60*60*1000));
                var expires = "expires="+d.toUTCString();
                document.cookie = cname + "=" + cvalue + "; path=/;domain="+ domain +";" + expires;
            },
            getCookie : function(cname) {//获取cookie
                var name = cname + "=";
                var ca = document.cookie.split(';');
                for(var i=0; i<ca.length; i++) {
                    var c = ca[i];
                    while (c.charAt(0)==' ') c = c.substring(1);
                    if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
                }
                return "";
            },
            clearCookie : function(name,domain) {//清除cookie
                this.setCookie(name, '', -1,domain);
            }

        };
    });
}());

/**
 * Created by chenmingkang on 16/3/1.
 */
;(function(){
    'use strict';
    angular.module('cz-countdown',[]).directive('countdown', ['$rootScope', '$window', '$timeout','$document',function($rootScope, $window, $timeout,$document) {
        return {
            restrict : 'EA',
            scope:{
                countdownData:'='
            },
            link : function(scope, elm, attrs) {
                var elmText = elm.attr('countdown-end') || '商品已下架';
                var elmTextStart = elm.attr('countdown-start') || '';
                var time;
                scope.$on('$destroy', function(){
                    clearInterval(time);
                });
                time = setInterval(function(){
                    if(!!scope.countdownData){
                        var date = new Date(new Date(scope.countdownData.replace(/-/g, '/')).getTime() - new Date().getTime());
                        var getDate = date / (60000*60) /24;
                        var getHours = (getDate - Math.floor(getDate)) * 24;
                        var getMinutes = (getHours - Math.floor(getHours)) * 60;
                        var getSeconds = Math.floor((getMinutes - Math.floor(getMinutes)) * 60);

                        var getDateText = getDate > 1 ? Math.floor(getDate) + '天' : '';
                        var getHoursText =getHours > 1 ?  Math.floor(getDate) + '时' : '';

                        if(getDate <= 0){
                            elm.html(elmText);
                            clearInterval(time);
                        }else{
                            elm.html(elmTextStart + getDateText + getHoursText + Math.floor(getMinutes) + '分' + getSeconds + '秒');
                        }
                    }
                },1000)
            }
        }
    }]);
}());

/**
 * Created by chenmingkang on 16/3/1.
 */

;(function() {
    'use strict';
    angular.module('cz-mobile', [
        'cz-bottom-scroll',      //滚动加载
        'cz-countdown',          //倒计时
        'cz-back-top',           //返回顶部
        'cz-form-validate',      //表单校验
        'cz-mask',               //dialog 之类弹层,添加背景上,禁用滚动条
        'cz-content-for',        //头部,只需要改变文字,或者隐藏.

        'cz-message',            //消息提示
        'cz-share',              //分享三端,微信,QQ,微博
        'cz-confirm',            //确定,取消弹出框
        'cz-cache',              //数据缓存-可以缓存类似service层的数据
        'cz-cookie',             //cookie
        'cz-util'            //节点流
    ]).run(['$rootScope',function($rootScope){
        $rootScope.bodyInfo = bodyInfo();

        function bodyInfo(){
            return{
                width : document.body.clientWidth < 750 ? document.body.clientWidth : 750,
                height : document.documentElement.clientHeight > 0 ? document.documentElement.clientHeight : window.innerHeight
            };
        };

    }]);

    angular.module('cz-util', [
        'cz-throttle'            //节点流
    ]);
}());
/**
 * Created by chenmingkang on 16/3/1.
 */
;(function(){
    'use strict';

    angular.module('cz-for-scroll',[]).factory('forScrollListFactory',function(){
        function List(data){
            this.listData = data;
        };

        List.prototype.getFirstData = function(){
            return this.listData[0];
        };

        List.prototype.getLastData = function(){
            return this.listData[this.listData.length];
        };

        List.prototype.getData = function(){
            return this.listData;
        };

        return function(data){
            return new List(data);
        }
    }).directive('forScroll',["$timeout","forScrollListFactory",function($timeout,forScrollListFactory) {
        return {
            restrict: 'EA',
            scope:{
                'forScrollData' : '=',
                'forScrollTargetData' : '='
            },
            link : function(scope, elm, attrs) {
                $timeout(function(){
                    var elm2Html = elm[0].outerHTML,
                        elm2,
                        elmParent = elm.parent();

                    //elm.parent().append(elm2Html);

                    //elm2 = elm[0].nextElementSibling;

                    //elm2.css({top:elmParent[0].offsetHeight});

                    //function marquee(){ // 若第一遍的内容已全部显示完毕，则重新开始显示
                    //    var top = elm2.attr('top');
                    //    elm2.css({
                    //        top: top + 1
                    //    });
                    //    if(top > elmParent[0].offsetHeight + elm2){
                    //
                    //    }
                    //}

                    function marquee(){ // 若第一遍的内容已全部显示完毕，则重新开始显示
                        if(elmParent[0].scrollHeight - (elmParent[0].scrollTop + elmParent[0].offsetHeight) <= 0) {
                            elmParent[0].scrollTop = 0;
                        }
                        else {
                            elmParent[0].scrollTop++;
                        } // 否则向上滚动1个像素的量。
                    }

                    var myMarquee = setInterval(marquee,50); // 按一定的速度滚动
                },0);

                //var time,
                //    position = attrs.forPosition || 'top',
                //    second = attrs.forScrollTime * 1000,
                //    listFn = forScrollListFactory(scope.forScrollTargetData);
                //
                //function reformData(){
                //    var shiftData,lastTargetData;
                //    if(position == 'top'){
                //        shiftData = scope.forScrollData.shift();
                //        lastTargetData = scope.forScrollTargetData.pop();
                //
                //        scope.forScrollTargetData.unshift(shiftData);
                //        scope.forScrollData.push(lastTargetData);
                //    }else{
                //        shiftData = scope.forScrollData.pop();
                //        lastTargetData = scope.forScrollTargetData.shift();
                //
                //        scope.forScrollTargetData.push(shiftData);
                //        scope.forScrollData.unshift(lastTargetData);
                //    }
                //    timeFn();
                //}
                //
                //function timeFn(){
                //    time = $timeout(function(){
                //        if(attrs.forScrollSuccess){
                //            scope.$parent.$eval(attrs.forScrollSuccess)(listFn.getFirstData(),reformData)
                //        }else{
                //            reformData();
                //        }
                //    },second);
                //};
                //
                //timeFn();
                //
                //scope.$on('$destroy',function(){
                //    $timeout.cancel(time);
                //});
            }
        }
    }]);
}());

/**
 * Created by chenmingkang on 16/3/1.
 *
 * 幕布,禁止滚动条滚动,适用于原生滚动条
 */
;(function(){
    'use strict';

    angular.module('cz-mask',[]).directive('mask',["$timeout",function($timeout) {
        return {
            restrict: 'EA',
            scope : {
                maskToggle : '='
            },
            link : function(scope, elm, attrs) {
                var $win = angular.element(window);
                var watchMask;
                $timeout(function(){
                    elm.bind('touchmove', function(evt) {
                        evt.preventDefault();
                    });

                    watchMask = scope.$watch('maskToggle',function(newVal,lodVal){
                        if(newVal){
                            $win.on('mousewheel',function(evt){
                                evt.preventDefault();
                                evt.stopPropagation();
                                return false;
                            });
                        }else{
                            $win.off('mousewheel');
                        }
                    });

                    scope.$on('$destroy',function(){
                        elm.off();
                        $win.off('mousewheel');
                        watchMask();
                    })
                },0);
            }
        }
    }]);
}());

/**
 * Created by chenmingkang on 16/3/1.
 */
;(function(){
    'use strict';
    angular.module('cz-message',[]).factory('messageFactory',['$rootScope','$timeout','$compile', function($rootScope,$timeout,$compile) {
        var time = time || undefined;
        var scope = $rootScope.$new();

        return function(o){
            $timeout(function(){
                var option = {
                    time : 3000,
                    text:''
                };
                angular.extend(option,o);
                var elm = (function(){
                    return document.getElementById('messageTop');
                }());
                scope.messageText = option.text;

                if(!elm){
                    // angular.element(document.body).append(html);
                    var html = '<div class="message-top" id="messageTop" ng-show="messageText">' +
                        '<div class="message-main-lay">'+
                        '<div class="message-main">'+
                        '<div class="message-text">{{messageText}}</div>'+
                        '</div>'+
                        '</div>'+
                        '</div>';
                    angular.element(document.body).append($compile(html)(scope))
                }

                $timeout.cancel(time);
                time = $timeout(function(){
                    elm = null;
                    scope.messageText = '';
                },option.time);
            },0)
        }
    }]);
}());

/**
 * Created by chenmingkang on 16/3/1.
 */
;(function(){
    'use strict';
    angular.module('cz-share',[]).factory('shareFactory', ['$rootScope','messageFactory','partnerUrlFactory',function($rootScope,messageFactory,partnerUrlFactory) {
        return function(option){
            var o = {
                title: '', // 分享标题
                desc: '原来国外这么便宜！100%正品保障，抢先下载还能领券哦！', // 分享描述
                link: location.href, // 分享链接
                imgUrl:  partnerUrlFactory.bizChannel ? 'http://m.zhefengle.cn/img/partner/logo.png' : 'http://m.zhefengle.cn/img/logo.png', // 分享图标
                type: '',
                dataUrl: '',
                success: function () {

                },
                cancel: function () {
                    messageFactory({text:'已取消分享'});
                    $scope.$apply();
                }
            };
            angular.extend(o,option);

            if($rootScope.browser.v.weixin){
                wx.ready(function(){
                    wx.onMenuShareAppMessage(o);
                    wx.onMenuShareTimeline(o);
                    wx.onMenuShareQQ(o);
                    wx.onMenuShareWeibo(o);
                });
            }
        };
    }]);
}());
/**
 * Created by chenmingkang on 16/3/1.
 */
;(function(){
    'use strict';
    angular.module('cz-throttle',[]).factory("throttleFactory",['$rootScope','$timeout', function($rootScope,$timeout) {   //节点流，滚动事件之类多次会导致性功能低下；
        return function(fn, threshhold, scope){
            threshhold || (threshhold = 250);
            var last,
                deferTimer;
            return function () {
                var context = scope || this;

                var now = +new Date,
                    args = arguments;
                if (last && now < last + threshhold) {
                    // hold on to it
                    $timeout.cancel(deferTimer);
                    deferTimer = $timeout(function () {
                        last = now;
                        fn.apply(context, args);
                    }, threshhold);
                } else {
                    last = now;
                    fn.apply(context, args);
                }
            };
        };
    }]);
}());
/**
 * Created by chenmingkang on 16/3/1.
 */
;(function(){
    'use strict';
// angular.validateAddMethod
//
    angular.module('cz-form-validate',[]).provider('formValidateConfig', function() {
        this.options = {
            errorClass      : '',
            errorMessage    : 'tip'
        };
        this.$get = function() {
            var options = this.options;
            return {
                getOptions: function() {
                    return angular.extend({},options);
                }
            };
        };

        this.setOptions = function(options) {
            angular.extend(this.options, options);
        };
    }).factory('validateFactory',['$compile','formValidateConfig',function($compile,formValidateConfig) {
        var rule = {};
        var opt = formValidateConfig.getOptions();
        function provide(scope,elm,attrs,controllers){
            var ngModelCtrl = controllers[0];
            function validate(){
                var formCtrl = controllers[1];
                var formName = formCtrl.form.attr("name");
                var elmName = attrs.name;
                /*转换前端传过来的校验规则*/

                if(opt.errorMessage === 'tip'){     //表示使用消息提示那种错误消息
                    formCtrl.formCrt.formValidateElmRule.push({
                        elmName : elmName,
                        rule : scope.validate
                    });
                }else{
                    for(var n in scope.validate){
                        ngModelCtrl.$setValidity(item, false);
                        elm.after($compile('<div class="error" ng-show="'+ formName +'.'+ elmName +'.$error.'+ n +' && '+ formName +'.formVaild">'+ scope.validate[n] +'</div>')(scope))
                    }

                    // var ruleRpe = rule.replace(/:/g,",");
                    // var ruleArray = ruleRpe.substr(1,rule.length-2).split(",");
                    // angular.forEach(ruleArray,function(item,index){
                    //     if(index % 2 == 0){
                    //         ngModelCtrl.$setValidity(item, false);
                    //         elm.after($compile('<div class="error" ng-show="'+ formName +'.'+ elmName +'.$error.'+ item +' && '+ formName +'.formVaild">'+ ruleArray[index + 1] +'</div>')(scope))
                    //     }
                    // });
                }
            }

            function matchRule(rules){   //负责匹配校验规则
                var valiDataType = attrs.validateType;/*校验数据类型*/

                for(var rulesName in rules){
                    if(rulesName === valiDataType){
                        injectRule(rulesName,rules[rulesName]);   //注入校验规则
                    }
                }

                if(attrs.equalTo){   //绑定两端
                    injectRule('equalTo',rules['equalTo']);   //注入校验规则
                    equalTo('equalTo',rules['equalTo']);  //注入两端的监听
                }
            }

            function validateItem(validateName,newVal,callback){   //负责校验每个单独元素规则
                var ngModelCtrl = controllers[0];
                var test = callback(newVal,elm);
                if(newVal){
                    ngModelCtrl.$setValidity(validateName, test);
                }else{
                    ngModelCtrl.$setValidity(validateName, true);   //默认为空不校验,让他默认显示空提示
                }
            }

            function injectRule(validateName,callback){   //注入校验规则
               scope.$parent.$watch(attrs.ngModel, function(newVal){
                    validateItem(validateName,newVal,callback);
                });
            }

            function equalTo(validateName,callback){  //如密码,确定密码,两端绑定
                var tarElm = angular.element(document.getElementById(attrs.equalTo));
                tarElm.on('keyup', function () {
                    scope.$apply(function(){
                        validateItem(validateName,elm.val(),callback)
                    })
                });

                scope.$on('$destroy',function(){
                    tarElm.off();
                });
            }

            return{
                build : function(rule){
                    validate();
                    matchRule(rule);
                }
            };
        }//validateName,callback

        function validateFns(scope,attrs,controllers){
            return {
                addRule : function(validateMethod){
                    for(var validateName in validateMethod){
                        rule[validateName] = validateMethod[validateName];
                    }
                },
                run : function(elm){
                    var provideFn = provide(scope,elm,attrs,controllers);
                    provideFn.build(rule);
                }
            }
        };

        return validateFns;
    }]).run(['validateFactory',function(validateFactory){
        validateFactory().addRule(angular.validateAddMethod);
    }]).directive('formSubmit',['messageFactory',function(messageFactory) {
        return {
            restrict : 'EA',
            require: ['^?formValidate'],
            scope : {
                formSubmit : '&'
            },
            controller: ['$element',"$attrs", function($element,$attrs) {
                this.formSubmit = $element;
            }],
            link: function (scope, elm, attrs,formController) {
                var form = formController[0].form;
                var formCrt = formController[0].formCrt;
                var formName = form.attr("name");

                elm.on('click',function(){
                    console.log(formCrt[formName].$valid)
                    if(formCrt[formName].$valid){
                        formCrt[formName].formVaild = false;
                        scope.formSubmit();
                    }else{
                        for(var i = 0;i < formCrt.formValidateElmRule.length;i++){
                            var item = formCrt.formValidateElmRule[i];
                            var isValidTrue = formCrt[formName][item.elmName].$valid;
                            if(!isValidTrue){
                                var errorNameObj = formCrt[formName][item.elmName].$error;
                                for(var n in errorNameObj){
                                    if(item.rule[n]){
                                        messageFactory({text : item.rule[n]})
                                    }
                                }
                                return;
                            }
                        }
                        formCrt[formName].formVaild = true;
                    }
                });

                scope.$on('$destroy', function(){
                    elm.off('click');
                });
            }
        }
    }]).directive('formValidate', function() {
        return {
            restrict: 'EA',
            require: ['^?formSubmit'],
            scope : true,
            controller: ['$scope','$element', "$attrs", function ($scope,$element, $attrs) {
                $scope.formValidateElmRule = [];
                this.formCrt = $scope;
                this.form = $element;
            }],
            link : function(scope, elm, attrs) {
                elm.attr('novalidate',true);
            }
        }
    }).directive('validate', ['$compile','$timeout','validateFactory',function($compile,$timeout,validateFactory) {
        return {
            restrict : 'EA',
            scope : {
                validateType : '@',
                validate : '='
            },
            require: ['ngModel','^?formValidate'],
            link : function(scope, elm, attrs,controllers) {
                 validateFactory(scope,attrs,controllers).run(elm);
            }
        }
    }])
}());

//var repeat = angular.element(document.getElementById(elm.attr("repeat")));
//repeat.on('keyup', function () {
//    repeatVal = this.value;
//    repeatFn();
//});
//scope.$watch(attrs.ngModel, function(newVal,lat){
//    tarVal = newVal || '';
//    repeatFn();
//});




/**
 * Created by chenmingkang on 16/3/11.
 *
 * 这里是校验公众的数据类型
 */

;(function(angular){
    angular.validateAddMethod = {
        phone : function(newVal){  //验证手机
            var res = new RegExp(/^1[3578][0-9]{9}$/);
            return newVal && res.test(newVal);
        },
        number : function(newVal){ //验证是否数字
            var res = new RegExp(/^[0-9]*$/);
            return newVal && res.test(newVal);
        },
        price : function(newVal){
            var res = new RegExp(/^\d+(\.\d+)?$/);
            return newVal && res.test(newVal);
        },
        priceUnZero : function(newVal){
            var res = new RegExp(/^\d+(\.\d+)?$/);
            return newVal && res.test(newVal) && newVal > 0;
        },
        equalTo : function(newVal,$elm){ //两端绑定
            var tarElm = function(){
                var id = $elm.attr('equal-to');
                return document.getElementById(id);
            }();
            return tarElm.value == newVal;
        },
        notChinese : function(newVal){
            var res = new RegExp(/[\u4e00-\u9fa5]+/);
            return newVal && !res.test(newVal);
        }
    };
})(angular);


//rule.phone = function(){
//    var res = new RegExp(/^1[3578][0-9]{9}$/);
//    scope.$watch(attrs.ngModel, function(newVal,lat){
//        if(!!newVal && newVal.length){
//            if(res.test(newVal)){
//                ngModelCtrl.$setValidity('phone', true);
//            }else{
//                ngModelCtrl.$setValidity('phone', false);
//            }
//        }else{
//            ngModelCtrl.$setValidity('phone', true);
//        }
//    });
//};
//rule.number = function(){
//    var res = new RegExp(/^[0-9]*$/);
//    scope.$watch(attrs.ngModel, function(newVal,lat){
//        if(!!newVal && newVal.length){
//            if(res.test(newVal)){
//                ngModelCtrl.$setValidity('number', true);
//            }else{
//                ngModelCtrl.$setValidity('number', false);
//            }
//        }else{
//            ngModelCtrl.$setValidity('number', true);
//        }
//    })
//},
//    rule.repeat = function(){
//        var repeat = angular.element(document.getElementById(elm.attr("repeat")));
//        var repeatVal = '';
//        var tarVal = '';
//        function repeatFn(){
//            if(tarVal.length > 0){
//                if(repeatVal == tarVal){
//                    ngModelCtrl.$setValidity('repeatMessage', true);
//                }else{
//                    ngModelCtrl.$setValidity('repeatMessage', false);
//                }
//            }else{
//                ngModelCtrl.$setValidity('repeatMessage', true);
//            }
//        };
//
//        repeat.on('keyup', function () {
//            repeatVal = this.value;
//            repeatFn();
//        });
//        scope.$watch(attrs.ngModel, function(newVal,lat){
//            tarVal = newVal || '';
//            repeatFn();
//        });
//    }