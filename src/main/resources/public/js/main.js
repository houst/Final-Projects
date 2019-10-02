jQuery(document).ready(function ($) {

	/**
	 * Activate form validating
	 */
	(function () {
		'use strict';
		window.addEventListener('load', function () {
			// Fetch all the forms we want to apply custom Bootstrap validation styles to
			var forms = document.getElementsByClassName('needs-validation');
			// Loop over them and prevent submission
			var validation = Array.prototype.filter.call(forms, function (form) {
				form.addEventListener('submit', function (event) {
					if (form.checkValidity() === false) {
						event.preventDefault();
						event.stopPropagation();
					}
					form.classList.add('was-validated');
				}, false);
			});
		}, false);
	})();



	/**
	 * Delay for a number of milliseconds
	 * @param {*} delay 
	 */
	$.fn.sleep = delay => {
		var start = new Date().getTime();
		while (new Date().getTime() < start + delay);
	}



	/**
	 * append a parameter to the url
	 * @param {*} url 
	 * @param {*} param 
	 * @param {*} value 
	 */
	$.fn.addParameterToUrlIfExist = (url, param, value) => {
		var hash = {};
		var parser = document.createElement('a');

		parser.href = url;

		var parameters = parser.search.split(/\?|&/);

		for (var i = 0; i < parameters.length; i++) {
			if (!parameters[i])
				continue;

			var ary = parameters[i].split('=');
			hash[ary[0]] = ary[1];
		}

		hash[param] = value;

		var list = [];
		Object.keys(hash).forEach(function (key) {
			list.push(key + '=' + hash[key]);
		});

		parser.search = '?' + list.join('&');
		return parser.href;
	}

});