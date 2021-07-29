
$(document).ready(function(){


$(document).on('click', '.top_nav_trigger', function(){
              $('.top_nav_links').slideToggle(300);
        });


   /*******Top Navigation Slide for Mobile Start****************/
   
 $(document).on('click', '.has_child > a', function(){
       var wWidth = $(window).width();
       if(wWidth < 1000){
          var target = $(this).next('ul').css('display');
                        if(target == 'none'){
                           $('.has_child ul').slideUp(500);
                           $(this).next('ul').slideDown(500);
                           $('.has_child ul').parent().removeClass('submenu_open');
                           $(this).parent().addClass('submenu_open');
                        }
                        else {
                           $(this).next('ul').slideUp(500);
                           $(this).parent().removeClass('submenu_open');
                        }
       }
      });

 $(document).on('click','.has_child_dropdown a', function(){
       var wWidth = $(window).width();
       if(wWidth < 1000){
          var target = $(this).next('ul').css('display');
                        if(target == 'none'){
                           $('.has_child_dropdown ul').slideUp(500);
                           $(this).next('ul').slideDown(500);
                           $('.has_child_dropdown ul').parent().removeClass('submenu_open');
                           $(this).parent().addClass('submenu_open');
                        }
                        else {
                           $(this).next('ul').slideUp(500);
                           $(this).parent().removeClass('submenu_open');
                        }
       }
      });

 
	
	
	  /******* Windows Resize Function ****************/
      
   $(window).resize(function(){
         var wWidth = $(window).width();
           if(wWidth > 960){
               $( ".has_child" ).removeClass( "submenu_open" );
     
               }
            
          
      
   });
	
	

});


