var app = angular
	.module('News', [ 'ui.router', 'ui-notification', 'ngResource' ])

	.config(
		function($stateProvider, $urlRouterProvider, $httpProvider) {

		    $stateProvider.state('home', {
			url : '/home',
			templateUrl : '/views/home.html',
			controller : 'MainCtrl'
		    // resolve : {
		    // postPromise : [ 'posts', function(posts)
		    // {
		    // return posts.getAll();
		    // } ]
		    // }
		    }).state('nav', {
			url : '/login',
			templateUrl : '/views/login.html',
			controller : 'Navigation'
		    }).state('posts', {
			url : '/{id}',
			templateUrl : '/views/post.html',
			controller : 'PostCtrl'
		    // resolve : {
		    // post : [
		    // '$stateParams',
		    // 'posts',
		    // function(
		    // $stateParams,
		    // posts) {
		    // return posts
		    // .get($stateParams.id);
		    // } ]
		    // }
		    }).state('error', {
			url : '/error',
			templateUrl : '/views/error.html',
			controller : 'Error'
		    });

		    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
		    // .when('/login', {
		    // templateUrl : '',
		    // controller : 'navigation',
		    // controllerAs: 'controller'
		    // })

		    $urlRouterProvider.otherwise('/home');

		})
	.controller(
		'MainCtrl',
		function($scope, $http, Notification) {

		    $scope.post = {};
		    $scope.isEdit = false;

		    $scope.authors = [];
		    $http.get('/authors').success(function(data) {
			$scope.authors = data;
		    });

		    $http.get('/posts').success(function(data) {
			$scope.posts = data;
		    });

		    $scope.savePost = function(post) {
			console.log(post);
			if (post.title == null || post.body == null
				|| post.author == null) {
			    Notification.error('Data Error');

			} else {
			    $http.post('/savePost', post).success(
				    function(data, status, headers, config) {
					$scope.posts = data;
					$scope.clearPost();
					$scope.isEdit = false;
				    });
			    Notification.success('Done!');
			}
		    };

		    $scope.deletePost = function(post) {
			if (confirm("Delete!")) {
			    $http.post('/deletePost', post).success(
				    function(data, status, headers, config) {
					$scope.posts = data;
					$scope.clearPost();
					Notification.info('Deleted!');
					$scope.isEdit = false;
				    }).error(function() {
					Notification.error('Must be Admin !');
				    })
			}
			;
		    };

		    $scope.editPost = function(post) {
			$scope.post = post;
			$scope.isEdit = true;
		    };

		    $scope.clearPost = function() {
			$scope.post = null;
			$scope.isEdit = false;
		    };

		})

	.controller(
		'PostCtrl',
		function($scope, $stateParams, $http, $resource, Notification) {

		    $scope.post = {};
		    $scope.comment = {};
		    $scope.comment.rating = 1;
		    $scope.postComment = {
			post : $scope.post,
			comment : $scope.comment
		    };

		    $scope.orderBy = [ 'rating', 'date', 'upvotes' ];
		    $scope.reverseSort = function() {
			$scope.order = ($scope.isReverse ? '' : '-')
				+ $scope.orderAttr;
		    };

		    $http.get('/posts/' + $stateParams.id).success(
			    function(data) {
				$scope.post = data;
			    });

		    // $http.get('/comments/'+
		    // $stateParams.id).success(function(data) {
		    // $scope.post.comments = data;
		    // });

		    $scope.saveComment = function() {
			$http.post('/saveComment/' + $scope.post.id,
				$scope.comment).success(
				function(data, status, headers, config) {
				    $scope.post = data;
				    $scope.clearComment();
				});
			Notification.success('Done!');
		    };

		    $scope.clearComment = function() {
			$scope.comment.rating = 1;
			$scope.comment.comment = null;
			$scope.commentForm.comment.$pristine = true;
		    }

		    $scope.upvote = function(isUp, comment) {
			$http
				.post(
					'/upvoteComment/' + isUp + '/'
						+ $scope.post.id, comment)
				.success(
					function(data, status, headers, config) {
					    $scope.post = data;
					})
				.error(function (error){
				    if (error.status == 403){
					Notification.error('Must be Admin !');
				    }
				});
		    };

		})

	.controller(
		'Navigation',
		function($rootScope, $scope, $http, $location) {
		    
		    $rootScope.userName = '';
		    $rootScope.isAdmin = false;
		    
		    var authenticate = function(credentials, callback) {
			var headers = credentials ? {
				authorization : "Basic "
						+ btoa(credentials.username + ":"
								+ credentials.password)
			} : {};

			$http.get('/user', {
				headers : headers
			}).success(function(data) {
			    //console.log(data);
				if (data == ''){
				    $location.path("/login");
				    $rootScope.authenticated = false;
				}
				else  {
				    $rootScope.userName = data.username;
				    $rootScope.authenticated = true;
				    checkIfIsAdmin(data.authorities);
				} 
				callback && callback($rootScope.authenticated);
			}).error(function() {
				$rootScope.authenticated = false;
				callback && callback(false);
			});

		    }
		    
		    checkIfIsAdmin = function(data){
		    	    if (data == null || !$rootScope.authenticated){
		    		return false;
		    	    }
		    	    data.some(function(e, index, array) {
                        	if (e.authority == 'ROLE_ADMIN') {
                        	    $rootScope.isAdmin = true;
                        	    return true;
                        	}
                        	return false;
		    	    });
		    };

		    authenticate();

		    $scope.credentials = {};
		    $scope.login = function() {
			authenticate($scope.credentials, function(authenticated) {
				if (authenticated) {
					console.log("Login succeeded")
					$location.path("/home");
					$scope.error = false;
					$rootScope.authenticated = true;
				} else {
					console.log("Login failed")
					$location.path("/login");
					$scope.error = true;
					$rootScope.authenticated = false;
				}
			})
		    };

		    $scope.logout = function() {
			$http.post('/logout', {}).finally(function() {
				$rootScope.authenticated = false;
				$location.path("/login");
			});
		    }

		}).controller(
			'Error',
			function($rootScope, $scope, $http, $location) {
			    
			});







