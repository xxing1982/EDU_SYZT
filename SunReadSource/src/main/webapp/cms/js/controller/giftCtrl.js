ctrls.controller("giftCtrl",['$scope', '$rootScope','Pageable', 'GetGifts', 'UpdateGift', 'Gift', '$stateParams', 'Dropzone', 'config', 'User',
	function($scope, $rootScope, Pageable, GetGifts, UpdateGift, Gift, $stateParams, Dropzone, config, User){
		$scope.selectSchool = {
			isEdit: true,
			isShowSchool: true,
			checkOne: function(item){
				$("#add").attr("disabled",false); 
				$scope.campusidSelected = item.id;
				$scope.searchArguments = {
        			schoolId:item.id,
				}
				$scope.createPageable();
			}
		};

		//add
		$scope.add = new Gift();
		$scope.dropzone = Dropzone(config.USERICON, function(url){
            $scope.add.picture = url;
        } );
		$scope.c_SD_add = function(){
			$scope.add.giftType = $scope.SelectedSD_add;
		}
		$scope.AddSys = function(){
			$scope.add.exchangeable = true;
			$scope.add.campusId = $scope.campusidSelected;
			$scope.add.userId = $rootScope.id;
			User.get({ id: $scope.id }, function(data) {
    			$scope.add.userName = data.username;
    			$scope.add.$save(function(){
					$scope.add = new Gift();
					$("#addModal").modal('hide');
					location.reload();
				});
  			});
			
		}

		//update
		$scope.edit = new Gift();
		$scope.c_SD_edit = function(){
			$scope.edit.giftType = $scope.SelectedSD_edit;
		}
		$scope.updateSys = function(item){
			$scope.message = "";
			var temp = angular.copy(item);
			$scope.edit.id = temp.id;
			$scope.edit.name = temp.name;
			$scope.edit.giftType = temp.giftType;
			$scope.edit.description = temp.description;
			$scope.edit.coin = temp.coin;
			$scope.edit.price = temp.price;
			$scope.SelectedSD_edit = temp.giftType;
		}
		$scope.EditSys = function(){
			UpdateGift.Update($scope.edit, function(){
				location.reload();
			})
		}

		//delete
		$scope.deleteSys = function(item){
			$rootScope.confirm_modal = {};
			$rootScope.confirm_modal.title="提示";
			$rootScope.confirm_modal.content="确定删除吗？";
			$rootScope.confirm_modal.click = function(){
				Gift.delete({ id: item.id }, function() {
    				location.reload();
  				});
			}
			$('#confirm-modal').modal();
		}

		$scope.createPageable = function (){
			$scope.searchPageable = new Pageable();

			$scope.searchPageable.size = 6;
			$scope.searchPageable.page = 0;

			$scope.searchPageable.pageNumbers.startPage = 1;
        	$scope.searchPageable.pageNumbers.content.length = 8;

        	// Set the placeholder elements
        	$scope.searchPageable.placeHolders.placeHoldersElement = {title: ""};
        	$scope.searchPageable.arguments=$scope.searchArguments;

        	// Build the pageable object
        	$scope.searchPageable.build(GetGifts);
        	$scope.searchPageable.showPage($stateParams.page === undefined ? 1 : $stateParams.page);
		}
	}]);