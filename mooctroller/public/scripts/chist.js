$(document).ready(function () {
  $(".alert-message").alert();
  window.setTimeout(function() { $(".alert-message").alert('close'); }, 2000);

  var table;
  var user = document.cookie.substring(5,document.cookie.length);

  $.get("/completed_courses/" + user, function(data, err){
    addToCourseTable(data);
  });

  //
  //parsing data from db to table rows
  //
  function addToCourseTable(courses){
    table = $('#coursetable').DataTable({
      "columns": [
        null,
        { "width": "20%" },
        { "width": "20%" },
        null,
        null,
        null,
        null
      ]
    });

    jQuery.each(courses, function(i, val) {
      //TODO:reimplement without string parsing

      table.row.add([
      val.course_image,
        val.title,
        val.short_desc,
        val.category,
        val.start_date,
        val.course_length,
        val.course_link
      ]);
      table.draw();
    });
  }

});
