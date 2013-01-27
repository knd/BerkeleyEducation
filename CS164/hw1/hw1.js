// ==UserScript==
// @name       cs164 hw1
// @namespace  http://use.i.E.your.homepage/
// @version    0.1
// @description  facebook activity visualization
// @match      http://www.facebook.com/*
// @require http://d3js.org/d3.v3.min.js
// @require http://d3js.org/d3.geo.projection.v0.min.js
// @require http://d3js.org/topojson.v0.min.js
// @copyright  2013+, Khanh Dao
// ==/UserScript==

	
	/////////////////// METADATA /////////////////////
	var MAP_WIDTH = 1500, MAP_HEIGHT = 1500;
	var MAP_URL = "http://www.cs.berkeley.edu/~bodik/world.json";
	var GEO_URL = "http://maps.googleapis.com/maps/api/geocode/json?";
	
    var svg = d3.select("body").insert("svg", ":first-child")
								.attr("width", MAP_WIDTH / 0.5)
								.attr("height", MAP_HEIGHT / 2.8);
								
	var projection = d3.geo.miller().scale(135);
	
	var people = [];
	var locations = [];
	//////////////////// end of METADATA //////////////
	
	// initially draw the world map
	window.addEventListener('load', function() {
		GM_xmlhttpRequest({
			method: "GET",
			url: MAP_URL,
			onload: function(response) {
	    		var world = JSON.parse(response.responseText);
	            var countries = topojson.object(world, world.objects.countries);
				var path = d3.geo.path().projection(projection);
				svg.append("path").datum(countries).attr("d", path);
				
	            // make border and color the map
	            svg.selectAll(".country")
			    		.data(topojson.object(world, world.objects.countries).geometries)
			  			.enter().append("path")
			    		.attr("class", function(d) { return "country " + d.id; })
			    		.attr("d", path).style("fill", "#DDC");
			}
		});
        
        setTimeout(getFriendsLinks, 1000);
	}, true);
	
	// utility function to check if an array contains element
	var contains = function(array, element) {
		var i;
		for (i = 0; i < array.length; i++) {
			if (array[i] === element) {
				return true;
			}
		}
		return false;
	}
	
	// utility function to draw red dot
	// @params is object that contains longitude & latitude
	var drawLocation = function(params) {
		var longitude = params.longitude;
		var latitude = params.latitude;
		var points = svg.append("g").attr("trid", "points");
		var bvCoords = projection([longitude, latitude]);
		points.append("svg:circle")
		    .attr("cx", bvCoords[0]).attr("r", 2).attr("cy", bvCoords[1])
		    .style("fill", "red");										
	};
	
	// utility function to extract location based on html text
	// the text is typically text/html of friends' pages
	// @text is string, @location returned can be "Seattle, Washington"
	var extractLivesIn = function(text) {
	    var reg = text.match(/Lives in (.*)>$/gm);
	    var elem = document.createElement("div");
	    elem.innerHTML = reg;
	    var location;
	    try {
	        location = elem.getElementsByTagName("a")[0].innerHTML;
	    } catch(err) {
	        console.log("can't parse html");
	    }
	    return location;
	};
	
	// utility function to send AJAX request and find out
	// longitude as well as latitude of given location
	var geocoding = function(params, drawLocation) {
		var location = params.location || "Berkeley";

	 	GM_xmlhttpRequest({
	  		method: "GET",
	  		url: GEO_URL + "address=" + location + "&sensor=true",
	  		onload: function(response) {
	            var response = JSON.parse(response.responseText);
	        	params.latitude = response.results[0].geometry.location.lat;
	        	params.longitude = response.results[0].geometry.location.lng;
	        	drawLocation(params);
	        }
	    });
	};

	// utitlity function to process the grabbed link
	// this typically send AJAX request ask for location
	// add to metadata 'people' and 'locations'
	// note that no duplicate will be added to metadata
	// because we don't want to draw the same red dot multiple times
	var processTheLink = function(link) {
    	if (!contains(people, link)) {
			people.push(link);
		    GM_xmlhttpRequest({
		  		method: "GET",
		  		url: link,
		  		onload: function(response) {
		           	var text = response.responseText;
		            var location = extractLivesIn(text);
                    console.log(location);
					if (!contains(locations, location)) {
						locations.push(location);
						geocoding({ location: location }, drawLocation);
					}   
		        }
		    });
		}
	};

	// get people with .actorName class
	// most likely grab the divs
	var getActorLinks = function() {
   		var divs = d3.selectAll(".actorName").each( function() {
	     	var link = d3.select(this).select("a").attr("href");
			//console.log(link);
			processTheLink(link);
			
	    }); 
	};

	// get links <a> with class .passiveName
	// most likely people with stuff shares, likes, doing stuff
	// that automatically broadcast to facebook
	var getPassiveNameLinks = function() {
        var aTags = d3.selectAll(".passiveName").each( function() {
            var link = d3.select(this).attr("href");
            //console.log(link);
            processTheLink(link);
        });
	};

	// get the people who comment on post
	// these are links <a> 
	var getUFICommentActorName = function() {
        var aTags = d3.selectAll(".UFICommentActorName").each( function() {
         	var link = d3.select(this).attr("href");
            //console.log(link);
            processTheLink(link);     
        });
	};
	
	
	// get all the links and extract where they live
	// link to be pushed global 'people'
	// location to be pushed global 'locations'
	var getFriendsLinks = function() {
       	getActorLinks();   
        getPassiveNameLinks();
        getUFICommentActorName();
        setTimeout(getFriendsLinks, 1000);
	};

	
