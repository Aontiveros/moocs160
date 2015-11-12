
var xml = new XMLHttpRequest()
var currentPage = 0;
var jsonData = null;
  $(document).ready(function () {
    //sidebar toggle
    $('.sidebar-btn').click(function(){
      $('#wrapper').toggleClass('toggled');
    });

    //
    //Ajax get request
    //
    $('#search-btn').click(function(){
      //console.log("---------get allCourseData-----------");
      var reg = /[^A-Za-z0-9 ]/;

      if(!reg.test($("#searchBarTextBox").value)){
        xml.open("GET", "/allCourseData", true);
        xml.send();
      }else{
        alert("Please use only letters and digits in your search.");
      }
    });

    //
    //Ajax response
    //
    xml.onreadystatechange = function() {
      if (xml.readyState == 4) {
        jsonData = jQuery.parseJSON(xml.responseText);
		
        //window.alert(JSON.stringify(xmldata));
        //document.write(JSON.stringify(xmldata));
        addToCourseTable(0);
        // TODO: close the xml stream? needed?
      }
    }

    //
    //ajax data parsed to table rows
    //
    function addToCourseTable(page){
	  $(".searchResRow").remove();
	  currentPage = page;
      var content;
	  var currentPageData = jsonData.slice(page * 10, (page + 1) * 10);
      jQuery.each(currentPageData, function(i, val) {
        //console.log(i +" ------  "+ val.title );
        content +=
        "<tr class=\"searchResRow\" id=\"searchResRow\">"+
        "<td><img class=\"img-circle\" src=\""+val.course_image + "\" width=\"100px\" height=\"100px\"></td>" +
        "<td id=\"c_title\">"+val.title +"</td>" +
        "<td id=\"c_shortdesc\" ><div id=\"table_entries\">" + val.short_desc + "</div></td>" +
        "<td>"+val.category +"</td>" +
        "<td>"+val.start_date +"</td>" +
        "<td>"+val.course_length +"</td>" +
        "<td>"+val.site +"</td></tr>"
        ;
      });
      $("#coursetable").append(content);
    }
	
	//get previous page
	$('.prev').click(function(){
		if (currentPage > 0){
			addToCourseTable(currentPage - 1);
		}
	})
	
	//get next page
	$('.next').click(function(){
		if ((currentPage + 1) * 10 < (jsonData.length + 10 )){
			addToCourseTable(currentPage + 1);
		}
	})
    //
    //TODO:
    //  add the search button handler
    //  add regex get requests
    //  add db queries on regex + course or prof option
    //


    //
    // locking header fucntionality
    //
    // var div=$('#lockedHeader');
    // var start=$(div).offset().top;

    //   $.event.add(window,'scroll',function(){
    //     var p=$(window).scrollTop();
    //     $(div).css('position',(p>start)?'fixed':'static');
    //     $(div).css('top',(p>start)?'0px':'');
    //$(div).css('width',(p>start)?'inherit':'');
    //$(div).css('margin-left',(p>start)?'100px':'');
    //   });
  });
