'use strict';

angular.module('jhApp')
    .controller('HomeController', function ($scope, Principal, LoginService) {
        Principal.identity().then(function(account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;
        });

        $scope.login = LoginService.open;

    });
