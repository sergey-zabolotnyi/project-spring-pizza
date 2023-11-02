'use strict';

let token = document.querySelector('meta[name="_csrf"]').content;
let sort = 'id';
let direct = 'asc';
let page = 1;
let category = 0;

let queryString = window.location.search;
console.log(queryString);
let urlParams = new URLSearchParams(queryString);

if (urlParams.has('page')) {
    page = urlParams.get('page');
    sort = urlParams.get('sort');
    direct = urlParams.get('direct');
    category = urlParams.get('category');
}

angular.module("get_form", [])
    .controller("GetController", ["$scope", "$http", function ($scope, $http) {
        $scope.pageable = {};
        $scope.getAllItems = function () {
            $http({
                method: "GET",
                url: "/api/get/" + page + '?sort=' + sort
                    + "&direct=" + direct + "&category=" + category
                ,
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                }
            }).then(
                function (data) {
                    console.log(data.data);

                    // Присваивание данных к $scope для использования в представлении
                    $scope.dishes = data.data.dishes;
                    $scope.categories = data.data.categories;
                    $scope.pageable.page = data.data.currentPage;
                    $scope.pageable.totalPages = data.data.totalPages;
                    $scope.pageable.sortField = data.data.sortField;
                    $scope.pageable.sortDirection = data.data.sortDirection;
                    $scope.pageable.categoryId = data.data.categoryId;
                },
                function (error) {
                    console.log(error);
                    // alert(error.data.message);
                    alert("Incorrect url parameters, reload main page.");
                }
            );
        }

        $scope.categories = [];
        $scope.getCategories = function () {
            $http({
                method: "GET",
                url: "/api/categories/get",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                }
            }).then(
                function (data) {
                    console.log(data.data);
                    $scope.categories = data.data;
                },
                function (error) {
                    console.log(error);
                    console.log("error");
                }
            );
        };

        $scope.getDish = function() {
            let id;
            let urlParam = new URLSearchParams(window.location.search);
            if (urlParam.has('id')) {
                id = urlParam.get('id');
            }
            $http({
                method: "GET",
                url: "/api/manager/dishes/update?id=" + id,
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                }
            }).then(
                function (data) {
                    console.log(data.data);
                    $scope.dish = data.data;
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
                'nameEn': document.querySelector('#name_en').value,
                'nameRu': document.querySelector('#name_ru').value,
                'price': Number.parseFloat(document.querySelector('#price').value),
                'category': {
                    'id': document.querySelector('#category').value
                }
            }
            $http({
                method: "POST",
                url: "/api/manager/dishes/create",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                },
                data: JSON.stringify(object)
            }).then(function (response) {
                if (response.data) {
                    location.replace('/manager/manage_dishes');
                }
            }, function (response) {
                alertErrors(response);
            });
        };

        $scope.update = function () {
            let object = {
                'id': document.querySelector('#updateId').value,
                'nameEn': document.querySelector('#name_en').value,
                'nameRu': document.querySelector('#name_ru').value,
                'price': document.querySelector('#price').value,
                'category': JSON.parse(document.querySelector('#updateCategories').value)
            }
            console.log(object);
            $http({
                method: "PUT",
                url: "/api/manager/dishes/update",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                },
                data: JSON.stringify(object)
            }).then(function (response) {
                // if (response.data) {
                location.replace('/manager/manage_dishes');
                // }
            }, function (response) {
                alertErrors(response);
            });
        };

        $scope.delete = function (event) {
            let id = event.currentTarget.getAttribute('dishId');
            console.log(id);
            let object = { "id": id }
            console.log(object);
            $http({
                method: "DELETE",
                url: "/api/manager/dishes/delete",
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
        $scope.totalDishesCount = 0; // Initialize to 0
        $scope.getAllDishesCount = function() {
            $http({
                method: "GET",
                url: "api/manager/dishes/get_all/count",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                }
            }).then(
                function (response) {
                    $scope.totalDishesCount = response.data;
                },
                function (error) {
                    console.log(error);
                }
            );
        };
    }]);
