app.filter("dateDay", function() {
    return function(date){
        var currentDates = new Date().getTime() - new Date(date).getTime(),
            currentDay = parseInt(currentDates / (60000*60) -1) //减去1小时
        if(currentDay >= 24*3){
            var datas = new Date(date);
            currentDay = datas.getFullYear() + '-' + (datas.getMonth()+1) + '-' + datas.getDate();
        }else if(currentDay >= 24){
            currentDay = parseInt(currentDay / 24) + "天前";
        }else if(currentDay == 0 ){
            var currentD = parseInt(currentDates / 60000);
            if(currentD >= 60){
                currentDay = "1小时前"
            }else{
                currentDay = currentD + "分钟前"
            }
        }else{
            currentDay = currentDay + "小时前"
        }

        return currentDay
    };
}).filter("cut", function() {
    var i = 0;
    var text = '';
    var returns = true;
    return function(date,findIndex){
        angular.forEach(date,function(item,index){
            if(returns){
                if(item === ';'){
                    if(i === findIndex){
                        returns = false;
                    }else{
                        i++;
                    }
                    return;
                }
                if(i >= findIndex){
                    text += item;
                }
            }
        });
        return text;
    };
}).filter("encodeUrl", function() {
    return function(data){
        return encodeURIComponent(data)
    };
}).filter("toFixed", function() {
    return function(data){
        if(!!data){
            return data.toFixed(2);
        }else{
            return data;
        }
    };
}).filter('to_trusted', ['$sce', function ($sce) {
    return function (text) {
        return $sce.trustAsHtml(text);
    };
}])

//app.filter("privateFixed",['$timeout', function($timeout) {
//    var reg = /@[\s\S]+?\[[0-9]+?\]/g;
//    return function(data){
//        var content = '';
//        if(data){
//            var ask = data.match(reg);   //匹配出@人的数组
//            var contents = (data.replace(reg,'||||')).split('||||');//匹配出@人的内容，并且转换成数组
//
//            if(ask){
//                if(!contents[0]){
//                    contents.splice(0,1); //去掉第一个空数组
//                }
//                angular.forEach(contents,function(childContent,childIndex){
//                    var askItem = ask[childIndex];
//                    var askRegIndex = askItem.indexOf('[');
//                    var askName = askItem.substr(0,askRegIndex);
//                    var askId = askItem.substr(askRegIndex + 1,askItem.length - askRegIndex - 2);
//                    content += '<a href='+ askId +'>'+ askName +'</a>'+ childContent +'';
//                });
//            }else{
//                content = contents[0];
//            }
//            return content;
//        }
//    };
//}]);


