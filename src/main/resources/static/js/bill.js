const url = "http://localhost:8080/api/bill";
let sorting = "";
let params = "";
let items = 1;
let itemsCount = 1;
let node = document.getElementById("billItems");
let responseTextAreaGet = document.getElementById("responseGet");

function deleteBillItem(id){
	if (itemsCount > 1){
		let child = document.getElementById(id);
		node.removeChild(child);
		itemsCount--;
	}
	
}

function addBillItem() {
	items++;
	itemsCount++;
	let div = document.createElement("div");
	div.id = "div" + items;

	let textnodeId = document.createTextNode("Id устройства: ");
	let idInp = document.createElement("input");
	idInp.type = "number";
	idInp.name = "deviceId";
	idInp.id = "deviceId" + items;
	idInp.value = 1;
	idInp.min = 1;

	let textnodeQuantity = document.createTextNode(" Количество: ");
	let quantityInp = document.createElement("input");
	quantityInp.type = "number";
	quantityInp.name = "quantity";
	quantityInp.id = "quantity" + items;
	quantityInp.value = 1;
	quantityInp.min = 1;

	let textnodePrice = document.createTextNode(" Цена: ");
	let priceInp = document.createElement("input");
	priceInp.type = "number";
	priceInp.name = "price";
	priceInp.id = "price" + items;
	priceInp.min = 0.00;
	priceInp.step = 0.10;

	let btn = document.createElement("button");
	btn.addEventListener("click", () => { deleteBillItem(div.id) });
	btn.textContent = "x";

	div.appendChild(textnodeId);
	div.appendChild(idInp);
	div.appendChild(textnodeQuantity);
	div.appendChild(quantityInp);
	div.appendChild(textnodePrice);
	div.appendChild(priceInp);
	div.appendChild(document.createTextNode(" "));
	div.appendChild(btn);
	let br = document.createElement("br");
	div.appendChild(br);

	node.appendChild(div);
}

function createBill() {
		let customerId = document.getElementById("customerId").value;
		let i = 1;
		let billItems = [];
		while (i <= items){
			if (document.getElementById("div"+i) != null) {
				let item = {
					deviceId : document.getElementById("deviceId" + i).value,
					quantity : document.getElementById("quantity" + i).value,
					price : document.getElementById("price" + i).value
				}
				billItems.push(item);
			}
			i++;
		}
		console.log(billItems.toString());

		let responseTextAreaPost = document.getElementById("responsePost");
		axios.post(url, {
			customerId : customerId,
			items : billItems
	    }).then(response => {
	    	console.log(response);
			responseTextAreaPost.textContent = printBill(response.data);

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

function getBills() {
	params = "";
	let customerIdParam = document.getElementById("customerIdParam").value;
	let purchaseDateTimeParam = document.getElementById("purchaseDateTimeParam").value;
	let purchaseDateTimeFrom = document.getElementById("purchaseDateTimeFrom").value;
	let purchaseDateTimeTo = document.getElementById("purchaseDateTimeTo").value;
	let totalPriceParam = document.getElementById("totalPriceParam").value;
	let totalPriceFrom = document.getElementById("totalPriceFrom").value;
	let totalPriceTo = document.getElementById("totalPriceTo").value;
	let page = document.getElementById("page").value;
	let pageItems = document.getElementById("pageItems").value;

	addParam("customerId=", customerIdParam);
	addParam("purchaseDateTime=", dateRu(purchaseDateTimeParam));
	addParam("purchaseDateTimeFrom=", dateRu(purchaseDateTimeFrom));
	addParam("purchaseDateTimeTo=", dateRu(purchaseDateTimeTo));
	addParam("totalPrice=", totalPriceParam);
	addParam("totalPriceFrom=", totalPriceFrom);
	addParam("totalPriceTo=", totalPriceTo);

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
		responseTextAreaGet.textContent = "";
		response.data.forEach(function(bill){
			responseTextAreaGet.textContent += printBill(bill);
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

function getBillById(){
	let id = document.getElementById("billId").value;
	console.log(url+"/"+ id);
	axios.get(url+"/"+ id).then(response => {
		console.log(response);
		responseTextAreaGet.textContent = printBill(response.data);
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

function printBill(bill){
	let res = "\n" + "  Id покупателя : " + bill["customerId"] + "\n";
	bill["items"].forEach(function(item) {
		res += "  Id устройства : " + item["deviceId"] + 
		       "  Количество : " + item["quantity"] +
		       "  Цена : " + item["price"] +
		       "  Сумма : " + item["totalPrice"] + "\n";
	});
	res += "  Сумма : " + bill["totalPrice"]  + "\n" +  
		   "  Дата и время покупки : " + bill["purchaseDateTime"] + "\n" +
		   "  id : " + bill["id"] + "\n\n";
	return res;
}

function dateRu(date){
	if (date != ""){
		let dateTime = new Date(date);
		return dateTime.toLocaleDateString("ru") + " " + dateTime.toLocaleTimeString("ru");
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