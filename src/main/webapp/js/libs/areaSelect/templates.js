angular.module("__chinaAreaSelectorTemplates__", []).run(["$templateCache", function($templateCache) {$templateCache.put("china-area-selector-with-wrapper.html","<div class=\"china-area-selector-wrapper\">\n    <span>省</span>\n    <select name=\"province\" ng-options=\"province for province in provinces\" ng-model=\"region.province\">\n        <option value=\"\">-- 选择省 --</option>\n    </select>\n    <span class=\"china-area-selector-icon\">\n                            <span>\n\n                            </span>\n    </span>\n</div>\n\n<div class=\"china-area-selector-wrapper\">\n    <span>市</span>\n    <select name=\"city\" ng-options=\"city for city in citys\" ng-model=\"region.city\">\n        <option value=\"\">-- 选择市 --</option>\n    </select>\n   <span class=\"china-area-selector-icon\">\n                            <span>\n\n                            </span>\n   </span>\n</div>\n\n<div class=\"china-area-selector-wrapper\">\n    <span>县</span>\n    <select name=\"area\" ng-options=\"area for area in areas\" ng-model=\"region.area\">\n        <option value=\"\">-- 选择市/区/县 --</option>\n    </select>\n   <span class=\"china-area-selector-icon\">\n                            <span>\n\n                            </span>\n   </span>\n</div>\n\n\n\n\n");
$templateCache.put("china-area-selector.html","\n<select name=\"province\" ng-options=\"province for province in provinces\" ng-model=\"region.province\">\n    <option value=\"\">-- 选择省 --</option>\n</select>\n<select name=\"city\" ng-options=\"city for city in citys\" ng-model=\"region.city\">\n    <option value=\"\">-- 选择市 --</option>\n</select>\n<select name=\"area\" ng-options=\"area for area in areas\" ng-model=\"region.area\">\n    <option value=\"\">-- 选择市/区/县 --</option>\n</select>\n");}]);