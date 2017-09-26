var app = angular.module('app', ['ngAnimate', 'ngSanitize', 'ui.bootstrap']);
    
app.controller('recipectl', ['newRecipeService', '$scope', '$http', function(newRecipeService, $scope, $http) {
      
	$scope.ovenTempsList = [];
	$scope.servingUnitsList = [];
	$scope.prepTimeUnitsList = [];
	$scope.cookTimeUnitsList = [];
	$scope.servingUnitQualifier = "pl";
	$scope.prepTimeQualifier = "pl";
	$scope.cookTimeQualifier = "pl";
	$scope.recipe = {};
    $scope.recipe.instructions = [];
    $scope.recipe.ingredients=[];
    $scope.instructionForm = {};
    $scope.ingredientForm = {};
    $scope.ingredientDisplay = {}
    $scope.panelIsOpen = [true, false, false, true];
    $scope.fractionsList = [
    	{},
    	{text: "1/4", value: .25}, 
    	{text: "1/3", value: .33333}, 
    	{text: "1/2", value: .5},
    	{text: "2/3", value: .66667},
    	{text: "3/4", value: .75}];
    
    $scope.recipe.instructions.push({orderIndex: 1, text:"Instruction one"});
    $scope.recipe.instructions.push({orderIndex: 2, text:"Instruction two"});
    $scope.recipe.instructions.push({orderIndex: 3, text:"Instruction three"});
    $scope.recipe.ingredients.push({name: "ingredient1", quantityDisplay: "1 1/2", quantity: 1.5, quantityUnit: "cups"});
    $scope.recipe.ingredients.push({name: "ingredient2", quantityDisplay: "2", quantity: 2, quantityUnit: "tbsp"});
    $scope.recipe.ingredients.push({name: "ingredient3", quantityDisplay: "1/2", quantity: .5, quantityUnit: "tsp"});
    
	$scope.init = function(){
		$scope.fetchServingUnits();
		$scope.fetchPrepTimeUnits();
		$scope.fetchCookTimeUnits();
		$scope.fetchOvenTemps();
		dropdowns = document.getElementsByName("select");
		for (var i=0; i<dropdowns.length; i++){
			i.selectedIndex = 1; 
		}
		$scope.checkOpenPanels(0);
	};
	
    $scope.fetchOvenTemps = function(){
		if ($scope.ovenTempsList.length === 0){
			$scope.ovenTempsList.push("");
			var promise = newRecipeService.fetchOvenTemps();
			promise.then(function(data){
				temps = data.data.data;
				for (i=0; i < temps.length; i++){
					$scope.ovenTempsList.push(temps[i].value);
				}
			}, function(data) {
				alert("Failed to get oven temps");
			});
		}
	};
	
	$scope.fetchServingUnits = function(){
		if ($scope.servingUnitsList.length === 0){
			$scope.servingUnitsList.push("");
			var promise = newRecipeService.fetchMeasUnits($scope.servingUnitQualifier);
			promise.then(function(data){
				units = data.data.data;
				for (i=0; i < units.length; i++){
					$scope.servingUnitsList.push(units[i].value);
				}
			}, function(data) {
				alert("Failed to get serving units");
			});
			$scope.unitUpdateFlag = false;
		}
	};
	
	$scope.fetchCookTimeUnits = function(){
		if ($scope.cookTimeUnitsList.length === 0){
			$scope.cookTimeUnitsList.push("");
			var promise = newRecipeService.fetchTimeUnits($scope.cookTimeQualifier);
			promise.then(function(data){
				units = data.data.data;
				for (i=0; i < units.length; i++){
					$scope.cookTimeUnitsList.push(units[i].value);
				}
			}, function(data) {
				alert("Failed to get time units");
			});
			$scope.unitUpdateFlag = false;
		}
	};
	
	$scope.fetchPrepTimeUnits = function(){
		if ($scope.prepTimeUnitsList.length === 0){
			$scope.prepTimeUnitsList.push("");
			var promise = newRecipeService.fetchTimeUnits($scope.prepTimeQualifier);
			promise.then(function(data){
				units = data.data.data;
				for (i=0; i < units.length; i++){
					$scope.prepTimeUnitsList.push(units[i].value);
				}
			}, function(data) {
				alert("Failed to get time units");
			});
		}
	};
	
    $scope.submitNewRecipe = function(){
        var promise = newRecipeService.saveNewRecipe($scope.recipe);
        promise.then(function(response){
        	$scope.result = "Success!";
        }, function(){
        	$scope.result = "Error";
        });
    };
    
    $scope.updatePrepTimeUnits = function(){
    	index = document.getElementById("prepTimeUnit").selectedIndex;
    	if (angular.isDefined($scope.recipe.prepTime)){
    		$scope.unitUpdateFlag = true;
    		if ($scope.recipe.prepTime === 1 && $scope.prepTimeQualifier === "pl"){
    			$scope.prepTimeQualifier = "s";
    			$scope.prepTimeUnitsList = [];
    			$scope.fetchPrepTimeUnits();
    		}
    		else if ($scope.recipe.prepTime !== 1 && $scope.prepTimeQualifier === "s"){
    			$scope.prepTimeQualifier = "pl";
    			$scope.prepTimeUnitsList = [];
    			$scope.fetchPrepTimeUnits();
    		}
    	};
    	document.getElementById("prepTimeUnit").selectedIndex = index;
    };
    
    $scope.updateCookTimeUnits = function(){
    	index = document.getElementById("cookTimeUnit").selectedIndex;
    	if (angular.isDefined($scope.recipe.cookTime)){
    		$scope.unitUpdateFlag = true;
    		if ($scope.recipe.cookTime === 1 && $scope.cookTimeQualifier === "pl"){
    			$scope.cookTimeQualifier = "s";
    			$scope.cookTimeUnitsList = [];
    			$scope.fetchCookTimeUnits();
    		}
    		else if ($scope.recipe.cookTime !== 1 && $scope.cookTimeQualifier === "s"){
    			$scope.cookTimeQualifier = "pl";
    			$scope.cookTimeUnitsList = [];
    			$scope.fetchCookTimeUnits();
    		}
    	};
    	document.getElementById("cookTimeUnit").selectedIndex = index;
    };
    
    $scope.updateServingUnits = function(){
    	if (angular.isDefined($scope.recipe.servingSize)){
    		index = document.getElementById("servingSizeUnit").selectedIndex;
    		if ($scope.recipe.servingSize === 1 && $scope.servingUnitQualifier === "pl"){
    			$scope.servingUnitQualifier = "s";
    			$scope.servingUnitsList = [];
    			$scope.fetchServingUnits();
    		}
    		else if ($scope.recipe.servingSize !== 1 && $scope.servingUnitQualifier === "s"){
    			$scope.servingUnitQualifier = "pl";
    			$scope.servingUnitsList = [];
    			$scope.fetchServingUnits();
    		}
    	};
    	document.getElementById("servingSizeUnit").selectedIndex = index;
    };
    
    $scope.addInstruction = function(){
        if (!angular.isDefined($scope.recipe.instructions)) {
            $scope.recipe.instructions = [];
        }
        if (angular.isDefined($scope.instructionForm.text) && $scope.instructionForm.text !== ""){
                $scope.instruction = {orderIndex : $scope.recipe.instructions.length + 1, text : $scope.instructionForm.text};
                $scope.recipe.instructions.push($scope.instruction);
                $scope.instructionForm.text = null;
        }
        
    };
    
    $scope.removeInstruction = function(orderIndex){
        if (angular.isDefined($scope.recipe.instructions)) {
            for (var i=0; i<$scope.recipe.instructions.length; i++){
                if ($scope.recipe.instructions[i].orderIndex === orderIndex){
                    $scope.recipe.instructions.splice(i, 1);
                    for (var j=i; j<$scope.recipe.instructions.length; j++){
                        $scope.recipe.instructions[j].orderIndex--;
                    }
                    break;
                }
            }
        }
    };
    
    $scope.addIngredient = function(){
        if (!angular.isDefined($scope.recipe.ingredients)) {
            $scope.recipe.ingredients = [];
        }
        if (angular.isDefined($scope.ingredientForm.name) && $scope.ingredientForm.name !== ""){
            if (!angular.isDefined($scope.ingredientForm.fraction)){
                    $scope.ingredientForm.fraction = "";
                }
            if (!angular.isDefined($scope.ingredientForm.quantityUnit)){
                $scope.ingredientForm.quantityUnit = "";
            }
            var index = document.getElementById("ingFraction").selectedIndex;
            var fractionValue = Number($scope.fractionsList[index].value);
            if (!isNaN(fractionValue)){
            	var ingQty = parseInt($scope.ingredientForm.quantity) + fractionValue;
            }
            else {
            	ingQty = parseInt($scope.ingredientForm.quantity)
            }
            $scope.ingredient = {
                name: $scope.ingredientForm.name,
                quantity: ingQty,
                quantityUnit: $scope.ingredientForm.quantityUnit,
                quantityDisplay: $scope.ingredientForm.quantity + " " + $scope.ingredientForm.fraction
                };
            
            $scope.recipe.ingredients.push($scope.ingredient);
            document.getElementById("ingQty").value = null;
            document.getElementById("ingQtyUnit").selectedIndex = 0;
            document.getElementById("ingFraction").selectedIndex = 0;
            $scope.ingredientForm = {};
        }
    };
    
    $scope.removeIngredient = function(name){
        if (angular.isDefined($scope.recipe.ingredients)) {
            for (var i=0; i<$scope.recipe.ingredients.length; i++){
                if ($scope.recipe.ingredients[i].name === name){
                    $scope.recipe.ingredients.splice(i, 1);
                    break;
                }
            }
        }
    };
    
    $scope.checkOpenPanels = function(index){
    	if (index !== 3) {
	    	for (var i=0; i<$scope.panelIsOpen.length; i++){
	            if (index === i){
	                $scope.panelIsOpen[i] = true;
	            }
	            else {
	                $scope.panelIsOpen[i] = false;            
	            }
	        } 
    	}
    	$scope.panelIsOpen[3] = true;
    }
}]);

app.directive('elastic', [
    '$timeout',
    function($timeout) {
        return {
            restrict: 'A',
            link: function($scope, element) {
                $scope.initialHeight = $scope.initialHeight || element[0].style.height;
                var resize = function() {
                    element[0].style.height = $scope.initialHeight;
                    element[0].style.height = "" + element[0].scrollHeight + "px";
                };
                element.on("input change", resize);
                $timeout(resize, 0);
            }
        };
    }
]);
    
    

