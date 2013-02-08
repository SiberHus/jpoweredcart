
function initDynaTable(attrs){
	var className = (attrs.className!=null)? attrs.className: 'dynatable';
	var rowName = (attrs.rowName!=null)? attrs.rowName: 'tr_';
	var startId = (attrs.startId!=null)? attrs.startId: 0;
	var addCallback = attrs.addCallback;
	var removeCallback = attrs.removeCallback;
	
	$('table.'+className+' tr.prototype').hide();
	// Add button functionality
	$('table.'+className+' a.add').click(function() {
		startId++;
		var master = $(this).parents('table.'+className);
		// Get a new row based on the prototype row
		var prot = master.find('.prototype').clone();
		prot.attr('class', '');
		prot.attr('style', '');
		prot.find('.id').attr('value', startId);
		prot.attr('id', rowName+startId);
		master.find('tbody').append(prot);
		if(addCallback!=null) addCallback(startId, prot);
	});
	
	// Remove button functionality
	$('table.'+className+' a.remove').live('click', function() {
		$(this).parents('tr').remove();
		if(removeCallback!=null) removeCallback();
	});
}
