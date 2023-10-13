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

        // Объект pageable для хранения данных страницы
        $scope.pageable = {};

        // Функция для получения данных
        $scope.getItems = function () {
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

        // Идентификатор элемента
        $scope.itemId = null;

        // Функция для отправки данных
        $scope.postdata = function(event) {
            let itemId = event.currentTarget.getAttribute('dishId');
            let object = { "itemId": itemId }
            console.log(object);
            $http({
                method: "POST",
                url: "/api/cart/create",
                headers: {
                    "Content-Type": "application/json",
                    'X-CSRF-TOKEN': token
                },
                data: JSON.stringify(object)
            }).then(function (response) {
                if (response.data) {
                    // alert("Блюдо " + itemId + " успешно добавлено.")
                }
            }, function (response) {
                alertErrors(response);
            });
        };
    }]);

let sorting = (field) => {
    direct = (direct === 'asc') ? 'desc' : 'asc';
    sort = field;
    replace();
}

let replace = () => {
    let str = '?page='+ page + '&sort=' + sort
        + '&direct=' + direct + "&category=" + category;
    location.replace(str);
}
