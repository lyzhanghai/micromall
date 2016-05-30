/**
 * Created by chenmingkang on 15/7/14.
 */
app.controller('shopCartCtr',["$scope","$rootScope","shopCartService","confirmFactory","messageFactory",function($scope,$rootScope,shopCartService,confirmFactory,messageFactory) {
    $scope.shopInfo = {
        total:0,
        selectLength:0,
        selectArray:[]
    };
    $scope.shopData = [];

    var itemLonNum = 0;   //改变了之后的数量
    var itemFocusNum = 0; //获取焦点之后的数量
    var upShopService = function(item){    //修改的service
        var upShopData ={
            buyNumber:item.buyNumber,
            goodsId:item.goodsId
        };
        shopCartService.cartUpNum(upShopData,function(data){
            $rootScope.loading = false;
        });
    };

    var isAllSelect = function(){      //是不是只有一个没选中
        if($scope.shopData.length == $scope.shopInfo.selectLength){
            $scope.allCheckSelect = true;
        }else{
            $scope.allCheckSelect = false;
        }
    };

    var itemTotalCount = function(item){   //求出商品多少钱
        item.itemTotal = parseFloat(item.itemCurPrice * item.buyNumber);
    };

    var totalCount = function(model,num){    //算出总价格
        if (model == 'add') {
            $scope.shopInfo.total = $scope.shopInfo.total + num;
        } else {
            $scope.shopInfo.total = $scope.shopInfo.total - num;
        }
    };

    var isSelectTotal = function(model,isSelect,num){  //如果有勾选的话 算出勾选的中价格
        if(isSelect){
            totalCount(model,num);
        }
    };


    $scope.upShopRedu = function(parItem,item){    //－－－－－－的方法
        if(item.buyNumber > 1){
            item.buyNumber--;
            isSelectTotal('less',item.checkSelect,item.price); //如果有勾选的话 算出勾选的中价格
            if(item.checkSelect){
                parItem.total -= parseFloat(item.price); //店铺的总价格
            }
            upShopService(item);
            itemTotalCount(item); //算出商品价格
        }
    };

    $scope.upShopPuls = function(parItem,item){      //＋＋＋＋的方法
        if(item.buyNumber < item.inventory){
            item.buyNumber++;
            isSelectTotal('add',item.checkSelect,item.price); //如果有勾选的话 算出勾选的中价格
            if(item.checkSelect){
                parItem.total += parseFloat(item.price); //店铺的总价格
            }
            upShopService(item);
            itemTotalCount(item); //算出商品价格
        }
    };

    $scope.upShopNum = function(parItem,item){    //num数量改变的方法
        var res = new RegExp(/^(\d)*$/);
        var itemCurPrice = parseInt(item.price);
        if(item.buyNumber > item.inventory){    //是否比存量大
            item.buyNumber = item.inventory;
        }

        if(!res.test(item.buyNumber)){  //是否是数字
            item.buyNumber = 1;
        }

        if(item.checkSelect){
            parItem.total = parseFloat(parItem.total - (itemLonNum * itemCurPrice) + (item.buyNumber * itemCurPrice));    //店铺的总价格
            $scope.shopInfo.total = $scope.shopInfo.total - (itemLonNum * itemCurPrice) + (item.buyNumber * itemCurPrice);   //用旧的减去 在加上新的
        }

        itemTotalCount(item); //算出商品价格

        itemLonNum = item.buyNumber;   //把个数存下来
    };

    $scope.upShopFocusNum = function(item){ //num数量移入促发的事件
        itemLonNum = itemFocusNum = item.buyNumber;    //吧个数存下来
    };

    $scope.upShopBlurNum = function(item){    //num数量移开促发的事件
        item.buyNumber = parseInt(item.buyNumber);
        if(itemFocusNum != item.buyNumber){
            if(!item.buyNumber){
                item.buyNumber = 1;
                itemTotalCount(item);
            }
            upShopService(item);
        }
    };

    $scope.checkSelect = function(parItem,item){  //每个商品全选选择
        if(item.checkSelect){
            $scope.shopInfo.selectLength++;
            totalCount('add',item.price);    //计算总价格
            $scope.shopInfo.selectArray.push(item.goodsId);
        }else{
            $scope.shopInfo.selectLength--;
            totalCount('less',item.price);   //计算总价格
            var itemIndex = angular.inArray(item.goodsId,$scope.shopInfo.selectArray);
            if(itemIndex > -1){
                $scope.shopInfo.selectArray.splice(itemIndex,1);
            }
        }
        isAllSelect();//判断是不是全部选中
    };


    $scope.allSelect = function(){
        $scope.shopInfo.selectArray = [];
        $scope.shopInfo.total = 0;
        angular.forEach($scope.shopData,function(item,i){   //如果他长度等于一 那就把它父节点给清除了
            if($scope.allCheckSelect){   //全部选中
                item.checkSelect = true;
                $scope.shopInfo.selectLength++;
                totalCount('add',item.price);
                $scope.shopInfo.selectArray.push(item.goodsId);
            }else{
                $scope.shopInfo.selectLength = 0;
                item.checkSelect = false;
            }
        });
    };


    $scope.deleteShop = function(item,index){
        confirmFactory({
            scope:$scope,
            text:'你确定从购物车删除该物品吗？',
            option:{
                go:function(){
                    shopCartService.cartDelete({
                        goodsId : item.goodsId
                    },function(data){
                        var itemIndex = angular.inArray(item.goodsId,$scope.shopInfo.selectArray);
                        if(itemIndex > -1){
                            $scope.shopInfo.selectArray.splice(itemIndex,1);
                            $scope.shopInfo.selectLength--;  //总的长度减1
                            isSelectTotal('less',item.checkSelect,item.itemTotal);
                        }
                        $scope.shopData.splice(index,1);
                        isAllSelect();
                        messageFactory({text : '删除成功'});
                    });
                }
            }
        });
    };

    $scope.deleteAll = function(){
        confirmFactory({
            text:'你确定从购物车删除这些物品吗？',
            option:{
                go:function(){
                    shopCartService.cartDeleteAll(function(data){
                        $scope.allCheckSelect = false;
                        $scope.shopInfo.total = 0;
                        $scope.shopInfo.selectArray = [];
                    });
                }
            }
        });
    };


    $scope.submit = function(){  //结算
        var  getData = {
            goodsIds : $scope.shopInfo.selectArray.join(','),
            cart : true
        };

        location.href = $rootScope.prefix + 'order/createOrder.html?' + angular.param(getData);
    };

    shopCartService.cartList(function(data){
        $scope.shopData = data.data;
    });

}]);
