var xml = new XMLHttpRequest();
var currentPage = 0;
var jsonData = null;
$(document).ready(function () {

  /*
  if cookie is set login and create account are hidden
  if cookie is not set both of those page links are present
  */
  if (document.cookie.indexOf('user') === 0){
    // window.alert("is: "+document.cookie);
    $('#login-btn').hide();
    $('#logout-btn').show();
    $('#createacc-btn').hide();
  }else{
    // window.alert("NOT: "+document.cookie);
    $('#login-btn').show();
    $('#logout-btn').hide();
    $('#createacc-btn').show();
  }

  //
  // response data from database
  //
  xml.onreadystatechange = function() {
    if (xml.readyState == 4) {
      jsonData = jQuery.parseJSON(xml.responseText);
      addToCourseTable(jsonData);
    }
  };

  var isTableEmpty = true;
  var table;
  //
  //parsing data from db to table rows
  //
  function addToCourseTable(page){
    //clears the table for the second search
    var rowVals = [];
    if(!isTableEmpty){
      table.clear();
    }

    jQuery.each(page, function(i, val) {
      //adds notification that a certificate paywall has been bypassed
      var bypassAlert = "";
      //button to link to course
      var linkToCourse =
      "<td>"+bypassAlert+"<a href=\""+ val.course_link +"\" target=\"_blank\">"+
      "<div class=\"btn btn-sm btn-success center-block\">" +
      val.site +
      "</div> </a> </td>";

      if(val.certificate == 'yes'){
        bypassAlert = "Certification paywall has been bypassed:";
      }

      if(isTableEmpty){
        $("#coursetable_body").append(
          "<tr id=\"searchResRow\">"+
          "<td><img class=\"img-circle\" src=\""+val.course_image + "\" width=\"100px\" height=\"100px\"></td>" +
          "<td id=\"c_title\"><h5><b>"+val.title +"</b></h5></td>" +
          "<td ><div id=\"shortdesc\">" + val.short_desc + "</div></td>" +
          "<td style=\"word-break: break-all;\">"+val.category +"</td>" +
          "<td>"+val.start_date.substring(0,10) +"</td>" +
          "<td>"+val.course_length +"</td>" +
          "<td>"+val.profname +"</td>" +
          linkToCourse +"</tr>");
        }else{
          //required fix for adding new data to table,
          // datatable plugin wont change table content unless table is not empty)
          rowVals = [
            "<td><img class=\"img-circle\" src=\""+val.course_image + "\" width=\"100px\" height=\"100px\"></td>" ,
            "<td id=\"c_title\"><h5><b>"+val.title +"</b></h5></td>",
            "<td><div id=\"shortdesc\">" + val.short_desc + "</div></td>",
            "<td>"+val.category +"</td>",
            "<td>"+val.start_date.substring(0,10) +"</td>",
            "<td>"+val.course_length +"</td>" ,
            "<td>"+val.profname +"</td>",
            linkToCourse
          ];
          table.row.add(rowVals);
        }
      });

      // removes old search content from table if needed
      if(isTableEmpty){
        addDataTable();
        isTableEmpty = false;
      }
      table.draw();
    }

    //
    // Creates Sortable Data Table after data
    // is inserted
    //
    function addDataTable(){
      //ensures the datatable plugin is initialized only once
      table = $('#coursetable').DataTable({
        "bProcessing": true,
        "bDeferRender": true
      });
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
      //search button handler
      //
      $('#search-btn').click(function(){
        var searchValue = $('#searchBarTextBox').val();
        xml.open("GET", "/search/" + searchValue, true);
        xml.send();
      });

      //
      // watching logo click
      //
      $('logo').click(function() {
        window.location.href="";
      });

      //
      // watching enter key for searching
      //
      $("#searchBarTextBox").keyup(function(event){
        if(event.keyCode == 13){
          $("#search-btn").click();
        }
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
          window.location.href = "../feature_pages/todo.html";
        }else{
          window.location.href = "../login.html";
        }
      });

      $('#coursehistory-btn').click(function(){
        if(document.cookie.indexOf('user') === 0){
          window.location.href = "../feature_pages/chist.html";
        }else{
          window.location.href = "../login.html";
        }
      });

      $('#professorhistory-btn').click(function(){
        if(document.cookie.indexOf('user')=== 0){
          window.location.href = "../feature_pages/phist.html";
        }else{
          window.location.href = "../login.html";
        }
      });

      $('#browsemajor-btn').click(function(){
        if(document.cookie.indexOf('usr')=== 0){
          console.log(document.cookie);
          window.location.href = "../feature_pages/browse.html";
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
    document.cookie = "user=; expires=Thu, 01 Jan 1970 00:00:00 UTC;"; //user=; why is this included in the cookie after logout?
    alert("Log out successful!");
    window.location.reload();
  }
