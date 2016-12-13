$(document).ready(function() {
	$( ".selectable tbody tr" ).on( "click", function() {
		$(".editHref").attr("href", "/order?action=edit&id="+document.selectedId);
		$(".addOrderHref").attr("href", "/order?action=setOrder&id="+document.selectedId);
        $("#deleteId").val(document.selectedId);
		console.log(document.selectedId);
	});
});