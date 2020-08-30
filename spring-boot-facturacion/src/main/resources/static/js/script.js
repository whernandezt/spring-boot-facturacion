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
