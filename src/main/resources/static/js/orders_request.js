'use strict';
// Получаем токен CSRF из мета-тега
let token = document.querySelector('meta[name="_csrf"]').content;

// Создаем модуль AngularJS с именем "get_form"
angular.module("get_form", [])
    .controller("GetController", ["$scope", "$http", function ($scope, $http) {

        // Массив для хранения заказов
        $scope.orders = [];
        $scope.dishes = [];
        $scope.totalPrice = 0;

        // Функция для получения элементов
        $scope.getItems = function () {
            $http({
                method: "GET",
                url: "/api/orders/get",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                }
            }).then(
                function (data) {
                    console.log(data.data);
                    $scope.orders = data.data;
                    // Если список заказов пуст, выводим сообщение и скрываем блок
                    if ($scope.orders[0] == null) {
                        document.querySelector("#errorMsg").innerHTML = 'Order list is empty!';
                        document.querySelector("#dishes_block").className = 'hidden';
                    }
                },
                function (error) {
                    console.log(error);
                    console.log("error");
                }
            );
        }

        // Функция для получения всех элементов
        $scope.getAllItems = function () {
            // Отправляем GET запрос к /api/orders/getAll
            $http({
                method: "GET",
                url: "/api/orders/getAll",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                }
            }).then(
                function (data) {
                    // Выводим данные в консоль для отладки
                    console.log(data.data);
                    // Обновляем массив заказов в $scope с полученными данными
                    $scope.orders = data.data;
                },
                function (error) {
                    // Если произошла ошибка, выводим ее в консоль
                    console.log(error);
                    // Выводим сообщение об ошибке
                    console.log("error");
                }
            );
        }

        $scope.getItemsById = function (orderId) {
            $http({
                method: "GET",
                url: "/api/get_dishes/" + orderId,
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                }
            }).then(
                function (response) {
                    console.log(response.data);
                    $scope.dishes = response.data;
                    if ($scope.dishes.length === 0) {
                        document.querySelector("#errorMsg").innerHTML = 'Order is empty!';
                        document.querySelector("#dishes_block").className = 'hidden';
                    }
                },
                function (error) {
                    console.log(error);
                    console.log("error");
                }
            );
        }

        $scope.itemId = null;

        // Функция confirmAction принимает событие как аргумент
        $scope.confirmAction = function (event) {
            let id = event.currentTarget.getAttribute('id');
            console.log(id);
            // Создаем объект с id
            let object = { "itemId": id }
            console.log(object);
            // Отправляем PUT запрос к /api/orders/confirm с данными объекта
            $http({
                method: "PUT",
                url: "/api/orders/confirm",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                },
                data: id
            }).then(function (response) {
                // if (response.data) {
                // Если ответ успешный, перезагружаем страницу
                location.reload();
                // }
            }, function (response) {
                // Если возникла ошибка, вызываем функцию alertErrors и выводим код статуса в консоль
                alertErrors(response);
                console.log(response.status);
            });
        };
        // Функция showAction принимает событие как аргумент
        $scope.showOrder = function (event) {
            let id = event.currentTarget.getAttribute('id');
            console.log(id);
            // Создаем объект с id
            let object = { "itemId": id }
            console.log(object);
            // Отправляем GET запрос к /api/orders/get_dishes/{orderId} с данными объекта
            $http({
                method: "GET",
                url: "/api/orders/get_dishes/{orderId}",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                },
                data: id
            }).then(function (response) {
                // if (response.data) {
                // Если ответ успешный, перезагружаем страницу
                location.reload();
                // }
            }, function (response) {
                // Если возникла ошибка, вызываем функцию alertErrors и выводим код статуса в консоль
                alertErrors(response);
                console.log(response.status);
            });
        };

    }]);