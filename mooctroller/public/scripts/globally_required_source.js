
$(document).ready(function () {


if (document.cookie.indexOf('user') === 0){
  $('#login-btn').hide();
  $('#logout-btn').show();
  $('#createacc-btn').hide();
}else{
  $('#login-btn').show();
  $('#logout-btn').hide();
  $('#createacc-btn').show();
}


  //////////////////////////
  //
  //
  //  Button Handlers
  //
  //
  //////////////////////////
  //sidebar toggle
  $('.sidebar-btn').click(function(){
    $('#wrapper').toggleClass('toggled');
  });

  //
  // watching logo click
  //
  $('.logo').click(function() {
    window.location.href="/";
  });

  //
  // login and create buttons
  //
  $('#createacc-btn').click(function(){
    window.location.href = "../create_account.html";
  });

  $('#login-btn').click(function(){
    window.location.href = "../login.html";
  });

  $('#todo-btn').click(function(){
    if(document.cookie.indexOf('user') === 0){
      window.location.href = "../saved_courses.html";
    }else{
      window.location.href = "../login.html";
    }
  });

  $('#coursehistory-btn').click(function(){
    if(document.cookie.indexOf('user') === 0){
      window.location.href = "../chist.html";
    }else{
      window.location.href = "../login.html";
    }
  });

  $('#professorhistory-btn').click(function(){
    if(document.cookie.indexOf('user')=== 0){
      window.location.href = "../phist.html";
    }else{
      window.location.href = "../login.html";
    }
  });

  $('#browsemajor-btn').click(function(){
    if(document.cookie.indexOf('user')=== 0){
      console.log(document.cookie);
      window.location.href = "../browse.html";
    }else{
      window.location.href = "../login.html";
    }
  });

  //////////////////////////
  //
  //
  //  End of Button Handlers
  //
  //
  //////////////////////////
});

function logOut(){
  //set cookie to expired date
  document.cookie = "user=; expires=Thu, 01 Jan 1970 00:00:00 UTC;";
  window.location.href = "/";
}
