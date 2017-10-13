/**
 * 
 */
var app = angular.module('app', ['ngAnimate', 'ngSanitize', 'ui.bootstrap']);
app.controller('pantryctl', ['pantryViewService', 'newRecipeService', '$scope', '$http', function(pantryViewService, newRecipeService, $scope, $http) {

	$scope.pantryIngredients = [];
	$scope.servingUnitsList = [];
	$scope.selectedIngredient = {};
	$scope.categoryIngredients= [];
	$scope.servingUnitQualifier = "pl";
	$scope.fractionsList = [
    	{},
    	{text: "1/4", value: .25}, 
    	{text: "1/3", value: .33333}, 
    	{text: "1/2", value: .5},
    	{text: "2/3", value: .66667},
    	{text: "3/4", value: .75}];
	$scope.qtyFlag = false;
	$scope.qtyFractFlag = false;
	$scope.qtyUnitFlag = false;
	
	$scope.getPantryIngredients = function(userId) {
		var promise = pantryViewService.getPantryIngredients(userId);
		promise.then(function(data){
			$scope.pantryIngredients = data.data.data;
			for (var i = 0; i < $scope.pantryIngredients.length; i++){
				$scope.pantryIngredients[i].image.imagePath = $scope.pantryIngredients[i].image.imagePath.replace("D:\\RecipeRex\\images\\", "/rr17/resources/images/ingredients/");
			}
			$scope.searchResults = angular.copy($scope.pantryIngredients);
			$scope.getPantryCategories(userId);
			
		}, function(data) {
			alert("Failed to get ingredients");
		});
	},
	$scope.getPantryCategories = function(userId) {
		var promise = pantryViewService.getPantryCategories(userId);
		promise.then(function(data){
			$scope.pantryCategories = data.data.data;
			$scope.setCategoryIngredients();
		}, function(data) {
			alert("Failed to get user pantry categories");
		});
	},
	$scope.setCategoryIngredients = function(){
		for(var i=0; i<$scope.pantryCategories.length; i++){
			$scope.categoryIngredients.push({name: $scope.pantryCategories[i].name, ingredients: []});
			for(var j=0; j<$scope.pantryIngredients.length; j++){
				for (var k=0; k<$scope.pantryIngredients[j].categories.length; k++){
					if (angular.equals($scope.pantryCategories[i].name, $scope.pantryIngredients[j].categories[k].name)){
						$scope.categoryIngredients[i].ingredients.push($scope.pantryIngredients[j]);
						break;
					}
				}
			}
		}
	},
	$scope.init = function(userId){
		$scope.getPantryIngredients(userId);
		$scope.selectedItem = {image: {text: "No item selected", imagePath: "/rr17/resources/images/ingredients/No_Item.jpg"}};
		$scope.fetchServingUnits();
	},
	$scope.setRowSize = function(index) {
			var element = document.getElementById("ingRow_" + $scope.categoryIngredients[index].name);
			var size = (($scope.categoryIngredients[index].ingredients.length * 152) + 12).toString();
			element.style.width = size + "px";
	},
	$scope.selectItem = function(item) {
		$scope.selectedItem = item;
		var qty = $scope.selectedItem.quantity.toString();
		var qtyArray = qty.split(".");
		var quantity = qtyArray[0];
		var fraction = $scope.getFraction("." + qtyArray[1]);
		var unit = $scope.selectedItem.quantityUnit;
		if (unit == null || unit == ""){
			unit = "...";
		}
		$scope.selectedIngredient = {
				name: $scope.selectedItem.name,
				description: $scope.selectedItem.description,
				quantity: quantity,
				fraction: fraction,
				quantityUnit: unit,
				categories: $scope.selectedItem.categories}
		
	},
	$scope.checkFlag = function(flag, value){
		if (flag || value == null || value == ""){
			return true;
		} else {
			return false;
		}
	},
	$scope.checkForQty = function(ingredient) {
		if (ingredient.quantity !== null && ingredient.quantity !== "") {
			return true;
		} else if (ingredient.fraction !== null && ingredient.fraction !== "") {
			return true;
		}
		return false;
	},
	$scope.checkForUnit = function(ingredient){
		if (ingredient.quantityUnit == null || ingredient.quantityUnit.trim() == ""){
			ingredient.quantityUnit = "...";
		}
	}
	$scope.getFraction = function(value){
		if (value == ".25"){
			return "1/4";
		} else if (value == ".33"){
			return "1/3";
		} else if (value == ".5"){
			return "1/2";
		} else if (value == ".66"){
			return "2/3";
		} else if (value == ".75"){
			return "3/4";
		} else return null;
	},
	$scope.fetchServingUnits = function(){
		if ($scope.servingUnitsList.length === 0){
			$scope.servingUnitsList.push("");
			var promise = newRecipeService.fetchMeasUnits($scope.servingUnitQualifier);
			promise.then(function(data){
				units = data.data.data;
				for (i=0; i < units.length; i++){
					$scope.servingUnitsList.push(units[i]);
				}
			}, function(data) {
				alert("Failed to get serving units");
			});
			$scope.unitUpdateFlag = false;
		}
	};
	$scope.updateServingUnits = function(index){
    	var refreshFlag = false;
    	unitElement = document.getElementById("ingQty" + index);
    	fractionElement = document.getElementById("ingQtyFraction" + index);
    	if (angular.isDefined(unitElement) && angular.isDefined(fractionElement)){
			if (fractionElement.selectedIndex === 0) {	
	    		if (parseInt(unitElement.value) === 1 && $scope.servingUnitQualifier === "pl"){
	    			$scope.servingUnitQualifier = "s";
	    			refreshFlag = true;
	    		}
	    		else if (parseInt(unitElement.value) !== 1 && $scope.servingUnitQualifier === "s"){
	    			$scope.servingUnitQualifier = "pl";
	    			refreshFlag = true;
	    		}
			}
			else {
				if ((!angular.isDefined(unitElement.value) || 
						parseInt(unitElement.value)  === 0 || 
						isNaN(parseInt(unitElement.value))) &&
						$scope.servingUnitQualifier === "pl"){
					$scope.servingUnitQualifier = "s";
					refreshFlag = true;
				}
				else if (parseInt(unitElement.value) !== 0 
						&& !isNaN(parseInt(unitElement.value)) 
						&& $scope.servingUnitQualifier === "s"){
	    			$scope.servingUnitQualifier = "pl";
	    			refreshFlag = true;
	    		}
			}
    	}
		if (refreshFlag){
    		$scope.servingUnitsList = [];
			$scope.fetchServingUnits();
			refreshFlag = false;
		}
    	
    };

}]);
