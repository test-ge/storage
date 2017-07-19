/**
 * 
 */

function dateTransform(dateTimestamp) {												
var dateUpdate = new Date(dateTimestamp);
var dateValue = dateUpdate.toLocaleDateString(navigator.language);
alert(dateValue);
document.getElementById('spanTest').innerHTML = dateValue;
}

require([ 'jquery' ], function ($) {
	$('#creationDate').text('ma date');
});

document.getElementById('creationDate').innerText = dateValue;
document.getElementById('creationDate').innerHTML = dateValue;

document.querySelector('#creationDate > div:nth(0)').innerText = dateValue;
