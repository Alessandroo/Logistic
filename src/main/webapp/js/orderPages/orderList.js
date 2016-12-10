$(document).ready(function() {
	$( ".selectable tbody tr" ).on( "click", function() {
		$(".editHref").attr("href", "/order?action=edit&id="+document.selectedUnique);
		$("#deleteId").val(document.selectedId);
		console.log(document.selectedId);
	});
	$( ".selectable tbody tr" ).on( "click", function() {
		$(".deleteHref").attr("href", "/order?action=delete&id="+document.selectedUnique);
		$("#deleteId").val(document.selectedId);
		console.log(document.selectedId);
	});
});