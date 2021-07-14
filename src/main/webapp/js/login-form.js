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
    
    var label = document.getElementById("signup-login-label");
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