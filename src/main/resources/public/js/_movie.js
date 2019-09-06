jQuery(document).ready(function ($) {

	/**
	 * Add new movie form AJAX handler
	 */
	$('#add-movie-form').submit(function (event) {
		event.preventDefault(); // Cancel

		var form = $(this);

		if (form[0].checkValidity() === false) {
			return;
		}

		var title = form.find('input[name="title"]');
		var duration = form.find('input[name="duration"]');
		var description = form.find('input[name="description"]');
		var _csrf = form.find('input[name="_csrf"]');

		var data = {
			title: title.val(),
			duration: duration.val(),
			description: description.val(),
			_csrf: _csrf.val()
		};

		$.ajax({
			data: data,
			timeout: 1000,
			type: 'POST',
			url: '/movie',
			beforeSend: function () {
				form.jmspinner();
			},
			complete: function () {
				form.jmspinner(false);
			}
		}).done((responseText, statusText, response) => {
			window.location.href = $.fn.addParameterToUrlIfExist("/en/movies", "movieadded", "true");
		}).fail((response, statusText) => {
			
		});
	});

	/**
	 * Edit movie form AJAX handler
	 */
	$('#edit-movie-form').submit(function (event) {
		event.preventDefault(); // Cancel

		var form = $(this);

		if (form[0].checkValidity() === false) {
			return;
		}

		
		var movieId = form.find('input[name="movieId"]')
		var title = form.find('input[name="title"]');
		var duration = form.find('input[name="duration"]');
		var description = form.find('input[name="description"]');
		var _csrf = form.find('input[name="_csrf"]');

		var data = {
			movieId: movieId.val(),
			title: title.val(),
			duration: duration.val(),
			description: description.val(),
			_csrf: _csrf.val()
		};

		$.ajax({
			data: data,
			timeout: 1000,
			type: 'PUT',
			url: '/movie/' + movieId.val(),
			beforeSend: function () {
				form.jmspinner();
			},
			complete: function () {
				form.jmspinner(false);
			}
		}).done((responseText, statusText, response) => {
			window.location.href = $.fn.addParameterToUrlIfExist(window.location.href, "updated", "true");
		}).fail((response, statusText) => {
			$.fn.sleep(1300); //TODO REMOVE IN PROD
			//invalidData.show();
		});
	});

});