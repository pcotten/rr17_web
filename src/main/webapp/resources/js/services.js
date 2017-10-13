var app = angular.module('app');

app.factory('pantryViewService', ['$q', '$http',  function($q, $http) {
    return {
    	
    	getPantryIngredients:function(userId){

            var deferred = $q.defer();
            var url = "http://localhost:8080/rr17/users/" + userId + "/pantry/ingredients";
            var req = {
                    method : 'GET',
                    url : url
            };
            $http(req).then(function(data){
                if (angular.isDefined(data)){
                	deferred.resolve({
                		data : data,
                	});
                }
            }, function(data, status){
                deferred.resolve("Error");
            });
            return deferred.promise;
        },
	    getPantryCategories:function(userId){
	
	        var deferred = $q.defer();
	        var url = "http://localhost:8080/rr17/users/" + userId + "/pantry/categories";
	        var req = {
	                method : 'GET',
	                url : url
	        };
	        $http(req).then(function(data){
	            if (angular.isDefined(data)){
	            	deferred.resolve({
	            		data : data,
	            	});
	            }
	        }, function(data, status){
	            deferred.resolve("Error");
	        });
	        return deferred.promise;
	    }
    };
}]);  
app.factory('newRecipeService', ['$q', '$http',  function($q, $http) {
    return {
    	
        fetchOvenTemps:function(){

            var deferred = $q.defer();
            var url = "http://localhost:8080/rr17/av/lu_oventemp";
            var req = {
                    method : 'GET',
                    url : url
            };
            $http(req).then(function(data){
                if (angular.isDefined(data)){
                	deferred.resolve({
                		data : data,
                	});
                }
            }, function(data, status){
                deferred.resolve("Error");
            });
            return deferred.promise;
        },
        fetchMeasUnits:function(qualifier){

            var deferred = $q.defer();
            var url = "http://localhost:8080/rr17/av/lu_measunit";
            if (qualifier != null){
            	url += "?qualifier=" + qualifier;
            }
            var req = {
                    method : 'GET',
                    url : url
            };
            $http(req).then(function(data){
                if (angular.isDefined(data)){
                	deferred.resolve({
                		data : data,
                	});
                }
            }, function(data, status){
                deferred.resolve("Error");
            });
            return deferred.promise;
        },
        fetchTimeUnits:function(qualifier){

            var deferred = $q.defer();
            var url = "http://localhost:8080/rr17/av/lu_timeunit";
            if (qualifier != null){
            	url += "?qualifier=" + qualifier;
            }
            var req = {
                    method : 'GET',
                    url : url
            };
            $http(req).then(function(data){
                if (angular.isDefined(data)){
                	deferred.resolve({
                		data : data,
                	});
                }
            }, function(data, status){
                deferred.resolve("Error");
            });
            return deferred.promise;
        },
        saveNewRecipe:function(recipe){
        	 var deferred = $q.defer();
             var url = "http://localhost:8080/rr17/users/1/recipes";
             var req = {
                     method : 'POST',
                     url : url,
                     data : recipe
             };
             $http(req).then(function(data){
            	 if (angular.isDefined(data)){
            		 deferred.resolve({
            			 dataFromService : data,
            		 });
            	 }
             }, function(data, status){
            	 deferred.resolve("Error");
             });
             return deferred.promise;
        },

    };
}]);
