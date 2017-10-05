/**
 * 
 */
var app = angular.module('app', ['ngAnimate', 'ngSanitize', 'ui.bootstrap']);
app.controller('pantryctl', ['pantryViewService', '$scope', '$http', function(pantryViewService, $scope, $http) {

	$scope.pantryIngredients = [];
	
	
	$scope.getPantryIngredients = function(userId) {
		var promise = pantryViewService.getPantryIngredients(userId);
		promise.then(function(data){
			$scope.pantryIngredients = data.data.data;
			for (var i = 0; i < $scope.pantryIngredients.length; i++){
				$scope.pantryIngredients[i].image.imagePath = $scope.pantryIngredients[i].image.imagePath.replace("D:\\RecipeRex\\images\\", "/rr17/resources/images/ingredients/");
			}
			
		}, function(data) {
			alert("Failed to get oven temps");
		});
	},
	$scope.init = function(userId){
		$scope.getPantryIngredients(userId);
		$scope.selectedItem = {image: {text: "No item selected", imagePath: "/rr17/resources/images/ingredients/No_Item.jpg"}};
		
	},
	$scope.selectItem = function(index) {
		$scope.selectedItem = $scope.pantryIngredients[index];
	}

}]);
