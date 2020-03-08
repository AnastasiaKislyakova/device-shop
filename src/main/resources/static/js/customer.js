const url = "http://localhost:8080/api/customer";
let sorting = "";
let params = "";

function createCustomer() {
		let firstName = document.getElementById("firstName").value;
		let lastName = document.getElementById("lastName").value;
		let middleName = document.getElementById("middleName").value;
		let date = new Date(document.getElementById("birthdate").value);
		let birthdate = date.toLocaleDateString("ru");

		let responseTextArea = document.getElementById("responsePost");
		axios.post(url, {
			firstName : firstName,
			lastName : lastName,
			middleName : middleName,
			birthdate : birthdate
	    }).then(response => {
	    	console.log(response);
			responseTextArea.textContent = printCustomer(response.data);

	    }).catch(error => {
	    	console.log(error);
	    	console.log(error.response);
	        if (error.response) {
	            alert(error.response.status + " " + error.response.statusText + "\n" + error.response.data["error"]["message"]);
	        } else {
	            alert(error);
	        }
	    })
}

function getCustomers() {
	let responseTextArea = document.getElementById("responseGet");
	params = "";
	let firstNameParam = document.getElementById("firstNameParam").value;
	let lastNameParam = document.getElementById("lastNameParam").value;
	let middleNameParam = document.getElementById("middleNameParam").value;
	let birthdateParam = document.getElementById("birthdateParam").value;
	let birthdateFrom = document.getElementById("birthdateFrom").value;
	let birthdateTo = document.getElementById("birthdateTo").value;
	let page = document.getElementById("page").value;
	let pageItems = document.getElementById("pageItems").value;

	addParam("firstName=", firstNameParam);
	addParam("lastName=", lastNameParam);
	addParam("middleName=", middleNameParam);
	addParam("birthdate=", dateRu(birthdateParam));
	addParam("birthdateFrom=", dateRu(birthdateFrom));
	addParam("birthdateTo=", dateRu(birthdateTo));

	if (params != ""){
		params += "&";
	} 
	params = "?" + params + "page=" + page + "&" + "pageItems=" + pageItems;
	if (sorting != "") {
		params += "&orderBy=" + sorting;
	}
	console.log(url + params);

	axios.get(url+params).then(response => {
		console.log(response);
		responseTextArea.textContent = "";
		response.data.forEach(function(customer){
			responseTextArea.textContent += printCustomer(customer);
		})
	}).catch(error => {
			console.log(error);
	    	console.log(error.response);
	        if (error.response) {
	            alert(error.response.status + " " + error.response.statusText + "\n" + error.response.data["error"]["message"]);
	        } else {
	            alert(error);
	        }
	    })
}

function getCustomerById(){
	let responseTextArea = document.getElementById("responseGet");
	let id = document.getElementById("customerId").value;
	console.log(url+"/"+ id);
	axios.get(url+"/"+ id).then(response => {
		console.log(response);
		responseTextArea.textContent = printCustomer(response.data);
	}).catch(error => {
	        console.log(error);
	    	console.log(error.response);
	        if (error.response) {
	            alert(error.response.status + " " + error.response.statusText + "\n" + error.response.data["error"]["message"]);
	        } else {
	            alert(error);
	        }
	    })
}

function printCustomer(customer){
	return "\n" +
		   "  Фамилия : " + customer["lastName"] + "\n" +
		   "  Имя : " + customer["firstName"] + "\n" +
		   "  Отчество : " + customer["middleName"] + "\n" +
		   "  Дата рождения : " + customer["birthdate"] + "\n" +
		   "  id : " + customer["id"] + "\n\n";
}

function dateRu(date){
	if (date != ""){
		return new Date(date).toLocaleDateString("ru");
	} else {
		return "";
	}	
}

function addParam(paramName, paramValue){
	if (paramValue != "") {
		if (params != ""){
			params += "&";
		}
		params += paramName + paramValue;
	}
}

function addSorting(){
	if (sorting != ""){
		sorting += ",";
	}
	let e = document.getElementById("sortBy");
	let param = e.options[e.selectedIndex].value;
	let sortParamsTextArea = document.getElementById("sortParams");
	sortParamsTextArea.textContent += e.options[e.selectedIndex].text + " ";
	if (document.getElementById("desc").checked) {
		param = "-" + param;
		sortParamsTextArea.textContent += "по убыванию \n";
	}else {
		sortParamsTextArea.textContent += "по возрастанию \n";
	}
	sorting += param;
}

function cleanSorting(){
	let sortParamsTextArea = document.getElementById("sortParams");
	sortParamsTextArea.textContent = "";
	sorting = "";
}