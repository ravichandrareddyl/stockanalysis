(function () {
    angular.module('dashboard.directives', ['utils', 'dashBoard.services'])
        .directive('countryWidget', function () {
            return {
                restrict: 'E',
                templateUrl: 'js/directives/countryWidget.html',
                scope: {
                    country: '=country',
                    shouldDisplayChecks: '=shouldDisplayChecks'
                },
                controller: function ($scope) {
                    var steps = [
                        { step: 0, name: 'Annual Closing Date' },
                        { step: 1, name: 'Rate Upload Status' },
                        { step: 2, name: 'Rate Upload Verification Status' },
                        { step: 3, name: 'FTTRAN Batch Status' },
                        { step: 4, name: 'BancsConnect Pending Txns' },
                        { step: 5, name: 'Treasury Suspense Balances' },
                        { step: 6, name: 'Proxy Balances' },
                        { step: 7, name: 'Unposted Transactions' },
                        { step: 8, name: 'ISO Reconciliation Status' },
                        { step: 9, name: 'CCA Booking Status' },
                        { step: 10, name: 'CAA Booking Status' },
                        { step: 11, name: 'SBA Booking Status' },
                        { step: 12, name: 'LAA Booking Status' },
                        { step: 13, name: 'TDA Booking Status' },
                        { step: 14, name: 'ODA Booking Status' },
                        { step: 15, name: 'Bills Booking Status' },
                        { step: 16, name: 'Unposted Transactions' },
                        { step: 17, name: 'Proxy Balances' },
                        { step: 18, name: 'Generate Annual Closing Reports' },
                        { step: 19, name: 'Wait till HO does rate upload and verifies' },
                        { step: 20, name: 'Transfer Foreign Inc/Exp to LCY' },
                        { step: 21, name: 'Wait till all branchs completes PNLUS' },
                        { step: 22, name: 'BBOTC' },
                        { step: 23, name: 'ISO Reconciliation Status' },
                        { step: 24, name: 'REVAL' },
                        { step: 25, name: 'Please Pass necessary TAX entries and Authorise' },
                        { step: 26, name: 'GL will be Updated by authorising this step' },
                        { step: 27, name: 'GL Update Status' },
                        { step: 28, name: 'MCBL and PnL Reports' },
                        { step: 29, name: 'All IBG and Local Reports Generated' },
                        { step: 30, name: 'BackUp of Tables for Reports' },
                        { step: 31, name: 'Cold Back UP Done' },
                        { step: 32, name: 'PnL Transfer to Current year PnL Account' },
                        { step: 33, name: 'GL will be Updated by authorising this step' },
                        { step: 34, name: 'GL Update Status' },
                        { step: 35, name: 'MCBL and PnL Reports' }
                    ];
    
                    $scope.steps = {};
                    steps.forEach(function(obj) {
                        $scope.steps[obj.step] =  obj.name;
                    });
                }
            }
        })
        .directive('anclsStepper', function () {
            return {
                restrict: 'E',
                templateUrl: 'js/directives/stepperDirective.html',
                scope: {
                    country: '=country',
                    steps: '=steps'
                }
            }
        })
        .directive('alertWidget', function () {
            return {
                restrict: 'E',
                templateUrl: 'js/directives/alertsWidget.html',
                scope: {
                    alerts: '=alerts'
                },
                controller: function ($scope) {
                    
                    $scope.alertCheck = function (itm) {
                        var name = itm.property;
                        var value = itm.propval;

                        switch (name) {
                            case 'cdciOperatingStatus':
                                return value == 'O' ? false : true;
                            case 'is01Holiday':
                                return value == '0' ? true : false;
                            case 'is31Holiday':
                                return value == '0' ? true : false;
                            case 'preHscodSetup':
                                return value == '0' ? true : false;
                            case 'preanBlockingFlg':
                                return value == 'N' ? true : false;
                            case 'preanNon999':
                                return value == '0' ? true : false;
                        }
                    }
                }


            }
        })
})();