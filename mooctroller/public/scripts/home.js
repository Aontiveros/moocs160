var xml = new XMLHttpRequest();
var currentPage = 0;
var jsonData = null;
$(document).ready(function () {

  //
  //search button handler
  //
  $('#search-btn').click(function(){
    var searchValue = $('#searchBarTextBox').val();
    xml.open("GET", "/search/" + searchValue, true);
    xml.send();
  });
  
  $('#searchBarTextBox').on('keyup', function(e) {
    if (e.which == 13) {
      $('#search-btn').click();
    }
  });

  //
  // response data from database
  //
  // can be replaced with jquery .ajax call
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
      val.site +"</div> </a> " +
      "<div id=\"savecourse-btn\" class=\"btn btn-sm btn-primary center-block savecourse-btn \"> Save For Later</div> </td>";


      if(val.certificate == 'yes'){
        bypassAlert = "Certification paywall has been bypassed:";
      }

      if(isTableEmpty){
        $("#coursetable_body").append(
          "<tr id=\"searchResRow\">"+
          "<td><img class=\"img-circle\" src=\""+ val.course_image + "\" width=\"100px\" height=\"100px\"></td>" +
          "<td id=\"c_title\"><h5><b>" + val.title + "</b></h5></td>" +
          "<td ><div id=\"shortdesc\">" + val.short_desc + "</div></td>" +
          "<td style=\"word-break: break-all;\"><div>" + val.category + "</div></td>" +
          "<td><div>"+val.start_date.substring(0,10) + "</div></td>" +
          "<td><div>"+val.course_length + "</div></td>" +
          "<td><div>"+val.profname + "</div></td>" +
          "<div>"+ linkToCourse + "</div></tr>");

        }else{
          //required fix for adding new data to table,
          // datatable plugin wont change table content unless table is not empty)
          rowVals = [
            "<img class=\"img-circle\" src=\""+val.course_image + "\" width=\"100px\" height=\"100px\">",
            "<div id=\"c_title\"><h5><b>"+val.title +"</b></h5></div>",
            "<div id=\"shortdesc\">" + val.short_desc + "</div>",
            "<div>"+val.category +"</div>",
            "<div>"+val.start_date.substring(0,10) +"</div>",
            "<div>"+val.course_length +"</div>" ,
            "<div>"+val.profname +"</div>",
            "<div>"+ linkToCourse +"</div>"
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

    //
    // 'save course for later' button handler
    //
    // (dynamically loaded button has special click handler)
    //
    $('#coursetable tbody').on('click', "#savecourse-btn", function() {
      if(document.cookie.indexOf('user')=== 0){
        var postData = JSON.stringify({
          priority:null,
          usr: document.cookie,
          data: table.row( $(this).parents('tr') ).data()
        });
        //alert(table.row( $(this).parents('tr') ).data());

        $.ajax({
          url:"/save_course_data",
          type: "POST",
          contentType: "application/json",
          data: postData,
          success: function() {
            alert('Course Saved!');
          },
          error: function(){
            alert('Course did not save correctly, please contact team 1 @ edmunddao@gmail.com');
          }
        });
      }else{
        window.location.href = "../login.html";
      }
    });

  });
