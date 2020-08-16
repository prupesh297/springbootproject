var app = angular.module("EmployeeManagement", []);
 
// Controller Part
app.controller("EmployeeController", function($scope, $http) {
 
 
    $scope.employees = [];
    $scope.employeeForm = {
        id: -1,
        name: "",
        gender: "",
        department:"",
        dob:new Date()
    };
    $scope.uploadResult ="";
    // Now load the data from server
    _refreshEmployeeData();
    // HTTP POST/PUT methods for add/edit employee  
    // Call: http://localhost:8080/employee
    $scope.submitEmployee = function() {
 
        var method = "";
        var url = "";
 
        if ($scope.employeeForm.id == -1) {
        	console.log("create methode");
            method = "POST";
            url = '/empapp/emp/createemployee';
        } else {
        	console.log("update methode");
            method = "PUT";
            url = '/empapp/emp/updateemployee';
        }
 
        $http({
            method: method,
            url: url,
            data: angular.toJson($scope.employeeForm),
            headers: {
                'Content-Type': 'application/json'
            }
        }).then(_success, _error);
    };
 
    $scope.createEmployee = function() {
        _clearFormData();
    }
 
    // HTTP DELETE- delete employee by Id
    // Call: http://localhost:8080/employee/{empId}
    $scope.deleteEmployee = function(employee) {
        $http({
            method: 'DELETE',
            url: '/empapp/emp/empl/' + employee.id
        }).then(_success, _error);
    };
    
    // In case of edit
    $scope.editEmployee = function(employee) {
        $scope.employeeForm.id = employee.id;
        $scope.employeeForm.name = employee.name;
        $scope.employeeForm.gender = employee.gender;
        $scope.employeeForm.department = employee.department;
        $scope.employeeForm.dob = new Date(employee.dob);
    };
 
    // Private Method  
    // HTTP GET- get all employees collection
    // Call: http://localhost:8080/employees
    function _refreshEmployeeData() {
        $http({
            method: 'GET',
            url: '/empapp/emp/allemployee'
        }).then(
            function(res) { // success
                $scope.employees = res.data;
            },
            function(res) { // error
                console.log("Error: " + res.status + " : " + res.data);
            }
        );
    }
 
    function _success(res) {
        _refreshEmployeeData();
        _clearFormData();
    }
 
    function _error(res) {
        var data = res.data;
        var status = res.status;
        var header = res.header;
        var config = res.config;
        _refreshEmployeeData();
        //alert("Error: " + status + ":" + data);
    }
 
    function _errorr(res) {
        _refreshEmployeeData();
        _clearFormData();
    }
    
    // Clear the form
    function _clearFormData() {
        $scope.employeeForm.id = -1;
        $scope.employeeForm.name = "";
        $scope.employeeForm.gender = "";
        $scope.employeeForm.department = "";
        $scope.employeeForm.dob = "";
    };
    
    $scope.SelectFile = function (file) {
        $scope.myForm.file = file;
    };
    //ng-click="SelectFile(myForm.file)
    $scope.doUploadFile = function(files) {  
    	//SelectFile(); 
        var url = "/empapp/emp/upload/excel";
        var data = new FormData();
        data.append("file", files[0]);
        console.log("file Name:"+files[0].name)
        var config = {
                transformRequest: angular.identity,
                transformResponse: angular.identity,
                headers: {
                    'Content-Type': undefined
                }
            }
        $http.post(url, data, config).then(
            		
                    // Success
                    function(response) {
                        _refreshEmployeeData();
                        $scope.uploadResult = response.dada;
                        
                    },
                    // Error
                    function(response) {
                    	$scope.uploadResult = response.dada;
                    	alert("Error: " + response.data);
                    });
        	        
       };
});
