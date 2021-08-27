
document.getElementById("update-user-form").style.display = "none";



function updateAssessment(id, value, subject, comment, publicationDate, publicationTime, editDate, editTime, videogameId, userId){
	document.getElementById("create-assessment-form").style.display = "none";
    document.getElementById("update-assessment-form").style.display = "block";

    document.getElementById("assessment-input-update-id").value = id;
    document.getElementById("assessment-input-update-value").value = value;
    document.getElementById("assessment-input-update-subject").value = subject;
    document.getElementById("assessment-input-update-comment").value = comment;
    document.getElementById("assessment-input-update-publicationdate").value = publicationDate;
    document.getElementById("assessment-input-update-publicationtime").value = publicationTime;
    document.getElementById("assessment-input-update-editdate").value = editDate;
    document.getElementById("assessment-input-update-edittime").value = editTime;
    document.getElementById("assessment-input-update-videogameid").value = videogameId;
    document.getElementById("assessment-input-update-userid").value = userId;
}

function updateBill(id, userId, billingDate, billingTime, paid, paidDate, paidTime){
	document.getElementById("create-bill-form").style.display = "none";
    document.getElementById("update-bill-form").style.display = "block";
    
    document.getElementById("bill-input-update-id").value = id;
    document.getElementById("bill-input-update-userid").value = userId;
    document.getElementById("bill-input-update-billingdate").value = billingDate;
    document.getElementById("bill-input-update-billingtime").value = billingTime;
    document.getElementById("bill-input-update-paid").checked = (paid == "true");
    document.getElementById("bill-input-update-paiddate").value = paidDate;
    document.getElementById("bill-input-update-paidtime").value = paidTime;
}

function updateCategory(id, name, description){
	document.getElementById("create-category-form").style.display = "none";
    document.getElementById("update-category-form").style.display = "block";
    
    document.getElementById("category-input-update-id").value = id;
    document.getElementById("category-input-update-name").value = name;
    document.getElementById("category-input-update-description").value = description;
}

function updatePurchase(id, amount, videogameId, billId){
	document.getElementById("create-purchase-form").style.display = "none";
    document.getElementById("update-purchase-form").style.display = "block";
    
    document.getElementById("purchase-input-update-id").value = id;
    document.getElementById("purchase-input-update-amount").value = amount;
    document.getElementById("purchase-input-update-videogameid").value = videogameId;
    document.getElementById("purchase-input-update-billid").value = billId;
}

function updateRental(id, startDate, startTime, endDate, endTime, returned, videogameId, billId){	
	document.getElementById("create-rental-form").style.display = "none";
    document.getElementById("update-rental-form").style.display = "block";

    document.getElementById("rental-input-update-id").value = id;
    document.getElementById("rental-input-update-startdate").value = startDate;
    document.getElementById("rental-input-update-starttime").value = startTime;
    document.getElementById("rental-input-update-enddate").value = endDate;
    document.getElementById("rental-input-update-endtime").value = endTime;
    document.getElementById("rental-input-update-returned").checked = (returned == "true");
    document.getElementById("rental-input-update-videogameid").value = videogameId;
    document.getElementById("rental-input-update-billid").value = billId;  
}

function updateUser(id, nickname, email, isModerator, isAdmin){

    alert(nickname);

	document.getElementById("create-user-form").style.display = "none";
    document.getElementById("update-user-form").style.display = "block";
    
    document.getElementById("update-user-input-id").value = id;
    document.getElementById("update-user-input-nickname").value = nickname;
    document.getElementById("update-user-input-email").value = email;
    document.getElementById("update-user-input-ismoderator").checked = (isModerator == "true");
    document.getElementById("update-user-input-isadmin").checked = (isAdmin == "true");
}

function updateVideogame(id, name, description, releaseDate, stock, purchasePrice, rentalPrice){	
	
	displayAdminPage();
	
	document.getElementById("create-videogame-form").style.display = "none";
    document.getElementById("update-videogame-form").style.display = "block";
    
    document.getElementById("add-videogameimage-form").style.display = "block";
    document.getElementById("add-videogamecategory-form").style.display = "block";
    
    document.getElementById("videogameimage-title").style.display = "block";
    document.getElementById("videogamecategory-title").style.display = "block";
    document.getElementById("table-videogameimage").style.display = "block";
    document.getElementById("table-videogamescategories").style.display = "block";
    
    document.getElementById("input-send-videogameimage").value = "Anadir imagen a '" + name + "'";
    document.getElementById("input-send-videogamecategory").value = "Anadir categoria a '" + name + "'";
    
    document.getElementById("videogame-input-update-id").value = id;
    document.getElementById("videogame-input-update-name").value = name;
    document.getElementById("videogame-input-update-description").value = description;
    document.getElementById("videogame-input-update-releasedate").value = releaseDate;
    document.getElementById("videogame-input-update-stock").value = stock;    
    document.getElementById("videogame-input-update-purchaseprice").value = purchasePrice;
    document.getElementById("videogame-input-update-rentalprice").value = rentalPrice;  
    
    document.getElementById("videogameimage-input-idvideogame").value = id;
}


function cancelUpdateAssessment(){
	document.getElementById("create-assessment-form").style.display = "block";
    document.getElementById("update-assessment-form").style.display = "none";
}

function cancelUpdateBill(){
	document.getElementById("create-bill-form").style.display = "block";
    document.getElementById("update-bill-form").style.display = "none";
}

function cancelUpdateCategory(){
	document.getElementById("create-category-form").style.display = "block";
    document.getElementById("update-category-form").style.display = "none";
}


function cancelUpdatePurchase(){
	document.getElementById("create-purchase-form").style.display = "block";
    document.getElementById("update-purchase-form").style.display = "none";
}

function cancelUpdateRental(){
	document.getElementById("create-rental-form").style.display = "block";
    document.getElementById("update-rental-form").style.display = "none";
}

function cancelUpdateUser(){
	document.getElementById("create-user-form").style.display = "block";
    document.getElementById("update-user-form").style.display = "none";
}

function cancelUpdateVideogame(){
	document.getElementById("create-videogame-form").style.display = "block";
    document.getElementById("update-videogame-form").style.display = "none";
    
    document.getElementById("videogameimage-title").style.display = "none";
    document.getElementById("videogamecategory-title").style.display = "none";
    document.getElementById("table-videogameimage").style.display = "none";
    document.getElementById("table-videogamescategories").style.display = "none";
    
    document.getElementById("add-videogameimage-form").style.display = "none";
    document.getElementById("add-videogamecategory-form").style.display = "none";
}
