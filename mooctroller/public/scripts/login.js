var createCookie = function(email){
    document.cookie="user="+email;
};

function goBackToSearch(){
    window.location.href="index.html";
}

function validate() {
        var email = document.getElementById("emailField").value;
        var password = document.getElementById("passField").value;
        $.ajax({
       type: "POST",
        url: "/loginusr",
            contentType: "application/json",
       
       data:JSON.stringify({
           username:email,
           password:password
       }),
       success: function() {
          createCookie(email);
         alert('Log in successful!');
         goBackToSearch();
       },
       error: function(){
           alert('Invalid log in, please try again');
       }
    });

}