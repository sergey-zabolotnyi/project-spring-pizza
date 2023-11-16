'use strict';
// Получаем токен CSRF из мета-тега
let token = document.querySelector('meta[name="_csrf"]').content;


// Создаем модуль AngularJS с именем "get_form"
angular.module("get_form", [])
    .controller("GetController", ["$scope", "$http", function ($scope, $http) {

        // Массив для хранения заказов
        $scope.reviews = [];

        // Функция для получения всех элементов
        $scope.getAllItems = function () {
            // Отправляем GET запрос к /api/orders/getAll
            $http({
                method: "GET",
                url: "/api/reviews/getAll",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                }
            }).then(
                function (data) {
                    // Выводим данные в консоль для отладки
                    console.log(data.data);
                    // Обновляем массив заказов в $scope с полученными данными
                    $scope.reviews = data.data;
                },
                function (error) {
                    // Если произошла ошибка, выводим ее в консоль
                    console.log(error);
                    // Выводим сообщение об ошибке
                    console.log("error");
                }
            );
        };

        $scope.users = [];
        $scope.getUsers = function () {
            $http({
                method: "GET",
                url: "/signup/get",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                }
            }).then(
                function (data) {
                    console.log(data.data);
                    $scope.users = data.data;
                },
                function (error) {
                    console.log(error);
                    console.log("error");
                }
            );
        };

        $scope.create = function () {
            let object = {
                'id': null,
                'user': {
                    'id': document.querySelector('#user').value
                },
                'text': document.querySelector('#text').value,
                'rating': parseInt(document.querySelector('#rating').value, 10)
            }
            $http({
                method: "POST",
                url: "/api/reviews/create",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                },
                data: JSON.stringify(object)
            }).then(function (response) {
                if (response.data) {
                    location.replace('/reviews');
                }
            }, function (response) {
                alertErrors(response);
            });
        };

        $scope.getReview = function() {
            let id;
            let urlParam = new URLSearchParams(window.location.search);
            if (urlParam.has('id')) {
                id = urlParam.get('id');
            }
            $http({
                method: "GET",
                url: "/api/reviews/update?id=" + id,
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                }
            }).then(
                function (data) {
                    console.log(data.data);
                    $scope.review = data.data;
                },
                function (error) {
                    console.log(error);
                    console.log("error");
                }
            );
        };

        $scope.update = function () {
            let object = {
                'id': document.querySelector('#updateId').value,
                'user': JSON.parse(document.querySelector('#updateUser').value),
                'text': document.querySelector('#text').value,
                'rating': document.querySelector('#rating').value
            }
            console.log(object);
            $http({
                method: "PUT",
                url: "/api/reviews/update",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                },
                data: JSON.stringify(object)
            }).then(function (response) {
                // if (response.data) {
                location.replace('/reviews');
                // }
            }, function (response) {
                alertErrors(response);
            });
        };

        $scope.delete = function (event) {
            let id = event.currentTarget.getAttribute('reviewId');
            console.log(id);
            let object = { "id": id }
            console.log(object);
            $http({
                method: "DELETE",
                url: "/api/reviews/delete",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                },
                data: id
            }).then(function (response) {
                window.location.reload();
            }, function (response) {
                alertErrors(response);
            });
        };

    }]);