(function () {
  angular.module('home', ['utils', 'ngAnimate', 'ngStorage', 'dashBoard.services'])
  .controller('home', function($scope, $location, $localStorage, statsService) {
    var self = this;
    self.aggregates = {};
    self.stocks = [];
    // aggregates = {};
    // self.countriesData = [];

    self.goTo = function () {
      //$location.path('/progress');
    }
      
    function activate () {
      statsService.getStockStats()
        .then(function (res) {
          self.aggregates = res.aggregates;
          self.stocks = res.stocks;
          //console.log('prinitng response' + JSON.stringify(res))
        })
        .catch(function (err) {
            console.error('printing error' + JSON.stringify(err));
        })
    }
     
    activate();
    });
})();