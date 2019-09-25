jQuery(document).ready(function ($) {

	/**
	 * Add seance form AJAX handler
	 */
	$('#add-seance-form').submit(function (event) {
		event.preventDefault(); // Cancel

		var form = $(this);

		if (form[0].checkValidity() === false) {
			return;
		}

		// var invalidDataInput = form.find('.alertify-log-error');
		// var successDataInput = form.find('.alertify-log-success');

		var date_submit = form.find('input[name="date_submit"]');
		var time = form.find('input[name="time"]');
		var begin = date_submit.val() + 'T' + time.val();
		var movieTitle = form.find('.mdb-select option:selected');

		var data = {
			begin: begin,
			movieTitle: movieTitle.val(),
		};

		$.ajax({
			data: data,
			timeout: 1000,
			type: 'POST',
			url: '/seance',
			beforeSend: function () {
				form.jmspinner();
			},
			complete: function () {
				form.jmspinner(false);
			}
		}).done((responseText, statusText, response) => {
			// successDataInput.hide();
			// invalidDataInput.hide();
			window.location.href = $.fn.addParameterToUrlIfExist(window.location.href, "added", "true");
		}).fail((response, statusText) => {
			// successDataInput.hide();
			// invalidDataInput.show();
		});
	});

	/**
	 * Book form AJAX handler
	 */
	$('#book-form').submit(function (event) {
		event.preventDefault(); // Cancel

		var form = $(this);

		if (form[0].checkValidity() === false) {
			return;
		}

		var seanceId = form.find('input[name="seanceId"]')
		var _csrf = form.find('input[name="_csrf"]');

		var data = {
			seanceId: seanceId.val(),
			_csrf: _csrf.val()
		};

		form.find('input[type="checkbox"]:checked').each(function() {
			var checkbox = $(this);
			data[checkbox.attr("name")] = checkbox.val();
		});

		$.ajax({
			data: data,
			timeout: 1000,
			type: 'PUT',
			url: '/seance/' + seanceId.val() + '/tickets',
			beforeSend: function () {
				form.jmspinner();
			},
			complete: function () {
				form.jmspinner(false);
			}
		}).done((responseText, statusText, response) => {
			window.location.href = $.fn.addParameterToUrlIfExist(window.location.href, "added", "true");
		}).fail((response, statusText) => {
			$.fn.sleep(1300); //TODO REMOVE IN PROD
		});
	});

	/**
	 *  MDB Select Initialization
	 */ 
	$('.mdb-select').materialSelect();

	/**
	 *  MDB Date & Time picker UA locale
	 */
	if (window.location.pathname.substring(1, 3) == 'ua') {
		jQuery.extend( jQuery.fn.pickadate.defaults, {
			monthsFull: [ 'січень', 'лютий', 'березень', 'квітень', 'травень', 'червень', 'липень', 'серпень', 'вересень', 'жовтень', 'листопад', 'грудень' ],
			monthsShort: [ 'січ', 'лют', 'бер', 'кві', 'тра', 'чер', 'лип', 'сер', 'вер', 'жов', 'лис', 'гру' ],
			weekdaysFull: [ 'неділя', 'понеділок', 'вівторок', 'середа', 'четвер', 'п‘ятниця', 'субота' ],
			weekdaysShort: [ 'нд', 'пн', 'вт', 'ср', 'чт', 'пт', 'сб' ],
			today: 'сьогодні',
			clear: 'викреслити',
			firstDay: 1,
			format: 'dd mmmm yyyy p.',
			formatSubmit: 'yyyy-mm-dd'
		});
		jQuery.extend( jQuery.fn.pickatime.defaults, {
			clear: 'викреслити'
		});
		$('.datepicker').pickadate({
			formatSubmit: 'yyyy-mm-dd'
		});
		$('#input_starttime').pickatime({
			donetext: "добавити",
			twelvehour: false,
			autoclose: true
		});

	} else {
		/**
		 *  MDB Data Picker Initialization
		 */
		$('.datepicker').pickadate({
			formatSubmit: 'yyyy-mm-dd'
		});

		/**
		 *  MDB Time Picker Initialization
		 */
		$('#input_starttime').pickatime({
			twelvehour: false,
			autoclose: true
		});
	}

});