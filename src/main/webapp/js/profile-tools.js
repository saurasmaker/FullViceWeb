/*
 * Profile Tools 
 
 
 
 
var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
    return new bootstrap.Popover(popoverTriggerEl)
});
*/



var userInputConfirm = document.getElementById("user-input-confirm");
var userBtnCancel = document.getElementById("user-btn-cancel");

function enableUsernameInput(){
    document.getElementById("user-input-username").disabled = false;
    userInputConfirm.disabled = userBtnCancel.disabled = false;
}

function enablePictureInput(){
    document.getElementById("user-input-picture").disabled = false;
    userInputConfirm.disabled = userBtnCancel.disabled = false;
}

function enableEmailInput(){
    document.getElementById("user-input-email").disabled = false;
    userInputConfirm.disabled = userBtnCancel.disabled = false;
}

function resetUserData(username, email) { 

    var inputUsername = document.getElementById("user-input-username");
    var inputEmail = document.getElementById("user-input-email");

    inputUsername.disabled = true;
    inputUsername.value = username;

    inputEmail.disabled = true;
    inputEmail.value = email;

    userInputConfirm.disabled = userBtnCancel.disabled = true;
}



var profileInputConfirm = document.getElementById("profile-input-confirm");
var profileBtnCancel = document.getElementById("profile-btn-cancel");

function enableNameInput(){
    document.getElementById("update-profile-input-name").disabled = false;
    profileInputConfirm.disabled = profileBtnCancel.disabled = false;
}

function enableSurnamesInput(){
    document.getElementById("update-profile-input-surnames").disabled = false;
    profileInputConfirm.disabled = profileBtnCancel.disabled = false;
}

function enableBiographyInput(){
    document.getElementById("update-profile-input-biography").disabled = false;
    profileInputConfirm.disabled = profileBtnCancel.disabled = false;
}

function enableBirthdayInput(){
    document.getElementById("update-profile-input-birthday").disabled = false;
    profileInputConfirm.disabled = profileBtnCancel.disabled = false;
}

function resetProfileData(name, surnames, biography, birthday) { 

    var inputName = document.getElementById("update-profile-input-name");
    var inputSurnames = document.getElementById("update-profile-input-surnames");
    var inputBiography = document.getElementById("update-profile-input-biography");
    var inputBirthday = document.getElementById("update-profile-input-birthday");

    inputName.disabled = inputSurnames.disabled = inputBiography.disabled = inputBirthday.disabled = true;
    inputName.value = name;
    inputSurnames.value = surnames;
    inputBiography.value = biography;
    inputBirthday.value = birthday;

    profileInputConfirm.disabled = profileBtnCancel.disabled = true;
}


function cancelEditGamerProfile(id, nameInGame, analysisPage){
	var inputNameInGame = document.getElementById("update-gamer-profile-input-name-" + id);
	var inputAnalysisPage = document.getElementById("update-gamer-profile-input-description-" + id);
	
	inputNameInGame.value = nameInGame;
	inputAnalysisPage.value = analysisPage;
}

function removeGamerProfile(){
	return confirm('Are you sure you want to remove this "Gamer Profile"? Click OK to proceed otherwise click Cancel.');
}

