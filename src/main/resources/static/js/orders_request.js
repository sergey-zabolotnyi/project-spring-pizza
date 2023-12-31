'use strict';
// Получаем токен CSRF из мета-тега
let token = document.querySelector('meta[name="_csrf"]').content;

// Создаем модуль AngularJS с именем "get_form"
angular.module("get_form", [])
    .controller("GetController", ["$scope", "$http", function ($scope, $http) {

        // Массив для хранения заказов
        $scope.orders = [];

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

        $scope.dishes = [];
        $scope.orderId = "";
        $scope.totalAmount = 0; // Переменная для хранения общей суммы заказа

        $scope.getOrderDetails = function(orderId) {
            let id;
            let urlParam = new URLSearchParams(window.location.search);
            if (urlParam.has('id')) {
                id = urlParam.get('id');
            }
            $http({
                method: "GET",
                url: "/api/orders/get_dishes?id=" + id,
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                }
            }).then(
                function(data) {
                    // Обновляем переменные в $scope с полученными данными
                    $scope.dishes = data.data;
                    $scope.orderId = id;
                    $scope.totalAmount = calculateTotalAmount(data.data);

                    // Можете использовать console.log для отладки
                    console.log($scope.dishes);
                    console.log($scope.orderId);
                    console.log($scope.totalAmount);
                },
                function (response) {
                    // Если возникла ошибка, вызываем функцию alertErrors и выводим код статуса в консоль
                    alertErrors(response);
                    console.log(response.status);
                });
        };

        // Функция для подсчета общей суммы заказа
        function calculateTotalAmount(dishes) {
            let total = 0;
            for (let i = 0; i < dishes.length; i++) {
                total += dishes[i].price;
            }
            return total;
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

    }]);