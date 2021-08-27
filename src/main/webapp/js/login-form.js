var label = document.getElementById("signup-login-label");

function showPassIn(form) {
  var f = document.getElementById(form);
  var e = f.getElementsByClassName("password_input");
  for(i = 0; i < e.length; ++i){
      if (e[i].type === "password") {
          e[i].type = "text";
      } else {
          e[i].type = "password";
      }
  }
}

function showSignUpForm(){
    
    var divLogin = document.getElementById("login-form");
    var divSignup = document.getElementById("signup-form");

    if(label.value == "true"){
    	label.value = "false";
        label.innerHTML = "Sign up";
        divLogin.style.display = "block";
        divSignup.style.display = "none";
        
    }else{
        label.value = "true";
        label.innerHTML = "Login";
        divLogin.style.display = "none";
        divSignup.style.display = "block";
    }
}

function doCaptcha() {
    var response = grecaptcha.getResponse();

    if(response.length!=0){
        return true;
    }
    else{
        document.getElementById('status').innerHTML="Debes aceptar el captcha.";
        return false;
    }
};