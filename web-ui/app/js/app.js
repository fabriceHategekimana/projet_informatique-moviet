/**
 * App main js file
 */
var movietApp = angular.module('movietApp', ["ngRoute"]);

movietApp.config(['$routeProvider', function ($routeProvider) {
    $routeProvider
    // home:
    .when("/", {templateUrl: "app/partials/home.html", controller: "home"})
    // to add:
    // .when("/login", {
    //   templateUrl : "login.htm"
    // })
    // error 404:
    .when("/404", {templateUrl: "app/partials/404.html"})
    .otherwise("/404", {templateUrl: "app/partials/404.html"});
}]);

// example for the controllers:
// movietApp.controller('login', ['$scope', function($scope){
    
// }])