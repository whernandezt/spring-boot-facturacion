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

$(document).ready( function () {
	
	//Para que los tooltip tengan estilo
	 $(function () {
	        $("[data-toggle='tooltip']").tooltip();
	    });
	 
	//Tablas con busqueda
    oTable = $('.primaryTable').DataTable();   
    $('.search').keyup(function(){
          oTable.search($(this).val()).draw() ;
    })
    
    //Dropdownlist con busqueda
    $('.searchDrop').selectize({
        sortField: 'text'
    });
    
} );

//Cambiar el menu seleccionado
$('.navbar-nav .nav-item').click(function(){
    $('.navbar-nav .nav-item').removeClass('active');
    $(this).addClass('active');
})
