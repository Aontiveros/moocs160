
$(document).ready(function () {

  var user = document.cookie.substring(5,document.cookie.length);

  $.get("/saved_course_data/" + user, function(data, err){
    addToCourseTable(data);
  });


  var table;
  //
  //parsing data from db to table rows
  //
  function addToCourseTable(courses){
    table = $('#coursetable').DataTable({
      rowReorder:true
    });

    jQuery.each(courses, function(i, val) {
      jsonData= JSON.stringify(val.course_data);
      //data is a different format after reordering rows, must be handled
      var rowdata ="",c = "";
      if(JSON.stringify(jsonData.indexOf('['))<0){
        c = jsonData.replace(',','>,');

        rowdata = c.split(">,");

        var linkToCourse = "<a href=\"" + rowdata[7].substring(rowdata[7].indexOf('https'),rowdata[7].indexOf('target')-1) + "\" target=\"_blank\">"+
        "<div class=\"btn btn-sm btn-success center-block\">Course Site</div> </a>";

        //
        // console.log(
        //   "\n------------------------" +rowdata[0] +
        //   "\n------------------------" +rowdata[1]+
        //   "\n------------------------" +rowdata[2]+
        //   "\n------------------------" +rowdata[3]+
        //   "\n------------------------" +rowdata[4]+
        //   "\n------------------------" +rowdata[5]+
        //   "\n------------------------" +rowdata[6]+
        //   "\n------------------------" +rowdata[7]
        // );

        table.row.add([
          JSON.stringify(val.priority),
          rowdata[0].substring(1,rowdata[0].length).replace(new RegExp('\\\\"','g'),""),
          "<"+rowdata[1].substring(1,rowdata[1].length).replace(new RegExp('\\\\"','g'),"")+">",
          "<"+rowdata[2].substring(1,rowdata[2].length).replace(new RegExp('\\\\"','g'),"")+">",
          "<"+rowdata[3].substring(1,rowdata[3].length).replace(new RegExp('\\\\"','g'),"")+">",
          "<"+rowdata[4].substring(1,rowdata[4].length).replace(new RegExp('\\\\"','g'),"")+">",
          "<"+rowdata[5].substring(1,rowdata[5].length).replace(new RegExp('\\\\"','g'),"")+">",
          "<"+rowdata[6].substring(1,rowdata[6].length).replace(new RegExp('\\\\"','g'),"")+">",
          linkToCourse,
        ]);

      }else{

        c = jsonData.replace(/\[/,'').replace(/\]/,'');
        console.log("this is c |"+c+"|");
        rowdata = c.split('",');

        // console.log(
        //   "\n-----|-------------------" +rowdata[0] +
        //   "\n-----|-------------------" +rowdata[1]+
        //   "\n-----|-------------------" +rowdata[2]+
        //   "\n-----|-------------------" +rowdata[3]+
        //   "\n-----|-------------------" +rowdata[4]+
        //   "\n-----|-------------------" +rowdata[5]+
        //   "\n-----|-------------------" +rowdata[6]+
        //   "\n-----|-------------------" +rowdata[7]
        // );

        var linkToCourse = "<a href=\"" + rowdata[7].substring(rowdata[7].indexOf('https'),rowdata[7].indexOf('target')-1) + "\" target=\"_blank\">"+
        "<div class=\"btn btn-sm btn-success center-block\">Course Site</div> </a>";

        table.row.add([
          JSON.stringify(val.priority),
          rowdata[0].substring(3,rowdata[0].length-1),
          rowdata[1].substring(2,rowdata[1].length-1),
          rowdata[2].substring(2,rowdata[2].length-1),
          rowdata[3].substring(2,rowdata[3].length-1),
          rowdata[4].substring(2,rowdata[4].length-1),
          rowdata[5].substring(2,rowdata[5].length-1),
          rowdata[6].substring(2,rowdata[6].length-1),
          linkToCourse,
        ]);
      }
      table.draw();
    });
  }

  $('.save-btn').click(function(){

    var d = table.rows().data();

    $.post("/reset_courses/" + user, function(data, err){
      save_courses(d);
      alert("Changes Saved");
    });
  });

  $('.delete_course-btn').click(function() {

    $.ajax({
      url:"/delete_course/"+user,
      type: "POST",
      contentType: "application/json",
      // data: table.row(0).data(),
      success: function() {
        alert('Course removed.');
      },
      error: function(err){
        alert('error'+err);
      }
    });

    table.row(0).remove();
    table.draw();
  });

  $('.completed_course-btn').click(function(){
    //add to course history
    //alert(table.row( $(this).parents('tr') ).data());

    $.ajax({
      url:"/completed_course/"+user,
      type: "POST",
      contentType: "application/json",
      // data: table.row(0).data(),
      success: function() {
        alert('Course added to course history! Good Job!');
      },
      error: function(err){
        alert('error'+err);
      }
    });

    table.row(0).remove();
    table.draw();

  });

  function save_courses(d){
    for(i = 0; i < d.length; i++){
      var postData = JSON.stringify({
        priority: null,
        usr: document.cookie,
        data: d[i]
      });

      $.ajax({
        url:"/update_saved_course/",
        type: "POST",
        contentType: "application/json",
        data: postData
      });

    }
  }
});
