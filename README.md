# links2html

A small project for converting the file format I use to store interesting links into html to be displayed on a webpage. I used a [mustache template](https://mustache.github.io/) to describe the html output format.

# Building and Running
Build by running the `package` maven task, and find the jar with dependencies in the target/ directory. The jar file can be run with `java -jar target/links2html.jar <full path to link file/file> <full path to output directory/>`

## The file format for links

Links consist of at least one url, an optional description line, and an optional list of tags, one per line. A single character is used to delimit each section. The delimiter characters are `@` for urls, `?` for the description, and `+` for tags. Additionally, `#` is used for comments, and lines starting with this character are ignored Blank lines are ignored. Here is what a link file might look like:

	#Section 1
	@
	url
	?
	description
	+
	tag1
	tag2

	@
	url1
	url2
	?
	description
	
	#Section 2
	@
	url
	+
	tag1

	@
	url
	
Of course I usually like to have both tags and descriptions associated with urls.
