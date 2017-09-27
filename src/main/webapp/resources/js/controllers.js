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
	$scope.ingredientGroups = [];
    $scope.recipe.instructions = [];
    $scope.recipe.ingredients={};
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
    
    $scope.ingredientGroups.push({name: "Ingredients List", ingredients:[], index: 0});
    $scope.recipe.instructions.push({orderIndex: 1, text:"Instruction one"});
    $scope.recipe.instructions.push({orderIndex: 2, text:"Instruction two"});
    $scope.recipe.instructions.push({orderIndex: 3, text:"Instruction three"});
    $scope.ingredientGroups[0].ingredients.push({name: "ingredient1", quantityDisplay: "1 1/2", quantity: 1.5, quantityUnit: "cups"});
    $scope.ingredientGroups[0].ingredients.push({name: "ingredient2", quantityDisplay: "2", quantity: 2, quantityUnit: "tbsp"});
    $scope.ingredientGroups[0].ingredients.push({name: "ingredient3", quantityDisplay: "1/2", quantity: .5, quantityUnit: "tsp"});
    
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
    	for (var i = 0; i < $scope.ingredientGroups.length; i++){
    		$scope.recipe.ingredients[i] = $scope.ingredientGroups[i].ingredients;
    	}
    	if (isRecipeValid($scope.recipe)){
	        var promise = newRecipeService.saveNewRecipe($scope.recipe);
	        promise.then(function(response){
	        	$scope.result = "Success!";
	        }, function(){
	        	$scope.result = "Error";
	        });
    	}
    };
    
    $scope.isRecipeValid = function(recipe){
    	if (!angular.isDefined(recipe.title) || anugular.equals(recipe.title.trim(), "")) return false;
    	if (!angular.isDefined(recipe.instructions) || !recipe.instructions.length > 0) return false;
    	if (!angular.isDefined(recipe.ingredients[0]) || !recipe.ingredients[0].length > 0) return false;
    	// possibly add more advanced validation in the future
    }
    
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
    
    $scope.updateServingUnits = function(groupIndex){
    	var refreshFlag = false;
    	if ($scope.panelIsOpen[0] === true){
    		unitElement = document.getElementById("servingSize");
    		fractionElement = document.getElementById("servingSizeFraction");
    	}
    	else if ($scope.panelIsOpen[1] === true) {
    		unitElement = document.getElementById("ingQty" + groupIndex);
    		fractionElement = document.getElementById("ingFraction" + groupIndex);
    	}
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
    $scope.addIngredientGroup = function(){
    	if(!angular.isDefined($scope.ingredientGroups)){
    		$scope.ingredientGroups = [];
    	}
    	$scope.ingredientGroups.push({name: "", ingredients: [], index: $scope.ingredientGroups.length});
    };
    
    $scope.removeIngredientGroup = function(groupIndex){
    	$scope.ingredientGroups.splice(groupIndex, 1);
    };
    
    $scope.addIngredient = function(groupIndex){
        if (angular.isDefined($scope.ingredientGroups[groupIndex])){
        	if (!angular.isDefined($scope.ingredientGroups[groupIndex].ingredients)) {
        		$scope.ingredientGroups[groupIndex].ingredients = [];
        	}
        
	        if (angular.isDefined($scope.ingredientForm[groupIndex].name) && $scope.ingredientForm[groupIndex].name !== ""){
	            if (!angular.isDefined($scope.ingredientForm[groupIndex].fraction)){
	                    $scope.ingredientForm[groupIndex].fraction = "";
	                }
	            if (!angular.isDefined($scope.ingredientForm[groupIndex].quantityUnit)){
	                $scope.ingredientForm[groupIndex].quantityUnit = "";
	            }
	            var index = document.getElementById("ingFraction" + groupIndex).selectedIndex;
	            var fractionValue = Number($scope.fractionsList[index].value);
	            if (!isNaN(fractionValue)){
	            	var ingQty = fractionValue;
	            	if (angular.isDefined($scope.ingredientForm[groupIndex].quantity)){
	            		ingQty += parseInt($scope.ingredientForm[groupIndex].quantity);
	            		var qtyDisplay = $scope.ingredientForm[groupIndex].quantity + " " + $scope.ingredientForm[groupIndex].fraction;
	            	}
	            	else {
	            		qtyDisplay = $scope.ingredientForm[groupIndex].fraction;
	            	}
	            }
	            else {
	            	ingQty = parseInt($scope.ingredientForm[groupIndex].quantity)
	            	qtyDisplay = $scope.ingredientForm[groupIndex].quantity;
	            }
	            $scope.ingredient = {
	                name: $scope.ingredientForm[groupIndex].name,
	                quantity: ingQty,
	                quantityUnit: $scope.ingredientForm[groupIndex].quantityUnit,
	                quantityDisplay: qtyDisplay,
	                groupIndex: parseInt(groupIndex),
	                groupName: $scope.ingredientGroups[groupIndex].name
	                };
	            
	            $scope.ingredientGroups[groupIndex].ingredients.push($scope.ingredient);
	            document.getElementById("ingQty" + groupIndex).value = null;
	            document.getElementById("ingQtyUnit" + groupIndex).selectedIndex = 0;
	            document.getElementById("ingFraction" + groupIndex).selectedIndex = 0;
	            $scope.ingredientForm[groupIndex] = {};
	        }
        }
    };
    
    $scope.removeIngredient = function(groupIndex, ingredient){
        if (angular.isDefined($scope.ingredientGroups[groupIndex])) {
        	var index = $scope.ingredientGroups[groupIndex].ingredients.indexOf(ingredient);
//            for (var i=0; i<$scope.recipe.ingredients.length; i++){
//                if ($scope.recipe.ingredients[i].name === name){
//                    $scope.recipe.ingredients.splice(i, 1);
//                    break;
//                }
//            }
        	$scope.ingredientGroups[groupIndex].ingredients.splice(index, 1)
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
    };
    $scope.checkMinValues = function(){
    	var elements = document.getElementByName("input");
    	for (var i = 0; i < elements.length; i++){
    		if (elements[i].type === "number"){
    			if (elements[i].value < 0){
    				elements[i].value = 0;
    			}
    		}
    	}
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

    
    

