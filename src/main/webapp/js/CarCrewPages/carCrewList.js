$(document).ready(function() {
	$( ".selectable tbody tr" ).on( "click", function() {
		$(".editHref").attr("href", "/carCrew?action=edit&id="+document.selectedUnique);
		$("#deleteId").val(document.selectedId);
		console.log(document.selectedId);
	});
	$( ".selectable tbody tr" ).on( "click", function() {
		$(".deleteHref").attr("href", "/carCrew?action=delete&id="+document.selectedUnique);
		$("#deleteId").val(document.selectedId);
		console.log(document.selectedId);
	});
});