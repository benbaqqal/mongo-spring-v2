angular.module('myApp', [ 'ui-notification' ]).controller(
		'appCtrl',
		function($scope, $http, $timeout, Notification) {

			$scope.task = {};

			$http.get('/taskList').success(function(data) {
				$scope.tasks = data;
			});

			$scope.saveTask = function() {
				if ($scope.task.title == null
						|| $scope.task.description == null) {
					Notification.error('Data Error');

				} else {
					$http.post('/saveTask', $scope.task).success(
							function(data, status, headers, config) {
								$scope.tasks = data;
								$scope.clear();
							});
					Notification.success('Done!');
				}
			};

			$scope.deleteTask = function(task) {
				if (confirm("Delete!")) {
					$http.post('/deleteTask', task).success(
							function(data, status, headers, config) {
								$scope.tasks = data;
								$scope.clear();
								Notification.info('Deleted!');
							})
				}
				;
			};

			$scope.editTask = function(task) {
				$scope.task.id = task.id;
				$scope.task.title = task.title;
				$scope.task.description = task.description;
			};

			$scope.clear = function() {
				$scope.task.id = null;
				$scope.task.title = null;
				$scope.task.description = null;
			};
		});