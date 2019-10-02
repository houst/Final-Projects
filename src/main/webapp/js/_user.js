jQuery(document).ready(function ($) {

	/**
	 * Login form AJAX handler
	 */
	$('#login-form').submit(function (event) {
		event.preventDefault(); // Cancel

		var form = $(this);

		if (form[0].checkValidity() === false) {
			return;
		}

		var invalidData = form.find(".alertify-log-error");
		var email = form.find('input[name="email"]');
		var password = form.find('input[name="password"]');

		var data = {
			email: email.val(),
			password: password.val(),
		};

		$.ajax({
			data: data,
			timeout: 1000,
			type: 'POST',
			url: '/login',
			beforeSend: function () {
				form.jmspinner();
			},
			complete: function () {
				form.jmspinner(false);
			}
		}).done((responseText, statusText, response) => {
			invalidData.hide();
			// window.location.href = "/?logged";
			// window.location.href = window.location.href;
			window.location.href = $.fn.addParameterToUrlIfExist(window.location.href, "logged", "true");
		}).fail((response, statusText) => {
			$.fn.sleep(1300); //TODO REMOVE IN PROD
			password.val('');
			invalidData.show();
		});
	});



	/**
	 * Registration form AJAX handler
	 */
	$('#reg-form').submit(function (event) {
		event.preventDefault(); // Cancel

		var form = $(this);

		if (form[0].checkValidity() === false) {
			return;
		}

		var alertEmailExist = form.find(".alert-email-exist");
		var alertConfirmationFailed = form.find(".alert-confirmation-failed");
		alertConfirmationFailed.hide();
		alertEmailExist.hide();

		var email = form.find('input[name="email"]');
		var password = form.find('input[name="password"]');
		var matchingPassword = form.find('input[name="matchingPassword"]');
		var username = form.find('input[name="username"]');
		var tel = form.find('input[name="tel"]');

		var data = {
			email: email.val(),
			password: password.val(),
			matchingPassword: matchingPassword.val(),
			username: username.val(),
			tel: tel.val(),
		};

		$.ajax({
			data: data,
			timeout: 1000,
			type: 'POST',
			url: '/user',
			beforeSend: function () {
				form.jmspinner();
			},
			complete: function () {
				form.jmspinner(false);
			}
		}).done((responseText, statusText, response) => {
			window.location.href = $.fn.addParameterToUrlIfExist(window.location.href, "registered", "true");
		}).fail((response, statusText) => {
			password.val('');
			matchingPassword.val('');
			alertConfirmationFailed.hide();
			alertEmailExist.hide();
			if (response.status === 400) {
				alertConfirmationFailed.show();
			} else {	
				alertEmailExist.show();
			}
		});
	});



	/**
	 * Edit user form AJAX handler
	 */
	$('#edit-user-form').submit(function (event) {
		event.preventDefault(); // Cancel

		var form = $(this);

		if (form[0].checkValidity() === false) {
			return;
		}
		
		var userId = form.find('input[name="userId"]');
		var email = form.find('input[name="email"]');
		var name = form.find('input[name="name"]');
		var number = form.find('input[name="number"]');

		var data = {
			userId: userId.val(),
			email: email.val(),
			name: name.val(),
			number: number.val(),
		};

		form.find('input[type="checkbox"]:checked').each(function() {
			var checkbox = $(this);
			data[checkbox.attr("name")] = checkbox.val();
		});

		$.ajax({
			data: data,
			timeout: 1000,
			type: 'PUT',
			url: '/user',
			beforeSend: function () {
				form.jmspinner();
			},
			complete: function () {
				form.jmspinner(false);
			}
		}).done((responseText, statusText, response) => {
			window.location.href = $.fn.addParameterToUrlIfExist(window.location.href, "updated", "true");
		}).fail((response, statusText) => {
			//invalidData.show();
		});
	});


});