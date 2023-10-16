'use strict';
// Получаем токен CSRF из мета-тега
let token = document.querySelector('meta[name="_csrf"]').content;

// Создаем модуль AngularJS с именем "get_form"
angular.module("get_form", [])
    .controller("GetController", ["$scope", "$http", function ($scope, $http) {

        $scope.totalDishesCount = 0; // Инициализируем нулевым значением
        function getAllDishesCount() {
            $http({
                method: "GET",
                url: "/api/manager/dishes/get_count",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                }
            }).then(
                function (response) {
                    console.log(response.data);
                    $scope.totalDishesCount = response.data;
                },
                function (error) {
                    console.log(error);
                    console.log("error");
                }
            );
        }
        getAllDishesCount(); // Вызываем функцию сразу после инициализации контроллера

        $scope.totalCategoriesCount = 0; // Инициализируем нулевым значением
        function getAllCategoriesCount() {
            $http({
                method: "GET",
                url: "/api/categories/count",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                }
            }).then(
                function (response) {
                    console.log(response.data);
                    $scope.totalCategoriesCount = response.data;
                },
                function (error) {
                    console.log(error);
                    console.log("error");
                }
            );
        }
        getAllCategoriesCount(); // Вызываем функцию сразу после инициализации контроллера

        $scope.totalUsersCount = 0; // Инициализируем нулевым значением
        function getAllUsersCount() {
            $http({
                method: "GET",
                url: "/signup/count",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                }
            }).then(
                function (response) {
                    console.log(response.data);
                    $scope.totalUsersCount = response.data;
                },
                function (error) {
                    console.log(error);
                    console.log("error");
                }
            );
        }
        getAllUsersCount(); // Вызываем функцию сразу после инициализации контроллера

        $scope.ordersAverageAmount = 0; // Инициализируем нулевым значением
        function getAverageOrdersSum() {
            $http({
                method: "GET",
                url: "/api/orders/average",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                }
            }).then(
                function (response) {
                    console.log(response.data);
                    $scope.ordersAverageAmount = response.data;
                },
                function (error) {
                    console.log(error);
                    console.log("error");
                }
            );
        }
        getAverageOrdersSum(); // Вызываем функцию сразу после инициализации контроллера

        $scope.totalOrdersAmount = 0; // Инициализируем нулевым значением
        function getTotalOrdersSum() {
            $http({
                method: "GET",
                url: "/api/orders/get_total_sum",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                }
            }).then(
                function (response) {
                    console.log(response.data);
                    $scope.totalOrdersAmount = response.data;
                },
                function (error) {
                    console.log(error);
                    console.log("error");
                }
            );
        }
        getTotalOrdersSum(); // Вызываем функцию сразу после инициализации контроллера

        $scope.totalOrdersCount = 0; // Инициализируем нулевым значением
        function getTotalOrdersCount() {
            $http({
                method: "GET",
                url: "/api/orders/count",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                }
            }).then(
                function (response) {
                    console.log(response.data);
                    $scope.totalOrdersCount = response.data;
                },
                function (error) {
                    console.log(error);
                    console.log("error");
                }
            );
        }
        getTotalOrdersCount(); // Вызываем функцию сразу после инициализации контроллера

    }]);