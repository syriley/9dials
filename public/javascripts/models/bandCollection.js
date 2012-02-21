var BandCollection = Backbone.Collection.extend({
	model:Band,
	url: "/bands/list"	
});