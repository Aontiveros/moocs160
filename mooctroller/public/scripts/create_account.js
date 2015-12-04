var createCookie = function(email){
    document.cookie="user="+email;
};

function goBackToSearch(){
    window.location.href="index.html";
}

var minPassLength = 8;
function verifyPassword(pass1, pass2){
	var valid = true;

	if (pass1 !== pass2){
		valid = false;
		alert("Passwords do not match");
	}

	if (pass1.length < minPassLength || pass2.length < minPassLength ){
		valid = false;
		alert("Minimum password length is 8 characters");
	}

	return valid;
}

var emailRegex = /^(([^<>()[\]\.,;:\s@\"]+(\.[^<>()[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i;
function verifyEmail(email){
	var valid = emailRegex.test(email);

	if (!valid){
		alert("Email address is not valid");
	}

	return valid;
}

function validate() {
        var email = document.getElementById("emailField").value;
        var pass1 = document.getElementById("passField1").value;
		var pass2 = document.getElementById("passField2").value;
        verifyEmail(email);
		verifyPassword(pass1, pass2);
		if (verifyEmail(email) && verifyPassword(pass1, pass2)){
            $.ajax({
                type: "POST",
                    url: "/createusr",
                        contentType: "application/json",

                data:JSON.stringify({
                    username:email,
                    password:pass1
                }),
                success: function() {
                    createCookie(email);
                    alert('Account Created!');
                    goBackToSearch();
                },
                error: function(){
                    alert('Account not created, account may already exist');
                }
            });
        }
}
