
	/*display options*/
	function showDirectory(show){
		if(show){
			document.getElementById("directory").style.display = ""
		}else{
			document.getElementById("directory").style.display = "none"
		}
	}

	selectedTags = new Set()
    function addToGlobalTagList(text) {
		document.getElementById("allTags").innerHTML +=
				"<li class=\"headerTag\" onclick=\"handleTagClick(this)\">"+ text +"</li>";
    }
    function initializeTagList(){
		var tags = document.querySelectorAll("li")
        var tagTextSet = new Set(Array.from(tags)
			.map(tag =>{ return tag.innerText}).sort());
		tagTextSet.forEach(addToGlobalTagList);
		tags.forEach(tag =>{ tag.status = "off";})
		
        var headerTags = document.querySelectorAll(".headerTag");
        headerTags.forEach(tag =>{ tag.status = "off";})
    }
    function handleTagClick(clickedTag){
		updateTagStatus(clickedTag)
		updateLinks()
		updateTagList()
	}
	function updateTagStatus(clickedTag){
		currentStatus = clickedTag.status
		newStatus = ""
		if(currentStatus === "off"){
			newStatus = "on"
		}else{
			newStatus = "off"
		}
		tags = document.querySelectorAll("li")
		tagText = clickedTag.innerText
		for(let i = 0; i < tags.length; i++){
			if(tags[i].innerText === tagText){
				switch(String(newStatus)){
					case "off":
						tags[i].status = "off"
						tags[i].style.border = "5px outset black"
						selectedTags.delete(tagText)
						break;
					case "on":
						tags[i].status = "on"
						tags[i].style.border = "5px inset black"
						selectedTags.add(tagText)
						break;
				}
			}
		}
		
	}
    function updateLinks(){
		var links = document.querySelectorAll(".link")
		for(let i = 0; i < links.length; i++){
			var tags = links[i].getElementsByTagName("li")
			var linkDisplayStatus = "none"
			if(selectedTags.size > 0){
				var linkTagSet = Array.from(tags).map(tag => { return tag.innerText; })
				var intersection = Array.from(selectedTags).map(el => { return linkTagSet.includes(el); })
				if(!intersection.includes(false)){
					linkDisplayStatus = ""
				}
				links[i].style.display = linkDisplayStatus
			}else{
				links[i].style.display = ""
			}
		}
		
	}
	function updateTagList(){
		var links = document.querySelectorAll(".link")
		var activeLinks = Array.from(links).filter(link => {return link.style.display === ""; })
		var activeTags = new Set()
		activeLinks.forEach(link => { 
			Array.from(link.getElementsByTagName("li")).forEach(tag => {
				activeTags.add(tag.innerText);
			});
		});
		document.getElementById("allTags").innerHTML = ""
		Array.from(activeTags).sort().forEach(addToGlobalTagList);
		
		var headerTags = document.querySelectorAll(".headerTag");
        for(let i = 0; i < headerTags.length; i++){
			if(selectedTags.has(headerTags[i].innerText)){
				headerTags[i].status = "on"
				headerTags[i].style.border = "5px inset black"
			}else{
				headerTags[i].status = "off"
				headerTags[i].style.border = "5px outset black"
			}
        }
	}
