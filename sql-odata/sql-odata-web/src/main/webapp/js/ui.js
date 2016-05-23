"use strict";

window.addEventListener('popstate', function(event) {	   	
	var uriInfo = new UriInfo(event.state);				      	
	dispatch($metadata,uriInfo);		
});

function navigationHandler(event) {
	var href = event.target.getAttribute('href');			      	
	var uriInfo = new UriInfo(href);	     	
	dispatch($metadata,uriInfo);
	history.pushState(href, null, uriInfo.toUIUri());
	return event.preventDefault();
}

function renderHtml(id,innerHtml){	
	var element = document.getElementById(id);
	element.innerHTML = innerHtml;
	var links = document.querySelectorAll( "#" + id +  " a.odataUri");					 
	for (var i = 0, l = links.length; i < l; i++) {
		links[i].addEventListener('click', navigationHandler, true);
	}	
}

function metadataCallback(metadata){
	var locationInfo = new  UriInfo(location.href);
	var list = "<ul>";
	for (var e in metadata.entitySets) {						
		var uriInfo = new UriInfo(locationInfo.scheme + "://" +
				locationInfo.server + ":" + locationInfo.port + "/" + 
				locationInfo.contextPath + "/" + e);		

		uriInfo.parameters.$top = 20;
		uriInfo.parameters.$skip = 0;
		list += "<li><a class=\"odataUri\" href=\"{0}\">{1}</a></li>"
			.format(uriInfo.toUIUri(),e);
	}
	list += "</ul>";									
	renderHtml("menu",list)

	dispatch(metadata,locationInfo);
}

function isSelected(uriInfo,propName){
	if(uriInfo.parameters === undefined){
		return true;
	}
	var selectOption = uriInfo.parameters.$select;
	if(selectOption === undefined || selectOption == "*"){
		return true;
	}else {
		return selectOption.split(",").indexOf(propName) != -1;
	}

}

function buildEntityView(uriInfo){
	$service.get(uriInfo, function(data,$metadata) {		
		var	eSetName = uriInfo.pathInfo[0].name;
		var entityType = $metadata.resolveEntityType(uriInfo);		
		var dataTable = "<div class=\"header\"><h2>{0}</h2></div><table>".format(uriInfo.getPath());
		for(var prop in entityType.properties){
			if(isSelected(uriInfo,prop)){
				dataTable += "<tr><td>{0}</td><td>{1}</td></tr>".format(prop,data[prop]);
			}
		}
		for(var nav in entityType.navProperties){
			var propTypeName = entityType.navProperties[nav].type;
			var navInfo = uriInfo.toUri().toUriInfo();
			navInfo.pathInfo.push({"name" : nav });
			navInfo.parameters.$top = 20;
			navInfo.parameters.$skip = 0;
			dataTable += "<tr><td>{0}</td><td><a class=\"odataUri\" href=\"{1}\">{2}</a></td></tr>".format(nav,navInfo.toUIUri(),propTypeName.split(".")[1]);
		}		
		dataTable += "</table>";	

		renderHtml("dataTable",dataTable);

	});
}

function buildEntitySetView(uriInfo) {			
	$service.get(uriInfo, function(data,$metadata) {

		var entityType = $metadata.resolveEntityType(uriInfo);
		var entities = data.value;		
		var dataTable = "<div class=\"header\"><h2>{0}</h2></div><table><thead><tr>".format(uriInfo.getPath());
		var colCount = 0;
		for (var k in entityType.keys) {
			if(isSelected(uriInfo,k)){
				dataTable += "<th>{0}</th>".format(k.split("_").join("<br/>"));
				colCount++;		
			}
		}
		for (var p in entityType.properties) {
			if(entityType.keys[p] === undefined && isSelected(uriInfo,p)){
				dataTable += "<th>{0}</th>".format(p.split("_").join("<br/>"));
				colCount++;
			}	
		}
		dataTable += "</thead></tr><tbody>";
		for (var i in entities) {
			var row = entities[i];
			if (i % 2 == 1) {
				dataTable += "<tr >";
			} else {
				dataTable += "<tr class=\"alt\">";
			}
			var entityKey = {};			
			var entityUri = uriInfo.toServiceUri().toUriInfo();
			for (var k in entityType.keys) {				
					entityKey[k] = row[k];				
			}	
			entityUri.pathInfo = [{"name" : entityType.name }];
			entityUri.pathInfo[0].keys = entityKey; 
			delete entityUri.parameters.$top;
			delete entityUri.parameters.$skip;			
			for (var k in entityType.keys) {	
				if(isSelected(uriInfo,k)){
				dataTable += "<td><a class=\"odataUri\" href=\"{0}\">{1}</a></td>".format(entityUri.toUIUri(),row[k]);
				}
			}
			for (var col in entityType.properties) {
				if(entityType.keys[col] === undefined && isSelected(uriInfo,col)){
					dataTable += "<td>{0}</td>".format(row[col]);
				}
			}
			dataTable += "</tr>";
		}
		for(var j = 1; j < uriInfo.parameters.$top - i; j++ ){
			dataTable += "<tr><td colspan=\"{0}\">&nbsp;</td></tr>".format(entityType.properties.lenght);
		}
		dataTable += "</tbody>";
		var previus = new UriInfo(uriInfo.toUri());

		var skip = parseInt(uriInfo.parameters.$skip) - parseInt(uriInfo.parameters.$top);
		var previus = new UriInfo(uriInfo.toUri());
		previus.parameters.$skip = skip < 0 ? 0 : skip;

		var next = new UriInfo(uriInfo.toUri());
		if(++i == parseInt(next.parameters.$top)){
			next.parameters.$skip = parseInt(uriInfo.parameters.$skip) + parseInt(uriInfo.parameters.$top);
		}		
		dataTable += document.getElementById("dataTableFoot").
		innerHTML.format(colCount,previus.toUri(),next.toUri());				
		dataTable += "</table>";

		renderHtml("dataTable",dataTable);


	});
}

