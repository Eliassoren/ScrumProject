/**
 * Created by EliasBrattli on 12/01/2017.
 */
$(document).ready(function() {
    $("#hamburger-toggle").click(function(){
        $("#hamburger-menu").toggleClass("hamburger-menu-open");
        $("#hamburger-toggle").toggleClass("hamburger-toggle-open");
    });
});

/*$('#triangle').mousedown(function(e) {
 e.preventDefault();

 //create matching canvas representation for the image
 if(!this.canvas) {
 this.canvas = $('<canvas/>')[0];
 this.canvas.width = this.width;
 this.canvas.height = this.height;
 this.canvas.getContext('2d').drawImage(this, 0, 0, this.width, this.height);
 }
 var pixelData = this.canvas.getContext('2d').getImageData(e.offsetX, e.offsetY, 1, 1).data;

 //check that the pixel is not transparent
 if (pixelData[3] > 0) {
 //your code
 }
 });*/