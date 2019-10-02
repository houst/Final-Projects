https://codepen.io/alexpivtorak/pen/ByJLoL

$('button.success').click(function() {
  alertify.set({ delay: 1700 });
  							alertify.success("Success notification");  
});

$('button.alert').click(function() {
    alertify.set({ delay: 1700 });
	    							alertify.error("Error notification");  
});