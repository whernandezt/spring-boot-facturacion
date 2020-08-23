$(document).ready( function () {
    //$('.primaryTable').DataTable();
    oTable = $('.primaryTable').DataTable();   
    $('.search').keyup(function(){
          oTable.search($(this).val()).draw() ;
    })
    
} );