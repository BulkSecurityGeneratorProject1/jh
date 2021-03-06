'use strict';

describe('Controller Tests', function () {

    beforeEach(module('jhApp'));

    describe('LoginController', function () {
        var $scope, authService;

        beforeEach(inject(function ($rootScope, $controller, Auth) {
            $scope = $rootScope.$new();
            authService = Auth;
            $controller('LoginController',
                {
                    $scope: $scope,
                    Auth: authService,
                    $uibModalInstance: null
                });
        }));

        it('should set remember Me', function () {
            expect($scope.rememberMe).toBeTruthy();
        });
    });
});
