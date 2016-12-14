$(document).ready(function() {
	$( ".selectable tbody tr" ).on( "click", function() {
		$(".editHref").attr("href", "/order?action=addOrder&carCrewId="+document.selectedUnique);
		$(".deleteHref").attr("href", "/carCrew?action=delete&id="+document.selectedUnique);
		$(".recomendHref").attr("href", "/carCrew?action=recomendation&id="+document.selectedUnique);
		$("#deleteId").val(document.selectedId);
		console.log(document.selectedId);
	});
});