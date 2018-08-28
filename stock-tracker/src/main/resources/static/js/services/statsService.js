(function () {
    angular.module('dashBoard.services', ['ngStorage'])
        .factory('statsService', function ($http, $q, $localStorage) {
            var self = {};

            function getStatus() {
                return $http.post("/getStatus");
            }

            function getStockStats () {
                return getStatus()
                    .then(function(res) {
                        var stocks = res.data;
                        var completed = [];
                        var yetToBeTracked = [];
                        var tracking = [];

                        stocks.forEach(function (stock) {
                            if (stock.trackingStatus === 'COMPLETED') {
                                completed.push(stock);
                            } else if (stock.trackingStatus === 'TRACKING') {
                                tracking.push(stock);
                            } else {
                                yetToBeTracked.push(stock);
                            }
                        });

                        return {
                            aggregates : {
                                completed: completed,
                                tracking: tracking,
                                yetToBeTracked: yetToBeTracked
                            },
                            stocks: stocks
                        };
                    })
            }

            return {
                getStockStats: getStockStats
            };
        });
})();