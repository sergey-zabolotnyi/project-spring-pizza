'use strict';
// Получаем токен CSRF из мета-тега
let token = document.querySelector('meta[name="_csrf"]').content;

// Создаем модуль AngularJS с именем "get_form"
angular.module("get_form", [])
    .controller("GetController", ["$scope", "$http", function ($scope, $http) {

        // Функция для получения товаров из корзины
        $scope.getItems = function () {
            $http({
                method: "GET",
                url: "/api/cart/get",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                }
            }).then(
                function (data) {
                    console.log(data.data);
                    // Обновляем информацию о блюдах и общей стоимости в $scope
                    $scope.dishes = data.data.dishes;
                    $scope.totalPrice = data.data.totalPrice;
                    // Если корзина пуста, выводим сообщение и скрываем блок
                    if ($scope.dishes[0] == null) {
                        document.querySelector("#errorMsg").innerHTML = 'Cart is empty!';
                        document.querySelector("#dishes_block").className = 'hidden';
                    }
                },
                function (error) {
                    console.log(error);
                    console.log("error");
                }
            );
        }

        // Функция для создания заказа
        $scope.postdata = function () {
            $http({
                method: "POST",
                url: "/api/orders/create",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                }
            }).then(function (response) {
                if (response.data) {
                    location.replace('/orders');
                }
            }, function (response) {
                alertErrors(response);
            });
        };

        // Функция для удаления товара из корзины
        $scope.delete = function (event) {
            let id = event.currentTarget.getAttribute('id');
            console.log(id);
            let object = { "itemId": id }
            console.log(object);
            $http({
                method: "DELETE",
                url: "/api/cart/delete",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                },
                data: JSON.stringify(object)
            }).then(function (response) {
                console.log(response);
                window.location.reload();
            }, function (response) {
                alertErrors(response);
            });
        };

        // Функция для удаления всех товаров из корзины
        $scope.deleteAll = function () {
            $http({
                method: "DELETE",
                url: "/api/cart/deleteAll",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                }
            }).then(function (response) {
                console.log(response);
                window.location.reload();
            }, function (response) {
                alertErrors(response);
            });
        };

    }]);
