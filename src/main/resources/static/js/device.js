const urlDevice = "http://localhost:8080/api/device";
const urlColor = "http://localhost:8080/api/color";
let sorting = "";
let params = "";

function checkColor() {
	let colorRGB = document.getElementById("color").value;
	let colorRGBInt = parseInt(colorRGB.replace("#", ""), 16);
	let colorNameField = document.getElementById("colorName");
	axios.get(urlColor + "/" + colorRGBInt).then(response => {
			console.log(urlColor + "/" + colorRGBInt);
			console.log(response);
			if (response.data != ""){
				colorNameField.value = response.data["name"];
				// foundRgb = colorRgbInt;
				// foundName = response.data["name"];
			} else {
				colorNameField.value = "";
			}

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

function createDevice() {
		params = "";
		let e = document.getElementById("deviceType");
		let deviceType = e.options[e.selectedIndex].value;
		let modelName = document.getElementById("modelName").value;
		let manufacturer = document.getElementById("manufacturer").value;
		let date = new Date(document.getElementById("manufactureDate").value);
		let manufactureDate = date.toLocaleDateString("ru");
		let colorRGB = document.getElementById("color").value;
		let colorRGBInt = parseInt(colorRGB.replace("#", ""), 16);
		let colorName = document.getElementById("colorName").value;
		let price = document.getElementById("price").value;
		let responseTextArea = document.getElementById("responsePost");

	    axios.post(urlDevice, {
	    	deviceType : deviceType,
	    	modelName : modelName,
	    	manufacturer : manufacturer,
	    	manufactureDate : manufactureDate,
	    	colorName : colorName,
	    	colorRGB : colorRGBInt,
	    	price : price
	    }).then(response => {
	    	console.log(response);
			responseTextArea.textContent = printDevice(response.data);

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

function getDevices() {
	let responseTextArea = document.getElementById("responseGet");
	let e = document.getElementById("deviceTypeParam");
	let deviceTypeParam = e.options[e.selectedIndex].value;
	let modelNameParam = document.getElementById("modelNameParam").value;
	let manufacturerParam = document.getElementById("manufacturerParam").value;
	let manufactureDateParam = document.getElementById("manufactureDateParam").value;
	let manufactureDateFrom = document.getElementById("manufactureDateFrom").value;
	let manufactureDateTo = document.getElementById("manufactureDateTo").value;
	let colorRGB = document.getElementById("color").value;
	let colorRGBIntParam = parseInt(colorRGB.replace("#", ""), 16);
	let colorNameParam = document.getElementById("colorName").value;
	let priceParam = document.getElementById("priceParam").value;
	let priceFrom = document.getElementById("priceFrom").value;
	let priceTo = document.getElementById("priceTo").value;
	let page = document.getElementById("page").value;
	let pageItems = document.getElementById("pageItems").value;
	
	addParam("deviceType=", deviceTypeParam);
	addParam("modelName=", modelNameParam);
	addParam("manufacturer=", manufacturerParam);
	addParam("manufactureDate=", dateRu(manufactureDateParam));
	addParam("manufactureDateFrom=", dateRu(manufactureDateFrom));
	addParam("manufactureDateTo=", dateRu(manufactureDateTo));
	if (document.getElementById("colorRB").checked) {
		addParam("colorRGB=", colorRGBIntParam);
		addParam("colorName=", colorNameParam);
	}
	addParam("price=", priceParam);
	addParam("priceFrom=", priceFrom);
	addParam("priceTo=", priceTo);

	if (params != ""){
		params += "&";
	} 
	params = "?" + params + "page=" + page + "&" + "pageItems=" + pageItems;
	if (sorting != "") {
		params += "&orderBy=" + sorting;
	}
	console.log(urlDevice + params);

	axios.get(urlDevice+params).then(response => {
		console.log(response);
		responseTextArea.textContent = "";
		response.data.forEach(function(device){
			responseTextArea.textContent += printDevice(device);
		})
		params="";
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

function getDeviceById(){
	let responseTextArea = document.getElementById("responseGet");
	let id = document.getElementById("deviceId").value;
	console.log(urlDevice + "/" + id);
	axios.get(urlDevice + "/" + id).then(response => {
		console.log(response);
		responseTextArea.textContent = printDevice(response.data);
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

function printDevice(device){
	return "\n" +
		   "  Тип : " + device["deviceType"] + "\n" +
		   "  Модель : " + device["modelName"] + "\n" +
		   "  Производитель : " + device["manufacturer"] + "\n" +
		   "  Дата выпуска : " + device["manufactureDate"] + "\n" +
		   "  Название цвета : " + device["colorName"] + "\n" +
		   "  RGB : " + device["colorRGB"] + "\n" +
		   "  Цена : " + device["price"] + "\n" +
		   "  id : " + device["id"] + "\n\n";
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