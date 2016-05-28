/**
 * Created by chenmingkang on 16/3/8.
 */
if (browser.v.ios9) {
    app.config(['$provide', function($provide) {
        $provide.decorator('$browser', ['$delegate', function($delegate){
            var origUrl = $delegate.url;
            var pendingHref = null;
            var pendingHrefTimer = null;

            var newUrl = function (url, replace, state) {
                if (url) {
                    // setter
                    var result = origUrl(url, replace, state);

                    if (window.location.href == url || pendingHref == url) {
                        return;
                    }

                    pendingHref = url;

                    if (pendingHrefTimer){
                        clearTimeout(pendingHrefTimer);
                    }

                    pendingHrefTimer = setTimeout(function () {
                        if (window.location.href == pendingHref) {
                            pendingHref = null;
                        }
                        pendingHrefTimer = null;
                    }, 0);

                    return result;
                } else {
                    // getter
                    if (pendingHref == window.location.href) {
                        pendingHref = null;
                    }

                    return pendingHref || origUrl(url, replace, state);
                }
            };

            $delegate.url = newUrl;
            return $delegate;
        }]);
    }]);
}
