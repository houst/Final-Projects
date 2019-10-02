jQuery(document).ready(function ($) {

	/**
	 * Pay form for tickets bill AJAX handler
	 */
	$('#pay-form').submit(function (event) {
		event.preventDefault(); // Cancel

		var form = $(this);
		
		var _csrf = form.find('input[name="_csrf"]');

		var data = {
			_csrf: _csrf.val()
		};

		$.ajax({
			data: data,
			timeout: 1000,
			type: 'POST',
			url: '/bill/tickets/pay',
			beforeSend: function () {
				form.jmspinner();
			},
			complete: function () {
				form.jmspinner(false);
			}
		}).done((responseText, statusText, response) => {
			window.location.href = $.fn.addParameterToUrlIfExist(window.location.href, "paid", "true");
		}).fail((response, statusText) => {
			$.fn.sleep(1300); //TODO REMOVE IN PROD
		});
	});

});