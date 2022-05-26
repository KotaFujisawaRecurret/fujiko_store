/**
 * 
 */
 
 function onChangeCategory(e){
	// e.valueが0の場合はnewCategoryDiv Elementを表示
	let div = document.getElementById("newCategoryDiv");
	
	if(e.target.value === "0"){
		div.style.display = "block";
	}
	// 0以外の場合はnewCategory Elementのvalueを空文字にして非表示
	else{
		div.style.display = "none";
		let newCategory = document.getElementById("newCategory");
		newCategory.value = "";
	}
	
}
