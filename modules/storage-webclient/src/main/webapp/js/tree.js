$.fn.extend({
    treed: function (o) {
      
      var openedClass = 'fa fa-plus-square-o';
      var closedClass = 'fa fa-minus-square-o';
      
      if (typeof o != 'undefined'){
        if (typeof o.openedClass != 'undefined'){
        openedClass = o.openedClass;
        }
        if (typeof o.closedClass != 'undefined'){
        closedClass = o.closedClass;
        }
      };
      
        //initialize each of the top levels
        var tree = $(this);
        tree.addClass("tree");
        tree.find("li").has("ul").each(function () {
            var branch = $(this); //li with children ul
            branch.prepend("<i class='indicator " + closedClass + "' aria-hidden=true></i>");
            branch.addClass('branch');
            branch.on('click', function (e) {
                if (this == e.target) {
                    var icon = $(this).children('i:first');
                    icon.toggleClass(openedClass + " " + closedClass);
                    $(this).children().children().toggle();
                }
            })
            branch.children().children().toggle();
        });
        //fire event from the dynamically added icon
      tree.find('.branch .indicator').each(function(){
        $(this).on('click', function () {
            $(this).closest('li').click();
        });
      });
        //fire event to open branch if the li contains an anchor instead of text
        tree.find('.branch>a').each(function () {
            $(this).on('click', function (e) {
                $(this).closest('li').click();
                e.preventDefault();
            });
        });
        //fire event to open branch if the li contains a button instead of text
        tree.find('.branch>button').each(function () {
            $(this).on('click', function (e) {
                $(this).closest('li').click();
                e.preventDefault();
            });
        });
        

    }
});

//Initialization of treeviews
$(".tray").treed({openedClass:'fa fa-minus-square-o', closedClass:'fa fa-plus-square-o'});

$(document).ready(function () {
	$('#confirmDeleteModal').on('show.bs.modal', function (event) {
		var a_tag = $(event.relatedTarget)
		var storageId = a_tag.data('id')
		var storageTray = a_tag.data('tray')
		var content = 'Êtes-vous sûr de vouloir supprimer le fichier ' + storageId + ' ?'
		var modal = $(this);
		modal.find('.modal-body').text(content);	  
		modal.find('#uid').val(storageId);
		modal.find('#tray').val(storageTray);
	})
});
	

function refreshTray(tray) {
	$.ajax(location.href, {	
	    method : "GET",
	    contentType : "application/json",
	    cache: false,
	}).done(function(res){
		var body = $(res).find("#tray-" + tray + "-tree");
		var length = $(res).find("#tray-" + tray + "-length")[0].innerText;
		$("#tray-" + tray + '-tree').replaceWith(body);
		$("#tray-" + tray + '-length').addClass().replaceWith(length);
		$("#tray-" + tray + "-tree").treed({openedClass:'fa fa-minus-square-o', closedClass:'fa fa-plus-square-o'});
	}).fail(function(){
	});
};