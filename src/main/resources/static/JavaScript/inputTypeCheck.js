/**
 * 
 */
 

function chk01(e){
	// categoryKeyの値を取得
	let price = document.getElementById("price");
	let stock = document.getElementById("stock");
	let delivaryDays = document.getElementById("delivaryDays");
	
	let reg = new RegExp(/^[0-9]+$/);
	if(reg.test(stock.value) && reg.test(price.value) && reg.test(delivaryDays.value)){
		return true;
	}
	else{
		window.alert("条件に従って入力してください");
		return false;
	}
	
}

