

$(document).ready( function () {
	
    (function($) {
        "use strict";

        // Add active state to sidbar nav links
        var path = window.location.href; // because the 'href' property of the DOM element is the absolute path
            $("#layoutSidenav_nav .sb-sidenav a.nav-link").each(function() {
                if (this.href === path) {
                    $(this).addClass("active");
                }
            });

        // Toggle the side navigation
        $("#sidebarToggle").on("click", function(e) {
            e.preventDefault();
            $("body").toggleClass("sb-sidenav-toggled");
        });
    })(jQuery);
	
  //Para que los tooltip tengan estilo
    $("[data-toggle='tooltip']").tooltip();
	 
	//Tablas con busqueda
    oTable = $('.primaryTable').DataTable();   
    $('.search').keyup(function(){
          oTable.search($(this).val()).draw() ;
    })
    
    //Dropdownlist con busqueda
    $('.searchDrop').selectize({
        sortField: 'text'
    });
    
    //Productos al cambiar checkbox inventariable
    $("#chb_inventariable").change(function() {
    	var hdf = $('#hdf_codprd').val();
    	if(this.checked) {
    		if (hdf != "null") {
      	      $("#prd_existencia").prop("disabled", true );
      	   }
    		else{
    			 $("#prd_existencia").prop("disabled", false );
    		}
    	}
    	else{
			 $("#prd_existencia").prop("disabled", true );
		}
    	});
    
} );

function isNumberKey(evt)
{
   var charCode = (evt.which) ? evt.which : evt.keyCode;
   if (charCode != 46 && charCode > 31 
     && (charCode < 48 || charCode > 57))
      return false;
   if (charCode == 46 && evt.srcElement.value.split('.').length>1) 
	   return false; 

   return true;
}
