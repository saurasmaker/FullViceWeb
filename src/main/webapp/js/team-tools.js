/*
 * Team Tools 
 
 
 
 
var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
var popoverList = popoverTriggerList.map(function (popoverTriggerEl) {
    return new bootstrap.Popover(popoverTriggerEl)
});
*/


var teamInputConfirm = document.getElementById("team-input-confirm");
var teamBtnCancel = document.getElementById("team-btn-cancel");

function enableNameInput(){
    document.getElementById("team-input-name").disabled = false;
    teamInputConfirm.disabled = teamBtnCancel.disabled = false;
}

function enableDescriptionInput(){
    document.getElementById("team-input-description").disabled = false;
    teamInputConfirm.disabled = teamBtnCancel.disabled = false;
}

function enableVideoGameIdInput(){
	document.getElementById("team-input-video-game").disabled = false;
	teamInputConfirm.disabled = teamBtnCancel.disabled = false;
}

function enablePictureInput(){
    document.getElementById("team-input-logo").disabled = false;
    teamInputConfirm.disabled = teamBtnCancel.disabled = false;
}



function resetTeamData(name, description, videoGameId) { 

    var inputName = document.getElementById("team-input-name");
    var inputDescription = document.getElementById("team-input-description");
	var inputVideoGameId = document.getElementById("team-input-video-game");
	
    inputName.disabled = true;
    inputName.value = name;

    inputDescription.disabled = true;
    inputDescription.value = description;

	inputVideoGameId.disabled = true;
	inputVideoGameId.value = videoGameId;

    teamInputConfirm.disabled = teamBtnCancel.disabled = true;
}
